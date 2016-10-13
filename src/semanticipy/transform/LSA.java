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
				}
			}
			
			
			
	        
	        
	        
			//svd
			long time1, time2;
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

			return transformed_matrix;
		}else{
			System.out.printf("dimension reduction cannot be greater than %s",rows);
		}
		return null;
	}

}
