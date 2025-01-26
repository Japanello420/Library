package com.example.library.libraryupdater;

import com.example.library.libraryupdater.Controller.UpdaterController;
import com.example.library.libraryupdater.Service.UpdaterService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

public class UpdaterControllerTest {

    @Mock
    private UpdaterService updaterService;

    @InjectMocks
    private UpdaterController updaterController;

    private MockMvc mockMvc;

    public UpdaterControllerTest() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(updaterController).build();
    }

    @Test
    public void testStartUpdate() throws Exception {
        // Arrange
        doNothing().when(updaterService).updateData();

        // Act & Assert
        mockMvc.perform(get("/updater/start"))
                .andExpect(status().isOk())
                .andExpect(content().string("Data update completed."));

        verify(updaterService, times(1)).updateData();
    }
}
