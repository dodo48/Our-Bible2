import java.util.ArrayList;

public class AllBookDescription {
	
    static ArrayList<BookDescription> arraylist = new ArrayList<BookDescription>();
    
    public static void addBook(String ID, String IDBook, String Description) {
    	arraylist.add(new BookDescription(ID, IDBook, Description));	
    }
    
    public static boolean compBook(String ID, String IDBook, String Description) {
    	
    	boolean res = true;
    	
    	for(BookDescription str: arraylist) {
    		
			System.out.println("»ду по циклу");
			
    		if(IDBook.equals(str.IDBook)) {
    			System.out.println(str);
        		System.out.println(str.IDBook);
        		System.out.println("равно");
        		System.out.println(IDBook);
            	return false;
    		}
    		else res = true;
    	}
    	return res;
    }
	   
	

}
