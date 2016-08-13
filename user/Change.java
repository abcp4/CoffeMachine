package user;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Change {
    private int [] available ;
    private int [] new_change;
    private double[] money ;
    public boolean isAvailable = false;
    DecimalFormat df = new DecimalFormat("#.##");
   
    public Change(int[] available, double[] money){
        this.available = available;
        this.money = money;
        new_change = new int[available.length];
        for(int i = 0; i < new_change.length; i++)
        {
            new_change[i] = 0;
        }
    }
   
   
    public boolean getIsAvailable()
    {
      return isAvailable;
    }
 
 
    public double getTotal()
    {
        double total = 0.0;
      	double finalTotal = 0.0;
        for(int i = 0; i < new_change.length; i ++)
        {
            total += new_change[i]*money[i];
        }
     
       
        //finalTotal = Math.round(total*100.0)/100.0;
 		return total;
 
    }
   
    public void computeNewChange(Double change)
    {  
        //Limitar em duas casas decimais
         
     
        for(int i = 0; i < available.length; i++)
        {
            //Verifica se a quantidade de dinheiro disponivel nao eh nula
            if(available[i] != 0)
            {	
            	System.out.println("erro embaixo com change:"+change);
                //change = Double.parseDouble(df.format(change));
            	change = truncateDecimal(change,2);
                System.out.println("erro acima");
                int result = (int) (change/money[i]);
                if(result != 0)
                {
                    //Verifica se tem a quantidade suficiente para extrair do caixa
                    if(result <= available[i])
                    {
                        new_change[i] += result;
                        change -= new_change[i]*money[i];
                        available[i] -= result;
                    }
                    //Caso nao tenha o suficiente, extraia o disponivel
                    else
                    {
                        new_change[i] = available[i];
                        change -= available[i]*money[i];
                        available[i] -= available[i];
                    }
                   
                }
            }
        }
     
     
     
      //System.out.println(change);
      //change = Double.valueOf(df.format(change));
      double value = money[available.length - 1] - change;
      //value = Double.parseDouble(df.format(value));
      value = truncateDecimal(value,2);
   
      //Calcular centavos restante
      if(available[available.length - 1] != 0)
        {
         
          // diferenca maxima de 2 centavos
          if(value >= 0.01 && value <= 0.03){
           
              int result =(int) Math.round( (change/money[available.length - 1]));
              if(result <= available[available.length - 1])
              {
                  //result = result == 0? 1: result;
                  new_change[available.length - 1] += result;
                  available[available.length - 1] -= result;
                  change -= new_change[available.length - 1]*money[available.length - 1];
 
              }
              else
              {
                  new_change[available.length - 1] = available[available.length - 1];
                  change -= available[available.length - 1]*money[available.length - 1];
                  available[available.length - 1] -= available[available.length - 1];
              }
          }
        }
     
     
      if(change <= 0.03)
        isAvailable = true;
        //return new_change;
    }
 
    public int [] getNewChange()
    {
        return new_change;
    }
    
    public ArrayList<Integer> obterTroco()
    {
    	ArrayList<Integer> legal = new ArrayList<>();
    	
    	for(int i = 0; i < 4; i++)
    	{
    		legal.add(new_change[i]);
    	}
        return legal;
        
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