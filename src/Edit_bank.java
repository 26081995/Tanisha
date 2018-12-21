import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.Color;
import javax.swing.SwingConstants;

public class Edit_bank extends JInternalFrame {
	private JTextField bankname;
	private JTextField acno;
	private JTextField acname;
	private JTextField brname;
	private JTextField ifsc;
	private JTextField opbal;
	public String company = null;
	String p_id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edit_bank frame = new Edit_bank();
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
	public Edit_bank() {
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
        
		setBounds((w-572)/2, (h-341)/2, 572, 341);
		setClosable(true);
		getContentPane().setLayout(null);
		
		
		final java.sql.Connection connection = Databaseconnection.connection2();
		
		try 
		{
			String query5 = "select * from Company_temp";
	        PreparedStatement pmt5 = connection.prepareStatement(query5);
	        ResultSet rs5 = pmt5.executeQuery();
		    while(rs5.next())
		    {
		    	company = rs5.getString("Name");                                            
	        }
		    rs5.close();
		    pmt5.close();
		} 
		catch (Exception ae) {
			ae.printStackTrace();
			// TODO: handle exception
		}
		
		JLabel lblEditBank = new JLabel("Edit Bank");
		lblEditBank.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblEditBank.setBounds(220, 11, 112, 25);
		getContentPane().add(lblEditBank);
		
		JLabel label_1 = new JLabel("Bank Name:*");
		label_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_1.setBounds(52, 52, 112, 25);
		getContentPane().add(label_1);
		
		bankname = new JTextField();
		bankname.setEditable(false);
		bankname.setForeground(Color.RED);
		bankname.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		bankname.setColumns(10);
		bankname.setBounds(210, 50, 326, 25);
		getContentPane().add(bankname);
		
		JLabel label_2 = new JLabel("Account no:*");
		label_2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_2.setBounds(52, 85, 112, 25);
		getContentPane().add(label_2);
		
		acno = new JTextField();
		acno.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) 
			{
				char c = e.getKeyChar();
			
				if (!(Character.isDigit(c) || (c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
				        getToolkit().beep();
				        e.consume();
				      }
			}
		});
		acno.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		acno.setColumns(10);
		acno.setBounds(210, 85, 326, 25);
		getContentPane().add(acno);
		
		JLabel label_3 = new JLabel("Account name:*");
		label_3.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_3.setBounds(52, 120, 126, 25);
		getContentPane().add(label_3);
		
		acname = new JTextField();
		acname.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		acname.setColumns(10);
		acname.setBounds(210, 120, 326, 25);
		getContentPane().add(acname);
		
		JLabel label_4 = new JLabel("Branch Name:");
		label_4.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_4.setBounds(52, 156, 126, 25);
		getContentPane().add(label_4);
		
		brname = new JTextField();
		brname.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		brname.setColumns(10);
		brname.setBounds(209, 155, 327, 25);
		getContentPane().add(brname);
		
		JLabel label_5 = new JLabel("IFSC:");
		label_5.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_5.setBounds(52, 190, 77, 25);
		getContentPane().add(label_5);
		
		ifsc = new JTextField();
		ifsc.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		ifsc.setColumns(10);
		ifsc.setBounds(210, 190, 326, 24);
		getContentPane().add(ifsc);
		
		JLabel label_6 = new JLabel("Opening Balance:*");
		label_6.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_6.setBounds(52, 225, 148, 25);
		getContentPane().add(label_6);
		
		opbal = new JTextField();
		opbal.setHorizontalAlignment(SwingConstants.RIGHT);
		opbal.setForeground(Color.RED);
		opbal.setEditable(false);
		opbal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) 
			{
				char c = e.getKeyChar();
			
				if (!(Character.isDigit(c) || (c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
				        getToolkit().beep();
				        e.consume();
				      }
			}
		});
		opbal.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		opbal.setColumns(10);
		opbal.setBounds(210, 225, 326, 25);
		getContentPane().add(opbal);
		
		
		
		JButton buttonSubmit = new JButton("Update");
		buttonSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try 
				{
					 if(bankname.getText().equals("") || acno.getText().equals("") || acname.getText().equals("") || opbal.getText().equals("") )
	                    {
	                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
	                    }
	                    else 
	                    {
	                        
	                        String query1="update Bank set Bank_name = '"+bankname.getText()+"',Account_name = '"+acname.getText()+"', Branch = '"+brname.getText()+"',IFSC= '"+ifsc.getText()+"',Account_number='"+acno.getText()+"' where Id='"+p_id+"' and Company = '"+company+"'";
		                    PreparedStatement pmt1 = connection.prepareStatement(query1);
		                    pmt1.executeUpdate();
		                    pmt1.close();
		                    
	                        View_bank b = new View_bank();
		                    JDesktopPane desktopPane = getDesktopPane();
		                    desktopPane.add(b);
		                    b.show();
		                    dispose();
	                        
	                    }
				} 
				catch (Exception ae) 
				{
					ae.printStackTrace();
					// TODO: handle exception
				}
				
			}
		});
		buttonSubmit.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		buttonSubmit.setBounds(220, 266, 89, 23);
		getContentPane().add(buttonSubmit);

		
		opbal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try 
				{
					 if(bankname.getText().equals("") || acno.getText().equals("") || acname.getText().equals("") || opbal.getText().equals("") )
	                    {
	                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
	                    }
	                    else 
	                    {
	                        String query="insert into Bank (Bank_name,Account_number,Account_name,Branch,IFSC,Balance,Company) VALUES ('"+bankname.getText()+"','"+acno.getText()+"','"+acname.getText()+"','"+brname.getText()+"','"+ifsc.getText()+"','"+opbal.getText()+"','"+company+"')";
	                        PreparedStatement pmt = connection.prepareStatement(query);
	                        pmt.executeUpdate();
	                        pmt.close();
	                        
	                        
	                        Edit_bank b = new Edit_bank();
		                    JDesktopPane desktopPane = getDesktopPane();
		                    desktopPane.add(b);
		                    b.show();
		                    dispose();
	                    }
				} 
				catch (Exception ae) 
				{
					ae.printStackTrace();
					// TODO: handle exception
				}
				
			
			}
		});
	
		try
		{
			String query11 = "SELECT * from Bank_id ";
	        PreparedStatement pmt11 = connection.prepareStatement(query11);
	        ResultSet rs11 = pmt11.executeQuery();
	        while(rs11.next())
	        {
	        	p_id=rs11.getString("Id");
	        }
	        rs11.close();
	        pmt11.close();
	        
			
	        
	        String query1 = "SELECT * from Bank where Id='"+p_id+"' ";
	        PreparedStatement pmt1 = connection.prepareStatement(query1);
	        ResultSet rs1 = pmt1.executeQuery();
	        while(rs1.next())
	        {
	        	bankname.setText(rs1.getString("Bank_name"));
	        	acno.setText(rs1.getString("Account_number"));
		    	acname.setText(rs1.getString("Account_name"));
	        	brname.setText(rs1.getString("Branch"));
	        	ifsc.setText(rs1.getString("IFSC"));
	        	opbal.setText((rs1.getString("Balance")));
	        }
	        rs1.close();
	        pmt1.close();
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
	}

}
