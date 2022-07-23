package edu.acme.service;

import edu.acme.model.Department;
import edu.acme.repository.DepartmentRepository;
import edu.acme.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

public class DepartmentService implements CrudService<Department,Long>{

    private  DepartmentRepository departmentRepository;
    private  StudentRepository studentRepository;

    @Override
    public List<Department> findAll() {
        return null;
    }

    @Override
    public Optional<Department> findByID(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Department department) {
        return departmentRepository.delete(department);
    }

    @Override
    public void create(Department department) {
       departmentRepository.create(department);
    }

    @Override
    public boolean update(Department department) {
        return false;
    }
}
