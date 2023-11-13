package com.example.zhidao.pojo.vo.issue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Builder
public class CreateIssueRequest {
    @NotNull
    @Size(min = 4, max = 20, message = "用户名长度必须在 4-20 之间")
    @Pattern(regexp = "^[a-zA-Z\\d_]*$", message = "用户名只能包含大小写字母,数字,下划线")
    private String username;
    @NotNull
    @Size(min = 1, max = 300, message = "问题的标题为1-300字")
    private String issueTitle;
    @NotNull
    @Size(min = 1, max = 1000, message = "问题内容最多为1-1000字")
    private String issueContent;
    private List<String> issueImages;
}
