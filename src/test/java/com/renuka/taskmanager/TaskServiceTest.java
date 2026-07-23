package com.renuka.taskmanager;

import com.renuka.taskmanager.exception.TaskNotFoundException;
import com.renuka.taskmanager.model.Task;
import com.renuka.taskmanager.model.TaskStatus;
import com.renuka.taskmanager.repository.TaskRepository;
import com.renuka.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task sampleTask;

    @BeforeEach
    void setUp() {
        sampleTask = new Task("Write report", "Quarterly summary", TaskStatus.TODO, null);
        sampleTask.setId(1L);
    }

    @Test
    void getTaskById_whenTaskExists_returnsTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        Task result = taskService.getTaskById(1L);

        assertEquals("Write report", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskById_whenTaskDoesNotExist_throwsException() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(99L));
    }

    @Test
    void createTask_savesAndReturnsTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        Task result = taskService.createTask(sampleTask);

        assertNotNull(result);
        assertEquals(TaskStatus.TODO, result.getStatus());
        verify(taskRepository, times(1)).save(sampleTask);
    }

    @Test
    void updateTask_whenTaskExists_updatesFields() {
        Task updated = new Task("Updated title", "Updated desc", TaskStatus.DONE, null);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));
        when(taskRepository.save(any(Task.class))).thenReturn(sampleTask);

        Task result = taskService.updateTask(1L, updated);

        assertEquals("Updated title", result.getTitle());
        assertEquals(TaskStatus.DONE, result.getStatus());
    }

    @Test
    void deleteTask_whenTaskExists_removesTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(sampleTask));

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).delete(sampleTask);
    }

    @Test
    void getTasksByStatus_returnsFilteredList() {
        when(taskRepository.findByStatus(TaskStatus.TODO)).thenReturn(List.of(sampleTask));

        List<Task> result = taskService.getTasksByStatus(TaskStatus.TODO);

        assertEquals(1, result.size());
        assertEquals(TaskStatus.TODO, result.get(0).getStatus());
    }
}
