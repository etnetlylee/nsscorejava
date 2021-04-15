package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;
import model.BrokerFirm;

public class Decoder5 extends Decoder {
    static final String uniqueID = "5";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        data.UNGBROKER = new ArrayList<Map<String, String>>();
        data.GROBROKER = new ArrayList<Object>();

        if (value != null && value.trim() != "") {
            final List<String> brokerFirms = Arrays.asList(value.split("|"));
            for (String firms : brokerFirms) {
                BrokerFirm brokerGroupStruct = new BrokerFirm();
                final List<String> brokerFirm = Arrays.asList(firms.split(","));
                brokerGroupStruct.setFirmCode(brokerFirm.get(0));
                brokerGroupStruct.setEngName(brokerFirm.get(1));
                brokerGroupStruct.setTcName(brokerFirm.get(2));
                brokerGroupStruct.setScName(brokerFirm.get(3));
                brokerGroupStruct.setRegion(brokerFirm.get(4));
                final List<String> brokers = Arrays.asList(brokerFirm.get(5).split(" "));
                brokerGroupStruct.setBrokersNo(brokers);
                data.GROBROKER.add(brokerGroupStruct);
                List<String> firmList = new ArrayList<String>();

                for (String broker : brokers) {
                    Map<String, String> ungroupStruct = new HashMap<String, String>();
                    ungroupStruct.put("firmCode", brokerGroupStruct.getFirmCode());
                    ungroupStruct.put("brokerCode", broker);
                    ungroupStruct.put("scName", brokerGroupStruct.getScName());
                    ungroupStruct.put("engName", brokerGroupStruct.getEngName());

                    data.UNGBROKER.add(ungroupStruct);
                    final String brokerCode = ungroupStruct.get("brokerCode");
                    data.CODEFIRM_MAP.put(brokerCode, ungroupStruct.get("firmCode"));
                    data.UNGBROSCHMAP.put(brokerCode, brokerGroupStruct);
                    firmList.add(ungroupStruct.get("brokerCode"));
                }
                Collections.sort(firmList);
                final String firmCode = brokerGroupStruct.getFirmCode();
                if (data.FIRMMAP.containsKey(firmCode)) {
                    data.FIRMMAP.put(firmCode, firmList);
                }
                if (data.GRUBROSCHMAP.containsKey(firmCode)) {
                    data.GRUBROSCHMAP.put(firmCode, brokerGroupStruct);
                }
            }
        }
        return null;
    }
}
