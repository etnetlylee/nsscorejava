package com.etnet.coresdk.config.hk;

import java.util.Arrays;
import java.util.HashMap;

import com.etnet.coresdk.coreConfigInterface.AsaDecodersConfig;
import com.etnet.coresdk.coreModel.AsaDecoder;
import com.etnet.coresdk.decoder.Decoder10;
import com.etnet.coresdk.decoder.Decoder11;
import com.etnet.coresdk.decoder.Decoder12;
import com.etnet.coresdk.decoder.Decoder17;
import com.etnet.coresdk.decoder.Decoder18;
import com.etnet.coresdk.decoder.Decoder19;
import com.etnet.coresdk.decoder.Decoder24;
import com.etnet.coresdk.decoder.Decoder25;
import com.etnet.coresdk.decoder.Decoder29;
import com.etnet.coresdk.decoder.Decoder31;
import com.etnet.coresdk.decoder.Decoder36;
import com.etnet.coresdk.decoder.Decoder4;
import com.etnet.coresdk.decoder.Decoder40;
import com.etnet.coresdk.decoder.Decoder5;
import com.etnet.coresdk.decoder.Decoder6;
import com.etnet.coresdk.decoder.Decoder7;
import com.etnet.coresdk.decoder.Decoder8;
import com.etnet.coresdk.decoder.Decoder83;
import com.etnet.coresdk.decoder.Decoder85;
import com.etnet.coresdk.decoder.Decoder87;
import com.etnet.coresdk.decoder.Decoder88;
import com.etnet.coresdk.decoder.Decoder9;
import com.etnet.coresdk.decoder.DecoderRFR;

import static com.etnet.coresdk.constants.AsaConstant.ASA_FIELD_HK;
import static com.etnet.coresdk.constants.AsaConstant.FIRM_NAME;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_ADRLIST;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_AHMAPPING;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_BLOCK_TRADE;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_BROKER;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_ETF;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_HSHARE_COLUMNIST_WITH_LANG;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_INDUSTRY;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_ISSUE;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_RISK_FREE;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_SCREEN_INDEX_LIST;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_SECURITY_RELATED;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_SHORTSELLDATE;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_SPREAD_TABLE;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_STOCK_LINK_CODE;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_STOCK_NAME;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_TIPS_REC_NAME;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_TRADING_DAY;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_TRADING_DAY_20;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_TRADING_TIME;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_URL_INFO;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_VCM_CAS_LIST;
import static com.etnet.coresdk.constants.AsaConstant.SINGLE_RESOURCE_DTDC_MAPPING;

public class HkAsaConfig {
    public final AsaDecodersConfig HkAsaConfig = new AsaDecodersConfig(
            ASA_FIELD_HK,
            new HashMap<String, AsaDecoder>() {{
                put(RESOURCE_AHMAPPING, new AsaDecoder(RESOURCE_AHMAPPING, "", Decoder4.uniqueID, new Decoder4()));
                put(RESOURCE_BROKER, new AsaDecoder(RESOURCE_BROKER, "", Decoder5.uniqueID, new Decoder5()));
                put(RESOURCE_STOCK_LINK_CODE, new AsaDecoder(RESOURCE_STOCK_LINK_CODE, "", Decoder6.uniqueID, new Decoder6()));
                put(RESOURCE_SPREAD_TABLE, new AsaDecoder(RESOURCE_SPREAD_TABLE, "", Decoder7.uniqueID, new Decoder7()));
                put(RESOURCE_SECURITY_RELATED, new AsaDecoder(RESOURCE_SECURITY_RELATED, "", Decoder8.uniqueID, new Decoder8()));
                put(RESOURCE_ADRLIST, new AsaDecoder(RESOURCE_ADRLIST, "", Decoder9.uniqueID, new Decoder9()));
                put(RESOURCE_STOCK_NAME, new AsaDecoder(RESOURCE_STOCK_NAME, "stockName", Decoder10.uniqueID, new Decoder10()));
                put(RESOURCE_INDUSTRY, new AsaDecoder(RESOURCE_INDUSTRY, "", Decoder11.uniqueID, new Decoder11()));
                put(RESOURCE_BLOCK_TRADE, new AsaDecoder(RESOURCE_BLOCK_TRADE, "", Decoder12.uniqueID, new Decoder12()));
                put(RESOURCE_TRADING_DAY, new AsaDecoder(RESOURCE_TRADING_DAY, "", Decoder17.uniqueID, new Decoder17()));
                put(RESOURCE_TRADING_TIME, new AsaDecoder(RESOURCE_TRADING_TIME, "", Decoder18.uniqueID, new Decoder18()));
                put(RESOURCE_ETF, new AsaDecoder(RESOURCE_ETF, "etf", Decoder19.uniqueID, new Decoder19()));
                put(RESOURCE_TIPS_REC_NAME, new AsaDecoder(RESOURCE_TIPS_REC_NAME, "", Decoder25.uniqueID, new Decoder25()));
                put(RESOURCE_ISSUE, new AsaDecoder(RESOURCE_ISSUE, "", Decoder29.uniqueID, new Decoder29()));
                put(RESOURCE_TRADING_DAY_20, new AsaDecoder(RESOURCE_TRADING_DAY_20, "", Decoder88.uniqueID, new Decoder88()));
                put(RESOURCE_URL_INFO, new AsaDecoder(RESOURCE_URL_INFO, "", Decoder31.uniqueID, new Decoder31()));
                put(RESOURCE_RISK_FREE, new AsaDecoder(RESOURCE_RISK_FREE, "", DecoderRFR.uniqueID, new DecoderRFR()));
                put(RESOURCE_SCREEN_INDEX_LIST, new AsaDecoder(RESOURCE_SCREEN_INDEX_LIST, "", Decoder36.uniqueID, new Decoder36()));
                put(SINGLE_RESOURCE_DTDC_MAPPING, new AsaDecoder(SINGLE_RESOURCE_DTDC_MAPPING, "", Decoder40.uniqueID, new Decoder40()));
                put(RESOURCE_VCM_CAS_LIST, new AsaDecoder(RESOURCE_VCM_CAS_LIST, "", Decoder83.uniqueID, new Decoder83()));
                put(RESOURCE_HSHARE_COLUMNIST_WITH_LANG, new AsaDecoder(RESOURCE_HSHARE_COLUMNIST_WITH_LANG, "", Decoder85.uniqueID, new Decoder85()));
                put(FIRM_NAME, new AsaDecoder(FIRM_NAME, "", Decoder87.uniqueID, new Decoder87()));
                put(RESOURCE_SHORTSELLDATE, new AsaDecoder(RESOURCE_SHORTSELLDATE, "", Decoder24.uniqueID, new Decoder24()));
//                put(RESOURCE_DISCLAIMERS, new AsaDecoder(RESOURCE_DISCLAIMERS, "", DecoderDisclaimers.uniqueID, new DecoderDisclaimers()));
//                put(RESOURCE_SEARCH_LIST, new AsaDecoder(RESOURCE_SEARCH_LIST, "", DecoderSearchList.uniqueID, new DecoderSearchList()));
//                put(RESOURCE_INSTRUMENT_NAME, new AsaDecoder(RESOURCE_INSTRUMENT_NAME, "", DecoderInstrument.uniqueID, new DecoderInstrument()));
            }},
            Arrays.asList(RESOURCE_SPREAD_TABLE,
                    RESOURCE_TRADING_DAY,
                    RESOURCE_RISK_FREE,
                    RESOURCE_SHORTSELLDATE)
    );

}
