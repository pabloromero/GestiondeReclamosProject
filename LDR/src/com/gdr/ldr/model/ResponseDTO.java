package com.gdr.ldr.model;

/**
 * Created by promero on 31/07/13.
 */
public class ResponseDTO {
    public Boolean error ;
    public Object object;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }


}
