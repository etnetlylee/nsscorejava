package coreNetwork;

import coreController.ProcessorController;
import coreInterfaceNetwork.NssCallback;

public class HttpConnection implements NssCallback {
    ProcessorController processorCtrl;

    @Override
    public void onClose() {
        try {
            throw new Exception("[NssCore][BmpConnection] Method not implemented.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Object error, StackTraceElement stackTrace) {
        try {
            throw new Exception("[NssCore][BmpConnection] Method not implemented.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Object data) {
        try {
            throw new Exception("[NssCore][BmpConnection] Method not implemented.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen() {
        try {
            throw new Exception("[NssCore][BmpConnection] Method not implemented.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

