package com.kms.seft203;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.seft203.task.Task;
import com.kms.seft203.task.TaskApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskApi.class)
public class TaskApiTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppVersionRepository appVersionRepo;

    @Test
    void testGetCurrentVersion() throws Exception {
//        Task task = new Task(0L, "Task 1", false, "user-1");

//        this.mockMvc.perform(
//                    post("/tasks")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .accept(MediaType.APPLICATION_JSON)
//                            .content(toJSONString(task)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.task").value("Task 1"))
//                .andExpect(jsonPath("$.isCompleted").value(false));
    }

    private String toJSONString(Object obj) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(obj);
    }
}
