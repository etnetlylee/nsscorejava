package com.etnet.coresdk.coreController;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.etnet.coresdk.api.ContextProvider;
import com.etnet.coresdk.constants.SecurityID;
import com.etnet.coresdk.constants.SecurityTradeKey;
import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.model.TradingTimeInfo;
import com.etnet.coresdk.util.AsaDataHelper;
import com.etnet.coresdk.util.SecurityCodeHelper;

public class GroupingController extends ContextProvider {
    public static int MORNING_OPEN = 1000;
    public static int MORNING_CLOSE = 1230;
    public static int CUTOFF = 1400;
    public static int AFTERNOON_OPEN = 1430;
    public static int AFTERNOON_CLOSE = 1600;

    public static int MORNING_OPEN_F = 945;
    public static int MORNING_CLOSE_F = 1230;
    public static int CUTOFF_F = 1400;
    public static int AFTERNOON_OPEN_F = 1430;
    public static int AFTERNOON_CLOSE_F = 1615;

    public static String STOCK_SH_FLAG = "SH.";
    public static String STOCK_SZ_FLAG = "SZ.";
    public static String INDEX_SH_FLAG = "CSI.";

    NssCoreContext _context;

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    /**
     * Classfying SH/SZ, Stock/Index/Future, then passing to baseMinGroup chart type, can be 1,3,5,15,60
     *
     * @param code      stock code
     * @param secType   STOCK_SH/STOCK_SZ/STOCK/FUTURE/INDEX
     * @param chartType 1,3,5,15,30,60,100,101,102,103
     * @param time      His (e.g. 092000, 155959)
     * @return string
     */
    public String groupMin(String code, int secType, int chartType, String time) {
        String tradingKey = getTradingKey(code, secType);
        if (tradingKey != "") {
            // If tradingKey is not null, it means there are amOpen, amClose, pmOpen, pmClose, cuttoff
            return baseMinGroup(
                    tradingKey, secType, chartType, time); // 1154 --> 1150
        } else {
            return time;
        }
    }

    public String getTradingKey(String code, int secType) {
        String tradingKey = "";
        switch (secType) {
            case SecurityID.SECURITYID_STOCK_SH:
                List<String> shStockCodes = Arrays.asList(code.split("."));
                if (shStockCodes.size() == 2) {
                    tradingKey = SecurityTradeKey.TRADEKEY_STOCK_SH +
                            shStockCodes.get(1); // TRADING_TIME_TYPE_FOR_ASHARE_SH_STOCK
                }
                break;
            case SecurityID.SECURITYID_STOCK_SZ:
                List<String> szStockCodes = Arrays.asList(code.split("."));
                if (szStockCodes.size() == 2) {
                    tradingKey = SecurityTradeKey.TRADEKEY_STOCK_SZ +
                            szStockCodes.get(1); // TRADING_TIME_TYPE_FOR_ASHARE_SZ_STOCK
                }
                break;
            case SecurityID.SECURITYID_STOCK:
                tradingKey = SecurityTradeKey.TRADEKEY_STOCK +
                        code; // TRADING_TIME_TYPE_FOR_STOCK
                break;
            case SecurityID.SECURITYID_FUTURE:
                tradingKey = SecurityTradeKey.TRADEKEY_FUTURE +
                        code; // TRADING_TIME_TYPE_FOR_FUTURE
                break;
            case SecurityID.SECURITYID_INDEX:
                tradingKey = SecurityTradeKey.TRADEKEY_INDEX +
                        code; // TRADING_TIME_TYPE_FOR_INDEX
                break;
            default:
                break;
        }

        return tradingKey;
    }

    public String baseMinGroup(String key, int secType, int chartType, String timeString) {
        int groupingTime = 0;
        int time = Integer.parseInt(timeString, 10);
        TradingTimeInfo dateInfo = AsaDataHelper.getTradingTime(key, _context);
        if (dateInfo != null) {
            int nMorningOpen = Integer.parseInt(dateInfo.getAmOpen(), 10);
            int nMorningClose = Integer.parseInt(dateInfo.getAmClose(), 10);
            int nCutoff = Integer.parseInt(dateInfo.getCutOff(), 10);
            int nAfternoonOpen = Integer.parseInt(dateInfo.getPmOpen(), 10);
            int nAfternoonClose = Integer.parseInt(dateInfo.getPmClose(), 10);

            if (nMorningOpen > 0 && time < nMorningOpen) {
                if (secType == SecurityID.SECURITYID_FUTURE) {
                    groupingTime = group4BeforeMorningOpenFuture(
                            nMorningOpen, chartType); // for T+1 group.
                } else {
                    groupingTime = specialTimeGroup(chartType, nMorningOpen, false);
                }
            } else if (nMorningClose > 0 &&
                    nCutoff > 0 &&
                    time >= nMorningClose &&
                    time < nCutoff) {
                groupingTime = specialTimeGroup(chartType, nMorningClose, true);
            } else if (nCutoff > 0 &&
                    nAfternoonOpen > 0 &&
                    time >= nCutoff &&
                    time < nAfternoonOpen) {
                groupingTime = specialTimeGroup(chartType, nAfternoonOpen, false);
            } else if (nAfternoonClose > 0 && time >= nAfternoonClose) {
                groupingTime = afterCloseTimeGroup(chartType, nAfternoonClose);
            } else {
                groupingTime = normalTradingTimeGroup(chartType, time);
            }
            groupingTime = checkGroupTime(groupingTime, nAfternoonClose);
        }
        return formatGroupTime(groupingTime);
    }

    public String formatGroupTime(int groupingTime) {
        String rsTime = String.valueOf(groupingTime); // convert to string
        if (rsTime.length() < 4) {
            // zero padding
            rsTime = "0" + rsTime;
        }
        return rsTime;
    }

    public int checkGroupTime(int groupingTime, int nAfternoonOpen) {
        if (groupingTime % 100 >= 60) {
            groupingTime = ((groupingTime / 100) + 1) * 100;
        }
        if (nAfternoonOpen > 0 && groupingTime > nAfternoonOpen) {
            groupingTime = nAfternoonOpen;
        }
        return groupingTime;
    }

    public int normalTradingTimeGroup(int chartType, int time) {
        int ihr = (int) Math.floor(time / 100);
        int imin = (int) Math.floor(time % 100);
        int tem = (int) Math.floor(imin / chartType + 1);
        switch (chartType) {
            case 1: // 1 minute chart
                return time;
            default:
                imin = (tem * chartType);
                if (imin >= 60) {
                    ihr++;
                    imin = 0;
                }
                return (int) Math.floor(ihr * 100 + imin);
        }
    }

    public int afterCloseTimeGroup(int chartType, int time) {
        switch (chartType) {
            case 1: // 1 minute chart
                return formateDate(time);
            default:
                return time;
        }
    }

    public int specialTimeGroup(int chartType, int time, boolean isForamtTime) {
        switch (chartType) {
            case 1: // 1 minute chart
                return time;
            default:
                if (isForamtTime) {
                    time = formateDate(time);
                }
                return groupTime(time, chartType);
        }
    }

    public int groupTime(int time, int chartType) {
        int tempHour = (int) Math.floor(time / 100);
        int tempMin = (int) Math.floor(time % 100);
        int tem = (int) Math.floor(tempMin / chartType + 1);
        int imin = (int) Math.floor(tem * chartType);
        return tempHour * 100 + imin;
    }

    public int formateDate(int date) {
        int closeHr = (int) Math.floor(date / 100);
        int closeMin = (int) Math.floor(date % 100 - 1);
        if (closeMin < 0) {
            closeHr--;
            closeMin = 59;
        }
        return closeHr * 100 + closeMin;
    }

    //  for T and T+1 future group.
    public int group4BeforeMorningOpenFuture(int nMorningOpen, int chartType) {
        int groupingTime = 0;

        double openHour = nMorningOpen / 100;
        int openMin = nMorningOpen % 100;

        if (chartType == 1) {
            // Handle the unusual case
            groupingTime = nMorningOpen;
            return groupingTime;
        }

        // Step 1: Normal case.
        double groupMin = openMin + chartType;

        // Step 2: Adjust Hour Unit, if necessary
        double groupHour = openHour + groupMin / 60;
        // Step 2: Adjust Minuter Unit, if necessary
        groupMin = groupMin % 60;

        // step 3: adjust Unit position. let the group time at the correct Chart Type Unit position
        groupMin = (groupMin / chartType) * chartType;

        // Step 4: combine the Hour Unit and Minuter Unit
        groupingTime = (int) (groupHour * 100 + groupMin);

        return groupingTime;
    }

    public int getSecurityType(String code) {
        int secType = SecurityID.SECURITYID_STOCK;
        if (SecurityCodeHelper.isSHCode(code)) {
            secType = SecurityID.SECURITYID_STOCK_SH;
        } else if (SecurityCodeHelper.isSZCode(code)) {
            secType = SecurityID.SECURITYID_STOCK_SH;
        } else {
            int nPos = code.indexOf(".");
            if (nPos >= 0) {
                String iCode = code.substring(0, nPos);
                String month = code.substring(nPos + 1);
                List<String> iList = AsaDataHelper.getFutureMonth(iCode, _context);
                if (AsaDataHelper.isFutureT1Code(code, _context)) {
                    secType = SecurityID.SECURITYID_FUTURE;
                } else if (iList != null && iList.contains(month)) {
                    secType = SecurityID.SECURITYID_FUTURE;
                } else {
                    secType = SecurityID.SECURITYID_INDEX;
                }
            }
        }
        return secType;
    }

    public Integer groupDaily(String code, String minType, int currTimestamp) {
        if (!(Arrays.asList("100","101", "102", "103", "104", "105").contains(minType))){
            return null;
        }
        Calendar current = Calendar.getInstance();
        current.setTimeInMillis(currTimestamp);

        int mYear = current.get(Calendar.YEAR);
        int mMonth = current.get(Calendar.MONTH);
        int mDay = current.get(Calendar.DAY_OF_MONTH);

        current.set(Calendar.YEAR, mYear);
        current.set(Calendar.MONTH, mMonth);
        current.set(Calendar.DAY_OF_MONTH, mDay);

        return (int) current.getTimeInMillis();
    }

    public boolean isAfternoonClose(
            String code, int securityId, int chartType, int timestamp) {
        String tradingKey = getTradingKey(code, securityId);
        boolean isClosed = false;
        if (tradingKey != "") {
            Calendar t = Calendar.getInstance();
            t.setTimeInMillis(timestamp);
            Date date = t.getTime();
            SimpleDateFormat format1 = new SimpleDateFormat("HHmm");
            String formattedDate = format1.format(date);
            int time = Integer.parseInt(formattedDate, 10);
            TradingTimeInfo dateInfo =
                    AsaDataHelper.getTradingTime(tradingKey, _context);
            if (dateInfo != null) {
                int nAfternoonOpen = Integer.parseInt(dateInfo.getPmClose(), 10);
                if (nAfternoonOpen > 0 && time >= nAfternoonOpen) {
                    isClosed = true;
                }
            }
        }
        return isClosed;
    }
}
