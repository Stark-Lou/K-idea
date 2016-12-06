package whu.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于储存信息，避免重复运算
 */
public class SystemInfo {
    public static List<KLineGraph> list;

    public static List<KLineGraph> getList() {
        return list;
    }

    public static void setList(List<KLineGraph> list) {
        SystemInfo.list = list;
    }
}
