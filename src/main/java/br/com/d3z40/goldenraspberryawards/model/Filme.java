package br.com.d3z40.goldenraspberryawards.model;

import lombok.Builder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@Entity
@Table(name = "filme")
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "filme_seq")
    @SequenceGenerator(name = "filme_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false, name = "year_filme")
    private int year;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL)
    private List<Studio> studios = new ArrayList<>();

    @OneToMany(mappedBy = "filme", cascade = CascadeType.ALL)
    private List<Producer> producers = new ArrayList<>();

    private boolean winner;

    public Filme() {
    }

    public Filme(Long id, int year, String title, List<Studio> studios, List<Producer> producers, boolean winner) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Studio> getStudios() {
        return studios;
    }

    public void setStudios(List<Studio> studios) {
        this.studios = studios;

        studios.forEach(s -> s.setFilme(this));
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;

        producers.forEach(p -> p.setFilme(this));
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public boolean addStudio(Studio studio) {
        if (studios == null) {
            studios = new ArrayList<>();
        }

        studio.setFilme(this);
        return studios.add(studio);
    }

    public boolean addProducer(Producer producer) {
        if (producers == null) {
            producers = new ArrayList<>();
        }

        producer.setFilme(this);
        return producers.add(producer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Filme filme = (Filme) o;
        return year == filme.year && Objects.equals(title, filme.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(year, title);
    }

    @Override
    public String toString() {
        return "Filme{" +
                "id=" + id +
                ", year=" + year +
                ", title='" + title + '\'' +
                ", studios=" + studios +
                ", producers=" + producers +
                ", winner=" + winner +
                '}';
    }
}
