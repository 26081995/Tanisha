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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;


import net.proteanit.sql.DbUtils;

public class View_driver extends JInternalFrame {
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
					View_driver frame = new View_driver();
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
	public View_driver() {getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
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
    setClosable(true);
	setBounds((w-1200)/2, (h-600)/2, 1200, 600);
	getContentPane().setLayout(null);
	
	JLabel lblViewExpense = new JLabel("Driver");
	lblViewExpense.setFont(new Font("Book Antiqua", Font.BOLD, 25));
	lblViewExpense.setBounds(511, 0, 147, 25);
	getContentPane().add(lblViewExpense);
	
	final java.sql.Connection connection = Databaseconnection.connection2();
	
	try
	{
		String query51 = "select * from Company_temp";
        PreparedStatement pmt51 = connection.prepareStatement(query51);
        ResultSet rs51 = pmt51.executeQuery();
	    while(rs51.next())
	    {
	    	company=rs51.getString("Name");
        }
	    rs51.close();
	    pmt51.close();
	}
	catch(Exception ae)
	{
		ae.printStackTrace();
	}
	
	
	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(10, 91, 1164, 469);
	getContentPane().add(scrollPane);
	
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
	
	try 
	{
		String query = "select ID,Name,Address1,Mobile,Vehicle_no,Salary,Balance from Driver WHERE Company='"+company+"'";
	    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
	    ResultSet rs = pmt.executeQuery();
	    table.setModel(DbUtils.resultSetToTableModel(rs));
	    
	    table.setAutoCreateRowSorter(true);

	    javax.swing.table.TableColumn col = null;
        for(int j=0;j<7;j++)
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
        /*DefaultTableCellRenderer right=new DateRenderer1();
        right.setHorizontalAlignment(SwingConstants.RIGHT);
        
        DefaultTableCellRenderer left=new DefaultTableCellRenderer();
        left.setHorizontalAlignment(SwingConstants.LEFT);
        
        DefaultTableCellRenderer center=new DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        */
      
        Dimension d=new Dimension(10,0);
        
        table.setIntercellSpacing(d);
                  
        
        /*table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(3).setCellRenderer(center);
        table.getColumnModel().getColumn(4).setCellRenderer(right);
        table.getColumnModel().getColumn(5).setCellRenderer(right);
        table.getColumnModel().getColumn(6).setCellRenderer(right);
        
        */
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                int r = table.getSelectedRow();
                String i = (table.getModel().getValueAt(r,0).toString());
                id = Integer.parseInt(i);

                try
                 {
                	 	String query = "delete from Driver_id";
	                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
	                    pmt.executeUpdate();
	                    pmt.close();

	                    String query1 = "Insert Into Driver_id(ID) Values ('"+id+"')";
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
	}
	catch (SQLException e) {
	    e.printStackTrace();
	}

	textField = new JTextField();
	textField.addKeyListener(new KeyAdapter() {
		public void keyReleased(KeyEvent e) {
			try 
			{
                String query = "select ID,Name,Address1,Mobile,Vehicle_no,Salary,Balance from Driver where (Name like '%"+textField.getText()+"%'  or Mobile like '%"+textField.getText()+"%' or Address1 like '%"+textField.getText()+"%' or Salary like '%"+textField.getText()+"%'  or Vehicle_no like '%"+textField.getText()+"%' ) and Company='"+company+"'";
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
                    else if(j==1)
                    {
                        col.setPreferredWidth(300);
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
                */
              
                Dimension d=new Dimension(10,0);
                
                table.setIntercellSpacing(d);
                          
                
                /*table.getColumnModel().getColumn(0).setCellRenderer(center);
                table.getColumnModel().getColumn(3).setCellRenderer(center);
                table.getColumnModel().getColumn(4).setCellRenderer(right);
                table.getColumnModel().getColumn(5).setCellRenderer(right);
                table.getColumnModel().getColumn(6).setCellRenderer(right);
        */        
            } 
			catch (SQLException ae) {
                ae.printStackTrace();
            }
		}
	});
	
	textField.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	textField.setColumns(10);
	textField.setBounds(460, 40, 194, 25);
	getContentPane().add(textField);
	
	JButton button = new JButton("Delete");
	button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try
            {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
				if(dialogResult == JOptionPane.YES_OPTION){
				
					String query = "delete from Driver where ID="+id;
                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                    pmt.executeUpdate();
                    pmt.close();
                    
                    
                    String query1 = "select ID,Name,Address1,Mobile,Vehicle_no,Salary,Balance from Driver where Company='"+company+"'";
        		    java.sql.PreparedStatement pmt1 = connection.prepareStatement(query1);
        		    ResultSet rs = pmt1.executeQuery();
        		    table.setModel(DbUtils.resultSetToTableModel(rs));
        		    
        		    javax.swing.table.TableColumn col = null;
                    for(int j=0;j<7;j++)
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
                    
                    /*DefaultTableCellRenderer right=new DateRenderer1();
                    right.setHorizontalAlignment(SwingConstants.RIGHT);
                    
                    DefaultTableCellRenderer left=new DefaultTableCellRenderer();
                    left.setHorizontalAlignment(SwingConstants.LEFT);
                    
                    DefaultTableCellRenderer center=new DefaultTableCellRenderer();
                    center.setHorizontalAlignment(SwingConstants.CENTER);
                  */  
                  
                    Dimension d=new Dimension(10,0);
                    
                    table.setIntercellSpacing(d);
                              
                    
                  /*  table.getColumnModel().getColumn(0).setCellRenderer(center);
                    table.getColumnModel().getColumn(3).setCellRenderer(center);
                    table.getColumnModel().getColumn(4).setCellRenderer(right);
                    table.getColumnModel().getColumn(5).setCellRenderer(right);
                    table.getColumnModel().getColumn(6).setCellRenderer(right);
            */        
				}
            }
           catch (Exception ae) {
               ae.printStackTrace();
           }
		}
	});
	
	button.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	button.setBounds(1032, 40, 89, 25);
	getContentPane().add(button);
	
	JButton btnEdit = new JButton("Edit");
	btnEdit.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Edit_driver b = new Edit_driver();
            JDesktopPane desktopPane = getDesktopPane();
            desktopPane.add(b);
            b.show();
            dispose();
		}
	});
	btnEdit.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	btnEdit.setBounds(89, 40, 89, 25);
	getContentPane().add(btnEdit);
	
	JButton btnDetail = new JButton("Detail");
	btnDetail.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Detail_driver bq = new Detail_driver();
            JDesktopPane desktopPane = getDesktopPane();
            desktopPane.add(bq);
            bq.show();
            
		}
	});
	btnDetail.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	btnDetail.setBounds(274, 40, 89, 25);
	getContentPane().add(btnDetail);
	
	JLabel label = new JLabel("Search");
	label.setFont(new Font("Book Antiqua", Font.BOLD, 16));
	label.setBounds(387, 40, 63, 25);
	getContentPane().add(label);
}
}