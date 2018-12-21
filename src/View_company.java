import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.sql.Connection;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;


import net.proteanit.sql.DbUtils;

public class View_company extends JInternalFrame {
	private JTextField textField;
	private JTable table;
	public int id;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_company frame = new View_company();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public View_company() {
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
		setBounds((w-1200)/2, (h-482)/2, 1200, 482);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 1174, 442);
		getContentPane().add(panel);
		
		final Connection connection = Databaseconnection.connection2();

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
                    
                    // pull data from the database 
                    
                    String query = "select ID,Name from Company where (Name like '%"+textField.getText()+"%')";
                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    pmt.close();
                    rs.close();
                    
                    javax.swing.table.TableColumn col = null;
                    for(int j=0;j<2;j++)
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
                        
                    }
                    
                    
                    
                    /*DefaultTableCellRenderer left=new DefaultTableCellRenderer();
                    left.setHorizontalAlignment(SwingConstants.LEFT);
                    
                    DefaultTableCellRenderer center=new DefaultTableCellRenderer();
                    center.setHorizontalAlignment(SwingConstants.CENTER);
                    */
                  
                    Dimension d=new Dimension(10,0);
                    
                    table.setIntercellSpacing(d);
                              
                    
                    //table.getColumnModel().getColumn(0).setCellRenderer(center);
                    
                } catch (SQLException ae) {
                    ae.printStackTrace();
                }
			}
		});
		
		
		textField.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(427, 40, 299, 20);
		panel.add(textField);
		
		JLabel lblViewVoucher = new JLabel("Company");
		lblViewVoucher.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblViewVoucher.setBounds(521, 0, 156, 24);
		panel.add(lblViewVoucher);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 75, 1154, 344);
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
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				
				 int r = table.getSelectedRow();
                 String i = (table.getModel().getValueAt(r,0).toString());
                 id = Integer.parseInt(i);
                
                 try
                 {
                	 	String query="delete from Company_id";
			            PreparedStatement pmt = connection.prepareStatement(query);
			            pmt.executeUpdate();
			            pmt.close();
			            
                	 	String query1="insert into Company_id(ID) Values ('"+id+"')";
			            PreparedStatement pmt1 = connection.prepareStatement(query1);
			            pmt1.executeUpdate();
			            pmt1.close();
                 }
                 catch(Exception ae)
                 {
                	 ae.printStackTrace();
                 }
			}
		});

		try 
        {
            String query1 = "select ID,Name from Company";
            java.sql.PreparedStatement pmt1 = connection.prepareStatement(query1);
            ResultSet rs1 = pmt1.executeQuery();
            table.setModel(DbUtils.resultSetToTableModel(rs1));
            pmt1.close();
            rs1.close();

            javax.swing.table.TableColumn col = null;
            for(int j=0;j<2;j++)
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
            }
            
            /*DefaultTableCellRenderer left=new DefaultTableCellRenderer();
            left.setHorizontalAlignment(SwingConstants.LEFT);
            
            DefaultTableCellRenderer center=new DefaultTableCellRenderer();
            center.setHorizontalAlignment(SwingConstants.CENTER);
            */
          
            Dimension d=new Dimension(10,0);
            
            table.setIntercellSpacing(d);
                      
            
            //table.getColumnModel().getColumn(0).setCellRenderer(center);
        }
        catch (SQLException ae) 
        {
            ae.printStackTrace();
        }

        scrollPane.setViewportView(table);

		
		
		JLabel label = new JLabel("Search");
		label.setFont(new Font("Book Antiqua", Font.BOLD, 16));
		label.setBounds(354, 40, 63, 25);
		panel.add(label);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Edit_company b = new Edit_company();
                JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(b);
                b.show();
                dispose();
			}
		});
		btnEdit.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnEdit.setBounds(69, 40, 89, 23);
		panel.add(btnEdit);
		
		JButton btnDetail = new JButton("Detail");
		btnDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Detail_company b = new Detail_company();
                JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(b);
                b.show();
                
			}
		});
		btnDetail.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnDetail.setBounds(956, 40, 89, 23);
		panel.add(btnDetail);
		
		table.setAutoCreateRowSorter(true);
	}
	
	public static boolean empty( final String s ) 
	{
		return s == null || s.trim().isEmpty();
	}
}