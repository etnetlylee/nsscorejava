package coreSubscriber.request;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import coreSubscriber.Subscriber;
import util.DecodeHelper;

import static constants.Command.COMMAND_ADD_BOTH;

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
        for (String code : _codes){
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
        List<String> willSubscribeCodes = [];
        // List<String> willSubscribeFields = _fields;
        Map<String, List<String>> willSeperateSubscribe = {};
        Map<String, List<String>> willFetchFromCache = {};
        Map<String, List<String>> willWaitFromCache = {};
        final Map<String, DecoderInfo> decoderConfig =
                getNssCoreContext().getDecoderConfig().getConfig();

        if (_codes == null || _codes.length == 0) {
            return null;
        }
        if (_fields == null || _fields.length == 0) {
            return null;
        }

        final snap =_commandId == COMMAND_ADD_SNAPSHOT;

        _codes.forEach((code) {
                List < String > willSubscribeFieldsInThisLoop =[];
        _fields.forEach((field) {
                bool isSubscribed = getSubscriberController().hasQuoteData(code, field);
        // add it immediately
        getSubscriberController()
                .addQuoteSubscriber(code, field, subscriber, snap);
        if (isSubscribed) {
            // from cache?
            NssData nssData =
                    getSubscriberController().getQuoteData(code, field).getNssData();
            bool isDataReady = nssData.getReady();
            if (isDataReady) {
                // update From cache directly
                willFetchFromCache.putIfAbsent(code, () = > []);
                willFetchFromCache[code].add(field);
            } else {
                // wait data return
                willWaitFromCache.putIfAbsent(code, () = > []);
                willWaitFromCache[code].add(field);
            }
        } else {
            // from nss
            final DecoderInfo decoder = decoderConfig[field];
            final List<String> serverFieldIds = decoder.getServerFieldIds();
            if (serverFieldIds != null) {
                if (decoder.isComposite()) {
                    bool allServerFieldSubscribed = true;
                    serverFieldIds.forEach((serverFieldId) {
                    final bool isServerFieldIdSubscribed =
                            getSubscriberController().hasQuoteData(code, serverFieldId);
                    if (isServerFieldIdSubscribed) {
                        this
                                .getSubscriberController()
                                .addCompositeDeps(code, serverFieldId);
                        // would like to trigger decoder again...
                        willSubscribeFieldsInThisLoop.add(serverFieldId);
                    } else {
                        final quoteData =getSubscriberController()
                                .createQuoteData(code, serverFieldId);
                        quoteData.plusCompositeDepsCount();
                        allServerFieldSubscribed = false;
                        willSubscribeFieldsInThisLoop.add(serverFieldId);
                    }
              });
                } else {
                    willSubscribeFieldsInThisLoop.add(field);
                }
            } else {
                willSubscribeFieldsInThisLoop.add(field);
            }
        }
      });

        if (willSubscribeFieldsInThisLoop.length == _fields.length) {
            // new code
            willSubscribeCodes.add(code);
        } else {
            if (willSubscribeFieldsInThisLoop.length > 0) {
                willSeperateSubscribe.putIfAbsent(code, () = > []);
                willSeperateSubscribe[code].addAll(willSubscribeFieldsInThisLoop);
            }
        }
    });

        log.info("will wait cache ready:");
        willWaitFromCache.forEach((String code, List < String > fieldIds) {
            log.info("    - " + code + " = " + fieldIds.join("|"));
        });
        log.info("end");

        log.info("will fetch from cache:");
        willFetchFromCache.forEach((String code, List < String > fieldIds) {
            log.info("    - " + code + " = " + fieldIds.join("|"));
            List<QuoteData> data = [];
            fieldIds.forEach((fieldId) {
            final cachedQuoteData =
            getSubscriberController().getQuoteData(code, fieldId);
            if (cachedQuoteData != null &&
                    cachedQuoteData.getNssData().getReady()) {
                data.add(cachedQuoteData);
            } else {
                // final DecoderInfo decoder = decoderConfig[fieldId];
                // if (decoder.composite) {
                // } else {
                // }
            }
      });
            subscriber.informUpdate(data);
            // getSubscriberController().fetchQuoteDataFromCache(subscriber, code, fieldIds, snap);
        });
        log.info("end");

        log.info("will subscribe from nss:");
        // use server field ids to subscribe
        List<String> fieldList = [];
        _fields.forEach((fid) {
        final DecoderInfo decoder = decoderConfig[fid];
        final List<String> serverFieldIds = decoder.getServerFieldIds();
        if (serverFieldIds != null && serverFieldIds.isNotEmpty) {
            serverFieldIds.forEach((serverFieldId) {
            if (!fieldList.contains(serverFieldId)) {
                fieldList.add(serverFieldId);
            }
        });
        } else {
            fieldList.add(fid);
        }
    });
        log.info("end");

        log.info("ready to send nss command");
        log.info("[FU] = Full set of code-field, [SE] = Partial code-field");
        // Common code-fields
        if (willSubscribeCodes.length > 0) {
            // Command controller
            log.info(
                    "[FU]  " + willSubscribeCodes.join("|") + " = " + _fields.join("|"));
            getNssCoreContext()
                    .getController()
                    .getCommandController()
                    .sendAddQuoteCommand(
                            getRequestId(), willSubscribeCodes, fieldList, _commandId);
        }
        if (willSeperateSubscribe.length > 0) {
            // Each code-fields will send seperately
            willSeperateSubscribe.forEach((String code, List < String > fieldIds) {
                log.info(" [SE] " + code + " = " + fieldIds.join("|"));
                getNssCoreContext()
                        .getController()
                        .getCommandController()
                        .sendAddQuoteCommand(getRequestId(),[code], fieldIds, _commandId);
            });
        }

        return null;
    }

    @override
    unsubscribe(Subscriber subscriber) {
        List<String> willUnsubscribeCodes = [];
        Map<String, List<String>> willSeperateUnsubscribe = {};
        final Map<String, DecoderInfo> decoderConfig =
                getNssCoreContext().getDecoderConfig().getConfig();

        final snap =_commandId == COMMAND_ADD_SNAPSHOT;
        _codes.forEach((code) {
                List < String > willUnSubscribeFieldsInThisLoop =[];
        _fields.forEach((fieldId) {
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
                final DecoderInfo decoder = decoderConfig[fieldId];
                if (decoder.isComposite()) {
                    final List<String> serverFieldIds = decoder.getServerFieldIds();
                    serverFieldIds.forEach((serverFieldID) {
                    if (getSubscriberController()
                            .hasQuoteData(code, serverFieldID)) {
                        final QuoteData quoteData = getSubscriberController()
                                .getQuoteData(code, serverFieldID);
                        final int depsCount = quoteData.minusCompositeDepsCount();
                        // if no composite field depends on this server field and no subscriber listen to this server
                    // field
                        // e.g. 82S1 and I60S deps on server field id 161
                        if (depsCount == 0 &&
                                quoteData.getSubscription().length == 0) {
                            // we could remove subscription from server e.g. server field 161
                            willUnSubscribeFieldsInThisLoop.add(fieldId);
                        }
                    } else {
                        // should be an error, as we have to add deps server field to quote cache in subscribe()
                    }
              });
                } else {
                    willUnSubscribeFieldsInThisLoop
                            .add(fieldId); // need send remove cmd
                }
            }
        }
      });

        if (willUnSubscribeFieldsInThisLoop.length != _fields.length) {
            // some no nned to be unsubscribed
            willSeperateUnsubscribe.putIfAbsent(code, () = > []);
            willSeperateUnsubscribe[code].addAll(willUnSubscribeFieldsInThisLoop);
        } else {
            willUnsubscribeCodes.add(code);
        }
    });

        if (!snap) {
            log.info("will unsubscribe from nss");
            // Common code-fields
            if (willUnsubscribeCodes.length > 0) {
                // Command controller
                final codes =willUnsubscribeCodes.join("|");
                final fields =_fields.join("|");
                if (codes.isNotEmpty && fields.isNotEmpty) {
                    log.info("    - " + codes + " = " + fields);
                    final commandController =
                    getNssCoreContext().getController().getCommandController();
                    commandController.sendRemoveQuoteCommand(
                            getRequestId(), willUnsubscribeCodes, _fields);
                } else {
                    log.info("codes or fields are empty");
                }
            }
            if (willSeperateUnsubscribe.length > 0) {
                // Each code-fields will send seperately
                willSeperateUnsubscribe.forEach((String code, List < String > fieldIds) {
                    if (code.isNotEmpty && fieldIds.length > 0) {
                        log.info("    - " + code + " = " + fieldIds.join("|"));
                        getNssCoreContext()
                                .getController()
                                .getCommandController()
                                .sendRemoveQuoteCommand(getRequestId(),[code], fieldIds);
                    }
                });
            }
        } else {
            log.info("skill unsubscribe command in snapshot request");
        }
        log.info("done");
        // detect storage!
        return null;
    }

    List<String> convertToServerFieldIds(List<String> fieldIDs) {
        final fieldList = [];
        final Map<String, DecoderInfo> decoderConfig =
                getNssCoreContext().getDecoderConfig().getConfig();
        fieldIDs.forEach((fid) {
        final DecoderInfo decoder = decoderConfig[fid];
        final List<String> serverFieldIds = decoder.getServerFieldIds();
        if (serverFieldIds != null && serverFieldIds.isNotEmpty) {
            fieldList.addAll(serverFieldIds
                    .where((serverFieldId) = > !fieldList.contains(serverFieldId)));
        } else {
            fieldList.add(fid);
        }
    });
        return fieldList;
    }
}
