package com.web10.music.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 评论表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "评论", description = "评论表，可以在song，song list，mv下评论")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment implements Serializable {

    //序列化版本号
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户昵称")
    @TableField(exist = false)
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    @TableField(exist = false)
    private String avatar;

    @ApiModelProperty(value = "歌曲id")
    private Integer songId;

    @ApiModelProperty(value = "歌单id")
    private Integer songListId;

    @ApiModelProperty(value = "mv id")
    private Integer mvId;

    @ApiModelProperty(value = "评论正文")
    private String content;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "点赞数")
    private Integer up;


}
