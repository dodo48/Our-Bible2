import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class DBase {
	public String path;
	public String description;
	
	public Connection conn;
	//public Statement statmt;  
	//public ResultSet resSet;


	// ����������� �������
	public DBase(String path, String description) {
		this.path = path;
		this.description = description;
	}
	
	// --------����������� � ���� ������--------
	public void ConnDBase() throws ClassNotFoundException, SQLException	{
		conn = null;
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:" + path);

		System.out.println("���� ����������!");
	}
	
	// -------- ����� �������--------
	public String ReadDB() throws ClassNotFoundException, SQLException {
		Statement statmt = conn.createStatement();
		ResultSet resSet = statmt.executeQuery("SELECT * FROM BOOKS;");

		String result = "<body> <H1>" + description + "</H1>";

		while (resSet.next()) {
			// int id = resSet.getInt("id");

			String long_name = resSet.getString("long_name");
			result = result + "<P>" + long_name + "</P>";

			//System.out.println("�������� ��������: " + short_name);
		}

		System.out.println("������� ��������");

		return result;
    }


	public String ReadDescription() throws ClassNotFoundException, SQLException {
		Statement statmt = conn.createStatement();
		ResultSet resSet = statmt.executeQuery("SELECT name, value FROM INFO;");

		//String result = "<body> <H1>" + description + "</H1>";

		while (resSet.next()) {
			
			if(resSet.getString("name").equals("description")) {
				return  resSet.getString("value");
			}

		}

		return "";
    }
	
	
	
	// -------- ����� �������--------
	public void SaveInfo(String IDBook, String Description) throws ClassNotFoundException, SQLException {
		
		String insertBook = "INSERT INTO BookDescription (IDBook, Description) VALUES (\""+IDBook+"\",\""+Description+ "\")";
		PreparedStatement preparedStatement = null;
		
		System.out.println(insertBook);
		preparedStatement = conn.prepareStatement(insertBook);
		
		preparedStatement.execute();

    }
	
	
	
	
	
	// -------- ����� ������� FullBooksList --------
	public void ReadFullBooksList() throws ClassNotFoundException, SQLException {

		Statement statmt;
		try {
			statmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		ResultSet resSet = statmt.executeQuery("SELECT * FROM BookDescription;");

		BookDescription bd = new BookDescription();

		System.out.println("����� ������ ����� ������� BookDescription");
		
			while (resSet.next()) {

				System.out.println("����� ������� BookDescription");
				
				bd.ID = String.valueOf(resSet.getInt("ID"));
				bd.IDBook = resSet.getString("IDBook");
				bd.Description = resSet.getString("Description");
				AllBookDescription.addBook(bd.ID, bd.IDBook, bd.Description);
				//SaveInfo(bd.IDBook, bd.Description);
			}
			System.out.println("������� ��������");


    }
	
	
	
	
	// �������� ����� �����
	public String GetChapterText(int bookNumber, int chapter) throws ClassNotFoundException, SQLException {
		Statement statmt = conn.createStatement();
		ResultSet resSet = statmt.executeQuery("SELECT * FROM VERSES WHERE book_number = " + bookNumber + " AND chapter = " + chapter + ";");

		String result = "<body><H1>����� " + chapter + "</H1>"
		+ "<br><a href=\"http://���2\">������ ��� �������������</a></br>"
		+ "<br><a href=\"http://���\">������ � ��������������</a></br>";

		while (resSet.next()) {
			// int id = resSet.getInt("id");

			String text = resSet.getString("text");
			text = text.replaceAll("<S>", "<!-- <S>");
			text = text.replaceAll("</S>", "</S> -->");
			result = result + "<br>" + resSet.getString("verse") + " " + text + "</br>";

			//System.out.println("�������� ��������: " + short_name);
		}
		result = result + "</body>";

		System.out.println("������� ��������");

		return result;

	}
	
	// --------��������--------
	public void CloseDB() throws ClassNotFoundException, SQLException {
		//statmt.close();
		//resSet.close();
		conn.close();

		System.out.println("���������� �������");
	}

}