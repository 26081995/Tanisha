import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
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

public class Detail_driver extends JInternalFrame {
	private JTextField name;
	private JTextField address1;
	private JTextField address2;
	private JTextField mobile;
	private JTextField salary;
	String company=null,company1=null;
	private JTextField vehicle;
	private JTextField state;
	private JTextField city;
	private JTextField mobile1;
	private JTextField address3;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Detail_driver frame = new Detail_driver();
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
	public Detail_driver() {
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
	        setClosable(true);
	        setBounds((w-498)/2, (h-466)/2, 498, 466);
			getContentPane().setLayout(null);
		
			final java.sql.Connection connection = Databaseconnection.connection2();
			DecimalFormat dc=new DecimalFormat("0.00");
			try
			{
				String query1 = "select * from Driver_id";
			    PreparedStatement pmt1 = connection.prepareStatement(query1);
			    ResultSet rs1 = pmt1.executeQuery();
			    while(rs1.next())
			    {
			    	company=rs1.getString("ID");
			    }
			    rs1.close();
			}
			catch(Exception ae)
			{
				ae.printStackTrace();
			}
			
			try
			{
				String query1 = "select * from Company_temp";
			    PreparedStatement pmt1 = connection.prepareStatement(query1);
			    ResultSet rs1 = pmt1.executeQuery();
			    while(rs1.next())
			    {
			    	company1=rs1.getString("Name");
			    }
			    rs1.close();
			}
			catch(Exception ae)
			{
				ae.printStackTrace();
			}
			
			
			
			JLabel lblAddClient = new JLabel("Detail Driver");
		lblAddClient.setBounds(160, 11, 154, 36);
		lblAddClient.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		getContentPane().add(lblAddClient);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblName.setBounds(10, 75, 128, 25);
		getContentPane().add(lblName);
		
		name = new JTextField();
		name.setForeground(Color.RED);
		name.setEditable(false);
		name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		name.setColumns(10);
		name.setBounds(137, 75, 324, 25);
		getContentPane().add(name);
		
		JLabel lblAddress = new JLabel("Address 1:");
		lblAddress.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress.setBounds(10, 110, 108, 25);
		getContentPane().add(lblAddress);
		
		address1 = new JTextField();
		address1.setForeground(Color.RED);
		address1.setEditable(false);
		address1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address1.setColumns(10);
		address1.setBounds(137, 110, 324, 25);
		getContentPane().add(address1);
		
		JLabel lblAddress_1 = new JLabel("Address 2:");
		lblAddress_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress_1.setBounds(10, 145, 108, 25);
		getContentPane().add(lblAddress_1);
		
		address2 = new JTextField();
		address2.setForeground(Color.RED);
		address2.setEditable(false);
		address2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address2.setColumns(10);
		address2.setBounds(137, 145, 324, 25);
		getContentPane().add(address2);
		
		JLabel lblState = new JLabel("State:");
		lblState.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblState.setBounds(12, 218, 79, 25);
		getContentPane().add(lblState);
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblCity.setBounds(12, 253, 53, 25);
		getContentPane().add(lblCity);
		
		JLabel lblMobileNo = new JLabel("Mobile 1:");
		lblMobileNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblMobileNo.setBounds(12, 288, 108, 25);
		getContentPane().add(lblMobileNo);
		
		mobile = new JTextField();
		mobile.setForeground(Color.RED);
		mobile.setEditable(false);
		mobile.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		mobile.setColumns(10);
		mobile.setBounds(139, 288, 322, 25);
		getContentPane().add(mobile);
		
		JLabel lblCurrentBalance = new JLabel("Salary:");
		lblCurrentBalance.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblCurrentBalance.setBounds(12, 394, 126, 25);
		getContentPane().add(lblCurrentBalance);
		
		salary = new JTextField();
		salary.setForeground(Color.RED);
		salary.setEditable(false);
		salary.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		salary.setColumns(10);
		salary.setBounds(139, 394, 322, 25);
		getContentPane().add(salary);
		
		JLabel lblPanNo = new JLabel("Vehicle No:");
		lblPanNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblPanNo.setBounds(12, 359, 108, 25);
		getContentPane().add(lblPanNo);
		
		vehicle = new JTextField();
		vehicle.setForeground(Color.RED);
		vehicle.setEditable(false);
		vehicle.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		vehicle.setColumns(10);
		vehicle.setBounds(137, 359, 324, 25);
		getContentPane().add(vehicle);
		
		state = new JTextField();
		state.setForeground(Color.RED);
		state.setEditable(false);
		state.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		state.setColumns(10);
		state.setBounds(137, 218, 324, 25);
		getContentPane().add(state);
		
		city = new JTextField();
		city.setForeground(Color.RED);
		city.setEditable(false);
		city.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		city.setColumns(10);
		city.setBounds(137, 253, 324, 25);
		getContentPane().add(city);
		
		JLabel lblMobile = new JLabel("Mobile 2:");
		lblMobile.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblMobile.setBounds(12, 323, 108, 14);
		getContentPane().add(lblMobile);
		
		mobile1 = new JTextField();
		mobile1.setForeground(Color.RED);
		mobile1.setEditable(false);
		mobile1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		mobile1.setColumns(10);
		mobile1.setBounds(139, 323, 322, 25);
		getContentPane().add(mobile1);
		
		JLabel lblAddress_2 = new JLabel("Address 3:");
		lblAddress_2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress_2.setBounds(10, 181, 108, 25);
		getContentPane().add(lblAddress_2);
		
		address3 = new JTextField();
		address3.setForeground(Color.RED);
		address3.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address3.setEditable(false);
		address3.setColumns(10);
		address3.setBounds(137, 181, 324, 25);
		getContentPane().add(address3);
		
		TableModel model = null;
		
		
		
		try
		{
			String query1 = "select * from Driver where ID='"+company+"'";
		    PreparedStatement pmt1 = connection.prepareStatement(query1);
		    ResultSet rs1 = pmt1.executeQuery();
		    while(rs1.next())
		    {
		    	name.setText(rs1.getString("Name"));
		    	address1.setText(rs1.getString("Address1"));
		    	address2.setText(rs1.getString("Address2"));
		    	address3.setText(rs1.getString("Address3"));
		    	state.setText(rs1.getString("State"));
		    	city.setText(rs1.getString("City"));
		    	mobile.setText(rs1.getString("Mobile"));
		    	salary.setText((rs1.getString("Salary")));
		    	vehicle.setText(rs1.getString("Vehicle_no"));
		    	mobile1.setText(rs1.getString("Mobile1"));
		    }
		    rs1.close();
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
		
	}
	public static boolean empty( final String s ) 
	{
		return s == null || s.trim().isEmpty();
	}
}
