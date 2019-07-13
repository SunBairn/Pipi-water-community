package com.zls.community.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String accoutId;
    private String name;
    private String token;
    private long createTime;
    private long modifityTime;
    private String portraitUrl;
}
