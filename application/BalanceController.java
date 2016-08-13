package application;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sql.BalancoSQL;
import sql.TransactionSQL;

public class BalanceController implements Initializable{
	@FXML
	private Button tenButton,fiveButton,twoButton,fiftyCentButton,confirmButton,cancelButton;
	
	@FXML
	private TextField tenTextField,fiveTextField,twoTextField,fiftyCentTextField;
	
	@FXML
	private Label totalLabel;
	
	private Double totalValue = 0.0;
	private int[]money = {0,0,0,0};
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		totalLabel.setText("R$ 0.0");
		tenTextField.setText("0");
		fiveTextField.setText("0");
		twoTextField.setText("0");
		fiftyCentTextField.setText("0");
		
		tenButton.setOnAction(e->handleMoneyButtons(10.0,tenTextField));
		fiveButton.setOnAction(e->handleMoneyButtons(5.0,fiveTextField));
		twoButton.setOnAction(e->handleMoneyButtons(2.0,twoTextField));
		fiftyCentButton.setOnAction(e->handleMoneyButtons(0.50,fiftyCentTextField));
		
		confirmButton.setOnAction(e->handleDeposit());
		cancelButton.setOnAction(e->handleClose());
	}
	
	public void handleMoneyButtons(Double value,TextField result){
		
		Integer textValue = Integer.parseInt(result.getText());
		textValue+=1;
		result.setText(textValue.toString());
		totalValue+=value;
		totalLabel.setText("R$ "+totalValue.toString());
	}

	public void handleDeposit(){
		
		money[0] = Integer.parseInt(tenTextField.getText());
		money[1] = Integer.parseInt(fiveTextField.getText());
		money[2] = Integer.parseInt(twoTextField.getText());
		money[3] = Integer.parseInt(fiftyCentTextField.getText());
		
		TransactionSQL tr = new TransactionSQL();
		tr.insertTransaction("Admin","deposito", money);
		BalancoSQL bal = new BalancoSQL();
		int[] balacoTotal = bal.getBalanco();
		for(int i=0;i<4;i++){money[i] += balacoTotal[i] ;};
		Double total = money[0]*10+money[1]*5+money[2]*2+money[3]*0.5;
		
		bal.updateBalanco(total, money);
		Stage stage = (Stage) confirmButton.getScene().getWindow();
		stage.close();
	}
	public void handleClose(){
		Stage stage = (Stage) confirmButton.getScene().getWindow();
		stage.close();
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
