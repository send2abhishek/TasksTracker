package com.attra.taskstracker.Infrastructure;

import java.util.HashMap;

public class ServiceResponse {


    private HashMap<String, String> propertyErrors;

    public ServiceResponse() {

        propertyErrors = new HashMap<>();
    }


    public void setPropertyErrors(String Property, String Error) {

        propertyErrors.put(Property, Error);
    }

    public String getPropertyErrors(String Property) {


        return propertyErrors.get(Property);
    }


    public boolean didSucceed() {


        return propertyErrors.size() == 0;
    }

    public int getpropertySize() {

        return propertyErrors.size();
    }
}
