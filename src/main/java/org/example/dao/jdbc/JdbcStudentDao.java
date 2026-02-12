package org.example.dao.jdbc;

import org.example.dao.StudentDao;
import org.example.model.Student;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("jdbc")
public class JdbcStudentDao implements StudentDao {

    private final DataSource dataSource;

    public JdbcStudentDao(DataSource dataSource) { this.dataSource = dataSource; }

    @Override
    public void save(Student student) {
        String sql = "INSERT INTO students (name, email) VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public Optional<Student> findById(Long id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(new Student(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email")));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email")));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return students;
    }

    @Override
    public void update(Student student) {
        String sql = "UPDATE students SET name=?, email=? WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setLong(3, student.getId());
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { throw new RuntimeException(e); }
    }
}
