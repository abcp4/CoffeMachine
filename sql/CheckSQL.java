package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckSQL {

	public CheckSQL(){	}
	
	public Boolean checkTable(String tableName){
		Boolean exists = false;
		 Connection c = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/postgres",
	            "postgres", "admin");
	         Statement stmt = null;
	         stmt = c.createStatement();
	         ResultSet rs = stmt.executeQuery( "SELECT EXISTS ( SELECT 1"
	         		+ "FROM   information_schema.tables "
	         		+ " WHERE  table_schema = 'public'"
	         		+ "AND    table_name = '"+ tableName+"');") ;
	         
	         while ( rs.next() ) {
	        	  exists = rs.getBoolean("exists");
	        	  
	           }
	         rs.close();
	         stmt.close();
	         c.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      System.out.println("Checked Tables successfully");
	      return exists; 
	   }
	
}
