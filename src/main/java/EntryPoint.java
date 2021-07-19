import DAO.DepartmentDAO;
import Queries.SelectData;
import operations.CreateTables;
import operations.InsertData;

import static operations.SQL.*;

public class EntryPoint {
    public static void main(String[] args) {
        SelectData selectData = new SelectData();
       /* CreateTables createTables = new CreateTables();
        InsertData insertData  = new InsertData();

        createTables.createTable(CREATE_PERSON_TABLE);
        createTables.createTable(CREATE_DEPARTMENT_TABLE);
        createTables.createTable(ADD_FOREIGN_KEY);

        insertData.insertData(INSERT_PERSONS);
        insertData.insertData(INSERT_DEPARTMENT);*/

/*       selectData.getDepartment();
        selectData.getPerson();*/

        DepartmentDAO departmentDAO = new DepartmentDAO();
        departmentDAO.getById(1);
    }
}
