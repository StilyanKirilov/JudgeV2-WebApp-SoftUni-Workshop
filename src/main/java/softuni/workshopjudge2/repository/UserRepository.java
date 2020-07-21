package softuni.workshopjudge2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.workshopjudge2.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
}
