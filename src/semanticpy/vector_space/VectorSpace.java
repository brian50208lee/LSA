package semanticpy.vector_space;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import semanticipy.transform.*;
import semanticpy.parser.Parser;;

public class VectorSpace {
	Parser parser;
	float collection_of_document_term_vectors[][];
	HashMap<String, Integer> vector_index_to_keyword_mapping;
	float collection_of_term_document_vectors[][];
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
		
		
		float matrix[][] = new float[documents.size()][0];
		for (int i = 0; i < documents.size(); i++) {
			matrix[i] = _make_vector(documents.get(i));
		}


		
		//TFIDF
		TFIDF tfidf = new TFIDF(matrix);
		matrix = tfidf.transform();
		
		
		//LSA
		LSA lsa = new LSA(matrix);
		matrix = lsa.transform();
		
		for (int i = 0; i < matrix[0].length; i++) {
			System.out.print(matrix[0][i]+" ");
		}
		System.out.println();
		
		/*
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j]+" ");
			}
			System.out.println();
		}
		*/
		collection_of_document_term_vectors = matrix;
		collection_of_term_document_vectors = _build_term_document_vector();
	}
	private float[][] _build_term_document_vector() {
		float word_vectors[][] = new float[collection_of_document_term_vectors[0].length][0];
		
		ArrayList<ArrayList<Float>> list = new ArrayList<ArrayList<Float>>();
		for(int i = 0 ; i < collection_of_document_term_vectors[0].length ; i++){
			list.add(new ArrayList<Float>());
		}
		for(float v[] : collection_of_document_term_vectors){
			for(int i = 0 ; i < v.length ; i++){
				list.get(i).add(v[i]);
			}
		}
		//convert to matrix[][]
		for (int i = 0 ; i < list.size() ; i++) {
			word_vectors[i] = new float[list.get(i).size()];
			for (int j = 0; j < list.get(i).size(); j++) {
				word_vectors[i][j] = list.get(i).get(j);
			}
		}
		
		return word_vectors;
	}

	private float[] _make_vector(String word_string) {
		//""" @pre: unique(vectorIndex) """
		
		//System.out.println(word_string);

		float vector[] = new float[vector_index_to_keyword_mapping.size()];
		ArrayList<String> word_list = parser.tokenize_and_remove_stop_words(new ArrayList<String>(Arrays.asList(word_string.split(" "))));

		
		for(String word : word_list){
			vector[vector_index_to_keyword_mapping.get(word)] += 1; //#Use simple Term Count Model
		}

		
		return vector;
	}

	public void print_matrix(){};
	public float[][] get_doc_vector(){
		return collection_of_document_term_vectors;
	};
	private float _cosine(){
		return 0;
	}
	private HashMap<String, Integer> _get_vector_key_index(ArrayList<String> document_list){
		ArrayList<String> vocabulary_list = parser.tokenize_and_remove_stop_words(document_list);
		//for(String s :vocabulary_list)System.out.print(s+",");
		System.out.println();
		ArrayList<String> unique_vocabulary_list = _remove_duplicates(vocabulary_list);
		unique_vocabulary_list.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		});
		//for(String s :unique_vocabulary_list)System.out.print(s+"\n");
		//System.out.println("\n"+vocabulary_list.size());
		//System.out.println(unique_vocabulary_list.size());
		
		HashMap<String, Integer> vector_index = new HashMap<String ,Integer>();
		int offset = 0;
		for(String word : unique_vocabulary_list){
			vector_index.put(word, offset);
			offset += 1 ;
		}
		
		return vector_index;
	}

	private ArrayList<String> _remove_duplicates(ArrayList<String> list) {
		Set set = new HashSet<String>();
		ArrayList<String> rmdList = new ArrayList<String>();
		for (String s : list){
			if (!set.contains(s)) {
				rmdList.add(s);
				set.add(s);
			}
		}
		return  rmdList ;
	}

	public HashMap<String, Integer> get_vector_index_to_keyword_mapping() {
		return vector_index_to_keyword_mapping;
	}
	
	public void print_term_similarity(String word1,String word2){
		System.out.println(vector_index_to_keyword_mapping.get(word1));
		System.out.println(vector_index_to_keyword_mapping.get(word2));
		int i1=vector_index_to_keyword_mapping.get(word1);
		int i2=vector_index_to_keyword_mapping.get(word2);
		System.out.println(_cosine(collection_of_term_document_vectors[i1],collection_of_term_document_vectors[i2]));
		//print "End"
	}

	private double _cosine(float[] vector1, float[] vector2) {
		double dot = 0;
		for(int i = 0 ; i < vector1.length; i++ ){
			dot += vector1[i]*vector2[i];
		}
		
		double norm1 = 0;
		for(int i = 0 ; i < vector1.length; i++ ){
			norm1 += vector1[i]*vector1[i];
		}
		norm1 = Math.sqrt(norm1);
		
		double norm2 = 0;
		for(int i = 0 ; i < vector2.length; i++ ){
			norm2 += vector2[i]*vector2[i];
		}
		norm2 = Math.sqrt(norm2);
		
		
		return dot / (norm1*norm2);
	}
}
