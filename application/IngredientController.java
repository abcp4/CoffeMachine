package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class IngredientController implements Initializable{
	
	@FXML
	private TextField ingredientQuantidadeTF,ingredientTF;
	@FXML
	private Button ingredientPlus,ingredientMinus,ingredientConfirm,cancelButton;
	@FXML
	private ListView ingredientList;
	@FXML
	private ChoiceBox ingredienteTipo;
	
	public Boolean confirmOperation = false;
	public ArrayList ingredienteNamesList = new ArrayList();
	public ArrayList ingredienteQuantidadeList = new ArrayList();
	public ArrayList ingredienteTipoList = new ArrayList();
	
	private int[] twoConditions = {0,0};
	public ArrayList<String> ingredientItems = new ArrayList();
	public ObservableList<String> names = FXCollections.observableArrayList(ingredientItems);
	 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		ingredienteTipo.setItems(FXCollections.observableArrayList("gramas", "ml"));
		ingredienteTipo.getSelectionModel().select(0);//coloque a 1 opcao
		ingredientPlus.setDisable(true);
		
		 
		 ingredientList.setItems(names);
		
		ingredientPlus.setOnAction(e -> ingredientPlusHandler());
				
		ingredientQuantidadeTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	if(ingredientQuantidadeTF.getText().isEmpty()){
            		ingredientPlus.setDisable(true);
            		twoConditions[0] = 0;
        		}else{
        			twoConditions[0] = 1;
        			
        		}
            	if(twoConditions[0] == twoConditions[1]){
            		ingredientPlus.setDisable(false);
            		
            	}
            }
        });
		
		ingredientTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	if(ingredientTF.getText().isEmpty()){
            		ingredientPlus.setDisable(true);
            		twoConditions[1] = 0;
        		}else{
        			twoConditions[1] = 1;
        			
        		}
            	if(twoConditions[0] == twoConditions[1]){
            		ingredientPlus.setDisable(false);
            		
            	}
            }
        });
		
		ingredientConfirm.setOnAction(e -> ingredientConfirmHandler());
		cancelButton.setOnAction(e -> closeHandle());
	}
	public void closeHandle(){
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	public void ingredientPlusHandler() {
		
	    String text1 = ingredientTF.getText();
	    System.out.println("ingredient plus");
	    Double quantidade = Double.parseDouble(ingredientQuantidadeTF.getText());
	    ingredienteNamesList.add(text1);
	    ingredienteQuantidadeList.add(quantidade);
	    ingredienteTipoList.add((String)ingredienteTipo.getValue());
	    
	    names.add(quantidade+" "+(String)ingredienteTipo.getValue()+" de "+text1);
	}

	public void ingredientConfirmHandler(){
		confirmOperation = true;
		Stage stage = (Stage) ingredientConfirm.getScene().getWindow();
		stage.close();
	}
	public ArrayList getNames(){
		return this.ingredienteNamesList;
	}
	public ArrayList getQuantidades(){
		return this.ingredienteQuantidadeList;
	}
	public ArrayList getTipos(){
		return this.ingredienteTipoList;
	}
	public ObservableList getIngredientsList(){
		return this.names;
	}
}
