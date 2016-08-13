package sql;

public class CoffeObject {
	public String name;
	public Double preco;
	public Double taxa;
CoffeObject(String name, Double preco,Double taxa){
		this.name = name;
		this.preco = preco;
		this.taxa = taxa;
	}
	public String getName() {
	return name;
	}
	public Double getPreco() {
		return preco;
	}
	public Double getTaxa() {
		return taxa;
	}

}
