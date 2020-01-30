package com.as12.services;

import com.as12.mailing.EmailBuilder;
import com.as12.mailing.Mail;
import com.as12.models.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReportService {

    ArrayList<Employee> employeeList;
    private void createDummyRecords() {
        employeeList.add(new Employee("John", "Corner", 22, "johncorner@gmail.com"));
        employeeList.add(new Employee("Mary", "Sue", 25, "marysue@gmail.com"));
        employeeList.add(new Employee("James", "Johnson", 42, "jamesjohnson@gmail.com"));

    }
    ReportService () {
        employeeList = new ArrayList<Employee>();
        createDummyRecords(); // Populate dummy data for reports. This data probably comes from DB some where.

    }
    public Mail createSampleReport(String sender, String recipient) {
        Mail mail = new EmailBuilder()
                .From(sender) // For gmail, this field is ignored.
                .To(recipient)
                .Template("report-template.html")
                .AddContext("employeeList", this.employeeList)
                .Subject("Employee Report")
                .createMail();

       return mail;
    }
}
