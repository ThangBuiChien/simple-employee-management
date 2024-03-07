package com.example.firstspringproject.service;

import com.example.firstspringproject.model.Employee;

import java.util.*;

public interface EmployeeService {
    List<Employee> getAllEmployee();

    void saveEmp(Employee emp);

    Employee findEmpById(long id);

    void deleteEmp(long id);



}
