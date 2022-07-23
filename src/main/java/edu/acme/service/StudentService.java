package edu.acme.service;

import edu.acme.model.Student;
import edu.acme.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

public class StudentService implements CrudService<Student,Long>{
    private StudentRepository studentRepository;

    @Override
    public List<Student> findAll() {
        return null;
    }

    @Override
    public Optional<Student> findByID(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Student student) {
        return studentRepository.delete(student);

    }

    @Override
    public void create(Student student) {
        studentRepository.create(student);
    }

    @Override
    public boolean update(Student student) {
        return false;
    }
}
