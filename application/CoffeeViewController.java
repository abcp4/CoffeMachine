package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import sql.CoffeObject;
import sql.CoffeSQL;

public class CoffeeViewController implements Initializable {

	@FXML
    private ListView listCoffeeView; 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//ArrayList<String> ar = new ArrayList();
		//ar.add(new String("aaa"));
		//ObservableList<String> names = FXCollections.observableArrayList(ar);
		ArrayList<String> coffeNames = new ArrayList();
		CoffeSQL coffeSQL = new CoffeSQL();
		ArrayList<CoffeObject> coffes = coffeSQL.getCoffes();
		for (CoffeObject temp : coffes) {
			coffeNames.add(temp.name);
		}
		ObservableList<String> names = FXCollections.observableArrayList(coffeNames);
		listCoffeeView.setItems(names);
	}
	

}
