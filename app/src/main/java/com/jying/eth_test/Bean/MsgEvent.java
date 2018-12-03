package com.jying.eth_test.Bean;

public class MsgEvent {
    private String key;
    private String msg;

    public MsgEvent(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "MsgEvent{" +
                "key='" + key + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
