package com.wsq.security.oauth2.security.vo;

import com.wsq.security.oauth2.security.common.enums.ReponseStatusEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回实体类
 */
@NoArgsConstructor
@Data
public class Result<T> {
    /**
     * 返回码的值
     */
    private Integer status;
    /**
     * 返回码的描述
     */
    private String message;
    /**
     * 返回的数据
     */
    private T data;

    public Result(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static Result success() {
        return new Result(ReponseStatusEnum.SUCCESS.getValue(), null, null);
    }

    public static Result success(Object data) {
        return new Result(ReponseStatusEnum.SUCCESS.getValue(), null, data);
    }

    public static Result error(int errorCode, String message) {
        return new Result(errorCode, message, null);
    }

    public static Result error(ReponseStatusEnum error) {
        return new Result(error.getValue(), error.getName(), null);
    }
}