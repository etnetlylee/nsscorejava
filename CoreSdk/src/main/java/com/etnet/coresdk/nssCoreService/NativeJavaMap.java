package com.etnet.coresdk.nssCoreService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.etnet.coresdk.api.ApiFields.*;


public class NativeJavaMap {
    public static final Map<String, String> nativeJavaMap = new HashMap<String, String>() {{
        put(FKEY_CODE, FID_CODE);
        put(FKEY_TC_NAME, FID_TC_NAME);
        put(FKEY_SC_NAME, FID_SC_NAME);
        put(FKEY_EN_NAME, FID_EN_NAME);
        put(FKEY_SMA_10, FID_SMA_10);
        put(FKEY_SMA_20, FID_SMA_20);
        put(FKEY_SMA_50, FID_SMA_50);
        put(FKEY_SMA_250, FID_SMA_250);
        put(FKEY_1M_HIGH, FID_1M_HIGH);
        put(FKEY_1M_LOW, FID_1M_LOW);
        put(FKEY_52W_HIGH, FID_52W_HIGH);
        put(FKEY_52W_LOW, FID_52W_LOW);
        put(FKEY_MARKET, FID_MARKET);
        put(FKEY_INDUSTRY_REL, FID_INDUSTRY_REL);
        put(FKEY_INDEX_REL, FID_INDEX_REL);
        put(FKEY_SECURITY_TYPE, FID_SECURITY_TYPE);
        put(FKEY_HV20, FID_HV20);
        put(FKEY_MARKET_CAP, FID_MARKET_CAP);
        put(FKEY_M_NAV, FID_M_NAV);
        put(FKEY_EXERCISE_PRICE, FID_EXERCISE_PRICE);
        put(FKEY_ENTITLEMENT_RATIO, FID_ENTITLEMENT_RATIO);
        put(FKEY_ISSUER_NAME_TC, FID_ISSUER_NAME_TC);
        put(FKEY_ISSUER_NAME_SC, FID_ISSUER_NAME_SC);
        put(FKEY_ISSUER_NAME_EN, FID_ISSUER_NAME_EN);
        put(FKEY_LP_CODE, FID_LP_CODE);
        put(FKEY_WARRANT_TYPE, FID_WARRANT_TYPE);
        put(FKEY_CALL_PRICE, FID_CALL_PRICE);
        put(FKEY_CBBC_TYPE, FID_CBBC_TYPE);
        put(FKEY_SR_RU, FID_SR_RU);
        put(FKEY_CBBC_RELATION, FID_CBBC_RELATION);
        put(FKEY_WARR_RELATION, FID_WARR_RELATION);
        put(FKEY_NOMINAL, FID_NOMINAL);
        put(FKEY_PER_CHANGE, FID_PER_CHANGE);
        put(FKEY_TURNOVER, FID_TURNOVER);
        put(FKEY_VOLUME, FID_VOLUME);
        put(FKEY_NO_OF_TRANS, FID_NO_OF_TRANS);
        put(FKEY_CHANGE, FID_CHANGE);
        put(FKEY_HIGH, FID_HIGH);
        put(FKEY_LOW, FID_LOW);
        put(FKEY_PE_RATIO, FID_PE_RATIO);
        put(FKEY_DPS, FID_DPS);
        put(FKEY_EPS, FID_EPS);
        put(FKEY_EXPECT_EPS, FID_EXPECT_EPS);
        put(FKEY_EXPECT_DPS, FID_EXPECT_DPS);
        put(FKEY_EM_NAV, FID_EM_NAV);
        put(FKEY_CLOSE, FID_CLOSE);
        put(FKEY_IEP, FID_IEP);
        put(FKEY_IEV, FID_IEV);
        put(FKEY_ASK, FID_ASK);
        put(FKEY_BID, FID_BID);
        put(FKEY_OPEN, FID_OPEN);
        put(FKEY_PERCENT_YIELD, FID_PERCENT_YIELD);
        put(FKEY_EXPECT_PERCENT_YIELD, FID_EXPECT_PERCENT_YIELD);
        put(FKEY_EXPECT_PE_RATIO, FID_EXPECT_PE_RATIO);
        put(FKEY_WEIGHTED_AVG, FID_WEIGHTED_AVG);
        put(FKEY_VOLUME_RATIO, FID_VOLUME_RATIO);
        put(FKEY_VOLUME_T, FID_VOLUME_T);
        put(FKEY_AMT_T, FID_AMT_T);
        put(FKEY_PERCENT_M_NAV, FID_PERCENT_M_NAV);
        put(FKEY_EXPECT_NET_PROFIT, FID_EXPECT_NET_PROFIT);
        put(FKEY_PERCENT_IV, FID_PERCENT_IV);
        put(FKEY_CONVERSION_COST, FID_CONVERSION_COST);
        put(FKEY_DELTA, FID_DELTA);
        put(FKEY_GEARING_RATIO, FID_GEARING_RATIO);
        put(FKEY_EFF_GEARING_RATIO, FID_EFF_GEARING_RATIO);
        put(FKEY_DAYS_TO_EXPIRY, FID_DAYS_TO_EXPIRY);
        put(FKEY_LAST_TRADE_DATE, FID_LAST_TRADE_DATE);
        put(FKEY_LP_BUY_VOL, FID_LP_BUY_VOL);
        put(FKEY_LP_BUY_PRICE, FID_LP_BUY_PRICE);
        put(FKEY_LP_SOLD_VOL, FID_LP_SOLD_VOL);
        put(FKEY_LP_SOLD_PRICE, FID_LP_SOLD_PRICE);
        put(FKEY_OUTSTAND_QUAN, FID_OUTSTAND_QUAN);
        put(FKEY_PERCENT_OUTSTAND_QUAN, FID_PERCENT_OUTSTAND_QUAN);
        put(FKEY_UNITS_ISSUED, FID_UNITS_ISSUED);
        put(FKEY_SHORTSELL, FID_SHORTSELL);
        put(FKEY_PERCENT_PREMIUM, FID_PERCENT_PREMIUM);
        put(FKEY_AVAILABLE_TRADINGDAY, FID_AVAILABLE_TRADINGDAY);
        put(FKEY_SUMMARY_QUEUE, FID_SUMMARY_QUEUE);
        put(FKEY_BROKER_QUEUE, FID_BROKER_QUEUE);
        put(FKEY_BOARDLOT, FID_BOARDLOT);
        put(FKEY_CURRENCY, FID_CURRENCY);
        put(FKEY_SPREAD, FID_SPREAD);
        put(FKEY_BIDASK_FORCE_BAR, FID_BIDASK_FORCE_BAR);
        put(FKEY_BUYSELL_TRN_FORCE_BAR, FID_BUYSELL_TRN_FORCE_BAR);
        put(FKEY_IND_BUYSELL_FORCE_BAR, FID_IND_BUYSELL_FORCE_BAR);
        put(FKEY_ADR_CLOSE_HKD, FID_ADR_CLOSE_HKD);
        put(FKEY_ADR_PERCENT_CHG, FID_ADR_PERCENT_CHG);
        put(FKEY_PRICE_UP_DOWN, FID_PRICE_UP_DOWN);
        put(FKEY_UNDERLYING_CBBC_TRN, FID_UNDERLYING_CBBC_TRN);
        put(FKEY_UNDERLYING_WAR_TRN, FID_UNDERLYING_WAR_TRN);
        put(FKEY_D52WK_N_HIGH, FID_D52WK_N_HIGH);
        put(FKEY_D52WK_N_LOW, FID_D52WK_N_LOW);
        put(FKEY_FREE_TEXT, FID_FREE_TEXT);
        put(FKEY_IND_BS_TRN_FORCE_BAR, FID_IND_BS_TRN_FORCE_BAR);
        put(FKEY_WAR_PER_OUT_QTY, FID_WAR_PER_OUT_QTY);
        put(FKEY_CBBC_PER_OUT_QTY, FID_CBBC_PER_OUT_QTY);
        put(FKEY_WAR_OUT_QTY, FID_WAR_OUT_QTY);
        put(FKEY_CBBC_OUT_QTY, FID_CBBC_OUT_QTY);
        put(FKEY_INDUSTRY_TRN, FID_INDUSTRY_TRN);
        put(FKEY_INDUSTRY_PER_MT, FID_INDUSTRY_PER_MT);
        put(FKEY_RATE, FID_RATE);
        put(FKEY_PREMIUM, FID_PREMIUM);
        put(FKEY_WAR_NEW_LISTING, FID_WAR_NEW_LISTING);
        put(FKEY_CBBC_NEW_LISTING, FID_CBBC_NEW_LISTING);
        put(FKEY_WAR_EXPIRING, FID_WAR_EXPIRING);
        put(FKEY_CBBC_EXPIRING, FID_CBBC_EXPIRING);
        put(FKEY_CBBC_KNOCKOUTS, FID_CBBC_KNOCKOUTS);
        put(FKEY_CBBC_HIGHRISK, FID_CBBC_HIGHRISK);
        put(FKEY_PRV_DAY_TRN, FID_PRV_DAY_TRN);
        put(FKEY_PRV_DAY_TRN_MT, FID_PRV_DAY_TRN_MT);
        put(FKEY_UP_LIMIT, FID_UP_LIMIT);
        put(FKEY_DOWN_LIMIT, FID_DOWN_LIMIT);
        put(FKEY_SINGLE_QTY, FID_SINGLE_QTY);
        put(FKEY_FUTURE_QUEUE, FID_FUTURE_QUEUE);
        put(FKEY_LAST_QTY, FID_LAST_QTY);
        put(FKEY_LAST_INDICATOR, FID_LAST_INDICATOR);
        put(FKEY_PRV_VOL, FID_PRV_VOL);
        put(FKEY_CHG_VOL, FID_CHG_VOL);
        put(FKEY_GOI, FID_GOI);
        put(FKEY_NOI, FID_NOI);
        put(FKEY_CHG_GOI, FID_CHG_GOI);
        put(FKEY_CHG_NOI, FID_CHG_NOI);
        put(FKEY_EAS, FID_EAS);
        put(FKEY_TIME, FID_TIME);
        put(FKEY_L_HIGH, FID_L_HIGH);
        put(FKEY_L_LOW, FID_L_LOW);
        put(FKEY_TTL_VOL, FID_TTL_VOL);
        put(FKEY_TTL_PRV_VOL, FID_TTL_PRV_VOL);
        put(FKEY_TTL_GOI, FID_TTL_GOI);
        put(FKEY_TTL_NOI, FID_TTL_NOI);
        put(FKEY_TTL_CHG_GOI, FID_TTL_CHG_GOI);
        put(FKEY_TTL_CHG_NOI, FID_TTL_CHG_NOI);
        put(FKEY_COMBO_SERIES, FID_COMBO_SERIES);
        put(FKEY_FPP_1M, FID_FPP_1M);
        put(FKEY_FPP_3M, FID_FPP_3M);
        put(FKEY_FPP_6M, FID_FPP_6M);
        put(FKEY_FPP_1Y, FID_FPP_1Y);
        put(FKEY_AVG_PER_CHANGE, FID_AVG_PER_CHANGE);
        put(FKEY_BIDASK_FORCEBAR_NORMAL, FID_BIDASK_FORCEBAR_NORMAL);
        put(FKEY_PRC_UPDOWN_NOCHG_NOTRN, FID_PRC_UPDOWN_NOCHG_NOTRN);
        put(FKEY_PER_MONEYNESS, FID_PER_MONEYNESS);
        put(FKEY_STOCK2SPREADTABLE, FID_STOCK2SPREADTABLE);
        put(FKEY_PERCHG_TO_CALLPRC, FID_PERCHG_TO_CALLPRC);
        put(FKEY_CHG_TO_CALLPRC, FID_CHG_TO_CALLPRC);
        put(FKEY_ISSUER_PRICE, FID_ISSUER_PRICE);
        put(FKEY_PROSTICKS_MODAL_PT, FID_PROSTICKS_MODAL_PT);
        put(FKEY_PROSTICKS_MODAL_COUNT, FID_PROSTICKS_MODAL_COUNT);
        put(FKEY_PROSTICKS_ACT_RANGE_UP, FID_PROSTICKS_ACT_RANGE_UP);
        put(FKEY_PROSTICKS_ACT_RANGE_LOW, FID_PROSTICKS_ACT_RANGE_LOW);
        put(FKEY_NBV, FID_NBV);
        put(FKEY_CURRENCY_FINSTMT, FID_CURRENCY_FINSTMT);
        put(FKEY_NET_PROFIT, FID_NET_PROFIT);
        put(FKEY_CURR_PROFITEST_SUMMARY, FID_CURR_PROFITEST_SUMMARY);
        put(FKEY_LIST_CURRENCY, FID_LIST_CURRENCY);
        put(FKEY_LIST_PRICE, FID_LIST_PRICE);
        put(FKEY_LIST_DATE, FID_LIST_DATE);
        put(FKEY_ISC, FID_ISC);
        put(FKEY_EX_DATE, FID_EX_DATE);
        put(FKEY_PAY_DATE, FID_PAY_DATE);
        put(FKEY_ANN_DATE, FID_ANN_DATE);
        put(FKEY_RESULT_DATE, FID_RESULT_DATE);
        put(FKEY_EVENT_REMINDAR, FID_EVENT_REMINDAR);
        put(FKEY_DIVIDEND_ANNOUNCEMENT, FID_DIVIDEND_ANNOUNCEMENT);
        put(FKEY_DISCLOSURE_INTERESTS, FID_DISCLOSURE_INTERESTS);
        put(FKEY_FUND_RAISING, FID_FUND_RAISING);
        put(FKEY_SHORTSELL_DATE, FID_SHORTSELL_DATE);
        put(FKEY_ASC, FID_ASC);
        put(FKEY_NO_ADJ_HIST_HIGH, FID_NO_ADJ_HIST_HIGH);
        put(FKEY_NO_ADJ_HIST_LOW, FID_NO_ADJ_HIST_LOW);
        put(FKEY_ISSUER_INIT_ENG, FID_ISSUER_INIT_ENG);
        put(FKEY_ISSUER_INIT_TCHI, FID_ISSUER_INIT_TCHI);
        put(FKEY_ISSUER_INIT_SCHI, FID_ISSUER_INIT_SCHI);
        put(FKEY_CALLED, FID_CALLED);
        put(FKEY_TURNOVER_TOP50, FID_TURNOVER_TOP50);
        put(FKEY_CCLOSEAT_1MTH_AGO, FID_CCLOSEAT_1MTH_AGO);
        put(FKEY_CCLOSEAT_3MTHS_AGO, FID_CCLOSEAT_3MTHS_AGO);
        put(FKEY_CCLOSEAT_6MTHS_AGO, FID_CCLOSEAT_6MTHS_AGO);
        put(FKEY_CCLOSEAT_12MTHS_AGO, FID_CCLOSEAT_12MTHS_AGO);
        put(FKEY_D_SUMMARY_QUEUE, FID_D_SUMMARY_QUEUE);
        put(FKEY_UNDERLYING_ASSET_TC, FID_UNDERLYING_ASSET_TC);
        put(FKEY_UNDERLYING_ASSET_SC, FID_UNDERLYING_ASSET_SC);
        put(FKEY_UNDERLYING_ASSET_ENG, FID_UNDERLYING_ASSET_ENG);
        put(FKEY_CATEGORY_TC, FID_CATEGORY_TC);
        put(FKEY_CATEGORY_SC, FID_CATEGORY_SC);
        put(FKEY_CATEGORY_ENG, FID_CATEGORY_ENG);
        put(FKEY_STK_SUSPEND_REMINDER, FID_STK_SUSPEND_REMINDER);
        put(FKEY_NAV, FID_NAV);
        put(FKEY_ETF_NAV_DATE, FID_ETF_NAV_DATE);
        put(FKEY_AHFT_MKT_STATUS, FID_AHFT_MKT_STATUS);
        put(FKEY_RATIO, FID_RATIO);
        put(FKEY_PREFIX_GB_NAME, FID_PREFIX_GB_NAME);
        put(FKEY_PER_MAX_RISE, FID_PER_MAX_RISE);
        put(FKEY_PER_MAX_DECLINE, FID_PER_MAX_DECLINE);
        put(FKEY_SSUMMARY_QUEUE, FID_SSUMMARY_QUEUE);
        put(FKEY_STOCK_CONNECT, FID_STOCK_CONNECT);
        put(FKEY_FREE_FLOAT_ISC, FID_FREE_FLOAT_ISC);
        put(FKEY_UP_LIMIT_PRICE, FID_UP_LIMIT_PRICE);
        put(FKEY_DOWN_LIMIT_PRICE, FID_DOWN_LIMIT_PRICE);
        put(FKEY_REACH_LIMIT_UPDOWN, FID_REACH_LIMIT_UPDOWN);
        put(FKEY_BLK_TRADE_TRN_RATIOBAR, FID_BLK_TRADE_TRN_RATIOBAR);
        put(FKEY_BLK_TRADE_TRN_RATIOBAR, FID_BLK_TRADE_TRN_RATIOBAR);
        put(FKEY_BLOCK_TURNOVER, FID_BLOCK_TURNOVER);
        put(FKEY_BLOCK_VOLUME, FID_BLOCK_VOLUME);
        put(FKEY_PB_RATIO, FID_PB_RATIO);
        put(FKEY_P_EST_NAV, FID_P_EST_NAV);
        put(FKEY_PREM, FID_PREM);
        put(FKEY_PREM, FID_PREM);
        put(FKEY_RSI9, FID_RSI9);
        put(FKEY_RSI14, FID_RSI14);
        put(FKEY_PERCENT_SHORTSELL, FID_PERCENT_SHORTSELL);
        put(FKEY_SHORTSELL5SMA, FID_SHORTSELL5SMA);
        put(FKEY_ETF_PREMIUM, FID_ETF_PREMIUM);
        put(FKEY_VCM_TRIGGER, FID_VCM_TRIGGER);
        put(FKEY_VCM_INDICATOR, FID_VCM_INDICATOR);
        put(FKEY_CAS_REF_PRICE, FID_CAS_REF_PRICE);
        put(FKEY_IEP_REF_PRC_UPLIMIT, FID_IEP_REF_PRC_UPLIMIT);
        put(FKEY_IEP_REF_PRC_LOWLIMIT, FID_IEP_REF_PRC_LOWLIMIT);
        put(FKEY_CAS_DIRECTION, FID_CAS_DIRECTION);
        put(FKEY_CAS_BALANCE_QTY, FID_CAS_BALANCE_QTY);
        put(FKEY_CAS_INDICATOR, FID_CAS_INDICATOR);
        put(FKEY_TRADE_STATUSID, FID_TRADE_STATUSID);
        put(FKEY_VCM_TIME, FID_VCM_TIME);
        put(FKEY_VCM_TRIGGER_PERIOD, FID_VCM_TRIGGER_PERIOD);
        put(FKEY_ETF_IOPV, FID_ETF_IOPV);
        put(FKEY_CAS_STATUS, FID_CAS_STATUS);
        put(FKEY_HK_SZ_STK_CONNECT, FID_HK_SZ_STK_CONNECT);
        put(FKEY_DAY_CLOSE, FID_DAY_CLOSE);
        put(FKEY_TURNOVER_RATE, FID_TURNOVER_RATE);
        put(FKEY_ORDER_RATIOAH, FID_ORDER_RATIOAH);
        put(FKEY_TICK, FID_TICK);
        put("161_transQueue", FID_TRANSQUEUE); // TODO, Custom change the value of the key
        put("82S1_transQueue", FID_D_TRANSQUEUE); // TODO, Custom change the value of the key
        put(FKEY_TRANSQUEUE, FID_TRANSACTION);
        put(FKEY_ORDER_RATIO, FID_ORDER_RATIO);
        put(FKEY_LIMIT_UP, FID_LIMIT_UP);
        put(FKEY_LIMIT_DOWN, FID_LIMIT_DOWN);
        put(FKEY_FLUCTUATION, FID_FLUCTUATION);
        put(FKEY_DATA4MONEYFLOW, FID_DATA4MONEYFLOW);
        put(FKEY_DATA4SHORTSELL, FID_DATA4SHORTSELL);
        put(FKEY_DATA4WARRANT, FID_DATA4WARRANT);
        put(FKEY_DATA4H, FID_DATA4H);
        put(FKEY_DATA4A, FID_DATA4A);
        put(FKEY_PREMIUM4AH, FID_PREMIUM4AH);
        put(FKEY_O_NORMINAL, FID_O_NORMINAL);
        put(FKEY_O_VOL, FID_O_VOL);
        put(FKEY_O_CHG, FID_O_CHG);
        put(FKEY_O_HIGH, FID_O_HIGH);
        put(FKEY_O_LOW, FID_O_LOW);
        put(FKEY_O_PRV_CLOSE, FID_O_PRV_CLOSE);
        put(FKEY_O_ASK, FID_O_ASK);
        put(FKEY_O_BID, FID_O_BID);
        put(FKEY_O_OPEN, FID_O_OPEN);
        put(FKEY_O_BIDASK, FID_O_BIDASK);
        put(FKEY_O_LAST_QTY, FID_O_LAST_QTY);
        put(FKEY_O_LASTINDICATOR, FID_O_LASTINDICATOR);
        put(FKEY_O_PRE_VOL, FID_O_PRE_VOL);
        put(FKEY_O_CHGVOL, FID_O_CHGVOL);
        put(FKEY_O_GOI, FID_O_GOI);
        put(FKEY_O_NOI, FID_O_NOI);
        put(FKEY_O_CHGGOI, FID_O_CHGGOI);
        put(FKEY_O_CHGNOI, FID_O_CHGNOI);
        put(FKEY_TRANSSUM, FID_TRANSSUM);
        put(FKEY_HOT_SECTORS, FID_HOT_SECTORS);
        put(FKEY_DAILY_QUOTA_DATA, FID_DAILY_QUOTA_DATA);
        put(FKEY_MONTHLY_QUOTA_DATA, FID_MONTHLY_QUOTA_DATA);
        put(FKEY_T10_STKCNT_TRN, FID_T10_STKCNT_TRN);
        put(FKEY_EXCHANGE_CODE, FID_EXCHANGE_CODE);
        put(FKEY_ALL_DIVIDEND_ANNOUNCE, FID_ALL_DIVIDEND_ANNOUNCE);
        put(FKEY_OPERATING_REVEUE_PERSHARE, FID_OPERATING_REVEUE_PERSHARE);
        put(FKEY_BOOKVALUE_PERSHARE, FID_BOOKVALUE_PERSHARE);
        put(FKEY_CASHFLOW_YIELD, FID_CASHFLOW_YIELD);
        put(FKEY_ENTERPRISE_VALUE, FID_ENTERPRISE_VALUE);
        put(FKEY_REVENUE, FID_REVENUE);
        put(FKEY_MS_EARNINGS_PERSHARE, FID_MS_EARNINGS_PERSHARE);
        put(FKEY_MS_FORWARDEST_EPS, FID_MS_FORWARDEST_EPS);
        put(FKEY_MS_MEDBUSINESS_DESC, FID_MS_MEDBUSINESS_DESC);
        put(FKEY_PREVOLUME, FID_PREVOLUME);
        put(FKEY_POSTVOLUME, FID_POSTVOLUME);
        put(FKEY_CUSIP, FID_CUSIP);
        put(FKEY_GENERAL_INSTRU_STATUS, FID_GENERAL_INSTRU_STATUS);
        put(FKEY_MSINDCD, FID_MSINDCD);
        put(FKEY_LOWER_STRIKE_PRICE, FID_LOWER_STRIKE_PRICE);
        put(FKEY_UPPER_STRIKE_PRICE, FID_UPPER_STRIKE_PRICE);
        put(FKEY_IN_THE_RANGE_PAYMENT, FID_IN_THE_RANGE_PAYMENT);
        put(FKEY_OUT_OF_THE_RANGE_PAYMENT, FID_OUT_OF_THE_RANGE_PAYMENT);
        put(FKEY_POTENTIAL_RETURN, FID_POTENTIAL_RETURN);
        put(FKEY_UPPER_STRIKE_DISTANCE, FID_UPPER_STRIKE_DISTANCE);
        put(FKEY_UPPER_STRIKE_DISTANCE_RATE, FID_UPPER_STRIKE_DISTANCE_RATE);
        put(FKEY_LOWER_STRIKE_DISTANCE, FID_LOWER_STRIKE_DISTANCE);
        put(FKEY_LOWER_STRIKE_DISTANCE_RATE, FID_LOWER_STRIKE_DISTANCE_RATE);
        put(FKEY_STRIK_EDISTANCE_RATE, FID_STRIK_EDISTANCE_RATE);
        put(FKEY_IS_IN_THE_RANGE, FID_IS_IN_THE_RANGE);
        put(FKEY_STRIKE_WIDTH, FID_STRIKE_WIDTH);
        put(FKEY_UNDERLYING_EN, FID_UNDERLYING_EN);
        put(FKEY_UNDERLYING_SC, FID_UNDERLYING_SC);
        put(FKEY_UNDERLYING_TC, FID_UNDERLYING_TC);
    }};

    public void main() {
        List<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry : nativeJavaMap.entrySet()) {
            list.add(entry.getKey());
        }
        System.out.print(list.size());

        for (int i = 0; i < list.size(); i += 4) {
            System.out.print("|\t${list[i].toString()}\t|\t${list[i+1].toString()}\t|\t${list[i+2].toString()" +
                    "}\t|\t${list[i+3]" +
                    ".toString()}\t|");
        }
        // key  for (int i = 0; i )
        //   // print ("HelloWorld");
    }


}
