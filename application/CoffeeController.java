package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sql.CoffeSQL;
import sql.ReceitaSQL;

public class CoffeeController implements Initializable {

    @FXML 
    private Button criaReceita,desfazReceita,addCoffeSalvar,cancelCoffe;
    @FXML
    private MenuItem addIngredient, editIngredient;
    
    @FXML
    private TextField addCoffePreco,addCoffeName,addCoffeTaxa;
    @FXML
    private ListView receitaListView;
    public ArrayList<String> ingredienteNamesList ;
	public ArrayList<Double> ingredienteQuantidadeList ;
	public ArrayList<String> ingredienteTipoList ;
	
    private int[] fourConditions = {0,0,0,0};
    private IngredientController controller;
    public ArrayList<String> ingredientItems = new ArrayList();
	public ObservableList<String> names = FXCollections.observableArrayList(ingredientItems);
	
    @Override 
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        // initialize your logic here: all @FXML variables will have been injected
    	receitaListView.setItems(names);
    	criaReceita.setOnAction(e -> openView("Receita::Criar","IngredientView.fxml"));
    	desfazReceita.setOnAction(e ->handleDesfazReceita());
    	addCoffeSalvar.setDisable(true);
    	addCoffeSalvar.setOnAction(e ->handleSalvaBD());
    	
    	addCoffePreco.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	if(addCoffePreco.getText().isEmpty()){
            		fourConditions[0] = 0;
            		addCoffeSalvar.setDisable(true);
        		}else{
        			fourConditions[0] = 1;
        			
        		}
            	if(fourConditions[0] == fourConditions[1] && fourConditions[1] == fourConditions[2] && 
            			fourConditions[2] == fourConditions[3] && fourConditions[1]==fourConditions[3] ){
            		addCoffeSalvar.setDisable(false);
            		
            	}
            }
        });
    	
    	addCoffeName.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	if(addCoffeName.getText().isEmpty()){
            		fourConditions[1] = 0;
            		addCoffeSalvar.setDisable(true);
        		}else{
        			fourConditions[1] = 1;
        			
        		}
            	if(fourConditions[0] == fourConditions[1] && fourConditions[1] == fourConditions[2] && 
            			fourConditions[2] == fourConditions[3] && fourConditions[1]==fourConditions[3]){
            		addCoffeSalvar.setDisable(false);
            		
            	}
            }
        });
    	
    	addCoffeTaxa.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	if(addCoffeTaxa.getText().isEmpty()){
            		fourConditions[2] = 0;
            		addCoffeSalvar.setDisable(true);
        		}else{
        			fourConditions[2] = 1;
        			
        		}
            	if(fourConditions[0] == fourConditions[1] && fourConditions[1] == fourConditions[2] && 
            			fourConditions[2] == fourConditions[3] && fourConditions[1]==fourConditions[3]){
            		addCoffeSalvar.setDisable(false);
            	}
            }
        });
    	cancelCoffe.setOnAction(e->handleClose());
    }
    
    public void handleClose(){
    	Stage stage = (Stage) cancelCoffe.getScene().getWindow();
		stage.close();
    }
    
    public void openView(String title, String view){
    	Stage stage; 
    	Parent root = null;
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(view)); 
        
        stage = new Stage();
        try {
        	root = fxmlLoader.load();
			controller = fxmlLoader.<IngredientController>getController();
			} catch (IOException e) {
					// TODO Auto-generated catch block
			e.printStackTrace();
		}
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.setOnHidden(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                // Refresh the parent window here
            	System.out.println("deu update no pai");
            	ingredienteNamesList = controller.getNames();
            	ingredienteQuantidadeList = controller.getQuantidades();
            	ingredienteTipoList = controller.getTipos();
            	names = controller.getIngredientsList();
            	if(!names.isEmpty()){
	            	receitaListView.setItems(names);
	            	fourConditions[3] = 1;
	            	if(fourConditions[0]==fourConditions[1] && fourConditions[2]==fourConditions[3]
	            			&& fourConditions[1]==fourConditions[3]){
	            		addCoffeSalvar.setDisable(false);
	            	}
	            	//System.out.println(names.get(0));
	            	/*ArrayList<String> names = controller.getNames();
	            	for(String temp : names){
	            		System.out.println(temp);
	            	}*/
            	}
            	            	
            }
        });
        stage.showAndWait();
   }

    public void handleDesfazReceita(){
    	fourConditions[3] = 0;
    	addCoffeSalvar.setDisable(true);
    	ingredienteNamesList.clear();
    	ingredienteQuantidadeList.clear();
    	ingredienteTipoList.clear();
    	names.clear();
    	receitaListView.getItems().clear();
    	System.out.println("limpou tudo");
    }

    public void handleSalvaBD(){//dps tratar caso da tabela n existir
    	CoffeSQL cof = new CoffeSQL();
    	String name = addCoffeName.getText();
    	Double preco = Double.parseDouble(addCoffePreco.getText());
    	Double taxa = Double.parseDouble(addCoffeTaxa.getText());
    	cof.insertCoffe(name, preco, taxa);
    	ReceitaSQL rec = new ReceitaSQL();
    	for(int i=0; i< ingredienteNamesList.size();i++){
    		rec.insertIngredient(name, ingredienteNamesList.get(i), ingredienteQuantidadeList.get(i),
    				ingredienteTipoList.get(i));
    	}
    	Stage stage = (Stage) addCoffeSalvar.getScene().getWindow();
		stage.close();
    }
}
