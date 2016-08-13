package user;

import java.io.IOException;
import java.math.BigDecimal;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sql.CoffeObject;
import sql.CoffeSQL;
import sql.ReceitaSQL;
import sql.RecipientObject;
import sql.RecipienteSQL;

public class MainController implements Initializable {

    @FXML //  fx:id="loginButton"
    private Button loginButton, buyButton;
    @FXML
    private ChoiceBox<String> cupSizeButton;
    @FXML
    private ListView<String> cafeList,ingredientList;
    @FXML
    private Label subTotalLabel;
    ObservableList<String> names;
    ObservableList<String> namesIngredientes;
    private Double currentPrice = 0.0;
    private Double currentTax = 0.0;
    private Double currentSubTotal = 0.0;
    
    private ArrayList<RecipientObject> recipientesObjects;
    SaleController controller;
    
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'MainView.fxml'.";
        subTotalLabel.setText("R$ 0.0");
        buyButton.setDisable(true);
        //buyButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
        cupSizeButton.setItems(FXCollections.observableArrayList(
        	    "50ml", "100ml", "150ml")
        	);
        cupSizeButton.getSelectionModel().select(0);
        // initialize your logic here: all @FXML variables will have been injected
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
        	Stage stage; 
            Parent root;
            
            @Override
            public void handle(ActionEvent event) {
            	 stage=(Stage) loginButton.getScene().getWindow();
            	 try {
					root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	 Scene scene = new Scene(root);
                 stage.setScene(scene);
                 stage.show();
            }
        });
        //loginButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
        //buyButton.setOnAction(e -> openView("Cafe::Confirmar Compra","SaleView.fxml"));
        ReceitaSQL rec = new ReceitaSQL();
        CoffeSQL coffeSQL = new CoffeSQL();
		ArrayList<CoffeObject> coffes = coffeSQL.getCoffes();
		ArrayList<String> coffeNames = new ArrayList();
		for (CoffeObject temp : coffes) {
			coffeNames.add(temp.name);
		}
		 names = FXCollections.observableArrayList(coffeNames);
		 cafeList.setItems(names);
		 cafeList.getSelectionModel().selectedItemProperty().addListener(
		            new ChangeListener<String>() {
		                public void changed(ObservableValue<? extends String> ov, 
		                    String old_val, String new_val) {
		                	System.out.println(new_val);
		                	
		                	//mudar lista de ingredientes a cada novo cafe selecionado	
		             		ArrayList<String> descricoes = rec.getDescricao(new_val);
		             		for(String temp : descricoes){
		             			System.out.println(temp);
		             		}
		             		if(!descricoes.isEmpty()){
		             			buyButton.setDisable(false);
		             		}
		             		namesIngredientes = FXCollections.observableArrayList(descricoes);
		             		ingredientList.setItems(namesIngredientes);
		             		//Calcula sub-total
		             		Double selectedValue = 0.0;
		             		String selected = (String) cupSizeButton.getSelectionModel().getSelectedItem();
		             		if(selected.equals("50ml")){
		             			selectedValue = 50.0;
		             		}else if(selected.equals("100ml")){
		             			selectedValue = 100.0;
		             		}else if(selected.equals("150ml")){
		             			selectedValue = 150.0;
		             		}
		             		CoffeObject cb = coffeSQL.getCoffe(new_val);
		             		//Preco_Atual = Preco_Base*[1 + taxa*(mlAtual/mlBase - 1)]
		             		//Preco_atual_150ml = 2,50 + 1*(150ml/50ml - 1)*2,50
		             		currentPrice = cb.preco;
		             		currentTax = cb.taxa;
		            
		             		Double subTotal = currentPrice*(1+currentTax*((selectedValue/50.0) - 1));
		             		currentSubTotal =  truncateDecimal(subTotal,2);
		             		System.out.println("Current price: "+currentPrice+" CurrentTax:"
		             				+ currentTax+ " SubTotal: "+currentSubTotal);
		             		subTotalLabel.setText("R$ "+truncateDecimal(subTotal,2).toString());
		                }
		        }); 
		
		 cupSizeButton.getSelectionModel().selectedItemProperty().addListener(
		            new ChangeListener<String>() {
		                public void changed(ObservableValue<? extends String> ov, 
		                    String old_val, String new_val) {
		                	//Calcula sub-total
		             		Double selectedValue = 0.0;
		             		String selected = (String) cupSizeButton.getSelectionModel().getSelectedItem();
		             		if(selected.equals("50ml")){
		             			selectedValue = 50.0;
		             		}else if(selected.equals("100ml")){
		             			selectedValue = 100.0;
		             		}else if(selected.equals("150ml")){
		             			selectedValue = 150.0;
		             		}
		                	Double subTotal = currentPrice*(1+currentTax*((selectedValue/50.0) - 1));
		                	currentSubTotal =  truncateDecimal(subTotal,2);
		                	subTotalLabel.setText(currentSubTotal.toString());
		                }
			        }); 
		
		 buyButton.setOnAction(e -> verifyIngredients());     
    }

    private static Double truncateDecimal(double x,int numberofDecimals)
	{
	    if ( x > 0) {
	        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR).doubleValue();
	    } else {
	        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING).doubleValue();
	    }
	}
    
    public void verifyIngredients(){
    	RecipienteSQL recipiente = new RecipienteSQL();
    	recipientesObjects = recipiente.getRecipientesObjects();
    	Boolean valid = false;
    	//verificacao 1: saber se todos os ingredientes existem
    	for(String temp : namesIngredientes){
    		String ingrediente = temp.split("\\s+")[3];
    		System.out.println("Ingrediente: "+ingrediente);
    		Boolean foundMatch = false;
    		for(int i =0; i<recipientesObjects.size();i++){
    			
    			System.out.println(recipientesObjects.get(i).getName());
    			if(recipientesObjects.get(i).getName().equals(ingrediente)){
    				foundMatch = true;
    			}
    		}
    		if(foundMatch){
    			valid = true;
    		}else{
    			valid = false;
    			Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle("Warning Dialog");
    			alert.setHeaderText("Ausencia de Ingrediente");
    			alert.setContentText("Escolha outra opcao");

    			alert.showAndWait();
    			return;//faz o alerta aqui de que n ha ingredientes
    			
    		}
    	}
    
    	
    	//verificacao 2
    	for(String temp : namesIngredientes){
    		Double quantidade = Double.parseDouble(temp.split(" ")[0]);
    		String ingrediente = temp.split(" ")[3];
    		for(RecipientObject obj : recipientesObjects){
    			if(obj.getName().equals(ingrediente)){
    				obj.setQuantidade(obj.getQuantidade()-quantidade);
    				if(obj.getQuantidade()<0){
    					valid = false;
    					Alert alert = new Alert(AlertType.WARNING);
    	    			alert.setTitle("Warning Dialog");
    	    			alert.setHeaderText("Quantidade insuficiente de Ingrediente");
    	    			alert.setContentText("Escolha outra opcao");

    	    			alert.showAndWait();
    					return;//faz o alerta aqui de que n ha ingredientes
    				}
    			}
    		}
    	}
    	
    	
    	if(valid){
    	openView("Cafe::Confirmar Compra","SaleView.fxml");
    	}
    }
    
    public void openView(String title, String view){
    	Stage stage; 
    	Parent root = null;
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(view)); 
        
        stage = new Stage();
        try {
        	root = fxmlLoader.load();
			controller = fxmlLoader.<SaleController>getController();
			controller.setTotalToPay(currentSubTotal);
			controller.setRemainingIngredients(recipientesObjects);
			
			} catch (IOException e) {
			e.printStackTrace();
		}
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        
        stage.showAndWait();
   }
}