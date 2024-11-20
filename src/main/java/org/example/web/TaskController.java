package org.example.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.constants.TaskStatus;
import org.example.model.Task;
import org.example.web.vo.TaskRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.example.service.TaskService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     *
     * @param req 추가하고자 하는 할 일
     * @return 추가된 할 일
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest req){
        this.taskService.add(req.getTitle(), req.getDescription(), req.getDueDate());
        return null;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getTask(Optional<String> dueDate){
        List<Task> result;

        if(dueDate.isPresent()){
            result = this.taskService.getByDueDate(dueDate.get());
        } else {
            result =this.taskService.getALL();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> fetchOneTask(@PathVariable Long id){
        var result = this.taskService.getOne(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getByStatus(@PathVariable TaskStatus status){
        var result = this.taskService.getByStatus(status);
        return ResponseEntity.ok(result);
    }

}
