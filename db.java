import java.sql.*;

public class Main{
    public static void main(String[] args) {
        try {
           System.out.println("Enter the type of database (1, 2, 3, 4, or 5):");
            int dbType = Integer.parseInt(System.console().readLine());
            System.out.println("Enter the database URL:");
            String dbUrl = System.console().readLine();
            System.out.println("Enter the username:");
            String username = System.console().readLine();
            System.out.println("Enter the password:");
            String password = System.console().readLine();

            // Load the appropriate driver and connect to the database
            Connection conn = null;
            switch (dbType) {
                case 1:
                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                    conn = DriverManager.getConnection(dbUrl, username, password);
                    break;
                case 2:
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection(dbUrl, username, password);
                    break;
                // Add cases for other driver types as needed
            }
            

            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", null);
            while (tables.next()) {
                String tableName = tables.getString(3);
                System.out.println("Retrieving data from table: " + tableName);

                // Retrieve the data from the table
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName);
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(rs.getString(i) + " ");
                    }
                    System.out.println();
                }
            }

            // Close the connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}