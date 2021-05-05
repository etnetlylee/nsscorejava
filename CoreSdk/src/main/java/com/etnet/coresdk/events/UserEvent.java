package com.etnet.coresdk.events;

public class UserEvent extends AppEvent {
    public static int HttpLoginSuccess = 1;
    public static int HttpLoginFailed = 2;
    public static int NssLoginSucess = 3;
    public static int NssLoginFailed = 4;
    public static int NssLoginDuplicated = 5;
    public static int NssLoginUnknown = 9999;

    public UserEvent(int event, Object data) {
        super(event, data);
    };
}
