import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.awt.event.ActionEvent;

public class View_salary extends JInternalFrame {
	private JTextField textField;
	private JTable table;
	private int id;
	String company=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_salary frame = new View_salary();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	class DateRenderer extends DefaultTableCellRenderer 
	{
		private static final long serialVersionUID = 1L;
		private java.util.Date dateValue;
		private SimpleDateFormat sdfNewValue = new SimpleDateFormat("dd-MM-yyyy");
		private String valueToString = "";

		@Override
		public void setValue(Object value) {
		    if ((value != null)) {
		        String stringFormat = value.toString();
		        try {
		            dateValue = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(stringFormat);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        valueToString = sdfNewValue.format(dateValue);
		        value = valueToString;
		    }
		    super.setValue(value);
		}
		}
	
	/**
	 * Create the frame.
	 */
	public View_salary() {
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
	        getRootPane().getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
	            public void actionPerformed(ActionEvent e)
	            {
	            	int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
	            	
	            	dispose();
					}
	            }
	        });
	        
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-120;
        int w = (int)(width);
        int h = (int)(height);
		setBounds((w-669)/2, (h-454)/2, 669, 454);
		setClosable(true);
		getContentPane().setLayout(null);
		
		final Connection connection = Databaseconnection.connection2();
		
		try
		{
			String query1 = "select * from Company_temp";
		    PreparedStatement pmt1 = connection.prepareStatement(query1);
		    ResultSet rs1 = pmt1.executeQuery();
		    while(rs1.next())
		    {
		    	company=rs1.getString("Name");
		    }
		    rs1.close();
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 643, 414);
		getContentPane().add(panel);
		
		JLabel lblViewSalary = new JLabel("Salary");
		lblViewSalary.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblViewSalary.setBounds(253, 0, 99, 25);
		panel.add(lblViewSalary);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try 
				{
                    String query = "select ID,Name,Month,Year,Salary,Leave_day from Salary where (Name like '%"+textField.getText()+"%' or Month like '%"+textField.getText()+"%' or Year like '%"+textField.getText()+"%' or Salary like '%"+textField.getText()+"%' or Leave_day like '%"+textField.getText()+"%') and Company='"+company+"'";
                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    pmt.close();
                    rs.close();
                    
                    javax.swing.table.TableColumn col = null;
                    for(int j=0;j<6;j++)
                    {
                        col = table.getColumnModel().getColumn(j);
                        if(j==0)
                        {
                            col.setPreferredWidth(0);
                        }
                        else if(j==1)
                        {
                            col.setPreferredWidth(700);
                        }
                        else
                        {
                            col.setPreferredWidth(100);
                        }
                    }
                }
				catch (SQLException ae) {
                    ae.printStackTrace();
                }
			}
		});
	
		textField.setFont(new Font("Bahnschrift", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(244, 40, 119, 25);
		panel.add(textField);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 78, 623, 291);
		panel.add(scrollPane);
		
		TableModel model = null;
		table = new JTable(model){

	            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int rowIndex, int colIndex) {
	            return false; //Disallow the editing of any cell
	            }
	            };
		table.setFont(new Font("Book Antiqua", Font.PLAIN, 12));

		scrollPane.setViewportView(table);
		try {
		    // pull data from the database 
		  
		    String query = "select ID,Name,Month,Year,Salary,Leave_day from Salary where Company='"+company+"'";
		    PreparedStatement pmt = connection.prepareStatement(query);
		    ResultSet rs = pmt.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
		    
		    javax.swing.table.TableColumn col = null;
            for(int j=0;j<6;j++)
            {
                col = table.getColumnModel().getColumn(j);
                if(j==0)
                {
                    col.setPreferredWidth(0);
                }
                else if(j==1)
                {
                    col.setPreferredWidth(700);
                }
                else
                {
                    col.setPreferredWidth(100);
                }
            }
		
		    
			table.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent arg0) {
	                int r = table.getSelectedRow();
	                String i = (table.getModel().getValueAt(r,0).toString());
	                id = Integer.parseInt(i);
	            }
	        });
		    
		JButton button = new JButton("Delete");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION)
					{
						float t = 0,t2 =0 ;
	                    String n = null;
	                    
	                    String query7 = "select * from Salary where ID ='"+id+"'";
	                    java.sql.PreparedStatement pmt7 = connection.prepareStatement(query7);
	                    ResultSet rs7 = pmt7.executeQuery();
	                    while(rs7.next())
	                    {
	                         t2 = rs7.getFloat("Salary");
	                         n = rs7.getString("Name");
	                    }
	                    
	                    String query8 = "select * from Driver where Name ='"+n+"' and Company='"+company+"'";
	                    java.sql.PreparedStatement pmt8 = connection.prepareStatement(query8);
	                    ResultSet rs8 = pmt8.executeQuery();
	                    while(rs8.next())
	                    {
	                         t = rs8.getFloat("Balance");
	                    }
	                   
	                    float t1 = t - t2;                    
	                    
	                    String query9 = "UPDATE Driver set Balance ="+t1+" where Name ='"+n+"' and Company='"+company+"'";
	                    java.sql.PreparedStatement pmt9 = connection.prepareStatement(query9);
	                    pmt9.executeUpdate();
	                    pmt9.close();
	
						String query2 = "delete from Salary where ID="+id;
	                    PreparedStatement pmt2 = connection.prepareStatement(query2);
	                    pmt2.executeUpdate();
	                    pmt2.close();
	                    
	                    String query3 = "select ID,Name,Month,Year,Salary,Leave_day from Salary where Company='"+company+"'";
	        		    PreparedStatement pmt3 = connection.prepareStatement(query3);
	        		    ResultSet rs3 = pmt3.executeQuery();
	        		    table.setModel(DbUtils.resultSetToTableModel(rs3));
	        		    
	        		    javax.swing.table.TableColumn col = null;
	                    for(int j=0;j<6;j++)
	                    {
	                        col = table.getColumnModel().getColumn(j);
	                        if(j==0)
	                        {
	                            col.setPreferredWidth(0);
	                        }
	                        else if(j==1)
	                        {
	                            col.setPreferredWidth(700);
	                        }
	                        else
	                        {
	                            col.setPreferredWidth(100);
	                        }
	                    }
					}
                }
				catch (SQLException ae) {
                   ae.printStackTrace();
               }
			}
		});
		button.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		button.setBounds(274, 380, 89, 25);
		panel.add(button);
		
		JLabel label = new JLabel("Search");
		label.setFont(new Font("Book Antiqua", Font.BOLD, 16));
		label.setBounds(171, 40, 63, 25);
		panel.add(label);
		}
		catch (SQLException e) {
		    e.printStackTrace();
		}
		
		table.setAutoCreateRowSorter(true);
	}
}