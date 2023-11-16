package com.example.zhidao.service.impl;

import com.example.zhidao.dao.CommentRepository;
import com.example.zhidao.dao.UserRepository;
import com.example.zhidao.pojo.entity.Comment;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.pojo.vo.common.BizException;
import com.example.zhidao.pojo.vo.common.ExceptionEnum;
import com.example.zhidao.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Comment createComment(String username, Long answerId, String content) {
        User user = userRepository.findUserByUsername(username);
        return commentRepository.save(Comment.builder().userId(user.getUserId()).answerId(answerId)
                .content(content).likedNumber(0).build());
    }

    @Override
    public void deleteComment(String username, Long commentId) {
        if (commentRepository.findById(commentId).isPresent()) {
            Comment comment = commentRepository.findById(commentId).get();
            User user = userRepository.findUserByUsername(username);
            if (!Objects.equals(user.getUserId(), comment.getUserId())) {
                throw new BizException(ExceptionEnum.REMOVE_OTHERS_COMMENT);
            } else {
                commentRepository.deleteById(commentId);
            }
        } else {
            throw new BizException(ExceptionEnum.COMMENT_NOT_EXIST);
        }

    }

    @Override
    public List<Comment> findCommentPages(Long answerId, Integer page, Integer pageSize) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "likedNumber"));
        orders.add(new Sort.Order(Sort.Direction.DESC, "createdAt"));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(orders));
        Page<Comment> all = commentRepository.findAll(pageable);
        ArrayList<Comment> comments = new ArrayList<>();
        for (Comment comment : all) {
            comments.add(comment);
        }
        return comments;
    }
}
