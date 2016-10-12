package test_importance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import com.sun.tracing.dtrace.ProviderAttributes;

import semanticpy.vector_space.VectorSpace;

public class TestSemanticPy {
	private  static ArrayList<String> vector_index_to_doc_mapping = new ArrayList<String>();
	private  static ArrayList<String> documents;
	
	public static void setUp() throws IOException{
        ArrayList<String> all_docs= new ArrayList<String>();
        BufferedWriter output = new BufferedWriter(new FileWriter(new File("LSA_term_similarity")));
        

        String path = "allKeyWords(remove duplicated)/";
        int cnt=0;
        for(String f : new File(path).list()){
        	vector_index_to_doc_mapping.add(f);
        	cnt+=1;
        	StringBuilder doc = new StringBuilder("");
        	BufferedReader fi = new BufferedReader(new FileReader(new File(path + f)));
        	Object lines[] =fi.lines().toArray();
        	for (Object l : lines) {
				String ls = l.toString().replace("\n", "");
				doc.append(ls);
				doc.append(" ");
			}
        	doc = doc.deleteCharAt(doc.length()-1);
        	all_docs.add(doc.toString());
        	//System.out.println(doc);
        }
        
        documents = all_docs;
		
	}
	public static void test_similarity(){
		VectorSpace vectorSpace = new VectorSpace(documents);
		System.out.println();
	
	}
	
	

	public static void main(String[] args) {
		try {
			setUp();
			test_similarity();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
