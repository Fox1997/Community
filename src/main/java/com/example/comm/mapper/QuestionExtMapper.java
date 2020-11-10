package com.example.comm.mapper;

import com.example.comm.model.Question;
import com.example.comm.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
   int incView(Question question);
}