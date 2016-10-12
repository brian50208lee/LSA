package semanticpy.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.sun.xml.internal.ws.util.StringUtils;

import semanticipy.porter_stemmer.PorterStemmer;

public class Parser {
	PorterStemmer stemmer ;
	ArrayList<String> stopwords = new ArrayList<String>();
	String STOP_WORDS_FILE = "./data/english.stop";
	public Parser(){
		this(null);
	}
	public Parser(BufferedReader stopwords_io_stream) {
		stemmer = new PorterStemmer();
		//load stop words file stream
		if (stopwords_io_stream == null) {
			try {
				stopwords_io_stream = new BufferedReader(new FileReader(new File(STOP_WORDS_FILE)));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
		}
		
		//read stop words 
		for (Object line : stopwords_io_stream.lines().toArray()) {
			stopwords.add(line.toString());
		}
	}
	
	
	public ArrayList<String> tokenize_and_remove_stop_words(ArrayList<String> document_list){
		if (document_list == null || document_list.size() ==0) {
			return new ArrayList<String>();
		}
		
		//ex. ["a" , "b" , "c"] -> "a b c"
		StringBuilder vocabulary_string = new StringBuilder("");
		for (String str : document_list) {
			vocabulary_string.append(str + " ");
		}
		vocabulary_string.deleteCharAt(vocabulary_string.length()-1);
		
		
		ArrayList<String> tokenised_vocabulary_list = _tokenise(vocabulary_string.toString());
				
		
		return null;
	}

	private ArrayList<String> _tokenise(String string) {
		//System.out.println(string);
		string = _clean(string);
		//System.out.println(string);
		String words[] = string.split(" ");
		ArrayList<String> strList = new ArrayList<String>();
		for (String word : words) {
			strList.add(stemmer.stem(word,0,word.length()-1));
		}
		return strList;
	}
	private String _clean(String string) {
		string = string.replaceAll("\\.", " ");
		string = string.replaceAll("\\s+", " ");
		string = string.toLowerCase();
		

		return string;
	}
}
