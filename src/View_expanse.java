import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import net.proteanit.sql.DbUtils;

public class View_expanse extends JInternalFrame {
	private JTable table;
	private int id;
	private JTextField textField;
	String company=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_expanse frame = new View_expanse();
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
	public View_expanse() {
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
		setClosable(true);
		setBounds((w-624)/2, (h-432)/2, 624, 432);
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
		panel.setBounds(0, 0, 608, 403);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblViewExpanse = new JLabel("Expense");
		lblViewExpanse.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblViewExpanse.setBounds(249, 0, 113, 25);
		panel.add(lblViewExpanse);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
                    
                    // pull data from the database 
                    
                    String query = "select ID,Name,Remark from Expense where (ID like '%"+textField.getText()+"%' or Name like '%"+textField.getText()+"%' or Remark like '%"+textField.getText()+"%' ) and Company='"+company+"'";
                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    pmt.close();
                    rs.close();
                    
                    javax.swing.table.TableColumn col = null;
                    for(int j=0;j<3;j++)
                    {
                        col = table.getColumnModel().getColumn(j);
                        if(j==0)
                        {
                            col.setPreferredWidth(0);
                        }
                        else if(j==1)
                        {
                            col.setPreferredWidth(300);
                        }
                        else
                        {
                            col.setPreferredWidth(100);
                        }
                    }
                    
                    DefaultTableCellRenderer right=new DefaultTableCellRenderer();
                    right.setHorizontalAlignment(SwingConstants.RIGHT);
                    
                    DefaultTableCellRenderer left=new DefaultTableCellRenderer();
                    left.setHorizontalAlignment(SwingConstants.LEFT);
                    
                    DefaultTableCellRenderer center=new DefaultTableCellRenderer();
                    center.setHorizontalAlignment(SwingConstants.CENTER);
                    
                    DefaultTableCellRenderer vi=new DefaultTableCellRenderer();
                    vi.setVisible(false);
                   
                  
                    Dimension d=new Dimension(10,0);
                    
                    table.setIntercellSpacing(d);
                    //table.removeColumn(table.getColumn(1));     
                    
                    table.getColumnModel().getColumn(0).setCellRenderer(center);
                    
                   
                } catch (SQLException ae) {
                    ae.printStackTrace();
                }
			}
		});
		
		textField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(231, 40, 131, 25);
		panel.add(textField);

		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 84, 588, 261);
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
		  
		    String query = "select ID,Name,Remark from Expense where Company='"+company+"'"; 
		    PreparedStatement pmt = connection.prepareStatement(query);
		    ResultSet rs = pmt.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
		
		    javax.swing.table.TableColumn col = null;
            for(int j=0;j<3;j++)
            {
                col = table.getColumnModel().getColumn(j);
                if(j==0)
                {
                    col.setPreferredWidth(0);
                }
                else if(j==1)
                {
                    col.setPreferredWidth(300);
                }
                else
                {
                    col.setPreferredWidth(100);
                }
            }
            
            DefaultTableCellRenderer right=new DefaultTableCellRenderer();
            right.setHorizontalAlignment(SwingConstants.RIGHT);
            
            DefaultTableCellRenderer left=new DefaultTableCellRenderer();
            left.setHorizontalAlignment(SwingConstants.LEFT);
            
            DefaultTableCellRenderer center=new DefaultTableCellRenderer();
            center.setHorizontalAlignment(SwingConstants.CENTER);
            
            DefaultTableCellRenderer vi=new DefaultTableCellRenderer();
            vi.setVisible(false);
           
          
            Dimension d=new Dimension(10,0);
            
            table.setIntercellSpacing(d);
            //table.removeColumn(table.getColumn(1));     
            
            table.getColumnModel().getColumn(0).setCellRenderer(center);
		}
		catch (SQLException e) {
		    e.printStackTrace();
		}
			table.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent arg0) {
	                int r = table.getSelectedRow();
	                String i = (table.getModel().getValueAt(r,0).toString());
	                id = Integer.parseInt(i);
	                
	                
	               
	            }
	        });

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try
				{
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
					
					String query = "delete from Expense where ID="+id;
					PreparedStatement pmt = connection.prepareStatement(query);
	                pmt.executeUpdate();
	                pmt.close();
	                
	                String query1 = "select ID,Name,Remark from Expense where Company='"+company+"'";
	    		    PreparedStatement pmt1 = connection.prepareStatement(query1);
	    		    ResultSet rs = pmt1.executeQuery();
	    		    table.setModel(DbUtils.resultSetToTableModel(rs));
	    		    
	    		    javax.swing.table.TableColumn col = null;
                    for(int j=0;j<3;j++)
                    {
                        col = table.getColumnModel().getColumn(j);
                        if(j==0)
                        {
                            col.setPreferredWidth(0);
                        }
                        else if(j==1)
                        {
                            col.setPreferredWidth(300);
                        }
                        else
                        {
                            col.setPreferredWidth(100);
                        }
                    }
                    
                    DefaultTableCellRenderer right=new DefaultTableCellRenderer();
                    right.setHorizontalAlignment(SwingConstants.RIGHT);
                    
                    DefaultTableCellRenderer left=new DefaultTableCellRenderer();
                    left.setHorizontalAlignment(SwingConstants.LEFT);
                    
                    DefaultTableCellRenderer center=new DefaultTableCellRenderer();
                    center.setHorizontalAlignment(SwingConstants.CENTER);
                    
                    DefaultTableCellRenderer vi=new DefaultTableCellRenderer();
                    vi.setVisible(false);
                   
                  
                    Dimension d=new Dimension(10,0);
                    
                    table.setIntercellSpacing(d);
                    //table.removeColumn(table.getColumn(1));     
                    
                    table.getColumnModel().getColumn(0).setCellRenderer(center);
					}
					}
				catch (SQLException ae) {
				    ae.printStackTrace();
				}
			}
		});
		btnDelete.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnDelete.setBounds(255, 369, 89, 23);
		panel.add(btnDelete);

		JLabel label = new JLabel("Search");
		label.setFont(new Font("Book Antiqua", Font.BOLD, 16));
		label.setBounds(158, 40, 63, 25);
		panel.add(label);
		
		table.setAutoCreateRowSorter(true);
	}
}