import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class Add_bank extends JInternalFrame {
	private JTextField bank_name;
	private JTextField account_no;
	private JTextField balance;
	private JTextField branch_name;
	String company=null;
	private JTextField account_name;
	private JTextField ifsc;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_bank frame = new Add_bank();
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
	public Add_bank() {
		
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
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 546, 307);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblAddAccount = new JLabel("Add Bank");
		lblAddAccount.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblAddAccount.setBounds(220, 11, 128, 25);
		panel.add(lblAddAccount);
		
		JLabel lblBankName = new JLabel("Bank Name:*");
		lblBankName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblBankName.setBounds(52, 50, 112, 25);
		panel.add(lblBankName);
		
		bank_name = new JTextField();
		bank_name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		bank_name.setColumns(10);
		bank_name.setBounds(210, 50, 326, 25);
		panel.add(bank_name);
		
		JLabel lblAccountNo = new JLabel("Account no:*");
		lblAccountNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAccountNo.setBounds(52, 85, 112, 25);
		panel.add(lblAccountNo);
		
		account_no = new JTextField();
		account_no.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
                if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) || (c=='.'))){
                    getToolkit().beep();
                    e.consume();
                }
			}
		});
		
		account_no.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		account_no.setColumns(10);
		account_no.setBounds(210, 85, 326, 25);
		panel.add(account_no);
		
		JLabel lblOpeningBalance = new JLabel("Opening Balance:*");
		lblOpeningBalance.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblOpeningBalance.setBounds(52, 225, 148, 25);
		panel.add(lblOpeningBalance);
		
		balance = new JTextField();
		balance.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
                if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) || (c=='.'))){
                    getToolkit().beep();
                    e.consume();
                }
			}
		});
		
		balance.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		balance.setColumns(10);
		balance.setBounds(210, 225, 326, 25);
		panel.add(balance);
		
		
		JLabel lblBranchName = new JLabel("Branch Name:");
		lblBranchName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblBranchName.setBounds(52, 155, 126, 25);
		panel.add(lblBranchName);
		
		branch_name = new JTextField();
		branch_name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		branch_name.setColumns(10);
		branch_name.setBounds(209, 155, 327, 25);
		panel.add(branch_name);

		final Connection connection = Databaseconnection.connection2();
		
		try
		{
        	String query1 = "select * from Company_temp";
		    PreparedStatement pmt1 = connection.prepareStatement(query1);
		    ResultSet rs = pmt1.executeQuery();
		    while(rs.next())
		    {
		    	company=rs.getString("Name");
		    }
		    rs.close();
		}
        catch (SQLException ae) {
		    ae.printStackTrace();
		}
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					if(bank_name.getText().equals("")||account_no.getText().equals("")||balance.getText().equals("")||account_name.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    else
                    {
                        String query="insert into Bank (Company,Bank_name,Account_number,Account_name,Branch,IFSC,Balance) VALUES ('"+company+"','"+bank_name.getText()+"','"+account_no.getText()+"','"+account_name.getText()+"','"+branch_name.getText()+"','"+ifsc.getText()+"','"+balance.getText()+"')";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                        Add_bank b = new Add_bank();
	                    JDesktopPane desktopPane = getDesktopPane();
	                    desktopPane.add(b);
	                    b.show();
	                    dispose();
                    }
                    
                }
               catch (SQLException ae) {
                   ae.printStackTrace();
               }
				
			}
		});
		button.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		button.setBounds(220, 266, 89, 23);
		panel.add(button);
		
		JLabel lblAccountName = new JLabel("Account name:*");
		lblAccountName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAccountName.setBounds(52, 120, 126, 25);
		panel.add(lblAccountName);
		
		account_name = new JTextField();
		account_name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		account_name.setColumns(10);
		account_name.setBounds(210, 120, 326, 25);
		panel.add(account_name);
		
		JLabel lblIfsc = new JLabel("IFSC:");
		lblIfsc.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblIfsc.setBounds(52, 190, 77, 25);
		panel.add(lblIfsc);
		
		ifsc = new JTextField();
		ifsc.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		ifsc.setColumns(10);
		ifsc.setBounds(210, 190, 326, 25);
		panel.add(ifsc);

		balance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					if(bank_name.getText().equals("")||account_no.getText().equals("")||balance.getText().equals("")||account_name.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    else
                    {
                        String query="insert into Bank (Company,Bank_name,Account_number,Account_name,Branch,IFSC,Balance) VALUES ('"+company+"','"+bank_name.getText()+"','"+account_no.getText()+"','"+account_name.getText()+"','"+branch_name.getText()+"','"+ifsc.getText()+"','"+balance.getText()+"')";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                        Add_bank b = new Add_bank();
	                    JDesktopPane desktopPane = getDesktopPane();
	                    desktopPane.add(b);
	                    b.show();
	                    dispose();
                    }
                    
                }
               catch (SQLException ae) {
                   ae.printStackTrace();
               }
			}
		});
	}
}