package sql;

import java.util.ArrayList;

public class Tester {

	public static void main(String[] args) {

		CoffeSQL cof= new CoffeSQL();
		cof.createTable();
		cof.insertCoffe("cafe1", 3.03,4.50);
		cof.insertCoffe("cafe2", 2.03,2.50);
		/*cof.removeCoffe("cafe2");
		ArrayList<CoffeObject> coffes2 = cof.getCoffes();
		for (CoffeObject temp : coffes2) {
			System.out.println(temp.name);
		}*/
		
		ReceitaSQL rec = new ReceitaSQL();
		rec.createTable();
		rec.insertIngredient("cafe1", "agua",2.3,"grama");
		rec.insertIngredient("cafe1", "cafe",3.5,"grama");
		rec.insertIngredient("deathwish", "vodka",4.5,"ml");
		
		ArrayList<String> ingredientes = rec.getIngredients("cafe1");
		for(String temp : ingredientes){
			System.out.println(temp);
		}
		
		RecipienteSQL rep = new RecipienteSQL();
		rep.createTable();
		rep.insertIngredient("agua", 50.0);
		rep.insertIngredient("cafe", 12.0);
		//System.out.println(rep.getQuantidade("cafe"));
		//ArrayList<String> recipientes = rep.getRecipientes();
		//for(String temp : recipientes){
		//	System.out.println(temp);
		//}
		//System.out.println(rep.getName(1));
		rep.updateQuantidade(1, 40.0);
		System.out.println(rep.getQuantidade(1));
		
			
		TransactionSQL tr = new TransactionSQL();
		tr.createTable();
		int[] a = {9,9,9,9};
		tr.insertTransaction("Admin","deposito", a);
		//int[] a = {9,9,9,9};
		BalancoSQL bal = new BalancoSQL();
		Double total = a[0]*10+a[1]*5+a[2]*2+a[3]*0.5;
		bal.createTable();
		bal.insertBalanco(total, a);
		//int[] b = {8,8,8,8};
		//Double total2 = b[0]*10+b[1]*5+b[2]*2+b[3]*0.5;
		//bal.updateBalanco(total2, b);
	}

}
