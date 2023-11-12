package com.example.zhidao.pojo.vo.common;

public enum ExceptionEnum implements BaseErrorInfoInterface{
    // 数据操作错误定义
    SUCCESS("2000", "成功!"),
    INVALID_CREDENTIAL("4000", "用户名或密码错误"),
    USERNAME_EXIST("4001", "用户名已存在"),



    ;


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
