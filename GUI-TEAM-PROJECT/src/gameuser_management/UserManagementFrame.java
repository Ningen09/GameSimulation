package gameuser_management;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class UserManagementFrame extends JFrame
{
	int windowVerticalSize = 600;
	int windowHorizontalSize = 900;

	UserAddFrame userAddFrame;
	
	JTabbedPane tab;
	String[] tabTitle = {"유저","전적","캐릭터","무기","게임"};
	JPanel[] panels = new JPanel[tabTitle.length];

	//유저탭에서 사용되는 변수
	JButton refreshBtn; // 테이블 새로고침(load()함수 호출)버튼
	
	JButton userCRUDBtn; //유저관리 버튼
	
	JTextField searchTxtFld;
	JComboBox<String> searchOptCmbBox;
	//
	
	UserButtonHandler userBtnHandler = new UserButtonHandler();

	
	
	public UserManagementFrame()
	{
		init();
		addHandler();

		addComponent();		
	}

	void init()
	{
		//* 객체 생성 *// 
		tab = new JTabbedPane();

		// 패널객체 생성
		panels[0] = new UserPanel();
		panels[1] = new JPanel();
		panels[2] = new JPanel();
		panels[3] = new JPanel();
//		panels[4] = new GamePanel();

		
		// 유저탭에서 사용되는 객체 생성
		refreshBtn = new JButton("새로고침");
		
		userCRUDBtn = new JButton("유저관리");
		searchTxtFld = new JTextField(10);
		searchOptCmbBox = new JComboBox<String>(UserPanel.userInfoAttribute);
				
		//* SET *// 

		// Frame 기본 설정
		setTitle("GameManagement Program");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);		
		setSize(windowHorizontalSize, windowVerticalSize);
		setVisible(true);

		// 컴포넌트 위치와 크기조절
		tab.setBounds(0, 0, windowHorizontalSize-15, windowVerticalSize-39);
		userCRUDBtn.setBounds(550, 2, 100, 20);
		searchTxtFld.setBounds(350, 2, 100, 20);
		searchOptCmbBox.setBounds(450, 2, 80, 20);
		refreshBtn.setBounds(770, 2, 100, 20);

		// Layout 설정
		setLayout(null);
	}

	void addComponent() // 컴포넌트를 화면에 추가
	{		
		addTab();
		addUserTabObject();

		add(tab);
	}

	void addTab() // 탭 추가
	{
		for(int i=0; i<tabTitle.length;i++)
		{
			tab.addTab(tabTitle[i], panels[i]);
		}
	}

	void addUserTabObject()
	{
		add(refreshBtn);
		add(userCRUDBtn);
		add(searchTxtFld);
		add(searchOptCmbBox);
	}

	void addHandler()
	{
		tab.addChangeListener(new TabChangeHandelr());
		
		refreshBtn.addActionListener(userBtnHandler);
		userCRUDBtn.addActionListener(userBtnHandler);
		searchTxtFld.getDocument().addDocumentListener(new SearchHandler());
	}

	public static void main(String[] args)
	{
		new UserManagementFrame();
	}

	class TabChangeHandelr implements ChangeListener // 우측상단에 버튼을 탭마다 다르게 표현하기위해 사용
	{
		@Override
		public void stateChanged(ChangeEvent event) {
			Object source = event.getSource();
			if(source instanceof JTabbedPane)
			{
				JTabbedPane tab = (JTabbedPane)source;
				int index = tab.getSelectedIndex();

				if(index == 0)
				{
					userCRUDBtn.setVisible(true);
					refreshBtn.setVisible(true);
					searchOptCmbBox.setVisible(true);
					searchTxtFld.setVisible(true);
				}
				else
				{
					userCRUDBtn.setVisible(false);
					refreshBtn.setVisible(false);
					searchOptCmbBox.setVisible(false);
					searchTxtFld.setVisible(false);
				}
			}

		}

	}

	class UserButtonHandler implements ActionListener // 유저탭에서 우측상단버튼들 리스너
	{
		@Override
		public void actionPerformed(ActionEvent event) 
		{
			Object source = event.getSource();

			if(source instanceof JButton)
			{
				JButton btn = (JButton)source;

				String command = btn.getText();

				if(command == userCRUDBtn.getText()) // 유저관리
				{
					userAddFrame = new UserAddFrame();
				}
				else if(command == refreshBtn.getText()) // 새로고침
				{
					UserPanel.load();
				}
			}
		}
	}
	
	class SearchHandler implements DocumentListener // 검색을 위한 리스너
	{

		@Override
		public void insertUpdate(DocumentEvent e) {
			UserPanel.search(searchTxtFld.getText(), searchOptCmbBox.getSelectedIndex());
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			if(searchTxtFld.getText().equals(""))
			{
				UserPanel.userInfoTable.setModel(UserPanel.userInfoModel);
			}
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			UserPanel.search(searchTxtFld.getText(), searchOptCmbBox.getSelectedIndex());
		}

	
	}
}

