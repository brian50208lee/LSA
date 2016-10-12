package semanticpy.vector_space;

import java.util.ArrayList;
import java.util.HashMap;

import com.sun.org.apache.bcel.internal.util.ClassLoader;

import semanticipy.transform.*;
import semanticpy.parser.Parser;;

public class VectorSpace {
	Parser parser;
	float collecttion_of_document_term_vectors[];
	HashMap<String, Integer> vector_index_to_keyword_mapping[];
	float collection_of_term_document_vectors[];
	public VectorSpace(ArrayList<String> documents) {
		this(documents,new Class[]{TFIDF.class,LSA.class});
	}
	
	public VectorSpace(ArrayList<String> documents , Class transforms[]) {
		parser = new Parser();
		if(documents.size() > 0 )
			_build(documents , transforms);
	}
	private void _build(ArrayList<String> documents, Class[] transforms){
		vector_index_to_keyword_mapping = _get_vector_key_index(documents);
		/*unimplement*/

	}
	public void print_matrix(){};
	public void get_vector_index_to_key_word_mapping(){};
	public void get_doc_vector(){};
	private float _cosine(){
		return 0;
	}
	private HashMap<String, Integer>[] _get_vector_key_index(ArrayList<String> document_list){
		ArrayList<String> vocabulary_list = parser.tokenize_and_remove_stop_words(document_list);
		
		return null;
	}
}
