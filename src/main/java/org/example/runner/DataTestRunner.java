package org.example.runner;

import org.example.dao.StudentDao;
import org.example.model.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataTestRunner implements CommandLineRunner {

    private final StudentDao studentDao;

    public DataTestRunner(StudentDao studentDao) { this.studentDao = studentDao; }

    @Override
    public void run(String... args) {
        System.out.println("=== INSERT ===");
        studentDao.save(new Student(null, "Alex", "alex@test.com"));
        studentDao.save(new Student(null, "Maria", "maria@test.com"));

        System.out.println("=== FIND ALL ===");
        studentDao.findAll().forEach(System.out::println);

        System.out.println("=== UPDATE ===");
        Student student = studentDao.findById(1L).orElseThrow();
        student.setEmail("alex_updated@test.com");
        studentDao.update(student);

        System.out.println("=== AFTER UPDATE ===");
        System.out.println(studentDao.findById(1L).orElseThrow());

        System.out.println("=== DELETE ===");
        studentDao.deleteById(2L);

        System.out.println("=== FINAL LIST ===");
        studentDao.findAll().forEach(System.out::println);
    }
}
