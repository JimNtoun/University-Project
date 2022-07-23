package edu.acme.repository;

import edu.acme.DatabaseConnectionPooling;
import edu.acme.model.Enrollment;
import edu.acme.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository implements CrudRepository<Student, Long>{
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionPooling.class);

    @Override
    public List<Student> findAll() {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""))){

             ResultSet resultSet = preparedStatement.executeQuery();

             List<Student> students = new ArrayList<>();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setName(resultSet.getString("name"));
                student.setAddress(resultSet.getString("address"));
                student.setPhone(resultSet.getString("phone"));
                students.add(student);
            }

            } catch (SQLException ex) {
            logger.error("Error.", ex);
        }

        return null;
    }

    @Override
    public Optional<Student> findById(Long id) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""))){

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setName(resultSet.getString("name"));
                student.setAddress(resultSet.getString("address"));
                student.setPhone(resultSet.getString("phone"));
            }else{
                return Optional.empty();
            }

        } catch (SQLException ex) {
            logger.error("Error.", ex);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Student student) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""))){


            preparedStatement.setLong(1,student.getId());
            int row = preparedStatement.executeUpdate();
            return row == 1;
        } catch (SQLException ex) {
            logger.error("Error.", ex);
        }
        return false;
    }

    @Override
    public boolean update(Student student) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""))){

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getAddress());
            preparedStatement.setString(3, student.getPhone());
            preparedStatement.setLong(4, student.getId());

            int row = preparedStatement.executeUpdate();
            return row == 1;

        } catch (SQLException ex) {
            logger.error("Error.", ex);
        }
        return false;
    }

    @Override
    public boolean create(Student student) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""), new String[]{"id"})){

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getAddress());
            preparedStatement.setString(3, student.getPhone());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            logger.error("Error.", ex);
        }
        return false;
    }

    public boolean deleteEnrollment(final Enrollment enrollment) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""))) {


            preparedStatement.setLong(1, enrollment.getId());

            int row = preparedStatement.executeUpdate();
            return row == 1;
        } catch (SQLException ex) {
            logger.error("Error.", ex);
        }
        return false;
    }

    public void createEnrollment(final Student student, final Enrollment enrollment) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""), new String[]{"id"})) {


            preparedStatement.setLong(1, enrollment.getUnit().getId());
            preparedStatement.setDouble(2, enrollment.getGrade());
            preparedStatement.setLong(3, student.getId());
            preparedStatement.executeUpdate();

            // setting id
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next(); // we only suppose that there is a single generated key
            enrollment.setId(generatedKeys.getLong(1));

        } catch (SQLException ex) {
            logger.error("Error.", ex);
        }
    }

    public boolean updateEnrollment(final Student student, final Enrollment enrollment) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""))) {


            preparedStatement.setLong(1, enrollment.getUnit().getId());
            preparedStatement.setDouble(2, enrollment.getGrade());
            preparedStatement.setLong(3, student.getId());
            preparedStatement.setLong(4, enrollment.getId());

            int rowAffected = preparedStatement.executeUpdate();
            return rowAffected == 1;
        } catch (SQLException ex) {
            logger.error("Error.", ex);
        }
        return false;
    }
}
