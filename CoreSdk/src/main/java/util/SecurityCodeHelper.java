package util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import constants.SecurityID;
import constants.SecurityTradeKey;
import constants.SecurityType;

import static constants.SecurityMarket.AH;
import static constants.SecurityMarket.HK;
import static constants.SecurityMarket.SH;
import static constants.SecurityMarket.SZ;

public class SecurityCodeHelper {

    static Pattern regExp = Pattern.compile("[0-9]*");
//    static RegExp regExp = RegExp(r"^[0-9]*$");

    /**
     * Deprecated, please use SecurityCodeHelper.getNormalizedSecurityCode()
     * Return normalized future/option code
     *
     * @param code security code
     */
    public static String getNormalizedCode(String code) {
        return SecurityCodeHelper.getNormalizedSecurityCode(code);
    }

    /**
     * Return normalized future/option code
     *
     * @param code security code
     */
    public static String getNormalizedSecurityCode(String code) {
        int underlinePos = code.indexOf("_");
        String shortCode = code;
        if (underlinePos != -1) {
            // Handle Options
            String splitCode = code.substring(0, underlinePos);
            if (regExp.matcher(splitCode).matches()) {
                if (code.contains("_v")) {
                    // vcm
                    shortCode = code;
                } else {
                    shortCode = code.substring(0, code.indexOf("_") + 1);
                }
            } else if (code.length() > 11 && code.contains(".")) {
                if (!(code.substring(-3) == "Pre") &&
                        code.split("_")[0].contains(".")) {
                    // long option code
                    shortCode = code.substring(0, code.indexOf("_") + 1);
                } else {
                    // CCS_006_201109_5.0:
                    shortCode = code;
                }
            } else {
                shortCode = code;
            }
        } else {
            shortCode = code;
        }
        return shortCode;
    }

    /**
     * Deprecated, use SecurityCodeHelper.getTypedSecurityMarket() instead
     * Return stock exchange market e.g. SH/SZ/HK
     *
     * @param code stock code
     */
    public static String getSecurityMarket(String code) {
        // code = "SZ.000002";
        // build SDK use if condition instead of switch
        if (code.contains("SH")) {
            return "SH";
        } else if (code.contains("SZ")) {
            return "SZ";
        } else {
            return "HK";
        }
    }

    /**
     * Return stock exchange market e.g. SH/SZ/HK/AH
     *
     * @param code stock code
     */
    public static String getSecurityMarketType(String code) {
        if (code.contains("SH.")) {
            return SH;
        } else if (code.contains("SZ.")) {
            return SZ;
        } else if (code.contains("A.")) {
            return AH;
        } else {
            return HK;
        }
    }

    /**
     * Return detail type of a given security based on regex.
     *
     * @param code Security code (nss)
     */
    public static int getSecurityID(String code) {
        if (Pattern.compile("^\\d{1,5}$").matcher(code).matches()) {
            return SecurityID.SECURITYID_STOCK;
        } else if (Pattern.compile("^S&P\\.\\w+$").matcher(code).matches()) {
            // S&P.HKL and S&P.GEM
            return SecurityID.SECURITYID_INDEX;
        } else if (Pattern.compile("^HSIS\\.+$").matcher(code).matches()) {
            // HSIS.HSI
            // HSIS.CEI
            return SecurityID.SECURITYID_INDEX;
        } else if (Pattern.compile("^GLOBAL\\.+$").matcher(code).matches()) {
            // GLOBAL.NDI
            return SecurityID.SECURITYID_INDEX_GLOBAL;
        } else if (Pattern.compile("^FOREX\\.\\w{3}\\/\\w{3}$").matcher(code).matches()) {
            // FOREX.USD/HKD
            return SecurityID.SECURITYID_FX;
        } else if (Pattern.compile("^SH\\.\\d{6}$").matcher(code).matches()) {
            // SH.601398
            return SecurityID.SECURITYID_STOCK_SH;
        } else if (Pattern.compile("^SZ\\.\\d{6}$").matcher(code).matches()) {
            // SZ.000001
            return SecurityID.SECURITYID_STOCK_SZ;
        } else if (Pattern.compile("^CSI\\.\\d{6}$").matcher(code).matches()) {
            // China Securities Index
            // CSI.000009
            return SecurityID.SECURITYID_INDEX_SH;
        } else if (Pattern.compile("^SZSE\\.\\d{6}$").matcher(code).matches()) {
            // Shenzhen Stock Exchange
            // SZSE.399107
            return SecurityID.SECURITYID_INDEX_SZ;
        } else if (Pattern.compile("^\\w\\d+\\.\\d{6}_$").matcher(code).matches()) {
            // HSI.201907_
            return SecurityID.SECURITYID_OPTION;
        } else if (Pattern.compile("^\\w\\d]+\\.\\d{6}$").matcher(code).matches()) {
            // HSI.201907
            // HS1.201907
            return SecurityID.SECURITYID_FUTURE;
        } else {
            return -1;
        }
    }

    /**
     * Return zero-padded hkex code e.g. 5 will be padded to 00005
     *
     * @param code full code or trimmed hkex code
     */
    static String padStockCode(String code) {
        String stockCode = String.valueOf(Integer.parseInt(code, 10)); // 00005
        while (stockCode.length() < 5) {
            stockCode = "0" + stockCode;
        }
        return stockCode;
    }

    static String getCodeMarket(String code) {
        if (Pattern.compile("^\\d{1,5}(.HK)?$").matcher(code).matches()) {
            return "H"; // Hong Kong Stock
        }
        if (Pattern.compile("^[06]{1}\\d{5}(.SZ|.SH|.A)?$").matcher(code).matches()) {
            return "A"; // Shanghai and Shenzhen Stock
        }
        return null;
    }

    static boolean isAShareSecurityCode(String code) {
        return Pattern.compile("^S(H|Z).\\d{6}$").matcher(code).matches();
    }

    /**
     * Return general security type without consider market.
     *
     * @param code Security code of server side
     */
    public static int getGeneralSecurityID(String code) {
        int t = SecurityCodeHelper.getSecurityID(code);
        int r;
        switch (t) {
            case SecurityID.SECURITYID_STOCK:
            case SecurityID.SECURITYID_STOCK_SH:
            case SecurityID.SECURITYID_STOCK_SZ:
                r = SecurityID.SECURITYID_STOCK;
                break;
            case SecurityID.SECURITYID_INDEX:
            case SecurityID.SECURITYID_INDEX_SH:
            case SecurityID.SECURITYID_INDEX_SZ:
                r = SecurityID.SECURITYID_INDEX;
                break;
            case SecurityID.SECURITYID_OPTION:
                r = SecurityID.SECURITYID_OPTION;
                break;
            case SecurityID.SECURITYID_FUTURE:
                r = SecurityID.SECURITYID_FUTURE;
                break;
            default:
                // default
                r = SecurityID.SECURITYID_STOCK;
                break;
        }
        return r;
    }

    public static String getDetailedSecurityType(String code) {
        String secType = SecurityType.SECURITYTYPE_STOCK;
        int nPos = code.indexOf(".");
        if (nPos >= 0) {
            secType = SecurityType.SECURITYTYPE_INDEX;
            if (code.indexOf(SecurityTradeKey.TRADEKEY_INDEX_SZ) == 0) {
                // SZ Index
                secType = SecurityType.SECURITYTYPE_INDEX_SZ;
            } else if (code.indexOf(SecurityTradeKey.TRADEKEY_STOCK_SZ) == 0) {
                // SZ Stock
                secType = SecurityType.SECURITYTYPE_STOCK_SZ;
            } else if (code.indexOf(SecurityTradeKey.TRADEKEY_STOCK_SH) == 0) {
                // SH Stock
                secType = SecurityType.SECURITYTYPE_STOCK_SH;
            } else if (code.indexOf(SecurityTradeKey.TRADEKEY_INDEX_SH) == 0) {
                // SH Index
                secType = SecurityType.SECURITYTYPE_INDEX_SH;
            } else {
            }
        }
        return secType;
    }

    /**
     * Return whether code is a valid formatted hkex code
     *
     * @param code full code
     */
    static boolean isHKCode(String code) {
        return SecurityCodeHelper.getSecurityID(code) ==
                SecurityID.SECURITYID_STOCK;
    }

    /**
     * Return whether code is a valid formatted shse code
     *
     * @param code full code
     */
    public static boolean isSHCode(String code) {
        return SecurityCodeHelper.getSecurityID(code) ==
                SecurityID.SECURITYID_STOCK_SH;
    }

    /**
     * Return whether code is a valid formatted szse code
     *
     * @param code full code
     */
    public static boolean isSZCode(String code) {
        return SecurityCodeHelper.getSecurityID(code) ==
                SecurityID.SECURITYID_STOCK_SZ;
    }

    /**
     * Return true when code is prefix with A., else return false
     *
     * @param code stock code
     */
    static boolean isACode(String code) {
        return Pattern.compile("^A\\.\\d+").matcher(code).matches();
    }

    /**
     * Return whether code is a valid formatted ashare code included A.XXXXXX
     *
     * @param code full code
     */
    public static boolean isAShareCode(String code) {
        return SecurityCodeHelper.isSHCode(code) ||
                SecurityCodeHelper.isSZCode(code) ||
                SecurityCodeHelper.isACode(code);
    }

    /**
     * Return whether code is a valid formatted hkex future code
     *
     * @param code full code
     */
    static boolean isHKFutureCode(String code) {
        return SecurityCodeHelper.getSecurityID(code) ==
                SecurityID.SECURITYID_FUTURE;
    }

    /**
     * Return whether code is a valid formatted option code
     *
     * @param code full code
     */
    static boolean isHKOptionCode(String code) {
        return SecurityCodeHelper.getSecurityID(code) ==
                SecurityID.SECURITYID_OPTION;
    }

    /**
     * Return whether code is a valid formatted index (e.g. HSI, CEI) code
     *
     * @param code full code
     */
    static boolean isHKIndexCode(String code) {
        return SecurityCodeHelper.getSecurityID(code) ==
                SecurityID.SECURITYID_INDEX;
    }

    /**
     * Return code for http requst as some code doest not need prefix
     *
     * @param code code with dot e.g. SH.601398, HSHS.HSI
     */
    public static String formatSecurityCodeForHttp(String code) {
        final List<String> splitedCode = Arrays.asList(code.split("."));
        String codeForHttp = code;
        if (Pattern.compile("^\\d{1,5}$").matcher(code).matches()) {
            codeForHttp = code;
        } else if (Pattern.compile("S&P\\.\\w+").matcher(code).matches()) {
            // S&P.HKL and S&P.GEM
            codeForHttp = splitedCode.get(1);
        } else if (Pattern.compile("^HSIS\\.*").matcher(code).matches()) {
            // HSIS.HSI
            // HSIS.CEI
            codeForHttp = splitedCode.get(1);
        } else if (Pattern.compile("^SH\\.\\d{6}$").matcher(code).matches()) {
            // SH.601398
            codeForHttp = code;
        } else if (Pattern.compile("^SZ\\.\\d{6}$").matcher(code).matches()) {
            // SZ.000001
            codeForHttp = code;
        } else if (Pattern.compile("^CSI\\.\\d{6}$").matcher(code).matches()) {
            // China Securities Index
            // CSI.000009
            codeForHttp = code;
        } else if (Pattern.compile("^SZSE\\.\\d{6}$").matcher(code).matches()) {
            // Shenzhen Stock Exchange
            // SZSE.399107
            codeForHttp = code;
        } else if (Pattern.compile("\\w\\d+\\.\\d{6}_$").matcher(code).matches()) {
            // HSI.201907_
            codeForHttp = code;
        } else if (Pattern.compile("\\w\\d+\\.\\d{6}$").matcher(code).matches()) {
            // HSI.201907
            // HS1.201907
            codeForHttp = code;
        } else {
            codeForHttp = code;
        }
        return codeForHttp;
    }
}
