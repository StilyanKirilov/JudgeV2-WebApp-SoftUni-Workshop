package softuni.workshopjudge2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.workshopjudge2.model.entity.Homework;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, String> {
}
