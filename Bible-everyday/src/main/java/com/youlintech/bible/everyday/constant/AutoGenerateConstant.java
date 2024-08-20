package com.youlintech.bible.everyday.constant;

/**
 * @author oyhz
 */
public class AutoGenerateConstant {
    /**
     * 本地自动生成URL请求地址
     */
    public static final String AUTO_GENERATE_URL = "http://127.0.0.1:7978/auto/Generate/everydayBible";

    /**
     * 自动生成的存储在redis中，存储key
     */
    public static final String AUTO_GENERATE_BIBLE_KEY = "autogenerate:bible";
    public static final Integer AUTO_GENERATE_MAX_SIZE = 7;
    public static final String AUTO_GENERATE_PREFIX = "```json";
    public static final String AUTO_GENERATE_JSON_PREFIX = "[";
    public static final String AUTO_GENERATE_JSON_SUFFIX = "]";
}

