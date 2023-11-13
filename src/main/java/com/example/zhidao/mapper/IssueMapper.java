package com.example.zhidao.mapper;

import com.example.zhidao.pojo.entity.Issue;
import com.example.zhidao.pojo.vo.issue.IssueVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IssueMapper {
    IssueMapper INSTANCT = Mappers.getMapper(IssueMapper.class);
    IssueVO entity2VO(Issue issue);
}
