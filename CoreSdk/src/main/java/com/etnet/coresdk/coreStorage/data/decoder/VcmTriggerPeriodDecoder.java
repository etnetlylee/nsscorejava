package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.Vcm;

public class VcmTriggerPeriodDecoder extends Decoder {
    public static final String uniqueID = "vcmtriggerperiod";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached =
                getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;
        final String value = (rawData.getData()).toString();
        final List<String> fields = Arrays.asList(value.split("|"));
        Vcm vcm = new Vcm();

        if (fields.size() == 7) {
            final int seqNo = Integer.parseInt(fields.get(0), 10);
            final String startTime = fields.get(1); // HHMMSS
            final String endTime = fields.get(2); // HHMMSS
            final double refPrice = Double.parseDouble(fields.get(3));
            final double lowLimit = Double.parseDouble(fields.get(4));
            final double upLimit = Double.parseDouble(fields.get(5));
            final boolean onOff = fields.get(6) == "1"; // 1=ON/0=OFF

            vcm.setSeqNo(seqNo);
            vcm.setStartTime(startTime);
            vcm.setEndTime(endTime);
            vcm.setRefPrice(refPrice);
            vcm.setUpLimit(upLimit);
            vcm.setLowLimit(lowLimit);
            vcm.setOnOff(onOff);
        }

        if (cached != null) {
            nssData = cached.getNssData();
        } else {
            nssData = new NssData(null);
        }
        nssData.setData(vcm);
        nssData.setReady(true);

        return nssData;
    }
}
