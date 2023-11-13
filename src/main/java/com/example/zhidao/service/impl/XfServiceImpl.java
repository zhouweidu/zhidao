package com.example.zhidao.service.impl;

import com.example.zhidao.config.XfRestConfig;
import com.example.zhidao.pojo.vo.xf.Question;
import com.example.zhidao.service.XfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class XfServiceImpl implements XfService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private XfRestConfig xfRestConfig;

    public String sendQuestionToXf(String question) {
        return restTemplate.postForObject(xfRestConfig.getHostUrl() + xfRestConfig.getSendQuestionUri()
                , new Question(question), String.class);
    }
}
