package coreStorage.data.decoder;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.QuoteData;
import coreModel.RawData;
import model.TurnoverRate;

public class TurnoverRateDecoder extends Decoder {
    public static final String uniqueID = "turnoverrate";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached =
                getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;
        final String value = (rawData.getData()).toString();

        if (cached != null) {
            nssData = cached.getNssData();
        } else {
            nssData = new NssData(null);
        }

        TurnoverRate turnOverRateStruct = (TurnoverRate) nssData.getData();
        if (turnOverRateStruct == null) {
            turnOverRateStruct = new TurnoverRate(null, null, null);
            nssData.setData(turnOverRateStruct);
            return nssData;
        }
        String serverFieldId = rawData.getServerFieldId();
        if (serverFieldId == "38") {
            turnOverRateStruct.setVolume(Integer.parseInt(value,10));
        } else if (serverFieldId == "324") {
            turnOverRateStruct.setLoatISC(Integer.parseInt(value,  10));
        }

        int volume = turnOverRateStruct.getVolume();
        double loatISC = turnOverRateStruct.getLoatISC();
        if (volume != 0 &&  loatISC != 0) {
            double turnOverRate = volume / loatISC;
            turnOverRateStruct.setTurnoverRate(turnOverRate);
            nssData.setData(turnOverRateStruct);
        }

        if (volume != 0 && loatISC != 0) {
            nssData.setReady(true);
        }

        return nssData;
    }
}
