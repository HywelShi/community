package com.kgc.community.service;

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

    public List<QuestionDTO> listAll(){
        List<Question> questionList = questionMapper.listAll();
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();
        for(Question qusetion: questionList){
            User user = userMapper.findById(qusetion.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //拷贝属性
            BeanUtils.copyProperties(qusetion,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
