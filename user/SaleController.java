package user;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sql.BalancoSQL;
import sql.RecipientObject;
import sql.RecipienteSQL;
import sql.TransactionSQL;

public class SaleController implements Initializable{

	
	@FXML
	private Button tenButton,fiveButton,twoButton,fiftyCentButton,confirmButton,cancelButton;
	
	@FXML
	private TextField tenTextField,fiveTextField,twoTextField,fiftyCentTextField;
	
	@FXML
	private Label totalToPayLabel,payedValueLabel,changeLabel;
	
	private Double payedValue = 0.0;
	private Double totalToPay = 0.0;
	private Double change = 0.0;
	
	private ArrayList<RecipientObject> recipientesObjects;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tenTextField.setText("0");
		fiveTextField.setText("0");
		twoTextField.setText("0");
		fiftyCentTextField.setText("0");
		
		totalToPayLabel.setText("R$ "+totalToPay.toString());
		payedValueLabel.setText("R$ 0.0 ");
		changeLabel.setText("R$ 0.0 ");
		
		tenButton.setOnAction(e->handleMoneyButtons(10.0,tenTextField));
		fiveButton.setOnAction(e->handleMoneyButtons(5.0,fiveTextField));
		twoButton.setOnAction(e->handleMoneyButtons(2.0,twoTextField));
		fiftyCentButton.setOnAction(e->handleMoneyButtons(0.50,fiftyCentTextField));
		
		confirmButton.setOnAction(e->handleChange());
		cancelButton.setOnAction(e->closeFunction());
	}
	public void closeFunction(){
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	public void handleChange(){
		BalancoSQL bl = new BalancoSQL();
		int[] balanco = bl.getBalanco();
		double[] money = {10,5,2,0.5};
		Change c = new Change(balanco, money);
		c.computeNewChange(change);
		int[] resultado = c.getNewChange();
		//Double comparacao = Math.round(change)-c.getTotal();
		//System.out.println(comparacao);
		if(c.getIsAvailable()){
			System.out.println("ta ok");
			takeIngredients();
			takeMoney(resultado,balanco,bl);
		}else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Erro de Transacao");
			alert.setHeaderText("Troco Insuficiente");
			alert.setContentText("Escolha outras notas");

			alert.showAndWait();
		}
		System.out.println("Devolvido:");
		System.out.println("R$ 10: " +resultado[0]);
		System.out.println("R$ 5: " +resultado[1]);
		System.out.println("R$ 2: " +resultado[2]);
		System.out.println("R$ 0,50: " +resultado[3]);
		
		closeFunction();
	}
	
	public void takeMoney(int[] resultado,int[] balanco,BalancoSQL bl){
		TransactionSQL tl = new TransactionSQL();
		int[] new_balanco = {0,0,0,0};
		for(int i=0;i<new_balanco.length;i++){
			new_balanco[i] = balanco[i] - resultado[i];
		}
		Double total = new_balanco[0]*10+new_balanco[1]*5+new_balanco[2]*2+new_balanco[3]*0.5;
		bl.updateBalanco(total, new_balanco);//atualiza com retirada de troco
		int[] money = {0,0,0,0};
		money[0] = Integer.parseInt(tenTextField.getText());
		money[1] = Integer.parseInt(fiveTextField.getText());
		money[2] = Integer.parseInt(twoTextField.getText());
		money[3] = Integer.parseInt(fiftyCentTextField.getText());
		bl.updateBalanco(total, new_balanco);//atualiza com insercao de deposito
		tl.insertTransaction("client", "deposito", money);//registra transacao
	}
	
	public void takeIngredients(){
		RecipienteSQL rec = new RecipienteSQL();
		for(RecipientObject temp : recipientesObjects){
			System.out.println(temp.getName()+" , "+temp.getQuantidade());
		rec.updateQuantidade(temp.getName(),truncateDecimal(temp.getQuantidade(),2));
		}
	}
	
	public void handleMoneyButtons(Double value,TextField result){
		Integer textValue = Integer.parseInt(result.getText());
		textValue+=1;
		result.setText(textValue.toString());
		payedValue+=value;
		payedValueLabel.setText("R$ "+payedValue.toString());
	    change = truncateDecimal(payedValue-totalToPay,2);
		changeLabel.setText("R$ "+change.toString());
	}
	public void setTotalToPay(Double value){
		System.out.println("Valor inserido "+value);
		totalToPay = value;
		totalToPayLabel.setText("R$ "+totalToPay.toString());
	}
	public void setRemainingIngredients(ArrayList<RecipientObject> rO){
		 recipientesObjects = rO;
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
