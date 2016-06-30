package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdminController implements Initializable {

    @FXML //  fx:id="waterDetails"
    private Button waterDetails;
    @FXML
    private MenuItem addCoffee, editCoffee; // Value injected by FXMLLoader
    //@FXML
    //private MenuItem addIngredient, editIngredient;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        // initialize your logic here: all @FXML variables will have been injected
    	waterDetails.setOnAction(e -> openView("Ingrediente::Editar","view/ContainerView.fxml"));
    	addCoffee.setOnAction(e -> openView("Café::Cadastrar","view/CoffeeView.fxml"));
    	editCoffee.setOnAction(e -> openView("Café::Procurar","view/EditCoffeeView.fxml"));

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
    
}
