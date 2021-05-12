package com.etnet.coresdk.coreSubscriberRequestChart;

import java.util.ArrayList;
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
import com.etnet.coresdk.util.ChartHelper;
import com.etnet.coresdk.util.DataHelper;
import com.etnet.coresdk.util.SecurityCodeHelper;

import static com.etnet.coresdk.constants.EndPoints.ENDPOINT_FUTSERVLET;
import static com.etnet.coresdk.constants.EndPoints.ENDPOINT_INDEXSERVLET;
import static com.etnet.coresdk.constants.EndPoints.ENDPOINT_SECSERVLET;

public class DayChartSubscriber extends GenericChartSubscriber
        implements OnQuoteDataReceived {
    final Logger log = Logger.getLogger("DayChartSubscriber");

    HttpSubscriber _httpSubscriber;
    OnQuoteDataReceived _onQuoteDataReceived;
    Pattern _periodRegExp;
    Pattern _replaceRegExp;

    public DayChartSubscriber(String name) {
        super(name);
        _httpSubscriber = new HttpSubscriber("HttpSubscriber@" + name);
        _httpSubscriber.setOnQuoteDataReceived(this);
        _periodRegExp = Pattern.compile("I(d+)N?S");
        _replaceRegExp = Pattern.compile("[INS]*");
    }

    @Override
    public void setContext(NssCoreContext context) {
        super.setContext(context);
        _httpSubscriber.setContext(context);
    }

    /**
     * group incoming data list
     *
     * @param code     stock code
     * @param data     incoming data list
     * @param period   chart period
     * @param snapshot historical data
     */
    List<Transaction> groupTransaction(
            String code, List<Transaction> data, String period, boolean snapshot) {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        for (Transaction row : data) {
            // for each transaction
            int timestamp = row.getTimestamp();
            double nominal = row.getNominal();
            double volume = row.getVolume();
            // determine the grouping time for this data
            int groupTimestamp = getContext()
                    .getController()
                    .getGroupingController()
                    .groupDaily(code, period, timestamp);
            Transaction groupData = DataHelper.getTransaction(
                    transactionList, null, null, groupTimestamp);
            if (groupData != null) {
                if (groupData.getBaseVolume() != null) {
                    // groupData is hist data, then we update all value except baseVolume
                    groupData.setClose(nominal);
                    groupData.setNominal(row.getNominal());
                } else if (row.getBaseVolume() != null) {
                    // row is hist data, then we update all value except baseVolume
                    groupData.setClose(row.getClose());
                    groupData.setBaseVolume(row.getBaseVolume());
                }

                groupData.setDisplayTimestamp(row.getTimestamp());
                groupData.setGroupTimestamp(groupTimestamp);
                groupData.setTimestamp(row.getTimestamp());
                groupData.setOpen(row.getOpen());
                groupData.setHigh(row.getHigh());
                groupData.setLow(row.getLow());
                groupData.setVolume(row.getVolume());
                groupData.setTranType(row.getTranType());
                groupData.setTransNo(row.getTransNo());
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
                        row.getTransNo());

                // If historical data come first
                if (row.getBaseVolume() != null) {
                    record.setBaseVolume(row.getBaseVolume());
                }

                if (transactionList != null) {
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
            put(HttpParam.HTTPPARAM_MARKET, getContext().getConfig().getMarket());// HK/US
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

        super.subscribe();
        _httpSubscriber.subscribe();
    }

    @Override
    public void onQuoteDataReceived(List<QuoteData> bundle) {
        // notify chart-subscriber
        onDataUpdated(bundle);
    }

    public void setOnQuoteDataReceived(OnQuoteDataReceived onQuoteDataReceived) {
        _onQuoteDataReceived = onQuoteDataReceived;
    }

    @Override
    public void onDataUpdated(Object quoteDataList) {
        List<String> currentSubscribedCodes = super.currentCodes();
        List<String> currentSubscribedFields = super.currentFields();
        if (currentSubscribedCodes != null && currentSubscribedFields != null){
            if (currentSubscribedCodes.size() > 0 &&
                    currentSubscribedFields.size() > 0) {
                String code = currentSubscribedCodes.get(0);
                String fieldId = currentSubscribedFields.get(0);
                String period = "";
                boolean matchedPeriod = _periodRegExp.matcher(fieldId).matches();
                if (matchedPeriod) {
                    period = _replaceRegExp.matcher(fieldId).replaceAll("");
                } else {
                    log.info("no matching period");
                }

                if (quoteDataList instanceof java.util.List) {
                    List<QuoteData> processedQuoteDataList = (List<QuoteData>) quoteDataList;

                    for (QuoteData quoteData : processedQuoteDataList) {
                        NssData nssData = quoteData.getNssData();
                        boolean snapshot = nssData.getSnapshot();
                        List<Transaction> data = (List<Transaction>) nssData.getData();
                        if (data == null) {
                            return;
                        }
                        try {
                            // for 1,5,15,30,60 period
                            // data is a group of historical transaction data from http
                            // or a single transcation from open,high,low,close,volume

                            // for daily,weekly,monthly period
                            // data is group of historical transaction data from http
                            // or a single groupped transaction of multiple transaction queue

                            // we now group it in transaction list (this is still NOT the final list
                            // which will send to front-end component), most of time, data is a single record list
                            //  which represent it is a update from stream data

                            // NOTE: week/month data will have latest day in grouping time!
                            List<Transaction> transactionList =
                                    this.groupTransaction(code, data, period, snapshot);
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
    }
}
