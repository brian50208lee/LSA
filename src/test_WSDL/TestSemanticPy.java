package test_WSDL;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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
        String newpath = "LSA_similarity/";
        //#relation = open("LSA_similarity/","w")
        ArrayList<String> all_w = new ArrayList<String>(vectorSpace.get_word_vector().keySet());
        all_w.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		});
        //#print vectorSpace.print_word_vector()
        for(String k: all_w){
            if( k.equals("")){
                continue;
            }
            BufferedWriter fi = new BufferedWriter(new FileWriter(new File(newpath+k)));
            System.out.println("WORD: " + k);

            for(String k1 : all_w){
                String simi = String.format("%.2e",  (Math.round(vectorSpace.get_term_similarity(k,k1)*1000)/1000.0));
                fi.write(String.format("%s %s %s\n",k,k1,simi));
            }
            fi.close();
        }
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
