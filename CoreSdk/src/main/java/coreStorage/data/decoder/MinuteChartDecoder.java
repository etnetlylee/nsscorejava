package coreStorage.data.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.Transaction;
import util.AsaDataHelper;
import util.SecurityCodeHelper;

public class MinuteChartDecoder extends Decoder {
    public static final String uniqueID = "minutechart";
    final Logger log = Logger.getLogger("MinuteChartDecoder");

    @Override
    public NssData decodeSnapshot(String code, RawData rawData) {
        final List<String> value = (List<String>) rawData.getData();
        NssData nssData = new NssData(null);
        List<Transaction> transactionList = (List<Transaction>) nssData.getData();
        if (transactionList == null) {
            transactionList = new ArrayList<Transaction>();
        }
        final String codeType = SecurityCodeHelper.getSecurityMarketType(code);
        final int tradingDay =
                AsaDataHelper.getMarketTradingDay(codeType, getContext());
        if (tradingDay == 0) {
            log.info("invalid trading day");
        } else {
            // Format
            // Date, Open, High, Low, CLose, Volume
            // 1470381840000,8.760,8.760,8.750,8.750,5000"
            for (String val : value) {
                final List<String> structInfo = Arrays.asList(val.split(","));
                if (structInfo.size() >= 5) {
                    int timestamp = Math.abs(Integer.parseInt(structInfo.get(0)));
                    // Data are sort by date ASC
                    transactionList.add(new Transaction(
                            0, 0,
                            timestamp,
                            Double.parseDouble(structInfo.get(1)),
                            Double.parseDouble(structInfo.get(2)),
                            Double.parseDouble(structInfo.get(3)),
                            Double.parseDouble(structInfo.get(4)),
                            Double.parseDouble(structInfo.get(4)),
                            Double.parseDouble(structInfo.get(5)),
                            Double.parseDouble(structInfo.get(5)),
                            null,
                            -1
                    ));
                }
            }
        }

        Collections.reverse(transactionList);
        List<Transaction> unshiftList = new ArrayList<Transaction> (transactionList);
        log.info("snap receive and decode: ${transactionList}");
        nssData.setData(unshiftList);
        nssData.setReady(true);
        nssData.setFieldID(rawData.getFieldID() + "H");
        nssData.setSnapshot(true);
        return nssData;
    }

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = rawData.getData().toString();
        // fieldID -> example value
        // 161 -> 371|200|Y|A|15:59:34|14809
        // 82  -> 371|200|Y|A|15:59|14805
        // 159 -> 371|200|Y|A|15:59|14805
        NssData nssData = new NssData(null);
        List<Transaction> transactionList = (List<Transaction>) nssData.getData();
        if (transactionList == null) {
            transactionList = new ArrayList<Transaction>();
        }
        String securityMarket = SecurityCodeHelper.getSecurityMarketType(code);
        int tradingDay =
                AsaDataHelper.getMarketTradingDay(securityMarket, getContext());
        if (tradingDay == 0) {
            log.info("invalid trading day");
        } else if (value.trim().length() > 0) {
            final List<String> rows = Arrays.asList(value.split("#"));
            for (String row : rows){
                final List<String> val = Arrays.asList(row.split("|"));
                double nominal = Double.parseDouble(val.get(0));
                final List<String> time = Arrays.asList(val.get(4).split(":"));
                int timeInMs = (Integer.parseInt(time.get(0), 10) *3600 +
                        Integer.parseInt(time.get(1), 10) *60) *
                1000;
                // U and " " are automatch transactions
                if (val.get(2) != null && val.get(2) != "") {
                    val.set(2, val.get(2));
                } else {
                    val.set(2, " ");
                }
                // [2] remarks
                // U   =
                // " " =
                // ""  = Index
                // 0   = Future
                if (val.get(2) == "U" || val.get(2) == " " || val.get(2) == "" || val.get(2) == "0") {
                    if (val.get(4) != null && val.get(4) != "") {
                        transactionList.add(new Transaction(
                                0,0,
                                tradingDay + timeInMs,
                                nominal,
                                nominal,
                                nominal,
                                nominal,
                                nominal,
                                Double.parseDouble(val.get(1)),
                                0.0,
                                val.get(2),
                                Integer.parseInt(val.get(5), 10)
            ));
                    }
                }
            }

        }
        nssData.setData(transactionList);
        nssData.setReady(true);
        nssData.setSnapshot(false);
        return nssData;
    }
}