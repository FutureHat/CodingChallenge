package application;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.routines.EmailValidator;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.awt.event.ActionEvent;

public class AddNewData extends JFrame {

	private JPanel contentPane;
	private JTextField txtA;
	private JLabel lblA;
	private JLabel lblB;
	private JTextField txtB;
	private JLabel lblC;
	private JTextField txtC;
	private JRadioButton buttonMale;
	private JRadioButton buttonFemale;
	private ButtonGroup gender;
	private JLabel lblD;
	private JLabel lblE;
	private JTextField txtE;
	private JTextField txtF;
	private JLabel lblF;
	private JLabel lblG;
	private JTextField txtG;
	private JRadioButton btnHTrue;
	private JRadioButton btnHFalse;
	private ButtonGroup groupH;
	private ButtonGroup groupI;
	private JLabel lblH;
	private JLabel lblI;
	private JRadioButton btnITrue;
	private JRadioButton btnIFalse;
	private JLabel lblJ;
	private JTextField txtJ;
	private JButton btnAddData;
	public String A;
	public String B;
	public String C;
	public String D;
	public String E;
	public String F;
	public String G;
	public String H;
	public String I;
	public String J;
	public boolean validData;
	FileWriter pw;
	FileWriter logs;
	Connection connection = null;	
	databaseConnection db = new databaseConnection();
	ResultSet tableChecker;
	
	    

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddNewData frame = new AddNewData();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddNewData() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 379, 555);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		txtA = new JTextField();
		txtA.setBounds(69, 38, 262, 29);
		contentPane.add(txtA);
		txtA.setColumns(10);
		
		lblA = new JLabel("A");
		lblA.setBounds(42, 44, 25, 16);
		contentPane.add(lblA);
		
		lblB = new JLabel("B");
		lblB.setBounds(42, 79, 25, 16);
		contentPane.add(lblB);
		
		txtB = new JTextField();
		txtB.setColumns(10);
		txtB.setBounds(69, 73, 262, 29);
		contentPane.add(txtB);
		
		lblC = new JLabel("C");
		lblC.setBounds(42, 114, 25, 16);
		contentPane.add(lblC);
		
		txtC = new JTextField();
		txtC.setColumns(10);
		txtC.setBounds(69, 108, 262, 29);
		contentPane.add(txtC);
		
		buttonMale = new JRadioButton("Male");
		buttonMale.setBounds(69, 147, 92, 29);
		contentPane.add(buttonMale);
		
		buttonFemale = new JRadioButton("Female");
		buttonFemale.setBounds(165, 147, 92, 29);
		contentPane.add(buttonFemale);
		buttonMale.setSelected(true);
		gender = new ButtonGroup();
		gender.add(buttonFemale);
		gender.add(buttonMale);
		lblD = new JLabel("D");
		lblD.setBounds(42, 147, 19, 29);
		contentPane.add(lblD);
		
		txtF = new JTextField();
		txtF.setBounds(69, 220, 262, 29);
		contentPane.add(txtF);
		txtF.setColumns(10);
		
		lblF = new JLabel("F");
		lblF.setBounds(42, 226, 25, 16);
		contentPane.add(lblF);
		
		lblG = new JLabel("G");
		lblG.setBounds(42, 261, 25, 16);
		contentPane.add(lblG);
		
		txtG = new JTextField();
		txtG.setColumns(10);
		txtG.setBounds(69, 255, 262, 29);
		contentPane.add(txtG);
		
		btnHTrue = new JRadioButton("True");
		btnHTrue.setBounds(69, 290, 92, 29);
		contentPane.add(btnHTrue);
		
		btnHFalse = new JRadioButton("False");
		btnHFalse.setBounds(165, 290, 92, 29);
		contentPane.add(btnHFalse);
		btnHTrue.setSelected(true);
		groupH = new ButtonGroup();
		groupH.add(btnHTrue);
		groupH.add(btnHFalse);
		
		lblH = new JLabel("H");
		lblH.setBounds(42, 290, 19, 29);
		contentPane.add(lblH);
		
		lblI = new JLabel("I");
		lblI.setBounds(42, 324, 19, 29);
		contentPane.add(lblI);
		btnITrue = new JRadioButton("True");
		btnITrue.setBounds(69, 324, 92, 29);
		contentPane.add(btnITrue);

		btnITrue.setSelected(true);
		
		btnIFalse = new JRadioButton("False");
		btnIFalse.setBounds(165, 324, 92, 29);
		contentPane.add(btnIFalse);
		groupI = new ButtonGroup();
		groupI.add(btnITrue);
		groupI.add(btnIFalse);
		lblJ = new JLabel("J");
		lblJ.setBounds(42, 360, 25, 16);
		contentPane.add(lblJ);
		
		txtJ = new JTextField();
		txtJ.setColumns(10);
		txtJ.setBounds(69, 354, 262, 29);
		contentPane.add(txtJ);
		
		btnAddData = new JButton("Add Data");
		
		btnAddData.setBounds(42, 413, 289, 36);
		contentPane.add(btnAddData);
		
		lblE = new JLabel("E");
		lblE.setBounds(42, 191, 25, 16);
		contentPane.add(lblE);
		
		txtE = new JTextField();
		txtE.setColumns(10);
		txtE.setBounds(69, 185, 262, 29);
		contentPane.add(txtE);
		btnAddData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dataChecker();
				if(validData) {
					addData();
				}
				else {
					invalidData();
				}
				dispose();
			}
		});
		
		
	}
	public void dataChecker() {
		validData = true;
		if(StringUtils.isAlpha(txtA.getText().toString())) {
			A = txtA.getText().toString();
		}
		else {
			A = txtA.getText().toString();
			validData =  false;
			JOptionPane.showMessageDialog(null, "Input A / Firstname must be letters", "Wrong Input", JOptionPane.ERROR_MESSAGE);
		}
		
		if(StringUtils.isAlpha(txtB.getText().toString())) {
			B = txtB.getText().toString();
		}
		else {
			B = txtB.getText().toString();
			validData =  false;
			JOptionPane.showMessageDialog(null, "Input B / Lastname must be letters", "Wrong Input", JOptionPane.ERROR_MESSAGE);
		}
		EmailValidator validator = EmailValidator.getInstance();
		if(validator.isValid(txtC.getText().toString())) {
			C = txtC.getText().toString();
		}
		else {
			C = txtC.getText().toString();
			validData =  false;
			JOptionPane.showMessageDialog(null, "Input C / Invalid email", "Wrong Input", JOptionPane.ERROR_MESSAGE);

		}
		
		if(buttonMale.isSelected()) {
			D = "Male";
		}
		else if (buttonFemale.isSelected()) {
			D = "Female";
		}
		
		if(!txtE.getText().toString().isEmpty()) {
			E = txtE.getText().toString();
		}
		else {
			E = txtE.getText().toString();
			validData =  false;
			JOptionPane.showMessageDialog(null, "Input E", "Wrong Input", JOptionPane.ERROR_MESSAGE);
		}
		
		if(!txtF.getText().toString().isEmpty()) {
			F= txtF.getText().toString();
		}
		else {
			F= txtF.getText().toString();
			validData =  false;
			JOptionPane.showMessageDialog(null, "Input F", "Wrong Input", JOptionPane.ERROR_MESSAGE);
		}
		
		if(NumberUtils.isNumber(txtG.getText().toString())) {
			G = txtG.getText().toString();
		}
		else {
			G = txtG.getText().toString();
			validData =  false;
			JOptionPane.showMessageDialog(null, "Input G/ G must be number", "Wrong Input", JOptionPane.ERROR_MESSAGE);
		}
		
		if(btnHTrue.isSelected()) {
			H = "TRUE";
		}
		else if(btnHFalse.isSelected()) {
			H = "FALSE";
		}
		
		if(btnITrue.isSelected()) {
			I = "TRUE";
		}
		else if (btnITrue.isSelected()) {
			I = "FALSE";
		}
		if(!txtJ.getText().toString().isEmpty()) {
			J= txtJ.getText().toString();
		}
		else {
			J= txtJ.getText().toString();
			validData =  false;
			JOptionPane.showMessageDialog(null, "Input J", "Wrong Input", JOptionPane.ERROR_MESSAGE);
		}
		
		    
		
		
		
	}
	public void addData() {		
		try {
			connection = db.dbConnector();	
			String insert = "INSERT INTO "+SmallApplication.fileName+ " (A, B, C, D, E, F, G, H, I, J) "
					+"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement pstmt = connection.prepareStatement(insert);
			pstmt.setString(1, A);
			pstmt.setString(2, B);
			pstmt.setString(3, C);
			pstmt.setString(4, D);
			pstmt.setString(5, E);
			pstmt.setString(6, F);
			pstmt.setString(7, "$"+G);
			pstmt.setString(8, H);
			pstmt.setString(9, I);
			pstmt.setString(10, J);
			pstmt.executeUpdate();
			SmallApplication.validCount++;
			logs = new FileWriter(SmallApplication.fileName+".log");
			logs.write("# of records received: "+(SmallApplication.invalidCount+SmallApplication.validCount)+"\n"+"# of records sucessful: "+SmallApplication.validCount+"\n"+"# of records failed: "+SmallApplication.invalidCount);
			logs.close();
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void invalidData() {
		SmallApplication.invalidCount++;
		File fileCheck = new File(SmallApplication.fileName+"-bad.csv");
		
		
		try {
			logs = new FileWriter(SmallApplication.fileName+".log");
			logs.write("# of records received: "+(SmallApplication.invalidCount+SmallApplication.validCount)+"\n"+"# of records sucessful: "+SmallApplication.validCount+"\n"+"# of records failed: "+SmallApplication.invalidCount);
			logs.close();
			pw = new FileWriter(SmallApplication.fileName+"-bad.csv", true);
			if(fileCheck.length()==0) {
				pw.append("A");
				pw.append(",");
				pw.append("B");
				pw.append(",");
				pw.append("C");
				pw.append(",");
				pw.append("D");
				pw.append(",");
				pw.append("E");
				pw.append(",");
				pw.append("F");
				pw.append(",");
				pw.append("G");
				pw.append(",");
				pw.append("H");
				pw.append(",");
				pw.append("I");
				pw.append(",");
				pw.append("J");
				pw.append(",");
				pw.append("\n");
			}
			
			pw.append(A);
		    pw.append(",");
		    pw.append(B);
		    pw.append(",");
		    pw.append(C);
		    pw.append(",");
		    pw.append(D);
		    pw.append(",");
		    pw.append(E);
		    pw.append(",");
		    pw.append(F);
		    pw.append(",");
		    pw.append(G);
		    pw.append(",");
		    pw.append(H);
		    pw.append(",");
		    pw.append(I);
		    pw.append(",");
		    pw.append(J);
		    pw.append(",");
		    pw.append("\n");
		    pw.flush();
		    pw.close();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SecurityException e) {  
	        e.printStackTrace();  
	    }  
      
		
	}
}
