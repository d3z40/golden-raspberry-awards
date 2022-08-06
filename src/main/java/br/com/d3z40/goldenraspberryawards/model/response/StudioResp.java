package br.com.d3z40.goldenraspberryawards.model.response;

public class StudioResp {

    private String name;
    private long winCount;

    public StudioResp(String name, long winCount) {
        this.name = name;
        this.winCount = winCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWinCount() {
        return winCount;
    }

    public void setWinCount(long winCount) {
        this.winCount = winCount;
    }
}
