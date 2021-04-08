package za.co.bmw.kanban.service;

import za.co.bmw.kanban.KanbanApplication;
import za.co.bmw.kanban.model.Kanban;
import za.co.bmw.kanban.model.KanbanDTO;
import za.co.bmw.kanban.model.Task;
import za.co.bmw.kanban.model.TaskDTO;
import za.co.bmw.kanban.repository.KanbanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KanbanServiceImpl implements KanbanService {

    private final KanbanRepository kanbanRepository;

    @Override
    @Transactional
    public List<Kanban> getAllKanbanBoards() {
        List<Kanban> kanbanBoards = new ArrayList<>();
        Iterable<Kanban> kanbanIterable = kanbanRepository.findAll();
        kanbanIterable.forEach(kanbanBoards::add);

        return kanbanBoards;
    }

    @Override
    @Transactional
    public Optional<Kanban> getKanbanById(Long id) {

        return kanbanRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Kanban> getKanbanByTitle(String title) {

        return kanbanRepository.findByTitle(title);
    }

    @Override
    @Transactional
    public Kanban saveNewKanban(KanbanDTO kanbanDTO) {
        return kanbanRepository.save(convertDTOToKanban(kanbanDTO));
    }

    @Override
    @Transactional
    public Kanban updateKanban(Kanban oldKanban, KanbanDTO newKanbanDTO) {
        return kanbanRepository.save(oldKanban);
    }

    @Override
    @Transactional
    public void deleteKanban(Kanban kanban) {
        kanbanRepository.deleteById(kanban.getId());
    }

    @Override
    @Transactional
    public Kanban addNewTaskToKanban(Long kanbanId, TaskDTO taskDTO) {
        Optional<Kanban> kanbanOptional = getKanbanById(kanbanId);
        Kanban kanBan = kanbanOptional.get();
        kanBan.addTask(convertDTOToTask(taskDTO));
        return kanBan;
    }

    private Kanban convertDTOToKanban(KanbanDTO kanbanDTO) {
        Kanban kanban = new Kanban();
        kanban.setTitle(kanbanDTO.getTitle());
        kanban.setCreatedDate(LocalDateTime.now());
        return kanban;
    }

    private Task convertDTOToTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setColor(taskDTO.getColor());
        task.setStatus(taskDTO.getStatus());
        task.setTags(taskDTO.getTags());
        task.setLocalDateTime(LocalDateTime.now());
        return task;
    }
}
