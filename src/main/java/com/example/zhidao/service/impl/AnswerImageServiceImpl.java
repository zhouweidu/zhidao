package com.example.zhidao.service.impl;

import com.example.zhidao.dao.AnswerImageRepository;
import com.example.zhidao.pojo.entity.AnswerImage;
import com.example.zhidao.service.AnswerImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnswerImageServiceImpl implements AnswerImageService {
    @Autowired
    private AnswerImageRepository answerImageRepository;

    @Override
    public List<AnswerImage> createAnswerImages(Long answerId, List<String> answerImages) {
        if (answerImages == null || answerImages.size() == 0) {
            return null;
        }
        ArrayList<AnswerImage> imageArrayList = new ArrayList<>();
        for (String answerImage : answerImages) {
            imageArrayList.add(AnswerImage.builder().answerId(answerId).imagePath(answerImage).build());
        }
        return answerImageRepository.saveAll(imageArrayList);
    }

    @Override
    public List<AnswerImage> findAnswerImagesByAnswerId(Long answerId) {
        return answerImageRepository.findAnswerImagesByAnswerId(answerId);
    }

    @Override
    @Transactional
    public void deleteAnswerImages(Long answerId) {
        List<AnswerImage> images = findAnswerImagesByAnswerId(answerId);
        if (images != null && images.size() > 0) {
            answerImageRepository.deleteByAnswerId(answerId);
        }
    }
}
