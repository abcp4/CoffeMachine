package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sql.CoffeObject;
import sql.CoffeSQL;
import sql.ReceitaSQL;

public class CoffeeViewController implements Initializable {

	@FXML
    private ListView listCoffeeView; 
	@FXML
	private Button coffeViewDetalhe,coffeViewRemove,coffeViewSair;
	
	private ArrayList removeList = new ArrayList();
	
	ObservableList<String> names;
	private String selectedItem  = null;
	private int selectedIndex = 0;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//ArrayList<String> ar = new ArrayList();
		//ar.add(new String("aaa"));
		//ObservableList<String> names = FXCollections.observableArrayList(ar);
		
		CoffeSQL coffeSQL = new CoffeSQL();
		ArrayList<CoffeObject> coffes = coffeSQL.getCoffes();
		ArrayList<String> coffeNames = new ArrayList();
		for (CoffeObject temp : coffes) {
			coffeNames.add(temp.name);
		}
		 names = FXCollections.observableArrayList(coffeNames);
		listCoffeeView.setItems(names);
		
		listCoffeeView.getSelectionModel().selectedItemProperty().addListener(
	            new ChangeListener<String>() {
	                public void changed(ObservableValue<? extends String> ov, 
	                    String old_val, String new_val) {
	                	System.out.println(new_val);
	                	selectedItem = new_val;
	                	selectedIndex = listCoffeeView.getSelectionModel().getSelectedIndex();
	                	
	                }
	        });
		
		
		coffeViewRemove.setOnAction(e->handleRemove());
		coffeViewSair.setOnAction(e->handleSair());
	}
	
public void handleRemove(){
	if(selectedItem!=null){
		CoffeSQL coffeSQL = new CoffeSQL();
		ReceitaSQL rec = new ReceitaSQL();
		System.out.println(selectedItem);
		coffeSQL.removeCoffe(selectedItem);
		rec.removeIngredients(selectedItem);
		names.remove(selectedIndex);
	}
}
public void handleSair(){
	Stage stage = (Stage) coffeViewSair.getScene().getWindow();
	stage.close();
}
}
