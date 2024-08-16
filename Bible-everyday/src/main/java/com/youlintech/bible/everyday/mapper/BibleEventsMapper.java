package com.youlintech.bible.everyday.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.youlintech.bible.everyday.domain.BibleEvents;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * 每日圣经Mapper接口
 *
 * @author oy
 * @date 2024-08-14
 */
@Mapper
public interface BibleEventsMapper extends BaseMapper<BibleEvents> {
    /**
     * 通过日期查询圣经
     *
     * @param bibleDate 经文日期
     * @return 每日圣经对象
     */
    BibleEvents getOneByBibleDate(@Param("bibleDate") Date bibleDate);

}
