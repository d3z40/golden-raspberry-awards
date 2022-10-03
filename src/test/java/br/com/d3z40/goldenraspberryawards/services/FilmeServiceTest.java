package br.com.d3z40.goldenraspberryawards.services;

import br.com.d3z40.goldenraspberryawards.model.Filme;
import br.com.d3z40.goldenraspberryawards.model.Producer;
import br.com.d3z40.goldenraspberryawards.model.Studio;
import br.com.d3z40.goldenraspberryawards.model.response.*;
import br.com.d3z40.goldenraspberryawards.repository.FilmeRepository;
import br.com.d3z40.goldenraspberryawards.repository.queries.FilmeRepositoryQueries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class FilmeServiceTest {

    private final int YEAR = 1990;

    private final int QUANTITY_WINNERS_BY_YEAR = 2;
    private List<Filme> filmes;
    private Filme filme1;
    private Filme filme2;
    private Studio studio1;
    private Studio studio2;
    private Producer producer1;
    private Producer producer2;

    private List<YearResp> yearsResps;

    private YearResp yearResp1;

    private YearResp yearResp2;

    private List<StudioResp> studiosResps;

    private StudioResp studioResp1;

    private StudioResp studioResp2;

    private List<Interval> listInterval;

    private Interval interval1;

    private Interval interval2;

    private Interval interval3;

    private Interval interval4;

    private Interval intervalMin;

    private Interval intervalMax;

    private FilmeService filmeService;

    private FilmeRepository filmeRepository;

    private FilmeRepositoryQueries filmeRepositoryQueries;

    @BeforeEach
    public void setUp() {
        filmeRepository = mock(FilmeRepository.class);
        filmeRepositoryQueries = mock(FilmeRepositoryQueries.class);

        filmeService = new FilmeService(filmeRepository, filmeRepositoryQueries);

        studio1 = new Studio(1L, "Studio 1", filme1);
        producer1 = new Producer(1L, "Producer 1", filme1);
        filme1 = new Filme(1L, YEAR,  "Filme 1", Arrays.asList(studio1), Arrays.asList(producer1), true);

        studio2 = new Studio(1L, "Studio 2", filme2);
        producer2 = new Producer(1L, "Producer 2", filme2);
        filme2 = new Filme(1L, YEAR,  "Filme 2", Arrays.asList(studio2), Arrays.asList(producer2), true);

        filmes = new ArrayList<>(Arrays.asList(filme1, filme2));

        yearResp1 = new YearResp(YEAR, 3);

        yearResp2 = new YearResp(YEAR + 1, 2);

        yearsResps = new ArrayList<>(Arrays.asList(yearResp1, yearResp2));

        studioResp1 = new StudioResp("Studio 1", 5);

        studioResp2 = new StudioResp("Studio 2", 4);

        studiosResps = new ArrayList<>(Arrays.asList(studioResp1, studioResp2));

        interval1 = new Interval("Producer 1", 2000);
        interval2 = new Interval("Producer 1", 2005);
        interval3 = new Interval("Producer 2", 2000);
        interval4 = new Interval("Producer 2", 2010);

        intervalMin = new Interval("Producer 1", 5, 2000, 2005);
        intervalMax = new Interval("Producer 2", 10, 2000, 2010);

        listInterval = new ArrayList<>(Arrays.asList(interval1, interval2, interval3, interval4));
    }

    @Test
    public void salva_filme() {
        when(filmeRepository.save(filme1))
                .thenReturn(filme1);

        ResponseEntity<Filme> filmeResponseEntity = filmeService.saveFilme(filme1);

        verify(filmeRepository).save(filme1);
        assertEquals(filmeResponseEntity.getStatusCode(), HttpStatus.CREATED);
        assertEquals(filmeResponseEntity.getBody().getId(), filme1.getId());
    }

    @Test
    public void deve_retornar_filmes_vencedores_por_ano() {
        when(filmeRepository.getFilmesByWinnerIsTrueAndYearEquals(anyInt()))
                .thenReturn(filmes);

        ResponseEntity<List<FilmeResp>> filmesWinner = filmeService.getFilmesByWinnerIsTrueAndYearEquals(YEAR);

        verify(filmeRepository).getFilmesByWinnerIsTrueAndYearEquals(YEAR);
        assertEquals(filmesWinner.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(filmesWinner.getBody().size(), filmes.size());
    }

    @Test
    public void deve_retornar_qtde_filmes_vencedores_por_ano() {
        when(filmeRepositoryQueries.getQuantWinnersByYear(QUANTITY_WINNERS_BY_YEAR))
                .thenReturn(yearsResps);

        ResponseEntity<Years> quantWinnersByYear = filmeService.getQuantWinnersByYear();

        verify(filmeRepositoryQueries).getQuantWinnersByYear(QUANTITY_WINNERS_BY_YEAR);
        assertEquals(quantWinnersByYear.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(quantWinnersByYear.getBody().getYears().size(), yearsResps.size());
    }

    @Test
    public void deve_retornar_studios_vencedores_ordernados_mais_vencedores() {
        when(filmeRepositoryQueries.getStudiosOrderByAwards())
                .thenReturn(studiosResps);

        ResponseEntity<Studios> studiosOrderByAwards = filmeService.getStudiosOrderByAwards();

        verify(filmeRepositoryQueries).getStudiosOrderByAwards();
        assertEquals(studiosOrderByAwards.getStatusCode(), HttpStatus.ACCEPTED);
        assertEquals(studiosOrderByAwards.getBody().getStudios().size(), studiosResps.size());
    }

    @Test
    public void deve_retornar_maior_e_menor_intervalo_de_vencedores() {
        when(filmeRepositoryQueries.getProducersByInterval())
                .thenReturn(listInterval);

        ResponseEntity<ProducerIntervResp> producersByInterval = filmeService.getProducersByInterval();

        verify(filmeRepositoryQueries).getProducersByInterval();
        assertEquals(producersByInterval.getStatusCode(), HttpStatus.OK);
        assertEquals(producersByInterval.getBody().getMax(), intervalMax);
        assertEquals(producersByInterval.getBody().getMin(), intervalMin);
    }

    @Test
    public void deve_deletar_filme() {
        filme1.setWinner(false);

        when(filmeRepository.findById(1L))
                .thenReturn(Optional.ofNullable(filme1));

        ResponseEntity<HttpStatus> httpStatusResponseEntity = filmeService.deleteFilmeById(filme1.getId());

        verify(filmeRepository).findById(filme1.getId());
        verify(filmeRepository).deleteById(filme1.getId());
        assertEquals(httpStatusResponseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void deve_lancar_excecao_ao_deletar_filme_vencedor() {
        when(filmeRepository.findById(1L))
                .thenReturn(Optional.ofNullable(filme1));

        RuntimeException runtimeException = assertThrows(
                RuntimeException.class,
                () -> filmeService.deleteFilmeById(filme1.getId()));

        verify(filmeRepository).findById(1L);
        assertEquals(runtimeException.getMessage(), "Filme já foi vencedor, não permitido exclui-lo.");
    }

    @Test
    public void deve_retornar_lista_de_filmes() {
        when(filmeRepository.findAll())
                .thenReturn(filmes);

        ResponseEntity<List<Filme>> entityList = filmeService.getFilmes();

        verify(filmeRepository).findAll();
        assertEquals(entityList.getStatusCode(), HttpStatus.OK);
        assertEquals(entityList.getBody().size(), filmes.size());
    }

    @Test
    public void deve_retornar_filme_pelo_id() {
        when(filmeRepository.findById(filme1.getId()))
                .thenReturn(Optional.ofNullable(filme1));

        ResponseEntity<Filme> filmeById = filmeService.getFilmeById(filme1.getId());

        verify(filmeRepository).findById(filme1.getId());
        assertEquals(filmeById.getStatusCode(), HttpStatus.OK);
        assertEquals(filmeById.getBody(), filme1);
    }

    @Test
    public void deve_retornar_httpstatuscode_not_found() {
        ResponseEntity<Filme> filmeById = filmeService.getFilmeById(filme1.getId());

        verify(filmeRepository).findById(filme1.getId());
        assertEquals(filmeById.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}