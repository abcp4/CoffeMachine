package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class RecipienteSQL {

	public void createTable() {

	      Connection c = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/postgres",
	            "postgres", "admin");
	         Statement stmt = null;
	         stmt = c.createStatement();
	         String sql = "CREATE TABLE RECIPIENTE " +
	                      "(ID SERIAL PRIMARY KEY  NOT NULL," +
	                      " NAME TEXT NOT NULL, QUANTIDADE NUMERIC NOT NULL)";
	                  
	         stmt.executeUpdate(sql);
	         stmt.close();
	         c.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      System.out.println("Created Recipiente Table successfully");
	   }
	     
	  public void insertIngredient(String recipienteName,Double quantidade) {
	      
	          Connection c = null;
	          try {
	             Class.forName("org.postgresql.Driver");
	             c = DriverManager
	                .getConnection("jdbc:postgresql://localhost:5432/postgres",
	                "postgres", "admin");
	             c.setAutoCommit(false);
	             Statement stmt = null;
	             stmt = c.createStatement();
	             String sql = "INSERT INTO Recipiente (name,quantidade) VALUES( '"
	             		+recipienteName+ "' , "+ quantidade+" );";
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
	   
	  public void updateQuantidade(int recipienteID,Double quantidade){
		  Connection c = null;
	        try {
	           Class.forName("org.postgresql.Driver");
	           c = DriverManager
	              .getConnection("jdbc:postgresql://localhost:5432/postgres",
	              "postgres", "admin");
	           
	          Statement stmt = null;
	          stmt = c.createStatement();
	          stmt.executeUpdate( "UPDATE Recipiente SET quantidade = "
	           		+quantidade+" WHERE Recipiente.id = "+recipienteID);
	           stmt.close();
	           c.close();
	        } catch (Exception e) {
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	        }
	    }
	 
	  public void updateQuantidade(String name,Double quantidade){
		  Connection c = null;
	        try {
	           Class.forName("org.postgresql.Driver");
	           c = DriverManager
	              .getConnection("jdbc:postgresql://localhost:5432/postgres",
	              "postgres", "admin");
	           
	          Statement stmt = null;
	          stmt = c.createStatement();
	          stmt.executeUpdate( "UPDATE Recipiente SET quantidade = "
	           		+quantidade+" WHERE Recipiente.name = '"+name+"'");
	           stmt.close();
	           c.close();
	        } catch (Exception e) {
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	        }
	    }
	 
	  public void removeRecipiente(int recipienteID){
		  
		  Connection c = null;
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager
	            .getConnection("jdbc:postgresql://localhost:5432/postgres",
	            "postgres", "admin");
	         Statement stmt = null;
	         stmt = c.createStatement();
	         String sql = "DELETE FROM  RECIPIENTE " +
	                      "WHERE id = "+recipienteID+";";
	                  
	         stmt.executeUpdate(sql);
	         stmt.close();
	         c.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      System.out.println("Removed Recipiente Row successfully");
	   
	  }
	  
	  public String getName(int id){
		    Connection c = null;
	    	String  name = null;
	        try {
	           Class.forName("org.postgresql.Driver");
	           c = DriverManager
	              .getConnection("jdbc:postgresql://localhost:5432/postgres",
	              "postgres", "admin");
	           c.setAutoCommit(false);
	           Statement stmt = null;
	           stmt = c.createStatement();
	           ResultSet rs = stmt.executeQuery( "SELECT name FROM Recipiente where "
	           		+ "Recipiente.id = "+id);
	           while ( rs.next() ) {
	        	   name = rs.getString("Name");
	           }
	           
	           rs.close();
	           stmt.close();
	           c.close();
	        } catch (Exception e) {
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	        }
	        return name;
	    }
	  
	  
	  public  Double getQuantidade(int recipienteID){
	    	Connection c = null;
	    	Double  quantidade = 0.0;
	        try {
	           Class.forName("org.postgresql.Driver");
	           c = DriverManager
	              .getConnection("jdbc:postgresql://localhost:5432/postgres",
	              "postgres", "admin");
	           c.setAutoCommit(false);
	           Statement stmt = null;
	           stmt = c.createStatement();
	           ResultSet rs = stmt.executeQuery( "SELECT QUANTIDADE FROM Recipiente where "
	           		+ "Recipiente.id = "+recipienteID );
	           while ( rs.next() ) {
	        	   quantidade = rs.getDouble("Quantidade");
	           }
	           
	           rs.close();
	           stmt.close();
	           c.close();
	        } catch (Exception e) {
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	        }
	        return quantidade;
	    }
	   
	  public ArrayList getRecipientesIds(){
	    	Connection c = null;
	    	ArrayList<Integer> recipienteList = new ArrayList();
	        try {
	           Class.forName("org.postgresql.Driver");
	           c = DriverManager
	              .getConnection("jdbc:postgresql://localhost:5432/postgres",
	              "postgres", "admin");
	           c.setAutoCommit(false);
	           Statement stmt = null;
	           stmt = c.createStatement();
	           ResultSet rs = stmt.executeQuery( "SELECT id FROM RECIPIENTE ORDER BY id;" );
	           while ( rs.next() ) {
	        	   int  id = rs.getInt("id");
	        	   recipienteList.add(id);
	           }
	           rs.close();
	           stmt.close();
	           c.close();
	        } catch (Exception e) {
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	        }
	        return recipienteList;
	    }

	  public ArrayList<RecipientObject>  getRecipientesObjects(){
		  Connection c = null;
	    	ArrayList<RecipientObject> recipienteList = new ArrayList();
	        try {
	           Class.forName("org.postgresql.Driver");
	           c = DriverManager
	              .getConnection("jdbc:postgresql://localhost:5432/postgres",
	              "postgres", "admin");
	           c.setAutoCommit(false);
	           Statement stmt = null;
	           stmt = c.createStatement();
	           ResultSet rs = stmt.executeQuery( "SELECT name,quantidade FROM RECIPIENTE ORDER BY id;" );
	           while ( rs.next() ) {
	        	   String  name = rs.getString("name");
	        	   Double  quantidade = rs.getDouble("quantidade");
	        	   recipienteList.add(new RecipientObject(name,quantidade));
	           }
	           rs.close();
	           stmt.close();
	           c.close();
	        } catch (Exception e) {
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	        }
	        return recipienteList;
	    }

	  
	  
}
