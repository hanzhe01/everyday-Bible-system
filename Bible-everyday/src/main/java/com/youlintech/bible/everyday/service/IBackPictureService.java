package com.youlintech.bible.everyday.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlintech.bible.common.core.domain.AjaxResult;
import com.youlintech.bible.everyday.domain.BackPicture;

import java.util.Collection;
import java.util.List;

/**
 * 背景图片Service接口
 *
 * @author oyhz
 * @date 2024-08-21
 */
public interface IBackPictureService extends IService<BackPicture> {

    /**
     * 查询背景图片列表
     *
     * @param backPicture 背景图片
     * @return 背景图片集合
     */
    public List<BackPicture> selectBackPictureList(BackPicture backPicture);

    /**
     * 新增背景图片
     * @param backPicture
     * @return
     */
    public AjaxResult addBackPicture(BackPicture backPicture);

    /**
     * 修改背景图片
     * @param backPicture
     * @return
     */
    public AjaxResult updateBackPicture(BackPicture backPicture);

    /**
     * 删除背景图片
     * @param list  id集合
     * @return
     */
    public AjaxResult removeBackPictureByIds(Collection<?> list);

}
