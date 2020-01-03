package com.kgc.community.service;

import com.kgc.community.dto.PaginationDTO;
import com.kgc.community.dto.QuestionDTO;
import com.kgc.community.mapper.QuestionMapper;
import com.kgc.community.mapper.UserMapper;
import com.kgc.community.model.Question;
import com.kgc.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);


        //页码边界处理
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }


        Integer offset = (page - 1) * size;
        List<Question> questionList = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for (Question qusetion : questionList) {
            User user = userMapper.findById(qusetion.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //拷贝属性
            BeanUtils.copyProperties(qusetion, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO listByUserId(Integer userId, Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.countByUserId(userId);
        paginationDTO.setTotalCount(totalCount);
        paginationDTO.setPagination(totalCount,page,size);
        //页码边界处理
        if (page < 1) {
            page = 1;
        }
        if (page > paginationDTO.getTotalPage()) {
            page = paginationDTO.getTotalPage();
        }


        Integer offset = (page - 1) * size;
        List<Question> questionList = questionMapper.listByUserId(userId,offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for (Question qusetion : questionList) {
            User user = userMapper.findById(qusetion.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //拷贝属性
            BeanUtils.copyProperties(qusetion, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
}
