package org.deslre.utils;

import lombok.Data;

@Data
public class Result {
    private Object data;
    private String msg;
    private int code;

	// getter setter 省略，构造方法省略
    // 操作成功返回数据
    public static Result ok(Object data) {
        return ok(200, "操作成功", data);
    }

    public static Result ok(String msg) {
        return ok(200, msg, null);
    }

    public static Result ok(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result ok(String msg, Object data) {
        return ok(200,msg,data);
    }

    // 操作异常返回
    public static Result fail(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result fail(String msg) {
        return fail(400,msg,null);
    }

    public static Result fail(int code, String msg) {
        return fail(code,msg,"null");
    }

    public static Result fail(String msg, Object data) {
        return fail(400,msg,data);
    }
}