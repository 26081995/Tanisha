import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class Edit_driver extends JInternalFrame {
	private JTextField name;
	private JTextField address1;
	private JTextField address2;
	private JTextField mobile;
	String company=null,company1=null;
	private JTextField mobile1;
	private JTextField salary;
	int id11;
	private JTextField address3;
	private JTextField vehicle;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edit_driver frame = new Edit_driver();
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
	public Edit_driver() {getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
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
        setBounds((w-600)/2, (h-431)/2, 600, 431);
		getContentPane().setLayout(null);
	
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
		
		JLabel lblAddClient = new JLabel("Edit Driver");
	lblAddClient.setBounds(233, 0, 135, 24);
	lblAddClient.setFont(new Font("Book Antiqua", Font.BOLD, 25));
	getContentPane().add(lblAddClient);
	
	JLabel lblName = new JLabel("Name:*");
	lblName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	lblName.setBounds(12, 35, 128, 25);
	getContentPane().add(lblName);
	
	name = new JTextField();
	name.setForeground(Color.RED);
	name.setEditable(false);
	name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	name.setColumns(10);
	name.setBounds(149, 35, 427, 25);
	getContentPane().add(name);
	
	JLabel lblAddress = new JLabel("Address 1:");
	lblAddress.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	lblAddress.setBounds(12, 71, 108, 25);
	getContentPane().add(lblAddress);
	
	address1 = new JTextField();
	address1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	address1.setColumns(10);
	address1.setBounds(149, 71, 427, 25);
	getContentPane().add(address1);
	
	JLabel lblAddress_1 = new JLabel("Address 2:");
	lblAddress_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	lblAddress_1.setBounds(12, 106, 108, 25);
	getContentPane().add(lblAddress_1);
	
	address2 = new JTextField();
	address2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	address2.setColumns(10);
	address2.setBounds(149, 106, 427, 25);
	getContentPane().add(address2);
	
	JLabel label_3 = new JLabel("State:*");
	label_3.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	label_3.setBounds(12, 182, 79, 25);
	getContentPane().add(label_3);
	
	JComboBox state = new JComboBox();
	state.addItem("SELECT");
	state.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	state.setBounds(149, 182, 425, 26);
	getContentPane().add(state);
	
	JLabel label_4 = new JLabel("City:*");
	label_4.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	label_4.setBounds(12, 217, 53, 25);
	getContentPane().add(label_4);
	
	JComboBox city = new JComboBox();
	city.addItem("SELECT");
	city.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	city.setBounds(149, 217, 425, 26);
	getContentPane().add(city);
	
	JLabel lblMobileNo = new JLabel("Mobile 1:");
	lblMobileNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	lblMobileNo.setBounds(12, 252, 108, 25);
	getContentPane().add(lblMobileNo);
	
	mobile = new JTextField();
	mobile.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	mobile.setColumns(10);
	mobile.setBounds(149, 252, 150, 25);
	getContentPane().add(mobile);
	
	JLabel lblSalary = new JLabel("Salary:*");
	lblSalary.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	lblSalary.setBounds(12, 324, 108, 25);
	getContentPane().add(lblSalary);
	
	salary = new JTextField();
	salary.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	salary.setColumns(10);
	salary.setBounds(149, 324, 427, 25);
	getContentPane().add(salary);
	
	
	
	try
	{
		String query1 = "select * from State";
	    PreparedStatement pmt1 = connection.prepareStatement(query1);
	    ResultSet rs1 = pmt1.executeQuery();
	    while(rs1.next())
	    {
	    	state.addItem(rs1.getString("State_name"));
	    }
	    rs1.close();
	}
	catch(Exception ae)
	{
		ae.printStackTrace();
	}
	
	state.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			try
			{
				city.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
				
				String query1 = "select * from City where  State_name='"+state.getSelectedItem().toString()+"'";
			    PreparedStatement pmt1 = connection.prepareStatement(query1);
			    ResultSet rs = pmt1.executeQuery();
			    while(rs.next())
			    {
			    	city.addItem(rs.getString("City_name"));
			    }
			    rs.close();
			}
			catch(Exception ae)
			{
				ae.printStackTrace();
			}
		}
	});
	
	state.setSelectedItem("Gujarat");
	city.setSelectedItem("ahmedabad");
	
	JLabel lblMobile = new JLabel("Mobile 2:");
	lblMobile.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	lblMobile.setBounds(321, 252, 101, 25);
	getContentPane().add(lblMobile);
	
	mobile1 = new JTextField();
	mobile1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	mobile1.setColumns(10);
	mobile1.setBounds(420, 252, 150, 25);
	getContentPane().add(mobile1);
	
	JButton button = new JButton("Update");
	button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			try
			{
				String text = mobile.getText();
	            int counter = text.length();
	            
	            if(name.getText().equals("")   || city.getSelectedItem().toString().equals("SELECT")||salary.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "please enter details");
				}
				else
				{
					String query="update Driver set Address1='"+address1.getText()+"',Address3='"+address3.getText()+"',Address2='"+address2.getText()+"',City='"+city.getSelectedItem().toString()+"',State='"+state.getSelectedItem().toString()+"',Mobile='"+mobile.getText()+"',Salary='"+salary.getText()+"',Mobile1='"+mobile1.getText()+"',Vehicle_no='"+vehicle.getText()+"' where ID='"+id11+"'";
		            PreparedStatement pmt = connection.prepareStatement(query);
		            pmt.executeUpdate();
		            pmt.close();

		            
		            View_driver b1 = new View_driver();
                    JDesktopPane desktopPane = getDesktopPane();
                    desktopPane.add(b1);
                    b1.show();
                    dispose();
				}
			}
			catch (Exception ae) {
				ae.printStackTrace();
				// TODO: handle exception
			}
		}
	});
	button.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	button.setBounds(248, 360, 105, 25);
	getContentPane().add(button);
	
	JLabel lblAddress_2 = new JLabel("Address 3:");
	lblAddress_2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	lblAddress_2.setBounds(12, 142, 108, 25);
	getContentPane().add(lblAddress_2);
	
	address3 = new JTextField();
	address3.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	address3.setColumns(10);
	address3.setBounds(149, 142, 427, 25);
	getContentPane().add(address3);
	
	JLabel lblGstNo = new JLabel("Vehicle No:");
	lblGstNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	lblGstNo.setBounds(10, 288, 108, 25);
	getContentPane().add(lblGstNo);
	
	vehicle = new JTextField();
	vehicle.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	vehicle.setColumns(10);
	vehicle.setBounds(147, 288, 427, 25);
	getContentPane().add(vehicle);
	
	
	button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			try
			{
				String text = mobile.getText();
	            int counter = text.length();
	            
	            if(name.getText().equals("")   || city.getSelectedItem().toString().equals("SELECT")||salary.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "please enter details");
				}
				else
				{
					String query="update Driver set Address1='"+address1.getText()+"',Address3='"+address3.getText()+"',Address2='"+address2.getText()+"',City='"+city.getSelectedItem().toString()+"',State='"+state.getSelectedItem().toString()+"',Mobile='"+mobile.getText()+"',Salary='"+salary.getText()+"',Mobile1='"+mobile1.getText()+"',Vehicle_no='"+vehicle.getText()+"' where ID='"+id11+"'";
		            PreparedStatement pmt = connection.prepareStatement(query);
		            pmt.executeUpdate();
		            pmt.close();

		            
		            View_driver b1 = new View_driver();
                    JDesktopPane desktopPane = getDesktopPane();
                    desktopPane.add(b1);
                    b1.show();
                    dispose();
				}
			}
			catch (Exception ae) {
				ae.printStackTrace();
				// TODO: handle exception
			}
		}
	});
	
	
	
	try
	{
		String query11 = "SELECT * from Driver_id ";
        PreparedStatement pmt11 = connection.prepareStatement(query11);
        ResultSet rs11 = pmt11.executeQuery();
        while(rs11.next())
        {
        	id11=rs11.getInt("Id");
        }
        rs11.close();
        pmt11.close();
        
        
		String query1 = "select * from Driver where ID='"+id11+"'";
	    PreparedStatement pmt1 = connection.prepareStatement(query1);
	    ResultSet rs1 = pmt1.executeQuery();
	    while(rs1.next())
	    {
	    	name.setText(rs1.getString("Name"));
	    	address1.setText(rs1.getString("Address1"));
	    	address2.setText(rs1.getString("Address2"));
	    	address3.setText(rs1.getString("Address3"));
	    	state.setSelectedItem(rs1.getString("State"));
	    	city.setSelectedItem(rs1.getString("City"));
	    	mobile.setText(rs1.getString("Mobile"));
	    	salary.setText(rs1.getString("Salary"));
	    	mobile1.setText(rs1.getString("Mobile1"));
	    	vehicle.setText(rs1.getString("Vehicle_no"));
	    	
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
