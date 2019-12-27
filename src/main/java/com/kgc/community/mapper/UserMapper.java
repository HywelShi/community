package com.kgc.community.mapper;

import com.kgc.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    /**
     * 插入用户
     * @param user
     */
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified)values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    public void insert(User user);

    /**
     * 根据cookie中的token查询用户
     * @param token
     * @return
     */
    @Select("select * from user where token = #{token}")
    public User selectByToken(@Param("token") String token);
}