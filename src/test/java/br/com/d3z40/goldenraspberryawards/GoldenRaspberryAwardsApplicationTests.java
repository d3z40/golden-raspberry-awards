package br.com.d3z40.goldenraspberryawards;

import io.restassured.RestAssured;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
public abstract class GoldenRaspberryAwardsApplicationTests {

	@Value("${local.server.port}")
	protected int port;

	@BeforeEach
	public void setup() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost:8080/";
		RestAssured.basePath = "awards";
	}

	@Test
	void contextLoads() {
		System.out.println("Porta: " + port);
	}
}
