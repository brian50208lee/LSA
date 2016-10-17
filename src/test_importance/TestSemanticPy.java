package test_importance;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
	public static void test_similarity() throws IOException{
		VectorSpace vectorSpace = new VectorSpace(documents);
		
		System.out.println("output ...");
		String newpath = "LSA_importance/";
		BufferedWriter dw = new BufferedWriter(new FileWriter(new File(newpath + "doc_index")));
		int ind = 0;
        for( String d :vector_index_to_doc_mapping){
            dw.write(d+" "+ind+"\n");
            ind+=1;
        }
        dw.close();
        
        BufferedWriter tw = new BufferedWriter(new FileWriter(new File(newpath + "word_index")));
        ind=0;
        for(String t : vectorSpace.get_vector_index_to_keyword_mapping().keySet()){
            tw.write(t+" "+vectorSpace.get_vector_index_to_keyword_mapping().get(t)+"\n");
            ind+=1;
        }
        tw.close();
        
        BufferedWriter vw = new BufferedWriter(new FileWriter(new File(newpath + "TF")));
        for(double v[] : vectorSpace.get_doc_vector()){
            for(double n : v){
            	//System.out.println(n);     
                vw.write(n+" ");
            }
            vw.write("\n");
        }
        vw.close();
        
        
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
