package com.as12.models;

import java.util.Objects;

public class ReportRequest {
    private String recipient;

    public ReportRequest() {
        this.recipient = "";
    }
    public ReportRequest(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportRequest that = (ReportRequest) o;
        return Objects.equals(recipient, that.recipient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipient);
    }
}
