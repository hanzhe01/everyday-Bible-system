package com.youlintech.bible.everyday.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 * 日期枚举类
 *
 * @author oyhz
 */
public enum BibleDayEnum {
    /**
     * 表示今天的日期，通常用于初始化或默认选择当前日期。
     */
    TODAY(0, "今天"),
    /**
     * 表示昨天的日期，通常用于初始化或默认选择当前日期。
     */
    YESTERDAY(1, "昨天"),
    /**
     * 表示前天的日期，通常用于初始化或默认选择当前日期。
     */
    BEFORE_YESTERDAY(2, "前天"),
    /**
     * 表示大前天的日期，通常用于初始化或默认选择当前日期。
     */
    THREE_DAYS_AGO(3, "大前天"),
    OTHER_DAYS(4, "其他");

    @EnumValue // 标记数据库存的值是code
    private final int code;
    @JsonValue // 标记响应json值
    private final String name;
    private static final Map<Integer, BibleDayEnum> LOOKUP = new HashMap<>();
    private static final Map<String, BibleDayEnum> NAME_LOOKUP = new HashMap<>();

    static {
        for (BibleDayEnum day : values()) {
            LOOKUP.put(day.code, day);
            NAME_LOOKUP.put(day.name, day);
        }
    }

    BibleDayEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static BibleDayEnum getByCode(int code) {
        return LOOKUP.get(code);
    }

    public static BibleDayEnum getByName(String name) {
        return NAME_LOOKUP.get(name);
    }
}
