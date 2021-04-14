package constants;

public class AsaConstant {
    public static final String ASA_FIELD_US = "458";
    public static final String ASA_FIELD_HK = "108";
    public static final String ASA_FIELD = ASA_FIELD_HK;

    public static final String SCREENLIST_OPTIONQUOTE = "OptionsQuote";

    public static final String SCREENLIST_FUTURESQUOTE = "FuturesQuote";
    public static final String SCREENLIST_FUTURESPRICEDEPTH = "FuturesPriceDepth";
    public static final String SCREENLIST_OPTIONSQUOTE = "OptionsQuote";
    public static final String SCREENLIST_DUALFUTURESQUOTE = "DualFuturesQuote";
    public static final String SCREENLIST_SECTORINDEXLIST = "SectorIndexList";
    public static final String SCREENLIST_SHHKSECTORINDEXLIST = "ShHkSectorIndexList";

    public static final String SCREENLIST_SECTORSHINDEXLIST =
            "SectorSHIndexList"; // Added in V7.6
    public static final String SCREENLIST_SECTORSZINDEXLIST =
            "SectorSZIndexList"; // Added in V7.6

    public static final String SCREENLIST_INDEXBAR4FUTURE = "IndexBar4Future";
    public static final String SCREENLIST_INDEXBAR4LOCALINDEX = "IndexBar4LocalIndex";
    public static final String SCREENLIST_INDEXBAR4GLOBALINDEX = "IndexBar4LocalIndex";
    public static final String SCREENLIST_INDEXBAR4GLOBALBARLIST = "IndexBar4GlobalBar";

    public static final String INDEXFOREXLIST_NAME = "ForexList";
    public static final String COMMODITYLIST_NAME = "CommodityList";
    public static final String INDEXLOCALLIST_NAME = "LocalIndex";
    public static final String INDEXCHINALIST_NAME = "ChinaIndex";
    public static final String INDEXGLOBALLIST_NAME = "GlobalIndexNoCn";
    public static final String INDEXGLOBALLIST_NAME_MIQ = "GlobalIndex";
    public static final String CHARTCODE_NAME = "Chart";

    public static final int SEC_TYPE_NONE = -1;
    public static final int SEC_TYPE_STOCK = 0;
    public static final int SEC_TYPE_WARR = 1;
    public static final int SEC_TYPE_CBBC = 2;
    public static final int SEC_TYPE_PUT = 3;
    public static final int SEC_TYPE_CALL = 4;
    public static final int SEC_TYPE_BULL = 5;
    public static final int SEC_TYPE_BEAR = 6;
    public static final int SEC_TYPE_ETF = 7;
    public static final int SEC_TYPE_ADR = 8;
    public static final int SEC_TYPE_BULL_N = 9;
    public static final int SEC_TYPE_BULL_R = 10;
    public static final int SEC_TYPE_BEAR_N = 11;
    public static final int SEC_TYPE_BEAR_R = 12;
    public static final int SEC_ASHARE = 13;

    public static final int AH_TYPE_NONE = -1;
    public static final int AH_TYPE_SZ = 0;
    public static final int AH_TYPE_SH = 1;

    public static final String STOCK_EXCHANGE_SSE = "SSE";
    public static final String STOCK_EXCHANGE_SZSE = "SZSE";
    public static final String STOCK_EXCHANGE_HKEX = "HKEX";
    public static final String STOCK_EXCHANGE_HKEXSZ = "HKEXSZ";

    public static final String LANG_TC = "TC";
    public static final String LANG_SC = "SC";
    public static final String LANG_EN = "EN";

    public static final String TRADING_TIME_TYPE_FOR_STOCK = "MDF";
    public static final String TRADING_TIME_TYPE_FOR_FUTURE = "PRS";
    public static final String TRADING_TIME_TYPE_FOR_INDEX = "HSIL";
    public static final String DEFAULT_TRADING_TIME = "DEFAULT";

    public static final String STOCK_CONNECTED_CURRENT = "C";
    public static final String STOCK_CONNECTED_EX = "E";

    public static final String SEARCHLIST_FUTURE_QUOTE = "FUTURE_QUOTE";
    public static final String SEARCHLIST_OPTION_QUOTE = "OPTION_QUOTE";
    public static final String SEARCHLIST_CHART = "CHART";
    public static final String SEARCHLIST_INDEXBAR_FUTURE = "INDEXBAR_FUTURE";
    public static final String SEARCHLIST_INDEXBAR_HK = "INDEXBAR_HK";
    public static final String SEARCHLIST_INDEXBAR_CN = "INDEXBAR_CN";
    public static final String SEARCHLIST_INDEXBAR_GLOBAL = "INDEXBAR_GLOBAL";
    public static final String SEARCHLIST_LOCALINDEX_PRIMARY = "LOCALINDEX_PRIMARY";
    public static final String SEARCHLIST_LOCALINDEX_INDUSTRY = "LOCALINDEX_INDUSTRY";
    public static final String SEARCHLIST_LOCALINDEX_COMPOSITE = "LOCALINDEX_COMPOSITE";
    public static final String SEARCHLIST_LOCALINDEX_CHINAMARKET = "LOCALINDEX_CHINAMARKET";
    public static final String SEARCHLIST_LOCALINDEX_HKEXSP = "LOCALINDEX_HKEXSP";
    public static final String SEARCHLIST_CHINAINDEX_PRIMARY = "CHINAINDEX_PRIMARY";
    public static final String SEARCHLIST_CHINAINDEX_SH = "CHINAINDEX_SH";
    public static final String SEARCHLIST_CHINAINDEX_SZ = "CHINAINDEX_SZ";
    public static final String SEARCHLIST_GLOBALINDEX_PRIMARY = "GLOBALINDEX_PRIMARY";
    public static final String SEARCHLIST_GLOBALINDEX_APAC = "GLOBALINDEX_APAC";
    public static final String SEARCHLIST_GLOBALINDEX_EURAMR = "GLOBALINDEX_EURAMR";

    public static final String DISCLAIMER_ETNET = "ETNET_Disclaimer";
    public static final String DISCLAIMER_ICE = "ICE_Disclaimer";

    public static final String RESOURCE_WAR_NEWLISTING = "1";
    public static final String RESOURCE_CBBC_NEWLISTING = "2";
    public static final String RESOURCE_CBBC_EXPIRING = "3";
    public static final String RESOURCE_AHMAPPING = "4";
    public static final String RESOURCE_BROKER = "5";
    public static final String RESOURCE_STOCK_LINK_CODE = "6";
    public static final String RESOURCE_SPREAD_TABLE = "7";
    public static final String RESOURCE_SECURITY_RELATED = "8";
    public static final String RESOURCE_ADRLIST = "9";
    public static final String RESOURCE_STOCK_NAME = "10";
    public static final String RESOURCE_INDUSTRY = "11";
    public static final String RESOURCE_BLOCK_TRADE = "12";
    public static final String RESOURCE_FUTURE_CODE_LIST = "13";
    public static final String RESOURCE_FUTURE_RELATED_LIST = "14";
    public static final String RESOURCE_OPTION_CODE_LIST = "15";
    public static final String RESOURCE_OPTION_RELATED_LIST = "16";
    public static final String RESOURCE_TRADING_DAY = "17";
    public static final String RESOURCE_TRADING_TIME = "18";
    public static final String RESOURCE_ETF = "19";
    public static final String RESOURCE_LOCAL_INDEX_CODE_LIST = "21";
    public static final String RESOURCE_GLOBAL_INDEX_CODE_LIST = "22";
    public static final String RESOURCE_FOREX_CODE_LIST = "23";
    public static final String RESOURCE_TIPS_REC_NAME = "25";
    public static final String SINGLE_RESOURCE_CEI_BARLIST = "26";
    public static final String SINGLE_RESOURCE_FHSI_BARLIST = "27";
    public static final String SINGLE_RESOURCE_GLOBAL_BARLIST = "28";
    public static final String RESOURCE_ISSUE = "29";
    public static final String RESOURCE_INDEX_INFO = "30";
    public static final String RESOURCE_URL_INFO = "31";
    public static final String RESOURCE_RISK_FREE = "RFR";
    public static final String RESOURCE_SCREEN_INDEX_LIST = "36";
    public static final String SINGLE_RESOURCE_FUTURE_CODE_LIST_ASA = "37";
    public static final String SINGLE_RESOURCE_OPTION_CODE_LIST_ASA = "38";
    public static final String SINGLE_RESOURCE_AHFT_MAPPING = "39";
    public static final String SINGLE_RESOURCE_DTDC_MAPPING = "40";
    public static final String RESOURCE_FUTURE_RELATED_LIST_FROM_ASA = "41";
    public static final String RESOURCE_OPTION_RELATED_LIST_FROM_ASA = "42";
    public static final String RESOURCE_SH_CODE_LIST = "60";
    public static final String RESOURCE_SZ_CODE_LIST = "70";
    public static final String RESOURCE_SH_STATE_LIST = "61";
    public static final String RESOURCE_SZ_STATE_LIST = "71";
    public static final String RESOURCE_SH_INDEX = "62";
    public static final String RESOURCE_SH_INDUSTRY = "63";
    public static final String RESOURCE_SZ_INDUSTRY = "73";
    public static final String RESOURCE_SH_TRADINGDAY = "64";
    public static final String RESOURCE_ASHARE_SH_TRADINGDAY_PLUS = "65";
    public static final String RESOURCE_ASHARE_SZ_TRADINGDAY_PLUS = "75";
    public static final String RESOURCE_SHORTSELLDATE = "24";
    public static final String RESOURCE_VCM_CAS_LIST = "83";
    public static final String RESOURCE_HSHARE_COLUMNIST_WITH_LANG = "85";
    public static final String FIRM_NAME = "87";
    public static final String RESOURCE_TRADING_DAY_20 = "88";

    public static final String RESOURCE_US_TRADING_DATE = "87";
    public static final String RESOURCE_US_CUSIP_ISIN = "88";
    public static final String RESOURCE_US_INDEX_LIST = "89";
    public static final String RESOURCE_US_INDUSTRY_CODE_LIST = "90";
    public static final String RESOURCE_INSTRUMENT_NAME = "instrument";
    public static final String RESOURCE_SEARCH_LIST = "searchlist";
    public static final String RESOURCE_DISCLAIMERS = "disclaimers";

    public static final String DATA_STREAMING = "streaming";
    public static final String DATA_SNAPSHOT = "snapshot";
    public static final String DATA_HTTP = "http_data";
}
