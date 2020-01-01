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
     * 分页查询
     * @return
     * @param offset
     * @param size
     */
    @Select("select * from question limit #{offset},#{size}")
    public List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    /**
     * 查询所有数据总数
     * @return
     */
    @Select("select count(1) from question")
    public Integer count();
}
