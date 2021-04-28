package coreSubscriber.request;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import coreController.CommandController;
import coreModel.DecoderInfo;
import coreModel.NssData;
import coreModel.QuoteData;
import coreSubscriber.Subscriber;
import util.DecodeHelper;

import static constants.Command.COMMAND_ADD_BOTH;
import static constants.Command.COMMAND_ADD_SNAPSHOT;

public class QuoteRequest extends Request {
    final Logger log = Logger.getLogger("QuoteRequest");
    List<String> _codes;
    List<String> _fields;
    int _commandId = COMMAND_ADD_BOTH;

    public QuoteRequest(int commandId, List<String> codes, List<String> fields) {
        super(1, commandId, -1);
        this._codes = codes;
        this._fields = fields;
        setCommandId(commandId);
    }

    public List<String> getCodes() {
        final List<String> denormalizeCodes = new ArrayList<String>();
        final Map<String, List<String>> futureMonthMap =
                getNssCoreContext().getAsaStorage().FUTURE_MONTH_MAP_FROM_ASA;
        for (String code : _codes) {
            denormalizeCodes.add(DecodeHelper.denormalizeCode(code, futureMonthMap));
        }
        return denormalizeCodes;
    }

    public List<String> getFields() {
        return this._fields;
    }

    public String getSerializedCode() {
        return StringUtils.join(_codes, "|");
    }

    public String getSerializedField() {
        return StringUtils.join(_fields, "|");
    }

    public void resubscribe() {
        List<String> codes = getCodes();
        List<String> fields = getFields();
        List<String> serverFieldIds =
                DecodeHelper.getServerFieldIds(fields, getNssCoreContext());
        getNssCoreContext()
                .getController()
                .getCommandController()
                .sendAddQuoteCommand(
                        getRequestId(), codes, serverFieldIds, getCommandId());
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        // server scope code-field checking
        List<String> willSubscribeCodes = new ArrayList<String>();
        // List<String> willSubscribeFields = _fields;
        Map<String, List<String>> willSeperateSubscribe = new HashMap<String, List<String>>();
        Map<String, List<String>> willFetchFromCache = new HashMap<String, List<String>>();
        Map<String, List<String>> willWaitFromCache = new HashMap<String, List<String>>();
        final Map<String, DecoderInfo> decoderConfig =
                getNssCoreContext().getDecoderConfig().getConfig();

        final boolean snap = _commandId == COMMAND_ADD_SNAPSHOT;

        for (String code : _codes) {
            List<String> willSubscribeFieldsInThisLoop = new ArrayList<String>();
            for (String field : _fields) {
                boolean isSubscribed = getSubscriberController().hasQuoteData(code, field);
                // add it immediately
                getSubscriberController()
                        .addQuoteSubscriber(code, field, subscriber, snap);
                if (isSubscribed) {
                    // from cache?
                    NssData nssData =
                            getSubscriberController().getQuoteData(code, field).getNssData();
                    boolean isDataReady = nssData.getReady();
                    if (isDataReady) {
                        // update From cache directly
                        if (willFetchFromCache.get(code) != null) {
                            willFetchFromCache.put(code, new ArrayList<String>());
                        }
                        willFetchFromCache.get(code).add(field);
                    } else {
                        // wait data return
                        if (willWaitFromCache.get(code) != null) {
                            willWaitFromCache.put(code, new ArrayList<String>());
                        }
                        willWaitFromCache.get(code).add(field);
                    }
                } else {
                    // from nss
                    final DecoderInfo decoder = decoderConfig.get(field);
                    final List<String> serverFieldIds = decoder.getServerFieldIds();
                    if (serverFieldIds != null) {
                        if (decoder.isComposite()) {
                            boolean allServerFieldSubscribed = true;
                            for (String serverFieldId : serverFieldIds) {
                                final boolean isServerFieldIdSubscribed =
                                        getSubscriberController().hasQuoteData(code, serverFieldId);
                                if (isServerFieldIdSubscribed) {
                                    this
                                            .getSubscriberController()
                                            .addCompositeDeps(code, serverFieldId);
                                    // would like to trigger decoder again...
                                    willSubscribeFieldsInThisLoop.add(serverFieldId);
                                } else {
                                    final QuoteData quoteData = getSubscriberController()
                                            .createQuoteData(code, serverFieldId);
                                    quoteData.plusCompositeDepsCount();
                                    allServerFieldSubscribed = false;
                                    willSubscribeFieldsInThisLoop.add(serverFieldId);
                                }
                            }
                        } else {
                            willSubscribeFieldsInThisLoop.add(field);
                        }
                    } else {
                        willSubscribeFieldsInThisLoop.add(field);
                    }
                }
            }

            if (willSubscribeFieldsInThisLoop.size() == _fields.size()) {
                // new code
                willSubscribeCodes.add(code);
            } else {
                if (willSubscribeFieldsInThisLoop.size() > 0) {
                    if (willSeperateSubscribe.get(code) != null) {
                        willSeperateSubscribe.put(code, new ArrayList<String>());
                    }
                    willSeperateSubscribe.get(code).addAll(willSubscribeFieldsInThisLoop);
                }
            }
        }


        log.info("will wait cache ready:");

        for (Map.Entry<String, List<String>> entry : willWaitFromCache.entrySet()) {
            log.info("    - " + entry.getKey() + " = " + StringUtils.join(entry.getValue(), "|"));
        }

        log.info("end");

        log.info("will fetch from cache:");

        for (Map.Entry<String, List<String>> entry : willFetchFromCache.entrySet()) {
            log.info("    - " + entry.getKey() + " = " + StringUtils.join(entry.getValue(), "|"));
            List<QuoteData> data = new ArrayList<QuoteData>();
            for (String fieldId : entry.getValue()) {
                final QuoteData cachedQuoteData =
                        getSubscriberController().getQuoteData(entry.getKey(), fieldId);
                if (cachedQuoteData != null &&
                        cachedQuoteData.getNssData().getReady()) {
                    data.add(cachedQuoteData);
                } else {
                    // final DecoderInfo decoder = decoderConfig[fieldId];
                    // if (decoder.composite) {
                    // } else {
                    // }
                }
            }
            subscriber.informUpdate(data);
            // getSubscriberController().fetchQuoteDataFromCache(subscriber, code, fieldIds, snap);
        }

        log.info("end");

        log.info("will subscribe from nss:");
        // use server field ids to subscribe
        List<String> fieldList = new ArrayList<String>();
        for (String fid : _fields) {
            final DecoderInfo decoder = decoderConfig.get(fid);
            final List<String> serverFieldIds = decoder.getServerFieldIds();
            if (serverFieldIds != null && !serverFieldIds.isEmpty()) {
                for (String serverFieldId : serverFieldIds) {
                    if (!fieldList.contains(serverFieldId)) {
                        fieldList.add(serverFieldId);
                    }
                }
            } else {
                fieldList.add(fid);
            }
        }
        log.info("end");

        log.info("ready to send nss command");
        log.info("[FU] = Full set of code-field, [SE] = Partial code-field");
        // Common code-fields
        if (willSubscribeCodes.size() > 0) {
            // Command controller
            log.info(
                    "[FU]  " + StringUtils.join(willSubscribeCodes, "|") + " = " + StringUtils.join(_fields, "|"));
            getNssCoreContext()
                    .getController()
                    .getCommandController()
                    .sendAddQuoteCommand(
                            getRequestId(), willSubscribeCodes, fieldList, _commandId);
        }
        if (willSeperateSubscribe.size() > 0) {
            // Each code-fields will send seperately
            for (Map.Entry<String, List<String>> entry : willSeperateSubscribe.entrySet()) {
                log.info(" [SE] " + entry.getKey() + " = " + StringUtils.join(entry.getValue(), ("|")));
                getNssCoreContext()
                        .getController()
                        .getCommandController()
                        .sendAddQuoteCommand(getRequestId(), Arrays.asList(entry.getKey()), entry.getValue(),
                                _commandId);
            }
        }
    }

    @Override
    public void unsubscribe(Subscriber subscriber) {
        List<String> willUnsubscribeCodes = new ArrayList<String>();
        Map<String, List<String>> willSeperateUnsubscribe = new HashMap<String, List<String>>();
        final Map<String, DecoderInfo> decoderConfig =
                getNssCoreContext().getDecoderConfig().getConfig();

        final boolean snap = _commandId == COMMAND_ADD_SNAPSHOT;
        for (String code : _codes) {
            List<String> willUnSubscribeFieldsInThisLoop = new ArrayList<String>();
            for (String fieldId : _fields) {
                // remove struct
                int count = getSubscriberController()
                        .removeQuoteSubscriber(code, fieldId, subscriber);
                if (count == -1) {
                    // skip snapshot?
                } else if (count == 0) {
                    if (willUnSubscribeFieldsInThisLoop.contains(fieldId)) {
                        willUnSubscribeFieldsInThisLoop
                                .add(fieldId); // need send remove cmd
                    } else {
                        final DecoderInfo decoder = decoderConfig.get(fieldId);
                        if (decoder.isComposite()) {
                            final List<String> serverFieldIds = decoder.getServerFieldIds();
                            for (String serverFieldID : serverFieldIds) {
                                if (getSubscriberController()
                                        .hasQuoteData(code, serverFieldID)) {
                                    final QuoteData quoteData = getSubscriberController()
                                            .getQuoteData(code, serverFieldID);
                                    final int depsCount = quoteData.minusCompositeDepsCount();
                                    // if no composite field depends on this server field and no subscriber listen to
                                    // this
                                    // server
                                    // field
                                    // e.g. 82S1 and I60S deps on server field id 161
                                    if (depsCount == 0 &&
                                            quoteData.getSubscription().size() == 0) {
                                        // we could remove subscription from server e.g. server field 161
                                        willUnSubscribeFieldsInThisLoop.add(fieldId);
                                    }
                                } else {
                                    // should be an error, as we have to add deps server field to quote cache in
                                    // subscribe()
                                }
                            }
                        } else {
                            willUnSubscribeFieldsInThisLoop
                                    .add(fieldId); // need send remove cmd
                        }
                    }
                }
            }
            if (willUnSubscribeFieldsInThisLoop.size() != _fields.size()) {
                // some no nned to be unsubscribed
                if (willSeperateUnsubscribe.get(code) != null) {
                    willSeperateUnsubscribe.put(code, new ArrayList<String>());

                }
                willSeperateUnsubscribe.get(code).addAll(willUnSubscribeFieldsInThisLoop);
            } else {
                willUnsubscribeCodes.add(code);
            }
        }
        if (!snap) {
            log.info("will unsubscribe from nss");
            // Common code-fields
            if (willUnsubscribeCodes.size() > 0) {
                // Command controller
                final String codes = StringUtils.join(willUnsubscribeCodes, "|");
                final String fields = StringUtils.join(_fields, "|");
                if (!codes.isEmpty() && !fields.isEmpty()) {
                    log.info("    - " + codes + " = " + fields);
                    final CommandController commandController =
                            getNssCoreContext().getController().getCommandController();
                    commandController.sendRemoveQuoteCommand(
                            getRequestId(), willUnsubscribeCodes, _fields);
                } else {
                    log.info("codes or fields are empty");
                }
            }
            if (willSeperateUnsubscribe.size() > 0) {
                // Each code-fields will send seperately
                for (Map.Entry<String, List<String>> entry : willSeperateUnsubscribe.entrySet()) {
                    if (!entry.getKey().isEmpty() && entry.getValue().size() > 0) {
                        log.info("    - " + entry.getKey() + " = " + StringUtils.join(entry.getValue(), "|"));
                        getNssCoreContext()
                                .getController()
                                .getCommandController()
                                .sendRemoveQuoteCommand(getRequestId(), Arrays.asList(entry.getKey()),
                                        entry.getValue());
                    }
                }
            }
        } else {
            log.info("skill unsubscribe command in snapshot request");
        }
        log.info("done");
        // detect storage!
    }

    public List<String> convertToServerFieldIds(List<String> fieldIDs) {
        final List<String> fieldList = new ArrayList<String>();
        final Map<String, DecoderInfo> decoderConfig =
                getNssCoreContext().getDecoderConfig().getConfig();
        for (String fid : fieldIDs){
            final DecoderInfo decoder = decoderConfig.get(fid);
            final List<String> serverFieldIds = decoder.getServerFieldIds();
            if (serverFieldIds != null && !serverFieldIds.isEmpty()) {
                for (String serverFieldId : serverFieldIds){
                    if (!fieldList.contains(serverFieldId)){
                        fieldList.add(serverFieldId);
                    }
                }
            } else {
                fieldList.add(fid);
            }
        }
        return fieldList;
    }
}
