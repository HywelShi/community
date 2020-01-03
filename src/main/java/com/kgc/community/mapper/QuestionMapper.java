package com.kgc.community.mapper;

import com.kgc.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    /**
     * 发布问题
     *
     * @param question
     */
    @Insert("insert into question(title,description,gmt_create,gmt_modified,creator,tag) values(#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create(Question question);

    /**
     * 分页查询-首页所有问题
     *
     * @param offset
     * @param size
     * @return
     */
    @Select("select * from question limit #{offset},#{size}")
    public List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    /**
     * 查询所有数据总数
     *
     * @return
     */
    @Select("select count(1) from question")
    public Integer count();

    /**
     * 查询当前用户问题并分页
     *
     * @param userId
     * @param offset
     * @param size
     * @return
     */
    @Select("select * from question where creator=#{userId} limit #{offset},#{size}")
    public List<Question> listByUserId(@Param(value = "userId") Integer userId,
                                       @Param(value = "offset") Integer offset,
                                       @Param(value = "size") Integer size);

    /**
     * 查询当前用户的问题总数
     * @param userId
     * @return
     */
    @Select("select count(1) from question where creator=#{userId}")
    public Integer countByUserId(@Param(value = "userId") Integer userId);
}
