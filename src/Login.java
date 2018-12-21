import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	int hj=0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		
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
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((w-450)/2, (h-300)/2, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 414, 240);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblLogin.setBounds(170, 11, 96, 35);
		panel.add(lblLogin);
		
		JLabel lblUser = new JLabel("User Name:");
		lblUser.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblUser.setBounds(51, 57, 110, 24);
		panel.add(lblUser);
		
		textField = new JTextField();
		textField.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		textField.setBounds(197, 57, 150, 24);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblPassword.setBounds(51, 102, 110, 24);
		panel.add(lblPassword);
		
		textField_1 = new JPasswordField();
		textField_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		textField_1.setColumns(10);
		textField_1.setBounds(197, 102, 150, 24);
		panel.add(textField_1);
		
		final Connection connection1 = Databaseconnection.connection2();
		
		JButton btnLogin = new JButton("Login");
		textField_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name=null,pd=null;
				
				try
    			{
    				String query5 = "select * from Login";
                    PreparedStatement pmt5 = connection1.prepareStatement(query5);
                    ResultSet rs5 = pmt5.executeQuery();
        		    while(rs5.next())
        		    {
        		    	name = rs5.getString("username");                                            
                        pd = rs5.getString("password");
                    }
        		    rs5.close();
        		    pmt5.close();
    			}
    			catch (SQLException ae) {
        		    ae.printStackTrace();
        		}
				
				if(textField.getText().equals("") || textField_1.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "please enter details");
				}
				else
				{
					int i=0;
					
					try
					{
						String query5 = "select * from Login where username='"+textField.getText()+"' and password='"+textField_1.getText()+"'";
	                    PreparedStatement pmt5 = connection1.prepareStatement(query5);
	                    ResultSet rs5 = pmt5.executeQuery();
	        		    while(rs5.next())
	        		    {
	        		    	i++;
	                    }
	        		    rs5.close();
	        		    pmt5.close();
					}
					catch(Exception ae)
					{
						ae.printStackTrace();
					}
					
					if(i>0)
					{
						Company c=new Company();
    		        	c.show();
    		            dispose();
	    			}
        		    else
    				{
    					textField.setText("");
    					textField_1.setText("");
    					textField.requestFocus();
    					JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
    				}
        		    
				}
				
				 
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name=null,pd=null;
				
				try
    			{
    				String query5 = "select * from Login";
                    PreparedStatement pmt5 = connection1.prepareStatement(query5);
                    ResultSet rs5 = pmt5.executeQuery();
        		    while(rs5.next())
        		    {
        		    	name = rs5.getString("username");                                            
                        pd = rs5.getString("password");
                    }
        		    rs5.close();
        		    pmt5.close();

        		    
        		}
    			catch (SQLException ae) {
        		    ae.printStackTrace();
        		}
				
				if(textField.getText().equals("") || textField_1.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "please enter details");
				}
				else
				{
					int i=0;
					
					try
					{
						String query5 = "select * from Login where username='"+textField.getText()+"' and password='"+textField_1.getText()+"'";
	                    PreparedStatement pmt5 = connection1.prepareStatement(query5);
	                    ResultSet rs5 = pmt5.executeQuery();
	        		    while(rs5.next())
	        		    {
	        		    	i++;
	                    }
	        		    rs5.close();
	        		    pmt5.close();
					}
					catch(Exception ae)
					{
						ae.printStackTrace();
					}
					
					if(i>0)
					{
						Company c=new Company();
    		        	c.show();
    		            dispose();
	    			}
        		    else
    				{
    					textField.setText("");
    					textField_1.setText("");
    					textField.requestFocus();
    					JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
    				}
        		    
				}
			}
		});
		
		btnLogin.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnLogin.setBounds(156, 157, 89, 23);
		panel.add(btnLogin);
		
		try
		{
			int k=0,k1=0;
			String query5 = "select * from sqlite_sequence where name='Version'";
            PreparedStatement pmt5 = connection1.prepareStatement(query5);
            ResultSet rs5 = pmt5.executeQuery();
		    while(rs5.next())
		    {
		    	k++;
		    }
		    rs5.close();
		    pmt5.close();
		    
		    if(k==0)
		    {
		    	String query3 = "CREATE TABLE `Version` (`ID`	INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,`Version`	TEXT);";
				PreparedStatement pmt3 = connection1.prepareStatement(query3);
    		    pmt3.executeUpdate();
    		    pmt3.close();
    		    
    		    String query31 = "insert into Version (Version) Values ('0.0')";
				PreparedStatement pmt31 = connection1.prepareStatement(query31);
    		    pmt31.executeUpdate();
    		    pmt31.close();
		    }

		    String query51 = "select * from Version where Version='6.0'";
            PreparedStatement pmt51 = connection1.prepareStatement(query51);
            ResultSet rs51 = pmt51.executeQuery();
		    while(rs51.next())
		    {
		    	k1++;
		    }
		    rs51.close();
		    pmt51.close();

		    if(k1==0)
		    {
		    	try
		    	{
		    		String query1 = "ALTER TABLE Quotation ADD Location TEXT;";
					PreparedStatement pmt1 = connection1.prepareStatement(query1);
	    		    pmt1.executeUpdate();
	    		    pmt1.close();
	    		    
	    		    String query11 = "update Quotation set Location = 'Gujarat';";
					PreparedStatement pmt11 = connection1.prepareStatement(query11);
	    		    pmt11.executeUpdate();
	    		    pmt11.close();
		    	}
		    	catch(Exception ae)
		    	{
		    		ae.printStackTrace();
		    	}

		    	String query31 = "insert into Version (Version) Values ('6.0')";
				PreparedStatement pmt31 = connection1.prepareStatement(query31);
    		    pmt31.executeUpdate();
    		    pmt31.close();
    		}
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
		
	}
}