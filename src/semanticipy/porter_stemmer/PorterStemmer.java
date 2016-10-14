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
		if (p.equals("geographic")) {

			//System.out.printf("#   %-30s",p );
		}
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
		//System.out.println(""+b.substring(k0, k+1));
		return b.substring(k0, k+1);
	}

	private void step5() {
        /*"""step5() removes a final -e if m() > 1, and changes -ll to -l if
        m() > 1.
        """*/
		
        this.j = this.k;
        if( this.b.charAt(this.k) == 'e'){
            int a = this.m();
            if( a > 1 || (a == 1 &&  !cvc(this.k-1))){
                this.k = this.k - 1 ;
            }
        }
        if( this.b.charAt(this.k) == 'l' && doublec(this.k) && m() > 1){
            this.k = this.k -1 ;
        }
		
	}

	private void step4() {
        //"""step4() takes off -ant, -ence etc., in context <c>vcvc<v>."""
        if( b.charAt(k - 1) == 'a'){
            if( ends("al")) ;//pass
            else return;
        }else if( b.charAt(k - 1) == 'c'){
            if( ends("ance"));// pass
            else if( ends("ence"));// pass
            else return;
        }else if( b.charAt(k - 1) == 'e'){
            if( ends("er"));// pass
            else return;
        }else if( b.charAt(k - 1) == 'i'){
            if( ends("ic"));// pass
            else  return;
        }else if( b.charAt(k - 1) == 'l'){
            if( ends("able"));// pass
            else if( ends("ible"));// pass
            else return;
        }else if( b.charAt(k - 1) == 'n'){
            if( ends("ant"));// pass
            else if( ends("ement")); // pass
            else if( ends("ment")); // pass
            else if( ends("ent")); //pass
            else return;
        }else if( b.charAt(k - 1) == 'o'){
            if( ends("ion") && (b.charAt(j) == 's' || b.charAt(j) == 't')); // pass
            else if( ends("ou") ); //pass
            //# takes care of -ous
            else  return;
        }else if( b.charAt(k - 1) == 's'){
            if( ends("ism")); //pass
            else return;
        }else if( b.charAt(k - 1) == 't'){
            if( ends("ate")); //pass
            else if( ends("iti")); //pass
            else return;
		}else if( b.charAt(k - 1) == 'u'){
            if( ends("ous")); //pass
            else return;
		}else if( b.charAt(k - 1) == 'v'){
            if( ends("ive")); //pass
            else return;
		}else if( b.charAt(k - 1) == 'z'){
            if( ends("ize")); //pass
            else return;
		}else{
            return;
		}
        if( m() > 1){
            k = j;
        }
	}

	private void step3() {

        //"""step3() dels with -ic-, -full, -ness etc. similar strategy to step2."""
        if( this.b.charAt(this.k) == 'e'){
            if(ends("icate")){     r("ic");
            }else if(ends("ative")){  r("");
        	}else if( ends("alize")){   r("al");}
        }else if( this.b.charAt(this.k) == 'i'){
            if( ends("iciti")){     r("ic");}
        }else if( this.b.charAt(this.k) == 'l'){
            if(ends("ical")){r("ic");
            }else if( ends("ful") ){    r("");}
        }else if( this.b.charAt(this.k) == 's'){
            if( ends("ness")){      r("");}
        }
		
	}

	private void step2() {
        /*step2() maps double suffices to single ones.
        so -ization ( = -ize plus -ation) maps to -ize etc. note that the
        string before the suffix must give m() > 0.
        */
		//System.out.print(this.b);
        if( this.b.charAt(this.k - 1) == 'a'){
            if( ends("ational") )	r("ate");
            else if( ends("tional"))r("tion");
        }else if( this.b.charAt(this.k - 1) == 'c'){
            if( ends("enci") )     	r("ence");
            else if( ends("anci"))  r("ance");
        }else if( b.charAt(this.k - 1) == 'e'){
            if( ends("izer") )      r("ize");
        }else if( this.b.charAt(this.k - 1) == 'l'){
            if( ends("bli") )       r("ble"); 
            //# --DEPARTURE--
            //# To match the published algorithm, replace this phrase with
            //#   if ends("abli"):      r("able")
            else if(ends("alli"))    	r("al");
            else if(ends("entli"))   	r("ent");
            else if(ends("eli"))		r("e");
            else if(ends("ousli"))   	r("ous");
        }else if( this.b.charAt(this.k - 1) == 'o'){
            if(ends("ization"))   r("ize");
            else if (ends("ation") )  r("ate");
            else if (ends("ator"))   r("ate");
        }else if( this.b.charAt(this.k - 1) == 's'){
            if (ends("alism"))     r("al");
            else if (ends("iveness")) r("ive");
            else if (ends("fulness")) r("ful");
            else if (ends("ousness")) r("ous");
        }else if( this.b.charAt(this.k - 1) == 't'){
            if (ends("aliti"))     r("al");
            else if( ends("iviti"))   r("ive");
            else if( ends("biliti"))  r("ble");
        }else if( b.charAt(this.k - 1) == 'g'){ //# --DEPARTURE--
            if( ends("logi"))      r("log");
        }
        //# To match the published algorithm, delete this phrase
		
	}

	private void r(String s) {
	     //  """r(s) is used further down."""
        if( m() > 0){
            setto(s);
        }
	}

	private void step1c() {
        //"""step1c() turns terminal y to i when there is another vowel in the stem."""
        if (ends("y") && vowelinstem()){
            this.b = this.b.substring(0,this.k) + 'i' + this.b.substring(this.k+1,b.length());
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
	        }else if( (ends("ed") || ends("ing")) && vowelinstem()){
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
	            }else if(m() == 1 && cvc(this.k)){
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
        for( int i = this.k0; i< this.j+1 ; i++ ){
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
        this.b = this.b.substring(0,this.j+1) + s + this.b.substring(this.j+length+1,b.length());
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
