import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.List;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class QAHGUI extends JFrame implements ActionListener, TableModelListener
{
	
	private final int FIELD_SIZE = 25;
	
	private JButton btnFav, btnRec, btnAdd, btnPas, btnSearch, btnIngr, btnSub, btnReg, btnLog, btnToReg, btnAddRec;
	private JPanel pnlMenu, pnlTop, pnlButtons, pnlFav, pnlPers, pnlIngr, pnlPass, pnlReg, pnlLog, content;
	private RecipeDB db;
	private List<Recipe> list;
	private String[] columnNames = {"Recipe Name"};
	private Object[][] data;
	private JTable tblFav, tblPers, tblSearch;
	private JScrollPane scrlPnFav, scrlPnPers, scrlPnSearch, scrlPnAdd;
	private JPanel pnlSearch;
	private JLabel lblTop;
	private JTextField txfSearch, txfTitle, txfUser, txfUsrLog;
	private JPasswordField pwfPass, pwfConf, pwfPasLog;
	
	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[5], nutLabel = new JLabel[15];
	private JTextField[] txfField = new JTextField[5], ingrField = new JTextField[20], nutField = new JTextField[15];
	private JPasswordField[] passField = new JPasswordField[3];
	private int ingrSize = 0;
	private String username;
	
	
	public QAHGUI() {
		super("Quick And Healthy Meals");
		
		db = new RecipeDB();
		try
		{
			list = db.getRecipes();
			
			data = new Object[list.size()][columnNames.length];
			for (int i=0; i<list.size(); i++) {
				data[i][0] = list.get(i).getRecipeTitle();
				
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		createComponents();
		setVisible(true);
		setSize(600, 600);
	}
    
	private void createComponents()
	{
		//Menu Panel
		////////////////////////////////////////////////////////
		pnlMenu = new JPanel();
		pnlMenu.setLayout(new GridLayout(2,0));
		
		pnlTop = new JPanel();
		lblTop = new JLabel("Quick And Healthy Meals");
		lblTop.setFont(new Font("Serif", Font.PLAIN, 20));
		txfSearch = new JTextField(FIELD_SIZE);
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(this);
		pnlTop.add(lblTop);
		pnlTop.add(txfSearch);
		pnlTop.add(btnSearch);
		
		pnlButtons = new JPanel();
		btnFav = new JButton("View Favorites");
		btnFav.addActionListener(this);
		btnRec = new JButton("Personal Recipes");
		btnRec.addActionListener(this);
		btnAdd = new JButton("Add Recipe");
		btnAdd.addActionListener(this);
		btnPas = new JButton("Change Password");
		btnPas.addActionListener(this);
		pnlButtons.add(btnFav);
		pnlButtons.add(btnRec);
		pnlButtons.add(btnAdd);
		pnlButtons.add(btnPas);
		
		pnlMenu.add(pnlTop, BorderLayout.NORTH);
		pnlMenu.add(pnlButtons, BorderLayout.SOUTH);
		
		//Favorites Panel TODO
		////////////////////////////////////////////////////////
		pnlFav = new JPanel();
		tblFav = new JTable(data, columnNames);
		scrlPnFav = new JScrollPane(tblFav);
		pnlFav.add(scrlPnFav);
		tblFav.getModel().addTableModelListener(this);
		
		//Personal Panel TODO
		////////////////////////////////////////////////////////
		pnlPers = new JPanel();
		tblPers= new JTable(data, columnNames);
		scrlPnPers = new JScrollPane(tblPers);
		pnlPers.add(scrlPnPers);
		tblPers.getModel().addTableModelListener(this);
		
		//Add Panel TODO
		////////////////////////////////////////////////////////
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new BoxLayout(pnlAdd, BoxLayout.Y_AXIS));
		String labelNames[] = {"Enter Recipe Name: ", "Enter Category: ", "Enter Preparation Time: ", "Enter Cook Time: "};
		for (int i=0; i<labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfLabel[i] = new JLabel(labelNames[i]);
			txfField[i] = new JTextField(FIELD_SIZE);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAdd.add(panel);
		}
		pnlIngr = new JPanel();
		pnlIngr.setLayout(new BoxLayout(pnlIngr,BoxLayout.Y_AXIS));
		JPanel addPanel1 = new JPanel();
		JLabel lab = new JLabel("Enter Ingredient");
		ingrField[ingrSize] = new JTextField(FIELD_SIZE);
		addPanel1.add(lab);
		addPanel1.add(ingrField[ingrSize]);
		pnlIngr.add(addPanel1);
		ingrSize++;
		JPanel btn = new JPanel();
		btnIngr = new JButton("Add More Ingredients");
		btnIngr.addActionListener(this);
		btn.add(btnIngr);
		pnlAdd.add(pnlIngr);
		pnlAdd.add(btn);
		JPanel panel2 = new JPanel();
		String nutInfo[] = {"Enter Serving Size: ", "Enter Serving Size Unit: ", "Enter Calories: ", "Enter Calories From Fat: ", "Enter Saturated Fat: ",
				"Enter Cholestoral: ", "Enter Sodium: ", "Enter Total Carbohydrates: ", "Enter Dietary Fiber: ", "Enter Sugars: ", "Enter Protein: ", "Enter Vitamin A: ",
				"Enter Vitamin C: ", "Enter Calcium: ", "Enter Iron: "};
		for (int i=0; i<nutInfo.length; i++) {
			JPanel panel = new JPanel();
			nutLabel[i] = new JLabel(nutInfo[i]);
			nutField[i] = new JTextField(FIELD_SIZE);
			panel.add(nutLabel[i]);
			panel.add(nutField[i]);
			pnlAdd.add(panel);
		}
		btnAddRec = new JButton("Add");
		btnAddRec.addActionListener(this);
		panel2.add(btnAddRec);
		pnlAdd.add(panel2);
		
		scrlPnAdd = new JScrollPane(pnlAdd);
		scrlPnAdd.setPreferredSize(new Dimension(580, 480));
		
		//Search Result Panel TODO
		////////////////////////////////////////////////////////
		pnlSearch = new JPanel();
		tblSearch= new JTable(data, columnNames);
		scrlPnSearch = new JScrollPane(tblSearch);
		pnlSearch.add(scrlPnSearch);
		tblSearch.getModel().addTableModelListener(this);
		
		//Recipe Panel TODO
		////////////////////////////////////////////////////////
		
		//Change Password Panel TODO
		////////////////////////////////////////////////////////
		pnlPass = new JPanel();
		pnlPass.setLayout(new BoxLayout(pnlPass,BoxLayout.Y_AXIS));
		String[] label = {"Old", "New", "New"};
		for(int i = 0; i < 3; i++){
			JPanel panel = new JPanel();
			JLabel str = new JLabel(label[i]);
			panel.add(str);
			passField[i] = new JPasswordField(25);
			panel.add(passField[i]);
			pnlPass.add(panel);
		}
		btnSub = new JButton("Submit");
		btnSub.addActionListener(this);
		pnlPass.add(btnSub);
		
		//Register Panel
		////////////////////////////////////////////////////////
		pnlReg = new JPanel();
		pnlReg.setLayout(new GridLayout(4,0));
		JPanel regPanel1 = new JPanel();
		JLabel usrnm = new JLabel("Username");
		txfUser = new JTextField(FIELD_SIZE);
		regPanel1.add(usrnm);
		regPanel1.add(txfUser);
		pnlReg.add(regPanel1);
		JPanel regPanel2 = new JPanel();
		JLabel pass = new JLabel("Password");
		pwfPass = new JPasswordField(FIELD_SIZE);
		regPanel2.add(pass);
		regPanel2.add(pwfPass);
		pnlReg.add(regPanel2);
		JPanel regPanel3 = new JPanel();
		JLabel conf = new JLabel("Confirm Password");
		pwfConf = new JPasswordField(FIELD_SIZE);
		regPanel3.add(conf);
		regPanel3.add(pwfConf);
		pnlReg.add(regPanel3);
		JPanel regPanel4 = new JPanel();
		btnReg = new JButton("Register");
		btnReg.addActionListener(this);
		regPanel4.add(btnReg);
		pnlReg.add(regPanel4);
		
		//Login Panel
		////////////////////////////////////////////////////////
		pnlLog = new JPanel();
		pnlLog.setLayout(new BorderLayout());
		lblTop = new JLabel("Quack And Healthy Meals");
		lblTop.setHorizontalAlignment(JLabel.CENTER);
		lblTop.setFont(new Font("Serif", Font.PLAIN, 20));
		pnlLog.add(lblTop, BorderLayout.NORTH);
		JPanel logpnl = new JPanel();
		logpnl.setLayout(new GridLayout(3,0));
		JPanel use = new JPanel();
		JLabel usename = new JLabel("Username");
		txfUsrLog = new JTextField(FIELD_SIZE);
		use.add(usename);
		use.add(txfUsrLog);
		logpnl.add(use);
		JPanel pss = new JPanel();
		JLabel passw = new JLabel("Password");
		pwfPasLog = new JPasswordField(FIELD_SIZE);
		pss.add(passw);
		pss.add(pwfPasLog);
		logpnl.add(pss);
		JPanel btnpl = new JPanel();
		btnLog = new JButton("Login");
		btnLog.addActionListener(this);
		btnToReg = new JButton("Register");
		btnToReg.addActionListener(this);
		btnpl.add(btnLog);
		btnpl.add(btnToReg);
		logpnl.add(btnpl);
		pnlLog.add(logpnl,BorderLayout.CENTER);
		
		add(pnlLog, BorderLayout.NORTH);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new QAHGUI();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnFav) {
			//TODO
		} else if (e.getSource() == btnRec){
			//TODO
		} else if (e.getSource() == btnAdd) {
			content.removeAll();
			content.add(scrlPnAdd);
			revalidate();
			repaint();
		} else if (e.getSource() == btnPas) {
			remove(content);
			content.removeAll();
			content.add(pnlPass);
			add(content, BorderLayout.CENTER);
			revalidate();
			repaint();
		} else if (e.getSource() == btnSearch) {
			//TODO
		} else if (e.getSource() == btnIngr) {
			JPanel ingr = new JPanel();
			ingr.add(new JLabel("Enter Ingredient"));
			ingrField[ingrSize] = new JTextField(FIELD_SIZE);
			ingr.add(ingrField[ingrSize]);
			ingrSize++;
			pnlIngr.add(ingr);
			if(ingrSize == 20){
				btnIngr.setEnabled(false);
			}
			revalidate();
			repaint();
		} else if (e.getSource() == btnSub) {
			//TODO
			char[] old  = passField[0].getPassword();
			char[] newPass = passField[1].getPassword();
			char[] confPass = passField[2].getPassword();
			boolean same = false;
			String oldPassword = new String(old);
			String newPassword = new String(newPass);
			for (int i = 0; i < newPass.length; i++){
				if(newPass[i] == confPass[i]){
					same = true;
				} else {
					same = false;
					break;
				}
			}
			if(same){
				try {
					if (RecipeDB.loginValidation(username, oldPassword)) {
						//TODO register new password
						RecipeDB.changePassword(username, newPassword);
					} else {
						JOptionPane.showMessageDialog(this, "Passwordand Username Do Not Match!", "", JOptionPane.WARNING_MESSAGE);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Passwords Do Not Match!", "", JOptionPane.WARNING_MESSAGE);
			}
			
		} else if (e.getSource() == btnReg) {
			String name = txfUser.getText();
			char[] password = pwfPass.getPassword();
			char[] confirm = pwfConf.getPassword();
			boolean same = false;
			boolean exists = false;
			if(password.length == confirm.length){
				for(int i = 0; i < password.length; i++){
					if(password[i] == confirm[i]){
						same = true;
					} else {
						same = false;
						break;
					}
				}
			}
			if(same){
				try{
					exists =  RecipeDB.userExists(name);
				} catch (SQLException ex){
					ex.printStackTrace();
				}
				if(exists){
					JOptionPane.showMessageDialog(this, "That Username is Already Taken!", "", JOptionPane.WARNING_MESSAGE);
				} else {
					String pass = new String(password);
					try{
						RecipeDB.registerUser(name, pass);
					} catch (SQLException ex){
						ex.printStackTrace();
					}
					remove(pnlReg);
					add(pnlLog, BorderLayout.NORTH);
					revalidate();
					repaint();
				}	
			} else {
				JOptionPane.showMessageDialog(this, "Passwords Do Not Match!", "", JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource() == btnLog) {
			username = txfUsrLog.getText();
			char[] c = pwfPasLog.getPassword();
			String password = new String(c);
			try{
				if(RecipeDB.loginValidation(username, password)){
					remove(pnlLog);
					add(pnlMenu, BorderLayout.NORTH);
					content = new JPanel();
					content.add(pnlFav);
					add(content, BorderLayout.SOUTH);
					revalidate();
					repaint();
				} else {
					JOptionPane.showMessageDialog(this, "That Login Information does not exist.", "", JOptionPane.WARNING_MESSAGE);
				}
			} catch (SQLException ex){
				ex.printStackTrace();
			}
		} else if (e.getSource() == btnToReg) {
			remove(pnlLog);
			add(pnlReg, BorderLayout.NORTH);
			revalidate();
			repaint();
			
		} else if (e.getSource() == btnAddRec) {
		//TODO
			//Read Fields
			btnIngr.setEnabled(true);
			//Add Recipe
			//Go to My recipes
		}
		
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        Object data = model.getValueAt(row, column);
        
        //db.updateMovie(row, columnName, data);
		
	}

}
