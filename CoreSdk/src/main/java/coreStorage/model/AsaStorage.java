package coreStorage.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.BrokerFirm;
import model.Disclaimer;
import model.FirmName;
import model.IEInfoStruct;
import model.IndexBaseInfo;
import model.Industry;
import model.IndustryBaseInfo;
import model.IndustryInfo;
import model.InstrumentInfo;
import model.SearchListItem;
import model.StockInfo;
import model.TradingTimeInfo;

import static java.util.Arrays.asList;

public class AsaStorage {
    // Decoder0
    List<String> WARR_NEW_LIST = new ArrayList();
    // Decoder1
    List<String> CBBC_NEW_LIST = new ArrayList();
    // Decoder2
    List<String> WARR_EXP_LIST = new ArrayList();
    // Decoder3
    public List<String> CBBC_EXP_LIST = new ArrayList();
    // Decoder4
    public Map<String, String> AHMAP = new HashMap<String, String>(); // hkex code => ashare code (1398 -> A.601398)
    public Map<String, Integer> SZORSHMAP = new HashMap<String, Integer>(); // ashare code (A.601398) => 0=SZ 1=SH
    // Decoder5
    public List<Object> GROBROKER = new ArrayList<Object>();
    public List<Map<String, String>> UNGBROKER = new ArrayList<Map<String, String>>();
    public Map<String, String> CODEFIRM_MAP = new HashMap<String, String>(); // brokerCode => firmCode
    public Map<String, BrokerFirm> UNGBROSCHMAP = new HashMap<String, BrokerFirm>(); // brokerCode => broker detail
    public Map<String, List<String>> FIRMMAP = new HashMap<String, List<String>>();
    public Map<String, BrokerFirm> GRUBROSCHMAP = new HashMap<String, BrokerFirm>();
    // Decoder6
    public Map<String, List<String>> RELATE_STOCKMAP = new HashMap<String, List<String>>(); // code => array of underlying code
    // Decoder7
    public Map<String, List<String>> SPREAD_KEY_MAP = new HashMap<String, List<String>>(); // code => list
    public Map<String, Map<String, String>> SPREAD_MAP = new HashMap<String, Map<String, String>>(); // spreadType => map
    // Decoder8
    public List<String> BOND_LIST = new ArrayList<String>(); // code
    public List<String> REIT_LIST = new ArrayList<String>(); // code
    public Map<String, StockInfo> STOCK_INFO_MAP = new HashMap<String, StockInfo>(); // code => StockInfo
    public Map<String, Map<String, List<String>>> UNDERLYINGMAP = new HashMap<String, Map<String, List<String>>>(); // code
    // => {relatedType: array}
    public List<String> SPECIAL_UNDERLYING_LIST = new ArrayList<String>();
    public List<String> VALIDCODE = new ArrayList<String>();
    public Map<String, String> UNDERLYINGCODE = new HashMap<String, String>(); // type => underlying code
    public Map<String, String> STKTYPEMAP = new HashMap<String, String>(); // code => stock type
    public Map<String, String> UNDERLYINGDETIAL = new HashMap<String, String>(); // underlying code => detail description of
    // that underlying type
    Map<String, String> CBBC_RN_MAP = new HashMap<String, String>(); // underlying code => bull bear type
    List<String> ADR_LIST = new ArrayList<String>(); // ADR code list
    // TRADING_TIME_MAP: {} = {};
    // TRADING_DAY_LIST: any[] = [];
    // AHFT_VALUE_MAP: {} = {};
    Map<String, List<String>> FUTURE_MONTH_MAP_FROM_ASA = new HashMap<String, List<String>>(); // futureCode => 
    // monthlist
    Map<String, String> FUTURE_UNDERLYING_MAP_FROM_ASA = new HashMap<String, String>(); // code =>
    Map<String, String> OPTION_UNDERLYING_MAP_FROM_ASA = new HashMap<String, String>(); // code =>
    Map<String, String> DECI_MAP_FROM_ASA = new HashMap<String, String>(); // code =>
    Map<String, String> DECI_MAP_FROM_ASA_OPTION = new HashMap<String, String>(); // code =>
    Map<String, String> DECI_MAP_FROM_ASA_FUTURE = new HashMap<String, String>(); // code =>
    Map<String, List<IndexBaseInfo>> INDEX_BASE_INFO_MAP = new HashMap<String, List<IndexBaseInfo>>(); // screenName 
    // => indexbaseinfo list
    // Decoder11
    List<String> INDUSTRY_SC_LIST = new ArrayList<String>();
    List<String> INDUSTRY_TC_LIST = new ArrayList<String>();
    List<String> INDUSTRY_EN_LIST = new ArrayList<String>();
    List<String> INDUSTRY_LIST = new ArrayList<String>();
    List<IndustryBaseInfo> INDUSTRY_CODE_LIST = new ArrayList<IndustryBaseInfo>();
    Map<String, List<String>> INDUSTRY_CODES_MAP = new HashMap<String, List<String>>(); // industryCode => list
    Map<String, String> INDUSTRY_CODE_MAP = new HashMap<String, String>(); // code => industry code
    // STOCK_INFO_MAP: { [code: string]: StockInfo } = {};
    Map<String, IndustryInfo> INDUSTRY_MAP = new HashMap<String, IndustryInfo>(); // industryCode => IndustryInfo
    // Decoder12
    double BLOCKTRADE;
    double BLOCKTRADENUM;
    double BLOCKTRADENUM_EX;
    // Decoder13
    // FUTURE_MAP: { [code: string]: string[] } = {}; // deprecated, pls use FUTURE_MONTH_MAP_FROM_ASA
    // Decoder14
    // FUTURE_UNDERLYING_MAP: { [code: string]: string } = {}; // deprecated, pls use FUTURE_UNDERLYING_MAP_FROM_ASA
    // Decoder15
    List<String> OPTION_LIST = new ArrayList<String>();
    // OPTION_MAP: { [code: string]: string[] } = {}; // deprecated, pls use OPTION_MONTH_MAP_FROM_ASA
    // DECI_MAP: { [code: string]: string } = {}; // deprecated, pls use DECI_MAP_FROM_ASA
    // Decoder16
    // OPTION_UNDERLYING_MAP: { [key: string]: string } = {}; // deprecated, pls use OPTION_UNDERLYING_MAP_FROM_ASA
    Map<String, List<String>> DECI_RELATED_MAP = new HashMap<String, List<String>>(); // code => array of name
    // Decoder17
    List<Integer> TRADING_DAY_LIST = new ArrayList<Integer>();
    // Decoder18
    Map<String, Map<String, TradingTimeInfo>> TRADING_TIME_MAP =
            new HashMap<String, Map<String, TradingTimeInfo>>(); // typeKey => {code: TradingTimeInfo}
    String STOCK_DEF_CUTOFF = "1230";
    // Decoder19
    // ETF_LIST: string[] = []; // Merged into STKTYPEMAP
    // Decoder21
    List<IndexBaseInfo> LOCAL_BASE_INFO_LIST = new ArrayList<IndexBaseInfo>();
    // Decoder22
    // Decoder23
    List<IndexBaseInfo> GLOBAL_BASE_INFO_LIST = new ArrayList<IndexBaseInfo>();
    // Deocder24
    String ShortSellDate;
    // Decoder25
    Map<String, String> TIPS_NAME_MAP = new HashMap<String, String>(); // tipsId(authorId) => tipsName(auther name)
    // Decoder29
    Map<String, List<String>> ISSUMAP = new HashMap<String, List<String>>(); // lang => issuers

    // Decoder31
    Map<String, IEInfoStruct> URL_INFO_MAP = new HashMap<String, IEInfoStruct>(); // key => IEInfo

    // Decoder38
    Map<String, List<String>> OPTION_MONTH_MAP_FROM_ASA = new HashMap<String, List<String>>(); // code => monthlist

    // Decoder36
    // INDEX_BASE_INFO_MAP

    // Decoder37
    // Decoder39
    Map<String, String> AHFT_MAP = new HashMap<String, String>(); // main code => underlying code
    Map<String, String> AHFT_VALUE_MAP = new HashMap<String, String>(); // underlying code => main code
    // Decoder40
    Map<String, String> DTDC_MAP = new HashMap<String, String>(); // a share code => hkex code
    Map<String, String> DTDC_VALUE_MAP = new HashMap<String, String>(); // hkex code => ashare code
    List<String> DTDC_LIST = new ArrayList<String>(); // A-Share codes

    // Decoder42
    Map DECI_RELATED_MAP_FROM_ASA = new HashMap<>();

    // Decoder60
    List<String> SH_SHARE_LIST = new ArrayList<String>();
    // VALIDCODE: string[] = [];
    // SEC_TYPE_STOCK
    List<StockInfo> SH_SHARE_INFO_LIST = new ArrayList<StockInfo>();
    Map<String, StockInfo> SH_SHARE_STOCK_INFO_MAP = new HashMap<String, StockInfo>(); // shCode => Stockinfo
    Map SEC_TYPE_STOCK = new HashMap<>();

    // Decoder61
    List<Object> SH_SHARE_CONSTATE_HLIST = new ArrayList<Object>();
    List<Object> SH_SHARE_CONSTATE_ALIST = new ArrayList<Object>();

    // Decoder63
    List<String> SH_SHARE_INDUSTRY_LIST = new ArrayList<String>();
    List<Industry> SH_SHARE_INDUSTRY_CODE_INFO_LIST = new ArrayList<Industry>();
    Map SH_SHARE_CODE_INDUSTRY_MAP = new HashMap<>();
    Map SH_SHARE_NUMBER_FOR_INDUSTRY_MAP = new HashMap<>();
    Map SH_SHARE_INDUSTRY_CODES_MAP = new HashMap<>();
    Map<String, Industry> SH_SHARE_CODE_INDUSTRYINFO_MAP = new HashMap<>(); // indCode => Industry
    Map<String, Boolean> SH_SHARE_CONNECT_STATES_MAP = new HashMap<String, Boolean>(); // code => state

    // Decoder65
    List<Integer> ASHARE_SH_TRADINGDAY_LIST = new ArrayList<Integer>();

    // Decoder70
    List<String> SZ_SHARE_LIST = new ArrayList<String>();
    // VALIDCODE: string[] = [];
    // SEC_TYPE_STOCK
    List<StockInfo> SZ_SHARE_INFO_LIST = new ArrayList<StockInfo>();
    Map<String, StockInfo> SZ_SHARE_STOCK_INFO_MAP = new HashMap<String, StockInfo>(); // szCode => StockInfo

    // Decoder71
    // todo
    List<String> SZ_SHARE_CONSTATE_HLIST = new ArrayList<String>();
    List<String> SZ_SHARE_CONSTATE_ALIST = new ArrayList<String>();
    Map<String, Boolean> SZ_SHARE_CONNECT_STATES_MAP = new HashMap<String, Boolean>(); // aCode => state
    // Decoder73
    List<String> SZ_SHARE_INDUSTRY_LIST = new ArrayList<String>();
    List<Industry> SZ_SHARE_INDUSTRY_CODE_INFO_LIST = new ArrayList<Industry>();
    Map<String, String> SZ_SHARE_CODE_INDUSTRY_MAP = new HashMap<String, String>(); // code => industry code
    Map<String, Integer> SZ_SHARE_NUMBER_FOR_INDUSTRY_MAP = new HashMap<String, Integer>(); // indCode => num
    Map<String, List<String>> SZ_SHARE_INDUSTRY_CODES_MAP = new HashMap<String, List<String>>(); // indeCode => list
    Map<String, Industry> SZ_SHARE_CODE_INDUSTRYINFO_MAP = new HashMap<String, Industry>(); // indCode => Industry

    // Decoder83

    // Decoder75
    List<Integer> ASHARE_SZ_TRADINGDAY_LIST = new ArrayList<Integer>();

    // Decoder85
    Map<String, List<String>> HSHARE_COMMENTATOR_LIST = new HashMap<String, List<String>>(); // lang => list
    Map<String, List<String>> HSHARE_RECOMMENTATOR_LIST = new HashMap<String, List<String>>(); // lang => list
    List<FirmName> FIRM_NAME = new ArrayList<FirmName>();

    // Decoder88
    List<Integer> TRADING_DAY_LIST_20 = new ArrayList<Integer>();

    // Decoder87US
    // TRADING_DAY_LIST: Date[] = [];

    // Decoder88US

    // Decoder89US
    List<Object> US_INDEX_LIST = new ArrayList<Object>();

    // Decoder90US
    List<Object> US_INDUSTRY_CODE_LIST = new ArrayList<Object>();

    //
    List<Object> RISK_FREE_VALUE = new ArrayList<Object>();

    // DecoderStaticResource
    Map<String, Object> STATIC_RESOURCE = new HashMap<String, Object>();
    Map<String, List<InstrumentInfo>> INSTRUMENT_NAME = new HashMap<String, List<InstrumentInfo>>(); // securityType 
    // => InstrumentInfo list
    Map<String, List<SearchListItem>> SEARCH_LIST = new HashMap<String, List<SearchListItem>>(); // listName =>
    // SearchListItemList
    Map<String, List<Disclaimer>> DISCLAIMERS = new HashMap<String, List<Disclaimer>>(); // name => Disclaimer list

    void clear() {
        clearArray();
        clearMap();
    }

    void clearArray() {
        List<List> clearList = asList(
                asList(this.WARR_NEW_LIST),
                asList(this.CBBC_NEW_LIST),
                asList(this.WARR_EXP_LIST),
                asList(this.CBBC_EXP_LIST),
                asList(this.GROBROKER),
                asList(this.UNGBROKER),
                asList(this.BOND_LIST),
                asList(this.REIT_LIST),
                asList(this.SPECIAL_UNDERLYING_LIST),
                asList(this.VALIDCODE),
                asList(this.ADR_LIST),
                asList(this.INDUSTRY_SC_LIST),
                asList(this.INDUSTRY_TC_LIST),
                asList(this.INDUSTRY_EN_LIST),
                asList(this.INDUSTRY_LIST),
                asList(this.INDUSTRY_CODE_LIST),
                asList(this.OPTION_LIST),
                asList(this.TRADING_DAY_LIST),
                asList(this.LOCAL_BASE_INFO_LIST),
                asList(this.GLOBAL_BASE_INFO_LIST),
                asList(this.DTDC_LIST),
                asList(this.SH_SHARE_LIST),
                asList(this.SH_SHARE_INFO_LIST),
                asList(this.SH_SHARE_CONSTATE_HLIST),
                asList(this.SH_SHARE_CONSTATE_ALIST),
                asList(this.SH_SHARE_INDUSTRY_LIST),
                asList(this.SH_SHARE_INDUSTRY_CODE_INFO_LIST),
                asList(this.ASHARE_SH_TRADINGDAY_LIST),
                asList(this.SZ_SHARE_LIST),
                asList(this.SZ_SHARE_INFO_LIST),
                asList(this.SZ_SHARE_CONSTATE_HLIST),
                asList(this.SZ_SHARE_CONSTATE_ALIST),
                asList(this.SZ_SHARE_INDUSTRY_LIST),
                asList(this.SZ_SHARE_INDUSTRY_CODE_INFO_LIST),
                asList(this.ASHARE_SZ_TRADINGDAY_LIST),
                asList(this.FIRM_NAME),
                asList(this.TRADING_DAY_LIST_20),
                asList(this.US_INDEX_LIST),
                asList(this.US_INDUSTRY_CODE_LIST),
                asList(this.RISK_FREE_VALUE)

        );
        for (List arr : clearList) {
            arr.clear();
        }
    }

    void clearMap() {
        List<Map> clearList = asList(
                this.AHMAP,
                this.SZORSHMAP,
                this.CODEFIRM_MAP,
                this.UNGBROSCHMAP,
                this.FIRMMAP,
                this.GRUBROSCHMAP,
                this.RELATE_STOCKMAP,
                this.SPREAD_KEY_MAP,
                this.SPREAD_MAP,
                this.STOCK_INFO_MAP,
                this.UNDERLYINGMAP,
                this.UNDERLYINGCODE,
                this.STKTYPEMAP,
                this.UNDERLYINGDETIAL,
                this.CBBC_RN_MAP,
                this.FUTURE_MONTH_MAP_FROM_ASA,
                this.FUTURE_UNDERLYING_MAP_FROM_ASA,
                this.OPTION_UNDERLYING_MAP_FROM_ASA,
                this.DECI_MAP_FROM_ASA,
                this.DECI_MAP_FROM_ASA_OPTION,
                this.DECI_MAP_FROM_ASA_FUTURE,
                this.INDEX_BASE_INFO_MAP,
                this.INDUSTRY_CODES_MAP,
                this.INDUSTRY_CODE_MAP,
                this.INDUSTRY_MAP,
                this.DECI_RELATED_MAP,
                this.TRADING_TIME_MAP,
                this.TIPS_NAME_MAP,
                this.ISSUMAP,
                this.URL_INFO_MAP,
                this.OPTION_MONTH_MAP_FROM_ASA,
                this.AHFT_MAP,
                this.AHFT_VALUE_MAP,
                this.DTDC_MAP,
                this.DTDC_VALUE_MAP,
                this.SH_SHARE_STOCK_INFO_MAP,
                this.SH_SHARE_CODE_INDUSTRYINFO_MAP,
                this.SH_SHARE_CONNECT_STATES_MAP,
                this.SZ_SHARE_STOCK_INFO_MAP,
                this.SZ_SHARE_CONNECT_STATES_MAP,
                this.SZ_SHARE_CODE_INDUSTRY_MAP,
                this.SZ_SHARE_NUMBER_FOR_INDUSTRY_MAP,
                this.SZ_SHARE_INDUSTRY_CODES_MAP,
                this.SZ_SHARE_CODE_INDUSTRYINFO_MAP,
                this.HSHARE_COMMENTATOR_LIST,
                this.HSHARE_RECOMMENTATOR_LIST,
                this.STATIC_RESOURCE,
                this.INSTRUMENT_NAME,
                this.SEARCH_LIST,
                this.DISCLAIMERS
        );
        for (Map map : clearList) {
            map.clear();
        }
    }
}
