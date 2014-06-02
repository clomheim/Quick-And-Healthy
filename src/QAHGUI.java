import java.awt.BorderLayout;
import java.awt.FlowLayout;
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
	
	private JButton btnFav, btnRec, btnAdd, btnPas, btnSearch;
	private JPanel pnlMenu, pnlTop, pnlButtons, pnlFav;
	private Object db;// TODO Change Object to Database Type
	private List<Object> list;// TODO Change object to Recipe?
	private String[] columnNames = {"Recipe Name"};
	
	private Object[][] data;
	private JTable table;
	private JScrollPane scrollPane;
	private JPanel pnlSearch;
	private JLabel lblTop, lblRecipe;
	private JTextField txfSearch, txfTitle;
	private JButton btnTitleSearch;
	
	private JPanel pnlAdd;
	private JLabel[] txfLabel = new JLabel[5];
	private JTextField[] txfField = new JTextField[5];
	private JButton btnAddMovie;
	
	
	public QAHGUI() {
		super("Quick And Healthy Meals");
		
		//db = new Object(); // TODO change to Database Type
		/*try  														// TODO Read in Data
		{
			list = db.getMovies();
			
			data = new Object[list.size()][columnNames.length];
			for (int i=0; i<list.size(); i++) {
				data[i][0] = list.get(i).getTitle();
				data[i][1] = list.get(i).getYear();
				data[i][2] = list.get(i).getLength();
				data[i][3] = list.get(i).getGenre();
				data[i][4] = list.get(i).getStudioName();
				
			}
			
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		createComponents();
		setVisible(true);
		setSize(500, 500);
	}
    
	private void createComponents()
	{
		//Menu Panel
////////////////////////////////////////////////////////
		pnlMenu = new JPanel();
		
		pnlTop = new JPanel();
		lblTop = new JLabel("Quick And Healthy Meals");
		txfSearch = new JTextField(45);
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
		add(pnlMenu, BorderLayout.NORTH);
		
		//Favorites Panel TODO
////////////////////////////////////////////////////////
		pnlFav = new JPanel();
		table = new JTable(data, columnNames);
		scrollPane = new JScrollPane(table);
		pnlFav.add(scrollPane);
		table.getModel().addTableModelListener(this);
		
		//Personal Panel TODO
////////////////////////////////////////////////////////
		pnlSearch = new JPanel();
		lblTitle = new JLabel("Enter Title: ");
		txfTitle = new JTextField(25);
		btnTitleSearch = new JButton("Search");
		btnTitleSearch.addActionListener(this);
		pnlSearch.add(lblTitle);
		pnlSearch.add(txfTitle);
		pnlSearch.add(btnTitleSearch);
		
		//Add Panel TODO
////////////////////////////////////////////////////////
		pnlAdd = new JPanel();
		pnlAdd.setLayout(new GridLayout(6, 0));
		String labelNames[] = {"Enter Title: ", "Enter Year: ", "Enter Length: ", "Enter Genre: ", "Enter Studio Name: "};
		for (int i=0; i<labelNames.length; i++) {
			JPanel panel = new JPanel();
			txfLabel[i] = new JLabel(labelNames[i]);
			txfField[i] = new JTextField(25);
			panel.add(txfLabel[i]);
			panel.add(txfField[i]);
			pnlAdd.add(panel);
		}
		JPanel panel = new JPanel();
		btnAddMovie = new JButton("Add");
		btnAddMovie.addActionListener(this);
		panel.add(btnAddMovie);
		pnlAdd.add(panel);
		
		add(pnlContent, BorderLayout.CENTER);
		
		//Search Result Panel TODO
////////////////////////////////////////////////////////
		
		//Recipe Panel TODO
////////////////////////////////////////////////////////
		
		//Change Password Panel TODO
////////////////////////////////////////////////////////
		
		//Register Panel TODO
////////////////////////////////////////////////////////
		
		//Login Panel TODO
////////////////////////////////////////////////////////
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new QAHGUI();

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == btnList) {
//			try {
//				list = db.getMovies();
//			} catch (SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			data = new Object[list.size()][columnNames.length];
//			for (int i=0; i<list.size(); i++) {
//				data[i][0] = list.get(i).getTitle();
//				data[i][1] = list.get(i).getYear();
//				data[i][2] = list.get(i).getLength();
//				data[i][3] = list.get(i).getGenre();
//				data[i][4] = list.get(i).getStudioName();
//			}
//			pnlContent.removeAll();
//			table = new JTable(data, columnNames);
//			table.getModel().addTableModelListener(this);
//			scrollPane = new JScrollPane(table);
//			pnlContent.add(scrollPane);
//			pnlContent.revalidate();
//			this.repaint();
//			
//		} else if (e.getSource() == btnSearch) {
//			pnlContent.removeAll();
//			pnlContent.add(pnlSearch);
//			pnlContent.revalidate();
//			this.repaint();
//		} else if (e.getSource() == btnAdd) {
//			pnlContent.removeAll();
//			pnlContent.add(pnlAdd);
//			pnlContent.revalidate();
//			this.repaint();
//			
//		} else if (e.getSource() == btnTitleSearch) {
//			String title = txfTitle.getText();
//			if (title.length() > 0) {
//				list = db.getMovies(title);
//				data = new Object[list.size()][columnNames.length];
//				for (int i=0; i<list.size(); i++) {
//					data[i][0] = list.get(i).getTitle();
//					data[i][1] = list.get(i).getYear();
//					data[i][2] = list.get(i).getLength();
//					data[i][3] = list.get(i).getGenre();
//					data[i][4] = list.get(i).getStudioName();
//				}
//				pnlContent.removeAll();
//				table = new JTable(data, columnNames);
//				table.getModel().addTableModelListener(this);
//				scrollPane = new JScrollPane(table);
//				pnlContent.add(scrollPane);
//				pnlContent.revalidate();
//				this.repaint();
//			}
//		} else if (e.getSource() == btnAddMovie) {
//			Movie movie = new Movie(txfField[0].getText(), Integer.parseInt(txfField[1].getText())
//					,Integer.parseInt(txfField[2].getText()), txfField[3].getText(), txfField[4].getText() );
//			db.addMovie(movie);
//			JOptionPane.showMessageDialog(null, "Added Successfully!");
//			for (int i=0; i<txfField.length; i++) {
//				txfField[i].setText("");
//			}
//		}
//		
//	}
//
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
