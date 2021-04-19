package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;

import static constants.AsaConstant.STOCK_CONNECTED_CURRENT;
import static constants.AsaConstant.STOCK_CONNECTED_EX;
import static constants.AsaConstant.STOCK_EXCHANGE_HKEX;
import static constants.AsaConstant.STOCK_EXCHANGE_SSE;

public class Decoder61 extends Decoder {
    static final String uniqueID = "61";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        data.SH_SHARE_CONSTATE_HLIST = new ArrayList<Object>();
        data.SH_SHARE_CONSTATE_ALIST = new ArrayList<Object>();
        if (value != null && value.trim() != "") {
            final List<String> values = Arrays.asList(value.split("|"));
            for (String codeInfo : values) {
                final List<String> infos = Arrays.asList(codeInfo.split(","));
                final String aShareCode = infos.get(0);
                final String aShareType = infos.get(1);
                final String connectType = infos.get(2);
                if (aShareType == STOCK_EXCHANGE_HKEX) {
                    data.SH_SHARE_CONSTATE_HLIST.add(aShareCode);
                } else if (aShareType == STOCK_EXCHANGE_SSE) {
                    data.SH_SHARE_CONSTATE_ALIST.add(aShareCode);
                } else {
                }
                boolean connected = true;
                if (connectType == STOCK_CONNECTED_CURRENT) {
                    connected = true;
                } else if (connectType == STOCK_CONNECTED_EX) {
                    connected = false;
                }
                data.SH_SHARE_CONNECT_STATES_MAP.put(aShareCode, connected);
            }
        }

        return null;
    }
}
