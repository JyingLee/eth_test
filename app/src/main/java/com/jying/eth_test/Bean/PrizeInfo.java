package com.jying.eth_test.Bean;

public class PrizeInfo {

    private int image;
    private String info;
    private String winner;

    public PrizeInfo(int image, String info, String winner) {
        this.image = image;
        this.info = info;
        this.winner = winner;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
