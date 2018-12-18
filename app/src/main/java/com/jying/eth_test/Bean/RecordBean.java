package com.jying.eth_test.Bean;

import java.math.BigInteger;

public class RecordBean {
    private BigInteger time;
    private BigInteger ak;
    private BigInteger flag;//1是收益，0是减少

    public RecordBean(BigInteger time, BigInteger ak, BigInteger flag) {
        this.time = time;
        this.ak = ak;
        this.flag = flag;
    }

    public BigInteger getTime() {
        return time;
    }

    public void setTime(BigInteger time) {
        this.time = time;
    }

    public BigInteger getAk() {
        return ak;
    }

    public void setAk(BigInteger ak) {
        this.ak = ak;
    }

    public BigInteger getFlag() {
        return flag;
    }

    public void setFlag(BigInteger flag) {
        this.flag = flag;
    }
}
