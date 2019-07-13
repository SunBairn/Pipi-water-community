package com.zls.community.mapper;

import com.zls.community.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user(accout_id,name,token,gmt_create,gmt_modified,portrait_url) value (#{accoutId},#{name},#{token},#{createTime} ,#{modifityTime},#{portraitUrl})")
    public void insert(User user);

    @Select("select * from user where token=#{value} ")
    User findByToken(@Param("value") String value);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer creator);
}
