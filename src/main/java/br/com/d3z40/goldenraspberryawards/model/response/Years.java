package br.com.d3z40.goldenraspberryawards.model.response;

import java.util.ArrayList;
import java.util.List;

public class Years {

    private List<YearResp> years;

    public Years(List<YearResp> yearRespList) {
        this.years = yearRespList;
    }

    public Years() {
        years = new ArrayList<>();
    }

    public List<YearResp> getYears() {
        return years;
    }

    public void setYears(List<YearResp> years) {
        this.years = years;
    }
}
