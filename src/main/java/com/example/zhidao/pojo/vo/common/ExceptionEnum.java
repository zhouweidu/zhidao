package com.example.zhidao.pojo.vo.common;

public enum ExceptionEnum implements BaseErrorInfoInterface{
    // 数据操作错误定义
    SUCCESS("2000", "成功!"),
    INVALID_CREDENTIAL("4000", "用户名或密码错误"),
    USERNAME_EXIST("4001", "用户名已存在"),
    ISSUE_NOT_EXIST("4002", "问题不存在"),
    ANSWER_NOT_EXIST("4003", "回答不存在"),
    COMMENT_NOT_EXIST("4004", "评论不存在"),
    REMOVE_OTHERS_COMMENT("4005", "不能删除别人的评论"),
    ANSWER_LIKED_NUMBER_IS_ZERO("4006","回答的点赞数为0" ),
    COMMENT_LIKED_NUMBER_IS_ZERO("4007", "评论的点赞数为0"),
    USER_NOT_FOUND("4008", "用户不存在");


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
