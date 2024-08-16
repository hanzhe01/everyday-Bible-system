package com.youlintech.bible.everyday.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.youlintech.bible.common.annotation.Excel;
import com.youlintech.bible.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 每日圣经对象 t_bible_events
 *
 * @author oy
 * @date 2024-08-14
 */
@Data
@ToString
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_bible_events")
@ApiModel(value = "TBibleEvents", description = "每日圣经实体")
public class BibleEvents extends BaseEntity implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 每日圣经id */
    @TableId(type= IdType.ASSIGN_ID)
    @ApiModelProperty("每日圣经id")
    private Long id;

    /** 经文诗节标题 */
    @Excel(name = "经文诗节标题")
    @ApiModelProperty("经文诗节标题")
    private String verse;

    /** 经文诗节内容 */
    @Excel(name = "经文诗节内容")
    @ApiModelProperty("经文诗节内容")
    private String verseContent;

    /** 经文日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "经文日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("经文日期")
    private Date bibleDate;

    /** 历史今日事件 */
    @Excel(name = "历史今日事件")
    @ApiModelProperty("历史今日事件")
    private String historyTodayEvents;

}
