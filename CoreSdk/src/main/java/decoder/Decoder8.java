package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;
import model.StockInfo;
import util.DataHelper;

import static constants.SecurityType.SECURITYTYPE_CBBC;
import static constants.SecurityType.SECURITYTYPE_STOCK;
import static constants.SecurityType.SECURITYTYPE_WARRANT;

public class Decoder8 extends Decoder {
    static final String uniqueID = "8";

    static final String TYPE_ALL = "ALL";
    static final String TYPE_WAR_OTHER = "WAR_OTHER";
    static final String TYPE_CALL = "CALL";
    static final String TYPE_PUT = "PUT";
    static final String TYPE_BULL = "BULL";
    static final String TYPE_BEAR = "BEAR";
    static final String TYPE_WAR_INL = "WAR_INL";

    static final String STKINFO_TYPE_WARRANT = "WARRANT";
    static final String STKINFO_TYPE_CALL = "WAR_CALL";
    static final String STKINFO_TYPE_PUT = "WAR_PUT";
    static final String STKINFO_TYPE_BULL = "CBBC_BULL";
    static final String STKINFO_TYPE_BEAR = "CBBC_BEAR";

    static final String CBBC_TYPE_BULL_R = "CBBC_BULL_R";
    static final String CBBC_TYPE_BULL_N = "CBBC_BULL_N";
    static final String CBBC_TYPE_BEAR_R = "CBBC_BEAR_R";
    static final String CBBC_TYPE_BEAR_N = "CBBC_BEAR_N";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            data.BOND_LIST = new ArrayList<String>();
            data.REIT_LIST = new ArrayList<String>();
            data.SPECIAL_UNDERLYING_LIST = new ArrayList<String>();

            if (value == null) {
                return null;
            }

            final List<String> values = Arrays.asList(value.split("|"));

            for (String val : values) {
                final List<String> valueCodes = Arrays.asList(val.split(","));
                Map<String, List<String>> relatedMap = new HashMap<String, List<String>>(); // name => list
                final String codeString = valueCodes.get(0);
                final List<String> securityInfo = Arrays.asList(codeString.split(" "));
                final String securityCode = securityInfo.get(0);
                final String securityType = securityInfo.get(1);

                String type = "STOCK";

                if (securityType == "BON") {
                    data.BOND_LIST.add(securityCode);
                } else if (securityType == "TRT_REI") {
                    data.REIT_LIST.add(securityCode);
                } else if (securityType == "null") {
                    type = "NONE";
                }
                if (data.STKTYPEMAP.containsKey(securityCode)) {
                    data.STKTYPEMAP.put(securityCode, SECURITYTYPE_STOCK);
                }


                if (DataHelper.binarySearch(data.VALIDCODE, securityCode) == -1) {
                    DataHelper.insertSorted(data.VALIDCODE, securityCode);
                }

                if (data.STOCK_INFO_MAP.get(securityCode) == null) {
                    data.STOCK_INFO_MAP.put(securityCode, new StockInfo(null, null, null, null, null, null));
                }

                StockInfo stockInfo = data.STOCK_INFO_MAP.get(securityCode);
                stockInfo.setType(type);
                if (valueCodes.size() == 1) {
                    data.UNDERLYINGMAP.put(securityCode, new HashMap<String, List<String>>());
                } else {
//                    RegExp valideCodeRegEx = RegExp(r"/^[0-9]+$/i");
                    Pattern valideCodeRegEx = Pattern.compile("[0-9]*");
                    if (!valideCodeRegEx.matcher(securityCode).matches()) {
                        data.SPECIAL_UNDERLYING_LIST.add(securityCode);
                    }
                    List<String> allList = new ArrayList<String>();
                    List<String> callList = new ArrayList<String>();
                    List<String> putList = new ArrayList<String>();
                    List<String> bullList = new ArrayList<String>();
                    List<String> bearList = new ArrayList<String>();
                    List<String> inlineWarrantList = new ArrayList<String>();
                    for (int j = 1; j < valueCodes.size(); j++) {
                        String typeString = valueCodes.get(j);
                        final List<String> types = Arrays.asList(typeString.split(" "));
                        if (DataHelper.binarySearch(data.VALIDCODE, types.get(0)) == -1) {
                            DataHelper.insertSorted(data.VALIDCODE, types.get(0));
                        }
                        data.UNDERLYINGCODE.put(types.get(0), securityCode);
                        final List<String> _temp = new ArrayList<String>();
                        _temp.add(securityCode);
                        data.RELATE_STOCKMAP.get(types.get(0)).addAll(_temp);
                        allList.add(types.get(0));
                        if (typeString.contains(Decoder8.TYPE_WAR_OTHER)) {
                            data.STKTYPEMAP.put(types.get(0), SECURITYTYPE_WARRANT);
                            StockInfo info = data.STOCK_INFO_MAP.get(types.get(0));
                            if (info == null) {
                                info = new StockInfo(null, null, null, null, null, null);
                                data.STOCK_INFO_MAP.put(types.get(0), info);
                            }
                            info.setType(Decoder8.STKINFO_TYPE_WARRANT);
                        } else if (typeString.contains(Decoder8.TYPE_CALL)) {
                            callList.add(types.get(0));
                            data.STKTYPEMAP.put(types.get(0), SECURITYTYPE_WARRANT);
                            data.UNDERLYINGDETIAL.put(types.get(0), Decoder8.STKINFO_TYPE_CALL);
                            StockInfo info = data.STOCK_INFO_MAP.get(types.get(0));
                            if (info == null) {
                                info = new StockInfo(null, null, null, null, null, null);
                                data.STOCK_INFO_MAP.put(types.get(0), info);
                            }
                            info.setType(Decoder8.STKINFO_TYPE_CALL);
                        } else if (typeString.contains(Decoder8.TYPE_PUT)) {
                            putList.add(types.get(0));
                            data.STKTYPEMAP.put(types.get(0), SECURITYTYPE_WARRANT);
                            data.UNDERLYINGDETIAL.put(types.get(0), Decoder8.STKINFO_TYPE_PUT);

                            StockInfo info = data.STOCK_INFO_MAP.get(types.get(0));
                            if (info == null) {
                                info = new StockInfo(null, null, null, null, null, null);
                                data.STOCK_INFO_MAP.put(types.get(0), info);
                            }
                            info.setType(Decoder8.STKINFO_TYPE_PUT);
                        } else if (typeString.contains(Decoder8.TYPE_BULL)) {
                            bullList.add(types.get(0));
                            data.STKTYPEMAP.put(types.get(0), SECURITYTYPE_CBBC);
                            data.UNDERLYINGDETIAL[types[0]] = Decoder8.STKINFO_TYPE_BULL;
                            if (types.length == 3 && types[2] == "R") {
                                data.CBBC_RN_MAP[types[0]] = Decoder8.CBBC_TYPE_BULL_R;
                            } else if (types.length == 3 && types[2] == "N") {
                                data.CBBC_RN_MAP[types[0]] = Decoder8.CBBC_TYPE_BULL_N;
                            }
                            StockInfo info = data.STOCK_INFO_MAP[types[0]];
                            if (info == null) {
                                info = StockInfo(null, null, null, null);
                                data.STOCK_INFO_MAP[types[0]] = info;
                            }
                            info.setType(Decoder8.STKINFO_TYPE_BULL);
                        } else if (typeString.contains(Decoder8.TYPE_BEAR)) {
                            bearList.add(types[0]);
                            data.STKTYPEMAP[types[0]] = SECURITYTYPE_CBBC;
                            data.UNDERLYINGDETIAL[types[0]] = Decoder8.STKINFO_TYPE_BEAR;
                            if (types.length == 3 && types[2] == "R") {
                                data.CBBC_RN_MAP[types[0]] = Decoder8.CBBC_TYPE_BEAR_R;
                            } else if (types.length == 3 && types[2] == "N") {
                                data.CBBC_RN_MAP[types[0]] = Decoder8.CBBC_TYPE_BEAR_N;
                            }
                            StockInfo info = data.STOCK_INFO_MAP[types[0]];
                            if (info == null) {
                                info = StockInfo(null, null, null, null);
                                data.STOCK_INFO_MAP[types[0]] = info;
                            }
                            info.setType(Decoder8.STKINFO_TYPE_BEAR);
                        } else if (typeString.contains(Decoder8.TYPE_WAR_INL)) {
                            inlineWarrantList.add(types[0]);
                            data.STKTYPEMAP[types[0]] = Decoder8.STKINFO_TYPE_WARRANT;
                            data.UNDERLYINGDETIAL[types[0]] = Decoder8.TYPE_WAR_INL;
                            StockInfo info = data.STOCK_INFO_MAP[types[0]];
                            if (info == null) {
                                info = StockInfo(null, null, null, null);
                                data.STOCK_INFO_MAP[types[0]] = info;
                            }
                            info.setType(Decoder8.TYPE_WAR_INL);
                        }
                    }
                    if (allList.length != 0) {
                        relatedMap[Decoder8.TYPE_ALL] = allList;
                    }
                    if (callList.length != 0) {
                        relatedMap[Decoder8.TYPE_CALL] = callList;
                    }
                    if (putList.length != 0) {
                        relatedMap[Decoder8.TYPE_PUT] = putList;
                    }
                    if (bullList.length != 0) {
                        relatedMap[Decoder8.TYPE_BULL] = bullList;
                    }
                    if (bearList.length != 0) {
                        relatedMap[Decoder8.TYPE_BEAR] = bearList;
                    }
                    if (inlineWarrantList.length > 0) {
                        relatedMap[Decoder8.TYPE_WAR_INL] = inlineWarrantList;
                    }
                    data.UNDERLYINGMAP[securityCode] = relatedMap;
                }
            }

            return null;
        }
    }
}
