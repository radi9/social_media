import java.io.IOException;

public class Matrix{
	
	static double[] hub; 
	static double[] ath; 
	static double[] hub_ath;
	
	public  Matrix(double[][] input){
		
		double[][] a = input ;
		double[][] indegree = new double[a.length][1];
		for(int i=0;i<a.length;i++){
			for(int j=0;j<a.length;j++){
				indegree[i][0] = indegree[i][0] + a[j][i];
			}
		} 
		double[][] outdegree = new double[a.length][1];
		for(int i=0;i<a.length;i++){
			//for(int j=0;j<a.length;j++){
				//outdegree[i][0] = outdegree[i][0] + a[i][j];
			//}
			outdegree[i][0] = 1;
		} 
		double[][] hub2 ;
		hub2 = multiply(a ,multiply(reversal(a),indegree));
		//print(hub2);
		hub = new double[hub2.length];
		for(int i=0;i<hub2.length;i++){
			hub[i]=hub2[i][0];
			//System.out.println(hub[i]/137);
		}
		double[][] ath2;
		ath2 = multiply(reversal(a) ,multiply(a,outdegree));
		//print(ath2);
		ath= new double[ath2.length];	
		for(int i=0;i<ath2.length;i++){
			ath[i]=ath2[i][0];
			//System.out.println(ath[i]/38);
		}
		hub_ath = new double[a.length];	
		for(int i = 0;i<hub_ath.length;i++){
			hub_ath[i] = (hub[i]+ath[i])/2; 
		}
		//for(int i=0;i<hub_ath.length;i++){
		//	System.out.println(hub_ath[i]);
		//}
			
	}
	
	
	/*matrix(double[][] input){
		double[][] a = new double[][]{
			 { 0, 0, 1 },
			 { 0, 0, 1 },
			 { 0, 0, 0 }		
		};
		double[][] a = input ;
		double[][] b = new double[a.length][1];
		for(int i=0;i<a.length;i++){
			b[i][0]=1;
		} 
		double[][] hub2 ;
		hub2 = multiply(a ,multiply(reversal(a),b));
		print(hub2);
		hub = new double[hub2.length];
		for(int i=0;i<hub2.length;i++){
			hub[i]=hub2[i][0];
			System.out.println(hub[i]);
		}	
	}*/
	
	public static double[][] multiply(double a[][], double b[][]) {
		   
		  int aRows = a.length,
		      aColumns = a[0].length,
		      bRows = b.length,
		      bColumns = b[0].length;
		   
		  if ( aColumns != bRows ) {
		    throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
		  }
		   
		  double[][] resultant = new double[aRows][bColumns];
		   
		  for(int i = 0; i < aRows; i++) { // aRow
		    for(int j = 0; j < bColumns; j++) { // bColumn
		      for(int k = 0; k < aColumns; k++) { // aColumn
		        resultant[i][j] += a[i][k] * b[k][j];
		      }
		    }  
		  }
		   
		  return resultant;
	}

	public static void print(double[][] a){
		for(int row =0 ; row < a.length; ++row){
			 for(int column =0; column<a[row].length;++column){
			    System.out.print(a[row][column]+" ");
			 }
			 System.out.println();
		}
		 System.out.println("==========");
	}
	
	public static  double[][] reversal(double a[][]){
		//if(a.length!=a[0].length){return a;}
		double b[][] = new double[a[0].length][a.length];
		for(int i =0;i<b[0].length;i++ ){
			for(int j =0;j<b.length;j++ ){
				b[j][i]=a[i][j];
			}
		}					
		return b;	
	}
	public static double[] get_hub(){
		
		return hub;
	}
	public static double[] get_ath(){
		
		return ath;
	}
	public static double[] get_hub_ath(){
		
		return hub_ath;
	}
}