package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BalancoSQL {

	public BalancoSQL(){	}

	public void createTable() {
	      Connection c = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/postgres",
	            "postgres", "admin");
	         Statement stmt = null;
	         stmt = c.createStatement();
	         String sql = "CREATE TABLE BALANCO " +
	                      "(ID SERIAL PRIMARY KEY  NOT NULL," +
	                      " TOTAL    NUMERIC    NOT NULL,"+
	                      " NOTA10  INTEGER  NOT NULL,"+ 
	                      " NOTA5  INTEGER  NOT NULL,"+ 
	                      " NOTA2  INTEGER  NOT NULL,"+ 
	                      " MOEDA50  INTEGER  NOT NULL);" ;
	         
	         stmt.executeUpdate(sql);
	         stmt.close();
	         c.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      System.out.println("Created Balanco Table successfully");
	   }


	public void insertBalanco(Double total, int[] money) {
	      
        Connection c = null;
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager
              .getConnection("jdbc:postgresql://localhost:5432/postgres",
              "postgres", "admin");
           c.setAutoCommit(false);
           Statement stmt = null;
           stmt = c.createStatement();
           String sql = "INSERT INTO Balanco (total,nota10,"
           		+ "nota5,nota2,moeda50) "
                   + "VALUES ("+total+","+money[0]+","+money[1]+
                   ","+money[2]+","+money[3]+");";
           stmt.executeUpdate(sql);
           stmt.close();
           c.commit();
           c.close();
        } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           System.exit(0);
        }
    
    System.out.println("Inserted on database successfully");
 }
 
	public void updateBalanco(Double total, int[] money) {

        Connection c = null;
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager
              .getConnection("jdbc:postgresql://localhost:5432/postgres",
              "postgres", "admin");
           Statement stmt = null;
           c.setAutoCommit(false);
           stmt = c.createStatement();
           String sql = "UPDATE Balanco Set total = "+total+",nota10 = "
           		+ money[0]+",nota5 = "+money[1]+", nota2 = "+money[2]+
           		",moeda50 = "+money[3]+" WHERE id = 1 ; ";    
           stmt.executeUpdate(sql);
           stmt.close();
           c.commit();
           c.close();
        } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           System.exit(0);
        }
	}
	
	public Double getTotal(){
		Connection c = null;
		int[] balanco={0,0,0,0};
		 Double  total = 0.0;
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager
              .getConnection("jdbc:postgresql://localhost:5432/postgres",
              "postgres", "admin");
           c.setAutoCommit(false);
           Statement stmt = null;
           stmt = c.createStatement();
           ResultSet rs = stmt.executeQuery( "SELECT total FROM Balanco where "
           		+ "Balanco.id  = 1 ");
           while ( rs.next() ) {
        	 total = rs.getDouble("total");
        	  
           }
           rs.close();
           stmt.close();
           c.close();
        } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           System.exit(0);
        }
        return total;
    }
	
	public int[] getBalanco(){
		Connection c = null;
		int[] balanco={0,0,0,0};
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager
              .getConnection("jdbc:postgresql://localhost:5432/postgres",
              "postgres", "admin");
           c.setAutoCommit(false);
           Statement stmt = null;
           stmt = c.createStatement();
           ResultSet rs = stmt.executeQuery( "SELECT * FROM Balanco where "
           		+ "Balanco.id  = 1 ");
           while ( rs.next() ) {
        	   Integer  nota10 = rs.getInt("Nota10");
        	   Integer  nota5 = rs.getInt("Nota10");
        	   Integer  nota2 = rs.getInt("Nota10");
        	   Integer  moeda50 = rs.getInt("Nota10");
        	   balanco[0] = nota10;
        	   balanco[1] = nota5;
        	   balanco[2] = nota2;
        	   balanco[3] = moeda50;
           }
           rs.close();
           stmt.close();
           c.close();
        } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           System.exit(0);
        }
        return balanco;
    }
	

}
