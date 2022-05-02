package com.arthi.traders.model;

public class Request_model {
    private String Requests_id,Requests_from,Requests_to,Requests_description,Requests_status,Requests_docs;

    public Request_model() {

    }

    public String getRequests_id() {
        return Requests_id;
    }

    public String getRequests_from() {
        return Requests_from;
    }

    public String getRequests_to() {
        return Requests_to;
    }

    public String getRequests_description() {
        return Requests_description;
    }

    public String getRequests_status() {
        return Requests_status;
    }

    public String getRequests_docs() {
        return Requests_docs;
    }

    public void setRequests_id(String requests_id) {
        Requests_id = requests_id;
    }

    public void setRequests_from(String requests_from) {
        Requests_from = requests_from;
    }

    public void setRequests_to(String requests_to) {
        Requests_to = requests_to;
    }

    public void setRequests_description(String requests_description) {
        Requests_description = requests_description;
    }

    public void setRequests_status(String requests_status) {
        Requests_status = requests_status;
    }

    public void setRequests_docs(String requests_docs) {
        Requests_docs = requests_docs;
    }
}
