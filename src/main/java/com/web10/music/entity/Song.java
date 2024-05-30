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
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 歌曲表
 * </p>
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "歌曲", description = "歌曲对象")
@EqualsAndHashCode(callSuper = false)
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "歌曲id")
    private String songId;

    @ApiModelProperty(value = "歌手id")
    private String singerId;

    @ApiModelProperty(value = "封面url")
    private String pic;

    @ApiModelProperty(value = "歌词")
    private String lyric;

    @ApiModelProperty(value = "歌曲url")
    private String url;

    @ApiModelProperty(value = "歌曲名")
    private String name;

    @ApiModelProperty(value = "歌曲专辑名")
    private String albumName;

    @ApiModelProperty(value = "歌曲时长")
    private String duration;

    @ApiModelProperty(value = "歌手名")
    private String singerName;

    @ApiModelProperty(value = "简介")
    private String introduction;

    @ApiModelProperty(value = "播放次数")
    private int playCount;

    @ApiModelProperty(value = "发行时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
