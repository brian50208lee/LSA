package semanticpy.vector_space;

import com.sun.org.apache.bcel.internal.util.ClassLoader;

import semanticipy.transform.*;
import semanticpy.parser.Parser;;

public class VectorSpace {
	Parser parser;
	float collecttion_of_document_term_vectors[];
	String vector_index_to_keyword_mapping[];
	float collection_of_term_document_vectors[];
	public VectorSpace(String str[]) {
		
		
		_build();
	}
	private void _build(){
		
	}
	public void print_matrix(){};
	public void get_vector_index_to_key_word_mapping(){};
	public void get_doc_vector(){};
	private float _cosine(){
		return 0;
	}
	private int _get_vector_key_index(String str){
		return 0;
	}
}
