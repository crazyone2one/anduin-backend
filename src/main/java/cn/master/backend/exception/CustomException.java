package cn.master.backend.exception;

/**
 * 自定义异常类
 *
 * @author create by 11's papa on 2022-11-21
 */
public class CustomException extends RuntimeException {
    public CustomException() {
        super();
    }

    public CustomException(String msg) {
        super(msg);
    }
}
