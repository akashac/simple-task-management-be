package de.flux.model;

import lombok.Getter;

import java.util.List;

@Getter
public class CustomResponse {
    public String message = "";
    public Object data = null;
    public List<String> errors;
    public int status;
}
