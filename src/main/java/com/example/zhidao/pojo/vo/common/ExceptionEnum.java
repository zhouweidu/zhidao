package com.example.zhidao.pojo.vo.common;

public enum ExceptionEnum implements BaseErrorInfoInterface {
    // 数据操作错误定义
    SUCCESS("2000", "成功!"),

    INVALID_CREDENTIAL("4000", "用户名或密码错误"),
    USERNAME_EXIST("4001", "用户名已存在"),
    USER_NOT_FOUND("4002", "用户不存在"),

    ISSUE_NOT_EXIST("4010", "问题不存在"),
    ISSUE_CONCERN_NUMBER_IS_ZERO("4011", "问题的关注数为0"),


    ANSWER_NOT_EXIST("4020", "回答不存在"),
    ANSWER_LIKED_NUMBER_IS_ZERO("4021", "回答的点赞数为0"),
    ANSWER_COLLECT_NUMBER_IS_ZERO("4022", "回答的收藏数为0"),

    COMMENT_NOT_EXIST("4030", "评论不存在"),
    REMOVE_OTHERS_COMMENT("4031", "不能删除别人的评论"),
    COMMENT_LIKED_NUMBER_IS_ZERO("4032", "评论的点赞数为0"),

    AI_ANSWER_NOT_EXIST("4040", "ai回答不存在"),
    AI_ANSWER_LIKED_NUMBER_IS_ZERO("4041", "ai回答点赞数为0"),
    AI_ANSWER_COLLECT_NUMBER_IS_ZERO("4042", "ai回答收藏数为0");


    /**
     * 错误码
     */
    private final String resultCode;

    /**
     * 错误描述
     */
    private final String resultMsg;

    ExceptionEnum(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    @Override
    public String getResultCode() {
        return resultCode;
    }

    @Override
    public String getResultMsg() {
        return resultMsg;
    }
}
