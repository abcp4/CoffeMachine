package application;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;



public class ContainerController implements Initializable {
 @FXML
 private Slider containerSlider;

 @FXML
 private ProgressBar containerBar;

 @FXML
 private Button containerConfirm;
 
 @FXML
 private TextField numericField;
 
 @FXML
 private Label percentContainer;
 
 @FXML
 private Button containerCancel;
 
 @FXML
 private SplitPane containerView;
 
 @FXML
 private Button cleanButton;
 
@Override
public void initialize(URL location, ResourceBundle resources) {

	containerSlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            	 BigDecimal bd = BigDecimal.valueOf((double) new_val);
        		 bd = bd.setScale(2, BigDecimal.ROUND_DOWN);
        		 Double percent = bd.doubleValue();
            	 System.out.println(bd.doubleValue());
            	 containerBar.setProgress((double) new_val/100);
            	 bd = BigDecimal.valueOf((double) percent/5).setScale(2, BigDecimal.ROUND_DOWN);
            	 Double value = bd.doubleValue();
            	 numericField.setText(value.toString());
            	 percentContainer.setText(percent.toString() + "%");
            }
        });
	
	numericField.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        	
        	if(!newValue.isEmpty())
            {
        		Double value = Double.parseDouble(newValue)*5;
	        	containerBar.setProgress((value)/100);
	        	percentContainer.setText(value.toString() + "%");
	        	containerSlider.setValue(value);
            }
        	
        }
    });
	
	cleanButton.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			containerBar.setProgress(0.0);
			numericField.setText("0");
			percentContainer.setText("0.0%");
			containerSlider.setValue(0.0);
			
		}
		
	});
	
	
	containerConfirm.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			
		}
		
	});
	
	/*containerView.getScene().getWindow().setOnCloseRequest(e ->{
		//e.consume();
		containerView.getScene().getWindow();
		//closeProgram(containerView.getScene().getWindow());
	});
	*/
	//fazer um para o botão fechar
	containerCancel.setOnAction(e ->Main.closePopUp((Stage)containerView.getScene().getWindow()));
}

}
