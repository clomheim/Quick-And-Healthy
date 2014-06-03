import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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

public class QAHGUI extends JFrame implements ActionListener, TableModelListener
{
	
	private final int FIELD_SIZE = 25;
	
	private JButton btnFav, btnRec, btnAdd, btnPas, btnSearch, btnIngr, btnSub, btnReg, btnLog, btnToReg, btnAddRec;
	private JPanel pnlMenu, pnlTop, pnlButtons, pnlFav, pnlPers, pnlIngr, pnlPass, pnlReg, pnlLog;
	private RecipeDB db;
	private List<Recipe> lstRec;
	private String[] columnNames = {"Recipe Name"};
	private Object[][] data;
	private JTable tblFav, tblPers;
	private JScrollPane scrlPnFav, scrlPnPers;
	private JPanel pnlSearch;
	private JLabel lblTop, lblRecipe;
	private JTextField txfSearch, txfTitle, txfUser, txfUsrLog;
	private JPasswordField pwfPass, pwfConf, pwfPasLog;
	
	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[5];
	private JTextField[] txfField = new JTextField[5], ingrField = new JTextField[20];
	private JPasswordField[] passField = new JPasswordField[3];
	private int ingrSize = 0;
	
	
	public QAHGUI() {
		super("Quick And Healthy Meals");
		
		db = new RecipeDB();
//		try  														// TODO Read in Data
//		{
//			lstRec = db.getMovies();
//			
//			data = new Object[list.size()][columnNames.length];
//			for (int i=0; i<list.size(); i++) {
//				data[i][0] = list.get(i).getTitle();
//				data[i][1] = list.get(i).getYear();
//				data[i][2] = list.get(i).getLength();
//				data[i][3] = list.get(i).getGenre();
//				data[i][4] = list.get(i).getStudioName();
//				
//			}
//			
//		} catch (SQLException e)
//		{
//			e.printStackTrace();
//		}
		createComponents();
		setVisible(true);
		setSize(600, 600);
	}
    
	private void createComponents()
	{
		//Menu Panel
		////////////////////////////////////////////////////////
		pnlMenu = new JPanel();
		
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
		pnlAdd.setLayout(new GridLayout(5, 0));
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
		JPanel panel1 = new JPanel();
		JLabel lab = new JLabel("Enter Ingredients");
		ingrField[ingrSize] = new JTextField(FIELD_SIZE);
		panel1.add(lab);
		panel1.add(ingrField[ingrSize]);
		pnlIngr.add(panel1);
		ingrSize++;
		btnIngr = new JButton("Add More Ingredients");
		btnIngr.addActionListener(this);
		pnlIngr.add(btnIngr);
		pnlAdd.add(panel1);
		JPanel panel2 = new JPanel();
		btnAddRec = new JButton("Add");
		btnAddRec.addActionListener(this);
		panel2.add(btnAddRec);
		pnlAdd.add(panel2);
		
		//Search Result Panel TODO
		////////////////////////////////////////////////////////
		pnlPers = new JPanel();
		tblPers= new JTable(data, columnNames);
		scrlPnPers = new JScrollPane(tblPers);
		pnlPers.add(scrlPnPers);
		tblPers.getModel().addTableModelListener(this);
		
		//Recipe Panel TODO
		////////////////////////////////////////////////////////
		
		//Change Password Panel TODO
		////////////////////////////////////////////////////////
		pnlPass = new JPanel();
		String[] label = {"Old", "New", "New"};
		for(int i = 0; i < 3; i++){
			JPanel panel = new JPanel();
			JLabel str = new JLabel(label[i]);
			panel.add(str);
			passField[i] = new JPasswordField(25);
			panel.add(passField[i]);
			pnlPass.add(panel1);
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
		lblTop.setHorizontalAlignment(JLabel.CENTER);
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
			
		} else if (e.getSource() == btnRec){
			
		} else if (e.getSource() == btnAdd) {	
			
		} else if (e.getSource() == btnPas) {
		
		} else if (e.getSource() == btnSearch) {
			
		} else if (e.getSource() == btnIngr) {
			
		} else if (e.getSource() == btnSub) {
				
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
				//TODO exists =  pass name and password to DB
				if(exists){
					JOptionPane.showMessageDialog(this, "That Username is Already Taken!", "", JOptionPane.WARNING_MESSAGE);
				} else {
					remove(pnlReg);
					add(pnlLog, BorderLayout.NORTH);
					revalidate();
					repaint();
				}
				
			} else {
				JOptionPane.showMessageDialog(this, "Passwords Do Not Match!", "", JOptionPane.WARNING_MESSAGE);
			}
		
		} else if (e.getSource() == btnLog) {
			String name = txfUsrLog.getText();
			char[] password = pwfPasLog.getPassword();
			if(true){//TODO check user name and password
				remove(pnlLog);
				add(pnlMenu, BorderLayout.NORTH);
				add(pnlFav, BorderLayout.SOUTH);
				revalidate();
				repaint();
			}
			
		} else if (e.getSource() == btnToReg) {
			remove(pnlLog);
			add(pnlReg, BorderLayout.NORTH);
			revalidate();
			repaint();
			
		} else if (e.getSource() == btnAddRec) {
		
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
