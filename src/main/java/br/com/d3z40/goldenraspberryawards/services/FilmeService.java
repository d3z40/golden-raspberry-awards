package br.com.d3z40.goldenraspberryawards.services;

import br.com.d3z40.goldenraspberryawards.model.Filme;
import br.com.d3z40.goldenraspberryawards.model.response.*;
import br.com.d3z40.goldenraspberryawards.repository.FilmeRepository;
import br.com.d3z40.goldenraspberryawards.repository.queries.FilmeRepositoryQueries;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;

    private final FilmeRepositoryQueries filmeRepositoryQueries;
    private final ModelMapper modelMapper;

    private final long QUANTITY_WINNERS_BY_YEAR = 2;

    public FilmeService(FilmeRepository filmeRepository, FilmeRepositoryQueries filmeRepositoryQueries) {
        this.filmeRepository = filmeRepository;
        this.filmeRepositoryQueries = filmeRepositoryQueries;
        this.modelMapper = new ModelMapper();
    }

    public ResponseEntity<Filme> saveFilme(Filme filme) {
        return new ResponseEntity<>(filmeRepository.save(filme), HttpStatus.CREATED);
    }


    public ResponseEntity<List<FilmeResp>> getFilmesByWinnerIsTrueAndYearEquals(int year) {
        List<Filme> filmes = filmeRepository.getFilmesByWinnerIsTrueAndYearEquals(year);

        List<FilmeResp> filmeRespList = filmes.stream()
                .map(f -> modelMapper.map(f, FilmeResp.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(filmeRespList, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Years> getQuantWinnersByYear() {

        List<YearResp> years = filmeRepositoryQueries.getQuantWinnersByYear(QUANTITY_WINNERS_BY_YEAR);

        return new ResponseEntity<>(new Years(years), HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Studios> getStudiosOrderByAwards() {

        List<StudioResp> studios = filmeRepositoryQueries.getStudiosOrderByAwards();

        return new ResponseEntity<>(new Studios(studios), HttpStatus.ACCEPTED);
    }

    public ResponseEntity<ProducerIntervResp> getProducersByInterval() {
        Map<String, List<Interval>> map = new HashMap<>();

        List<Interval> listInterval = filmeRepositoryQueries.getProducersByInterval();

        populaMap(map, listInterval);

        populaIntervals(map);

        return new ResponseEntity<>(getProducerIntervResp(map), HttpStatus.ALREADY_REPORTED);
    }

    private ProducerIntervResp getProducerIntervResp(Map<String, List<Interval>> map) {
        List<Interval> listIntervalMaiorQueZero = new ArrayList<>();

        populaListIntervalMaiorQueZero(map, listIntervalMaiorQueZero);

        Interval intMaior = getIntMaior(map, listIntervalMaiorQueZero);
        Interval intMenor = getIntMenor(map, listIntervalMaiorQueZero);

        return new ProducerIntervResp(intMenor, intMaior);
    }

    private Interval getIntMaior(Map<String, List<Interval>> map, List<Interval> listIntervalMaiorQueZero) {

        Interval intMaior = null;
        for (Interval i : listIntervalMaiorQueZero) {
            if (intMaior == null) {
                intMaior = i;
            }

            intMaior = i.getInterval() > intMaior.getInterval() ? i : intMaior;
        }

        return intMaior;
    }

    private Interval getIntMenor(Map<String, List<Interval>> map, List<Interval> listIntervalMaiorQueZero) {
        Interval intMenor = null;
        for (Interval i : listIntervalMaiorQueZero) {
            if (intMenor == null) {
                intMenor = i;
            }

            intMenor = i.getInterval() < intMenor.getInterval() ? i : intMenor;
        }

        return intMenor;
    }

    private void populaListIntervalMaiorQueZero(Map<String, List<Interval>> map, List<Interval> listMax) {
        map.entrySet()
                .stream()
                .forEach(s -> {
                    List<Interval> listValues = s.getValue();
                    for (Interval i : listValues) {
                        if (i.getInterval() > 0) {
                            listMax.add(i);
                        }
                    }
                });
    }

    private void populaMap(Map<String, List<Interval>> map, List<Interval> listInterval) {
        listInterval
                .forEach(k -> {
                    if (!map.containsKey(k.getProducer())) {
                        map.put(k.getProducer(), new ArrayList<>());
                    }
                    map.get(k.getProducer()).add(k);
                });
    }

    private void populaIntervals(Map<String, List<Interval>> map) {
        map.entrySet().stream()
                .forEach(k -> {
                    boolean first = true;
                    int year = 0;

                    List<Interval> listValue = k.getValue();
                    for (Interval i : listValue) {
                        if (first) {
                            year = i.getFollowingWin();
                            first = false;
                        }

                        i.setPreviousWin(year);
                        i.setInterval(i.getFollowingWin() - year);
                        year = i.getFollowingWin();
                    }
                });
    }

    public ResponseEntity<HttpStatus> deleteFilmeById(long id) {

        Optional<Filme> filme = filmeRepository.findById(id);
        if (filme.isPresent() && !filme.get().isWinner()) {
            filmeRepository.deleteById(id);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        throw new RuntimeException("Filme já foi vencedor, não permitido exclui-lo.");
        //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Filme>> getFilmes() {
        return new ResponseEntity<>(filmeRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Filme> getFilmeById(long id) {
        Optional<Filme> filmeOptional = filmeRepository.findById(id);

        if (filmeOptional.isPresent()) {
            return new ResponseEntity<>(filmeOptional.get(), HttpStatus.OK);
        }
        return null;
    }
}
