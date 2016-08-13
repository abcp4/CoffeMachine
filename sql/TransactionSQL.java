package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class TransactionSQL {
/// armazena tipo , armazena valor
	public TransactionSQL(){}
	
	public void createTable() {
	      Connection c = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/postgres",
	            "postgres", "admin");
	         Statement stmt = null;
	         stmt = c.createStatement();
	         String sql = "CREATE TABLE TRANSACTION " +
	                      "(ID SERIAL PRIMARY KEY  NOT NULL," +
	                      " NAME  TEXT    NOT NULL, " +
	                      " TIPO    TEXT    NOT NULL,"+
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
	     // System.out.println("Created Transaction Table successfully");
	   }

	public void dropTable() {
	      Connection c = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/postgres",
	            "postgres", "admin");
	         Statement stmt = null;
	         stmt = c.createStatement();
	         String sql = "DROP TABLE TRANSACTION ;" ;
	         
	         stmt.executeUpdate(sql);
	         stmt.close();
	         c.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      //System.out.println("dropped Transaction Table successfully");
	   }
	
	
	public void insertTransaction(String name,String tipo, int[] money) {
	      
        Connection c = null;
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager
              .getConnection("jdbc:postgresql://localhost:5432/postgres",
              "postgres", "admin");
           c.setAutoCommit(false);
           Statement stmt = null;
           stmt = c.createStatement();
           String sql = "INSERT INTO Transaction (name,tipo,nota10,"
           		+ "nota5,nota2,moeda50) "
                   + "VALUES ('"+ name+"',"+"'"+tipo+"',"+money[0]+","+money[1]+
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
    
    //System.out.println("Inserted on database successfully");
 }
	
	public Double getBalanco(){
		Double balanco = 0.0;
		Connection c = null;
		CoffeObject cb = null;
	    try {
	       Class.forName("org.postgresql.Driver");
	       c = DriverManager
	          .getConnection("jdbc:postgresql://localhost:5432/postgres",
	          "postgres", "admin");
	       c.setAutoCommit(false);
	       Statement stmt = null;
	       stmt = c.createStatement();
	       ResultSet rs = stmt.executeQuery( "SELECT tipo,nota10,nota5,nota2,moeda50 FROM transaction;" );
	       while ( rs.next() ) {
	    	   int[] b = {0,0,0,0};
		       b[0] = rs.getInt("nota10");
		       b[1]  = rs.getInt("nota5");
		       b[2]  = rs.getInt("nota2");
		       b[3]  = rs.getInt("moeda50");
	    	   Double total = b[0]*10+b[1]*5+b[2]*2+b[3]*0.5;
	    	   
	    	   if(rs.getString("tipo").equals("deposito")){
	    		   balanco+=total;
		    	   
	    	   }else{
	    		   balanco-=total;
	    	   }
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
