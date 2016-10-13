package semanticipy.transform;

public class LSA extends Transform{

	public LSA(float[][] matrix) {
		super(matrix);
	}
	
	public float[][] transform(){
		return transform(500);
	}
	public float[][] transform(int dimensions){
		/*""" Calculate SVD of objects matrix: U . SIGMA . VT = MATRIX 
	    Reduce the dimension of sigma by specified factor producing sigma'. 
	    Then dot product the matrices:  U . SIGMA' . VT = MATRIX'
		"""*/
		int rows = matrix.length;
		int cols = matrix[0].length;

		if( dimensions <= rows){// #Its a valid reduction

			//#Sigma comes out as a list rather than a matrix
			//u,sigma,vt = linalg.svd(self.matrix)
					//print "SIGMA",len(sigma)
					//#Dimension reduction, build SIGMA'
					//#revise
					//max_range = min(len(sigma),rows)
					//for index in xrange(rows - dimensions, max_range){
					//	sigma[index] = 0
					//}

					//#Reconstruct MATRIX'
			float transformed_matrix[][] = null ; //= dot(dot(u, linalg.diagsvd(sigma, len(self.matrix), len(vt))) ,vt)

			return transformed_matrix;
		}else{
			System.out.printf("dimension reduction cannot be greater than %s",rows);
		}
		return null;
	}

}
