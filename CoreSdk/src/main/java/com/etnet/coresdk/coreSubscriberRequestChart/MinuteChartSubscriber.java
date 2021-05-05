package com.etnet.coresdk.coreSubscriberRequestChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.etnet.coresdk.api.OnQuoteDataReceived;
import com.etnet.coresdk.constants.HttpParam;
import com.etnet.coresdk.constants.SecurityID;
import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreStorage.model.Transaction;
import com.etnet.coresdk.util.AsaDataHelper;
import com.etnet.coresdk.util.ChartHelper;
import com.etnet.coresdk.util.DataHelper;
import com.etnet.coresdk.util.DateHelper;
import com.etnet.coresdk.util.SecurityCodeHelper;

import static com.etnet.coresdk.constants.EndPoints.ENDPOINT_FUTSERVLET;
import static com.etnet.coresdk.constants.EndPoints.ENDPOINT_INDEXSERVLET;
import static com.etnet.coresdk.constants.EndPoints.ENDPOINT_SECSERVLET;

public class MinuteChartSubscriber extends GenericChartSubscriber
        implements OnQuoteDataReceived {
    final Logger log = Logger.getLogger("MinuteChartSubscriber");
    HttpSubscriber _httpSubscriber;
    Integer _lastTransNo;
    OnQuoteDataReceived _onQuoteDataReceived;

    public MinuteChartSubscriber(String name) {
        super(name);
        _httpSubscriber = new HttpSubscriber("HttpSubscriber@" + name);
        _httpSubscriber.setOnQuoteDataReceived(this);
    }

    @Override
    public void setContext(NssCoreContext context) {
        super.setContext(context);
        _httpSubscriber.setContext(context);
    }

    /**
     * Group a small list recevied from stream or http
     *
     * @param code     stock code
     * @param data     incoming transactions
     * @param period   chart period
     * @param snapshot historical data/from http
     */
    List<Transaction> groupTransaction(
            String code, List<Transaction> data, String period, boolean snapshot) {
        int secId = SecurityCodeHelper.getSecurityID(code);
        List<Transaction> transactionList = new ArrayList<Transaction>();
        String codeType = SecurityCodeHelper.getSecurityMarketType(code);
        int tradingDay = AsaDataHelper.getMarketTradingDay(codeType, getContext());
        Calendar t = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");
        t.setTimeInMillis(tradingDay);
        Date date = t.getTime();
        String tradingDayString = format1.format(date);

        for (Transaction row : data) {
            boolean isDisallowTransType = row.getTranType() != " " &&
                    row.getTranType() != "U" &&
                    (row.getTranType() != "0" && row.getTranType() == "");
            if (row.getTranType() != null && isDisallowTransType) {
                continue; // trans type = X, Y are "dont care" transactions
            }

            int timestamp = row.getTimestamp();
            double nominal = row.getNominal();
            double volume = row.getVolume();

            Calendar t2 = Calendar.getInstance();
            SimpleDateFormat format2 = new SimpleDateFormat("HHmm");
            t2.setTimeInMillis(timestamp);
            Date date2 = t2.getTime();
            String time = format2.format(date2);

            // determine the grouping time for this data
            int correctedPeroid = Integer.parseInt(period, 10);
            int groupTimestamp = 0;
            if (snapshot) {

                Calendar t3 = Calendar.getInstance();
                SimpleDateFormat format3 = new SimpleDateFormat("yyyyMMdd");
                t3.setTimeInMillis(timestamp);
                Date date3 = t3.getTime();
                String dataDay = format3.format(date2);


                if (Integer.parseInt(dataDay) > Integer.parseInt(tradingDayString)) {
                } else if (dataDay == tradingDayString) {
                    // for data in trading day, the times are not being groupped, we need to do it outself here
                    String groupTime = getContext().getController()
                            .getGroupingController()
                            .groupMin(code, secId, correctedPeroid, time);
                    groupTimestamp = DateHelper.createTimstampFromGroupTime(timestamp, groupTime);
                } else {
                    groupTimestamp = timestamp;
                }
            } else {
                String groupTime = getContext().getController()
                        .getGroupingController()
                        .groupMin(code, secId, correctedPeroid, time);
                groupTimestamp =
                        DateHelper.createTimstampFromGroupTime(timestamp, groupTime);
            }

            // Update transaction number
            int currentTransNo;
            if (row.getTransNo() != null && row.getTransNo() != 0) {
                currentTransNo = row.getTransNo();
                if (_lastTransNo == null || currentTransNo > _lastTransNo) {
                    _lastTransNo = currentTransNo;
                } else if (currentTransNo != -1) {
                    log.info("drop old transaction currentTransNo= ${currentTransNo}");
                    continue;
                }
            }

            // only for this data bundle... will not affect the old or newer data bundle
            // For historical data, it will check many times.
            // For realtime, it will likely check once only.
            Transaction groupData = DataHelper.getTransaction(
                    transactionList, null, null, groupTimestamp);
            if (groupData != null) {
                // this group data can be historical or realtime
                // if same groupTime exists, we update its OHLC and Vol
                if (nominal > groupData.getHigh()) {
                    groupData.setHigh(nominal);
                }
                if (nominal < groupData.getLow()) {
                    groupData.setLow(nominal);
                }
                // hist/real
                if (groupData.getBaseVolume() != null) {
                    groupData.setClose(nominal); // The nominal of this record should be the new close value
                } else {
                    if (row.getBaseVolume() != null) {
                        groupData.setBaseVolume(row.getBaseVolume());
                    }
                    groupData.setClose(row.getClose()); // The nominal of this record should be the new close value
                }
                groupData.setVolume(groupData.getVolume() + volume); // Accumulate
                groupData.setTransNo(row.getTransNo()); // Accumulate
                if (timestamp > groupData.getDisplayTimestamp()) {
                    // the display time is determined by security"s type and market
                    boolean isAfternoonClose = getContext()
                            .getController()
                            .getGroupingController()
                            .isAfternoonClose(code, secId, correctedPeroid, timestamp);
                    if (!isAfternoonClose) {
                        groupData.setDisplayTimestamp(timestamp);
                    }
                }
            } else {
                // Add a new record (both historical and realtime)
                Transaction record = new Transaction(
                        timestamp,
                        groupTimestamp,
                        timestamp,
                        (row.getOpen() != null) ? row.getOpen() : nominal,
                        (row.getHigh() != null) ? row.getHigh() : nominal,
                        (row.getLow() != null) ? row.getLow() : nominal,
                        (row.getClose() != null) ? row.getClose() : nominal,
                        nominal,
                        volume,
                        null,
                        row.getTranType(),
                        row.getTransNo()
                );

                // If historical data come first
                if (row.getBaseVolume() != null) {
                    record.setBaseVolume(row.getBaseVolume());
                }

                if (transactionList.size() == 0) {
                    transactionList.add(record);
                } else {
                    // old to new
                    if (groupTimestamp >
                            transactionList.get(transactionList.size() - 1).getGroupTimestamp()) {
                        // most
                        transactionList.add(record);
                    } else if (groupTimestamp < transactionList.get(0).getGroupTimestamp()) {
                        transactionList.add(0, record); // I am the first one
                    } else {
                        log.info("insertion error");
                    }
                }
            }
        }

        return transactionList;
    }

    public void subscribe() {
        final String code = willProcessCodes().get(0);
        final int codeGenericType = SecurityCodeHelper.getGeneralSecurityID(code);
        final String httpPeriodFormat =
                ChartHelper.getRequestPeriod(codeGenericType, getPeriod(), true);
        final String nssPeriodFormat =
                ChartHelper.getRequestPeriod(codeGenericType, getPeriod(), false);
        boolean isAHType = SecurityCodeHelper.isAShareCode(code);
        Map<String, String> params = new HashMap<String, String>() {{
            put(HttpParam.HTTPPARAM_CODE, SecurityCodeHelper.formatSecurityCodeForHttp(code));
            put(HttpParam.HTTPPARAM_MINTYPE, httpPeriodFormat);
            put(HttpParam.HTTPPARAM_ISTODAY, isTradingDayOnly() ? "1" : "0");
            put(HttpParam.HTTPPARAM_AH, isAHType ? "1" : "0");
            put(HttpParam.HTTPPARAM_MARKET, getContext().getConfig().getMarket()); // HK/US
    }};

        if (getRange() != 0) {
            params.put(HttpParam.HTTPPARAM_LIMIT, String.valueOf(getRange()));
        }
        _httpSubscriber.setCode(code);
        String apiEndpoint = "";
        log.info("codeGenericType: ${codeGenericType}");
        switch (codeGenericType) {
            case SecurityID.SECURITYID_FUTURE:
                apiEndpoint = ENDPOINT_FUTSERVLET;
                break;
            case SecurityID.SECURITYID_INDEX:
                apiEndpoint = ENDPOINT_INDEXSERVLET;
                break;
            case SecurityID.SECURITYID_STOCK:
                apiEndpoint = ENDPOINT_SECSERVLET;
                break;
            default:
                log.info("unknown security type");
                break;
        }

        _httpSubscriber.setEndPoint(apiEndpoint);
        _httpSubscriber.setFieldID(nssPeriodFormat);
        _httpSubscriber.setParams(params);

        broadcast(true);
        super.subscribe();
        _httpSubscriber.subscribe();
    }

    @Override
    public void onQuoteDataReceived(List<QuoteData> bundle) {
        // notify chart-subscriber
        onDataUpdated(bundle);
    }

    public void setOnQuoteDataReceived(OnQuoteDataReceived onQuoteDataReceived) {
        this._onQuoteDataReceived = onQuoteDataReceived;
    }

    @Override
    public void onDataUpdated(Object quoteDataList) {
        List<String> currentCodes = super.currentCodes();
        List<String> currentFields = super.currentFields();
        if (currentCodes.size() == 0 || currentFields.size() == 0) {
            return;
        }
        String code = currentCodes.get(0);
        String fieldId = currentFields.get(0);
        String period = "";
        boolean matchedPeriod = Pattern.compile("I(d+)N?S").matcher(fieldId).matches();
        if (matchedPeriod) {
            period = String.valueOf(fieldId.charAt(1));
        } else {
            log.info("no matching period");
        }
        List<QuoteData> processedQuoteDataList = new ArrayList<QuoteData>();
        if (quoteDataList instanceof java.util.List){
            List<QuoteData> _tempQuoteData = (List<QuoteData>) quoteDataList;
            for (QuoteData quoteData : _tempQuoteData){
                NssData nssData = quoteData.getNssData();
                boolean snapshot = nssData.getSnapshot();
                List<Transaction> data = (List<Transaction>) nssData.getData();
                try {
                    List<Transaction> transactionList =
                            groupTransaction(code, data, period, snapshot);
                    QuoteData fwQuoteData = new QuoteData(0, new NssData(transactionList));
                    fwQuoteData.setCode(code);
                    fwQuoteData.getNssData().setFieldID(fieldId);
                    fwQuoteData.getNssData().setName(fieldId);
                    fwQuoteData.getNssData().setData(transactionList);
                    fwQuoteData.getNssData().setSnapshot(snapshot);
                    fwQuoteData.getNssData().setReady(false);
                    processedQuoteDataList.add(fwQuoteData);
                    // order ASC(transNo)
                    if (_onQuoteDataReceived != null) {
                        _onQuoteDataReceived.onQuoteDataReceived(processedQuoteDataList);
                    }
                } catch (Exception e) {
                    log.warning(e.getMessage());
                }
            }
        }
    }
}
