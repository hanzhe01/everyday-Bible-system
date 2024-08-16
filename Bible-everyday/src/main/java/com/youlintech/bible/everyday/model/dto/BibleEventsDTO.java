package com.youlintech.bible.everyday.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "TBibleEventsDTO", description = "每日圣经DTO")
public class BibleEventsDTO implements Serializable {
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
