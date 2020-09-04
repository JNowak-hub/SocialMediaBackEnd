package pl.surf.web.demo.controller;

import org.springframework.web.bind.annotation.*;
import pl.surf.web.demo.model.Level;
import pl.surf.web.demo.service.LevelService;

import java.util.List;

@RestController
@RequestMapping("api/level")
@CrossOrigin(origins = "{cross.origin}")

public class LevelController {

    private LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @GetMapping("/{levelId}")
    public Level getLevelById(@PathVariable Long levelId) {
        return levelService.getLevelById(levelId);
    }

    @GetMapping
    public List<Level> getAllModels() {
        return levelService.getAllLevels();
    }

    @PostMapping
    public Long addLevel(@RequestBody Level level) {
        return levelService.addNewLevel(level);
    }
}
