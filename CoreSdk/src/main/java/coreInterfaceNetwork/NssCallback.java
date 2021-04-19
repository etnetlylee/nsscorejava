package coreInterfaceNetwork;

public interface NssCallback {
    public void onOpen();
    public void onClose();
    public void onMessage(Object data);
    public void onError(Object error, StackTraceElement stackTrace);
}
