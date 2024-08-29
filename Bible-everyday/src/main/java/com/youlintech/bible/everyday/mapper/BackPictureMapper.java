package com.youlintech.bible.everyday.mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlintech.bible.everyday.domain.BackPicture;
import org.apache.ibatis.annotations.Mapper;

/**
 * 背景图片Mapper接口
 *
 * @author oyhz
 * @date 2024-08-21
 */
@Mapper
public interface BackPictureMapper extends BaseMapper<BackPicture> {
    /**
     * 根据图片地址查询对象
     * @param picture
     * @return
     */
    BackPicture getOneByPicture(@Param("picture") String picture);
    /**
     * 根据是否是背景图片查询对象
     * @param isBg
     * @return
     */
    BackPicture getOneByIsBg(@Param("isBg") Integer isBg);
}
