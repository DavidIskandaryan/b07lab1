import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.Scanner;
public class Polynomial{
	double [] coefficients;
	int [] exponents;
	
	
	public Polynomial(){
		coefficients = new double [0];
		exponents = new int [0];
	}
	public Polynomial(File file){
		try{
			FileReader fr = new FileReader(file);
			BufferedReader b = new BufferedReader(fr);
			String read_poly = b.readLine();
			read_poly = read_poly.replaceAll("-","\\+-");
			String [] individual=read_poly.split("\\+");
			double [] temp_coefficients = new double[individual.length];
			int [] temp_exponents = new int[individual.length];		
			for(int i = 0;i < temp_coefficients.length; i++){
				if(individual[i].contains("x")){
					String [] temp_string = individual[i].split("x");
					temp_coefficients[i] = Double.parseDouble(temp_string[0]);
					temp_exponents[i] = Integer.parseInt(temp_string[1]);
				}
				else{
					temp_coefficients[i] = Double.parseDouble(individual[i]); 
				}
			}
			coefficients = temp_coefficients;
			exponents = temp_exponents;
			b.close();			
		} 
		catch (Exception ex){
			System.out.println("Error: " + ex);
		}		
	}	
	public Polynomial(double [] passed, int [] ex){
		coefficients = passed;
		exponents = ex;
	}
	public int search_index(int [] ex, int n){
		for(int i=0; i<ex.length; i++) 
			if(ex[i] == n){
				return i;
			}
		return -1;
	}	
	public Polynomial add(Polynomial other){
		int new_length = other.exponents.length;
		for(int i = 0;i < exponents.length; i++){
			if(search_index(other.exponents, exponents[i]) == -1){
				new_length +=1;
			}
		}
		int [] temp_exponents = new int[new_length];
		double [] temp_coefficients = new double[new_length];
		boolean found = false;
		for(int m = 0;m < new_length; m++){
			temp_exponents[m] = -1;
		}
		for(int j = 0;j < new_length; j++){
			found = false;
			while(found == false){
				for(int n = 0;n < coefficients.length; n++){
					if(search_index(temp_exponents, exponents[n]) == -1){
						temp_exponents[j] = exponents[n];
						found = true;
					}
				}
				if(found == false){
					for(int k = 0;k < other.coefficients.length; k++){
						if(search_index(temp_exponents, other.exponents[k]) == -1){
							temp_exponents[j] = other.exponents[k];
							found = true;
						}
					}					
				}				
			}
			if(search_index(exponents, temp_exponents[j]) != -1){
				temp_coefficients[j] += coefficients[search_index(exponents, temp_exponents[j])];
			}
			if(search_index(other.exponents, temp_exponents[j]) != -1){
				temp_coefficients[j] += other.coefficients[search_index(other.exponents, temp_exponents[j])];
			}			
		}						
		Polynomial return_polynomial = new Polynomial(temp_coefficients, temp_exponents);
		return return_polynomial;
	}
	public double evaluate(double value){
		double sum = 0;
		double power = 1;
		for(int i = 0;i < exponents.length; i++){
			power = 1;
			for(int j = 0;j < exponents[i]; j++){
				power *= value;
			}				
			sum += coefficients[i]*power;
		}
		return sum;
	}
	public boolean hasRoot(double value){
		double result = evaluate(value);
		if(result == 0){
			return true;
		}
		else{
			return false;
		}
	}	
	public Polynomial multiply(Polynomial other){
		Polynomial return_polynomial = new Polynomial();
		for(int i = 0;i < exponents.length; i++){
			for(int j = 0;j < other.exponents.length; j++){
				double [] temp_coefficients = new double[1];
				int [] temp_exponents = new int[1];
				temp_coefficients[0] = coefficients[i] * other.coefficients[j];
				temp_exponents[0] = exponents[i] + other.exponents[j];		
				Polynomial add_polynomial = new Polynomial(temp_coefficients, temp_exponents);				
				return_polynomial = return_polynomial.add(add_polynomial);
			}
		}
		return return_polynomial;
	}	
	public void saveToFile(String file_name){
		try{	
			PrintStream file = new PrintStream(file_name);
			String temp_string = "";
			for(int i = 0;i < exponents.length;i++){
				if(exponents[i] == 0){
					temp_string = temp_string.concat("+" + String.format("%.1f",coefficients[i]));				
				}
				else{
					temp_string = temp_string.concat("+" + String.format("%.1f",coefficients[i]) + "x" + exponents[i]);
				}
			}
			temp_string = temp_string.replaceFirst("\\+", "");
			temp_string = temp_string.replaceAll("\\+-","-");
			file.println(temp_string);
			file.close();			
		} 
		catch (Exception ex) {
			System.out.println("Error: " + ex);
		}			
	}
}