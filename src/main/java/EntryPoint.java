import DAO.DepartmentDAO;
import DAO.PersonDAO;
import Queries.SelectData;
import models.Department;
import models.Person;
import operations.CreateTables;
import operations.InsertData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import static operations.SQL.*;

public class EntryPoint {
    public static void main(String[] args) throws ParseException {
        SelectData selectData = new SelectData();
        CreateTables createTables = new CreateTables();
        InsertData insertData  = new InsertData();

//        createTables.createTable(CREATE_PERSON_TABLE);
//        createTables.createTable(CREATE_DEPARTMENT_TABLE);
//        createTables.createTable(ADD_FOREIGN_KEY);
//
//        insertData.insertData(INSERT_DEPARTMENT);
//        insertData.insertData(INSERT_PERSONS);

//        selectData.getDepartment();
//        selectData.getPerson();
//
//        departmentDAO.getById(1);
        //Department
        DepartmentDAO departmentDAO = new DepartmentDAO();
//        departmentDAO.create(new Department("Департамент hello world"));
//        departmentDAO.getAll();
//        departmentDAO.getLastId();
//        departmentDAO.update(new Department(5,"я поменял значение второй раз"));
//        departmentDAO.delete(5);
        //person
        PersonDAO personDAO = new PersonDAO();
//        personDAO.getAll();
//        personDAO.getById(3);
        long millis=System.currentTimeMillis();
        java.sql.Date date=new java.sql.Date(millis);
//        personDAO.create(new Person("Vladimir","Stavitskii",date,departmentDAO.getById(3)));
//        personDAO.update(new Person(7,"Nikita","Zheksenov",date,departmentDAO.getById(6)));
//        personDAO.delete(7);
        personDAO.fetchSize();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        personDAO.getAll();
    }
}
