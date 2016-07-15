package application;

import java.math.BigDecimal;
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
import javafx.scene.control.TextField;


public class RecipeController implements Initializable {
 @FXML
 private Slider recipeSlider;

 @FXML
 private ProgressBar recipeBar;

 @FXML
 private Button recipeConfirm;
 
 @FXML
 private TextField numericField;
 
 @FXML
 private Label percentRecipe;
 
@Override
public void initialize(URL location, ResourceBundle resources) {
	// TODO Auto-generated method stub

	recipeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	 BigDecimal bd = BigDecimal.valueOf((double) new_val);
        		 bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
        		 Double value = bd.doubleValue();
            	 System.out.println(bd.doubleValue());
            	 recipeBar.setProgress((double) new_val/100);
            	 numericField.setText(value.toString());
            	 percentRecipe.setText(value.toString() + "%");
            }
        });
	
	recipeConfirm.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	});
	
	numericField.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (!newValue.matches("\\d*")) {
            	numericField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        }
    });
}
}
