package com.etnet.coresdk.config.us;

import java.util.ArrayList;
import java.util.HashMap;

import com.etnet.coresdk.coreConfigInterface.AsaDecodersConfig;
import com.etnet.coresdk.coreModel.AsaDecoder;
import com.etnet.coresdk.decoder.Decoder12;
import com.etnet.coresdk.decoder.Decoder17;
import com.etnet.coresdk.decoder.Decoder18;
import com.etnet.coresdk.decoder.Decoder88US;
import com.etnet.coresdk.decoder.Decoder89US;
import com.etnet.coresdk.decoder.Decoder90US;

import static com.etnet.coresdk.constants.AsaConstant.ASA_FIELD_US;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_BLOCK_TRADE;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_TRADING_TIME;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_US_CUSIP_ISIN;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_US_INDEX_LIST;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_US_INDUSTRY_CODE_LIST;
import static com.etnet.coresdk.constants.AsaConstant.RESOURCE_US_TRADING_DATE;


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
