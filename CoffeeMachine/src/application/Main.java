package application;
	
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Main extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[])null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            StackPane page = (StackPane) FXMLLoader.load(Main.class.getResource("view/MainView.fxml"));
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            //primaryStage.setTitle("FXML is Simple");
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void closePopUp(Stage window)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Deseja salvar as alterações?");
		//alert.setContentText("Are you ok with this?");
		
		ButtonType yesButton = new ButtonType("Sim");
		ButtonType noButton = new ButtonType("Não");
		ButtonType cancelButton = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
		
		alert.getButtonTypes().setAll(yesButton, noButton, cancelButton);
		
		Optional<ButtonType> answer = alert.showAndWait();
		if(answer.get() == yesButton){
			System.out.println("Alterações Salvas com sucesso!");
			window.hide();
		}
		else if(answer.get()== noButton)
			window.close();
			
	}
    
 
}