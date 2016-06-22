package coffeMachinePackge;
import java.util.Scanner;
public class Gerente
{
	// Dados do gerente
	private static boolean checked = false; //False caso gerente não validado.
	private String usuario;
	private String senha;
	Scanner leitor = new Scanner(System.in);
	
	//Getrs e Setrs
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	/*Recebe um usuario e senha. Faz uma comparação com o usuario e senha registrados o bd*/
	
	public void login()  //Achei desnessessario argumentos, devido a os atributos já presentes, mas se acharem melhor posso alterar
	{
		String bdUser, bdPass;
		bdUser = bdPass = null; //Deve ser alterado para recebar os valores do BD
		setUsuario(leitor.nextLine()); //Recebe User
		setSenha(leitor.nextLine()); //Recebe a senha
		
		/*Compara os valores recebidos com os do BD. Caso sejam iguais a variavel, checked tem seu valor alterado pra verdade
		 * e o gerente pode fazer uso do sistema.*/
		if((bdUser == this.getUsuario()) && (bdPass == this.getSenha()))
		{
			checked = true;
		}
		checked = false;
	}
	
	//Continuar/terminar sapoha amanha...
}
