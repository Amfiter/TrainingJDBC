package operations;

public class SQL {
    public final static String CREATE_PERSON_TABLE =
            "CREATE TABLE person ("
                    + "ID SERIAL PRIMARY KEY,"
                    + "first_name VARCHAR(255) NOT NULL,"
                    + "second_name VARCHAR(255) NOT NULL,"
                    + "birthday DATE NOT NULL,"
                    + "department_id INTEGER NOT NULL)";
    public final static String CREATE_DEPARTMENT_TABLE =
            "CREATE TABLE department ("
                    + "ID SERIAL PRIMARY KEY,"
                    + "NAME VARCHAR(255) NOT NULL,"
                    + "UNIQUE(NAME))";

    public final static String ADD_FOREIGN_KEY = "ALTER TABLE person ADD CONSTRAINT department_fk FOREIGN KEY (department_id) REFERENCES department(id)";

    public final static String INSERT_PERSONS = "insert into person(first_name,second_name,birthday,department_id)\n" +
            "     values\n" +
            "    ('Сергей','Лукьяненко','1987-03-12',1),\n" +
            "     ('Ник','Перумов ','1994-12-09',2),\n" +
            "     ('Виктор','Пелевин','1996-08-21',3),\n" +
            "     ('Владимир','Васильев','1977-07-30',4),\n" +
            "     ('Татьяна','Устинова ','1969-01-02',5),\n" +
            "     ('Фредрик','Бакман ','1998-06-01',6)";
    public final static String INSERT_DEPARTMENT = "insert into department(name,id)\n" +
            " values\n" +
            " ('Департамент здравоохранения',1),\n" +
            " ('Департамент рыбнадзора',2),\n" +
            " ('Департамент птиц надзора',3),\n" +
            " ('Какой то непонятной департамент',4),\n" +
            " ('Департамент чего-то',5),\n" +
            " ('Департамент образования',6)";

}
