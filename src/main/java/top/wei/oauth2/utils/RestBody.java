package top.wei.oauth2.utils;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class RestBody<T> implements Rest<T>, Serializable {

    @Serial
    private static final long serialVersionUID = -7616216747521482608L;

    private int code = 200;

    private T data;

    private String msg = "";

    private boolean identifier = true;


    public static Rest<?> ok() {
        return new RestBody<>();
    }

    public static Rest<?> ok(String msg) {
        Rest<?> restBody = new RestBody<>();
        restBody.setMsg(msg);
        return restBody;
    }

    public static <T> Rest<T> okData(T data) {
        Rest<T> restBody = new RestBody<>();
        restBody.setData(data);
        return restBody;
    }

    public static <T> Rest<T> okData(T data, String msg) {
        Rest<T> restBody = new RestBody<>();
        restBody.setData(data);
        restBody.setMsg(msg);
        return restBody;
    }

    public static <T> Rest<T> build(int httpStatus, T data, String msg, boolean identifier) {
        Rest<T> restBody = new RestBody<>();
        restBody.setCode(httpStatus);
        restBody.setData(data);
        restBody.setMsg(msg);
        restBody.setIdentifier(identifier);
        return restBody;
    }

    public static Rest<?> failure(int httpStatus, String msg) {
        Rest<?> restBody = new RestBody<>();
        restBody.setCode(httpStatus);
        restBody.setMsg(msg);
        restBody.setIdentifier(false);
        return restBody;
    }


    @Override
    public String toString() {
        return "{"
                + "code:" + code
                + ", data:" + data
                + ", msg:" + msg
                + ", identifier:"
                + identifier
                + '}';
    }
}
