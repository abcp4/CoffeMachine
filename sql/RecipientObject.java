package sql;

public class RecipientObject {
public Double quantidade;
public String name;
RecipientObject(String name,Double quantidade){
	this.name = name;
	this.quantidade = quantidade;		
}
public Double getQuantidade() {
	return quantidade;
}
public void setQuantidade(Double quantidade) {
	this.quantidade = quantidade;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
}
