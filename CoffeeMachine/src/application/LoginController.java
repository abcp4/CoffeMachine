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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*
 * LoginController: Autenticação do administrador no sistema que irá
 * validar o usuário para que possa posteriormente fazer atualizações dentro da máquina de café.
 * homeButton = Redireciona para a tela inicial
 * loginButton = se usuário validado redireciona para a tela de administração da máquina.
 * */
public class LoginController implements Initializable {

    @FXML 
    private Button homeButton; 
    @FXML
    private TextField nameInput;
    @FXML
    private TextField passInput;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginError;

    
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        assert homeButton != null : "fx:id=\"homeButton\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert nameInput != null : "fx:id=\"nameInput\" was not injected: check your FXML file 'LoginView.fxml'.";
        assert passInput != null : "fx:id=\"passInput\" was not injected: check your FXML file 'LoginView.fxml'.";
        

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
        	Stage stage; 
            Parent root;
            
            @Override
            public void handle(ActionEvent event) {
            	 stage=(Stage) homeButton.getScene().getWindow();
            	 try {
					root = FXMLLoader.load(getClass().getResource("view/MainView.fxml"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	 Scene scene = new Scene(root);
                 stage.setScene(scene);
                 stage.show();
            }
        });
                
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
        	Stage stage; 
            Parent root;
            
        	@Override
            public void handle(ActionEvent event) {
        		boolean isAlphaNum = (nameInput.getText()).matches("[A-Za-z0-9]+");
        		if(isAlphaNum && !passInput.getText().isEmpty()){
        			System.out.println("Username: "+nameInput.getText());
	        		System.out.println("Password: "+passInput.getText());
	        		 stage=(Stage) loginButton.getScene().getWindow();
	            	 try {
						root = FXMLLoader.load(getClass().getResource("view/AdminView.fxml"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	 Scene scene = new Scene(root);
	                 stage.setScene(scene);
	                 stage.show();
        		}
        		else
        			//loginError.setText("Campo(s) incorreto(s)!");
        			System.out.println("Erro!");
            }
        });
        
   }
    
    

}