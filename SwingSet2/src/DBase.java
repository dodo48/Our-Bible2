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
	
	// ��������. �� ����� ���� ��� �������� �������� ������.
	public int caretPosition;


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
	
	// -------- �������� �������� �������--------
	public void DelInfo(String IDBook) throws ClassNotFoundException, SQLException {
		
		String deleteBook = "DELETE FROM BookDescription WHERE IDBook = (\""+IDBook+"\")";
		PreparedStatement preparedStatement = null;
		
		System.out.println(deleteBook);
		preparedStatement = conn.prepareStatement(deleteBook);
		
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
	public String GetChapterText(int bookNumber, int currChapter, int currVerse) throws ClassNotFoundException, SQLException {
		Statement statmt = conn.createStatement();
		
		// ����������� ����� �����+
		// NB ���������������� ������� books � books_all � ������ �����.
		String bookName = "";
		ResultSet resSet = statmt.executeQuery("SELECT * FROM BOOKS WHERE book_number = " + bookNumber + ";");
		if (resSet.next()) {
			bookName = resSet.getString("long_name");			
		}
		//-
		
		// ������� ����������.
		DBase chronoDBase = new DBase("db//ø��-�.subheadings.SQLite3", "���������� ����");
		chronoDBase.ConnDBase();
		ResultSet chronoResSet = chronoDBase.GetSubheadings(bookNumber);
		
		// ������� ����������.
		DBase subheadingsDBase = new DBase("db//ø��-�.subheadings.SQLite3", "������������� ����");
		subheadingsDBase.ConnDBase();
		ResultSet subheadingsResSet = subheadingsDBase.GetSubheadings(bookNumber);
		
		// ������� ������.
		DBase crossrefsDBase = new DBase("db//RST-x.crossreferences.SQLite3", "������������� ����");
		crossrefsDBase.ConnDBase();
		ResultSet crossrefsResSet = crossrefsDBase.GetCrossRefs(bookNumber);
		
		resSet = statmt.executeQuery("SELECT * FROM VERSES WHERE book_number = " + bookNumber + ";");

		String result = ""; 
				
		
		// ���������� HTML
		/* �����:
		H1 - �����
		H2 - �����
		H3 ���������� � ������������ ø��
		����� ����� - �����
		������
		�����������
		�������
		����� ������
		�������� ����� 
		*/	
		
		String colorBrown = "#A52A2A";
		String colorDarkTurquoise = "#00CED1";
		String colorSkyBlue = "#87CEEB";
		String colorDarkGray = "#A9A9A9";
		String colorGray = "#808080";
		
		// ������ �����
		// http://htmlbook.ru/samcss/sposoby-dobavleniya-stiley-na-stranitsu
		result +=
			"<head>" + "\n" +
			"<style type=\"text/css\">" + "\n" +
		// ��� �����
			"  H1 {" + "\n" +
			"	color: " + colorBrown + ";" + "\n" +
			"}" + "\n" +
		// �����
			"  H2 {" + "\n" +
			"	color: " + colorSkyBlue + ";" + "\n" +
			"}" + "\n" +
		// ������������ ����
			"  H3 {" + "\n" +
			"	color: " + colorBrown + ";" + "\n" +
			"}" + "\n" +
					
		// ����� �����:
			"  .verse {" + "\n" +
			"	color: " + colorGray + ";" + "\n" +
			"}" + "\n" +
			"</style>" + "\n" +
			"</head>" + "\n" +
			
			
			"<body>" + "\n" +
			"<H1>" + bookName + "</H1>" + "\n";
			
			
		while (resSet.next()) {
			// int id = resSet.getInt("id");

			// ������� ������ ������� �� RST+
			// ������ ������ ���� !-- (�����������) ����� ������������ ���������� ���, ��� ���������, ������ � ����� �����))
			String text = resSet.getString("text");
			text = text.replaceAll("<S>", "<!-- <S>");
			text = text.replaceAll("</S>", "</S> -->");
			
			int chapterNumber = resSet.getInt("chapter");			
			int verseNumber = resSet.getInt("verse");
			
			if (verseNumber == 1) {
				result += "<H2>����� " + chapterNumber + "</H2>" + "\n";
			}
			
			// ����������� �������������, ���� ����.
			if (FindInResultSet(chronoResSet, chapterNumber, verseNumber)) {
				// ��������� ������������ � �����������.
				result += "<H3>" + chronoResSet.getString("subheading") + "</H3>" + "\n";
			}
			
			if (FindInResultSet(subheadingsResSet, chapterNumber, verseNumber)) {
				// ��������� ������������.
				result += "<H3>" + subheadingsResSet.getString("subheading") + "</H3>" + "\n";
			}

			if (chapterNumber == currChapter && verseNumber == currVerse) {
				caretPosition = result.length();
				System.out.println(caretPosition);
			}					
				
			result += "<br><span class = \"verse\">" + verseNumber + "</span> " + text + "</br>";

			// ����������� ������. �� ����� ���� ���������.
			while (FindInResultSet(crossrefsResSet, chapterNumber, verseNumber)) {
				result +=
						"<br><a href=\"http://crossref"
						+ crossrefsResSet.getInt("book_to") + "/" // ����� ����� ������� ��� ����� 
						+ crossrefsResSet.getInt("chapter_to") + "/"
						+ crossrefsResSet.getInt("verse_to_start")
						+ "\">" 
												
						+ crossrefsResSet.getInt("book_to") + ":" // ����� ����� ������� ��� ����� 
						+ crossrefsResSet.getInt("chapter_to") + ":"
						+ crossrefsResSet.getInt("verse_to_start")
						+ "</a></br>";

				crossrefsResSet.next();
			}
			
		}
		result = result + "</body>";

		System.out.println("������� ��������");

		return result;
	}
	
	// ������������ ��� ������ � ���������� ������� � ���������������.
	// ������� ������� �� ������ � �������� ������� ����� � ����� (chapter+verse).
	// ���� ���������, ��� � ������� ��������� ���������� �������, �� ������� ����� �������� ��������, � �����, ��� ���� �������������� ������ ������� ������� Next()!
	public boolean FindInResultSet(ResultSet resSet, int chapterNumber, int verseNumber) throws ClassNotFoundException, SQLException {
	
		if (resSet.isAfterLast()) {
			return false;
		}
		
		do {
			if (resSet.isBeforeFirst()) {
				if (resSet.next() == false) {
					return false;
				};
			}
			
			int currChapter = resSet.getInt("chapter");
			int currVerse = resSet.getInt("verse");
			
			if (currChapter > chapterNumber || currChapter == chapterNumber && currVerse > verseNumber) {					
				break;
			}
			
			if (currChapter == chapterNumber && currVerse == verseNumber) {
				return true;
			}
		} while (resSet.next());
		
		return false;
	}
	
	
	// ���� ����� ��������� ������ DBase � ����������� �� ���� ���� (������, ������������, ����������, ������, �����������, �������).
	//
	// ��� ���� ������������� ��� ����������. 
	// ���������� ������� �� ������� subheadings.
	public ResultSet GetSubheadings(int bookNumber) throws ClassNotFoundException, SQLException {
		Statement statmt = conn.createStatement();
		ResultSet resSet = statmt.executeQuery(
			"SELECT * " + "\n" +
			"FROM subheadings " + "\n" +
			"WHERE book_number = " + bookNumber +"\n" + 
			"ORDER BY book_number, chapter, verse;");
		return resSet;
	}
	
	// ���������� ������� ������ �� cross_references
	public ResultSet GetCrossRefs(int bookNumber) throws ClassNotFoundException, SQLException {
		Statement statmt = conn.createStatement();
		ResultSet resSet = statmt.executeQuery(
			"SELECT * " + "\n" +
			"FROM cross_references " + "\n" +
			"WHERE book = " + bookNumber +"\n" + 
			"ORDER BY book, chapter, verse;");
		return resSet;
	}
	
	// --------��������--------
	public void CloseDB() throws ClassNotFoundException, SQLException {
		//statmt.close();
		//resSet.close();
		conn.close();

		System.out.println("���������� �������");
	}

}