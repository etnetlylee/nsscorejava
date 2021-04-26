package coreStorage.data.decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.QuoteData;
import coreModel.RawData;
import coreStorage.model.BrokerBidAskQueue;
import coreStorage.model.BrokerInfo;
import coreStorage.model.BrokerSpread;
import model.BrokerFirm;

public class BrokerQueueDecoder extends Decoder {
    public static final String uniqueID = "brokerqueue";
    final Logger log = Logger.getLogger("StorageDecodeDispatcher");

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

        Map<String, BrokerFirm> ungbroschMap =
                this.getContext().getAsaStorage().UNGBROSCHMAP;

        BrokerBidAskQueue bidAskQueue;
        if (nssData.getData() != null) {
            bidAskQueue = (BrokerBidAskQueue) nssData.getData();
        } else {
            bidAskQueue = new BrokerBidAskQueue();
        }

        final List<String> queues = Arrays.asList(value.split("#"));
        for (String row : queues) {
            // each queue e.g. 0/1/2/3/4...
            if (row.trim().length() == 0) {
                // Logger.getInstance().log("Empty broker update");
                continue;
            }
            final List<String> queue = Arrays.asList(row.split("|"));
            final String queueType = queue.get(0);
            final String spreadNo = queue.get(1);
            final List<String> queueBrokers = Arrays.asList(queue.get(2).split(","));
            Map<String, BrokerInfo> brokerMap = new HashMap<String, BrokerInfo>(); // spreadNum -> BrokerInfo

            if (queue.size() < 2 || queue.size() > 3) {
                continue;
            }
            for (String qb : queueBrokers) {
                String brokerName = "";
                String no = qb.toString();
                BrokerFirm brokerFirm;
                if (no.trim() == "") {
                    continue;
                }

                // FIXED As UNGBROSCHMAP use zero padding for broker no.
                if (no != "-1") {
                    while (no.length() < 4) {
                        no = "0" + no;
                    }
                } else {
                    this.log.info("remove queue");
                }

                brokerFirm = ungbroschMap.get(no);
                if (brokerFirm != null) {
                    brokerName = (brokerFirm.getTcName() != null)
                            ? brokerFirm.getTcName()
                            : (brokerFirm.getScName() != null)
                            ? brokerFirm.getScName()
                            : brokerFirm.getEngName();
                }
                brokerName = (brokerName != null) ? brokerName : qb;

                String brokerTcName = "";
                String brokerScName = "";
                String brokerEnName = "";

                if (brokerFirm != null) {
                    brokerTcName = brokerFirm.getTcName() != null
                            ? brokerFirm.getTcName()
                            : brokerFirm.getEngName();
                    brokerScName = brokerFirm.getScName() != null
                            ? brokerFirm.getScName()
                            : brokerFirm.getEngName();
                    brokerEnName = brokerFirm.getEngName();
                }

                brokerMap.put(no, new BrokerInfo(
                        no.toString(),
                        brokerName, // code for compatible, use lang name instead
                        brokerTcName,
                        brokerScName,
                        brokerEnName
                ));
            }

            if (spreadNo != null) {
                int intSpreadNo = Math.abs(Integer.parseInt(spreadNo));
                if ("A" == queueType) {
                    if (queueBrokers.get(0) == "-1" && queueBrokers.size() == 1) {
                        log.info("remove ask spread " +
                                spreadNo +
                                " list len=" +
                                bidAskQueue.getAskBrokersQueue().size());
                        removeSpread(bidAskQueue.getAskBrokersQueue(), intSpreadNo);
                    } else {
                        final BrokerSpread updateSpread =
                                new BrokerSpread(spreadNo, queueBrokers, brokerMap);
                        addOrUpdateSpread(
                                bidAskQueue.getAskBrokersQueue(), intSpreadNo, updateSpread);

                        // bidAskQueue.askBrokersQueue.replaceRange(intSpreadNo, intSpreadNo,
                        //     [BrokerSpread(spreadNo, queueBrokers, brokerMap)]);
                        log.info("update ask spread " +
                                spreadNo +
                                " len=" +
                                bidAskQueue.getAskBrokersQueue().size());
                    }
                } else if ("B" == queueType) {
                    if (queueBrokers.get(0) == "-1" && queueBrokers.size() == 1) {
                        removeSpread(bidAskQueue.getAskBrokersQueue(), intSpreadNo);
                        log.info("remove bid spread " +
                                spreadNo +
                                " list len=" +
                                bidAskQueue.getAskBrokersQueue().size());
                    } else {
                        final BrokerSpread updateSpread =
                                new BrokerSpread(spreadNo, queueBrokers, brokerMap);
                        addOrUpdateSpread(
                                bidAskQueue.getBidBrokersQueue(), intSpreadNo, updateSpread);
                        // bidAskQueue.bidBrokersQueue.replaceRange(intSpreadNo, intSpreadNo,
                        //     [BrokerSpread(spreadNo, queueBrokers, brokerMap)]);
                        log.info("update bid spread: " +
                                spreadNo +
                                " len=" +
                                bidAskQueue.getBidBrokersQueue().size());
                    }
                }
            } else {
                log.info("Invalid queue no for broker list");
            }
        }

        nssData.setData(bidAskQueue);
        nssData.setReady(true);
        return nssData;
    }

    public void addOrUpdateSpread(
            List<BrokerSpread> spreadList, int spreadNo, BrokerSpread spread) {
        int end = spreadNo >= spreadList.size() ? spreadNo : spreadNo + 1;
        if (spreadList.isEmpty()) {
            List<BrokerSpread> growableList = new ArrayList<BrokerSpread>();
            for(int i =0; i<end; i++){
                growableList.add(spread);
            }
            spreadList = growableList;
        } else {
            if (spreadNo > spreadList.size()) {
                spreadList.add(spread);
            } else {
                spreadList.add(spreadNo, spread);
            }
        }
    }

    public void removeSpread(List<BrokerSpread> spreadList, int spreadNo) {
        if (spreadNo > spreadList.size()) {
            spreadList.add(new BrokerSpread(null, null, null));
        } else {
            spreadList.add(spreadNo, new BrokerSpread(null, null, null));
        }
    }
}
