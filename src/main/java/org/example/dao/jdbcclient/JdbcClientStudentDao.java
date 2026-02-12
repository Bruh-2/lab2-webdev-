package org.example.dao.jdbcclient;

import org.example.dao.StudentDao;
import org.example.model.Student;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbcclient")
public class JdbcClientStudentDao implements StudentDao {

    private final JdbcTemplate jdbcTemplate;

    // Здесь явно говорим Spring, что нужен DataSource ds2
    public JdbcClientStudentDao(@Qualifier("ds2") DataSource ds2) {
        this.jdbcTemplate = new JdbcTemplate(ds2);
    }

    @Override
    public void save(Student student) {
        jdbcTemplate.update("INSERT INTO students (name, email) VALUES (?, ?)",
                student.getName(), student.getEmail());
    }

    @Override
    public Optional<Student> findById(Long id) {
        List<Student> list = jdbcTemplate.query(
                "SELECT * FROM students WHERE id = ?",
                (rs, rowNum) -> new Student(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email")),
                id);
        return list.stream().findFirst();
    }

    @Override
    public List<Student> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM students",
                (rs, rowNum) -> new Student(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email")));
    }

    @Override
    public void update(Student student) {
        jdbcTemplate.update("UPDATE students SET name=?, email=? WHERE id=?",
                student.getName(), student.getEmail(), student.getId());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM students WHERE id=?", id);
    }
}
