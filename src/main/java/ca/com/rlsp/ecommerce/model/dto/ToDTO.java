package ca.com.rlsp.ecommerce.model.dto;

import java.io.Serializable;

public class ToDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private String postal_code;

    public String getpostal_code() {
        return postal_code;
    }

    public void setpostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

}
