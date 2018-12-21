import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;


import net.proteanit.sql.DbUtils;

public class View_bank extends JInternalFrame {
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
					View_bank frame = new View_bank();
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
	public View_bank() {
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
	        getRootPane().getActionMap().put("Cancel", new AbstractAction(){ //$NON-NLS-1$
	            public void actionPerformed(ActionEvent e)
	            {
		            int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION)
					{
						dispose();
					}
	            }
	        });
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight()-120;
        int w = (int)(width);
        int h = (int)(height);
        setBounds((w-1325)/2, (h-457)/2, 1325, 457);
		setClosable(true);
		getContentPane().setLayout(null);
		
		final Connection connection = Databaseconnection.connection2();
		
		try
		{
        	
            String query1 = "select * from Company_temp";
		    PreparedStatement pmt1 = connection.prepareStatement(query1);
		    ResultSet rs = pmt1.executeQuery();
		    
		    while(rs.next())
		    {
		    	company=rs.getString("Name");
		    }
		    rs.close();
		}
        catch (SQLException ae) {
		    ae.printStackTrace();
		}
		
		
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 1299, 417);
		getContentPane().add(panel);
		
		JLabel lblViewAccount = new JLabel("Bank");
		lblViewAccount.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblViewAccount.setBounds(602, 0, 120, 25);
		panel.add(lblViewAccount);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
                    
                    // pull data from the database 
                    
                    String query = "select ID,Bank_name,Account_number,Account_name,Branch,IFSC,Balance from Bank where (Bank_name like '%"+textField.getText()+"%' or Account_number like '%"+textField.getText()+"%' or Branch like '%"+textField.getText()+"%' or Balance like '%"+textField.getText()+"%' or IFSC like '%"+textField.getText()+"%' or Account_name like '%"+textField.getText()+"%') and Company='"+company+"' ";
                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    pmt.close();
                    rs.close();
                    
                    javax.swing.table.TableColumn col = null;
                    for(int j=0;j<7;j++)
                    {
                        col = table.getColumnModel().getColumn(j);
                        if(j==0)
                        {
                            col.setPreferredWidth(0);
                        }
                        else
                        {
                            col.setPreferredWidth(100);
                        }
                    }
                    
                    /*DefaultTableCellRenderer right=new DateRenderer1();
                    right.setHorizontalAlignment(SwingConstants.RIGHT);
                    
                    DefaultTableCellRenderer left=new DefaultTableCellRenderer();
                    left.setHorizontalAlignment(SwingConstants.LEFT);
                    
                    DefaultTableCellRenderer center=new DefaultTableCellRenderer();
                    center.setHorizontalAlignment(SwingConstants.CENTER);
                    
                    DefaultTableCellRenderer vi=new DefaultTableCellRenderer();
                    vi.setVisible(false);
                   
                  */
                    Dimension d=new Dimension(10,0);
                    
                    table.setIntercellSpacing(d);
                    //table.removeColumn(table.getColumn(1));     
                    
                  /*  table.getColumnModel().getColumn(0).setCellRenderer(center);
                    table.getColumnModel().getColumn(5).setCellRenderer(center);
                    table.getColumnModel().getColumn(6).setCellRenderer(right);
                    table.getColumnModel().getColumn(2).setCellRenderer(center);*/
                }
				catch (SQLException ae) {
                    ae.printStackTrace();
                }
			}
		});
		
		textField.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(578, 40, 144, 25);
		panel.add(textField);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 76, 1279, 296);
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
		  
		    String query = "select ID,bank_name,Account_number,Account_name,Branch,IFSC,Balance from Bank where Company='"+company+"'";
		    PreparedStatement pmt = connection.prepareStatement(query);
		    ResultSet rs = pmt.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
		    
		    javax.swing.table.TableColumn col = null;
            for(int j=0;j<7;j++)
            {
                col = table.getColumnModel().getColumn(j);
                if(j==0)
                {
                    col.setPreferredWidth(0);
                }
                else
                {
                    col.setPreferredWidth(100);
                }
            }
            /*DefaultTableCellRenderer right=new DateRenderer1();
            right.setHorizontalAlignment(SwingConstants.RIGHT);
            
            DefaultTableCellRenderer left=new DefaultTableCellRenderer();
            left.setHorizontalAlignment(SwingConstants.LEFT);
            
            DefaultTableCellRenderer center=new DefaultTableCellRenderer();
            center.setHorizontalAlignment(SwingConstants.CENTER);
            
            DefaultTableCellRenderer vi=new DefaultTableCellRenderer();
            vi.setVisible(false);
           */
          
            Dimension d=new Dimension(10,0);
            
            table.setIntercellSpacing(d);
            //table.removeColumn(table.getColumn(1));     
            
           /* table.getColumnModel().getColumn(0).setCellRenderer(center);
            table.getColumnModel().getColumn(5).setCellRenderer(center);
            table.getColumnModel().getColumn(6).setCellRenderer(right);
            table.getColumnModel().getColumn(2).setCellRenderer(center);
		   */ 
			table.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent arg0) {
	                int r = table.getSelectedRow();
	                String i = (table.getModel().getValueAt(r,0).toString());
	                id = Integer.parseInt(i);
	                
	                try
	                
	                {
	                	String query = "delete from Bank_id";
	                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
	                    pmt.executeUpdate();
	                    pmt.close();
	                    
	                    String query1 = "Insert Into Bank_id(ID) Values ('"+id+"')";
	                    java.sql.PreparedStatement pmt1 = connection.prepareStatement(query1);
	                    pmt1.executeUpdate();
	                    pmt1.close();
	                }
	                catch(Exception ae)
	                {
	                	ae.printStackTrace();
	                }
	               
	            }
	        });
		    
		    
		

		JButton button = new JButton("Delete");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
					
					String query = "delete from Bank where ID="+id;
                    PreparedStatement pmt = connection.prepareStatement(query);
                    pmt.executeUpdate();
                    pmt.close();
                    
                    String query1 = "select ID,bank_name,Account_number,Account_name,Branch,IFSC,Balance from Bank where Company='"+company+"'";
        		    PreparedStatement pmt1 = connection.prepareStatement(query1);
        		    ResultSet rs1 = pmt1.executeQuery();
        		    table.setModel(DbUtils.resultSetToTableModel(rs1));
        		    
        		    javax.swing.table.TableColumn col = null;
                    for(int j=0;j<7;j++)
                    {
                        col = table.getColumnModel().getColumn(j);
                        if(j==0)
                        {
                            col.setPreferredWidth(0);
                        }
                        else
                        {
                            col.setPreferredWidth(100);
                        }
                    }
                    
           /*         DefaultTableCellRenderer right=new DateRenderer1();
                    right.setHorizontalAlignment(SwingConstants.RIGHT);
                    
                    DefaultTableCellRenderer left=new DefaultTableCellRenderer();
                    left.setHorizontalAlignment(SwingConstants.LEFT);
                    
                    DefaultTableCellRenderer center=new DefaultTableCellRenderer();
                    center.setHorizontalAlignment(SwingConstants.CENTER);
                    
                    DefaultTableCellRenderer vi=new DefaultTableCellRenderer();
                    vi.setVisible(false);
                   
           */       
                    Dimension d=new Dimension(10,0);
                    
                    table.setIntercellSpacing(d);
                    //table.removeColumn(table.getColumn(1));     
                    
           /*         table.getColumnModel().getColumn(0).setCellRenderer(center);
                    table.getColumnModel().getColumn(5).setCellRenderer(center);
                    table.getColumnModel().getColumn(6).setCellRenderer(right);
                    table.getColumnModel().getColumn(2).setCellRenderer(center);
           */      
					}
                    
                }
               catch (Exception ae) {
                   ae.printStackTrace();
               }
			}
		});
		button.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		button.setBounds(605, 381, 89, 25);
		panel.add(button);
		
		JLabel label = new JLabel("Search");
		label.setFont(new Font("Book Antiqua", Font.BOLD, 16));
		label.setBounds(505, 40, 63, 25);
		panel.add(label);
		}
		catch (SQLException e) {
		    e.printStackTrace();
		}
		table.setAutoCreateRowSorter(true);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Edit_bank b = new Edit_bank();
                JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(b);
                b.show();
                dispose();
			}
		});
		btnEdit.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnEdit.setBounds(24, 42, 89, 23);
		panel.add(btnEdit);
	}
}
