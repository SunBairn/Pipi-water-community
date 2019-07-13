package com.zls.community.controller;

import com.zls.community.dto.AccessTokenDTO;
import com.zls.community.dto.GithubUser;
import com.zls.community.mapper.UserMapper;
import com.zls.community.pojo.User;
import com.zls.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Controller
public class AuthorizeController {
    @Autowired
    GithubProvider githubProvider;

    @Autowired
    UserMapper userMapper;

    @Value("${github.client.id}")
    String clientId;

    @Value("${github.client.secret}")
    String secret;

    @Value("${github.redirect.uri}")
    String redirectUri;


    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code, @RequestParam("state") String state,
                           HttpServletRequest request, HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_secret(secret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUSer(accessToken);
        if (githubUser != null&&githubUser.getId()!=null) {
            User user =new User();
            user.setAccoutId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setPortraitUrl(githubUser.getAvatar_url());
        final String token = UUID.randomUUID().toString();
        user.setToken(token);
        user.setCreateTime(System.currentTimeMillis());
        user.setModifityTime(user.getCreateTime());
        userMapper.insert(user);
        //写入到cookie中
        response.addCookie(new Cookie("token",token));
        //登录成功
        return "redirect:/";
    }else {
        //登录失败
        return "redirect:/";
    }

    }
}
