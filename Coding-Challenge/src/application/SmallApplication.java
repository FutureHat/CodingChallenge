package application;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.io.FilenameUtils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import net.proteanit.sql.DbUtils;

import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class SmallApplication {

	private JFrame frame = new JFrame();
	private JButton csvFile;
	private static JTable tableData;
	private JScrollPane scrollPane;
	private JButton addData;
	public JFileChooser jf = new JFileChooser();
	public static String fileName = "";
	public JButton btnRefresh; 
	public static int validCount=0;
	public static int invalidCount=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SmallApplication window = new SmallApplication();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection connection = null;
	/**
	 * Create the application.
	 */
	public SmallApplication() {
		initialize();
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.setBounds(100, 100, 1131, 718);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		csvFile = new JButton("Insert CSV File");
		csvFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				csvFileAndTable();
			}
		});
		csvFile.setBounds(12, 27, 120, 32);
		frame.getContentPane().add(csvFile);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 72, 1089, 569);
		frame.getContentPane().add(scrollPane);
		
		tableData = new JTable();
		scrollPane.setViewportView(tableData);
		
		addData = new JButton("Insert Data");
		addData.setEnabled(false);
		
		addData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertNewData();
			}
		});
		addData.setBounds(144, 27, 120, 32);
		frame.getContentPane().add(addData);
		
		btnRefresh = new JButton("Refresh Table");
		btnRefresh.setEnabled(false);
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				databaseConnection db = new databaseConnection();
				connection = db.dbConnector();	
				Statement statement;
				try {
					statement = connection.createStatement();
					ResultSet rs = statement.executeQuery("select * from '"+FilenameUtils.getBaseName(jf.getSelectedFile().toString())+"'");
					tableData.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		btnRefresh.setBounds(975, 28, 126, 32);
		frame.getContentPane().add(btnRefresh);

		FileNameExtensionFilter filter = new FileNameExtensionFilter(
		        "CSV files", "csv");
		jf.setFileFilter(filter);
	}
	public void csvFileAndTable() {

		int file = jf.showOpenDialog(null);
		if(file==JFileChooser.APPROVE_OPTION){
			fileName = FilenameUtils.getBaseName(jf.getSelectedFile().toString());
			
			if(FilenameUtils.getExtension(jf.getSelectedFile().toString()).equalsIgnoreCase("csv")) {
				CSVReader reader = null;
				try {
					reader = new CSVReader(new FileReader(jf.getSelectedFile().toString()));
					databaseConnection db = new databaseConnection();
					connection = db.dbConnector();	
					Statement statement = connection.createStatement();
					//Check if the table exist
					ResultSet tableChecker = statement.executeQuery("Select name from sqlite_master  WHERE type = 'table' AND name = '"+FilenameUtils.getBaseName(jf.getSelectedFile().toString())+"'");
					if(!tableChecker.next()) {
						createTable(jf);
						insertDataFromCSV(jf, reader);
						ResultSet rs = statement.executeQuery("select * from '"+FilenameUtils.getBaseName(jf.getSelectedFile().toString())+"'");
						tableData.setModel(DbUtils.resultSetToTableModel(rs));
					}
					else {
						//If table exist, ask for overwrite the data or not
						if(JOptionPane.showConfirmDialog(null, "CSV already exist, do you want to overwrite it?", "Warning",
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
							statement.executeUpdate("DROP TABLE '"+FilenameUtils.getBaseName(jf.getSelectedFile().toString())+"';");
							createTable(jf);
							insertDataFromCSV(jf, reader);
							ResultSet rs = statement.executeQuery("select * from '"+FilenameUtils.getBaseName(jf.getSelectedFile().toString())+"'");
							tableData.setModel(DbUtils.resultSetToTableModel(rs));
						}
						else {
							ResultSet rs = statement.executeQuery("select * from '"+FilenameUtils.getBaseName(jf.getSelectedFile().toString())+"'");
							tableData.setModel(DbUtils.resultSetToTableModel(rs));
						}	
					}
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				addData.setEnabled(true);
				btnRefresh.setEnabled(true);
			}
			else {
				 JOptionPane.showMessageDialog(null, "Not a CSV file, Please choose other file",
					      "Error", JOptionPane.ERROR_MESSAGE);
				 addData.setEnabled(false);
				 btnRefresh.setEnabled(false);
				 
			}
			
		}
	}
	
	public void createTable(JFileChooser jf) {	
		try {
			databaseConnection db = new databaseConnection();
			connection = db.dbConnector();	
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE '"+FilenameUtils.getBaseName(jf.getSelectedFile().toString())+"'(	A TEXT NOT NULL," +
					"B TEXT NOT NULL,"+
					"C TEXT NOT NULL,"+
					"D TEXT NOT NULL,"+
					"E TEXT NOT NULL,"+
					"F TEXT NOT NULL,"+
					"G TEXT NOT NULL,"+
					"H TEXT NOT NULL,"+
					"I TEXT NOT NULL,"+
					"J TEXT NOT NULL );");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void insertDataFromCSV(JFileChooser jf, CSVReader reader) {
		String[] line;
		try {
			databaseConnection db = new databaseConnection();
			connection = db.dbConnector();	
			Statement statement = connection.createStatement();
			String insert = "INSERT INTO "+FilenameUtils.getBaseName(jf.getSelectedFile().toString())+ " (A, B, C, D, E, F, G, H, I, J) "
							+"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			 PreparedStatement pstmt = connection.prepareStatement(insert);
			 
			while((line = reader.readNext())!= null) {
				pstmt.setString(1, line[0]);
				pstmt.setString(2, line[1]);
				pstmt.setString(3, line[2]);
				pstmt.setString(4, line[3]);
				pstmt.setString(5, line[4]);
				pstmt.setString(6, line[5]);
				pstmt.setString(7, line[6]);
				pstmt.setString(8, line[7]);
				pstmt.setString(9, line[8]);
				pstmt.setString(10, line[9]);
				pstmt.executeUpdate();
			}
		} catch (CsvValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	public void insertNewData() {
		AddNewData newData = new AddNewData();
		newData.setVisible(true);
		
	}
}
