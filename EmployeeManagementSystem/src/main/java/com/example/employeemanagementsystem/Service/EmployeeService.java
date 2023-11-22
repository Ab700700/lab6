package com.example.employeemanagementsystem.Service;

import com.example.employeemanagementsystem.Model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    List<Employee> employeeList = new ArrayList<>();

    public List<Employee> getAll(){
        return employeeList;
    }

    public void add(Employee employee){
        employeeList.add(employee);
    }

    public boolean update(String id , Employee employee) {
        for (int i = 0; i < employeeList.size(); i++) {
            if(employeeList.get(i).getId().equals(id)){
                employeeList.set(i,employee);
                return true;
            }
        }
        return false;
    }

    public boolean delete(String id ){
        for(int i =0 ; i < employeeList.size(); i++){
            if(employeeList.get(i).getId().equals(id)){
                employeeList.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Employee> searchByPosition(String position){
        List<Employee> employees = new ArrayList<>();
        for(Employee e: employeeList){
            if(e.getPosition().equals(position)){
                employees.add(e);
            }
        }
        return employees;
    }

    public List<Employee> getByAgeRange(int start, int end){
        List<Employee> employees = new ArrayList<>();
        for(Employee e: employeeList){
            if(e.getAge()>=start && e.getAge()<=end){
                employees.add(e);
            }
        }
        return employees;
    }

    public Employee search(String id){
        for(Employee e : employeeList){
            if(e.getId().equals(id)) return e;
        }
        return  null;
    }

    public char applyLeave(String id){
        Employee em = search(id) ;

        if(em== null) return 'f';
        else if(em.isOnLeave()) return 'l';
        else if(em.getAnnualLeave()<=0)return 'a';
        else{em.setOnLeave(true);
            em.setAnnualLeave(em.getAnnualLeave()-1);
            return 'd';}


    }

    public List<Employee> noLeave(){
        List<Employee> employees = new ArrayList<>();
        for(Employee e : employeeList){
            if(e.getAnnualLeave()==0) employees.add(e);
        }
        return employees;
    }



    public char promote(String emid,String reid){
        Employee em = search(emid) ;
        Employee re = search(reid);


        if(em== null || re == null) return 'f';
        else if(em.isOnLeave()) return 'l';
        else if(em.getAge()<30)return 'a';
        else if(re.getPosition().equals("coordinator")|| em.getPosition().equals("supervisor"))return 'd';
        else {
            em.setPosition("supervisor");
            return 'p';
        }


    }

}
