package com.web10.music.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 * Mv
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "mv", description = "mv表")
public class Mv implements Serializable {
  //序列化版本号
  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键")
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  @ApiModelProperty(value = "封面url")
  private String cover;

  @ApiModelProperty(value = "名字")
  private String name;

  @ApiModelProperty(value = "描述")
  private String introduction;

  @ApiModelProperty(value = "播放链接")
  private String url;

  @ApiModelProperty(value = "歌手名")
  private String artistName;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @ApiModelProperty(value = "发行时间")
  private String publishTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @TableField(fill = FieldFill.INSERT)
  @ApiModelProperty(value = "创建时间（自动生成）")
  private LocalDateTime createTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  @TableField(fill = FieldFill.INSERT_UPDATE)
  @ApiModelProperty(value = "更新时间（自动生成）")
  private LocalDateTime updateTime;

  @ApiModelProperty(value = "播放量")
  private Integer playCount;
}
