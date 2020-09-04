package pl.surf.web.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.surf.web.demo.exceptions.LevelNotFoundException;
import pl.surf.web.demo.model.Level;
import pl.surf.web.demo.repository.LevelRepo;

import java.util.List;
import java.util.Optional;

@Service
public class LevelService {

    private LevelRepo levelRepo;

    public LevelService(LevelRepo levelRepo) {
        this.levelRepo = levelRepo;
    }

    public Level getLevelById(Long levelId){
        final Optional<Level> levelOptional = levelRepo.findById(levelId);
        if(levelOptional.isEmpty()){
            throw  new LevelNotFoundException("no such level with id = " + levelId);
        }
        return levelOptional.get();
    }

    public Boolean checkIfLevelExists(Long levelId){
        if(levelRepo.checkIfLevelExists(levelId)){
            return true;
        }
        throw new LevelNotFoundException("Level with id: " + levelId + " doesn't exists");
    }

    public List<Level> getAllLevels(){
        return levelRepo.findAll();
    }

    @Transactional
    public Long addNewLevel(Level level){
        return levelRepo.save(level).getId();
    }
}
