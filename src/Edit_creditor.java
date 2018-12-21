import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

public class Edit_creditor extends JInternalFrame {
	private JTextField name;
	private JTextField address1;
	private JTextField address2;
	private JTextField mobile;
	private JTextField email;
	private JTextField website;
	private JTextField gst;
	String company=null,company1=null;
	private JTextField pan;
	private JTextField mobile1;
	private JTextField landline;
	private JTextField landline1;
	private JTextField address3;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edit_creditor frame = new Edit_creditor();
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
	public Edit_creditor() {
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
	        setBounds((w-600)/2, (h-599)/2, 600, 528);
			getContentPane().setLayout(null);
		
			final java.sql.Connection connection = Databaseconnection.connection2();
			
			try
			{
				String query1 = "select * from Creditor_id";
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
			
			
			
			JLabel lblAddClient = new JLabel("Edit Creditor");
		lblAddClient.setBounds(214, 0, 154, 24);
		lblAddClient.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		getContentPane().add(lblAddClient);
		
		JLabel lblName = new JLabel("Name:*");
		lblName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblName.setBounds(10, 35, 128, 25);
		getContentPane().add(lblName);
		
		name = new JTextField();
		name.setForeground(Color.RED);
		name.setEditable(false);
		name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		name.setColumns(10);
		name.setBounds(147, 35, 427, 24);
		getContentPane().add(name);
		
		JLabel lblAddress = new JLabel("Address 1:");
		lblAddress.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress.setBounds(10, 68, 108, 25);
		getContentPane().add(lblAddress);
		
		address1 = new JTextField();
		address1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address1.setColumns(10);
		address1.setBounds(147, 68, 427, 25);
		getContentPane().add(address1);
		
		JLabel lblAddress_1 = new JLabel("Address 2:");
		lblAddress_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress_1.setBounds(10, 103, 108, 25);
		getContentPane().add(lblAddress_1);
		
		address2 = new JTextField();
		address2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address2.setColumns(10);
		address2.setBounds(147, 103, 427, 25);
		getContentPane().add(address2);
		
		JLabel label_3 = new JLabel("State:*");
		label_3.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_3.setBounds(10, 172, 79, 25);
		getContentPane().add(label_3);
		
		JComboBox state = new JComboBox();
		state.addItem("SELECT");
		state.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		state.setBounds(149, 172, 425, 26);
		getContentPane().add(state);
		
		JLabel label_4 = new JLabel("City:*");
		label_4.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_4.setBounds(10, 207, 53, 25);
		getContentPane().add(label_4);
		
		JComboBox city = new JComboBox();
		city.addItem("SELECT");
		city.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		city.setBounds(149, 207, 425, 26);
		getContentPane().add(city);
		
		JLabel lblMobile = new JLabel("Mobile 1:");
		lblMobile.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblMobile.setBounds(10, 242, 108, 25);
		getContentPane().add(lblMobile);
		
		mobile = new JTextField();
		mobile.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		mobile.setColumns(10);
		mobile.setBounds(149, 242, 150, 25);
		getContentPane().add(mobile);
		
		JLabel label_6 = new JLabel("Email:");
		label_6.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_6.setBounds(8, 314, 66, 25);
		getContentPane().add(label_6);
		
		email = new JTextField();
		email.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		email.setColumns(10);
		email.setBounds(149, 314, 425, 25);
		getContentPane().add(email);
		
		JLabel label_7 = new JLabel("Website:");
		label_7.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_7.setBounds(8, 349, 108, 25);
		getContentPane().add(label_7);
		
		website = new JTextField();
		website.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		website.setColumns(10);
		website.setBounds(149, 349, 425, 25);
		getContentPane().add(website);
		
		JLabel lblGstNo = new JLabel("GST No:");
		lblGstNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblGstNo.setBounds(8, 384, 108, 25);
		getContentPane().add(lblGstNo);
		
		gst = new JTextField();
		gst.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		gst.setColumns(10);
		gst.setBounds(147, 384, 427, 25);
		getContentPane().add(gst);
		
		JLabel lblPanNo = new JLabel("PAN No:");
		lblPanNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblPanNo.setBounds(8, 419, 108, 25);
		getContentPane().add(lblPanNo);
		
		pan = new JTextField();
		pan.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		pan.setColumns(10);
		pan.setBounds(147, 419, 427, 25);
		getContentPane().add(pan);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					String text = mobile.getText();
		            int counter = text.length();
		            
		            if(name.getText().equals("") || city.getSelectedItem().toString().equals("SELECT"))
    				{
    					JOptionPane.showMessageDialog(null, "please enter details");
    				}
					else
					{
						//String query="insert into Client(Company,Name,Address1,Address2,City,State,Mobile,Email,GST,Pan_no.Website,Balance) Values ('"+company+"','"+name.getText()+"','"+address1.getText()+"','"+address2.getText()+"','"+city.getSelectedItem().toString()+"','"+state.getSelectedItem().toString()+"','"+mobile.getText()+"','"+email.getText()+"','"+gst.getText()+"','"+pan.getText()+"','"+website.getText()+"','"+balance.getText()+"')";
						String query="update Creditor set Address3 = '"+address3.getText()+"',Address1='"+address1.getText()+"',Address2='"+address2.getText()+"',City='"+city.getSelectedItem().toString()+"',State='"+state.getSelectedItem().toString()+"',Mobile='"+mobile.getText()+"',Email='"+email.getText()+"',GST='"+gst.getText()+"',Pan_no='"+pan.getText()+"',Website='"+website.getText()+"',Mobile1='"+mobile1.getText()+"',Landline='"+landline.getText()+"',Landline1='"+landline1.getText()+"' where ID='"+company+"'";
			            PreparedStatement pmt = connection.prepareStatement(query);
			            pmt.executeUpdate();
			            pmt.close();
			            
			            View_creditor b = new View_creditor();
	                    JDesktopPane desktopPane = getDesktopPane();
	                    desktopPane.add(b);
	                    b.show();
	                    dispose();
					}
				}
				catch (Exception ae) {
					ae.printStackTrace();
					// TODO: handle exception
				}
			}
		});
		btnSubmit.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnSubmit.setBounds(247, 455, 89, 25);
		getContentPane().add(btnSubmit);
		
		
		pan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					String text = mobile.getText();
		            int counter = text.length();
		            
		            if(name.getText().equals("") || city.getSelectedItem().toString().equals("SELECT"))
    				{
    					JOptionPane.showMessageDialog(null, "please enter details");
    				}
					else
					{
						//String query="insert into Client(Company,Name,Address1,Address2,City,State,Mobile,Email,GST,Pan_no.Website,Balance) Values ('"+company+"','"+name.getText()+"','"+address1.getText()+"','"+address2.getText()+"','"+city.getSelectedItem().toString()+"','"+state.getSelectedItem().toString()+"','"+mobile.getText()+"','"+email.getText()+"','"+gst.getText()+"','"+pan.getText()+"','"+website.getText()+"','"+balance.getText()+"')";
						String query="update Creditor set Address3 = '"+address3.getText()+"',Address1='"+address1.getText()+"',Address2='"+address2.getText()+"',City='"+city.getSelectedItem().toString()+"',State='"+state.getSelectedItem().toString()+"',Mobile='"+mobile.getText()+"',Email='"+email.getText()+"',GST='"+gst.getText()+"',Pan_no='"+pan.getText()+"',Website='"+website.getText()+"',Mobile1='"+mobile1.getText()+"',Landline='"+landline.getText()+"',Landline1='"+landline1.getText()+"' where ID='"+company+"'";
			            PreparedStatement pmt = connection.prepareStatement(query);
			            pmt.executeUpdate();
			            pmt.close();
			            
			            			            
			            View_creditor b = new View_creditor();
	                    JDesktopPane desktopPane = getDesktopPane();
	                    desktopPane.add(b);
	                    b.show();
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
		
		JLabel lblMobile_1 = new JLabel("Mobile 2:");
		lblMobile_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblMobile_1.setBounds(324, 242, 96, 25);
		getContentPane().add(lblMobile_1);
		
		mobile1 = new JTextField();
		mobile1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		mobile1.setColumns(10);
		mobile1.setBounds(418, 242, 150, 25);
		getContentPane().add(mobile1);
		
		JLabel lblLandline = new JLabel("Landline 1:");
		lblLandline.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblLandline.setBounds(10, 278, 108, 25);
		getContentPane().add(lblLandline);
		
		landline = new JTextField();
		landline.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		landline.setColumns(10);
		landline.setBounds(149, 278, 150, 25);
		getContentPane().add(landline);
		
		JLabel lblLandline_1 = new JLabel("Landline 2:");
		lblLandline_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblLandline_1.setBounds(324, 278, 96, 25);
		getContentPane().add(lblLandline_1);
		
		landline1 = new JTextField();
		landline1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		landline1.setColumns(10);
		landline1.setBounds(418, 278, 150, 25);
		getContentPane().add(landline1);
		
		JLabel lblAddress_2 = new JLabel("Address 3:");
		lblAddress_2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress_2.setBounds(10, 139, 108, 25);
		getContentPane().add(lblAddress_2);
		
		address3 = new JTextField();
		address3.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address3.setColumns(10);
		address3.setBounds(147, 139, 427, 25);
		getContentPane().add(address3);
		
		try
		{
			String query1 = "select * from Creditor where ID='"+company+"'";
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
		    	email.setText(rs1.getString("Email"));
		    	website.setText(rs1.getString("Website"));
		    	gst.setText(rs1.getString("GST"));
		    	pan.setText(rs1.getString("Pan_no"));
		    	mobile1.setText(rs1.getString("Mobile1"));
		    	landline.setText(rs1.getString("Landline"));
		    	landline1.setText(rs1.getString("Landline1"));
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
