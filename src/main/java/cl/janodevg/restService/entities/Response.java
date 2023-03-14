package cl.janodevg.restService.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {


    @JsonProperty("mensaje")
    private Object message;

    public Response() {
    }

    public Response(Object message) {
        this.message = message;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
