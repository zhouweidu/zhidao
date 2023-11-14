package com.example.zhidao.service.impl;

import com.example.zhidao.dao.AnswerImageRepository;
import com.example.zhidao.dao.AnswerRepository;
import com.example.zhidao.dao.UserRepository;
import com.example.zhidao.pojo.entity.Answer;
import com.example.zhidao.pojo.entity.AnswerImage;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AnswerImageRepository answerImageRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Answer> findAnswerByIssueId(Long issueId, Integer page, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC,
                "likedNumber"));
        Page<Answer> answerPages = answerRepository.findByIssueId(issueId, pageRequest);
        ArrayList<Answer> answers = new ArrayList<>();
        for (Answer answerPage : answerPages) {
            answers.add(answerPage);
        }
        return answers;
    }

    @Override
    public Answer createAnswer(String username, Long issueId, String answerContent, List<String> answerImages) {
        User user = userRepository.findUserByUsername(username);
        Answer answer = answerRepository.save(Answer.builder().issueId(issueId).userId(user.getUserId())
                .answerContent(answerContent).likedNumber(0).commentNumber(0).collectNumber(0).build());
        if (answerImages != null && answerImages.size() != 0) {
            ArrayList<AnswerImage> answerImageArrayList = new ArrayList<>();
            for (String answerImage : answerImages) {
                answerImageArrayList.add(AnswerImage.builder().
                        answerId(answer.getAnswerId()).imagePath(answerImage).build());
            }
            answerImageRepository.saveAll(answerImageArrayList);
        }
        return answer;
    }
}
