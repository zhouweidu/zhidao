package com.example.zhidao.mapper;

import com.example.zhidao.pojo.entity.Answer;
import com.example.zhidao.pojo.vo.answer.AnswerVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AnswerMapper {
    AnswerMapper INSTANCT = Mappers.getMapper(AnswerMapper.class);

    @Mapping(target = "createdAt", source = "answer.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    AnswerVO entity2VO(Answer answer, List<String> answerImages);
    @Mapping(target = "createdAt", source = "answer.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    AnswerVO entity2VO(Answer answer, List<String> answerImages,String nickName);
    @Mapping(target = "createdAt", source = "answer.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    AnswerVO entity2VO(Answer answer, List<String> answerImages,String nickName,Boolean isLiked);
    @Mapping(target = "createdAt", source = "answer.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    AnswerVO entity2VO(Answer answer, List<String> answerImages,String nickName,Boolean isLiked,Boolean isCollected);
}
