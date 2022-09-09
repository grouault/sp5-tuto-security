package fr.exagone.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.exagone.dao.TaskRepository;
import fr.exagone.entities.Task;

@RestController
public class TaskRestController {

    private TaskRepository taskRepository;

    public TaskRestController(TaskRepository taskRepository) {
        super();
        this.taskRepository = taskRepository;
    }

    @GetMapping("/tasks")
    public List<Task> listTasks() {
        return this.taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public Task save(@RequestBody Task t) {
        return this.taskRepository.save(t);
    }


}
