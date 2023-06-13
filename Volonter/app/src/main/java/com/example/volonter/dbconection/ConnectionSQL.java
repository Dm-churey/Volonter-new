//package com.example.volonter.dbconection;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//
//public class ConnectionSQL {
//    private static final String url = "jdbc:mysql://a0820619.xsph.ru:3306/a0820619_volonter_bd";
//    private static final String username = "a0820619_volonter_bd";
//    private static final String password = "bd3";
//
//    static public Connection getConnect(){
//        try {
//            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
//            return DriverManager.getConnection(url, username, password);
//        } catch(Exception ex) {
//            System.out.println(ex.getMessage());
//            throw new RuntimeException(ex);
//        }
//    }
//
//}
