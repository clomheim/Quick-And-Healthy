import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

@SuppressWarnings("serial")
public class QAHGUI extends JFrame implements ActionListener, MouseListener
{
	
	private final int FIELD_SIZE = 25;
	
	private JButton btnFav, btnPers, btnAdd, btnPas, btnSearch, btnIngr, btnSub, btnReg, btnLog, btnToReg, btnAddRec, btnLike;
	private JPanel pnlMenu, pnlTop, pnlButtons, pnlFav, pnlPers, pnlIngr, pnlPass, pnlReg, pnlLog, content;
	private RecipeDB db;
	private List<Recipe> list, favList, persList;
	private String[] columnNames = {"Recipe Name"},
			favColNames = {"Recipe Name", "Remove"},
			persColNames = {"Recipe Name", "Remove"};
	private Object[][] data, favData, persData;
	private JTable tblFav, tblPers, tblSearch;
	private JScrollPane scrlPnFav, scrlPnPers, scrlPnSearch, scrlPnAdd;
	private JPanel pnlSearch;
	private JLabel lblTop;
	private JTextField txfSearch, txfUser, txfUsrLog;
	private JPasswordField pwfPass, pwfConf, pwfPasLog;
	private JTextArea txaDirs;
	
	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[4], nutLabel = new JLabel[15];
	private JTextField[] txfField = new JTextField[5], ingrField = new JTextField[20], nutField = new JTextField[15];
	private JPasswordField[] passField = new JPasswordField[3];
	private JCheckBox[] ingrChk = new JCheckBox[20];
	private int ingrSize = 0, maxId = 1000;
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
				if(maxId < list.get(i).getRecipeID()){
					maxId = list.get(i).getRecipeID();
				}
				
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		createComponents();
		setVisible(true);
		setSize(600, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		btnPers = new JButton("Personal Recipes");
		btnPers.addActionListener(this);
		btnAdd = new JButton("Add Recipe");
		btnAdd.addActionListener(this);
		btnPas = new JButton("Change Password");
		btnPas.addActionListener(this);
		pnlButtons.add(btnFav);
		pnlButtons.add(btnPers);
		pnlButtons.add(btnAdd);
		pnlButtons.add(btnPas);
		
		pnlMenu.add(pnlTop, BorderLayout.NORTH);
		pnlMenu.add(pnlButtons, BorderLayout.SOUTH);
		
		//Favorites Panel
		////////////////////////////////////////////////////////
		pnlFav = new JPanel();
		tblFav = new JTable();
		tblFav.addMouseListener(this);
		scrlPnFav = new JScrollPane(tblFav);
		pnlFav.add(scrlPnFav);
		
		//Personal Panel
		////////////////////////////////////////////////////////
		pnlPers = new JPanel();
		tblPers= new JTable();
		tblPers.addMouseListener(this);
		scrlPnPers = new JScrollPane(tblPers);
		pnlPers.add(scrlPnPers);
		
		//Add Panel
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
		JLabel lab2 = new JLabel("Optional");
		ingrChk[ingrSize] = new JCheckBox();
		addPanel1.add(lab);
		addPanel1.add(ingrField[ingrSize]);
		addPanel1.add(lab2);
		addPanel1.add(ingrChk[ingrSize]);
		pnlIngr.add(addPanel1);
		ingrSize++;
		JPanel btn = new JPanel();
		btnIngr = new JButton("Add More Ingredients");
		btnIngr.addActionListener(this);
		btn.add(btnIngr);
		pnlAdd.add(pnlIngr);
		pnlAdd.add(btn);
		JPanel direc = new JPanel();
		direc.add(new JLabel("Enter Directions"));
		txaDirs = new JTextArea(10,25);
		txaDirs.setLineWrap(true);
		direc.add(txaDirs);
		pnlAdd.add(direc);
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
		
		//Search Result Panel
		////////////////////////////////////////////////////////
		pnlSearch = new JPanel();
		tblSearch= new JTable();
		tblSearch.addMouseListener(this);
		scrlPnSearch = new JScrollPane(tblSearch);
		pnlSearch.add(scrlPnSearch);
		
		//Change Password Panel
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
		lblTop = new JLabel("Quick And Healthy Meals");
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
	
	private void createRecipeFrame(final Recipe recipe){
		JFrame rec = new JFrame(recipe.getRecipeTitle());
		rec.setLayout(new BoxLayout(rec.getContentPane(), BoxLayout.PAGE_AXIS));
		JPanel tops = new JPanel();
		JPanel btn = new JPanel();
		btn.setAlignmentX(Component.RIGHT_ALIGNMENT);
		tops.setLayout(new GridLayout(1, 2));
		tops.setAlignmentX(Component.LEFT_ALIGNMENT);
		btnLike = new JButton("Favorite");
		btnLike.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					db.addFavorite(recipe, username);
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
		});
		for(Recipe r: favList){
			if(r.getRecipeID() == recipe.getRecipeID()){
				btnLike.setEnabled(false);
				btnLike.setVisible(false);
			}
		}
		btnLike.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblTop.setAlignmentX(Component.LEFT_ALIGNMENT);
		btn.add(btnLike);
		tops.add(lblTop);
		tops.add(btn);
		rec.add(tops);
		JLabel ti = new JLabel("Recipe: " + recipe.getRecipeTitle());
		ti.setAlignmentX(Component.LEFT_ALIGNMENT);
		rec.add(ti);
		JPanel panel1 = new JPanel();
		panel1.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		JLabel cat = new JLabel("Category: " + recipe.getCategory());
		JLabel prep = new JLabel("Prep Time: " + recipe.getPrepTime().toString());
		JLabel cook = new JLabel("Cook Time: " + recipe.getCookTime().toString());
		panel1.add(cat);
		panel1.add(prep);
		panel1.add(cook);
		rec.add(panel1);
		JTextArea dir = new JTextArea(recipe.getDirections());
		dir.setEditable(false);
		dir.setColumns(20);
		JPanel super2 = new JPanel();
		super2.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel direct = new JLabel("Directions:");
		direct.setAlignmentY(Component.TOP_ALIGNMENT);
		super2.add(direct);
		super2.add(dir);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		List<String> ing = recipe.getIngredients();
		for(int i = 0; i< ing.size();i++){
			panel2.add(new JLabel(ing.get(i)));
		}
		super2.add(panel2);
		rec.add(super2);
		NutritionalInfo ni = recipe.getNutritionalInfo();
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(14, 2));
		panel3.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel3.add(new JLabel("Serving Size: " + Float.toString(ni.getServingSize())));
		panel3.add(new JLabel(ni.getServingSizeUnit()));
		panel3.add(new JLabel("Calories"));
		panel3.add(new JLabel(Integer.toString(ni.getCalories())));
		panel3.add(new JLabel("Calories From Fat"));
		panel3.add(new JLabel(Integer.toString(ni.getCaloriesFromFat())));
		panel3.add(new JLabel("Saturated Fat"));
		panel3.add(new JLabel(Integer.toString(ni.getSaturatedFat()) + "g"));
		panel3.add(new JLabel("Cholesterol"));
		panel3.add(new JLabel(Integer.toString(ni.getCholesterol()) + "mg"));
		panel3.add(new JLabel("Sodium"));
		panel3.add(new JLabel(Integer.toString(ni.getSodium()) + "mg"));
		panel3.add(new JLabel("Total Carbohydrates"));
		panel3.add(new JLabel(Integer.toString(ni.getTotalCarbohydrates()) + "g"));
		panel3.add(new JLabel("Dietary Fiber"));
		panel3.add(new JLabel(Integer.toString(ni.getDietaryFiber()) + "g"));
		panel3.add(new JLabel("Sugars"));
		panel3.add(new JLabel(Integer.toString(ni.getSugars()) + "g"));
		panel3.add(new JLabel("Protein"));
		panel3.add(new JLabel(Integer.toString(ni.getProtein()) + "g"));
		panel3.add(new JLabel("Vitamin A"));
		panel3.add(new JLabel(Integer.toString(ni.getVitaminA()) + "%"));
		panel3.add(new JLabel("VItamin C"));
		panel3.add(new JLabel(Integer.toString(ni.getVitaminC()) + "%"));
		panel3.add(new JLabel("Calcium"));
		panel3.add(new JLabel(Integer.toString(ni.getCalcium()) + "%"));
		panel3.add(new JLabel("Iron"));
		panel3.add(new JLabel(Integer.toString(ni.getIron()) + "%"));
		
		rec.setLocationRelativeTo(this);
		rec.add(panel3);
		rec.pack();
		rec.setVisible(true);
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
			try {
				favList = db.getUserFavorites(username);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			favData = new Object[favList.size()][favColNames.length];
			for (int i=0; i<favList.size(); i++) {
				favData[i][0] = favList.get(i).getRecipeTitle();
				favData[i][1] = "Remove";
			}
			tblFav = new JTable(favData, favColNames);
			tblFav.addMouseListener(this);
			scrlPnFav = new JScrollPane(tblFav);
			pnlFav.removeAll();
			pnlFav.add(scrlPnFav);
			content.removeAll();
			content.add(pnlFav);
			revalidate();
			repaint();
		} else if (e.getSource() == btnPers){
			try {
				persList = db.getUserRecipes(username);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			persData = new Object[persList.size()][persColNames.length];
			for (int i=0; i<persList.size(); i++) {
				persData[i][0] = persList.get(i).getRecipeTitle();
				persData[i][1] = "Remove";
			}
			tblPers = new JTable(persData, persColNames);
			tblPers.addMouseListener(this);
			scrlPnPers = new JScrollPane(tblPers);
			pnlPers.removeAll();
			pnlPers.add(scrlPnPers);
			content.removeAll();
			content.add(pnlPers);
			revalidate();
			repaint();
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
			String search = txfSearch.getText();
			try {
				list = db.searchRecipes(search);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			data = new Object[list.size()][columnNames.length];
			for (int i=0; i<list.size(); i++) {
				data[i][0] = list.get(i).getRecipeTitle();
			}
			tblSearch = new JTable(data, columnNames);
			tblSearch.addMouseListener(this);
			scrlPnSearch = new JScrollPane(tblSearch);
			pnlSearch.removeAll();
			pnlSearch.add(scrlPnSearch);
			content.removeAll();
			content.add(pnlSearch);
			revalidate();
			repaint();
		} else if (e.getSource() == btnIngr) {
			JPanel ingr = new JPanel();
			ingr.add(new JLabel("Enter Ingredient"));
			ingrField[ingrSize] = new JTextField(FIELD_SIZE);
			ingr.add(ingrField[ingrSize]);
			ingr.add(new JLabel("Optional"));
			ingrChk[ingrSize] = new JCheckBox();
			ingr.add(ingrChk[ingrSize]);
			ingrSize++;
			pnlIngr.add(ingr);
			if(ingrSize == 20){
				btnIngr.setEnabled(false);
			}
			revalidate();
			repaint();
		} else if (e.getSource() == btnSub) {
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
					if (db.loginValidation(username, oldPassword)) {
						db.changePassword(username, newPassword);
						JOptionPane.showMessageDialog(this, "Your Password has been updated!");
						passField[0].setText("");
						passField[1].setText("");
						passField[2].setText("");
					} else {
						JOptionPane.showMessageDialog(this, "Password and Username Do Not Match!", "", JOptionPane.WARNING_MESSAGE);
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
					exists =  db.userExists(name);
				} catch (SQLException ex){
					ex.printStackTrace();
				}
				if(exists){
					JOptionPane.showMessageDialog(this, "That Username is Already Taken!", "", JOptionPane.WARNING_MESSAGE);
				} else {
					String pass = new String(password);
					try{
						db.registerUser(name, pass);
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
				if(db.loginValidation(username, password)){
					remove(pnlLog);
					add(pnlMenu, BorderLayout.NORTH);
					favList = db.getUserFavorites(username);
					favData = new Object[favList.size()][favColNames.length];
					for (int i=0; i<favList.size(); i++) {
						favData[i][0] = favList.get(i).getRecipeTitle();
						favData[i][1] = "Remove";
					}
					tblFav = new JTable(favData, favColNames);
					tblFav.addMouseListener(this);
					scrlPnFav = new JScrollPane(tblFav);
					pnlFav.removeAll();
					pnlFav.add(scrlPnFav);
					content = new JPanel();
					content.add(pnlFav);
					add(content, BorderLayout.CENTER);
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
			boolean good = true;
			String title = txfField[0].getText();
			String cate = txfField[1].getText();
			String pTime =txfField[2].getText();
			String cTime = txfField[3].getText();
			String[] ingrs = new String[ingrSize];
			String directions = txaDirs.getText();
			String[] nutInfo = new String[15];
			boolean[] opt = new boolean[ingrSize];
			for (int i = 0; i < ingrSize; i++){
				ingrs[i] = ingrField[i].getText();
				opt[i] = ingrChk[i].isSelected();
			}
			for(int j = 0; j < 15; j++){
				nutInfo[j] = nutField[j].getText();
			}
			maxId++;
			Time prepTime = Time.valueOf(pTime);
			Time cookTime = Time.valueOf(cTime);
			String str = "00:15:00";
			Time quick = Time.valueOf(str);
			List<String> ingredients = (Arrays.asList(ingrs));
			NutritionalInfo ni = new NutritionalInfo(Float.parseFloat(nutInfo[0]), nutInfo[1], Integer.parseInt(nutInfo[2]), Integer.parseInt(nutInfo[3]), 
					Integer.parseInt(nutInfo[4]), Integer.parseInt(nutInfo[5]), Integer.parseInt(nutInfo[6]), Integer.parseInt(nutInfo[7]), Integer.parseInt(nutInfo[8]),
					Integer.parseInt(nutInfo[9]), Integer.parseInt(nutInfo[10]), Integer.parseInt(nutInfo[11]), Integer.parseInt(nutInfo[12]), Integer.parseInt(nutInfo[13]), 
					Integer.parseInt(nutInfo[14])); 
			if(cate.equalsIgnoreCase("Quick and Healthy")){
				if(!(prepTime.getTime() <= quick.getTime())){
					JOptionPane.showMessageDialog(this, "The Preporation Time is too Long for a Quick and Healthy Meal.", "", JOptionPane.WARNING_MESSAGE);
					good = false;
				}
				if(ni.getCalories() > 599){
					JOptionPane.showMessageDialog(this, "There are too many calories for a Quick and Healthy Meal.", "", JOptionPane.WARNING_MESSAGE);
					good = false;
				}
			}
			if(good){
				Recipe recipe = new Recipe(maxId, title, cate, prepTime, cookTime, ingredients, directions, ni);
			
				try {
					db.addRecipe(username, recipe);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			
				content.removeAll();
				content.add(pnlPers);
				revalidate();
				repaint();
			
				btnIngr.setEnabled(true);
				for (int i = 0; i < txfField.length; i++){
					if(txfField[i] != null)
						txfField[i].setText("");
					}
				for (int i = 0; i < ingrSize;i++){
					ingrField[i].setText("");
				}
				ingrSize = 0;
				JLabel lab = new JLabel("Enter Ingredient");;
				pnlIngr.removeAll();
				pnlIngr.add(lab);
				pnlIngr.add(ingrField[ingrSize]);
				ingrSize++;
				for (int i = 0; i < nutField.length;i++){
					nutField[i].setText("");
				}
				txaDirs.setText("");
			}
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		JTable tab = (JTable)arg0.getSource();
		int row = tab.getSelectedRow();
		int column = tab.getSelectedColumn();
		if(tab.equals(tblFav)){
			if(column == 0){;
				Recipe rec = favList.get(row);
				createRecipeFrame(rec);
			} else {
				Recipe rec = favList.get(row);
				try {
					db.removeFavorite(rec);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(this, "Please refresh the View to see your changes.");
			}
		}
		if(tab.equals(tblPers)){
			if(column == 0){
				Recipe rec = persList.get(row);
				createRecipeFrame(rec);
			} else {
				Recipe rec = persList.get(row);
				try {
					db.removeRecipe(rec);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(this, "Please refresh the View to see your changes.");
			}
		}
		if(tab.equals(tblSearch)){
			Recipe rec = list.get(row);
			createRecipeFrame(rec);
		}
		
		revalidate();
		repaint();
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {	
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {	
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
