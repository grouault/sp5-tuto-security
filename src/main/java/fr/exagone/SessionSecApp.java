package fr.exagone;

import fr.exagone.dao.RoleRepository;
import fr.exagone.dao.TaskRepository;
import fr.exagone.dao.UserRepository;
import fr.exagone.entities.AppRole;
import fr.exagone.entities.AppUser;
import fr.exagone.entities.Task;
import fr.exagone.service.AccountService;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SessionSecApp implements CommandLineRunner {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private AccountService accountService;

	public static void main(String[] args) {
		SpringApplication.run(SessionSecApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		accountService.saveUser(new AppUser(null, "admin", "1234", null));
		accountService.saveUser(new AppUser(null, "user", "1234", null));

		accountService.saveRole(new AppRole(null, "ADMIN"));
		accountService.saveRole(new AppRole(null, "USER"));

		// association roles <==> users
		accountService.addRoleToUser("admin", "ADMIN");
		accountService.addRoleToUser("user", "USER");

		// creation de quelques taches
		Stream.of("T1","T2","T3").forEach(t -> {
			this.taskRepository.save(new Task(null, t));
		});

		// affichage des taches
		taskRepository.findAll().forEach(t -> {
			System.out.println(t.getTaskName());
		});

	}

}
