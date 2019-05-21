package com.shiro.mapper;

import com.shiro.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE name = #{name}")
    @Results({@Result(property = "id",column = "id"),@Result(property = "name",column = "name"),@Result(property = "password",column = "password"),
            @Result(property = "perms",column = "perms")})
    public User findUserByName(String name);

    @Select("SELECT * FROM user WHERE id = #{id}")
    @Results({@Result(property = "id",column = "id"),@Result(property = "name",column = "name"),@Result(property = "password",column = "password"),
            @Result(property = "perms",column = "perms")})
    public User findUserByID(Integer id);
}
