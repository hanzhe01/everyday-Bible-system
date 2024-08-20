package com.youlintech.bible.everyday.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author oyhz
 * 前端每日圣经展示类
 */
@Data
public class BibleEventsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 每日圣经id */
    @ApiModelProperty("每日圣经id")
    private Long id;

    /** 经文诗节标题 */
    @ApiModelProperty("经文诗节标题")
    private String verse;

    /** 经文诗节内容 */
    @ApiModelProperty("经文诗节内容")
    private String verseContent;

    /** 经文日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("经文日期")
    private Date bibleDate;

    /** 历史今日事件 */
    @ApiModelProperty("历史今日事件")
    private String historyTodayEvents;

}
