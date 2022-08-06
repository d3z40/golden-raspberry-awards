package br.com.d3z40.goldenraspberryawards.model.response;

public class ProducerIntervResp {

    private Interval min;
    private Interval max;

    public ProducerIntervResp(Interval min, Interval max) {
        this.min = min;
        this.max = max;
    }

    public ProducerIntervResp() {
    }

    public Interval getMin() {
        return min;
    }

    public void setMin(Interval min) {
        this.min = min;
    }

    public Interval getMax() {
        return max;
    }

    public void setMax(Interval max) {
        this.max = max;
    }
}
