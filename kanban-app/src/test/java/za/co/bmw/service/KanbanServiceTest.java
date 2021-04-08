package za.co.bmw.service;

import za.co.bmw.kanban.model.Kanban;
import za.co.bmw.kanban.model.KanbanDTO;
import za.co.bmw.kanban.repository.KanbanRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import za.co.bmw.kanban.service.KanbanService;
import za.co.bmw.kanban.service.KanbanServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class KanbanServiceTest {

    KanbanService kanbanService;
    @Mock
    KanbanRepository kanbanRepository;

    @Before
    public void init() {
        kanbanService = new KanbanServiceImpl(kanbanRepository);
    }
    @Test
    public void TestKanbansInDB(){
        //given
        mockKanbanInDatabase(2);

        //when
        List<Kanban> kanbans = kanbanService.getAllKanbanBoards();

        //then
        assertEquals(2,kanbans.size());
    }

    @Test
    public void KanbanWithIdExistInDB_thenGetKanban() {
        //given
        when(kanbanRepository.findById(2L))
                .thenReturn(Optional.of(getKanban(2L)));
        //when
        Kanban kanban = kanbanService.getKanbanById(2L).get();
        //then
        assertEquals(2L, (long) kanban.getId());
    }

    @Test
    public void KanbanWithTitleExistInDB_thenGetKanban() {
        String title = "Kanban 1";
        //given
        when(kanbanRepository.findByTitle(title))
                .thenReturn(Optional.of(getKanban(1L)));
        //when
        Kanban kanban = kanbanService.getKanbanByTitle(title).get();
        //then
        assertEquals(1L, (long) kanban.getId());
    }
/**
    @Test
    public void whenNewKanbanToSave_thenSaveKanban() {
        String title = "Kanban 1";
        //given
        Kanban newKanban = new Kanban();
        newKanban.setTitle(title);

        when(kanbanRepository.save(any(Kanban.class)))
                .thenReturn(newKanban);

        //when
        Kanban kanban = kanbanService.saveNewKanban(new KanbanDTO(title));

        //then
        assertEquals(kanban.getTitle(), newKanban.getTitle());
    }
**/
    private void mockKanbanInDB(int kanbanCount) {
        when(kanbanRepository.findAll())
                .thenReturn(createKanbanList(kanbanCount));
    }
    private Kanban getKanban(Long kanbanId) {
        Kanban kanban = new Kanban();
        kanban.setId(kanbanId);
        kanban.setTitle("Kanban " + kanbanId);
        kanban.setTasks(new ArrayList<>());
        return kanban;
    }
    /**
    private List<Kanban> createKanbanList(int kanbanCount) {
        List<Kanban> kanbans = new ArrayList<>();
        IntStream.range(0, kanbanCount)
                .forEach(number -> {
                    Kanban kanban = new Kanban();
                    kanban.setId(Long.valueOf(number));
                    kanban.setTitle("Kanban " + number);
                    kanban.setTasks(new ArrayList<>());
                    kanbans.add(kanban);
                });
        return kanbans;
    }
     **/
    /** @Test
    public void KanbanWithTitleExistInDB_thenGetKanban() {
        String title = "Kanban 1";
        //given
        when(kanbanRepository.findByTitle(title))
                .thenReturn(Optional.of(getKanban(1L)));
        //when
        Kanban kanban = kanbanService.getKanbanByTitle(title).get();
        //then
        assertEquals(1L, (long) kanban.getId());
    }**/
    @Test
    public void when2KanbansInDatabase_thenGetListWithAllOfThem() {
        //given
        mockKanbanInDatabase(2);

        //when
        List<Kanban> kanbans = kanbanService.getAllKanbanBoards();

        //then
        assertEquals(2, kanbans.size());
    }

    @Test
    public void whenNewKanbanToSave_thenSaveKanban() {
        String title = "Kanban 1";
        //given
        Kanban newKanban = new Kanban();
        newKanban.setTitle(title);

        when(kanbanRepository.save((any(Kanban.class))))
                .thenReturn(newKanban);

        //when
        Kanban kanban = kanbanService.saveNewKanban(new KanbanDTO(title));

        //then
        assertEquals(kanban.getTitle(), newKanban.getTitle());
    }

    private void mockKanbanInDatabase(int kanbanCount) {
        when(kanbanRepository.findAll())
                .thenReturn(createKanbanList(kanbanCount));
    }

    /**
     * Mock Delete Kanban

     * **/
    public void MockDeleteKanban(){

        //when
        //Kanban kanban = kanbanService.getKanbanById(2L).get();
        Kanban kanban = new Kanban();
        when(kanbanService.getKanbanById((2L))).thenReturn(Optional.of(kanban));
        kanbanService.deleteKanban(kanban);

        //then
        verify(kanbanService,times(1)).deleteKanban(kanban);
    }

    private List<Kanban> createKanbanList(int kanbanCount) {
        List<Kanban> kanbans = new ArrayList<>();
        IntStream.range(0, kanbanCount)
                .forEach(number ->{
                    Kanban kanban = new Kanban();
                    kanban.setId(Long.valueOf(number));
                    kanban.setTitle("Kanban " + number);
                    kanban.setTasks(new ArrayList<>());
                    kanbans.add(kanban);
                });
        return kanbans;
 }
}
