package ro.developmentfactory.thedrones;

import org.springframework.boot.SpringApplication;

public class TestTheDronesApplication {

	public static void main(String[] args) {
		SpringApplication.from(TheDronesApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
