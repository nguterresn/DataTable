import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Color;

public class DataTable extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	
	public JTextField Name;
	public JTextField Email;
	public JTextField Phone;
	public JTextField Op;
	public JButton Add;
	
	int i = 0;
	int j = 0;
	
	public JTable table;
	public DefaultTableModel tableModel = new DefaultTableModel();  
	BufferedWriter writer = null;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataTable frame = new DataTable();
					frame.setVisible(true);
					frame.GetDataFromFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void GetDataFromFile() throws FileNotFoundException, IOException 
	{
		int len = 0;
		String parts[];
		
		try(BufferedReader br = new BufferedReader(new FileReader("DataTable.txt"))) {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		        j++;
		    }
		    
		    String lines[] = sb.toString().split("\\r?\\n");
		    
		    for (int k = 0; k < j; k++) {
		    	
		    	parts = lines[k].split(" ");
		    	
		    	len = parts.length;
		    	
		    	if (len == 4) {
		    		tableModel.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3]});
		    	}
		    	
		    	if (len == 5) {
		    		tableModel.addRow(new Object[]{parts[0], parts[1], parts[2], parts[3], parts[4]});
		    	}
		    	
		    	try {
		    		   i = Integer.parseInt(parts[0])+1;
		    		}
		    		catch (NumberFormatException e)
		    		{
		    		   i = 0;
		    		}
		    	
		    }
		}
	}
	

	/**
	 * Create the frame.
	 */
	public DataTable() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSuccess = new JLabel("Success!");
		lblSuccess.setForeground(new Color(50, 205, 50));
		lblSuccess.setBounds(913, 61, 61, 16);
		lblSuccess.setVisible(false);
		contentPane.add(lblSuccess);
		
		JLabel lblError = new JLabel("Error!");
		lblError.setForeground(new Color(255, 0, 0));
		lblError.setBounds(913, 61, 61, 16);
		lblError.setVisible(false);
		contentPane.add(lblError);
		
		Name = new JTextField();
		Name.setBounds(84, 56, 251, 25);
		contentPane.add(Name);
		Name.setColumns(10);
		
		Email = new JTextField();
		Email.setBounds(347, 56, 215, 25);
		contentPane.add(Email);
		Email.setColumns(10);
		
		Phone = new JTextField();
		Phone.setBounds(574, 56, 130, 25);
		contentPane.add(Phone);
		Phone.setColumns(10);
		
		Op = new JTextField();
		Op.setBounds(84, 105, 1032, 25);
		contentPane.add(Op);
		Op.setColumns(10);
		
		Add = new JButton("Add");
		Add.setBounds(1003, 43, 117, 54);
		contentPane.add(Add);
		
		Add.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	
		    	System.out.print("Adding Row...");	
		    
		    	
		    	if (Name.getText().equals("") || Email.getText().equals("") || Phone.getText().equals("")) {
		    		lblSuccess.setVisible(false);
		    		lblError.setVisible(true);
		    	} 
		    	else {
		    		
		    		tableModel.addRow(new Object[]{i, Name.getText(), Email.getText(), Phone.getText(), Op.getText()});
		    		
		    		String rowdata = i + " " + Name.getText() + " " + Email.getText() + " " + Phone.getText() + " " + Op.getText(); 
		    		lblSuccess.setVisible(true);
		    		lblError.setVisible(false);
			    	
			    	try {
						writer = new BufferedWriter(new FileWriter("DataTable.txt", true));
			    	    writer.write(rowdata);
			    	    writer.newLine();
			    	    writer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						lblSuccess.setVisible(false);
			    		lblError.setVisible(true);
					}
			    	
			    	i++;
			    	
		    	}
		    	
		        Name.setText("");
		        Email.setText("");
		        Phone.setText("");
		        Op.setText("");
		    }
		});
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(84, 36, 61, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("E-mail");
		lblNewLabel_1.setBounds(347, 36, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Phone number");
		lblNewLabel_2.setBounds(574, 36, 129, 16);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Opcional field");
		lblNewLabel_3.setBounds(84, 86, 130, 16);
		contentPane.add(lblNewLabel_3);
		
		tableModel.addColumn("ID");
		tableModel.addColumn("Name");
		tableModel.addColumn("Email");
		tableModel.addColumn("Phone number");
		tableModel.addColumn("Opcional field");
		table = new JTable(tableModel);
		table.getColumn("ID").setMaxWidth(30);
		table.getColumn("Phone number").setMinWidth(60);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.setBounds(84, 142, 902, 496);
		contentPane.add(table);
		
		JScrollPane scrollBar = new JScrollPane(table);
		scrollBar.setBounds(84, 142, 1036, 577);
		contentPane.add(scrollBar);
		
	}
}
