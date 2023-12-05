package com.example.zhidao.mapper;

import com.example.zhidao.pojo.entity.AIAnswer;
import com.example.zhidao.pojo.entity.Issue;
import com.example.zhidao.pojo.vo.issue.IssueAndAIAnswerVO;
import com.example.zhidao.pojo.vo.issue.IssueVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IssueMapper {
    IssueMapper INSTANCT = Mappers.getMapper(IssueMapper.class);

    @Mapping(target = "issueCreatedAt", source = "issue.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "aiAnswerCreatedAt", source = "aiAnswer.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(target = "issueId", source = "issue.issueId")
    IssueAndAIAnswerVO entity2VO(Issue issue, AIAnswer aiAnswer);

    @Mapping(target = "createdAt", source = "issue.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    IssueVO entity2VO(Issue issue, List<String> issueImages);

    @Mapping(target = "createdAt", source = "issue.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    IssueVO entity2VO(Issue issue, List<String> issueImages, String nickName);

    @Mapping(target = "createdAt", source = "issue.createdAt", dateFormat = "yyyy-MM-dd HH:mm:ss")
    IssueVO entity2VO(Issue issue, List<String> issueImages, String nickName, Boolean isConcerned);
}
