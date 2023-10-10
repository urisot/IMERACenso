package com.imera.censo.Contracts.Models;

public class Parameter {
    String parameter_name = "";
    String parameter_value = "";

    public Parameter(){
    }

    public Parameter(String key, String value){
        parameter_name = key;
        parameter_value = value;
    }

    public String getParameter_value() {
        return parameter_value;
    }

    public void setParameter_value(String parameter_value) {
        this.parameter_value = parameter_value;
    }

    public String getParameter_name() {
        return parameter_name;
    }

    public void setParameter_name(String parameter_name) {
        this.parameter_name = parameter_name;
    }





}
