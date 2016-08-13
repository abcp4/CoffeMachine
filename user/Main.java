package user;
	
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sql.BalancoSQL;
import sql.CheckSQL;
import sql.CoffeSQL;
import sql.ReceitaSQL;
import sql.RecipienteSQL;
import sql.TransactionSQL;

public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	initialization();
        Application.launch(Main.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
        	
            Pane page = (Pane) FXMLLoader.load(Main.class.getResource("MainView.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            //primaryStage.setTitle("FXML is Simple");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void initialization(){
    	CheckSQL cs = new CheckSQL();
    	if(!cs.checkTable("coffe")){
	    	CoffeSQL cof= new CoffeSQL();
			cof.createTable();
			cof.insertCoffe("cafe com leite", 2.5,4.50);
			cof.insertCoffe("cafe expresso", 1.5,2.50);
    	}
		
    	if(!cs.checkTable("receita")){
			ReceitaSQL rec = new ReceitaSQL();
			rec.createTable();
			rec.insertIngredient("cafe com leite", "agua",2.3,"ml");
			rec.insertIngredient("cafe com leite", "cafe",3.5,"grama");
			rec.insertIngredient("cafe com leite", "leite",2.5,"grama");
			rec.insertIngredient("cafe expresso", "agua",2.3,"ml");
			rec.insertIngredient("cafe expresso", "cafe",5.5,"grama");
    	}
		
    	if(!cs.checkTable("recipiente")){
			RecipienteSQL rep = new RecipienteSQL();
			rep.createTable();
			rep.insertIngredient("agua", 50.0);
			rep.insertIngredient("cafe", 12.0);
			rep.updateQuantidade(1, 40.0);
    	}
    	
    	if(!cs.checkTable("transaction")){
			TransactionSQL tr = new TransactionSQL();
			tr.createTable();
			int[] a = {9,9,9,9};
			tr.insertTransaction("Admin","deposito", a);
			//int[] a = {9,9,9,9};
			if(!cs.checkTable("balanco")){
				BalancoSQL bal = new BalancoSQL();
				Double total = a[0]*10+a[1]*5+a[2]*2+a[3]*0.5;
				bal.createTable();
				bal.insertBalanco(total, a);
			}
    	}
    }
}