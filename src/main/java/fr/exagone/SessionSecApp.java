package fr.exagone;

import fr.exagone.dao.TaskRepository;
import fr.exagone.entities.Task;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SessionSecApp implements CommandLineRunner {

	@Autowired
	private TaskRepository taskRepository;

	public static void main(String[] args) {
		SpringApplication.run(SessionSecApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// creation de quelques tachesZ
		Stream.of("T1","T2","T3").forEach(t -> {
			this.taskRepository.save(new Task(null, t));
		});

		// affichage des taches
		taskRepository.findAll().forEach(t -> {
			System.out.println(t.getTaskName());
		});

	}

}
