package coreCommand;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInput;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import api.ContextProvider;
import coreInterfaceLogin.LoginResponse;
import coreModel.NssCoreContext;
import events.UserEvent;
import okhttp3.Response;
import util.CommandBuilder;

import static constants.HttpParam.HTTPPARAM_COMPCODE;
import static constants.HttpParam.HTTPPARAM_ENCRYPT;
import static constants.HttpParam.HTTPPARAM_LOGINNAME;
import static constants.HttpParam.HTTPPARAM_PASSWORD;
import static constants.HttpParam.HTTPPARAM_PRODNAME;
import static constants.RequestId.REQID_LOGIN;

public class LoginCommand extends ContextProvider {
    final Logger log = Logger.getLogger("LoginCommand");

    NssCoreContext _context;
    Map<String, String> _params = new HashMap<String, String>();

    @Override
    public void setContext(NssCoreContext context) {
        this._context = context;
    }

    public void sendLoginCommand(String token) {
        int seqNo = _context.getStorage().getNextSeqNo();
        CommandBuilder cmd = new CommandBuilder(REQID_LOGIN, seqNo);
        cmd.append("1");
        cmd.append(token);
        cmd.append("1" /*1=IQ, 2=HV2*/);

        log.info("send login command" + cmd.toString().trim());
        _context.getController().getNetworkController().send(cmd.toString());
    }

    public void sendIntegratedLoginCommand(String username, String token, Object result) {
        // todo ： need to discuss involved EventBus
//        result.username = username;
//        this._context.events.fire(new UserEvent(UserEvent.HttpLoginSuccess, result));
    }

    public void sendHttpLoginCommand(String username, String password, boolean encrypted) throws Exception {
        try {
            if (username.isEmpty() || password.isEmpty()) {
                throw new Exception("Username or password are empty.");
            }

            String encrypt = encrypted ? "y" : "n";
            String urlParam = "?";
            _params.put(HTTPPARAM_LOGINNAME, username);
            _params.put(HTTPPARAM_PASSWORD, password);
            _params.put(HTTPPARAM_PRODNAME, _context.getConfig().getProductName());
            _params.put(HTTPPARAM_ENCRYPT, encrypt);
            _params.put(HTTPPARAM_COMPCODE, _context.getConfig().getCorpName());


            for (Map.Entry<String, String> entry : _params.entrySet()) {
                urlParam += entry.getKey() + "=" + entry.getValue() + "&";
            }

            log.info("login params: ${_params}");

            String url = _context.getConfig().getServer().getUms() +
                    _context.getConfig().getApiEndpoints().get("LoginUrl") +
                    urlParam;
            Response result = _context
                    .getController()
                    .getNetworkController()
                    .sendHttpGetRequest(url);
            if (result.code() == 200){
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> jsonResult = mapper.readValue((DataInput) result.body(), Map.class);
                        LoginResponse response = LoginResponse.fromJson(jsonResult);
                        if (response.isValid()) {
//                            _context.events
//                                    .fire(UserEvent(UserEvent.HttpLoginSuccess, response));
                        } else {
//                            _context.events.fire(UserEvent(UserEvent.HttpLoginFailed, null));
                        }
            }
//                    .then((Response result) {
//                try {
//                    if (result.statusCode == 200) {
//                        Map<String, dynamic> jsonResult = json.decode(result.body);
//                        LoginResponse response = LoginResponse.fromJson(jsonResult);
//                        if (response.isValid()) {
//                            _context.events
//                                    .fire(UserEvent(UserEvent.HttpLoginSuccess, response));
//                        } else {
//                            _context.events.fire(UserEvent(UserEvent.HttpLoginFailed, null));
//                        }
//                    } else {
//                        _context.events.fire(UserEvent(UserEvent.HttpLoginFailed, null));
//                    }
//                } catch (e) {
//                    log.info(e);
//                }
//            }).catchError((error) {
//                    log.info(error);
//            if (error is SocketException){
//                log.info("SocketException");
//            }
//            _context.events.fire(UserEvent(UserEvent.HttpLoginFailed, null));
//      });
        } catch (Exception e) {
            log.info(e.getMessage());
//            _context.events.fire(UserEvent(UserEvent.HttpLoginFailed, null));
        }
    }

    public void setNssCoreContext(NssCoreContext nssCoreContext) {
        _context = nssCoreContext;
    }
}
