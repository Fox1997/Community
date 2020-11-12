package com.example.comm.service;

import com.example.comm.dto.PaginationDto;
import com.example.comm.dto.QuestionDto;
import com.example.comm.exception.CustomizeErrorCode;
import com.example.comm.exception.CustomizeException;
import com.example.comm.mapper.QuestionExtMapper;
import com.example.comm.mapper.QuestionMapper;
import com.example.comm.mapper.UserMapper;
import com.example.comm.model.Question;
import com.example.comm.model.QuestionExample;
import com.example.comm.model.User;
import com.example.comm.model.UserExample;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired(required = false)
    private QuestionMapper questionMapper;

    @Autowired(required = false)
    private QuestionExtMapper questionExtMapper;

    @Autowired(required = false)
    private UserMapper userMapper;


    public PaginationDto list(Integer page, Integer size) {
        PaginationDto paginationDto=new PaginationDto();
        Integer totalPage;
        Integer totalCount=(int)questionMapper.countByExample(new QuestionExample());
        if(totalCount % size==0)
        {
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size+1;
        }
        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        paginationDto.setPagination(totalPage,page);
        Integer offset = size*(page-1);
        if(offset<0)
        {
            offset=0;
        }
        QuestionExample example3=new QuestionExample();

        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example3,new RowBounds(offset,size));

        List <QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question : questions) {
            UserExample example =new UserExample();
            example.createCriteria().andIdEqualTo(question.getCreator());
            User user =userMapper.selectByExample(example).get(0);
            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setQuestions(questionDtoList);

        return paginationDto;

    }

    public PaginationDto list(Long userId, Integer page, Integer size) {
        PaginationDto paginationDto=new PaginationDto();
        Integer totalPage;
        QuestionExample questionExample=new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount =(int) questionMapper.countByExample(questionExample);
        if(totalCount % size==0)
        {
            totalPage=totalCount/size;
        }else{
            totalPage=totalCount/size+1;
        }
        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        paginationDto.setPagination(totalPage,page);
        Integer offset = size*(page-1);
        QuestionExample example=new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(userId);
        RowBounds rowBounds=new RowBounds(offset,size);
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(example,rowBounds);
        List <QuestionDto> questionDtoList=new ArrayList<>();
        for (Question question : questions) {
            UserExample example1=new UserExample();
            example1.createCriteria().andIdEqualTo(question.getCreator());
            User user = userMapper.selectByExample(example1).get(0);
            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setQuestions(questionDtoList);

        return paginationDto;


    }

    public QuestionDto getById(Long id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        if(question==null)
        {
            throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        UserExample userExample=new UserExample();
        userExample.createCriteria().
                andIdEqualTo(question.getCreator());
        List<User> users=userMapper.selectByExample(userExample);
//        User user=userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(users.get(0));
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null)
        {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setReviewCount(0);
            question.setCommentCount(0);
            question.setLinkCount(0);
            questionMapper.insert(question);
        }else {
            Question updateQuestion=new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example =new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
           int update=questionMapper.updateByExampleSelective(updateQuestion,example);
           if(update !=1){
               throw  new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
           }

        }
    }

    public void incView(Long id) {
//        Question question=questionMapper.selectByPrimaryKey(id);
//        Question updateQuestion = new Question();
//        updateQuestion.setReviewCount(question.getReviewCount()+1);
//        QuestionExample example=new QuestionExample();
//        example.createCriteria().andIdEqualTo(question.getId());
//        questionMapper.updateByExampleSelective(updateQuestion,example);
        Question question =new Question();
        question.setId(id);
        question.setReviewCount(1);
        questionExtMapper.incView(question);
    }



}


