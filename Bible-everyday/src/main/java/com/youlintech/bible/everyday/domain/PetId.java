package com.youlintech.bible.everyday.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.youlintech.bible.common.annotation.Excel;
import com.youlintech.bible.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 白名单列表对象 t_pet
 *
 * @author oyhz
 * @date 2024-08-19
 */
@Data
@ToString
@NoArgsConstructor
@Accessors(chain = true)
@TableName("t_pet")
@ApiModel(value = "PetId", description = "白名单列表实体")
public class PetId extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 白名单id */
    @TableId(type= IdType.AUTO)
    @ApiModelProperty("白名单编号")
    private Long id;

    /** 白名单编号 */
    @Excel(name = "白名单编号")
    @ApiModelProperty("白名单ID")
    private String petId;
    /** 白名单编号 */
    @Excel(name = "网址+uuid")
    @ApiModelProperty("网址+url")
    private String urlUuid;
}
