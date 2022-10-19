package de.flux.resource;


import de.flux.dao.TaskDao;
import de.flux.model.CustomResponse;
import de.flux.model.Task;
import io.agroal.api.AgroalDataSource;
import io.quarkus.runtime.Startup;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Path("/taskmanger")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Startup
@ApplicationScoped
public class TaskManagerResource {

    @Inject
    AgroalDataSource dataSource;

    private Jdbi jdbi;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @PostConstruct
    public void intializeVaribales(){
        jdbi = Jdbi.create(dataSource);
        jdbi.installPlugin(new SqlObjectPlugin());

    }

    @GET
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response readTaskManagerList() {

        System.out.println("get all");

        CustomResponse customResponse = new CustomResponse();
        customResponse.message = "success";

        customResponse.data = getTaskList();
        return Response.ok(customResponse.data).build();
    }



    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewTask(Task newTask) {


        System.out.println("Add New");

        int maxId = jdbi.withExtension(TaskDao.class, TaskDao::getMaxTaskId);

        Timestamp timestamp = Timestamp.valueOf(sdf.format(new Date(System.currentTimeMillis())));

        jdbi.withExtension(TaskDao.class,
                dao -> dao.addTask(
                        maxId + 1,
                        newTask.getTitle(),
                        newTask.getPriority(),
                        newTask.getStatus(),
                        timestamp,
                        newTask.getDescription()));


        CustomResponse customResponse = new CustomResponse();
        customResponse.message = "success";
        customResponse.data = getTaskList();

        return Response.ok(customResponse.data).build();

    }

    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTask(Task newTask) {

        Timestamp timestamp = Timestamp.valueOf(sdf.format(new Date(System.currentTimeMillis())));

        jdbi.withExtension(TaskDao.class,
                dao -> dao.updateTaskById(newTask.getId(),
                        newTask.getTitle(),
                        newTask.getPriority(),
                        newTask.getStatus(),
                        timestamp,
                        newTask.getDescription()));

        CustomResponse customResponse = new CustomResponse();
        customResponse.message = "success";
        customResponse.data = getTaskList();

        return Response.ok(customResponse.data).build();

    }


    @DELETE
    @Path("/delete/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTaskById(@PathParam("id") int id) {

        jdbi.withExtension(TaskDao.class, dao -> dao.deleteTaskById(id));

        CustomResponse customResponse = new CustomResponse();


        customResponse.message = "success";
        customResponse.data = getTaskList();
        return Response.ok(customResponse.data).build();
    }



    public List<Task>  getTaskList(){

        return jdbi.withExtension(
                TaskDao.class,
                TaskDao::readAllTasks);
    }

}
