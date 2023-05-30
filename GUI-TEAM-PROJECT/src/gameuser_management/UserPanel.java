package gameuser_management;

import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class UserPanel extends JPanel
{
	static String[] userInfoAttribute = {"ID", "닉네임", "비밀번호", "이메일", "레벨", "승률", "랭크"};
	
	public static DefaultTableModel searchModel;
	public static DefaultTableModel userInfoModel;
	static JTable userInfoTable;

	JScrollPane userInfoScroll;
	
	public UserPanel()
	{	
		init();
		
		add(userInfoScroll);
		
		load();
	}
	
	void init()
	{
		setLayout(new GridLayout()); // 레이아웃 설정
		
		//* 객체 생성 *//
		searchModel = new DefaultTableModel(userInfoAttribute, 0);
		userInfoModel = new DefaultTableModel(userInfoAttribute, 0);
		userInfoTable = new JTable(userInfoModel);
		userInfoScroll = new JScrollPane(userInfoTable);
	}
	
	public static void addRow(String[] data)
	{
		userInfoModel.addRow(data);
	}
	
	public static Boolean isDataOverlap(String id) // 중복된 데이터가 있다면 true 리턴
	{
		for(int i=0; i<userInfoModel.getRowCount(); i++)
		{
			if(id.equals(userInfoModel.getValueAt(i, 0)))
			{
				return true;
			}
		}
		return false;
	}
	
	public static void search(String keyword, int option)
	{	
		Vector<Vector> data = userInfoModel.getDataVector();
		Vector<Vector> result = new Vector<>();
		
		//keyword를 포함하는 데이터를 result에 추가
		for(int i=0; i<userInfoTable.getRowCount(); i++)
		{
			String searchedWord = (String)data.get(i).get(option);
			if(searchedWord.contains(keyword))
			{
				result.add(data.get(i));
			}
		}
		
		//모델 초기화
		searchModel.setRowCount(0);

		//검색결과를 모델에 추가
		for(Vector<Vector> in : result)
		{
			searchModel.addRow(in);
		}
		
		userInfoTable.setModel(searchModel); // 모델 변경
	}
	
	static public void load() // 테이블에 DB에저장된 정보들을 로드
	{
		DB.makeConnection();

		String sql = "SELECT * FROM user;";
		String[] tmpData = new String[userInfoAttribute.length];
		
		UserPanel.userInfoModel.setNumRows(0);
		
		try
		{
			DB.rs = DB.stmt.executeQuery(sql);
			while(DB.rs.next())
			{
				for(int i=0; i<userInfoAttribute.length;i++)
				{
					tmpData[i] = DB.rs.getString(i+1);
				}
				
				UserPanel.addRow(tmpData);
			}
							
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		DB.disConnection();
	}
}
