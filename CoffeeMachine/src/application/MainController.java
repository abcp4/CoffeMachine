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
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML //  fx:id="myButton"
    private Button myButton; // Value injected by FXMLLoader
    
    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert myButton != null : "fx:id=\"myButton\" was not injected: check your FXML file 'MainView.fxml'.";

        // initialize your logic here: all @FXML variables will have been injected
        myButton.setOnAction(new EventHandler<ActionEvent>() {
        	Stage stage; 
            Parent root;
            
            @Override
            public void handle(ActionEvent event) {
            	 stage=(Stage) myButton.getScene().getWindow();
            	 try {
					root = FXMLLoader.load(getClass().getResource("view/LoginView.fxml"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	 Scene scene = new Scene(root);
                 stage.setScene(scene);
                 stage.show();
            }
        });
        
    }

}