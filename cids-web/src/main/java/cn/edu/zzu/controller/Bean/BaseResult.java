package cn.edu.zzu.controller.Bean;

import java.io.Serializable;

/**
 * Created by qinhao on 2018/5/14.
 */
public class BaseResult<T> implements Serializable {


    private static final long serialVersionUID = 2982260232898141985L;

    private boolean success; // 操作是否成功

    private T result; // 返回的结果域

    private String retMsg; // 返回的结果代码对应的消息

    public BaseResult() {
    }

    public BaseResult(boolean success) {
        this.success = success;
    }

    /**
     * 结果域
     */
    public T getResult() {
        return result;
    }

    public void setResult(final T result) {
        this.result = result;
    }


    /**
     * 操作是否成功,true表示成功
     */
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }
}
