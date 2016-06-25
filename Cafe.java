package coffeMachinePackge;
import java.util.ArrayList;
import java.util.Scanner;

/* EU SEI QUE T� UMA PORCARIA!
 * AINDA VOU LIMPAR E ORGANIZAR ISSO. AL�M DE FAZER ALGUMAS ALTERA��ES*/

/*EU SEI QUE VAMOS USAR GUI!!! Os SysOut s�o apenas pra ajudar a entender o que o codigo faz*/
public class Cafe
{
	ArrayList<Container> insumosDisponiveis = new ArrayList<Container>(); //recebe os containers de ingredientes
	ArrayList<String> ingredientes = new ArrayList<String>(); //recebe os ingredientes(nomes/strings) do caf�
	float[] dosagens = new float[5]; //recebe as dosagens de cada ingrediente.
	String nome; //Recebe o nome do caf�
	Scanner leitor = new Scanner(System.in); //� meio obvio oq isso faz -_-
	
	/*Arranjo t�cnico, para receber os containers de insumos*/
	public Cafe(ArrayList<Container> insumos)
	{
		this.insumosDisponiveis = insumos;
	}
	
	/*Mais uma vez o nome do m�todo � bem intuitivo...*/
	public void cadastraCafe(String nomeDoCafe)
	{
		int i = 0; //Contador
		int numeroDeIngredientes = 0; //Guarda o numero de ingredientes usados no caf�
		System.out.println("Insira o numero de ingredientes usados(no maximo 5)");
		numeroDeIngredientes = leitor.nextInt();
		boolean ingredientesChecked = true; // True, se os ingredientes usados existem nos containers
		boolean quantidadesChecked = true; // true, se existir o suficiente de cada ingrediente, para o preparo do caf�
		
		for(i = 0; i < numeroDeIngredientes; i++) // l�/recebe o nome de cada ingrediente
		{
			System.out.println("Insira o nome do " + (i+1) + "� ingrediente.");
			ingredientes.add(leitor.nextLine());
		}
		
		for(i = 0; i < numeroDeIngredientes; i++) // recebe a dosagem de cada ingrediente
		{
			System.out.println("Insia a quantidade de " + ingredientes.get(i));
			dosagens[i] = leitor.nextFloat();
		}
		
		for(i = 0; i < insumosDisponiveis.size(); i++) // Verifica se a maquina possui os ingredientes
		{
			
			if(insumosDisponiveis.contains(ingredientes.get(i)) == false)
			{
				System.out.println("O ingrediente "+ ingredientes.get(i)+ " n�o esta disponivel.");
				ingredientesChecked = false;
			}
		}
		
		for(i = 0; i < insumosDisponiveis.size(); i++) // verifica se possui o suficiente para preparar o caf�
		{
			if(insumosDisponiveis.get(i).getQuantidade() < dosagens[i])
			{
				System.out.println("n�o h� " + ingredientes.get(i)+ "suficiente");
				quantidadesChecked = false;
			}
		}
		
		if((quantidadesChecked == true && ingredientesChecked == true)) //testa se o cafe pode ser cadastrado
		{
			System.out.println(nome + " Cadastrado com sucesso.");
		}
	}
}
