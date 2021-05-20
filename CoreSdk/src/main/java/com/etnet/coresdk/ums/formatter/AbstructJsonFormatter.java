package com.etnet.coresdk.ums.formatter;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import com.etnet.content.data.Quote;

public abstract class AbstructJsonFormatter {
    private static final Logger logger = Logger.getLogger(AbstructJsonFormatter.class);
    protected static final String EMPTYSTRING = "";
    protected static final String SPACESTRING = " ";
    protected static final String DEFAULT = "-";

    protected static List<Object> getJsonListObject(JSONObject jsonObject, String field, Class<?> c) {
        try {
            if (jsonObject.has(field)) {
                JSONArray array = jsonObject.getJSONArray(field);
                if (array != null) {
                    List<Object> list = new ArrayList<Object>();
                    for (int i = 0; i < array.length(); i++) {
                        if (StringUtils.isBlank(array.getString(i))) {
                            list.add(null);
                        } else {
                            if (c.equals(Double.class)) {
                                list.add(array.getDouble(i));
                            } else if (c.equals(Long.class)) {
                                list.add(array.getLong(i));
                            } else if (c.equals(Integer.class)) {
                                list.add(array.getInt(i));
                            } else if (c.equals(String.class)) {
                                list.add(array.getString(i));
                            } else {
                                list.add(array.get(i));
                            }
                        }
                    }
                    return list;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (JSONException e) {
            logger.error("getJsonListObject: " + field, e);
            return null;
        }
    }
    protected static List<Object> getJsonListObject(JSONObject jsonObject, String field, String item, Class<?> c) {
        try {
            if (jsonObject.has(field)) {
                JSONArray array = jsonObject.getJSONArray(field);
                if (array != null) {
                    List<Object> list = new ArrayList<Object>();
                    for (int i = 0; i < array.length(); i++) {
                        if (StringUtils.isBlank(array.getJSONObject(i).getString(item).toString())) {
                            list.add(null);
                        } else {
                            if (c.equals(Double.class)) {
                                list.add(array.getJSONObject(i).getDouble(item));
                            } else if (c.equals(Integer.class)) {
                                list.add(array.getJSONObject(i).getInt(item));
                            } else if (c.equals(String.class)) {
                                list.add(array.getJSONObject(i).getString(item));
                            } else {
                                list.add(array.get(i));
                            }
                        }
                    }
                    return list;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (JSONException e) {
            logger.error("getJsonListObject: " + field, e);
            return null;
        }
    }
    protected static Object getJsonObject(JSONObject jsonObject, String field, Class<?> c) {
        try {
            if (jsonObject.has(field)) {
                if (StringUtils.isNotBlank(jsonObject.getString(field))) {
                    if (c.equals(String.class)) {
                        return jsonObject.getString(field);
                    } else if (c.equals(Integer.class)) {
                        return jsonObject.getInt(field);
                    } else if (c.equals(Double.class)) {
                        return jsonObject.getDouble(field);
                    } else if (c.equals(Long.class)) {
                        return jsonObject.getLong(field);
                    } else {
                        return jsonObject.get(field);
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (JSONException e) {
            logger.error("getJsonObject: " + field, e);
            return null;
        }
    }
    protected static Object getJsonObjectNotEmpty(JSONObject jsonObject, String field, Class<?> c) {
        try {
            if (jsonObject.has(field)) {
                if (StringUtils.isNotEmpty(jsonObject.getString(field))) {
                    if (c.equals(String.class)) {
                        return jsonObject.getString(field);
                    } else if (c.equals(Integer.class)) {
                        return jsonObject.getInt(field);
                    } else if (c.equals(Double.class)) {
                        return jsonObject.getDouble(field);
                    } else if (c.equals(Long.class)) {
                        return jsonObject.getLong(field);
                    } else {
                        return jsonObject.get(field);
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } catch (JSONException e) {
            logger.error("getJsonObject: " + field, e);
            return null;
        }
    }
}