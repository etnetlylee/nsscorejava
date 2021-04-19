package language;

import java.util.HashMap;
import java.util.Map;

public class Languages {
    public static final Map<String, String> LangEn  = new HashMap<String, String>() {{
        put("LINK_NO", "No Link");
        put("LINK_ALL", "Link All");
        put("LINK_RED", "Red");
        put("LOGIN_SUCCESS", "Core login success");
        put("CORE_INIT", "WebIQ Initializing...");
        put("ASA_INIT", "Asa Initializing...");
        put("ASA_FUTURE_NAME", "Future");
        put("ASA_OPTION_NAME", "Option");
    }};

    public static final Map<String, String> LangSc  = new HashMap<String, String>() {{
        put("LINK_NO", "沒有連繫");
        put("LINK_ALL", "连系所有");
        put("LINK_RED", "红色连系");
        put("LOGIN_SUCCESS", "成功登入系统");
        put("CORE_INIT", "系统核心初始化...");
        put("ASA_INIT", "基础数据初始化...");
        put("ASA_FUTURE_NAME", "期货");
        put("ASA_OPTION_NAME", "期权");
    }};

    public static final Map<String, String> LangTc  = new HashMap<String, String>() {{
        put("LINK_NO", "沒有連繫");
        put("LINK_ALL", "連繫所有");
        put("LINK_RED", "紅色連繫");
        put("LOGIN_SUCCESS", "成功登入系統");
        put("CORE_INIT", "系統核心初始化...");
        put("ASA_INIT", "基礎數據初始化...");
        put("ASA_FUTURE_NAME", "期貨");
        put("ASA_OPTION_NAME", "期權");
    }};

}
