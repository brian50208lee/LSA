package semanticipy.transform;

public class TFIDF extends Transform{
	int document_total;
	public TFIDF(float[][] matrix) {
		super(matrix);
		document_total = matrix.length;
	}
	
	public float[][] transform(){
        int rows = matrix.length;
        int cols = matrix[0].length;
        float transformed_matrix[][] = matrix.clone();

        for(int row = 0 ; row < rows ; row++ ){ //#For each document

            float word_total = 0;
            for (int i = 0 ; i < matrix[row].length; i++) {
            	word_total += matrix[row][i];
			}

            for(int col = 0 ; col < cols ; col++ ){//#For each term

                if( transformed_matrix[row][col] != 0){
                    transformed_matrix[row][col] = _tf_idf(row, col, word_total);
                }
            }
        }
        return transformed_matrix;
	}

	private float _tf_idf(int row, int col, float word_total) {
		float term_frequency = matrix[row][col] / word_total;
		float inverse_document_frequency = (float)Math.log(Math.abs(document_total /_get_term_document_occurences(col)));
		return term_frequency*inverse_document_frequency;
	}

	private float _get_term_document_occurences(int col) {
        //""" Find how many documents a term occurs in"""

        float term_document_occurrences = 0;

        int rows = matrix.length;
        int cols = matrix[0].length;

        for(int n = 0 ; n < rows ; n ++){
            if( matrix[n][col] > 0){// #Term appears in document
                term_document_occurrences +=1;
            }
        }
        return term_document_occurrences;
		
	}

}
