package br.com.d3z40.goldenraspberryawards.model.response;

import java.util.Objects;

public class Interval implements Comparable<Interval> {

    private String producer;
    private long interval;
    private int previousWin;
    private int followingWin;

    public Interval(String producer, int followingWin) {
        this.producer = producer;
        this.followingWin = followingWin;
    }

    public Interval(String producer, long interval, int previousWin, int followingWin) {
        this.producer = producer;
        this.interval = interval;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public int getPreviousWin() {
        return previousWin;
    }

    public void setPreviousWin(int previousWin) {
        this.previousWin = previousWin;
    }

    public int getFollowingWin() {
        return followingWin;
    }

    public void setFollowingWin(int followingWin) {
        this.followingWin = followingWin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return followingWin == interval.followingWin && Objects.equals(producer, interval.producer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producer, followingWin);
    }

    @Override
    public int compareTo(Interval o) {
        int ret = -1;

        if (o.getFollowingWin() > this.followingWin) {
            ret = 1;
        }

        return ret;
    }

    @Override
    public String toString() {
        return "Interval{" +
                "producer='" + producer + '\'' +
                ", interval=" + interval +
                ", previousWin=" + previousWin +
                ", followingWin=" + followingWin +
                '}';
    }
}
