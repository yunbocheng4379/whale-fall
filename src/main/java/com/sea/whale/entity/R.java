package com.sea.whale.entity;

import com.sea.whale.enums.ResultEnum;
import com.sea.whale.exception.AppException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Schema(name = "结果集实体", description = "统一结果集封装")
public class R {

    @Schema(name = "成功状态")
    private Boolean success ;

    @Schema(name = "状态码")
    private Integer code;

    @Schema(name = "提示信息")
    private String message;

    @Schema(name = "数据")
    private Map<String, Object> data = new HashMap<>();

    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage("成功");
        return r;
    }

    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(500);
        r.setMessage("失败");
        return r;
    }

    public static R error(String message) {
        R r = new R();
        r.setSuccess(false);
        r.setCode(500);
        r.setMessage(message);
        return r;
    }

    public static R error(Integer code, String message) {
        R r = new R();
        r.setSuccess(false);
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public static R error(ResultEnum resultEnum) {
        R r = new R();
        r.setSuccess(false);
        r.setCode(resultEnum.getCode());
        r.setMessage(resultEnum.getMessage());
        return r;
    }

    public static R error(AppException appException) {
        R r = new R();
        r.setSuccess(false);
        r.setCode(appException.getCode());
        r.setMessage(appException.getMessage());
        return r;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public R onlyList(Object value) {
        this.data.put("list", value);
        return this;
    }

    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
