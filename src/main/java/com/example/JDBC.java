package com.example;


import java.sql.*;

public class JDBC {
    static Connection  connection;
    static Statement  statement;
    static ResultSet  resultSet;
    static String url = "jdbc:mysql://localhost:3306/project";
    static String username = "root";
    static String password = "1234";


    public static void main(String args[]){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,username,password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from security where gender = 'M'");
            while(resultSet.next()){
                System.out.println (resultSet.getString(1)+"  "+resultSet.getString(2)+"  "+
                        resultSet.getString(3)+"  "+resultSet.getString(4));

            }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

    public static Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","1234");
        return connection;
    }

    public  static void closeConnection(Connection connection){
        try{
            if(connection != null)
                connection.close();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public  static void closeStatement(Statement statement){
        try{
            if(statement != null)
                statement.close();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public  static void closeResultset(ResultSet resultSet){
        try{
            if(resultSet != null)
                resultSet.close();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
