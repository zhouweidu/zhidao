package com.example.zhidao.mapper;

import com.example.zhidao.pojo.entity.AIAnswer;
import com.example.zhidao.pojo.vo.aianswer.AIAnswerVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AIAnswerMapper {
    AIAnswerMapper INSTANCT = Mappers.getMapper(AIAnswerMapper.class);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    AIAnswerVO entity2VO(AIAnswer aiAnswer);

    @Mapping(target = "createdAt", source = "aiAnswer.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    AIAnswerVO entity2VO(AIAnswer aiAnswer, Boolean isLiked);
    @Mapping(target = "createdAt", source = "aiAnswer.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    AIAnswerVO entity2VO(AIAnswer aiAnswer, Boolean isLiked,Boolean isCollected);
}
