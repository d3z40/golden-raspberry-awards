package br.com.d3z40.goldenraspberryawards.repository.queries.impl;

import br.com.d3z40.goldenraspberryawards.model.Filme;
import br.com.d3z40.goldenraspberryawards.model.response.Interval;
import br.com.d3z40.goldenraspberryawards.model.response.StudioResp;
import br.com.d3z40.goldenraspberryawards.model.response.YearResp;
import br.com.d3z40.goldenraspberryawards.repository.queries.FilmeRepositoryQueries;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Component
public class FilmeRepositoryQueriesImpl implements FilmeRepositoryQueries {

    private final EntityManager entity;

    protected FilmeRepositoryQueriesImpl(EntityManager entity) {
        this.entity = entity;
    }

    @Override
    public List<YearResp> getQuantWinnersByYear(long quantity) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT                                ");
        sql.append("    new br.com.d3z40.goldenraspberryawards.model.response.YearResp(f.year, count(f.year)) ");
        sql.append("FROM Filme f                           ");
        sql.append("WHERE                                  ");
        sql.append("    f.winner = TRUE                    ");
        sql.append("GROUP BY                               ");
        sql.append("    f.year                             ");
        sql.append("HAVING                                 ");
        sql.append("    COUNT(f.year) >= :quant            ");

        TypedQuery<YearResp> query = entity.createQuery(sql.toString(), YearResp.class);
        query.setParameter("quant", quantity);

        return query.getResultList();
    }

    @Override
    public List<StudioResp> getStudiosOrderByAwards() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT                                 ");
        sql.append("    new br.com.d3z40.goldenraspberryawards.model.response.StudioResp(s.name, count(f.id)) ");
        sql.append("FROM Studio s                          ");
        sql.append("JOIN s.filme f                         ");
        sql.append("WHERE                                  ");
        sql.append("    f.winner = TRUE                    ");
        sql.append("GROUP BY                               ");
        sql.append("    s.name                             ");
        sql.append("ORDER BY                               ");
        sql.append("    COUNT(f.id) DESC                   ");

        TypedQuery<StudioResp> query = entity.createQuery(sql.toString(), StudioResp.class);

        return query.getResultList();
    }

    @Override
    public List<Interval> getProducersByInterval() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT                                 ");
        sql.append("    new br.com.d3z40.goldenraspberryawards.model.response.Interval(p.name, f.year) ");
        sql.append("FROM Producer p                        ");
        sql.append("JOIN p.filme f                         ");
        sql.append("WHERE                                  ");
        sql.append("    f.winner = TRUE                    ");
        sql.append("GROUP BY                               ");
        sql.append("    p.name,                            ");
        sql.append("    f.year                             ");
        sql.append("ORDER BY                               ");
        sql.append("    p.name,                            ");
        sql.append("    f.year                             ");

        TypedQuery<Interval> query = entity.createQuery(sql.toString(), Interval.class);

        return query.getResultList();
    }

    @Override
    public List<Filme> getFilmesJoinProducerJoinStudio() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT              ");
        sql.append("    f,              ");
        sql.append("    p,              ");
        sql.append("    s               ");
        sql.append("FROM Filme f        ");
        sql.append("JOIN f.producers p  ");
        sql.append("JOIN f.studios s    ");

        TypedQuery<Filme> query = entity.createQuery(sql.toString(), Filme.class);

        return query.getResultList();
    }
}
