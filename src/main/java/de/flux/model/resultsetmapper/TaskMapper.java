package de.flux.model.resultsetmapper;

import de.flux.model.Task;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<Task> {
    @Override
    public Task map(ResultSet rs, StatementContext ctx) throws SQLException {

        Task task = new Task();
        task.setId(rs.getInt("id"));
        task.setTitle(rs.getString("title"));
        task.setStatus(rs.getString("status"));
        task.setPriority(rs.getString("priority"));
        task.setDescription(rs.getString("description"));
        task.setDateTime(rs.getTimestamp("dateTime"));
        return task;
    }
}
