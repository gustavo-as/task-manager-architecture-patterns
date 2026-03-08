package com.gustavohub.clean.infrastructure.repository.jdbc;

import com.gustavohub.clean.domain.entity.Task;
import com.gustavohub.clean.domain.repository.TaskRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * Adapter JDBC puro - sem Hibernate, sem ORM.
 * Ativar em application.properties: app.persistence.adapter=jdbc
 */
@Component
@ConditionalOnProperty(name = "app.persistence.adapter", havingValue = "jdbc")
public class TaskJdbcAdapter implements TaskRepository {

    private final JdbcTemplate jdbc;

    public TaskJdbcAdapter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Task save(Task task) {
        return task.getId() == null ? insert(task) : update(task);
    }

    private Task insert(Task task) {
        String sql = "INSERT INTO tasks (title, description, completed, created_at) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setBoolean(3, task.isCompleted());
            ps.setTimestamp(4, Timestamp.valueOf(task.getCreatedAt()));
            return ps;
        }, keyHolder);
        Long newId = keyHolder.getKey().longValue();
        return Task.reconstitute(newId, task.getTitle(), task.getDescription(),
                task.isCompleted(), task.getCreatedAt());
    }

    private Task update(Task task) {
        jdbc.update("UPDATE tasks SET title=?, description=?, completed=? WHERE id=?",
                task.getTitle(), task.getDescription(), task.isCompleted(), task.getId());
        return task;
    }

    @Override
    public Optional<Task> findById(Long id) {
        List<Task> result = jdbc.query("SELECT * FROM tasks WHERE id=?", new TaskRowMapper(), id);
        return result.stream().findFirst();
    }

    @Override
    public List<Task> findAll() {
        return jdbc.query("SELECT * FROM tasks ORDER BY created_at DESC", new TaskRowMapper());
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("DELETE FROM tasks WHERE id=?", id);
    }

    @Override
    public boolean existsById(Long id) {
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM tasks WHERE id=?", Integer.class, id);
        return count != null && count > 0;
    }

    private static class TaskRowMapper implements RowMapper<Task> {
        @Override
        public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Task.reconstitute(
                    rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getBoolean("completed"),
                    rs.getTimestamp("created_at").toLocalDateTime());
        }
    }
}
