package semanticipy.transform;

import Jama.Matrix;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.SingularValueDecomposition;

public class LSA extends Transform{

	public LSA(float[][] matrix) {
		super(matrix);
	}
	
	public float[][] transform(){
		return transform(500);
	}
	public float[][] transform(int dimensions){
		int rows = matrix.length;
		int cols = matrix[0].length;

		

		if( dimensions <= rows){// #Its a valid reduction
			//convert to double
			double dbMatrix[][] = new double[matrix.length][matrix[0].length];
			for (int i = 0; i < dbMatrix.length; i++) {
				for (int j = 0; j < dbMatrix[i].length; j++) {
					dbMatrix[i][j] = matrix[i][j];
					//System.out.print(dbMatrix[i][j] +" ");
				}
				//System.out.println();
			}		
			

	        
	        
	        
			//svd
			long time1, time2;
			
			//pColt
			DoubleMatrix2D coltMatrix = new DenseDoubleMatrix2D(dbMatrix);
			SingularValueDecomposition coltSVD = new SingularValueDecomposition(coltMatrix);
			
			
			double coltS[][] = coltSVD.getS().toArray();
	        
			// reduce dimension
			for (int i =rows - dimensions ; i < coltS.length; i++) {
				coltS[i][i]=0;
			}
			System.out.println("lsa vt");
	        double a[][] = coltSVD.getU().toArray();
	        for (int i = 0; i < a.length; i++) {
				for (int j = 0; j < a[i].length; j++) {
					//if (i == j ) {
						System.out.printf("%.3e ",a[i][j]);
					//}
					
				}
				System.out.println();
			}
			
			//jama svd
	        System.out.println("jamaSVD ...");
	        time1 =System.currentTimeMillis();
			Matrix jamaMatrix = new Matrix(dbMatrix);
			Jama.SingularValueDecomposition jamaSVD = jamaMatrix.svd();
			Matrix U = jamaSVD.getU();

			Matrix S = jamaSVD.getS();
			Matrix V = jamaSVD.getV();
			
			// reduce dimension
			for (int i =rows - dimensions ; i < S.getArray().length; i++) {
				S.getArray()[i][i]=0;
			}
			
			//new matrix
			double newMatrix[][] = U.times(S).times(V).getArray();
			float transformed_matrix[][] = new float[newMatrix.length][newMatrix[0].length] ;
			
			//convert to float
			for (int i = 0; i < newMatrix.length; i++) {
				for (int j = 0; j < newMatrix[i].length; j++) {
					transformed_matrix[i][j] = (float) newMatrix[i][j];
				}
			}
			time2 = System.currentTimeMillis();
			System.out.printf("done svd in %dms\n",time2-time1);
			
			/*
			System.out.println("after lsa");
			for (int i = 0; i < transformed_matrix.length; i++) {
				for (int j = 0; j < transformed_matrix[i].length; j++) {
					System.out.print(transformed_matrix[i][j]+" ");
					
				}
				System.out.println();
			}
			*/
			return transformed_matrix;
		}else{
			System.out.printf("dimension reduction cannot be greater than %s",rows);
		}
		return null;
	}

}
