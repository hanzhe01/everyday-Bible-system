package com.youlintech.bible.everyday.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.youlintech.bible.common.annotation.Excel;
import com.youlintech.bible.common.core.domain.BaseEntity;
import com.youlintech.bible.everyday.enums.PictureEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 背景图片对象 t_back_picture
 *
 * @author oyhz
 * @date 2024-08-21
 */
@Data
@ToString
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_back_picture")
@ApiModel(value = "BackPicture", description = "背景图片实体")
public class BackPicture extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 背景图片id */
    @TableId(type= IdType.AUTO)
    @ApiModelProperty("背景图片id")
    private Long id;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty("上传时间")
    private Date uploadTime;

    /** 图片名字 */
    @Excel(name = "图片名字")
    @ApiModelProperty("图片名字")
    private String imgName;

    /** 图片 */
    @Excel(name = "图片")
    @ApiModelProperty("图片")
    private String picture;

    /** 是否启用 */
    @Excel(name = "是否启用")
    @ApiModelProperty("是否启用")
    private PictureEnum isBg;

}
