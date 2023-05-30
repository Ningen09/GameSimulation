package gameuser_management;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class UserAddFrame extends JFrame
{
	int windowVerticalSize = 300;
	int windowHorizontalSize = 450;

	JLabel[] userInfoLbls = new JLabel[4];
	JTextField[] userInfoTxtFlds = new JTextField[4];
	String[] userInfos = {"ID", "비밀번호", "닉네임", "이메일"};
	
	String CRUD[] = {"추가", "수정", "삭제"};
	public JButton CRUDBtns[] = new JButton[CRUD.length];
	

	ActionHandler handler = new ActionHandler();

	public UserAddFrame()
	{
		init();
		setLocation();
		addHandler();
		addCompoenet();
	}

	void init()
	{
		setLayout(null);
		//* 객체 생성 *// 

		for(int i=0; i<userInfoLbls.length; i++)
		{
			userInfoLbls[i] = new JLabel(userInfos[i]);
		}

		for(int i=0; i<userInfoTxtFlds.length; i++)
		{
			userInfoTxtFlds[i] = new JTextField(10);
		}

		for(int i=0; i<CRUDBtns.length; i++)
		{
			CRUDBtns[i] = new JButton(CRUD[i]);
		}

		// Frame 기본 설정
		setTitle("유저추가");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		setSize(windowHorizontalSize, windowVerticalSize);
		setVisible(true);
	}

	void setLocation()
	{
		userInfoLbls[0].setBounds(50, 30, 50, 50);
		userInfoLbls[1].setBounds(230, 30, 50, 50);
		userInfoLbls[2].setBounds(50, 100, 50, 50);
		userInfoLbls[3].setBounds(230, 100, 50, 50);

		userInfoTxtFlds[0].setBounds(110, 40, 80, 30);
		userInfoTxtFlds[1].setBounds(290, 40, 80, 30);
		userInfoTxtFlds[2].setBounds(110, 110, 80, 30);
		userInfoTxtFlds[3].setBounds(290, 110, 80, 30);

		CRUDBtns[0].setBounds(30, 180, 80, 30);
		CRUDBtns[1].setBounds(180, 180, 80, 30);
		CRUDBtns[2].setBounds(330, 180, 80, 30);
	}

	void addHandler()
	{
		for(int i=0; i<CRUDBtns.length; i++)
		{
			CRUDBtns[i].addActionListener(handler);
		}
	}

	void addCompoenet()
	{
		for(int i=0; i<userInfoLbls.length; i++)
		{
			add(userInfoLbls[i]);
		}

		for(int i=0; i<userInfoTxtFlds.length; i++)
		{
			add(userInfoTxtFlds[i]);
		}

		for(int i=0; i<CRUDBtns.length; i++)
		{
			add(CRUDBtns[i]);
		}
	}

	public String[] getUserInfoInputs()
	{
		String[] userInfoInputs = new String[userInfos.length];

		for(int i=0; i<userInfoTxtFlds.length;i++)
		{
			userInfoInputs[i] = userInfoTxtFlds[i].getText();
		}	

		return userInfoInputs;
	}

	public String[] getUserInfos()
	{
		return userInfos;
	}

	public boolean isIdOverlap(ResultSet rs, String id)
	{
		String currentId;
		try
		{
			while(rs.next())
			{
				currentId = rs.getString(1);

				if(id.equals(currentId))
				{
					return true;
				}
			}

			return false;
		}
		catch(SQLException e)
		{
			e.printStackTrace();

			return true;
		}
	}

	public Boolean isEmpty() //공백체크
	{
		for(int i=0; i<userInfoTxtFlds.length; i++)
		{
			if(userInfoTxtFlds[i].getText().isEmpty())
				return userInfoTxtFlds[i].getText().isEmpty();
		}

		return false;
	}

	public Boolean isIdEmpty() // id공백체크
	{
		return userInfoTxtFlds[0].getText().isEmpty();
	}

	public String getEmptyLabelText() // 공백인 라벨의 이름 반환
	{
		for(int i=0; i<userInfoTxtFlds.length;i++)
		{
			if(userInfoTxtFlds[i].getText().isEmpty())
			{
				return userInfoLbls[i].getText();
			}
		}

		return null;
	}

	public void clear() // 텍스트필드 초기화
	{
		for(int i=0; i<userInfoTxtFlds.length; i++)
		{
			userInfoTxtFlds[i].setText("");
		}
	}

	
	class ActionHandler implements ActionListener
	{	
		@Override
		public void actionPerformed(ActionEvent event) 
		{
			DB.makeConnection();

			Object source = event.getSource();

			if(source instanceof JButton)
			{
				JButton btn = (JButton)source;

				String command = btn.getText(); // 눌러진 버튼의 text
				String[] userInfoInputs = new String[4]; // TextField에 압력된 유저의 정보들

				// 입력된 유저 정보를 저장
				userInfoInputs = getUserInfoInputs();

				if(command == "추가")
				{
					String sql;

					// 공백 체크
					if(isEmpty())
					{
						String emptyLblTxt = getEmptyLabelText();
						System.out.println("["+emptyLblTxt+"]공백입니다.");
						JOptionPane.showMessageDialog(null, "["+emptyLblTxt+"]공백입니다."); // 경고창 표시
						return;
					}


					sql = "SELECT * FROM user";
					try
					{
						DB.rs = DB.stmt.executeQuery(sql);

						// ID 중복 체크
						Boolean idOverlap = isIdOverlap(DB.rs, userInfoInputs[0]);

						if(idOverlap)
						{
							System.out.println("[ID]중복입니다.");
							JOptionPane.showMessageDialog(null, "[ID]중복입니다."); // 경고창 표시
						}
						else
						{
							sql = "INSERT INTO user values('"+userInfoInputs[0]+"','"+userInfoInputs[2]+"','"+userInfoInputs[1]+"','"+userInfoInputs[3]+"', 1, 0, 'unrank', 0"+")";

							System.out.println(sql);

							// 유저 정보를 DB에 입력
							try
							{
								DB.stmt.executeUpdate(sql);
							}
							catch(SQLException e)
							{
								e.printStackTrace();
							}

							clear(); // TextField 초기화

						}
					}
					catch(SQLException e)
					{
						e.printStackTrace();
					}
					
					UserPanel.load();
				}
				else if(command == "수정")
				{
					String sql = "SELECT * FROM user;";

					// 공백 체크
					if(isEmpty())
					{
						String emptyLblTxt = getEmptyLabelText();
						System.out.println("["+emptyLblTxt+"]공백입니다.");
						JOptionPane.showMessageDialog(null, "["+emptyLblTxt+"]공백입니다."); // 경고창 표시
						return;
					}					

					try
					{
						DB.rs = DB.stmt.executeQuery(sql);

						sql = "SELECT * FROM user where id='"+userInfoInputs[0]+"';";

						DB.rs = DB.stmt.executeQuery(sql);
						if(DB.rs.next())
						{
							sql = "UPDATE user SET nickname = '"+userInfoInputs[2]+"', password = '"+userInfoInputs[1]+"', email = '"+userInfoInputs[3]+"' where id = '"+userInfoInputs[0]+"';";
							System.out.println(sql);
							DB.stmt.executeUpdate(sql);
						}
						else
						{
							System.out.println("존재하지않는 ID입니다.");
							JOptionPane.showMessageDialog(null, "존재하지않는 ID입니다"); // 경고창 표시
							return;
						}
					}
					catch(SQLException e)
					{
						e.printStackTrace();
					}

					UserPanel.load();
					clear(); // TextField 초기화
				}
				else if(command == "삭제")
				{
					String sql="DELETE FROM user where id = '"+userInfoInputs[0]+"'";

					// 공백 체크
					if(userInfoInputs[0].equals(""))
					{
						System.out.println("[ID]공백입니다.");
						JOptionPane.showMessageDialog(null, "[ID]공백입니다."); // 경고창 표시
						return;
					}

					try
					{
						System.out.println(sql);
						if(DB.stmt.executeUpdate(sql) == 0)
						{
							System.out.println("존재하지 않는 ID입니다.");
							JOptionPane.showMessageDialog(null, "존재하지 않는 ID입니다."); // 경고창 표시
						}
					}
					catch(SQLException e)
					{
						e.printStackTrace();
					}

					UserPanel.load();

					clear(); // TextField 초기화
				}

			}

			DB.disConnection();
		}
	}
}