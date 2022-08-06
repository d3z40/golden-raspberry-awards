package br.com.d3z40.goldenraspberryawards.model.response;

public class YearResp {

    private int year;
    private long winnerCount;

    public YearResp(int year, long winnerCount) {
        this.year = year;
        this.winnerCount = winnerCount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public long getWinnerCount() {
        return winnerCount;
    }

    public void setWinnerCount(long winnerCount) {
        this.winnerCount = winnerCount;
    }
}
