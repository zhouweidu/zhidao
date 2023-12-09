package com.example.zhidao;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.example.zhidao.pojo.entity.Answer;
import com.example.zhidao.pojo.entity.Issue;
import com.example.zhidao.pojo.entity.User;
import com.example.zhidao.service.AIAnswerService;
import com.example.zhidao.service.AnswerService;
import com.example.zhidao.service.IssueService;
import com.example.zhidao.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@SpringBootApplication
public class ZhidaoApplication {
    @Value("${init-data}")
    private boolean enableInitData;
    private final String LOWER_CHAR = "abcdefghijklmnopqrstuvwxyz";
    private final String UPPER_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String NUMBER_CHAR = "0123456789";

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication.run(ZhidaoApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataInit(IssueService issueService, AIAnswerService aiAnswerService,
                                      UserService userService, AnswerService answerService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                if (!enableInitData) {
                    log.info("init data disabled");
                    return;
                }
                List<Issue> issuePages = issueService.findIssuePages(0, 1);
                if (issuePages != null && issuePages.size() != 0) {
                    log.info("init data already exist");
                    return;
                }
                log.info("init data start");
                InputStream inputStream = new ClassPathResource("init_data.json").getInputStream();
                JSONArray jsonArray = JSONArray.parseArray(IoUtil.readUtf8(inputStream));
                for (int i = 0; i < jsonArray.size(); i++) {
                    DataInit dataInit = JSONObject.toJavaObject(jsonArray.getJSONObject(i), DataInit.class);
                    log.info(dataInit.toString());
                    User userSubmit = getUser(userService);
                    Issue issue = issueService.createIssue(userSubmit.getUsername(), dataInit.issueTitle, dataInit.issueContent);
                    aiAnswerService.createAIAnswer(issue.getIssueId(),
                            issue.getIssueTitle() + " " + issue.getIssueContent());
                    for (int j = 0; j < dataInit.getAnswerContent().size(); j++) {
                        User userAnswer = getUser(userService);
                        Answer answer = answerService.createAnswer(userAnswer.getUsername(), issue.getIssueId(),
                                dataInit.getAnswerContent().get(j));
                        if (RandomUtil.randomInt(0, 10) < 4) {
                            for (int k = 0; k < RandomUtil.randomInt(1, 8); k++) {
                                User userLike = getUser(userService);
                                answerService.likedAnswer(answer.getAnswerId(), userLike.getUsername());
                            }
                        }
                    }
                }
                log.info("init data end");
            }
        };
    }

    private User getUser(UserService userService) {
        String username = RandomUtil.randomString(RandomUtil.randomInt(4, 20));
        String password = RandomUtil.randomString(UPPER_CHAR, 4) +
                RandomUtil.randomString(LOWER_CHAR, 6) + RandomUtil.randomString(NUMBER_CHAR, 6);
        String nickName = RandomUtil.randomString(RandomUtil.randomInt(6, 16));
        return userService.register(username, password, nickName, null);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Accessors(chain = true)
    @Builder
    private static class DataInit {
        private String issueTitle;
        private String issueContent;
        private List<String> answerContent;
    }
}
