import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class Change_password extends JInternalFrame {
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Change_password frame = new Change_password();
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
	public Change_password() {
		
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
		setBounds((w-450)/2, (h-220)/2, 450, 220);
		setClosable(true);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 179);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblChangePassword = new JLabel("Change Password");
		lblChangePassword.setFont(new Font("Book Antiqua", Font.BOLD, 20));
		lblChangePassword.setBounds(130, 0, 190, 25);
		panel.add(lblChangePassword);

		JLabel lblOldPassword = new JLabel("Old Password:");
		lblOldPassword.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblOldPassword.setBounds(10, 40, 123, 25);
		panel.add(lblOldPassword);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		passwordField.setBounds(170, 40, 166, 25);
		panel.add(passwordField);
		
		final Connection connection1 = Databaseconnection.connection2();
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String pd=null;
				try
    			{
    				String query5 = "select password from Login";
                    PreparedStatement pmt5 = connection1.prepareStatement(query5);
                    ResultSet rs5 = pmt5.executeQuery();
        		    while(rs5.next())
        		    {
        		    	pd = rs5.getString("password");
        		    }
        		    rs5.close();
        		    pmt5.close();
    			}
    			catch (SQLException ae) {
        		    ae.printStackTrace();
        		}

				if(passwordField.getText().equals("")||passwordField_2.getText().equals("")||passwordField_1.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "please enter details");
				}
				else if (passwordField.getText().equals(pd)) {
					try
	    			{
						if(passwordField_1.getText().equals(passwordField_2.getText()))
						{
							String query1 = "UPDATE Login set Password ='"+passwordField_2.getText()+"'";
							PreparedStatement pmt1 = connection1.prepareStatement(query1);
							pmt1.executeUpdate();
							pmt1.close();
		            
							JOptionPane.showMessageDialog(null, "Passwor Updated Successfully!!!");
							dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Password Does Not Match");
						}
					}
	    			catch (SQLException ae) {
	        		    ae.printStackTrace();
	        		}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "enter Correct Details");
				}
			}
		});
		btnSubmit.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnSubmit.setBounds(149, 150, 89, 25);
		panel.add(btnSubmit);
		
		JLabel lblNewPassword = new JLabel("New Password:");
		lblNewPassword.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblNewPassword.setBounds(10, 75, 123, 25);
		panel.add(lblNewPassword);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		passwordField_1.setBounds(170, 75, 166, 25);
		panel.add(passwordField_1);
		
		JLabel lblReenterPassword = new JLabel("Re-enter Password:");
		lblReenterPassword.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblReenterPassword.setBounds(10, 110, 151, 25);
		panel.add(lblReenterPassword);
		
		passwordField_2 = new JPasswordField();
		passwordField_2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		passwordField_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pd=null;
				try
    			{
    				String query5 = "select password from Login";
                    PreparedStatement pmt5 = connection1.prepareStatement(query5);
                    ResultSet rs5 = pmt5.executeQuery();
        		    while(rs5.next())
        		    {
        		    	pd = rs5.getString("password");
        		    }
        		    rs5.close();
        		    pmt5.close();
    			}
    			catch (SQLException ae) {
        		    ae.printStackTrace();
        		}
				
				if(passwordField.getText().equals("")||passwordField_2.getText().equals("")||passwordField_1.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "please enter details");
				}
				else if (passwordField.getText().equals(pd)) {
					try
	    			{
						if(passwordField_1.getText().equals(passwordField_2.getText()))
						{
							String query1 = "UPDATE Login set Password ='"+passwordField_2.getText()+"'";
							PreparedStatement pmt1 = connection1.prepareStatement(query1);
							pmt1.executeUpdate();
							pmt1.close();
		                      
							JOptionPane.showMessageDialog(null, "Passwor Updated Successfully!!!");
							dispose();
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Password Does Not Match");
						}
					}
	    			catch (SQLException ae) {
	        		    ae.printStackTrace();
	        		}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "enter Correct Details");
				}
			}
		});
		passwordField_2.setBounds(170, 110, 160, 25);
		panel.add(passwordField_2);
	}
}