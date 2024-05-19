package com.example.hospitalmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HospitalManagementDatabase {
      public static  Connection connectDB(){
            try{
                  Connection connect =DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalmanagement","root","ROOT#passw0rd");
                  return connect;
            }
            catch (Exception e){
                  e.printStackTrace();
            }
            return  null;
      }

}
