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

public class View_city extends JInternalFrame {
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
					View_city frame = new View_city();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public View_city() {
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
        getRootPane().getActionMap().put("Cancel", new AbstractAction()
        {
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
        
		setBounds((w-702)/2, (h-502)/2, 702, 502);
		getContentPane().setLayout(null);
		setClosable(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(10, 0, 666, 462);
		getContentPane().add(panel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 106, 646, 345);
		panel.add(scrollPane);
		
		final Connection connection = Databaseconnection.connection2();
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
		table.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				
				 int r = table.getSelectedRow();
                 String i = (table.getModel().getValueAt(r,0).toString());
                 id = Integer.parseInt(i);
                
                 
			}
		});
	            try {
	                // pull data from the database 
	                
	                String query = "select * from City";
	                java.sql.PreparedStatement pmt = connection.prepareStatement(query);
	                ResultSet rs = pmt.executeQuery();
	                table.setModel(DbUtils.resultSetToTableModel(rs));
	                pmt.close();
	                rs.close();
	                
	                javax.swing.table.TableColumn col = null;
                    for(int j=0;j<4;j++)
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
		scrollPane.setViewportView(table);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
                    
                    // pull data from the database 
                    
                    String query = "select * from City where ID like '%"+textField.getText()+"%'  or State_name like '%"+textField.getText()+"%' or City_name like '%"+textField.getText()+"%'";
                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    pmt.close();
                    rs.close();
                    
                    javax.swing.table.TableColumn col = null;
                    for(int j=0;j<4;j++)
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
		
		textField.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(236, 40, 124, 25);
		panel.add(textField);
		
		JLabel lblViewExtraHour = new JLabel("City");
		lblViewExtraHour.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblViewExtraHour.setBounds(255, 0, 69, 25);
		panel.add(lblViewExtraHour);
		
		JButton button = new JButton("Delete");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
					
                    
                  
                    String query = "delete from City where ID="+id;
                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                    pmt.executeUpdate();
                    pmt.close();
                    
                } }
                catch (SQLException ae)
                {
                    // TODO Auto-generated catch block
                    ae.printStackTrace();
                }
				View_city b = new View_city();
                JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(b);
                b.show();
                dispose();
			}
		});
		button.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		button.setBounds(552, 40, 89, 25);
		panel.add(button);
		
		JLabel label = new JLabel("Search");
		label.setFont(new Font("Book Antiqua", Font.BOLD, 16));
		label.setBounds(163, 40, 63, 25);
		panel.add(label);
		
		table.setAutoCreateRowSorter(true);
	}

}

