package ir.maktab.University.service.impl;

import ir.maktab.University.entities.*;
import ir.maktab.University.repository.TeacherRepository;
import ir.maktab.University.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
public class TeacherServiceImpl extends BaseServiceImpl<Teacher,Long,TeacherRepository> implements TeacherService {



    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        super(teacherRepository);
        this.teacherRepository = teacherRepository;
    }
    @Override
    public Teacher getTeacherByUserName(String username) {
        return teacherRepository.findByUserName(username);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAllByIsActiveTrue();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addCourseToTeacher(Course course, Teacher teacher) {
        teacher.getCourse().add(course);
        save(teacher);
    }

    @Override
    public Boolean isTeacherAllow(String username) {
        Teacher teacher = getTeacherByUserName(username);
        return teacher.getStatus().equals("Accepted");
    }

    @Override
    public Teacher createTeacher(User user) {
        Teacher teacher = new Teacher();
        Role role = new Role();
        role.setRoleName("TEACHER");
        teacher.setStatus("In progress");
        teacher.setFirstName(user.getFirstName());
        teacher.setLastName(user.getLastName());
        teacher.setBirthday(user.getBirthday());
        teacher.setUserName(user.getUserName());
        teacher.setPassword(user.getPassword());
        teacher.setNationalCode(user.getNationalCode());
        teacher.setGender(user.getGender());
        teacher.setEmail(user.getEmail());
        teacher.setType(user.getType());
        teacher.setActive(true);
        teacher.getRoles().add(role);
        save(teacher);
        return teacher;
    }

    @Override
    public void rejectTeacher(long userId) {
        Teacher teacher = findById(userId).get();
        teacher.setStatus("Rejected");
        save(teacher);
    }

    @Override
    public void acceptTeacher(long userId) {
        Teacher teacher = findById(userId).get();
        teacher.setStatus("Accepted");
        teacher.setActive(true);
        save(teacher);
    }

    @Override
    public void inActiveTeacher(long teacherId) {
        Teacher teacher = findById(teacherId).get();
        teacher.setActive(false);
        teacher.setUserName(null);
        save(teacher);
    }

    @Override
    public void changeRoleToTeacher(User user, String username) {
        Teacher teacher = createTeacher(user);
        teacher.setType("Teacher");
        teacher.setStatus("Accepted");
        teacher.setUserName(username);
        save(teacher);
    }
}
