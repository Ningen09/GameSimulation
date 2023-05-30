package gameuser_management;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegisterGUI extends JFrame
{	
	Dimension TxtFldSize = new Dimension(300, 10);
	
	int windowVerticalSize = 300;
	int windowHorizontalSize = 300;
	
	Container container;
	
	RegisterInfoPanel registerInputPane;
	
	public RegisterGUI()
	{
		init();
		createWindow();
	}
	
	void init()
	{
		registerInputPane = new RegisterInfoPanel();
		
		container = getContentPane();
		
		setTitle("회원가입");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		setSize(windowHorizontalSize, windowVerticalSize);
		setVisible(true);
//		setLayout(new BorderLayout());
	}
	
	void createWindow()
	{
		container.add(registerInputPane);
	}
	
	class RegisterInfoPanel extends JPanel
	{	
		JLabel idLbl;
		JLabel passwordLbl;
		JLabel emailLbl;
		
		JButton okBtn;
		
		JTextField[] registerInfoTxtFlds = new JTextField[3]; // ID, PASSWORD, EMAIL

				
		public RegisterInfoPanel()
		{
			init();
			createPanel();
			setSize();
			setLocation();
		}
		
		void init() // 객체생성
		{
			idLbl = new JLabel("ID");
			passwordLbl = new JLabel("PASSWORD");
			emailLbl = new JLabel("E-MAIL");
			
			registerInfoTxtFlds[0] = new JTextField(10); // ID
			registerInfoTxtFlds[1] = new JTextField(3); // PASSWORD
			registerInfoTxtFlds[2] = new JTextField(3); // EMAIL
			
			okBtn = new JButton("확인");
			
			setLayout(null);
		}
		
		void setSize()
		{
			for(int i=0; i<registerInfoTxtFlds.length; i++)
			{
				registerInfoTxtFlds[i].setSize(TxtFldSize);
			}
		}
		
		void setLocation()
		{
			idLbl.setBounds(40,10, 50, 50);
			passwordLbl.setBounds(40,60, 100, 50);
			emailLbl.setBounds(40,110, 50, 50);
		
			registerInfoTxtFlds[0].setBounds(170,20, 80, 20);
			registerInfoTxtFlds[1].setBounds(170, 70, 80, 20);
			registerInfoTxtFlds[2].setBounds(170,120, 80, 20);
			
			okBtn.setBounds(110, 170, 80, 40);
		}
		
		public boolean isIdOverlap(ResultSet rs, String id) // 아이디 중복검사
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
			for(int i=0; i<registerInfoTxtFlds.length; i++)
			{
				if(registerInfoTxtFlds[i].getText().isEmpty())
					return registerInfoTxtFlds[i].getText().isEmpty();
			}

			return false;
		}
		

		
		void createPanel()
		{
			add(idLbl);
			add(passwordLbl);
			add(emailLbl);
			add(okBtn);
			
			for(int i=0; i<registerInfoTxtFlds.length; i++)
			{
				add(registerInfoTxtFlds[i]);
			}
		}
		
//		회원가입 핸들러
		class RegisterHandler implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				
				if(source instanceof JButton)
				{
					JButton btn = (JButton)source;
					String command = btn.getText();
					
					if(command == "회원가입")
					{
						String sql;

						// 공백 체크
//						if(bookInfoInputPane.isEmpty())
//						{
//							String emptyLblTxt = bookInfoInputPane.getEmptyLabelText();
//							System.out.println("["+emptyLblTxt+"]공백입니다.");
//							JOptionPane.showMessageDialog(null, "["+emptyLblTxt+"]공백입니다."); // 경고창 표시
//							return;
//						}
//
//
//						sql = "SELECT * FROM book";
//						try
//						{
//							BookDB.rs = BookDB.stmt.executeQuery(sql);
//
//							// ID 중복 체크
//							Boolean idOverlap = bookInfoInputPane.isIdOverlap(BookDB.rs, bookInfoInputs[0]);
//
//							if(idOverlap)
//							{
//								System.out.println("[ID]중복입니다.");
//								JOptionPane.showMessageDialog(null, "[ID]중복입니다."); // 경고창 표시
//							}
//							else
//							{
//								sql = "INSERT INTO book(id, title, publisher, price) values('"+bookInfoInputs[0]+"','"+bookInfoInputs[1]+"','"+bookInfoInputs[2]+"','"+bookInfoInputs[3]+"')";
//
//								System.out.println(sql);
//
//								//							 책 정보를 DB에 입력
//								try
//								{
//									BookDB.stmt.executeUpdate(sql);
//								}
//								catch(SQLException e)
//								{
//									e.printStackTrace();
//								}
//
//								bookInfoInputPane.clear(); // TextField 초기화
//								bookManagementFrame.bookInfoOutputPane.load();
//							}
//						}
//						catch(SQLException e)
//						{
//							e.printStackTrace();
//						}
					}
				}
			}
			
		}
	}
}
