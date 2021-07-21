package DAO;

import connection.ConnectionToDB;
import models.Person;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static connection.Constants.*;

public class PersonDAO implements DAO<Person> {
    private ConnectionToDB connectionToDB = new ConnectionToDB();
    private DepartmentDAO departmentDAO = new DepartmentDAO();

    @Override
    public Person getById(int id) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            Person person = new Person();
            ResultSet resultSet = statement.executeQuery("select * from person WHERE id =" + id);
            while (resultSet.next()) {
                person.setId(resultSet.getInt(1));
                person.setFirstName(resultSet.getString(2));
                person.setSecondName(resultSet.getString(3));
                person.setBirthday(resultSet.getDate(4));
                int departmentId = resultSet.getInt("department_id");
                person.setDepartment(departmentDAO.getById(departmentId));
                System.out.println(
                        "person id: '" + person.getId() + "'" +
                                "person firstName: '" + person.getFirstName() + "'" +
                                "person secondName: '" + person.getSecondName() + "'" +
                                "person birthday: '" + person.getBirthday() + "'" +
                                "person department: '" + person.getDepartment().getId() + "'"
                );
                return person;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public Person create(Person person) {
        Connection connection = null;
        try {
            connection = connectionToDB.openConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into person(first_name,second_name,birthday,department_id) values (?,?,?,?)");
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getSecondName());
            preparedStatement.setDate(3, (Date) person.getBirthday());
            preparedStatement.setInt(4, person.getDepartment().getId());
            preparedStatement.executeUpdate();
            System.out.println("Entity was created");
            return person;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public Person update(Person person) {
        Connection connection = null;
        try {
            connection = connectionToDB.openConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE person SET first_name = ? , second_name = ? , birthday = ? , department_id = ? where id = ?");
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getSecondName());
            preparedStatement.setDate(3, (Date) person.getBirthday());
            preparedStatement.setInt(4, person.getDepartment().getId());
            preparedStatement.setInt(5, person.getId());
            preparedStatement.executeUpdate();
            System.out.println("Entity was updated");
            return getById(person.getId());
        } catch (
                SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void delete(int id) {
        Connection connection = null;
        try {
            connection = connectionToDB.openConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from person where id=?");
            preparedStatement.setInt(1, id);
            System.out.println("Entity was deleted");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    public List<Person> getList() {
        List<Person> personList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL + DATABASE, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select first_name,second_name,birthday,department_id from person");
            while (resultSet.next()) {
                Person person = new Person();
                person.setFirstName(resultSet.getString(1));
                person.setSecondName(resultSet.getString(2));
                person.setBirthday(resultSet.getDate(3));
                int departmentId = resultSet.getInt("department_id");
                person.setDepartment(departmentDAO.getById(departmentId));
                personList.add(person);
            }
            return personList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null;
    }

    //batch
    public List<Person> batchSave(List<Person> personList) {
        Connection connection = null;
        try {
            connection = connectionToDB.openConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO person (first_name, second_name, birthday, department_id) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            for (Person person : personList) {
                preparedStatement.setString(1, person.getFirstName());
                preparedStatement.setString(2, person.getSecondName());
                preparedStatement.setDate(3, (Date) person.getBirthday());
                preparedStatement.setInt(4, person.getDepartment().getId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generateListId(personList, generatedKeys);
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        System.out.println("Batch save successfully finished");
        return personList;
    }

    private List<Person> generateListId(List<Person> personList, ResultSet gererateId) {
        List<Integer> listId = new ArrayList<>();
        try {
            while (gererateId.next()) {
                listId.add(gererateId.getInt(1));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        for (int i = 0; i < personList.size(); i++) {
            personList.get(i).setId(listId.get(i));
        }
        return personList;
    }

    public List<Person> batchUpdate(List<Person> personList) {
        Connection connection = null;
        try {
            connection = connectionToDB.openConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("UPDATE person SET first_name = ?, second_name = ?, birthday = ?, department_id = ? WHERE id = ?");
            for (Person person : personList) {
                preparedStatement.setString(1, person.getFirstName());
                preparedStatement.setString(2, person.getSecondName());
                preparedStatement.setDate(3, (Date) person.getBirthday());
                preparedStatement.setLong(4, person.getDepartment().getId());
                preparedStatement.setInt(5, person.getId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        System.out.println("Batch update successfully finished");
        return personList;
    }

    public void batchDelete(List<Person> personList) {
        Connection connection = null;
        try {
            connection = connectionToDB.openConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("delete from person where id = ?");
            for (Person person : personList) {
                preparedStatement.setInt(1, person.getId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException throwables) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throwables.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        System.out.println(personList);
        System.out.println("Batch delete successfully finished");
    }

    //without fetchSize
    @Override
    public void printAll() {
        Connection connection = null;
        try {
            connection = connectionToDB.openConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from person  INNER JOIN department ON person.department_id = department.id");

            Person person = new Person();
            System.out.println("before select all");
            LocalDateTime beforeSimpleSimpleSelect = LocalDateTime.now();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                person.setId(resultSet.getInt(1));
                person.setFirstName(resultSet.getString(2));
                person.setSecondName(resultSet.getString(3));
                person.setBirthday(resultSet.getDate(4));
                int departmentId = resultSet.getInt(5);
                person.setDepartment(departmentDAO.getById(departmentId));
                System.out.println(
                        "person id: '" + person.getId() + "'" +
                                "person firstName: '" + person.getFirstName() + "'" +
                                "person secondName: '" + person.getSecondName() + "'" +
                                "person birthday: '" + person.getBirthday() + "'" +
                                "person department: '" + person.getDepartment().getId() + "'"
                );
            }
            System.out.println("after select all");
            LocalDateTime afterSimpleSimpleSelect = LocalDateTime.now();
            long simpleSelectAllTime = ChronoUnit.MILLIS.between(beforeSimpleSimpleSelect, afterSimpleSimpleSelect);
            System.out.println("Select all time: " + simpleSelectAllTime);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    //FetchSize
    public void fetchSize() {
        Connection connection = null;
        try {
            connection = connectionToDB.openConnection().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from person  INNER JOIN department ON person.department_id = department.id");
            connection.setAutoCommit(false);
            preparedStatement.setFetchSize(1);
            Person person = new Person();
            System.out.println("before select all");
            LocalDateTime beforeSimpleSimpleSelect = LocalDateTime.now();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                person.setId(resultSet.getInt(1));
                person.setFirstName(resultSet.getString(2));
                person.setSecondName(resultSet.getString(3));
                person.setBirthday(resultSet.getDate(4));
                int departmentId = resultSet.getInt(5);
                person.setDepartment(departmentDAO.getById(departmentId));
                System.out.println(
                        "person id: '" + person.getId() + "'" +
                                "person firstName: '" + person.getFirstName() + "'" +
                                "person secondName: '" + person.getSecondName() + "'" +
                                "person birthday: '" + person.getBirthday() + "'" +
                                "person department: '" + person.getDepartment().getId() + "'"
                );
            }
            System.out.println("after select all");
            LocalDateTime afterSimpleSimpleSelect = LocalDateTime.now();
            long simpleSelectAllTime = ChronoUnit.MILLIS.between(beforeSimpleSimpleSelect, afterSimpleSimpleSelect);
            System.out.println("Select all time: " + simpleSelectAllTime);
        } catch (
                SQLException e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

}
