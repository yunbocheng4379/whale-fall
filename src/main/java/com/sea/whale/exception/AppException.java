package com.sea.whale.exception;

import com.sea.whale.enums.ResultEnum;
import lombok.Getter;

/**
 * @author chengyunbo@gyyx.cn
 * @since 2023-03-28
 */
@Getter
public class AppException extends RuntimeException{


    private final Integer code;

    private final String message;

    public AppException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.message = resultEnum.getMessage();
        this.code = resultEnum.getCode();
    }

    public AppException(String message) {
        super(message);
        this.message = message;
        this.code = 500;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
