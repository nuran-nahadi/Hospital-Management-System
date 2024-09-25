1. Install MySQL workbench.

2. Install IntelliJ IDEA.

3a. Open Hospital-Management-System project(as a maven project) in Intellij IDEA

or,

3b. clone the github repository from 

https://github.com/nuran-nahadi/Hospital-Management-System.git

4. go to Project Structure and add the JAR file "mysql-connector-j-8.4.0.jar" situated in "38\Hospital-Management-System\lib" to the PROJECT -> MODULE

5. Open your MySQL workbench and make a local server (NB. Remember the username and password)

6. Import the schema (Database.sql) for our database. Now you should have the schema "hospitalmanagement" in your server.

7. Edit the file "Hospital-Management-System\src\main\java\com\example\hospitalmanagement\HospitalManagementDatabase.java"

such that ,    Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalmanagement","<username>","<password>");

replace the username and password with your own. Make sure your server is also using the port 3306. Otherwise change that in the code as well.

8. Now, Run the file "Hospital-Management-System\src\main\java\com\example\hospitalmanagement\HospitalManagementSystem.java"

9. You can view the username and password stored in the appropriate tables for every type of entity. Use it to access our program.