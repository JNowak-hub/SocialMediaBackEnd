package pl.surf.web.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.surf.web.demo.model.Level;

@Repository
public interface LevelRepo extends JpaRepository<Level, Long> {

    @Query(value = "SELECT EXISTS(SELECT * FROM levels WHERE level_id=?1)"
            , nativeQuery = true)
    Boolean checkIfLevelExists(Long levelId);
}
