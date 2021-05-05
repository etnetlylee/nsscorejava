package com.etnet.coresdk.coreStorage.data.decoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.Transaction;

import com.etnet.coresdk.util.AsaDataHelper;
import com.etnet.coresdk.util.SecurityCodeHelper;

public class DayChartDecoder extends Decoder {
    public static final String uniqueID = "daychart";
    final Logger log = Logger.getLogger("DayChartDecoder");

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        final List<String> value = (List<String>) rawData.getData();
        NssData nssData = new NssData(null);
        List<Transaction> transactionList = (List<Transaction>) nssData.getData();
        if (transactionList == null) {
            transactionList = new ArrayList<Transaction>();
        }
        String securityMarket = SecurityCodeHelper.getSecurityMarketType(code);
        final int tradingDay =
                AsaDataHelper.getMarketTradingDay(securityMarket, getContext());
        if (tradingDay == 0) {
            log.info("invalid trading day");
        } else {
            // "1471939793000,204.200,205.400,202.400,203.600,8919309",
            for (String rows : value) {
                final List<String> row = Arrays.asList(rows.split(","));
                if (row.size() >= 6) {
                    int timestamp = Math.abs(Integer.parseInt(row.get(0)));
                    transactionList.add(new Transaction(
                            0,
                            0,
                            timestamp,
                            Double.parseDouble(row.get(1)),
                            Double.parseDouble(row.get(2)),
                            Double.parseDouble(row.get(3)),
                            Double.parseDouble(row.get(4)),
                            Double.parseDouble(row.get(4)),
                            Double.parseDouble(row.get(5)),
                            Double.parseDouble(row.get(5)), null, -1
                    ));
                }
            }

            Collections.reverse(transactionList);
            List<Transaction> unshiftList = new ArrayList<Transaction>(transactionList);

            nssData.setData(unshiftList);
        }

        log.info("snap receive and decode: ${transactionList}");
        nssData.setReady(true);
        nssData.setFieldID(rawData.getFieldID() + "H");
        nssData.setSnapshot(true);
        return nssData;
    }

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached =
                getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        final String value = rawData.getData().toString();
        String securityMarket = SecurityCodeHelper.getSecurityMarketType(code);
        Calendar tradingDay = Calendar.getInstance();
        tradingDay.setTimeInMillis(AsaDataHelper.getMarketTradingDay(securityMarket, getContext()));
        tradingDay.add(Calendar.DATE, 1);
        Date date = tradingDay.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String td = format.format(date);

        NssData nssData = cached.getNssData();
        if (nssData == null) {
            nssData = new NssData(null);
        }
        List<Transaction> data = (List<Transaction>) nssData.getData();
        if (data == null) {
            data = new ArrayList<Transaction>();
        }

        if (value.length() == 0) {
            return nssData;
        }

        Transaction tradingDayTransaction = new Transaction(0, 0, 0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, 0);
        boolean found = false;
        for (Transaction transaction : data) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(transaction.getTimestamp());
            cal.add(Calendar.DATE, 1);
            Date date2 = tradingDay.getTime();
            SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
            String d = format.format(date);

            if (td == d) {
                // update trading day"s bar only
                // in case today is Sat, we still receive snap data
                found = true;
                tradingDayTransaction = transaction;
                break;
            }
        }

        if (!found) {
            tradingDayTransaction = new Transaction(
                    0, 0, 0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, null, -1

            );
            data.add(tradingDayTransaction);
        }
        if (tradingDayTransaction.getTimestamp() == 0) {
            // use latest timestamp.... as display time
            tradingDayTransaction.setTimestamp((int) tradingDay.getTimeInMillis());
        }
        String serverFieldId = rawData.getServerFieldId();
        if (serverFieldId == "34") {
            double v = Double.parseDouble(value);
            tradingDayTransaction.setClose(v);
            tradingDayTransaction.setNominal(v);
        } else if (serverFieldId == "54") {
            tradingDayTransaction.setOpen(Double.parseDouble(value));
        } else if (serverFieldId == "41") {
            tradingDayTransaction.setHigh(Double.parseDouble(value));
        } else if (serverFieldId == "42") {
            tradingDayTransaction.setLow(Double.parseDouble(value));
        } else if (serverFieldId == "38") {
            tradingDayTransaction.setVolume(Double.parseDouble(value));
        } else if (serverFieldId == "37") {

            if (Pattern.compile("^I\\.*NS$").matcher(rawData.getFieldID()).matches()) {
                tradingDayTransaction.setVolume(Double.parseDouble(value));
            }
        }
        // Save as historical data
        nssData.setData(data);
        nssData.setSnapshot(false);
        if (tradingDayTransaction.getClose() != 0.0 &&
                tradingDayTransaction.getOpen() != 0.0 &&
                tradingDayTransaction.getHigh() != 0.0 &&
                tradingDayTransaction.getLow() != 0.0 &&
                tradingDayTransaction.getVolume() != 0.0) {
            // tradingDayTransaction.transNo++;
            if (!nssData.getReady()) {
                nssData.setReady(true);
            }
        }
        return nssData;
    }
}
