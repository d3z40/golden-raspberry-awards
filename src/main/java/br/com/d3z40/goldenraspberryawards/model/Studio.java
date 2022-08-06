package br.com.d3z40.goldenraspberryawards.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;

import javax.persistence.*;
import java.util.Objects;

@Builder
@Entity
@Table(name = "studio")
public class Studio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studio_seq")
    @SequenceGenerator(name = "studio_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id", referencedColumnName = "id")
    @JsonIgnore
    private Filme filme;

    public Studio() {
    }

    public Studio(Long id, String name, Filme filme) {
        this.id = id;
        this.name = name;
        this.filme = filme;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Studio studio = (Studio) o;
        return name.equals(studio.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Studio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", filme=(" +
                    "id: " + filme.getId() +
                    "title: " + filme.getTitle() +
                    "year: " + filme.getYear() +
                ")}";
    }
}