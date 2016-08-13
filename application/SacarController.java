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

public class SacarController implements Initializable{
	@FXML
	private Button tenButton,fiveButton,twoButton,fiftyCentButton,confirmButton,cancelButton;
	
	@FXML
	private TextField tenTextField,fiveTextField,twoTextField,fiftyCentTextField;
	
	@FXML
	private Label totalLabel,saqueLabel;
	
	private int[] maxBalancoValues={0,0,0,0};
	private Double totalValue = 0.0;
	private Double totalSaque = 0.0;
	private int[]money = {0,0,0,0};
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		BalancoSQL bal = new BalancoSQL();
		int[] balacoValores = bal.getBalanco();
		Double balancoTotal = bal.getTotal();
		saqueLabel.setText("0");
		totalLabel.setText("R$ "+balancoTotal.toString());
		totalValue = balancoTotal;
		tenTextField.setText(new Integer(balacoValores[0]).toString());
		fiveTextField.setText(new Integer(balacoValores[1]).toString());
		twoTextField.setText(new Integer(balacoValores[2]).toString());
		fiftyCentTextField.setText(new Integer(balacoValores[3]).toString());
		for(int i = 0; i<4;i++){
			maxBalancoValues[i] = balacoValores[i];
		}
		
		tenButton.setOnAction(e->handleMoneyButtons(10.0,tenTextField));
		fiveButton.setOnAction(e->handleMoneyButtons(5.0,fiveTextField));
		twoButton.setOnAction(e->handleMoneyButtons(2.0,twoTextField));
		fiftyCentButton.setOnAction(e->handleMoneyButtons(0.50,fiftyCentTextField));
		
		confirmButton.setOnAction(e->handleSaque());
		cancelButton.setOnAction(e->handleClose());
	}
	
	public void handleMoneyButtons(Double value,TextField result){
		
		Integer textValue = Integer.parseInt(result.getText());
		if(textValue>0){
			textValue-=1;
			result.setText(textValue.toString());
			totalValue-=value;
			totalSaque+=value;
			totalLabel.setText("R$ "+totalValue.toString());
			saqueLabel.setText("R$ "+totalSaque.toString());
		}
	}

	public void handleSaque(){
		
		money[0] = Integer.parseInt(tenTextField.getText());
		money[1] = Integer.parseInt(fiveTextField.getText());
		money[2] = Integer.parseInt(twoTextField.getText());
		money[3] = Integer.parseInt(fiftyCentTextField.getText());
		
		int[] saque_money = {0,0,0,0};
		for(int i =0;i<4;i++){
			saque_money[i] = maxBalancoValues[i] - money[i];
		}
		TransactionSQL tr = new TransactionSQL();
		tr.insertTransaction("Admin","saque", saque_money);
		BalancoSQL bal = new BalancoSQL();
		
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
