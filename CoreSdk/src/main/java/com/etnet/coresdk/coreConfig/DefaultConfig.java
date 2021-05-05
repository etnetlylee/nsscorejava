package com.etnet.coresdk.coreConfig;

import java.util.HashMap;

import com.etnet.coresdk.constants.EndPoints;
import com.etnet.coresdk.coreConfigInterface.CoreConfig;
import com.etnet.coresdk.coreConfigInterface.InitiatorConfig;
import com.etnet.coresdk.coreConfigInterface.ServerUrls;

public class DefaultConfig {
    public final CoreConfig DefaultConfig = new CoreConfig(
            "H5", // productName
            "TC", // lang:TC|SC|EN
            new ServerUrls(
                    "https://ws01.etnet.com.hk/websocket", // wss
                    "https://iq6pilot.etnet.com.hk", //web
                    "https://iq6pilot.etnet.com.hk" // ums
            ),
            new InitiatorConfig(
                    true, //useASA
                    true //useNews
            ),
            "HK", // region
            false, // enableCensor
            new HashMap<String, String>() {{
                put("Login", EndPoints.ENDPOINT_LOGIN);
                put("IQServlet", EndPoints.ENDPOINT_IQSERVLET);
                put("FileServer", EndPoints.ENDPOINT_FILESERVER);
                put("TransSummaryServlet", EndPoints.ENDPOINT_TRANSSUMMARYSERVLET);
                put("TransExServlet", EndPoints.ENDPOINT_TRANSEXSERVLET);
                put("BrokerServlet", EndPoints.ENDPOINT_BROKERSERVLET);
                put("SecServlet", EndPoints.ENDPOINT_SECSERVLET);
                put("IndexServlet", EndPoints.ENDPOINT_INDEXSERVLET);
                put("FutServlet", EndPoints.ENDPOINT_FUTSERVLET);
                put("ChartWarServlet", EndPoints.ENDPOINT_CHARTWARSERVLET);
                put("QuotaServlet", EndPoints.ENDPOINT_QUOTASERVLET);
                put("Top10StockConnectTurnoverServlet", EndPoints.ENDPOINT_TOP10STOCKCONNECTTURNOVERSERVLET);
                put("HV2Login", EndPoints.ENDPOINT_HV2LOGIN);
                put("NewsSearch", EndPoints.ENDPOINT_NEWSSEARCH);
                put("GetNewsMarking", EndPoints.ENDPOINT_GETNEWSMARKING);
                put("FutTransServlet", EndPoints.ENDPOINT_FUTTRANSSERVLET);
                put("ChartServlet", EndPoints.ENDPOINT_CHARTSERVLET);
                put("GetSENews", EndPoints.ENDPOINT_GETSENEWS);
                put("GetMarketTips", EndPoints.ENDPOINT_GETMARKETTIPS);
                put("GetNews", EndPoints.ENDPOINT_GETNEWS);
                put("GetNewsContent", EndPoints.ENDPOINT_GETNEWSCONTENT);
                put("ShortSellServlet", EndPoints.ENDPOINT_SHORTSELLSERVLET);
                put("MoneyFlowServlet", EndPoints.ENDPOINT_MONEYFLOWSERVLET);
                // port from IQv7.9,
                put("PortfolioList", EndPoints.ENDPOINT_PORTFOLIOLIST);
                put("PortfolioMemberList", EndPoints.ENDPOINT_PORTFOLIOMEMBERLIST);
                put("PortfolioServlet", EndPoints.ENDPOINT_PORTFOLIOSERVLET);
                put("AddPortfolioMember", EndPoints.ENDPOINT_ADDPORTFOLIOMEMBER);
                put("UpdatePortfolioMember", EndPoints.ENDPOINT_UPDATEPORTFOLIOMEMBER);
                put("UpdatePortfolioCode", EndPoints.ENDPOINT_UPDATEPORTFOLIOCODE);
                put("DelPortfolioMember", EndPoints.ENDPOINT_DELPORTFOLIOMEMBER);

                put("PortfolioServlet2G", EndPoints.ENDPOINT_PORTFOLIOSERVLET2G);
                put("PortfolioList2G", EndPoints.ENDPOINT_PORTFOLIOLIST2G);
                put("AddPortfolioMember2G", EndPoints.ENDPOINT_ADDPORTFOLIOMEMBER2G);
                put("UpdatePortfolioMember2G", EndPoints.ENDPOINT_UPDATEPORTFOLIOMEMBER2G);
                put("DelPortfolioMember2G", EndPoints.ENDPOINT_DELPORTFOLIOMEMBER2G);
                put("PortfolioMemberList2G", EndPoints.ENDPOINT_PORTFOLIOMEMBERLIST2G);
                put("UpdatePortfolioCode2G", EndPoints.ENDPOINT_UPDATEPORTFOLIOCODE2G);

                put("MultiplePortfolioMemberList", EndPoints.ENDPOINT_MULTIPLEPORTFOLIOMEMBERLIST);

                // limitalarm
                put("LimitAlertListServlet", EndPoints.ENDPOINT_LIMITALERTLISTSERVLET);
                put("LimitAlertServlet", EndPoints.ENDPOINT_LIMITALERTSERVLET);
                put("DelLimitAlertServlet", EndPoints.ENDPOINT_DELLIMITALERTSERVLET);
                put("LimitAlertSetListServlet", EndPoints.ENDPOINT_LIMITALERTSETLISTSERVLET);
                put("LimitAlertSetServlet", EndPoints.ENDPOINT_LIMITALERTSETSERVLET);
                put("NewsExperts", EndPoints.ENDPOINT_NEWSEXPERTS);
                put("WSIP", EndPoints.ENDPOINT_WSIP);
                put("LoginUrl", EndPoints.ENDPOINT_LOGINURL);
                put("AssetsJson", EndPoints.ENDPOINT_ASSETSJSON);
                put("ScreensJson", EndPoints.ENDPOINT_SCREENSJSON);
            }}, // apiEndpoints
            false, // useRandomWSIP
            "HK", // market
            "Asia/Hong_Kong", // timezone
            "etnet", // corpName
            false // debug
    );
}
