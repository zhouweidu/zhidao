package com.example.zhidao.service.impl;

import com.example.zhidao.dao.IssueImageRepository;
import com.example.zhidao.pojo.entity.IssueImage;
import com.example.zhidao.service.IssueImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IssueImageServiceImpl implements IssueImageService {
    @Autowired
    private IssueImageRepository issueImageRepository;

    @Override
    public List<IssueImage> createIssueImages(Long issueId, List<String> issueImages) {
        if (issueImages == null || issueImages.size() == 0) {
            return null;
        }
        ArrayList<IssueImage> imageArrayList = new ArrayList<>();
        for (String issueImage : issueImages) {
            imageArrayList.add(IssueImage.builder().issueId(issueId).imagePath(issueImage).build());
        }
        return issueImageRepository.saveAll(imageArrayList);
    }

    @Override
    public List<IssueImage> findIssueImagesByIssueId(Long issueId) {
        return issueImageRepository.findIssueImagesByIssueId(issueId);
    }
}
