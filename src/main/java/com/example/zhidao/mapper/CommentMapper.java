package com.example.zhidao.mapper;

import com.example.zhidao.pojo.entity.Comment;
import com.example.zhidao.pojo.vo.comment.CommentVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCT = Mappers.getMapper(CommentMapper.class);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    CommentVO entity2VO(Comment comment);
}
