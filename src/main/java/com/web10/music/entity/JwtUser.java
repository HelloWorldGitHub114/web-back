package com.web10.music.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class JwtUser {

    Integer id;

    private List<String> roles;//用一对多映射查询，联u,u-r,r三表，集合里只放role的name

    private  List<String> permissions;//权限列表

    public JwtUser() {
    }
}
