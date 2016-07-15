package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ReceitaSQL {

	void createTable() {

	      Connection c = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/postgres",
	            "postgres", "admin");
	         Statement stmt = null;
	         stmt = c.createStatement();
	         String sql = "CREATE TABLE RECEITA " +
	                      "(ID SERIAL PRIMARY KEY  NOT NULL," +
	                      " NAME  TEXT    NOT NULL, " +
	                      " PRECO  NUMERIC     NOT NULL)" ;
	         stmt.executeUpdate(sql);
	         stmt.close();
	         c.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      System.out.println("Created Coffe Table successfully");
	   }
	     
	   void insertCoffe(String name, Double preco) {
	      
	          Connection c = null;
	          try {
	             Class.forName("org.postgresql.Driver");
	             c = DriverManager
	                .getConnection("jdbc:postgresql://localhost:5432/postgres",
	                "postgres", "admin");
	             c.setAutoCommit(false);
	             Statement stmt = null;
	             stmt = c.createStatement();
	             String sql = "INSERT INTO COFFE (NAME,PRECO) "
	                     + "VALUES ('"+ name+"',"+preco+");";
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
	   
	   /** 
	    * Retorna lista com nomes do cafes e seus preços
	    */
	    ArrayList getCoffes(){
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
	        	   coffeList.add(new CoffeObject(name, preco));
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
