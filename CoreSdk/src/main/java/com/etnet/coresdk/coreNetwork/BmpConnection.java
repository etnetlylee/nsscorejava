package com.etnet.coresdk.coreNetwork;

import com.etnet.coresdk.coreController.ProcessorController;
import com.etnet.coresdk.coreInterfaceNetwork.NssCallback;

public class BmpConnection implements NssCallback {
    ProcessorController processorCtrl;

    @Override
    public void onClose() {
        try {
            throw new Exception("[com.etnet.coresdk.NssCore][BmpConnection] Method not implemented.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Object error, StackTraceElement stackTrace) {
        try {
            throw new Exception("[com.etnet.coresdk.NssCore][BmpConnection] Method not implemented.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(Object data) {
        try {
            throw new Exception("[com.etnet.coresdk.NssCore][BmpConnection] Method not implemented.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen() {
        try {
            throw new Exception("[com.etnet.coresdk.NssCore][BmpConnection] Method not implemented.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

