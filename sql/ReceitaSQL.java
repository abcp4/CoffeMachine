package sql;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ReceitaSQL {

	public void createTable() {

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
	                      " NAME TEXT NOT NULL, "
	                      + "INGREDIENTE TEXT NOT NULL,"
	                      + "QUANTIDADE NUMERIC NOT NULL,"
	                      + "TIPO TEXT NOT NULL)";
	                  
	         stmt.executeUpdate(sql);
	         stmt.close();
	         c.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      //System.out.println("Created Receita Table successfully");
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
	         String sql = "DROP TABLE RECEITA";
	                  
	         stmt.executeUpdate(sql);
	         stmt.close();
	         c.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	      //System.out.println("dropped Receita Table successfully");
	   }
	     
	
	   public void insertIngredient(String coffeName,String ingredientName,Double quantidade,String tipo) {
	      
	          Connection c = null;
	          try {
	             Class.forName("org.postgresql.Driver");
	             c = DriverManager
	                .getConnection("jdbc:postgresql://localhost:5432/postgres",
	                "postgres", "admin");
	             c.setAutoCommit(false);
	             Statement stmt = null;
	             stmt = c.createStatement();
	             String sql = "INSERT INTO Receita (name,ingrediente,quantidade,tipo) VALUES( '"
	             		+coffeName+ "' , '"+ ingredientName+"' , "+quantidade+",'"+tipo+"');";
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
	   
	   
	   /** 
	    * Retorna lista com nomes do cafes e seus preços
	    */
	    public ArrayList getIngredients(String coffeName){
	    	Connection c = null;
	    	ArrayList<String> ingredientsList = new ArrayList();
	        try {
	           Class.forName("org.postgresql.Driver");
	           c = DriverManager
	              .getConnection("jdbc:postgresql://localhost:5432/postgres",
	              "postgres", "admin");
	           c.setAutoCommit(false);
	           Statement stmt = null;
	           stmt = c.createStatement();
	           ResultSet rs = stmt.executeQuery( "SELECT INGREDIENTE FROM Receita where "
	           		+ "Receita.name = '"+coffeName +"'");
	           while ( rs.next() ) {
	        	   String  name = rs.getString("Ingrediente");
	        	   ingredientsList.add(name);
	           }
	           rs.close();
	           stmt.close();
	           c.close();
	        } catch (Exception e) {
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	        }
	        return ingredientsList;
	    }
	    
	    public void removeIngredients(String coffeName){
	    	Connection c = null;
	        try {
	           Class.forName("org.postgresql.Driver");
	           c = DriverManager
	              .getConnection("jdbc:postgresql://localhost:5432/postgres",
	              "postgres", "admin");
	           //c.setAutoCommit(false);
	           Statement stmt = null;
	           stmt = c.createStatement();
	           String sql =  "DELETE FROM Receita where "
	           		+ "Receita.name = '"+coffeName +"'";
	           stmt.executeUpdate(sql);
	           stmt.close();
	           c.close();
	        } catch (Exception e) {
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	        }
	    }
	   
	    public ArrayList<String> getDescricao(String coffeName){
	    	Connection c = null; 
	    	ArrayList<String> descricaoList = new ArrayList();
	        try {
	           Class.forName("org.postgresql.Driver");
	           c = DriverManager
	              .getConnection("jdbc:postgresql://localhost:5432/postgres",
	              "postgres", "admin");
	           c.setAutoCommit(false);
	           Statement stmt = null;
	           stmt = c.createStatement();
	           ResultSet rs = stmt.executeQuery( "SELECT quantidade,tipo,ingrediente FROM Receita"
	           		+ " WHERE Receita.name = '"+coffeName +"'");
	           while ( rs.next() ) {
	        	   String  name = rs.getString("Ingrediente");
	        	   Double quantidade = rs.getDouble("Quantidade");
	        	   String tipo = rs.getString("Tipo");
	        	   descricaoList.add(quantidade+" "+tipo+" de "+name);
	           }
	           rs.close();
	           stmt.close();
	           c.close();
	        } catch (Exception e) {
	           e.printStackTrace();
	           System.err.println(e.getClass().getName()+": "+e.getMessage());
	           System.exit(0);
	        }
	        return descricaoList;
	    
	    }
	
}
