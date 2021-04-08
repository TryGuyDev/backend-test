package za.co.bmw.kanban.service;

import za.co.bmw.kanban.model.Task;
import za.co.bmw.kanban.model.TaskDTO;
import za.co.bmw.kanban.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public List<Task> getAllTasks() {
        Iterable<Task> taskIterable = taskRepository.findAll();
        List<Task> Tasks = new ArrayList();
        taskIterable.forEach(Tasks::add);
        return Tasks;
    }

    @Override
    @Transactional
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Task> getTaskByTitle(String title) {

        return taskRepository.findByTitle(title);
    }


    @Override
    @Transactional
    public Task saveNewTask(TaskDTO taskDTO) {
        return taskRepository.save(convertDTOToTask(taskDTO));
    }

    @Override
    @Transactional
    public Task updateTask(Task oldTask, TaskDTO newTaskDTO) {

        return taskRepository.save(updateTaskFromDTO(oldTask,newTaskDTO));

    }

    @Override
    @Transactional
    public void deleteTask(Task task) {
        taskRepository.deleteById(task.getId());
    }

    private Task convertDTOToTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setColor(taskDTO.getColor());
        task.setStatus(taskDTO.getStatus());
        task.setTags(taskDTO.getTags());
        return task;
    }

    private Task updateTaskFromDTO(Task task, TaskDTO taskDTO){
        if(Optional.ofNullable(taskDTO.getTitle()).isPresent()){
            task.setTitle(taskDTO.getTitle());
        }

        if (Optional.ofNullable((taskDTO.getDescription())).isPresent()) {
            task.setDescription(taskDTO.getDescription());
        }

        if (Optional.ofNullable((taskDTO.getColor())).isPresent()) {
            task.setColor(taskDTO.getColor());
        }

        if (Optional.ofNullable((taskDTO.getStatus())).isPresent()) {
            task.setStatus(taskDTO.getStatus());
        }

        if (Optional.ofNullable((taskDTO.getTags())).isPresent()) {
            task.setTags(taskDTO.getTags());
        }
        return task;
    }
}
