package semanticipy.porter_stemmer;

public class PorterStemmer {
	String b;
	int k;
	int k0;
	int j;
	public PorterStemmer(){
		this.b="";
		this.k=0;
		this.k0=0;
		this.j=0;
	}

	public String stem(String p, int i, int j) {
        this.b = p;
        this.k = j;
        this.k0 = i;
        if( k <= k0 + 1){
            return this.b;
        }
		step1ab();
		step1c();
		step2();
		step3();
		step4();
		step5();
		
		
		return b.substring(k0, k+1);
	}

	private void step5() {
		// TODO Auto-generated method stub
		
	}

	private void step4() {
		// TODO Auto-generated method stub
		
	}

	private void step3() {
		// TODO Auto-generated method stub
		
	}

	private void step2() {
        /*step2() maps double suffices to single ones.
        so -ization ( = -ize plus -ation) maps to -ize etc. note that the
        string before the suffix must give m() > 0.
        */
        if self.b[self.k - 1] == 'a':
            if self.ends("ational"):   self.r("ate")
            elif self.ends("tional"):  self.r("tion")
        elif self.b[self.k - 1] == 'c':
            if self.ends("enci"):      self.r("ence")
            elif self.ends("anci"):    self.r("ance")
        elif self.b[self.k - 1] == 'e':
            if self.ends("izer"):      self.r("ize")
        elif self.b[self.k - 1] == 'l':
            if self.ends("bli"):       self.r("ble") # --DEPARTURE--
            # To match the published algorithm, replace this phrase with
            #   if self.ends("abli"):      self.r("able")
            elif self.ends("alli"):    self.r("al")
            elif self.ends("entli"):   self.r("ent")
            elif self.ends("eli"):     self.r("e")
            elif self.ends("ousli"):   self.r("ous")
        elif self.b[self.k - 1] == 'o':
            if self.ends("ization"):   self.r("ize")
            elif self.ends("ation"):   self.r("ate")
            elif self.ends("ator"):    self.r("ate")
        elif self.b[self.k - 1] == 's':
            if self.ends("alism"):     self.r("al")
            elif self.ends("iveness"): self.r("ive")
            elif self.ends("fulness"): self.r("ful")
            elif self.ends("ousness"): self.r("ous")
        elif self.b[self.k - 1] == 't':
            if self.ends("aliti"):     self.r("al")
            elif self.ends("iviti"):   self.r("ive")
            elif self.ends("biliti"):  self.r("ble")
        elif self.b[self.k - 1] == 'g': # --DEPARTURE--
            if self.ends("logi"):      self.r("log")
        # To match the published algorithm, delete this phrase
		
	}

	private void step1c() {
        //"""step1c() turns terminal y to i when there is another vowel in the stem."""
        if (ends("y") && vowelinstem()){
            this.b = this.b.substring(0,this.k) + 'i' + this.b.substring(this.k+1,b.length()-1);
        }
		
	}

	private void step1ab() {
	       if( this.b.charAt(this.k) == 's'){
	            if( ends("sses"))
	                this.k = this.k - 2 ;
	            else if( ends("ies") )
	                setto("i");
	            else if(b.charAt(this.k - 1) != 's')
	                this.k = this.k - 1 ;
	            
	       }
	        if( ends("eed")  ){
	            if( m() > 0){
	                this.k = this.k - 1;
	            }
	        }
	        else if( (ends("ed") || ends("ing")) && vowelinstem()){
	            this.k = this.j ;
	            if(ends("at"))setto("ate");
	            else if(ends("bl"))setto("ble");
	            else if(ends("iz"))setto("ize");
	            else if(doublec(this.k)){
	                this.k = this.k - 1 ;
	                char ch = b.charAt(this.k);
	                if( ch == 'l' || ch == 's' || ch == 'z'){
	                    this.k = this.k + 1;
	                }
	            }
	            else if(m() == 1 && cvc(this.k)){
	                setto("e");
	            }
	        }
	}
	
    private boolean cvc(int i) {
        /*cvc(i) is TRUE <=> i-2,i-1,i has the form consonant - vowel - consonant
        and also if the second c is not w,x or y. this is used when trying to
        restore an e at the end of a short  e.g.

           cav(e), lov(e), hop(e), crim(e), but
           snow, box, tray.
        */
        if( i < (this.k0 + 2) || !cons(i) || cons(i-1) || !cons(i-2) ){
            return false;
        }
        char ch = this.b.charAt(i);
        if( ch == 'w' || ch == 'x' || ch == 'y'){
            return false;
        }
        return true;
	}

	private boolean doublec(int j) {
        //"""doublec(j) is TRUE <=> j,(j-1) contain a double consonant."""
        if( j < (this.k0 + 1)){
            return false;
        }
        if (this.b.charAt(j) != this.b.charAt(j-1)){
            return false;
        }
        return cons(j);
	}

	private boolean vowelinstem() {
        //"""vowelinstem() is TRUE <=> k0,...j contains a vowel"""
        for( int i = this.k0; i<= this.j+1 ; i++ ){
            if (!cons(i)){
                return true;
            }
        }
        return false;
	}

	private int m() {
    	/*
        """m() measures the number of consonant sequences between k0 and j.
        if c is a consonant sequence and v a vowel sequence, and <..>
        indicates arbitrary presence,

           <c><v>       gives 0
           <c>vc<v>     gives 1
           <c>vcvc<v>   gives 2
           <c>vcvcvc<v> gives 3
           ....
        """
        */
        int n = 0;
        int i = this.k0 ; 
        while(true){
            if( i > this.j){
                return n;
            }
            if( !cons(i)){
                break;
            }
            i = i + 1;
        }
        i = i + 1;
        while(true){
            while(true){
                if( i > this.j){
                    return n;
                }
                if( cons(i)){
                    break;
                }
                i = i + 1;
            }
            i = i + 1;
            n = n + 1;
            while(true){
                if( i > this.j){
                    return n;
                }
                if(!cons(i)){
                    break;
                }
                i = i + 1;
            }
            i = i + 1;
        }
	}

	private boolean cons(int i) {
        //"""cons(i) is TRUE <=> b[i] is a consonant."""
        if( this.b.charAt(i) == 'a' || this.b.charAt(i) == 'e' || 
        		this.b.charAt(i) == 'i' || this.b.charAt(i) == 'o' || 
        		this.b.charAt(i) == 'u'){
            return false;
        }
        if( this.b.charAt(i) == 'y'){
            if( i == this.k0)
                return true;
            else
                return !cons(i - 1);
        }
        return true;
	}

	private void setto(String s) {
        // setto(s) sets (j+1),...k to the characters in the string s, readjusting k.
        int length = s.length();
        this.b = this.b.substring(0,this.j+1) + s + this.b.substring(this.j+length+1,b.length()-1);
        this.k = this.j + length;
	}

	private boolean ends(String s){
        int length = s.length();
        if( s.charAt(length - 1) != this.b.charAt(this.k)){
            return false;
        }
        if( length > (this.k - this.k0 + 1) ){
            return false;
        }
        if( !this.b.substring(this.k-length+1,this.k+1).equals(s)){
            return false;
        }
        this.j = this.k - length;
        return true;
    }
}
