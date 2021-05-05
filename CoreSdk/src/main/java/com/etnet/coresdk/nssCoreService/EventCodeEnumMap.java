package com.etnet.coresdk.nssCoreService;

import java.util.HashMap;
import java.util.Map;

public class EventCodeEnumMap {
    final Map<Integer, String> eventCodeEnumMap = new HashMap<Integer, String>() {{
        put(200, "LoginSuccess");
        put(403, "HttpLoginFailed");
        put(407, "NssLoginFailed");
        put(701, "NssLoginDuplicated");
        put(702, "NssLoginUnknown");
        put(703, "NssConnected");
        put(704, "NssDisconnect");
        put(705, "NssMaintenance");
        put(706, "NssHeartbeatTimeout");
        put(707, "NssTime");
        put(708, "NssAsaLoad");
        put(709, "NssAsaProgress");
        put(710, "NssCleanup");
        put(711, "NssHeartbeat");
        put(712, "NssReconnect");
        put(713, "NssNewsLoad");
        put(714, "NssData");
    }};
}
