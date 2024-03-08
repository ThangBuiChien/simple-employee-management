package com.example.firstspringproject.controller;

import com.example.firstspringproject.model.Employee;
import com.example.firstspringproject.repository.EmployeeRepository;
import com.example.firstspringproject.service.EmployeeService;
import com.example.firstspringproject.service.EmployeeServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

//import org.imgscalr.Scalr;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;


@Controller
@SessionAttributes("pageSize")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private HttpSession httpSession;


    @GetMapping("/sorting")
    public String getEmp(Model model,
            @RequestParam(defaultValue = "-1") int page,
            @RequestParam(defaultValue = "-1") int size,
            @RequestParam(defaultValue = "-1") String sortBy) {

        if(page == -1){
            page = (int)httpSession.getAttribute("page");
        }
        else{
            httpSession.setAttribute("page", page);

        }if(size == -1){
            size = (int)httpSession.getAttribute("size");
        }
        else{
            httpSession.setAttribute("size", size);

        }if(sortBy.equals("-1")){
            sortBy = (String)httpSession.getAttribute("sortBy");
        }
        else{
            httpSession.setAttribute("sortBy", sortBy);

        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Employee> emp = employeeService.findAll(pageable);
        model.addAttribute("listEmployees" ,employeeService.findAll(pageable) );
        int totalPages = emp.getTotalPages();
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);

       // httpSession.setAttribute("size", size);
      //  model.addAttribute("size", size);




        //httpSession.setAttribute("pageSize", size);


        return "index";
    }


    @GetMapping("/")
    public String viewHomePage(Model model){
        //set session object
        int page=0;
        int size = 3;
        String sortBy = "id";
        httpSession.setAttribute("pageSize", size);
        httpSession.setAttribute("page", page);
        httpSession.setAttribute("sortBy", "id");

        return "redirect:/sorting";
    }

    @GetMapping("/showEmpForm")
    public String showEmpForm(Model model){
        Employee emp = new Employee();
        model.addAttribute("employee", emp);
        return "add_new_emp";
    }

//    @PostMapping("/saveEmp")
//    public String saveEmp(@ModelAttribute("employee") Employee emp){
//        employeeService.saveEmp(emp);
//        return "redirect:/";
//
//    }

    @PostMapping("/saveEmp")
    public String saveEmp(@ModelAttribute("employee") Employee emp,
                          @RequestParam("image") MultipartFile file)
            throws IOException, SerialException, SQLException {

        //handle picture
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);

        emp.setAvatar(blob);

        // Redirect to the home page or any other appropriate view
        employeeService.saveEmp(emp);
        return "redirect:/";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        // save employee to database
        employeeService.saveEmp(employee);
        return "redirect:/";
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> displayImage(@RequestParam("id") long id) throws IOException, SQLException
    {
        Employee emp = employeeService.findEmpById(id);
        byte [] imageBytes = null;
        imageBytes = emp.getAvatar().getBytes(1,(int) emp.getAvatar().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }

    @GetMapping("/deleteEmp/{id}")
    public String deleteEmp(@PathVariable(value = "id") Long id) {
        // save employee to database
        employeeService.deleteEmp(id);
        return "redirect:/";
    }

    @GetMapping("/showUpdateEmpForm/{id}")
    public String showUpdateEmpForm(@PathVariable(value = "id") Long id, Model model) {
        // save employee to database
        Employee emp = employeeService.findEmpById(id);
        model.addAttribute("employee", emp);
        return "update_emp";
    }





}
