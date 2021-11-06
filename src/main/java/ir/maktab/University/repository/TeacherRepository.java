package ir.maktab.University.repository;

import ir.maktab.University.entities.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    List<Teacher> findAllByIsActiveTrue();

    Teacher findByUserNameAndPassword(String username, String password);

    Teacher findByUserName(String username);
}
