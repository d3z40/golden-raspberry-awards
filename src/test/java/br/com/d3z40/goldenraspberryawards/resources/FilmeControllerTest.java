package br.com.d3z40.goldenraspberryawards.resources;

import br.com.d3z40.goldenraspberryawards.GoldenRaspberryAwardsApplicationTests;
import groovy.json.JsonParser;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class FilmeControllerTest extends GoldenRaspberryAwardsApplicationTests {

    @Test
    void deve_retornar_filmes_vencedores_por_ano() {
        given()
                .pathParam("year", 1984)
        .get("/awards/winners/year/{year}")
        .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body("id", equalTo("26"));
    }

    @Test
    void deve_retornar_qtde_vencedores_por_ano() {
    }

    @Test
    void deve_retornar_studios_ordenados() {
    }

    @Test
    void deve_retornar_um_intervalo() {
    }

    @Test
    void deve_deletar_um_filme() {
    }

    @Test
    void deve_retornar_uma_lista_de_filmes() {
        Response res = given()
                .get("/filme");

        System.out.println(res.body().print());
    }

    @Test
    void deve_retornar_filme_por_id() {
        given()
                .pathParam("id", 1)
                .get("/filme/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .body(
                        "id", equalTo(1),
                        "year", equalTo(1980),
                        "title", equalTo("Can't Stop the Music"),
                        "winner", equalTo(true)
                );
    }

    @Test
    void deve_retornar_notfound() {
        given()
                .pathParam("id", 9999999)
                .get("/awards/filme/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}