package org.example.dao.jdbcTemplate;

import org.example.dao.StudentDao;
import org.example.model.Student;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbctemplate")
public class JdbcTemplateStudentDao implements StudentDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateStudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Student student) {
        jdbcTemplate.update(
                "INSERT INTO students (name, email) VALUES (?, ?)",
                student.getName(),
                student.getEmail()
        );
    }

    @Override
    public Optional<Student> findById(Long id) {
        List<Student> students = jdbcTemplate.query(
                "SELECT * FROM students WHERE id = ?",
                (rs, rowNum) ->
                        new Student(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("email")
                        ),
                id
        );

        return students.stream().findFirst();
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM students",
                (rs, rowNum) ->
                        new Student(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("email")
                        )
        );
    }

    @Override
    public void update(Student student) {
        jdbcTemplate.update(
                "UPDATE students SET name = ?, email = ? WHERE id = ?",
                student.getName(),
                student.getEmail(),
                student.getId()
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(
                "DELETE FROM students WHERE id = ?",
                id
        );
    }
}
