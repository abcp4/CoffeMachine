package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.ResultSet;
public class CoffeSQL {
	public CoffeSQL(){}
	
	public void createTable() {

      Connection c = null;
      try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/postgres",
            "postgres", "admin");
         Statement stmt = null;
         stmt = c.createStatement();
         String sql = "CREATE TABLE COFFE " +
                      "(ID SERIAL PRIMARY KEY  NOT NULL," +
                      " NAME  TEXT    NOT NULL, " +
                      " PRECO  NUMERIC  NOT NULL,"+ 
                      "TAXA NUMERIC NOT NULL );" ;
         stmt.executeUpdate(sql);
         stmt.close();
         c.close();
      } catch (Exception e) {
         e.printStackTrace();
         System.err.println(e.getClass().getName()+": "+e.getMessage());
         System.exit(0);
      }
      //System.out.println("Created Coffe Table successfully");
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
	         String sql = "DROP TABLE COFFE;" ;
	         stmt.executeUpdate(sql);
	         stmt.close();
	         c.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	     // System.out.println("Dropped Coffe Table successfully");
	   }
	     
	
	public void insertCoffe(String name, Double preco,Double taxa) {
      
          Connection c = null;
          try {
             Class.forName("org.postgresql.Driver");
             c = DriverManager
                .getConnection("jdbc:postgresql://localhost:5432/postgres",
                "postgres", "admin");
             c.setAutoCommit(false);
             Statement stmt = null;
             stmt = c.createStatement();
             String sql = "INSERT INTO COFFE (NAME,PRECO,TAXA) "
                     + "VALUES ('"+ name+"',"+preco+","+taxa+");";
             stmt.executeUpdate(sql);
             stmt.close();
             c.commit();
             c.close();
          } catch (Exception e) {
             e.printStackTrace();
             System.err.println(e.getClass().getName()+": "+e.getMessage());
             System.exit(0);
          }
      
     // System.out.println("Inserted on database successfully");
   }
   
	public void removeCoffe(String name){
		Connection c = null;
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager
              .getConnection("jdbc:postgresql://localhost:5432/postgres",
              "postgres", "admin");
           Statement stmt = null;
           stmt = c.createStatement();
           String sql = "DELETE FROM coffe WHERE coffe.name = '"+name+"'";
           stmt.executeUpdate(sql);
           stmt.close();
           c.close();
        } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           System.exit(0);
        }
        System.out.println("Deleted from coffe ");
	}

	public CoffeObject getCoffe(String coffeName){
		Connection c = null;
		CoffeObject cb = null;
		ArrayList<CoffeObject> coffeList = new ArrayList();
	    try {
	       Class.forName("org.postgresql.Driver");
	       c = DriverManager
	          .getConnection("jdbc:postgresql://localhost:5432/postgres",
	          "postgres", "admin");
	       c.setAutoCommit(false);
	       Statement stmt = null;
	       stmt = c.createStatement();
	       ResultSet rs = stmt.executeQuery( "SELECT * FROM COFFE WHERE "
	       		+ "name = '"+coffeName+"';" );
	       while ( rs.next() ) {
		       String  name = rs.getString("name");
	    	   Double preco = rs.getDouble("preco");
	    	   Double taxa = rs.getDouble("taxa");
	    	   cb  = new CoffeObject(name, preco,taxa);
	       }
           rs.close();
           stmt.close();
           c.close();
        } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           System.exit(0);
        }
        return cb;
    }
	
	
	/** 
    * Retorna lista com nomes do cafes e seus preços
    */
	public ArrayList getCoffes(){
    	Connection c = null;
    	ArrayList<CoffeObject> coffeList = new ArrayList();
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager
              .getConnection("jdbc:postgresql://localhost:5432/postgres",
              "postgres", "admin");
           c.setAutoCommit(false);
           Statement stmt = null;
           stmt = c.createStatement();
           ResultSet rs = stmt.executeQuery( "SELECT * FROM COFFE;" );
           while ( rs.next() ) {
        	   String  name = rs.getString("name");
        	   Double preco = rs.getDouble("preco");
        	   Double taxa = rs.getDouble("taxa"); 
        	   coffeList.add(new CoffeObject(name, preco,taxa));
           }
           rs.close();
           stmt.close();
           c.close();
        } catch (Exception e) {
           e.printStackTrace();
           System.err.println(e.getClass().getName()+": "+e.getMessage());
           System.exit(0);
        }
        return coffeList;
    }
   
}

