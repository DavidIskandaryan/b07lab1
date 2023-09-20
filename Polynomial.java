public class Polynomial{
	double [] coefficients;
	
	public Polynomial(){
		coefficients = new double [1];
	}
	public Polynomial(double [] passed){
		coefficients = passed;
	}	
	public Polynomial add(Polynomial other){
		double [] temp;
		if(other.coefficients.length >= coefficients.length){
			temp = new double[other.coefficients.length];
		}
		else{
			temp = new double[coefficients.length];
		}
		for(int i = 0;i < coefficients.length; i++){
			temp[i] += coefficients[i];
		}
		for(int j = 0;j < other.coefficients.length; j++){
			temp[j] += other.coefficients[j];
		}			
		Polynomial return_polynomial = new Polynomial(temp);
		return return_polynomial;
	}
	public double evaluate(double value){
		double sum = 0;
		double power = 1;
		for(int i = 0;i < coefficients.length; i++){
			power = 1;
			for(int j = 0;j < i; j++){
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
	
}