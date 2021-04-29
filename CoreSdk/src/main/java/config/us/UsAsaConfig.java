package config.us;

import java.util.ArrayList;
import java.util.HashMap;

import coreConfigInterface.AsaDecodersConfig;
import coreModel.AsaDecoder;
import decoder.Decoder12;
import decoder.Decoder17;
import decoder.Decoder18;
import decoder.Decoder88US;
import decoder.Decoder89US;
import decoder.Decoder90US;

import static constants.AsaConstant.ASA_FIELD_US;
import static constants.AsaConstant.RESOURCE_BLOCK_TRADE;
import static constants.AsaConstant.RESOURCE_TRADING_TIME;
import static constants.AsaConstant.RESOURCE_US_CUSIP_ISIN;
import static constants.AsaConstant.RESOURCE_US_INDEX_LIST;
import static constants.AsaConstant.RESOURCE_US_INDUSTRY_CODE_LIST;
import static constants.AsaConstant.RESOURCE_US_TRADING_DATE;


public class UsAsaConfig {
    public final AsaDecodersConfig ShAsaConfig = new AsaDecodersConfig(
            ASA_FIELD_US,
            new HashMap<String, AsaDecoder>() {{
                put(RESOURCE_BLOCK_TRADE, new AsaDecoder(RESOURCE_BLOCK_TRADE, "", Decoder12.uniqueID,
                        new Decoder12()));
                put(RESOURCE_US_TRADING_DATE, new AsaDecoder(RESOURCE_US_TRADING_DATE, "", Decoder17.uniqueID,
                        new Decoder17()));
                put(RESOURCE_TRADING_TIME, new AsaDecoder(RESOURCE_TRADING_TIME, "", Decoder18.uniqueID,
                        new Decoder18()));
                put(RESOURCE_US_CUSIP_ISIN, new AsaDecoder(RESOURCE_US_CUSIP_ISIN, "", Decoder88US.uniqueID,
                        new Decoder88US()));
                put(RESOURCE_US_INDEX_LIST, new AsaDecoder(RESOURCE_US_INDEX_LIST, "", Decoder89US.uniqueID,
                        new Decoder89US()));
                put(RESOURCE_US_INDUSTRY_CODE_LIST, new AsaDecoder(RESOURCE_US_INDUSTRY_CODE_LIST, "", Decoder90US.uniqueID,
                        new Decoder90US()));
            }},
            new ArrayList<String>()
    );
}
