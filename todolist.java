import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.ResultSetMetaData;

import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class todolist extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					todolist frame = new todolist();
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
	Connection conn= null;

	public todolist() {
		conn =(Connection) DB.dbconnect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1303, 668);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(174, 223, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("My To Do List");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel.setBounds(510, 37, 209, 53);
		contentPane.add(lblNewLabel);
		
		JLabel lblImpTask = new JLabel("Imp Task");
		lblImpTask.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblImpTask.setBounds(33, 157, 209, 53);
		contentPane.add(lblImpTask);
		
		JLabel lblNewLabel_1_1 = new JLabel("Other Tasks");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblNewLabel_1_1.setBounds(33, 237, 209, 53);
		contentPane.add(lblNewLabel_1_1);
		
		textField = new JTextField();
		textField.setBounds(252, 173, 297, 31);
		contentPane.add(textField);
		textField.setColumns(10);
		
		final JTextArea textArea = new JTextArea();
		textArea.setBounds(252, 237, 297, 288);
		contentPane.add(textArea);
		
		final JButton btnNewButton = new JButton("ADD");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 22));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					String imp = textField.getText();
					String other = textArea.getText();
					PreparedStatement pst =(PreparedStatement)conn.prepareStatement("INSERT INTO todo(imptask,othertask)VALUES(?,?)");
					pst.setString(1,imp);
					pst.setString(2,other);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"Task Added!");
					textField.setText("");
					textArea.setText("");
					int a;
					PreparedStatement pst1 =(PreparedStatement)conn.prepareStatement("SELECT * FROM todo");
						ResultSet rs = pst1.executeQuery();
						ResultSetMetaData rd = (ResultSetMetaData) rs.getMetaData();
						a=rd.getColumnCount();
						DefaultTableModel df = (DefaultTableModel)table.getModel();
						df.setRowCount(0);
						while(rs.next())
						{
							Vector v2 = new Vector();
							for(int i = 1;i<=a;i++)
							{
								v2.add(rs.getString("id"));
								v2.add(rs.getString("imptask"));
								v2.add(rs.getString("othertask"));
							}
							df.addRow(v2);
						}
;
						}
				catch(Exception e2)
				{
					System.out.println(e2);
				}

				
				
			}
		});
		btnNewButton.setBounds(93, 569, 85, 35);
		contentPane.add(btnNewButton);
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel df = (DefaultTableModel)table.getModel();
				int s =  table.getSelectedRow();
				int id = Integer.parseInt(df.getValueAt(s, 0).toString());
				try
				{
					String imp = textField.getText();
					String other = textArea.getText();
					PreparedStatement pst = (PreparedStatement)conn.prepareStatement("UPDATE todo SET imptask = ?, othertask = ? WHERE id = ? ");
						pst.setString(1,imp);
						pst.setString(2,other);
						pst.setInt(3,id);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null,"List Updated!");
						textField.setText("");
						textArea.setText("");
						int a;
						pst =(PreparedStatement)conn.prepareStatement("SELECT * FROM todo");
							ResultSet rs = pst.executeQuery();
							ResultSetMetaData rd = (ResultSetMetaData) rs.getMetaData();
							a=rd.getColumnCount();
							DefaultTableModel df1 = (DefaultTableModel)table.getModel();
							df.setRowCount(0);
							while(rs.next())
							{
								Vector v2 = new Vector();
								for(int i = 1;i<=a;i++)
								{
									v2.add(rs.getString("id"));
									v2.add(rs.getString("imptask"));
									v2.add(rs.getString("othertask"));
								}
								df.addRow(v2);
							}
				}
				catch(Exception e2)
				{
					System.out.println(e2);
				}
				
			}
		});
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 22));
		btnEdit.setBounds(241, 569, 119, 35);
		contentPane.add(btnEdit);
		
		JButton btnNewButton_1_1 = new JButton("DONE");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 22));
		btnNewButton_1_1.setBounds(419, 569, 117, 35);
		contentPane.add(btnNewButton_1_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(593, 173, 642, 356);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel df = (DefaultTableModel)table.getModel();
				int selected = table.getSelectedRow();
				int id = Integer.parseInt(df.getValueAt(selected, 0).toString());
				textField.setText(df.getValueAt(selected, 1).toString());
				textArea.setText(df.getValueAt(selected, 2).toString());
				btnNewButton.setEnabled(false);
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"id", "Imp Task", "Other task"
			}
		));
	}
}
