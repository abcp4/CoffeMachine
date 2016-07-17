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
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML //  fx:id="loginButton"
    private Button loginButton, buyButton;

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'MainView.fxml'.";

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
        
        //buyButton.setOnAction(e -> openView("Café::Confirmar Compra","SaleView.fxml"));
    }

    /*
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
   */
}