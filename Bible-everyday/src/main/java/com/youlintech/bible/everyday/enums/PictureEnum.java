package com.youlintech.bible.everyday.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author oyhz
 */

public enum PictureEnum {
    /**
     * IS_ENABLE  启用
     * IS_DISABLE 禁用
     */
    IS_ENABLE(0,"启用"),
    NOT_ENABLE(1,"禁用");

    @EnumValue
    private Integer code;
    @JsonValue
    private String name;

    PictureEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){}
}
