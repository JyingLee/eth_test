package com.jying.eth_test.Bean;

import java.util.List;

public class Plan {
    private String key;
    private List<String> list;

    public Plan(String key, List<String> list) {
        this.key = key;
        this.list = list;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
