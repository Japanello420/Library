package com.example.library.libraryupdater.Controller;

import com.example.library.libraryupdater.Service.UpdaterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdaterController {
    private final UpdaterService updaterService;

    public UpdaterController(UpdaterService updaterService) {
        this.updaterService = updaterService;
    }

    @GetMapping("/updater/start")
    public String startUpdate() {
        updaterService.updateData();
        return "Data update completed.";
    }
}
