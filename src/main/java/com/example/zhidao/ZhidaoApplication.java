package com.example.zhidao;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.zhidao.pojo.entity.Issue;
import com.example.zhidao.service.AIAnswerService;
import com.example.zhidao.service.AnswerService;
import com.example.zhidao.service.IssueService;
import com.example.zhidao.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;

@SpringBootApplication
public class ZhidaoApplication {
    @Value("${init-data}")
    private boolean enableInitData;
    private final String LOWER_CHAR="abcdefghijklmnopqrstuvwxyz";
    private final String UPPER_CHAR="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String NUMBER_CHAR="0123456789";

    public static void main(String[] args) {
        SpringApplication.run(ZhidaoApplication.class, args);
    }

    @Bean
    public CommandLineRunner dataInit(IssueService issueService, AIAnswerService aiAnswerService,
                                      UserService userService, AnswerService answerService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                if (!enableInitData){
                    return;
                }
                List<Issue> issuePages = issueService.findIssuePages(0, 1);
                if (issuePages != null && issuePages.size() != 0) {
                    return;
                }
                ClassPathResource classPathResource = new ClassPathResource("init_data.json");
                InputStream inputStream = classPathResource.getInputStream();
                byte[] bytes = new byte[0];
                bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                String jsonStr = new String(bytes);
                JSONArray jsonArray = JSONArray.parseArray(jsonStr);
                System.out.println("-------");
                System.out.println(jsonArray);
                System.out.println("-------");
                for (int i = 0; i < jsonArray.size(); i++) {
                    DataInit dataInit = JSONObject.toJavaObject(jsonArray.getJSONObject(i), DataInit.class);
                    System.out.println(dataInit);
                    String usernameSubmit = RandomUtil.randomString(RandomUtil.randomInt(4, 20));
                    String passwordSubmit=RandomUtil.randomString(UPPER_CHAR,4)+
                            RandomUtil.randomString(LOWER_CHAR,6)+RandomUtil.randomString(NUMBER_CHAR,6);
                    String nickNameSubmit=RandomUtil.randomString(RandomUtil.randomInt(6,16));
                    userService.register(usernameSubmit, passwordSubmit, nickNameSubmit, null);
                    Issue issue = issueService.createIssue(usernameSubmit, dataInit.issueTitle, dataInit.issueContent);
                    aiAnswerService.createAIAnswer(issue.getIssueId(),
                            issue.getIssueTitle() + " " + issue.getIssueContent());
                    for (int j = 0; j < dataInit.getAnswerContent().size(); j++) {
                        String username = RandomUtil.randomString(RandomUtil.randomInt(4, 20));
                        String password=RandomUtil.randomString(UPPER_CHAR,4)+
                                RandomUtil.randomString(LOWER_CHAR,6)+RandomUtil.randomString(NUMBER_CHAR,6);
                        String nickName=RandomUtil.randomString(RandomUtil.randomInt(6,16));
                        userService.register(username, password, nickName, null);
                        answerService.createAnswer(username,issue.getIssueId(),dataInit.getAnswerContent().get(j));
                    }
                }

            }
        };
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
