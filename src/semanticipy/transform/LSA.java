package semanticipy.transform;

import Jama.Matrix;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;
import cern.colt.matrix.linalg.SingularValueDecomposition;

public class LSA extends Transform{

	public LSA(double[][] matrix) {
		super(matrix);
	}
	
	public double[][] transform(){
		return transform(500);
	}
	public double[][] transform(int dimensions){
		int rows = matrix.length;
		int cols = matrix[0].length;

		

		if( dimensions <= rows){// #Its a valid reduction
			//pColt svd
			Algebra alg = new Algebra();
			DoubleMatrix2D coltMatrix = new DenseDoubleMatrix2D(matrix);
			SingularValueDecomposition coltSVD = new SingularValueDecomposition(coltMatrix);
			DoubleMatrix2D coltS = coltSVD.getS();
	        
			// reduce dimension
			for (int i =rows - dimensions ; i < coltS.toArray().length; i++) {
				coltS.set(i, i, 0);
			}
			
			//new matrix
			double newMatrix[][] = coltSVD.getU().zMult(coltSVD.getS(), null).zMult(alg.transpose(coltSVD.getV()), null).toArray();
			/*
			
			for (int i = 0; i < newMatrix.length; i++) {
				for (int j = 0; j < newMatrix[i].length; j++) {
					System.out.printf("%.3e " , newMatrix[i][j]);
				}
				System.out.println();
			}
			*/
			
			/*
			//jama svd
	        System.out.println("jamaSVD ...");
			Matrix jamaMatrix = new Matrix(matrix);
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
			*/
			
			
			return newMatrix;
		}else{
			System.out.printf("dimension reduction cannot be greater than %s",rows);
		}
		return null;
	}

}
