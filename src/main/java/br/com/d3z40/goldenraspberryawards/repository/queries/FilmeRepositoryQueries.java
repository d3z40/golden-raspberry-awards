package br.com.d3z40.goldenraspberryawards.repository.queries;

import br.com.d3z40.goldenraspberryawards.model.Filme;
import br.com.d3z40.goldenraspberryawards.model.response.Interval;
import br.com.d3z40.goldenraspberryawards.model.response.StudioResp;
import br.com.d3z40.goldenraspberryawards.model.response.YearResp;

import java.util.List;

public interface FilmeRepositoryQueries {

    List<YearResp> getQuantWinnersByYear(long quantity);

    List<StudioResp> getStudiosOrderByAwards();

    List<Interval> getProducersByInterval();

    List<Filme> getFilmesJoinProducerJoinStudio();
}
