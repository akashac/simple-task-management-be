package de.flux.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
public class Task {
    private int id;
    private String title;
    private String description;
    private String priority;
    private String status;

    private Timestamp dateTime;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + "\"," +
                "\"title\":\"" + title + "\"," +
                "\"description\":\"" + description + "\"," +
                "\"priority\":\"" + priority + "\"," +
                "\"status\":\"" + status + "\"," +
                "\"dateTime\":\"" + dateTime + "\"" +
                "}";
    }
}
