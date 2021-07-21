
import DAO.DepartmentDAO;
import DAO.PersonDAO;
import Queries.*;
import models.Department;
import models.Person;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static Queries.SQL.*;

public class EntryPoint {
    public static void main(String[] args) throws ParseException {

        SelectData selectData = new SelectData();
        CreateTables createTables = new CreateTables();
        InsertData insertData = new InsertData();
        DropTables dropTables = new DropTables();

//        drop
//        dropTables.dropTable(DROP_DEPARTMENT_TABLE);
//        dropTables.dropTable(DROP_PERSON_TABLE);
//
////        create tables and insert data
//        createTables.createTable(CREATE_PERSON_TABLE);
//        createTables.createTable(CREATE_DEPARTMENT_TABLE);
//        createTables.createTable(ADD_FOREIGN_KEY);
//
//        insertData.insertData(INSERT_DEPARTMENT);
//        insertData.insertData(INSERT_PERSONS);

//        selectData.getDepartment();
//        selectData.getPerson();
//
////        Department
        DepartmentDAO departmentDAO = new DepartmentDAO();
//        departmentDAO.getAll();
//        departmentDAO.getById(1);
//        departmentDAO.create(new Department("Департамент hello world"));
//        departmentDAO.getLastId();
//        departmentDAO.update(new Department(5, "я поменял значение второй раз"));
//        departmentDAO.delete(5);
//
//        person
        PersonDAO personDAO = new PersonDAO();
//        personDAO.getAll();
//        personDAO.getById(3);
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
//        personDAO.create(new Person("Vladimir", "Stavitskii", date, departmentDAO.getById(3)));
//        personDAO.update(new Person(7, "Nikita", "Zheksenov", date, departmentDAO.getById(6)));
//        personDAO.delete(7);
//
////        fetch size
//        personDAO.fetchSize();
//        System.out.println();
//        System.out.println();
//        personDAO.getAll();//without fetch size
//
////        batch
//        personDAO.getList();
//        System.out.println(personDAO.batchSave(personDAO.getList()));

//        List<Person> personListForUpdate = new ArrayList<>();
//        personListForUpdate.add(new Person(11, "Nikita", "Zheksenov", date, departmentDAO.getById(1)));
//        personListForUpdate.add(new Person(8, "Lavanda", "Keksenova", date, departmentDAO.getById(2)));
//
//        personDAO.batchUpdate(personListForUpdate);
//        List<Person> newList = personDAO.batchSave(personDAO.getList());
//        personDAO.batchDelete(newList);
//


    }
}
