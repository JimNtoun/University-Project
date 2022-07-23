package edu.acme.repository;

import edu.acme.DatabaseConnectionPooling;
import edu.acme.model.Department;
import edu.acme.model.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DepartmentRepository implements CrudRepository<Department,Long>{
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnectionPooling.class);

    @Override
    public List<Department> findAll() {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""))){

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Department> departments = new ArrayList<>();
            while (resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getLong("id"));
                department.setName(resultSet.getString("name"));
                departments.add(department);
            }

        } catch (SQLException ex) {
            logger.error("Error.", ex);
        }
        return null;
    }

    @Override
    public Optional<Department> findById(Long id) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""))){

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                Department department = new Department();
                department.setId(resultSet.getLong("id"));
                department.setName(resultSet.getString("name"));
            }else{
                return Optional.empty();
            }

        } catch (SQLException ex) {
            logger.error("Error.", ex);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Department department) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""))){


            preparedStatement.setLong(1,department.getId());
            int row = preparedStatement.executeUpdate();
            return row == 1;
        } catch (SQLException ex) {
            logger.error("Error.", ex);
        }
        return false;
    }

    @Override
    public boolean update(Department department) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""))){

            preparedStatement.setString(1, department.getName());
            preparedStatement.setLong(4, department.getId());

            int row = preparedStatement.executeUpdate();
            return row == 1;

        } catch (SQLException ex) {
            logger.error("Error.", ex);
        }
        return false;
    }

    @Override
    public boolean create(Department department) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""), new String[]{"id"})){

            preparedStatement.setString(1, department.getName());
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            logger.error("Error.", ex);
        }
        return false;
    }

    public boolean updateUnit(Department department, Unit unit) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""))){

            preparedStatement.setString(1, unit.getName());
            preparedStatement.setLong(3, department.getId());
            preparedStatement.setLong(4, unit.getId());
            int row = preparedStatement.executeUpdate();
            return row == 1;

        } catch (SQLException ex) {
            logger.error("Error.", ex);
        }
        return false;
    }

    public boolean deleteUnit(Unit unit) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""))){

            preparedStatement.setLong(1,unit.getId());

            int row = preparedStatement.executeUpdate();
            return row == 1;
        } catch (SQLException ex) {
            logger.error("Error.", ex);
        }
        return false;
    }

    public void createUnit(Department department, Unit unit) {
        try (Connection connection = ConnectionRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     ConnectionRepository.get(""), new String[]{"id"})) {


            preparedStatement.setString(1, unit.getName());
            preparedStatement.setLong(2, department.getId());
            preparedStatement.executeUpdate();



        }  catch (SQLException ex) {
            logger.error("Error.", ex);
        }
    }

}
