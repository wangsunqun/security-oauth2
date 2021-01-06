package com.wsq.security.oauth2.security.common.enums;

/**
 * 异常编码定义
 * lvganggang 20180502
 */
public enum ReponseStatusEnum {
    // 全局
    SUCCESS(200, "成功"),
    SYSTEM_ERROR(500, "系统错误"),
    SERVICE_NOT_SUPPORT(5001, "请求的服务不支持"),
    SERVER_BUSY(5002, "系统超时"),
    NET_ERROR(5003, "网络错误"),

    // 客户端
    PARAMETER_MISSING(4001, "请求缺少必要参数"),
    PARAMETER_ERROR(4002, "请求参数错误"),
    PARAMETER_INCOMPLETE(4003, "请求参数不完整"),
    DATA_DECRYPT_FAILED(4004, "数据解密失败"),
    DATA_CHECK_FAILED(4005, "数据校验失败"),
    SESSION_EXPIRED(4006, "通信会话已过期"),
    REQUEST_ILLICIT(4007, "非法请求"),
    SECRET_INVALID(4008, "秘钥失效"),
    FILE_OVERSIZE(4009, "文本、文件、图片大小超出限制"),
    FILE_FORMAT_ERROR(4010, "文本、文件、图片格式非法"),
    
    // 网关
    MISS_SIGN(6001, "签名丢失"),
    ERROR_SIGN(6002, "签名错误"),
    AUTH_ERROR(6003, "权限不足"),

    // login
    USER_NO_ACCESS(7001, "登入失败"),
    LOGIN_ERROR(7002, "登入失败");

    private final int value;
    private final String name;

    ReponseStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
