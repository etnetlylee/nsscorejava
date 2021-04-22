package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import coreModel.ListChanges;
import coreModel.ListItemChanges;
import coreStorage.model.Transaction;
import model.IndexBaseInfo;

public class DataHelper {
    public static double stringToDouble(String str) {
        double value = 0.0;
        if (str != null) {
            str = str.trim();
            if (str.length() > 0) {
                int nPos = str.length() - 1;
                String numberStr = str.substring(0, nPos);
                if (numberStr.length() > 1) {
                    value = Double.parseDouble(numberStr);
                }
                String ch = String.valueOf(str.charAt(nPos));
                if (ch == "M" || ch == "m") {
                    value *= 1000000.0;
                } else if (ch == "K" || ch == "k") {
                    value *= 1000.0;
                } else {
                    value = Double.parseDouble(str);
                }
            }
        }
        return value;
    }

    public static double formatNumber(double value) {
        double format;
        if (value == 0) {
            format = 0;
        } else if (value == 1.00) {
            format = 1.00;
        } else {
            String formatsString = String.format("%.2f", value);
            if (formatsString == "0.00") {
                format = 0.01;
            } else if (formatsString == "1.00") {
                format = 0.99;
            } else {
                format = Double.parseDouble(formatsString);
            }
        }
        return format;
    }

    public static boolean isSameIndexBaseInfo(IndexBaseInfo src, IndexBaseInfo target) {
        boolean ret = true;

        ret = ret && src.getCode().toLowerCase() == target.getCode().toLowerCase();
        ret =
                ret && src.getLevel().toLowerCase() == target.getLevel().toLowerCase();
        ret = ret &&
                src.getPosition().toLowerCase() == target.getLevel().toLowerCase();

        return ret;
    }

    public static void insertSorted(List sortedList, String value) {
        int min = 0;
        int max = sortedList.size();
        int index = (int) ((min + max) / 2);
        while (max > min) {
            final String element = String.valueOf(sortedList.get(index));
            final int comp = element.compareTo(value);
            if (comp == 0) {
                max = index;
            } else {
                min = index + 1;
            }
            index = (int) ((min + max) / 2);
        }
        sortedList.add(index, value);
    }

    public static int binarySearch(List sortedList, String value) {
        int min = 0;
        int max = sortedList.size();
        while (min < max) {
            final int mid = min + ((max - min) >> 1);
            final String element = String.valueOf(sortedList.get(mid));
            final int comp = element.compareTo(value);
            if (comp == 0) {
                return mid;
            }
            if (comp < 0) {
                min = mid + 1;
            } else {
                max = mid;
            }
        }
        return -1;
    }

    /**
     * Deprecated, use DataHelper.getTransaction() instead
     *
     * @param source         data source
     * @param dataName       data set name
     * @param fieldId        field
     * @param groupTimestamp search key
     */
    static Transaction getData(
            List<Transaction> source, String dataName, String fieldId, int groupTimestamp) {
        return DataHelper.getTransaction(source, dataName, fieldId, groupTimestamp);
    }

    /**
     * Return data object with key [groupTimestamp]
     *
     * @param source         data source
     * @param dataName       data set name
     * @param fieldId        field
     * @param groupTimestamp search key
     */
    static Transaction getTransaction(List<Transaction> source, String dataName,
                                      String fieldId, int groupTimestamp) {
        Transaction item = null;
        if (source == null) {
            return null;
        }
        for (int i = source.size() - 1; i >= 0; i--) {
            Transaction record = source.get(i);
            if (record.getGroupTimestamp() == groupTimestamp) {
                item = record;
                break;
            }
        }
        return item;
    }

    static String boolToInt(boolean val) {
        return val ? "1" : "0";
    }

    static String formatFullCode(String code) {
        return code;
    }

    static boolean isEmpty(Object map) {
        if (map instanceof Map) {
            if (((Map) map).size() > 0) {
                return false;
            }

        } else if (map instanceof List) {
            if (((List) map).size() > 0) {
                return false;
            }
        } else {
            return map != null;
        }
        return true;
    }

    public static List<String> getFullList(List<String> list1, List<String> list2) {
        list1.removeAll(list2);
        list1.addAll(list2);
        return list1;
    }

    public static List<String> filterList(List<String> fullList, List<String> deleteList) {
        fullList.removeAll(deleteList);
        return fullList;
    }


    static ListChanges detectListChanges(List<String> left, List<String> right) {
        List<String> _fullList = new ArrayList<String>();

        List<String> newInList = new ArrayList<String>();
        List<String> oldInList = new ArrayList<String>();
        List<ListItemChanges> ordInList = new ArrayList<ListItemChanges>();

        _fullList = getFullList(left, right);

        // Find new values
        newInList.addAll(filterList(_fullList, left));
        // Find old values, not in list anymore
        oldInList.addAll(filterList(_fullList, right));

        // Find order changed
        for (int ind = 0; ind < right.size(); ind++) {
            String value = right.get(ind);
            int pos = left.indexOf(value);
            if (pos != -1 && pos != ind) {
                ordInList.add(new ListItemChanges(value, pos, ind));
            }
        }

        return new ListChanges(newInList, oldInList, ordInList);
    }
}
