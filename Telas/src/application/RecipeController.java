package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;


public class RecipeController implements Initializable {
 @FXML
 private Slider recipeSlider;

 @FXML
 private ProgressBar recipeBar;

 @FXML
 private Button recipeConfirm;
 
@Override
public void initialize(URL location, ResourceBundle resources) {
	// TODO Auto-generated method stub

	recipeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	 System.out.println(new_val.doubleValue());
            	 recipeBar.setProgress((double) new_val/100);
            }
        });
	
	recipeConfirm.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	});
}
}