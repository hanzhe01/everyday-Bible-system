package com.youlintech.bible.everyday.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.youlintech.bible.common.annotation.Excel;
import com.youlintech.bible.common.core.domain.BaseEntity;
import com.youlintech.bible.everyday.enums.BibleDayEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 每日圣经展示对象 t_bible_view
 *
 * @author oyhz
 * @date 2024-08-15
 */
@Data
@ToString
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_bible_view")
@ApiModel(value = "BibleView", description = "每日圣经展示实体")
public class BibleView extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @TableId(type= IdType.AUTO)
    @ApiModelProperty("编号")
    private Long id;

    /** 发布日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("发布日期")
    private Date bibleDate;

    /** 圣经素材id */
    @Excel(name = "圣经素材id")
    @ApiModelProperty("圣经素材id")
    private Long bibleId;

    /** 圣经日期 */
    @Excel(name = "圣经日期")
    @ApiModelProperty("圣经日期")
    private BibleDayEnum bibleName;

}
