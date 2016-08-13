package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sql.BalancoSQL;
import sql.RecipienteSQL;

public class AdminController implements Initializable {

    @FXML
    private Button buttonRecipiente5,buttonRecipiente4,buttonRecipiente3,buttonRecipiente2
    			   ,buttonRecipiente1,sacarButton,depositarButton,returnButton;
     
    @FXML
    private MenuItem addCoffee, editCoffee; // Value injected by FXMLLoader
    @FXML
    private AnchorPane fatherPanePB;
    @FXML
    private AnchorPane fatherPaneLabel;
    @FXML
    private ProgressBar pb1;
    @FXML
    private ProgressBar pb2;
    @FXML
    private ProgressBar pb3;
    @FXML
    private ProgressBar pb4;
    @FXML
    private ProgressBar pb5;
    @FXML
    private Label saldoLabel;
    
    private ArrayList<Integer> recipientesIds ;
    //@FXML
    //private MenuItem addIngredient, editIngredient;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        // initialize your logic here: all @FXML variables will have been injected
    
    	addCoffee.setOnAction(e -> openView("Cafe::Cadastrar","AddCoffeeView.fxml"));
    	editCoffee.setOnAction(e -> openView("Cafe::Procurar","EditCoffeeView.fxml"));
    	depositarButton.setOnAction(e -> openView2("Balanco::Depositar","DepositarView.fxml"));
    	sacarButton.setOnAction(e -> openView2("Balanco::Depositar","SacarView.fxml"));
    	
    	BalancoSQL bal = new BalancoSQL();
    	Double total = bal.getTotal();
    	saldoLabel.setText(total.toString());
    	returnButton.setOnAction(e->handleReturn());
    	updateBars();
    	
    }
    public void updateBars(){
    	RecipienteSQL rep = new RecipienteSQL();
    	recipientesIds = rep.getRecipientesIds();
    	for(int j=0;j<fatherPaneLabel.getChildren().size();j++){
    		Label l = (Label) fatherPaneLabel.getChildren().get(j);
    		ProgressBar p = (ProgressBar) fatherPanePB.getChildren().get(j);
    		l.setText("Empty");
    		p.setProgress(0.0);
    	}
    	int i = 0;
		for(Integer temp : recipientesIds){
			Label l = (Label) fatherPaneLabel.getChildren().get(i);
			ProgressBar p = (ProgressBar) fatherPanePB.getChildren().get(i);
			l.setText(rep.getName(temp));
			p.setProgress(rep.getQuantidade(temp)/100);//i+1 = id
			i++;
		}
		while(recipientesIds.size()<5){
			recipientesIds.add(-1);
		}
		buttonRecipiente1.setOnAction(e -> openRecipeView("Ingrediente::Editar","RecipeView.fxml",recipientesIds.get(0)));
    	buttonRecipiente2.setOnAction(e -> openRecipeView("Ingrediente::Editar","RecipeView.fxml",recipientesIds.get(1)));
    	buttonRecipiente3.setOnAction(e -> openRecipeView("Ingrediente::Editar","RecipeView.fxml",recipientesIds.get(2)));
    	buttonRecipiente4.setOnAction(e -> openRecipeView("Ingrediente::Editar","RecipeView.fxml",recipientesIds.get(3)));
    	buttonRecipiente5.setOnAction(e -> openRecipeView("Ingrediente::Editar","RecipeView.fxml",recipientesIds.get(4)));
    	
    }
    public void handleReturn(){
    	Stage stage = (Stage) returnButton.getScene().getWindow();
		stage.close();
    	openView("Client:Main","../user/MainView.fxml");
    }
    
    public void openRecipeView(String title, String view, int id){
    	Stage stage; 
    	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(view));     
    	
    	Parent root = null;
		try {
			
			root = fxmlLoader.load();
			RecipeController controller = fxmlLoader.<RecipeController>getController();
			
			controller.setId(id);
			controller.updateValues();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    	
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        
        stage.setOnHidden(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                // Refresh the parent window here
            	System.out.println("deu update no pai");
            	updateBars();
            }
        });
        
        //aki passa o controle para o RecipeController
        stage.showAndWait();
        
   }
    
    public void openView(String title, String view){
    	Stage stage; 
    	Parent root = null;
        
        stage = new Stage();
        try {
        		root = FXMLLoader.load(getClass().getResource(view));
			} catch (IOException e) {
					// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        
   }
    
    public void openView2(String title, String view){
    	Stage stage; 
    	Parent root = null;
        
        stage = new Stage();
        try {
        		root = FXMLLoader.load(getClass().getResource(view));
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
            	BalancoSQL bal = new BalancoSQL();
            	Double total = bal.getTotal();
            	System.out.println(total);
            	saldoLabel.setText(total.toString());
            }
        });
        
        stage.showAndWait();
    }
}
