package br.com.d3z40.goldenraspberryawards.resources;

import br.com.d3z40.goldenraspberryawards.model.Filme;
import br.com.d3z40.goldenraspberryawards.model.response.FilmeResp;
import br.com.d3z40.goldenraspberryawards.model.response.ProducerIntervResp;
import br.com.d3z40.goldenraspberryawards.model.response.Studios;
import br.com.d3z40.goldenraspberryawards.model.response.Years;
import br.com.d3z40.goldenraspberryawards.services.FilmeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/awards")
public class FilmeController {

    private final FilmeService filmeService;

    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }


    @GetMapping("/winners/year/{year}")
    public ResponseEntity<List<FilmeResp>> getFilmesByWinnerIsTrueAndYearEquals(@PathVariable("year") int year) {

        return filmeService.getFilmesByWinnerIsTrueAndYearEquals(year);
    }

    @GetMapping("/winners/year")
    public ResponseEntity<Years> getQuantWinnersByYear() {

        return filmeService.getQuantWinnersByYear();
    }

    @GetMapping("/winners/studios")
    public ResponseEntity<Studios> getStudiosOrderByAwards() {

        return filmeService.getStudiosOrderByAwards();
    }

    @GetMapping("/winners/producers/interval")
    public ResponseEntity<ProducerIntervResp> getProducersByInterval() {

        return filmeService.getProducersByInterval();
    }

    @DeleteMapping("/filme/{id}")
    public ResponseEntity<HttpStatus> deleteFilme(@PathVariable("id") long id) {

        return filmeService.deleteFilmeById(id);
    }

    @GetMapping("/filme")
    public ResponseEntity<List<Filme>> getFilmes() {

        return filmeService.getFilmes();
    }

    @GetMapping("/filme/{id}")
    public ResponseEntity<Filme> getFilmesById(@PathVariable("id") long id) {

        return filmeService.getFilmeById(id);
    }
}
