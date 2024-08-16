package com.youlintech.bible.everyday.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlintech.bible.common.core.domain.AjaxResult;
import com.youlintech.bible.everyday.domain.BibleView;

import java.util.List;

/**
 * 每日圣经展示Service接口
 *
 * @author oyhz
 * @date 2024-08-15
 */
public interface IBibleViewService extends IService<BibleView> {

    /**
     * 查询每日圣经展示列表
     *
     * @param bibleView 每日圣经展示
     * @return 每日圣经展示集合
     */
    public List<BibleView> selectBibleViewList(BibleView bibleView);

    /**
     * 新增每日圣经展示
     *
     * @param bibleView 每日圣经展示
     * @return 结果
     */
    public AjaxResult saveBibleView(BibleView bibleView);

    /**
     * 修改每日圣经展示 主要是ID值
     * @param bibleView 修改对象值
     * @return
     */
    public AjaxResult updateBibleView(BibleView bibleView);

}
