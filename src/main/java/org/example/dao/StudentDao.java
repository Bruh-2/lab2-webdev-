package org.example.dao;


import org.example.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {

    void save(Student student);

    Optional<Student> findById(Long id);

    List<Student> findAll();

    void update(Student student);

    void deleteById(Long id);
}
