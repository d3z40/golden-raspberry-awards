package br.com.d3z40.goldenraspberryawards.model.response;

import java.util.ArrayList;
import java.util.List;

public class Studios {

    private List<StudioResp> studios;

    public Studios(List<StudioResp> studios) {
        this.studios = studios;
    }

    public Studios() {
        studios = new ArrayList<>();
    }

    public List<StudioResp> getStudios() {
        return studios;
    }

    public void setStudios(List<StudioResp> studios) {
        this.studios = studios;
    }
}
