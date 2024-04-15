package org.example;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.List;

public class App 
{
    private static String connectionString = "jdbc:postgresql://localhost:5432/DB_TEST";
    private static String username = "postgres";
    private static String password = "qwerty123";

    public static void main(String[] args) {
        printAllStudents();
        //insertStudent();
        //updateStudent();
        //deleteStudent();
        //insertStudentUsingPreparedStatement("Student S", 201);
        //updateStudentUsingPreparedStatement(5, "Student W", 201);
        //deleteStudentByIdUsingPreparedStatement(6);

//        List<Student> studentsToAdd = Arrays.asList(
//            new Student("Student 1", 403),
//            new Student("Student 2", 203),
//            new Student("Student 3", 201)
//        );
//        insertManyStudents(studentsToAdd);

        //printAllStudents();

        //printStudentsByGroupUsingPreparedStatement(201);
        //createTable();
        //saveFile();
        //downloadFile();
        //printMetadata();
    }

    private static void printAllStudents() {
        System.out.println("------ All Students ------");
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);

            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT * FROM Students");

            while (res.next()) {
                System.out.printf("ID: %d, Name: %s, Group: %d\n",
                        res.getInt("Id"),
                        res.getString("Name"),
                        res.getInt("GroupNumber")
                );
            }

            connection.close();
        }
        catch (SQLException ex) {
            System.out.println("SQL Error");
            ex.printStackTrace();
        }
    }

    private static void insertStudent() {
        System.out.println("------ Insert Student ------");
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);

            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO Students(Name, GroupNumber) VALUES('Student X', 101)");

            connection.close();
        }
        catch (SQLException ex) {
            System.out.println("SQL Error");
            ex.printStackTrace();
        }
    }

    private static void updateStudent() {
        System.out.println("------ Update Student ------");
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);

            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE Students SET GroupNumber = 508 WHERE Id = 3");

            connection.close();
        }
        catch (SQLException ex) {
            System.out.println("SQL Error");
            ex.printStackTrace();
        }
    }

    private static void deleteStudent() {
        System.out.println("------ Delete Student ------");
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);

            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Students WHERE Id = 3");

            connection.close();
        }
        catch (SQLException ex) {
            System.out.println("SQL Error");
            ex.printStackTrace();
        }
    }

    private static void insertStudentUsingPreparedStatement(String studentName, int studentGroupNumber) {
        System.out.println("------ Insert Student ------");
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);

            PreparedStatement statement = connection.prepareStatement("INSERT INTO Students(Name, GroupNumber) VALUES(?, ?)");
            statement.setString(1, studentName);
            statement.setInt(2, studentGroupNumber);

            statement.executeUpdate();

            connection.close();
        }
        catch (SQLException ex) {
            System.out.println("SQL Error");
            ex.printStackTrace();
        }
    }

    private static void updateStudentUsingPreparedStatement(int studentId, String studentName, int studentGroup) {
        System.out.println("------ Update Student ------");
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);

            PreparedStatement statement = connection.prepareStatement("UPDATE Students SET Name = ?, GroupNumber = ? WHERE Id = ?");
            statement.setString(1, studentName);
            statement.setInt(2, studentGroup);
            statement.setInt(3, studentId);

            statement.executeUpdate();

            connection.close();
        }
        catch (SQLException ex) {
            System.out.println("SQL Error");
            ex.printStackTrace();
        }
    }

    private static void deleteStudentByIdUsingPreparedStatement(int studentId) {
        System.out.println("------ Delete Student ------");
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);

            PreparedStatement statement = connection.prepareStatement("DELETE FROM Students WHERE Id = ?");
            statement.setInt(1, studentId);

            statement.executeUpdate();

            connection.close();
        }
        catch (SQLException ex) {
            System.out.println("SQL Error");
            ex.printStackTrace();
        }
    }

    private static void printStudentsByGroupUsingPreparedStatement(int groupNumber) {
        System.out.println("------ Students by Group ------");
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Students WHERE GroupNumber = ?");
            statement.setInt(1, groupNumber);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                System.out.printf("ID: %d, Name: %s, Group: %d\n",
                        res.getInt("Id"),
                        res.getString("Name"),
                        res.getInt("GroupNumber")
                );
            }

            connection.close();
        }
        catch (SQLException ex) {
            System.out.println("SQL Error");
            ex.printStackTrace();
        }
    }

    private static void insertManyStudents(List<Student> students) {
        System.out.println("------ Insert many Students ------");
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);

            StringBuilder sqlSB = new StringBuilder("INSERT INTO Students(Name, GroupNumber) VALUES");
            for (Student student : students) {
                sqlSB.append("(?, ?)");

                if (students.indexOf(student) != students.size() - 1) {
                    sqlSB.append(",");
                }
            }

            PreparedStatement statement = connection.prepareStatement(sqlSB.toString());

            for (Student student : students) {
                int studentIndex = students.indexOf(student);
                statement.setString(1 + 2 * studentIndex, student.getName());
                statement.setInt(2 + 2 * studentIndex, student.getGroupNumber());
            }

            statement.executeUpdate();

            connection.close();
        }
        catch (SQLException ex) {
            System.out.println("SQL Error");
            ex.printStackTrace();
        }
    }

    private static void createTable() {
        System.out.println("------ Create Table ------");
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);

            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE JustTable(Id INT PRIMARY KEY, Value INT NOT NULL)");

            connection.close();
        }
        catch (SQLException ex) {
            System.out.println("SQL Error");
            ex.printStackTrace();
        }
    }

    private static void saveFile() {
        System.out.println("------ Save File ------");
        try {
            File file = new File("C:/space_photo.jpg");
            FileReader fr = new FileReader(file);

            Connection connection = DriverManager.getConnection(connectionString, username, password);

            PreparedStatement statement = connection.prepareStatement("INSERT INTO Images(data) VALUES(?)");
//            Blob blob = connection.createBlob();
//            OutputStream os = blob.setBinaryStream(1);
//            Files.copy(Paths.get("C:/space_photo.jpg"), os);
//            statement.executeUpdate();

//            Інший спосіб заповнення файлу
//            byte[] content = Files.readAllBytes(Paths.get("C:/space_photo.jpg"));
//            blob.setBytes(1, content);
//            statement.setBlob(1, blob);

            // Збереження файлу не через Blob
            byte[] content = Files.readAllBytes(Paths.get("C:/space_photo.jpg"));
            statement.setBytes(1, content);
            statement.executeUpdate();

            connection.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void downloadFile() {
        System.out.println("------ Download File ------");
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);

            Statement statement = connection.createStatement();
            ResultSet resSet = statement.executeQuery("SELECT * FROM Images WHERE image_id = 1");
            resSet.next();
            byte[] data = resSet.getBytes("data");
            FileOutputStream fos = new FileOutputStream("C:/Users/inter/downloaded.jpg");
            fos.write(data);

            connection.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void printMetadata() {
        System.out.println("------ Print Metadata ------");
        try {
            Connection connection = DriverManager.getConnection(connectionString, username, password);

            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getDriverName());
            System.out.println(metaData.getDriverVersion());
            System.out.println(metaData.getDatabaseProductName());
            System.out.println(metaData.getURL());

            ResultSet tables = metaData.getTables(null, "public", null, null);
            while (tables.next()) {
                System.out.println(tables.getString("TABLE_NAME"));
            }

            connection.close();
        }
        catch (SQLException ex) {
            System.out.println("SQL Error");
            ex.printStackTrace();
        }
    }

}

class Student {
    private int id;
    private String name;
    private int groupNumber;

    Student(int id, String name, int groupNumber) {
        this.id = id;
        this.name = name;
        this.groupNumber = groupNumber;
    }

    Student(String name, int groupNumber) {
        this.name = name;
        this.groupNumber = groupNumber;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getGroupNumber() {
        return groupNumber;
    }
}
