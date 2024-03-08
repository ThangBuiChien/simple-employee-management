package com.example.firstspringproject.service;

import com.example.firstspringproject.model.Employee;
import com.example.firstspringproject.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

public interface EmployeeService {


    List<Employee> getAllEmployee();

    void saveEmp(Employee emp);

    Employee findEmpById(long id);

    void deleteEmp(long id);

    Page<Employee> findAll(Pageable pageable);





}
