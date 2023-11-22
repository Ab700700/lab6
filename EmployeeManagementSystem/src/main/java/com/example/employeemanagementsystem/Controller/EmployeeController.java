package com.example.employeemanagementsystem.Controller;

import com.example.employeemanagementsystem.ApiResponse.ApiResponse;
import com.example.employeemanagementsystem.Model.Employee;
import com.example.employeemanagementsystem.Service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ems")
@RequiredArgsConstructor
public class EmployeeController {

    public final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity get(){
        return  ResponseEntity.status(HttpStatus.OK).body(employeeService.getAll());
    }
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody @Valid Employee employee, Errors errors){
        if(errors.hasErrors()) return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(errors.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST.toString()));
        employeeService.add(employee);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Employee added",HttpStatus.OK.toString()));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity update(@PathVariable String id , @RequestBody @Valid Employee employee,Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(errors.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST.toString()));
        if(employeeService.update(id,employee)){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Employee updated",HttpStatus.OK.toString()));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Employee not found",HttpStatus.NOT_FOUND.toString()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id){
        if(employeeService.delete(id)){
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Employee deleted",HttpStatus.OK.toString()));
        }else{
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Employee not found",HttpStatus.NOT_FOUND.toString()));
        }
    }

    @GetMapping("/search/{position}")
    public ResponseEntity getByPosition(@PathVariable String position){
        if(position.equals("supervisor")||position.equals("coordinator")){
           return ResponseEntity.status(HttpStatus.OK).body(employeeService.searchByPosition(position));
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Write a valid position",HttpStatus.NOT_FOUND.toString()));
        }
    }

    @GetMapping("/searchage/{min}/{max}")
    public ResponseEntity getByAge(@PathVariable int min, @PathVariable int max){
        if(min<25 || max<25 || min>max ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("wrong input",HttpStatus.BAD_REQUEST.toString()));
        }else {
            return ResponseEntity.status(HttpStatus.OK).body(employeeService.getByAgeRange(min,max));
        }
    }

    @PutMapping("/applyannual/{id}")
    public ResponseEntity applyAnnual(@PathVariable String id){
        char Em = employeeService.applyLeave(id);
        if(Em=='f') return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Employee not found",HttpStatus.NOT_FOUND.toString()));
        else if(Em=='l') return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Employee already leave",HttpStatus.BAD_REQUEST.toString()));
        else if(Em=='a') return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("No annual leave remain",HttpStatus.BAD_REQUEST.toString()));
        else {
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Done",HttpStatus.OK.toString()));
        }
    }

    @GetMapping("/annual")
    public ResponseEntity annualLeave(){
        return ResponseEntity.status(HttpStatus.OK).body(employeeService.noLeave());
    }

    @PutMapping("/promote/{request}/{employee}")
    public ResponseEntity promoteEm(@PathVariable String request, @PathVariable String employee){
        char pro = employeeService.promote(employee,request);
        if(pro == 'f') return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("One of the employee not found",HttpStatus.NOT_FOUND.toString()));
        else if(pro=='l') return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Employee leave",HttpStatus.NOT_FOUND.toString()));
        else if(pro=='a') return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Employee is under 30",HttpStatus.BAD_REQUEST.toString()));
        else if(pro == 'd') return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("The requester is not a supervisor or the candidate is already supervisor",HttpStatus.BAD_REQUEST.toString()));
        else{
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Employee promoted",HttpStatus.OK.toString()));
        }
    }




}
