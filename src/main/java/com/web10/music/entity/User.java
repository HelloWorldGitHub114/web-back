package com.web10.music.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "User对象", description = "用户表")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

    //序列化版本号
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    @JsonIgnore
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "电话号码")
    private String phoneNum;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "生日")
    private LocalDateTime birth;

    @ApiModelProperty(value = "个性签名")
    private String introduction;

    @ApiModelProperty(value = "地区")
    private String location;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "随机盐")
    @JsonIgnore
    private String salt;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    @JsonIgnore
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新时间")
    @JsonIgnore
    private LocalDateTime updateTime;

    @TableField(exist = false)
    //@ApiModelProperty(value = "定义角色集合")
    @JsonIgnore
    private List<Role> roles;

    @ApiModelProperty(value = "定义角色集合")
    public List<String> getRolesString() {
        if(roles == null) return null;
        List<String> rolesString = new ArrayList<>();
        for (Role role : roles) {
            rolesString.add(role.getName());
        }
        return rolesString;
    }

    @ApiModelProperty(value = "定义权限集合")
    public List<String> getPermissionsString() {
        if(roles == null) return null;
        List<String> permsString = new ArrayList<>();
        for (Role role : roles) {
            for (Permission permission : role.getPerms()) {
                permsString.add(permission.getName());
            }
        }
        return permsString;
    }
}
