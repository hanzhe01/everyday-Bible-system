package com.youlintech.bible.everyday.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlintech.bible.everyday.domain.BibleEvents;

import java.util.List;

/**
 * 每日圣经Service接口
 *
 * @author oy
 * @date 2024-08-14
 */
public interface IBibleEventsService extends IService<BibleEvents> {

    /**
     * 查询每日圣经列表
     *
     * @param tBibleEvents 每日圣经
     * @return 每日圣经集合
     */
    public List<BibleEvents> selectBibleEventsList(BibleEvents tBibleEvents);

    /**
     * 批量导入
     * @param bibleEventsList  集合数据
     * @param operName  操作人
     * @return 返回结果
     */
    public String importBibleEvents(List<BibleEvents> bibleEventsList, String operName);



}
