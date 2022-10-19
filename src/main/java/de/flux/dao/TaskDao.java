package de.flux.dao;

import de.flux.model.Task;
import de.flux.model.resultsetmapper.TaskMapper;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.sql.Timestamp;
import java.util.List;

public interface TaskDao {

    @SqlQuery("SELECT MAX(id) FROM tasks;")
    int getMaxTaskId();


    @SqlQuery("SELECT * FROM tasks ORDER BY datetime desc;")
    @RegisterRowMapper(TaskMapper.class)
    List<Task> readAllTasks();


    @SqlUpdate("INSERT INTO tasks "
            + "(id,title,priority,status,datetime,description) "
            + "VALUES (:id,:title,:priority,:status,:datetime,:description);")
    boolean addTask(
            @Bind("id") int id,
            @Bind("title") String title,
            @Bind("priority") String priority,
            @Bind("status") String status,
            @Bind("datetime") Timestamp datetime,
            @Bind("description") String description);



    @SqlUpdate("UPDATE tasks "
            + "SET title=:title,"
            + "priority=:priority,"
            + "status=:status,"
            + "datetime=:datetime,"
            + "description=:description "
            + "WHERE id=:id;")
    boolean updateTaskById(
            @Bind("id") int id,
            @Bind("title") String title,
            @Bind("priority") String priority,
            @Bind("status") String status,
            @Bind("datetime") Timestamp datetime,
            @Bind("description") String description);


    @SqlUpdate("DELETE FROM tasks WHERE id=:id;")
    boolean deleteTaskById(
            @Bind("id") int id);


}
