package com.zls.community.controller;

import com.zls.community.dto.PageDTO;
import com.zls.community.dto.QuestionDTO;
import com.zls.community.mapper.QuestionMapper;
import com.zls.community.mapper.UserMapper;
import com.zls.community.pojo.User;
import com.zls.community.service.ProfileServiece;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    ProfileServiece profileServiece;
    @Autowired
    UserMapper userMapper;
    @GetMapping("/profile/{section}")
    public String profile(HttpServletRequest request,
                          @PathVariable(value = "section") String section,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size,
                          Model model){

        User user=null;
        Cookie[] cookies = request.getCookies();
        if (cookies!=null&&cookies.length!=0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String value = cookie.getValue();
                    user=userMapper.findByToken(value);
                    if (user != null) {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        if (user==null){
            return "redirect:/";
        }
        model.addAttribute("section",section);
        if (section.equals("questions")){
            model.addAttribute("sectionName","我的问题");
            PageDTO pageDTO = profileServiece.findQuestionById(user.getId(), page, size);
            model.addAttribute("pageDTO", pageDTO);
        }else if (section.equals("newreply")){
            model.addAttribute("sectionName", "最新回复");
        }
        return "profile";
    }
}
