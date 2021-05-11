package com.etnet.coresdk.coreNetwork;

import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.etnet.coresdk.coreController.ProcessorController;
import com.etnet.coresdk.coreInterfaceNetwork.Connection;
import com.etnet.coresdk.coreInterfaceNetwork.ConnectionHandler;
import com.etnet.coresdk.coreInterfaceNetwork.NssCallback;

import com.etnet.coresdk.coreModel.NssCoreContext;
import com.etnet.coresdk.network.ConnectOptions;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

// todo need to discuss : Robin
public class NssConnection extends Connection implements NssCallback {
    final Logger log = Logger.getLogger("NssConnection");
    WebSocket ws = null;
    boolean _wsError = false;
    boolean _paused = false;
    int _retryCount;
    int _currentRetry;
    List<List<Integer>> _retryStrategy;
    NssCoreContext _context;
    ConnectionHandler _handler;

    boolean _isConnected = false;
    ConnectOptions _lastConnectOptions = null;

    public NssConnection() {
        _retryCount = 5;
        _currentRetry = 0;
    }


    @Override
    public boolean isConnected() {
        return ws.isOpen();
    }

    @Override
    public void resume() {
        _paused = false;
        if (ws.isOpen()) {
            boolean closed = !isConnected();
            if (closed) {
                log.info("Connection to NSS");
                connect(null);
            } else {
                log.info("connection is avaiable");
            }
        }
    }

    @Override
    public void pause() {
        _paused = true;
        if (isConnected()) {
            log.info("paused, disconnecting from NSS");
            ws.sendClose(4000, "pause connection");
        }
    }

    @Override
    public boolean isPaused() {
        return _paused;
    }

    @Override
    public Connection retryCount(int maxCount) {
        _retryCount = maxCount;
        return this;
    }

    @Override
    public Connection withRetryStrategy(List<List<Integer>> strategy) {
        _retryStrategy = strategy;
        return this;
    }

    @Override
    public void connect(ConnectOptions options) {
        log.info("start connecting to nss");
        if (options == null && _lastConnectOptions != null) {
            // options is null, so we get last connect options.
            options = _lastConnectOptions;
            log.info("use last connect options");
        }

        if (_lastConnectOptions == null) {
            // first time connect will provide options
            _lastConnectOptions = options;
            log.info("save current connect options");
        }

        if (options.getUseRandomServer()) {
            log.info("use random wsip");
        } else {
            log.info("connect to default server");
            String nssUrl = options.getDefaultServer();
//            _createWebsocketInstance(nssUrl);
        }

        WebSocketFactory factory = new WebSocketFactory().setConnectionTimeout(5000);

        // Create a WebSocket. The timeout value set above is used.
        try {
            ws = factory.createSocket("wss://ws01.etnet.com.hk/websocket");

            ws.addListener(new WebSocketAdapter() {
                @Override
                public void onTextMessage(WebSocket websocket, String message) throws Exception {
                    System.out.println("[TAG] onTextMessage: " + message);
                }

                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {
                    System.out.println("[TAG] onBinarytMessage length: " + binary.length);

                    String str = new String(binary, StandardCharsets.UTF_8);
                    _context.getController().getProcessorController().process(str);
//                    ProcessorController pc = new ProcessorController();
//                    pc.process(str);
                    System.out.print(str);
                }

                public void onConnected(WebSocket websocket, Map<String, List<String>> headers) {
                    System.out.println("[TAG] onConnected " + headers);
                    // send sample text

                    ws.sendText("-1,8,0,1,%13v%3E%C3%BC%C2%8D%C2%86l%C3%B17hbf%12%C2%88M%C3%B1%C2%99%C3%BB%C2%81K%7F%22%0An,1");  // token

                    // request data
                    ws.sendText("-1,1,1,1,4,108");
                    ws.sendText("-1,1,2,1,5,108");
                    ws.sendText("-1,1,3,1,6,108");
                    ws.sendText("-1,1,4,2,7,108");
                    ws.sendText("-1,1,5,1,8,108");
                    ws.sendText("-1,1,6,1,9,108");
                    ws.sendText("-1,1,7,1,10,108");
                    ws.sendText("-1,1,8,1,11,108");
                    ws.sendText("-1,1,9,1,12,108");
                    ws.sendText("-1,1,10,2,17,108");
                    ws.sendText("-1,1,11,1,18,108");
                    ws.sendText("-1,1,12,1,19,108");
                    ws.sendText("-1,1,13,1,25,108");
                    ws.sendText("-1,1,14,1,29,108");
                    ws.sendText("-1,1,15,1,88,108");
                    ws.sendText("-1,1,16,1,31,108");
                    ws.sendText("-1,1,17,2,RFR,108");
                    ws.sendText("-1,1,18,1,36,108");
                    ws.sendText("-1,1,19,1,40,108");
                    ws.sendText("-1,1,20,1,83,108");
                    ws.sendText("-1,1,21,1,85,108");
                    ws.sendText("-1,1,22,1,87,108");
                    ws.sendText("-1,1,23,2,24,108");
                    ws.sendText("-1,1,24,1,92,108");
                    ws.sendText("-1,1,25,1,37,108");
                    ws.sendText("-1,1,26,1,38,108");
                    ws.sendText("-1,1,27,1,39,108");
                    ws.sendText("-1,1,28,1,41,108");
                    ws.sendText("-1,1,29,1,42,108");
                    ws.sendText("-1,1,30,1,60,108");
                    ws.sendText("-1,1,31,1,61,108");
                    ws.sendText("-1,1,32,1,62,108");
                    ws.sendText("-1,1,33,1,63,108");
                    ws.sendText("-1,1,34,1,65,108");
                    ws.sendText("-1,1,35,1,70,108");
                    ws.sendText("-1,1,36,1,71,108");
                    ws.sendText("-1,1,37,1,73,108");
                    ws.sendText("-1,1,38,1,75,108");
                    ws.sendText("-1,1,39,2,HSI,292");
                    ws.sendText("-1,9,40,2,293");
                    ws.sendText("-1,1,41,2,HSI.202105|HS1.202105,292|34|36|40|38|409");
                    ws.sendText("-1,1,42,2,HSI,34|36|40|38|409");
                    ws.sendText("-1,1,43,2,HSIS.AOI|MAIN,37|93|94|160|78|419");
                    ws.sendText("-1,1,44,2,GLOBAL.CS300|GLOBAL.SHCI,34|36|40");
                    ws.sendText("-1,1,45,2,GLOBAL.SHEA|GLOBAL.SHEB|GLOBAL.SZEA|GLOBAL.SZEB|GLOBAL.SZCI|GLOBAL.SZPI," +
                            "34|36|40");
                    ws.sendText("-1,1,46,2,HSIS.HSI,34|36|40|37");
                    ws.sendText("-1,1,47,2,HSIS.AOI,34|36|40");
                    ws.sendText("-1,1,48,2,HSIS.CEI,34|36|40");
                    ws.sendText("-1,1,49,2,700,34|40|36|327");
                    ws.sendText("-1,1,50,3,700,34|40|36|327");
                    ws.sendText("-1,1,51,2,700|200|24|1470|6618|9988|1,34|40|36|327");
                    ws.sendText("-1,1,52,2,700,531|429|423");
                    ws.sendText("-1,1,53,2,700,97");
                    ws.sendText("-1,1,54,2,700,85");
                    ws.sendText("-1,1,55,2,700,54|41|42|49|9|10|11|12");
                    ws.sendText("-1,1,56,2,700,38|37|39|84|86|50|51|5|6|7|18|43|57|55|56|17|41|42|49|58|59|241|209" +
                            "|210|45|44|78|420|419|227|8|418|406|407");
                    ws.sendText("-1,1,57,2,700,424|425|426|427|435|530|529|528|527|427|526|525|422");
                    ws.sendText("-1,1,58,2,700,53|52|286|234|201");
                    ws.sendText("-1,1,59,2,700,87");
                    ws.sendText("-1,1,60,2,700,161");
                    ws.sendText("-1,1,61,2,700,88");
                    ws.sendText("-1,1,62,2,700,2|4|3|315");
                    ws.sendText("-1,1,63,2,700,421|80|219|220|221|222|223");
                    ws.sendText("-1,1,64,2,700,90|91|319|437|107");
                    ws.sendText("-1,1,65,0,700,161");
                    ws.sendText("-1,1,66,0,9988,161");
                    ws.sendText("-1,1,67,2,200,41|42|9|10|11|12|421|80|219|220|221|222|223|315|286|525|531|435|429" +
                            "|423|422");
                    ws.sendText("-1,1,68,2,24,41|42|9|10|11|12|421|80|219|220|221|222|223|315|286|525|531|435|429|423" +
                            "|422");
                    ws.sendText("-1,1,69,2,1470,41|42|9|10|11|12|421|80|219|220|221|222|223|315|286|525|531|435|429" +
                            "|423|422");
                    ws.sendText("-1,1,70,2,6618,41|42|9|10|11|12|421|80|219|220|221|222|223|315|286|525|531|435|429" +
                            "|423|422");
                    ws.sendText("-1,1,71,2,9988,41|42|9|10|11|12|421|80|219|220|221|222|223|315|286|525|531|435|429" +
                            "|423|422");
                    ws.sendText("-1,1,72,2,1,41|42|9|10|11|12|421|80|219|220|221|222|223|315|286|525|531|435|429|423" +
                            "|422");
                    ws.sendText("-1,1,73,2,700,205|206|207|208|83");
                    ws.sendText("-1,1,74,2,7002,160");

                }
            });

            ws.connectAsynchronously();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void disconnect(int code, String reason) {
        if (ws.isOpen()) {
            ws.sendClose(code, reason);
        }
    }

    @Override
    public void reconnect() {
        int delay = 1000;
        _wsError = false;
        if (_retryStrategy != null) {
            log.info("retry strategy found");
            for (int i = 0; i < _retryStrategy.size(); i++) {
                if (_currentRetry <= _retryStrategy.get(i).get(0)) {
                    delay = _retryStrategy.get(i).get(1);
                } else {
                    break;
                }
            }
            log.info("retry strategy delay=" + delay);
            if (_currentRetry <= _retryCount && delay > -1) {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        connect(null);
                    }
                }, delay);
            } else {
                log.info("end of retry");
            }
        } else {
            log.info("no strategy found delay=" + delay);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    connect(null);
                }
            }, delay);
        }
    }

    @Override
    public void send(String cmd) {

    }

    @Override
    public void setHandler(ConnectionHandler handler) {

    }

    @Override
    public void onOpen() {
        log.info("nss socket open");
        _currentRetry = 0;
        _wsError = false;
        _isConnected = true;
        _handler.onConnected();
    }

    @Override
    public void onClose() {

    }

    @Override
    public void onMessage(Object data) {
        try {
            if (data instanceof byte[]) {
                String utf8Data = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    utf8Data = new String((byte[]) data, StandardCharsets.UTF_8);
                }
                if (_handler != null) {
                    _handler.onDataReceived(utf8Data);
                } else {
                    log.info("orphaned nss connection recv data");
                }
            }
        } catch ( Exception e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void onError(Object error, StackTraceElement stackTrace) {
        _wsError = true;
        log.info("close onError !");
        if (ws == null) {
            log.info("fail to do initial connection");
            _retryConnectionWithStrategy();
        } else if (!ws.isOpen()) {
            log.info("come from close event");
        }
    }

    public void _retryConnectionWithStrategy() {
        if (++_currentRetry <= _retryCount) {
            log.info("still have changes to recover, start re-connecting..." +
                    _currentRetry +
                    " <= " +
                    _retryCount);
            // wait lastMessage
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    log.info("timer task: start re-connecting");
                    if (_handler != null) {
                        if (_wsError || !_handler.isSessionEnd()) {
                            log.info(
                                    "timer task: user disconnected, but not kick-off by server actively");
                            if (++_currentRetry <= _retryCount) {
                                log.info("timer task: reconnecting... " +
                                        _currentRetry+
                                        " / " +
                                        _retryCount);
                                reconnect();
                            } else {
                                log.info("timer task: no retry connection");
                            }
                        } else if (_handler.isSessionEnd()) {
                            log.info("timer task: user session ended");
                        } else {
                            log.info("timer task: no reconnect, end");
                        }
                    } else {
                        log.info(
                                "timer task: orphaned nss connection should be terminated peacefully");
                    }
                }
            }, 2000);
        } else {
            log.info("no retry as retry count reach maximium");
        }
    }
}
