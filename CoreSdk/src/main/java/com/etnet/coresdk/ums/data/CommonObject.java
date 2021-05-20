package com.etnet.coresdk.ums.data;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonObject {
    private static final Logger logger = Logger.getLogger(CommonObject.class);

    public static final String DEFAULTStr = "-";
    //    public static final String DEFAULTSTRING = "-";
    public static final String format = "#,##0.000";
    public static final String formatStr = "0.000";
    public String getDefaultStr() {
        return DEFAULTStr;
    }
//    public List<?> getList(String attr) {
//        try {
//            return (List<?>) PropertyUtils.getProperty(this, attr);
//        } catch (Exception e) {
//            logger.debug("Fail to get Quote obj List: " + attr);
//            return null;
//        }
//    }
//
//    public Object getObj(String attr) {
//        try {
//            return PropertyUtils.getProperty(this, attr);
//        } catch (Exception e) {
//            logger.error(e, e);
//            logger.debug("Fail to get Quote obj: " + attr);
//            return null;
//        }
//    }
//
//    public void setObj(String attr, Object obj) {
//        try {
//            PropertyUtils.setProperty(this, attr, obj);
//        } catch (Exception e) {
//            logger.debug("Fail to set Quote value: " + attr);
//        }
//    }
//
//    public String getVal(String attr) {
//        try {
//            Object obj = PropertyUtils.getProperty(this, attr);
//            if (obj == null) {
//                return DEFAULTStr;
//            } else {
//                if (obj instanceof Date) {
//                    return getStrFromDate((Date) obj, "dd/MM/yyyy");
//                } else if (obj instanceof Double) {
//                    return getStdDouble(obj);
//                } else if (obj instanceof Integer) {
//                    return getStdInt(obj);
//                } else if (obj instanceof Long) {
//                    return getStdLong(obj);
//                } else {
//                    if (StringUtils.isBlank(obj.toString())) {
//                        return DEFAULTStr;
//                    } else {
//                        return obj.toString();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.debug("Fail to get Quote value: " + attr);
//            return DEFAULTStr;
//        }
//    }
    public static String getFormatVal(String val) {
        try {
            if (val == null) {
                return DEFAULTStr;
            }
            return val;
        } catch (Exception e) {
            return DEFAULTStr;
        }
    }
//    public String getValByDecimalPoint(String attr ,String decimalPoint) {
//        try {
//            Object obj = PropertyUtils.getProperty(this, attr);
//            if (obj == null) {
//                return DEFAULTStr;
//            } else {
//                if (obj instanceof Double) {
//                    return getStdDouble(obj ,decimalPoint);
//                } else if (obj instanceof Integer) {
//                    return getStdInt(obj ,decimalPoint);
//                } else if (obj instanceof Long) {
//                    return getStdLong(obj ,decimalPoint);
//                } else {
//                    if (StringUtils.isBlank(obj.toString())) {
//                        return DEFAULTStr;
//                    } else {
//                        return obj.toString();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.debug("Fail to get Quote value: " + attr);
//            return DEFAULTStr;
//        }
//    }

    public static String getStdDouble(Object obj) {
        try {
            return getStdDouble(obj, formatStr);
        } catch (Exception e) {
            return DEFAULTStr;
        }
    }

    public static String getStdSignedDoubleFormating(Object obj ,String formater) {
        try {
            Number dd =  new DecimalFormat(format).parse(String.valueOf(obj));
            if(dd.doubleValue() > 0){
                return "+"+getStdDoubleDisplayFormat(dd.doubleValue(), formater);
            }else{
                return getStdDoubleDisplayFormat(dd.doubleValue(), formater);
            }
        } catch (ParseException e) {
            return DEFAULTStr;
        } catch (Exception e) {
            logger.error(e,e);
            return DEFAULTStr;
        }

    }

    public static String getStdDoubleFormating(Object obj ,String formater) {
        try {
            Number dd =  new DecimalFormat(format).parse(String.valueOf(obj));
            return getStdDoubleDisplayFormat(dd.doubleValue(), formater);
        } catch (ParseException e) {
            return DEFAULTStr;
        } catch (Exception e) {
            logger.error(e,e);
            return DEFAULTStr;
        }
    }

    public static String getStdDoubleFormating(Object obj) {
        try {
            Number dd =  new DecimalFormat(format).parse(String.valueOf(obj));
            return getStdDoubleDisplayFormat(dd.doubleValue(), formatStr);
        } catch (ParseException e) {
            return DEFAULTStr;
        } catch (Exception e) {
            logger.error(e,e);
            return DEFAULTStr;
        }
    }
    public static String getStdDoubleDisplayFormat(Object obj, String format) {
        double v = ((Double) obj).doubleValue();
        NumberFormat nf = new DecimalFormat(format);
        if (v >= 1000000000) {
            return nf.format(v / 1000000000.0) + "B";
        } else if (v >= 1000000) {
            return nf.format(v / 1000000.0) + "M";
        } else if (v >= 10000) {
            return nf.format(v / 1000.0) + "K";
        } else if (v <= -1000000000) {
            return nf.format(v / 1000000000.0) + "B";
        } else if (v <= -1000000) {
            return nf.format(v / 1000000.0) + "M";
        } else if (v <= -10000) {
            return nf.format(v / 1000.0) + "K";
        }  else {
            return nf.format(v);
        }
    }
    public static String getStdDouble(Object obj, String format) {
        double v = ((Double) obj).doubleValue();
        NumberFormat nf = new DecimalFormat(format);
        if (v >= 1000000000) {
            return nf.format(v / 1000000000.0) + "B";
        } else if (v >= 1000000) {
            return nf.format(v / 1000000.0) + "M";
        } else if (v <= -1000000000) {
            return nf.format(v / 1000000000.0) + "B";
        } else if (v <= -1000000) {
            return nf.format(v / 1000000.0) + "M";
        } else {
            return nf.format(v);
        }
    }

    public static String getStdInt(Object obj) {
        return getStdInt(obj, "0.0");
    }

    public static String getStdInt(Object obj, String format) {
        int v = ((Integer) obj).intValue();
        NumberFormat nf = new DecimalFormat(format);
        if (v >= 1000000000) {
            return nf.format(v / 1000000000.0) + "B";
        } else if (v >= 1000000) {
            return nf.format(v / 1000000.0) + "M";
        } else if (v >= 10000) {
            return nf.format(v / 1000.0) + "K";
        } else {
            return new DecimalFormat("0").format(v);
        }
    }

    public static String getStdLong(Object obj) {
        return getStdLong(obj, "0.000");
    }

    public static String getStdLong(Object obj, String format) {
        try {
            long v = ((Long) obj).longValue();
            NumberFormat nf = new DecimalFormat(format);
            if (v >= 1000000000) {
                return nf.format(v / 1000000000.0) + "B";
            } else if (v >= 1000000) {
                return nf.format(v / 1000000.0) + "M";
            } else if (v >= 10000) {
                return nf.format(v / 1000.0) + "K";
            } else {
                return new DecimalFormat("0").format(v);
            }
        } catch (Exception e) {
            return (String)obj;
        }
    }

    public static Date getDateFromStr(String str, String format) {
        if (StringUtils.isNotBlank(str)) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            try {
                return sdf.parse(str);
            } catch (ParseException e) {
                logger.error(e, e);
            }
        }
        return null;
    }

    public static String getStrFromDate(Date date, String format) {
        if (date == null) {
            return null;
        } else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.format(date);
            } catch (Exception e) {
                logger.error(e, e);
                return null;
            }
        }
    }

    public static String getString(Object obj) {
        try {
            if (obj == null) {
                return DEFAULTStr;
            } else {
                if (obj instanceof Date) {
                    return ((Date) obj).toString();
                } else if (obj instanceof Double) {
                    return (new Double((Double) obj)).toString();
                } else if (obj instanceof Integer) {
                    return ((Integer) obj).toString();
                } else {
                    if (StringUtils.isBlank(obj.toString())) {
                        return DEFAULTStr;
                    } else {
                        return obj.toString();
                    }
                }
            }
        } catch (Exception e) {
            logger.debug("Fail to getString value: " + obj);
            return DEFAULTStr;
        }
    }
//    public static boolean hasProperty(Object o, String propertyName) {
//        if (o == null || propertyName == null) {
//            return false;
//        }
//        try
//        {
//            return PropertyUtils.getPropertyDescriptor(o, propertyName) != null;
//        }
//        catch (Exception e)
//        {
//            return false;
//        }
//    }
}
