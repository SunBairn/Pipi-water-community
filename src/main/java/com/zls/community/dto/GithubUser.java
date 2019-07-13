package com.zls.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    private  String name;
    private  Long id;
    private String bid; //描述
    private String avatar_url;//头像地址


}
