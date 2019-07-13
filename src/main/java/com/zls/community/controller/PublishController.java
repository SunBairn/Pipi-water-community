package com.zls.community.controller;

import com.zls.community.mapper.QuestionMapper;
import com.zls.community.mapper.UserMapper;
import com.zls.community.pojo.Question;
import com.zls.community.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/doPublish")
    public String doPublish(@RequestParam("title")String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            HttpServletRequest request,
                            Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description",description);
        model.addAttribute("tag", tag);
        if (title.equals("")){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description.equals("")){
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag.equals("")){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
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
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getId());
        question.setTag(tag);
        questionMapper.insertQuestion(question);
        return "redirect:/";
    }
}
