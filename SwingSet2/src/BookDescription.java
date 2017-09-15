import java.util.ArrayList;

public class BookDescription {
	
	String ID;
	String IDBook;
	String Description;
	
	
	public BookDescription(String ID, String IDBook, String Description) {
        this.ID = ID;
        this.IDBook = IDBook;
        this.Description = Description;
   }

   public String getID() {
        return ID;
   }
   public void setID(String ID) {
	this.ID = ID;
   }
   public String geIDBook() {
	return IDBook;
   }
   public void setIDBook(String IDBook) {
	this.IDBook = IDBook;
   }
   public String getDescription() {
	return Description;
   }
   public void setDiskription(String Description) {
	this.Description = Description;
   }	
   
   
}
