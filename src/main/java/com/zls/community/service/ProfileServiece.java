package com.zls.community.service;

import com.zls.community.dto.PageDTO;
import com.zls.community.dto.QuestionDTO;
import com.zls.community.mapper.QuestionMapper;
import com.zls.community.mapper.UserMapper;
import com.zls.community.pojo.Question;
import com.zls.community.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiece {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    public PageDTO findQuestionById(Integer id, Integer page, Integer size) {
        PageDTO pageDTO = new PageDTO();
        Integer total=questionMapper.countById(id);
        Integer totalPage=0;
        if (total%size==0){
            totalPage=total/size;
        }else{
            totalPage=total/size+1;
        }
        //加这个判断是为了防止我们手动输入页码时在后台查数据库的时候offset出错,很关键
        if (page>totalPage){
            page=totalPage;
        }
        if (page<1){
            page=1;
        }
        if (total==0){
            pageDTO.setPageNull();
        }else {
            pageDTO.setPagination(totalPage,page);
            //查询指定页面的question
            Integer offsize = size * (page - 1);
            List<Question> questions = questionMapper.findById(id, offsize, size);
            List<QuestionDTO> questionDTOS = new ArrayList<>();
            for (Question question : questions) {
                QuestionDTO questionDTO=new QuestionDTO();
                //将question中的属性复制到questionDTO对象里面
                BeanUtils.copyProperties(question,questionDTO);
                //根据question中的Creator查询头像
                User user =userMapper.findById(question.getCreator());
                questionDTO.setUser(user);
                questionDTOS.add(questionDTO);
            }
            pageDTO.setQuestions(questionDTOS);
        }
        return pageDTO;
    }
}

