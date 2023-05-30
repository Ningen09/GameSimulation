package gameuser_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB 
{
	public static Connection con = null;
	public static Statement stmt = null;
	public static ResultSet rs = null;
	
	public static Connection makeConnection()
	{
		String url = "jdbc:mysql://119.198.165.100:3306/game_data?useSSL=false&serverTimezone=UTC";
		String id="newuser";
		String password = "GUIPrograming01";
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("드라이브 적재 성공");
			con = DriverManager.getConnection(url, id, password);
			stmt = con.createStatement();

			System.out.println("데이터베이스 연결 성공");

		}
		catch(ClassNotFoundException e)
		{
			System.out.println("드라이버를 찾을 수 없습니다");
		}
		catch(SQLException e)
		{
			System.out.println("데이터베이스 연결 실패");
		}

		return con;
	}

	public static void disConnection()
	{
		try
		{
			con.close();
			stmt.close(); 
			rs.close();
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
}
