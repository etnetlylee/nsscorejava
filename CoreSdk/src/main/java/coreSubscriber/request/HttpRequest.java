package coreSubscriber.request;

import java.util.Calendar;
import java.util.Map;
import java.util.logging.Logger;

import coreSubscriber.SubscriberJava;

public class HttpRequest extends Request {
    final Logger log = Logger.getLogger("HttpRequest");

    String _code;
    String _field;
    Map<String, String> _params;
    String _endPoint;

    public HttpRequest(
            String endPoint, String code, String field, Map<String, String> params) {
        super(0, 0, 0);
        this._endPoint = endPoint;
        this._code = code;
        this._field = field;
        this._params = params;
    }

    public String getCode() {
        return _code;
    }

    public String getFieldID() {
        return _field;
    }

    public Map<String, String> getParams() {
        return _params;
    }

    @Override
    public void subscribe(SubscriberJava subscriberJava) {
        log.info("always create new http request, even using the same params");
        String apiUrl = getNssCoreContext().getConfig().getServer().getWeb() + _endPoint;
        String urlParam = "?";
        _params.put("uid", getNssCoreContext().getUser().getUserId());
        _params.put("token", getNssCoreContext().getUser().getWebToken());

        // force load with timestamp as url parameter
        Calendar t = Calendar.getInstance();
        _params.put("_t", String.valueOf(t.getTimeInMillis()));


        for (Map.Entry<String, String> entry : _params.entrySet()) {
            urlParam += entry.getKey() + "=" + entry.getValue() + "&";
        }

        getNssCoreContext()
                .getController()
                .getCommandController()
                .sendHttpGetRequest(
                        apiUrl + urlParam, this.getCode(), this.getFieldID(), subscriberJava);
    }

    @Override
    public void unsubscribe(SubscriberJava subscriberJava) {
        log.info("http request do not need to unsubscribe");
    }
}
