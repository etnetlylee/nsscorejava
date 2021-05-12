package com.etnet.coresdk.util;

import java.util.ArrayList;
import java.util.Collections;
//import java.util.Comparator;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.etnet.coresdk.constants.Lang;
import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.coreStorage.model.AsaStorage;
import com.etnet.coresdk.model.BrokerFirm;
import com.etnet.coresdk.model.IndexBaseInfo;
import com.etnet.coresdk.model.StockInfo;
import com.etnet.coresdk.model.TradingTimeInfo;

import static com.etnet.coresdk.constants.AsaConstant.LANG_EN;
import static com.etnet.coresdk.constants.AsaConstant.LANG_SC;
import static com.etnet.coresdk.constants.AsaConstant.LANG_TC;
import static com.etnet.coresdk.constants.AsaConstant.SCREENLIST_OPTIONQUOTE;
import static com.etnet.coresdk.constants.SecurityID.SECURITYID_STOCK;
import static com.etnet.coresdk.constants.SecurityID.SECURITYID_STOCK_SH;
import static com.etnet.coresdk.constants.SecurityID.SECURITYID_STOCK_SZ;
import static com.etnet.coresdk.constants.SecurityMarket.AH;
import static com.etnet.coresdk.constants.SecurityMarket.SH;
import static com.etnet.coresdk.constants.SecurityMarket.SZ;
import static com.etnet.coresdk.constants.SecurityType.SECURITYTYPE_STOCK;
import static com.etnet.coresdk.constants.SecurityType.SECURITYTYPE_STOCK_SH;
import static com.etnet.coresdk.constants.SecurityType.SECURITYTYPE_STOCK_SZ;

public class AsaDataHelper {
    /**
     * Return trading day of a market
     *
     * @param securityMarket HK,SH,SZ,AH
     * @param context        context of nss core
     */
    public static int getMarketTradingDay(
            String securityMarket, NssCoreContext context) {
        List<Integer> tradingDayList =
                AsaDataHelper.getMarketTradingDayList(securityMarket, context);
        int tradingDay = 0;
        if (tradingDayList != null && tradingDayList.size() >= 6) {
            tradingDay = tradingDayList.get(tradingDayList.size() - 6);
        }
        return tradingDay;
    }

    public static List<Integer> getMarketTradingDayList(
            String securityMarket, NssCoreContext context) {
        List<Integer> TRADING_DAY_LIST = new ArrayList<Integer>();
        AsaStorage asaStorage = context.getAsaStorage();
        if (securityMarket == SH) {
            TRADING_DAY_LIST = asaStorage.ASHARE_SH_TRADINGDAY_LIST;
        } else if (securityMarket == SZ) {
            TRADING_DAY_LIST = asaStorage.ASHARE_SZ_TRADINGDAY_LIST;
        } else if (securityMarket == AH) {
            TRADING_DAY_LIST = asaStorage.TRADING_DAY_LIST;
        } else {
            TRADING_DAY_LIST = asaStorage.TRADING_DAY_LIST;
        }
        return TRADING_DAY_LIST;
    }

    public static void futureOptionListHandler(
            AsaStorage data,
            Map<String, List<String>> monthListMap,
            String screenID,
            boolean isOption,
            NssCoreContext context) {
        List<String> futureOptionCodelist = new ArrayList<String>();
        List<String> monthListKeys = new ArrayList<String>(monthListMap.keySet());
        futureOptionCodelist.addAll(monthListKeys);
        Collections.sort(futureOptionCodelist);
        List<IndexBaseInfo> existingList = data.INDEX_BASE_INFO_MAP.get(screenID);
        List<IndexBaseInfo> indexInfoList = new ArrayList<IndexBaseInfo>();

        for (String futureOptionCode : futureOptionCodelist) {
            String underlyingCode = null;
            if (isOption) {
                underlyingCode =
                        AsaDataHelper.getOptionUnderlying(futureOptionCode, context);
            } else {
                underlyingCode =
                        AsaDataHelper.getFutureUnderlying(futureOptionCode, context);
            }

            if (underlyingCode != null && Pattern.compile("[0-9]*").matcher(underlyingCode).matches()) {
                String enName = AsaDataHelper.makeFutureOptionName(
                        futureOptionCode, underlyingCode, LANG_EN, screenID, context);
                String scName = AsaDataHelper.makeFutureOptionName(
                        futureOptionCode, underlyingCode, LANG_SC, screenID, context);
                String tcName = AsaDataHelper.makeFutureOptionName(
                        futureOptionCode, underlyingCode, LANG_TC, screenID, context);
                int stockCode = Integer.parseInt(underlyingCode, 10);

                final String posInfo = "1";
                final String levleName = "1";

                IndexBaseInfo indexInfo = new IndexBaseInfo();
                indexInfo.setLevel(levleName);
                indexInfo.setPosition(posInfo);
                indexInfo.setCode(futureOptionCode);
                indexInfo.setFullTcName(tcName);
                indexInfo.setFullScName(scName);
                indexInfo.setFullEnName(enName);
                indexInfo.setTcName(
                        AsaDataHelper.getStockName(underlyingCode, LANG_TC, context, null));
                indexInfo.setScName(
                        AsaDataHelper.getStockName(underlyingCode, LANG_SC, context, null));
                indexInfo.setEnName(
                        AsaDataHelper.getStockName(underlyingCode, LANG_EN, context, null));
                indexInfo.setUnderlyingStockCode(stockCode);

                if (existingList == null || !(existingList.indexOf(indexInfo) > -1)) {
                    indexInfoList.add(indexInfo);
                }
            } else {
            }
        }

        Collections.sort(indexInfoList, new IndexBaseInfoComparator());

        if (existingList != null) {
            existingList.addAll(indexInfoList);
        } else {
            existingList = indexInfoList;
        }

        data.INDEX_BASE_INFO_MAP.put(screenID, existingList);
    }

    /**
     * REturn name of future / option
     *
     * @param stockCode      stock code
     * @param underlyingCode underlying code
     * @param language       system language keys (tc, sc, en)
     * @param screenID       name of screen
     * @param context        context of nss core
     */
    public static String makeFutureOptionName(String stockCode, String underlyingCode,
                                              String language, String screenID, NssCoreContext context) {
        String name = AsaDataHelper.getStockName(underlyingCode, language, context, null);
        final String SEPARTOR = "-";

        String grammarWordLink = "";
        if (language == Lang.EN2) {
            grammarWordLink =
                    " "; // There is a space between English words; this is a common grammar
        }

        String asaLangKey = AsaDataHelper.getAsaLanguageKey(language);
        String futureWord = context
                .getController()
                .getCultureController()
                .getWord("ASA_FUTURE_NAME", asaLangKey);
        if (screenID == SCREENLIST_OPTIONQUOTE) {
            futureWord = context
                    .getController()
                    .getCultureController()
                    .getWord("ASA_OPTION_NAME", asaLangKey);
        }

        String stockFutureNameInScreen =
                SecurityCodeHelper.padStockCode(underlyingCode) +
                        " " +
                        stockCode +
                        " " +
                        SEPARTOR +
                        " " +
                        name;
        stockFutureNameInScreen += grammarWordLink + futureWord;

        return stockFutureNameInScreen;
    }

    /**
     * Return name of stock code
     *
     * @param stockCode    stock code
     * @param language     system language key (tc, sc, en)
     * @param context      context of nss core
     * @param securityType security type
     */
    public static String getStockName(String stockCode, String language, NssCoreContext context,
                                      String securityType) {
        StockInfo info =
                AsaDataHelper.getStockInfo(stockCode, context, securityType);
        String _stockName = "";
        if (info != null) {
            switch (language) {
                case Lang.EN2:
                    _stockName = info.getEnName();
                case Lang.SC2:
                    _stockName = info.getScName() != null ? info.getScName() : info.getEnName();
                case Lang.TC2:
                default:
                    _stockName = info.getTcName() != null ? info.getTcName() : info.getEnName();
            }
        }
        return _stockName;
    }

    /**
     * Return stock info of code
     *
     * @param stockCode    stock code
     * @param context      context of nss core
     * @param securityType specified security type
     */
    public static StockInfo getStockInfo(String stockCode, NssCoreContext context,
                                         String securityType) {
        if (securityType != null) {
            switch (securityType) {
                case SECURITYTYPE_STOCK_SH:
                    return context.getAsaStorage().SH_SHARE_STOCK_INFO_MAP.get(stockCode);
                case SECURITYTYPE_STOCK_SZ:
                    return context.getAsaStorage().SZ_SHARE_STOCK_INFO_MAP.get(stockCode);
                case SECURITYTYPE_STOCK:
                default:
                    final int securityID = SecurityCodeHelper.getSecurityID(stockCode);
                    switch (securityID) {
                        case SECURITYID_STOCK_SH:
                            return context.getAsaStorage().SH_SHARE_STOCK_INFO_MAP.get(stockCode);
                        case SECURITYID_STOCK_SZ:
                            return context.getAsaStorage().SZ_SHARE_STOCK_INFO_MAP.get(stockCode);
                        case SECURITYID_STOCK:
                        default:
                            return context.getAsaStorage().STOCK_INFO_MAP.get(stockCode);
                    }
            }
        } else {
            StockInfo fromSHMap =
                    context.getAsaStorage().SH_SHARE_STOCK_INFO_MAP.get(stockCode);
            StockInfo fromSZMap =
                    context.getAsaStorage().SZ_SHARE_STOCK_INFO_MAP.get(stockCode);
            StockInfo fromHKMap = context.getAsaStorage().STOCK_INFO_MAP.get(stockCode);

            if (fromSHMap != null) {
                return fromSHMap;
            } else if (fromSZMap != null) {
                return fromSZMap;
            } else {
                return fromHKMap;
            }
        }
    }

    /**
     * Get future underling 14
     *
     * @param futCode future code
     * @param context context of nss core
     */
    public static String getFutureUnderlying(String futCode, NssCoreContext context) {
        String t1Code = "";
        if (AsaDataHelper.isAHFTCode(futCode, context)) {
            t1Code = AsaDataHelper.getT1FutCodeByFutCode(futCode, context);
        }
        return context.getAsaStorage().FUTURE_UNDERLYING_MAP_FROM_ASA.get(t1Code);
    }

    /**
     * Convert system language keys (tc, sc, en) to asa language keys (TC, SC, EN)
     */
    static String getAsaLanguageKey(String lang) {
        String langKey = "";
        switch (lang) {
            case Lang.TC2:
                langKey = LANG_TC;
                break;
            case Lang.SC2:
                langKey = LANG_SC;
                break;
            case Lang.EN2:
                langKey = LANG_EN;
                break;
            default:
                break;
        }
        return langKey;
    }

    static String getOptionUnderlying(String index, NssCoreContext context) {
        return context.getAsaStorage().OPTION_UNDERLYING_MAP_FROM_ASA.get(index);
    }

    /**
     * Return whether futCode has T+1 code
     *
     * @param futCode future code
     * @param context context of nss core
     */
    static boolean isAHFTCode(String futCode, NssCoreContext context) {
        AsaStorage asaStorage = context.getAsaStorage();
        if (asaStorage.AHFT_VALUE_MAP != null) {
            if (asaStorage.AHFT_VALUE_MAP.get(futCode) != null && !asaStorage.AHFT_VALUE_MAP.get(futCode).isEmpty()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Return T+1 code of futCode
     *
     * @param futCode future code
     * @param context context of nss core
     */
    static String getT1FutCodeByFutCode(String futCode, NssCoreContext context) {
        return context.getAsaStorage().AHFT_VALUE_MAP.get(futCode);
    }

    public static List<String> getFutureMonth(String code, NssCoreContext context) {
        return context.getAsaStorage().FUTURE_MONTH_MAP_FROM_ASA.get(code);
    }

    static List<String> getOptionsMonth(String option, NssCoreContext context) {
        return context.getAsaStorage().OPTION_MONTH_MAP_FROM_ASA.get(option);
    }

    public static boolean isFutureT1Code(String futCode, NssCoreContext context) {
        boolean result = false;
        Map<String, String> ahftMap = context.getAsaStorage().AHFT_VALUE_MAP;


        for (Map.Entry<String, String> entry : ahftMap.entrySet()) {

            if (futCode != null && futCode.length() == 5) {
                if (futCode.substring(1, 4) == entry.getValue()) {
                    result = true;
                }
            } else {
                if (futCode.contains(entry.getValue())) {
                    result = true;
                }
            }
        }
        return result;
    }

    public static TradingTimeInfo getTradingTime(String key, NssCoreContext context) {
        Map<String, Map<String, TradingTimeInfo>> TRADING_TIME_MAP =
                context.getAsaStorage().TRADING_TIME_MAP;
        String type = key.split(".")[0];
        String code = key.split(".")[1];
        if (TRADING_TIME_MAP != null && !TRADING_TIME_MAP.isEmpty()) {
            TradingTimeInfo dateInfo = TRADING_TIME_MAP.get(type).get(code);
            if (dateInfo == null) {
                dateInfo = TRADING_TIME_MAP.get(type).get("DEFAULT");
            }
            return dateInfo;
        }
        return null;
    }

    public static List<String> getBrokersInSameFirm(
            String brokerCode, NssCoreContext context) {
        final BrokerFirm broker = context.getAsaStorage().UNGBROSCHMAP.get(brokerCode);
        if (broker != null) {
            return new ArrayList<String>();
        }
        return broker.getBrokersNo();
    }

    public static List<String> getBrokerCodesByFirm(
            String firmCode, NssCoreContext context) {
        final BrokerFirm brokerGroup = context.getAsaStorage().GRUBROSCHMAP.get(firmCode);
        if (brokerGroup != null) {
            return new ArrayList<String>();
        }
        return brokerGroup.getBrokersNo();
    }

    public static List<BrokerFirm> getBrokerFirmList(NssCoreContext context) {
        return context.getAsaStorage().GROBROKER;
    }

    public static String getBrokerFirmCode(String brokerCode, NssCoreContext context) {
        String firmCode = "";

        for (Map<String, String> broker : context.getAsaStorage().UNGBROKER) {
            if (broker.get("brokerCode") == brokerCode) {
                firmCode = broker.get("firmCode");
            }
        }
        return firmCode;
    }

    /**
     * Retirm name of broker firm
     *
     * @param firmCode code of broker firm
     * @param lang     system language key (tc, sc, en)
     * @param context  context of nss core
     */
    public static String getBrokerFirmName(
            String firmCode, String lang, NssCoreContext context) {
        final BrokerFirm brokerGroup =context.getAsaStorage().GRUBROSCHMAP.get(firmCode);
        String firmName = "";
        if (brokerGroup != null) {
            switch (lang) {
                case Lang.EN2:
                    return brokerGroup.getEngName();
                case Lang.SC2:
                    if (brokerGroup.getScName() != null) {
                        firmName = brokerGroup.getScName();
                    } else if (brokerGroup.getEngName() != null) {
                        firmName = brokerGroup.getEngName();
                    }
                    break;
                case Lang.TC2:
                    if (brokerGroup.getTcName() != null) {
                        firmName = brokerGroup.getTcName();
                    } else if (brokerGroup.getEngName() != null) {
                        firmName = brokerGroup.getEngName();
                    }
                    break;
            }
        }
        return firmName;
    }

    public static String getBrokerFirmRegion(String firmCode, NssCoreContext context) {
        final BrokerFirm brokerGroup =context.getAsaStorage().GRUBROSCHMAP.get(firmCode);
        return brokerGroup.getRegion();
    }
}

class IndexBaseInfoComparator implements Comparator<IndexBaseInfo> {
    public int compare(IndexBaseInfo a, IndexBaseInfo b) {
        if (a != null && b != null){
            if (Integer.parseInt(a.getCode()) > Integer.parseInt(b.getCode())) {
                return 1;
            } else if (Integer.parseInt(a.getCode()) < Integer.parseInt(b.getCode())) {
                return -1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }
}