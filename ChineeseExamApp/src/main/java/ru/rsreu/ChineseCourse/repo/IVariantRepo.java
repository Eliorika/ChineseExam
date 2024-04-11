package ru.rsreu.ChineseCourse.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.rsreu.ChineseCourse.model.Course;
import ru.rsreu.ChineseCourse.model.Variant;

import java.util.List;

@Repository
public interface IVariantRepo extends JpaRepository<Variant, Long> {

    @Query(value = "select * from get_translation(?1)", nativeQuery = true)
    Variant getTranslation(Long id);
}
