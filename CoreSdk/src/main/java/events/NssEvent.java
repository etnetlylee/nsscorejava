package events;

public class NssEvent extends AppEvent {
    public static int NssConnect = 1;
    public static int NssDisconnect = 2;
    public static int NssMaintenance = 3;
    public static int NssHeartbeatTimeout = 4;
    public static int NssTime = 5;
    public static int NssAsaLoad = 6;
    public static int NssAsaProgress = 7;
    public static int NssCleanup = 8;
    public static int NssHeartbeat = 9;
    public static int NssReconnect = 10;
    public static int NssNewsLoad = 11;
    public static int NssData = 99;

    public NssEvent(int event, Object data) {
        super(event, data);
    }
}
