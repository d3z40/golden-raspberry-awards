package br.com.d3z40.goldenraspberryawards.repository;

import br.com.d3z40.goldenraspberryawards.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    List<Filme> getFilmesByWinnerIsTrueAndYearEquals(int year);
}
