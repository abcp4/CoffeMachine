package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CoffeeController implements Initializable {

    @FXML 
    private Button doRecipe;
    
    @FXML 
    private Button cancelButton;
    
    @FXML
    private ToggleGroup options;
    
    private Pane coffeeView;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        // initialize your logic here: all @FXML variables will have been injected
    	doRecipe.setOnAction(e -> openView("Receita::Criar","view/IngredientView.fxml"));
    	
    	options.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
    	      public void changed(ObservableValue<? extends Toggle> ov,
    	          Toggle old_toggle, Toggle new_toggle) {
    	        if (options.getSelectedToggle() != null) {
    	        	RadioButton chk = (RadioButton)options.getSelectedToggle(); // Cast object to radio button
    	            System.out.println("Selected Radio Button - "+ chk.getText());
    	        }
    	      }
    	    });
    	
    	//cancelButton.setOnAction(e ->Main.closePopUp((Stage)coffeeView.getScene().getWindow()));
    	
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
