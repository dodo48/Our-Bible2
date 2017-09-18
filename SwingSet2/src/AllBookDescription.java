import java.util.ArrayList;

public class AllBookDescription {
	
    static ArrayList<BookDescription> arraylist = new ArrayList<BookDescription>();
    
    public static void addBook(String ID, String IDBook, String Description) {
    	
		System.out.println("Добавляю");
		arraylist.add(new BookDescription(ID, IDBook, Description));
    	
    	for(BookDescription str: arraylist) {
    		
			System.out.println("Иду по");
       		System.out.println(str.ID);
       		System.out.println(str.IDBook);
       		System.out.println(str.Description);
    	}

    }
    
    public static void showAllBook() {
    	
    	System.out.println("showAllBook");
    	
    	for(BookDescription str: arraylist) {
    		
			System.out.println("Иду по циклу in showAllBook");
       		System.out.println(str.ID);
       		System.out.println(str.IDBook);
       		System.out.println(str.Description);
    	}
    	
    	
    }
    
    public static boolean compBook(String IDBook) {
    	
    	boolean res = true;
    	
    	for(BookDescription str: arraylist) {
    		
			System.out.println("Иду по циклу");
			
    		if(IDBook.equals(str.IDBook)) {
            	return false;
    		}
    		else res = true;
    	}
    	return res;
    }
	   
	public static ArrayList<BookDescription> getAllBookDescription() {
		return arraylist;
	}

    public static boolean delBook(String IDBook) {
    	
    	boolean res = true;
    	
    	for(BookDescription str: arraylist) {
    		
    		if(str.IDBook.equals(IDBook)) {
            	arraylist.remove(str);
            	return true;
    		}
    		else res = true;
    	}
    	return res;
    }
	
}
