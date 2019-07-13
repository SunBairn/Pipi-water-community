package com.zls.community.mapper;

import com.zls.community.dto.QuestionDTO;
import com.zls.community.pojo.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) value (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void insertQuestion(Question question);

    @Select("select * from question limit #{offsize},#{size}")
    List<Question> findAllQuestion(@Param("offsize") Integer offsize,@Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator=#{id} limit #{offsize},#{size}")
    List<Question> findById(@Param("id") Integer id,@Param("offsize") Integer offsize,@Param("size") Integer size);

    @Select("select count(1) from question where creator=#{id} ")
    Integer countById(@Param("id")  Integer id);
}
