package com.etnet.coresdk.coreStorage.data.decoder;

import java.util.Arrays;
import java.util.List;

import com.etnet.coresdk.coreModel.Decoder;
import com.etnet.coresdk.coreModel.NssData;
import com.etnet.coresdk.coreModel.QuoteData;
import com.etnet.coresdk.coreModel.RawData;
import com.etnet.coresdk.coreStorage.model.VcmTriggerQueue;
import com.etnet.coresdk.coreStorage.model.VcmTriggerEvent;

public class VcmTriggerDecoder extends Decoder {
    public static final String uniqueID = "vcmtrigger";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        QuoteData cached =
                getContext().getStorage().getQuoteData(code, rawData.getFieldID());
        NssData nssData;
        final String value = (rawData.getData()).toString();

        VcmTriggerQueue queue = new VcmTriggerQueue();
        final List<String> records = Arrays.asList(value.split("#"));

        for (String record : records){
            final List<String> fields = Arrays.asList(record.split("|"));
            if (fields != null && fields.size() == 7) {
                VcmTriggerEvent vcmTriggerEvent = new VcmTriggerEvent();
                final String seqNo = fields.get(0);
                final String startTimeStr = fields.get(1); // HHMMSS
                final String endTime = fields.get(2); // HHMMSS
                final String referencePrice = fields.get(3);
                final String lowerPriceLimit = fields.get(4);
                final String upperPriceLimit = fields.get(5);
                final String indicator = fields.get(6); // 1=ON/0=OFF

                vcmTriggerEvent.setSeqNo(Integer.parseInt(seqNo, 10));
                vcmTriggerEvent.setStartTime(startTimeStr);
                vcmTriggerEvent.setEndTime(endTime);
                vcmTriggerEvent.setReferencePrice(Double.parseDouble(referencePrice));
                vcmTriggerEvent.setUpperPriceLimit(Double.parseDouble(upperPriceLimit));
                vcmTriggerEvent.setLowerPriceLimit(Double.parseDouble(lowerPriceLimit));
                vcmTriggerEvent.setIndicator(indicator == "1");

                final int startTime = Integer.parseInt(
                        startTimeStr.replaceAll(":", "").substring(0, 4), 10);
                final int cutoffTime = Integer.parseInt(
                        getContext().getAsaStorage().STOCK_DEF_CUTOFF, 10);
                if (startTime > cutoffTime) {
                    queue.setPmEvent(vcmTriggerEvent);
                } else {
                    queue.setAmEvent(vcmTriggerEvent);
                }

                if (indicator == "1") {
                    queue.setTriggering(true);
                    queue.setTriggeringStruct(vcmTriggerEvent);
                }
            }
        }


        if (cached != null) {
            nssData = cached.getNssData();
        } else {
            nssData = new NssData(null);
        }

        nssData.setData(queue);
        nssData.setReady(true);

        return nssData;
    }
}
