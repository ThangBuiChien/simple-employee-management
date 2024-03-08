package com.example.firstspringproject.repository;

import com.example.firstspringproject.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>  {

    @Query("SELECT p FROM Employee p WHERE CONCAT(p.firstName, ' ', p.lastName, ' ', p.email, ' ', p.id) LIKE %?1%")
    public List<Employee> search(String keyword);

    @Query("SELECT p FROM Employee p WHERE CONCAT(p.firstName, ' ', p.lastName, ' ', p.email, ' ', p.id) LIKE %?1%")
    public Page<Employee> searchPageable(Pageable pageable, String keyword);






}
