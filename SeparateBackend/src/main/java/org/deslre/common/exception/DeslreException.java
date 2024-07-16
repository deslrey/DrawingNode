package org.deslre.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.deslre.common.result.ResultCodeEnum;

/**
 * 自定义全局异常类
 *
 * @author qy
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeslreException extends RuntimeException {

    private Integer code;
    private ResultCodeEnum resultCodeEnum;

    /**
     * 通过状态码和错误消息创建异常对象
     */
    public DeslreException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     */
    public DeslreException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.resultCodeEnum = resultCodeEnum;
    }
}

