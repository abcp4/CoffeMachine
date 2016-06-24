package coffeMachinePackge;
public class Container
{
	private String insumo; //Nome do insumo
	private float quantidade = 0; //Independe do tipo de insumo(solido ou liquido)
	private String medida; //Quilos ou litros
	private int id;
	private float capacidade; //Define o maximo que um container pode armazenar;
	
	//Construtor
	public Container(int id, float capacidade)
	{
		this.id = id;
		this.capacidade = capacidade;
	}
	
	/*Recebe um nome para o insumo, e um inteiro que representa o tipo(liquido/solido)
	* a entrada 1 representa um solido e a medida é definida em Kg, zero representa
	* liquidos medidos em Lt*/
	public void cadastrarInsumo(String nome, int tipo)
	{
		this.insumo = nome;
		if(tipo == 1)
		{
			this.medida = "Kg";
		}
		else
		{
			this.medida = "Lt"; 
		}
	}
	
	
	/*Getrs e Setrs*/
	public float getQuantidade() //Importante. Possui a quantidade restante armazenada no container
	{
		return quantidade;
	}
	
	public void setQuantidade(float quantidade)
	{
		this.quantidade = quantidade;
	}
	
	public String getInsumo() {
		return insumo;
	}

	public void setInsumo(String insumo) {
		this.insumo = insumo;
	}

	public String getMedida() {
		return medida;
	}

	public void setMedida(String medida) {
		this.medida = medida;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getCapacidade() {
		return capacidade;
	}

	public void setCapacidade(float capacidade) {
		this.capacidade = capacidade;
	}

	
	public void reabastecer(Gerente g, Container c)
	{
		if(g.isChecked() == true) //Só pode ser executado por um gerente validado
		{
			c.setQuantidade(this.capacidade);
		}
	}
	
	public void limpar(Container c, Gerente g)
	{
		if(g.isChecked() == true) //Só pode ser executado por um gerente validado
		{
			c.setQuantidade(0);
			c.setId(-1);
			c.setInsumo("Não cadastrado");
		}
	}
	

}