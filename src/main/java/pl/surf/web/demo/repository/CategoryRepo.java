package pl.surf.web.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.surf.web.demo.model.Category;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    @Modifying
    @Query(value = "DELETE FROM Categories WHERE id=?1", nativeQuery = true)
    void deleteCategory(Long categoryId);

    boolean existsByDescription(String description);
}
