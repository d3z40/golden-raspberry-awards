package br.com.d3z40.goldenraspberryawards;

import br.com.d3z40.goldenraspberryawards.model.Filme;
import br.com.d3z40.goldenraspberryawards.model.Producer;
import br.com.d3z40.goldenraspberryawards.model.Studio;
import br.com.d3z40.goldenraspberryawards.services.FilmeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;

@SpringBootApplication
@Component
public class GoldenRaspberryAwardsApplication {

	public final FilmeService filmeService;

	public GoldenRaspberryAwardsApplication(FilmeService filmeService) {
		this.filmeService = filmeService;
	}

	public static void main(String[] args) {
		SpringApplication.run(GoldenRaspberryAwardsApplication.class, args);
	}

	@Bean
	public void populateDatabase() {
		final String CSV_PATH = "/csv/movielist.csv";
		final String DELIMITER = ";";
		final int COL_YEAR = 0;
		final int COL_TITLE = 1;
		final int COL_STUDIOS = 2;
		final int COL_PRODUCERS = 3;
		final int COL_WINNER = 4;

		String fileLine;
		Filme filme;
		Studio studio;
		Producer producer;

		try {
			File file = new File(getClass().getResource(CSV_PATH).toURI());
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			bufferedReader.readLine();

			while ((fileLine = bufferedReader.readLine()) != null) {
				String[] cols = fileLine.split(DELIMITER);
				boolean isWinner = false;

				if (cols.length > COL_WINNER) {
					isWinner = cols[COL_WINNER].equals("yes") ? true : false;
				}

				filme = Filme.builder()
						.year(Integer.parseInt(cols[COL_YEAR]))
						.title(cols[COL_TITLE])
						.winner(isWinner)
						.build();

				String[] studios = cols[COL_STUDIOS]
									.replace(" and ", ",")
									.split(",");

				for (String strStudio : studios) {
					studio = Studio.builder()
							.name(strStudio.trim())
							.build();

					filme.addStudio(studio);
				}

				String[] producers = cols[COL_PRODUCERS]
										.replace(" and ", ",")
										.split(",");

				for (String strProducer : producers) {
					producer = Producer.builder()
								.name(strProducer.trim())
								.build();

					filme.addProducer(producer);
				}

				filmeService.saveFilme(filme);
			}

			bufferedReader.close();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
