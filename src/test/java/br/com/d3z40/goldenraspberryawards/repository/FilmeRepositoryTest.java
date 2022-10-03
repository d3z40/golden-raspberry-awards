package br.com.d3z40.goldenraspberryawards.repository;

import br.com.d3z40.goldenraspberryawards.model.Filme;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
@Sql(scripts = {"classpath:load-database.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = {"classpath:clean-database.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FilmeRepositoryTest {

    @Autowired
    private FilmeRepository sut;

    @Test
    public void deve_retornar_filme_vencedor_por_ano() {
        List<Filme> filmes = sut.getFilmesByWinnerIsTrueAndYearEquals(1980);


        assertEquals(filmes.size(), 1);
        assertThat(filmes.get(0).getTitle()).isEqualTo("Cant Stop the Music");
        assertEquals(filmes.get(0).getTitle(), "Cant Stop the Music");
        assertEquals(filmes.get(0).getYear(), 1980);
    }
}
