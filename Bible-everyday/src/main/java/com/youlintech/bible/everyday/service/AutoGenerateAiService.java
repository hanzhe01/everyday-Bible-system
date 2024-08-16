package com.youlintech.bible.everyday.service;

import com.youlintech.bible.common.core.domain.AjaxResult;

import java.util.List;

/**
 * @author oyhz
 */
public interface AutoGenerateAiService {
    /**
     * 自动生成每日圣经对象并且进行存储
     * @param bibleDate  生成日期
     * @return AjaxResult
     */
    AjaxResult generateEverydayBible(List<String> bibleDate);
}
