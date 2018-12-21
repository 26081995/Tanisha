import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

public class Add_client extends JInternalFrame {
	private JTextField name;
	private JTextField address1;
	private JTextField address2;
	private JTextField mobile;
	private JTextField email;
	private JTextField website;
	private JTextField gst;
	private JTextField balance;
	String company=null;
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
					Add_client frame = new Add_client();
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
	public Add_client() {
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
	        setBounds((w-600)/2, (h-603)/2, 600, 603);
			getContentPane().setLayout(null);
		
			final java.sql.Connection connection = Databaseconnection.connection2();
			
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
			
			JLabel lblAddress_2 = new JLabel("Address 3:");
			lblAddress_2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
			lblAddress_2.setBounds(10, 175, 108, 25);
			getContentPane().add(lblAddress_2);
			
			address3 = new JTextField();
			address3.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
			address3.setColumns(10);
			address3.setBounds(147, 175, 427, 25);
			getContentPane().add(address3);
			
			
			JLabel lblAddClient = new JLabel("Add Client");
		lblAddClient.setBounds(215, 28, 154, 36);
		lblAddClient.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		getContentPane().add(lblAddClient);
		
		JLabel lblName = new JLabel("Name:*");
		lblName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblName.setBounds(10, 75, 128, 25);
		getContentPane().add(lblName);
		
		name = new JTextField();
		name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		name.setColumns(10);
		name.setBounds(147, 75, 427, 25);
		getContentPane().add(name);
		
		JLabel lblMobile_1 = new JLabel("Mobile 2:");
		lblMobile_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblMobile_1.setBounds(316, 282, 111, 25);
		getContentPane().add(lblMobile_1);
		
		mobile1 = new JTextField();
		mobile1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		mobile1.setColumns(10);
		mobile1.setBounds(424, 281, 150, 25);
		getContentPane().add(mobile1);
		
		JLabel lblLandline_1 = new JLabel("Landline 1:");
		lblLandline_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblLandline_1.setBounds(10, 317, 108, 25);
		getContentPane().add(lblLandline_1);
		
		landline = new JTextField();
		landline.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		landline.setColumns(10);
		landline.setBounds(147, 317, 150, 25);
		getContentPane().add(landline);
		
		JLabel lblLandline = new JLabel("Landline 2:");
		lblLandline.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblLandline.setBounds(316, 318, 111, 25);
		getContentPane().add(lblLandline);
		
		landline1 = new JTextField();
		landline1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		landline1.setColumns(10);
		landline1.setBounds(424, 316, 150, 25);
		getContentPane().add(landline1);
		
		JLabel lblAddress = new JLabel("Address 1:");
		lblAddress.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress.setBounds(10, 110, 108, 25);
		getContentPane().add(lblAddress);
		
		address1 = new JTextField();
		address1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address1.setColumns(10);
		address1.setBounds(147, 110, 427, 25);
		getContentPane().add(address1);
		
		JLabel lblAddress_1 = new JLabel("Address 2:");
		lblAddress_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress_1.setBounds(10, 145, 108, 25);
		getContentPane().add(lblAddress_1);
		
		address2 = new JTextField();
		address2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address2.setColumns(10);
		address2.setBounds(147, 145, 427, 25);
		getContentPane().add(address2);
		
		JLabel label_3 = new JLabel("State:*");
		label_3.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_3.setBounds(10, 211, 79, 25);
		getContentPane().add(label_3);
		
		JComboBox state = new JComboBox();
		state.addItem("SELECT");
		state.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		state.setBounds(147, 211, 425, 25);
		getContentPane().add(state);
		
		JLabel label_4 = new JLabel("City:*");
		label_4.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_4.setBounds(10, 246, 53, 25);
		getContentPane().add(label_4);
		
		JComboBox city = new JComboBox();
		city.addItem("SELECT");
		city.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		city.setBounds(147, 246, 425, 25);
		getContentPane().add(city);
		
		JLabel lblMobile = new JLabel("Mobile 1:");
		lblMobile.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblMobile.setBounds(10, 281, 108, 25);
		getContentPane().add(lblMobile);
		
		mobile = new JTextField();
		mobile.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		mobile.setColumns(10);
		mobile.setBounds(147, 281, 150, 25);
		getContentPane().add(mobile);
		
		JLabel label_6 = new JLabel("Email:");
		label_6.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_6.setBounds(10, 388, 66, 25);
		getContentPane().add(label_6);
		
		email = new JTextField();
		email.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		email.setColumns(10);
		email.setBounds(147, 388, 425, 25);
		getContentPane().add(email);
		
		JLabel label_7 = new JLabel("Website:");
		label_7.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_7.setBounds(10, 423, 108, 25);
		getContentPane().add(label_7);
		
		website = new JTextField();
		website.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		website.setColumns(10);
		website.setBounds(147, 423, 425, 25);
		getContentPane().add(website);
		
		JLabel lblGstNo = new JLabel("GST No:");
		lblGstNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblGstNo.setBounds(10, 353, 108, 25);
		getContentPane().add(lblGstNo);
		
		gst = new JTextField();
		gst.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		gst.setColumns(10);
		gst.setBounds(147, 353, 427, 25);
		getContentPane().add(gst);
		
		JLabel lblCurrentBalance = new JLabel("Current Balance:*");
		lblCurrentBalance.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblCurrentBalance.setBounds(10, 493, 126, 25);
		getContentPane().add(lblCurrentBalance);
		
		balance = new JTextField();
		balance.setText("0");
		balance.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		balance.setColumns(10);
		balance.setBounds(147, 493, 421, 25);
		getContentPane().add(balance);
		
		JLabel lblPanNo = new JLabel("PAN No:");
		lblPanNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblPanNo.setBounds(10, 458, 108, 25);
		getContentPane().add(lblPanNo);
		
		pan = new JTextField();
		pan.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		pan.setColumns(10);
		pan.setBounds(147, 458, 427, 25);
		getContentPane().add(pan);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					
					int j=0;
					String query5 = "SELECT  Name from Client where  Name='"+name.getText()+"' and Company='"+company+"'";
				    PreparedStatement pmt5 = connection.prepareStatement(query5);
				    ResultSet rs5 = pmt5.executeQuery();
					while(rs5.next())
					{
						j++;
				    }
					rs5.close();
					pmt5.close();
					
					
					String text = mobile.getText();
		            int counter = text.length();
		            if(counter==0)
		            {
		            	counter=10;
		            }
		            if(name.getText().equals("")  || city.getSelectedItem().toString().equals("SELECT")||balance.getText().equals(""))
    				{
    					JOptionPane.showMessageDialog(null, "please enter details");
    				}
		            else if (j>0) 
					{
						JOptionPane.showMessageDialog(null, "Client already available..");
					}
					else if(counter != 10)
        		    {
        		    	JOptionPane.showMessageDialog(null, "please enter Valid mobile no");
    				}
					else
					{
						String query="insert into Client(Company,Name,Address1,Address2,City,State,Mobile,Email,GST,Pan_no,Website,Balance,Mobile1,Landline,Landline1,Address3) Values ('"+company+"','"+name.getText()+"','"+address1.getText()+"','"+address2.getText()+"','"+city.getSelectedItem().toString()+"','"+state.getSelectedItem().toString()+"','"+mobile.getText()+"','"+email.getText()+"','"+gst.getText()+"','"+pan.getText()+"','"+website.getText()+"','"+balance.getText()+"','"+mobile1.getText()+"','"+landline.getText()+"','"+landline1.getText()+"','"+address3.getText()+"')";
			            PreparedStatement pmt = connection.prepareStatement(query);
			            pmt.executeUpdate();
			            pmt.close();
			            
			            Add_client b = new Add_client();
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
		btnSubmit.setBounds(256, 533, 89, 25);
		getContentPane().add(btnSubmit);
		
		
		balance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					String text = mobile.getText();
		            int counter = text.length();
		            if(counter==0)
		            {
		            	counter=10;
		            }
		            if(name.getText().equals("")  || city.getSelectedItem().toString().equals("SELECT")||balance.getText().equals(""))
    				{
    					JOptionPane.showMessageDialog(null, "please enter details");
    				}
					else if(counter != 10)
        		    {
        		    	JOptionPane.showMessageDialog(null, "please enter Valid mobile no");
    				}
					else
					{
						String query="insert into Client(Company,Name,Address1,Address2,City,State,Mobile,Email,GST,Pan_no,Website,Balance,Mobile1,Landline,Landline1,Address3) Values ('"+company+"','"+name.getText()+"','"+address1.getText()+"','"+address2.getText()+"','"+city.getSelectedItem().toString()+"','"+state.getSelectedItem().toString()+"','"+mobile.getText()+"','"+email.getText()+"','"+gst.getText()+"','"+pan.getText()+"','"+website.getText()+"','"+balance.getText()+"','"+mobile1.getText()+"','"+landline.getText()+"','"+landline1.getText()+"','"+address3.getText()+"')";
			            PreparedStatement pmt = connection.prepareStatement(query);
			            pmt.executeUpdate();
			            pmt.close();

			            Add_client b = new Add_client();
	                    JDesktopPane desktopPane = getDesktopPane();
	                    desktopPane.add(b);
	                    b.show();
	                    dispose();
					}
				}
				catch (Exception ae) {
					ae.printStackTrace();
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
		
		
	}
	public static boolean empty( final String s ) 
	{
		return s == null || s.trim().isEmpty();
	}
}