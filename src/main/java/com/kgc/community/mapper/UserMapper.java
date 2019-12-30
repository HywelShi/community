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
    @Insert("insert into user(name,account_id,token,gmt_create,gmt_modified,avatar_url)values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    public void insert(User user);

    /**
     * 根据cookie中的token查询用户
     * @param token
     * @return
     */
    @Select("select * from user where token = #{token}")
    public User selectByToken(@Param("token") String token);

    /**
     * 根据用户名查询GitHub用户
     * 需要修改为根据account_id查询
     * @param name
     * @return
     */
    @Select("select * from user where name = #{name}")
    public User selectByName(@Param("name") String name);

    /**
     * 查询用户id
     * @param id
     * @return
     */
    @Select("select * from user where id=#{id}")
    public User findById(@Param("id") Integer id);

}