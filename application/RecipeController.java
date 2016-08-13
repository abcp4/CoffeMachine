package application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.When.NumberConditionBuilder;
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
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sql.RecipienteSQL;


public class RecipeController implements Initializable {
 @FXML
 private Slider recipeSlider;

 @FXML
 private ProgressBar recipeBar;

 @FXML
 private Button recipeConfirm,eraseButton,cancelButton;
 
 @FXML
 private TextField numericField;
 
 @FXML
 private TextField  conteudoField;
 
 @FXML
 private Label percentRecipe;
 
 private int id;
 private Boolean newRecipient = false;
 private Boolean eraseAll = false;
@Override
public void initialize(URL location, ResourceBundle resources) {
	// atravez do textfield descobrir se e novo recipiente ou n
	
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
	
	numericField.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        	
        	if(!newValue.isEmpty())
            {
        		Double value = Double.parseDouble(newValue);
        		recipeBar.setProgress((value)/100);
	        	percentRecipe.setText(value.toString() + "%");
	        	recipeSlider.setValue(value);
            }
        	
        }
});
	
	recipeConfirm.setOnAction(new EventHandler<ActionEvent>() {

		@Override
		public void handle(ActionEvent event) {
			RecipienteSQL rep = new RecipienteSQL();
			if(eraseAll){
				rep.removeRecipiente(id);
			}else if(id>=0){//se for um recipiente ja existente(com nome) basta update
				DecimalFormat df = new DecimalFormat("##.##");
				df.setRoundingMode(RoundingMode.DOWN);
				rep.updateQuantidade(id,truncateDecimal(recipeBar.getProgress()*100,2));//cuidado aki
				System.out.println("Atualizou valor para: "+truncateDecimal(recipeBar.getProgress()*100,2));
			}else{//senao insere(id = -1)
					rep.insertIngredient(conteudoField.getText(), Double.parseDouble(numericField.getText()));
					System.out.println("inseriu: "+conteudoField.getText()+ "com valor:"+
							numericField.getText());
			}
			Stage stage = (Stage) recipeConfirm.getScene().getWindow();
			stage.close();
		}
		
	});
	
	conteudoField.textProperty().addListener(new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        	if(conteudoField.getText().isEmpty()){
    			recipeConfirm.setDisable(true);
    		}else{
    			recipeConfirm.setDisable(false);
    		}
        }
    });
	
	eraseButton.setOnAction(e->handleEraseAll());
	cancelButton.setOnAction(e->handleClose());
}

public void handleClose(){
	Stage stage = (Stage) cancelButton.getScene().getWindow();
	stage.close();
}
public void handleEraseAll(){
	conteudoField.setText("");
	numericField.setText("0");
	percentRecipe.setText("0.0%");
	recipeBar.setProgress(0.0);
	recipeConfirm.setDisable(false);
	eraseAll = true;
}
	public void setId(int id){
		System.out.println("set id");
		this.id = id;
	}
	public void updateValues(){
		if(this.id>=0){//id ja registrado
			RecipienteSQL rep = new RecipienteSQL();
			String name = rep.getName(this.id);
			System.out.println(name);
			System.out.println(this.id);
			if(name!=null){
			Double quantidade = rep.getQuantidade(id);
			recipeBar.setProgress(quantidade/100);
			conteudoField.setText(name);
			numericField.setText(quantidade.toString());
			percentRecipe.setText(quantidade.toString() + "%");
			}
			/*if(conteudoField.getText().isEmpty()){
				recipeConfirm.setDisable(true);
				newRecipient = true;
			}*/
		}
		
	}
	
	private static Double truncateDecimal(double x,int numberofDecimals)
	{
	    if ( x > 0) {
	        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR).doubleValue();
	    } else {
	        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING).doubleValue();
	    }
	}
}
