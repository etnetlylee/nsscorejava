package decoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import coreModel.Decoder;
import coreModel.NssData;
import coreModel.RawData;
import coreStorage.model.AsaStorage;
import model.IndexBaseInfo;
import util.DataHelper;

import static constants.AsaConstant.SCREENLIST_DUALFUTURESQUOTE;
import static constants.AsaConstant.SCREENLIST_FUTURESPRICEDEPTH;
import static constants.AsaConstant.SCREENLIST_FUTURESQUOTE;

public class Decoder36 extends Decoder {
    public static final String uniqueID = "36";

    @Override
    public NssData decodeStream(String code, RawData rawData) {
        final String value = (rawData.getData()).toString();
        AsaStorage data = getContext().getAsaStorage();
        if (value != null && value.trim() != "") {
            final List<String> products = Arrays.asList(value.split(":"));
            // final String productName = products[0];
            final String productInfo = products.get(1);

            final List<String> screens = Arrays.asList(productInfo.split("#"));

            for (String screenInfoTotal : screens) {
                final List<IndexBaseInfo> indexInfoList = new ArrayList<IndexBaseInfo>();
                final List<String> screenInfos = Arrays.asList(screenInfoTotal.split("@"));
                final String screenName = screenInfos.get(0);
                final String screenInfo = screenInfos.get(1);

                final List<String> levels = Arrays.asList(screenInfo.split("\\$")); // level

                if (data.INDEX_BASE_INFO_MAP.get(screenName) != null) {
                    data.INDEX_BASE_INFO_MAP.put(screenName, data.INDEX_BASE_INFO_MAP.get(screenName));
                } else {
                    data.INDEX_BASE_INFO_MAP.put(screenName, new ArrayList<IndexBaseInfo>());
                }
                List<IndexBaseInfo> screenList = data.INDEX_BASE_INFO_MAP.get(screenName);

                for (String levelInfoTotal : levels) {
                    final List<String> levelInfos = Arrays.asList(levelInfoTotal.split("="));
                    final String levleName = levelInfos.get(0);
                    final String levleInfo = levelInfos.get(1);

                    final List<String> indexs = Arrays.asList(levleInfo.split(",")); // should be common
                    for (String posString : indexs) {
                        IndexBaseInfo indexInfo = new IndexBaseInfo();
                        final List<String> names =
                                Arrays.asList(posString.split("|")); // variable name should be changed later.
                        final String posInfo = names.get(0);
                        final String indexCode = names.get(1);
                        final String tcName = names.get(2);
                        final String scName = names.get(3);
                        final String enName = names.get(4);

                        indexInfo.setLevel(levleName);
                        indexInfo.setPosition(posInfo);
                        indexInfo.setCode(indexCode);
                        indexInfo.setFullTcName(tcName);
                        indexInfo.setFullScName(scName);
                        indexInfo.setFullEnName(enName);

                        boolean exists = false;
                        for (IndexBaseInfo screenIndex : screenList) {
                            // final IndexBaseInfo target = screen[screenInfo];
                            if (DataHelper.isSameIndexBaseInfo(indexInfo, screenIndex)) {
                                exists = true;
                            }

                        }
                        if (!exists) {
                            indexInfoList.add(indexInfo);
                        }

                    }
                }

                // indexInfoList.sort(); // FIXME: why sort?

                if (screenList != null) {
                    screenList.addAll(indexInfoList);
                } else {
                    screenList = indexInfoList;
                }
                data.INDEX_BASE_INFO_MAP.put(screenName, screenList);

            }

            // mergeTVBChart();
            copyHandle(SCREENLIST_FUTURESPRICEDEPTH, SCREENLIST_DUALFUTURESQUOTE);
            copyHandle(SCREENLIST_FUTURESPRICEDEPTH, SCREENLIST_FUTURESQUOTE);
        }

        return null;
    }

    public void copyHandle(String fromScreen, String toScreen) {
        AsaStorage data = getContext().getAsaStorage();
        if (data.INDEX_BASE_INFO_MAP.get(fromScreen) != null) {
            data.INDEX_BASE_INFO_MAP.put(fromScreen, data.INDEX_BASE_INFO_MAP.get(fromScreen));
        } else {
            data.INDEX_BASE_INFO_MAP.put(fromScreen, new ArrayList<IndexBaseInfo>());
        }
        data.INDEX_BASE_INFO_MAP.put(toScreen, new ArrayList<IndexBaseInfo>()); // clear

        final List<IndexBaseInfo> fromList = data.INDEX_BASE_INFO_MAP.get(fromScreen);
        final List<IndexBaseInfo> toList = data.INDEX_BASE_INFO_MAP.get(toScreen);

        if (fromList.size() > 0) {
            for (IndexBaseInfo info : fromList) {
                final String code = info.getCode();
                final int parsedCode = Integer.parseInt(code);
                if (Math.abs(parsedCode) == Double.NaN) {
                    toList.add(info);
                }

            }
            data.INDEX_BASE_INFO_MAP.put(toScreen, toList);
        }
    }
}
