import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.event.ActionEvent;

import com.sun.deploy.net.URLEncoder;
import com.toedter.calendar.JDateChooser;



import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.xml.soap.Detail;
import javax.swing.JButton;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextArea;

public class Add_voucher extends JInternalFrame {
	private JTextField amount;
	private JTextField cheque;
	private JTextArea textField_2;
	String company=null,company1=null;
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_voucher frame = new Add_voucher();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Add_voucher() {
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
		setBounds((w-450)/2, (h-497)/2, 483, 497);
		setClosable(true);
		getContentPane().setLayout(null);

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
		
		

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 457, 598);
		getContentPane().add(panel);
		panel.setLayout(null);

		
		JLabel lblVouchers = new JLabel("Voucher");
		lblVouchers.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblVouchers.setBounds(171, 10, 100, 25);
		panel.add(lblVouchers);

		JLabel lblType = new JLabel("Type*");
		lblType.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblType.setBounds(58, 50, 119, 25);
		panel.add(lblType);

		JComboBox type = new JComboBox();
		type.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		type.addItem("SELECT");
		type.addItem("Credit");
		type.addItem("Debit");
		type.setBounds(191, 50, 193, 25);
		panel.add(type);

		JLabel lblAccount = new JLabel("Account*");
		lblAccount.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAccount.setBounds(58, 85, 119, 25);
		panel.add(lblAccount);

		JComboBox account = new JComboBox();
		account.setEnabled(true);
		account.addItem("SELECT");
		account.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		account.setBounds(191, 85, 193, 25);
		panel.add(account);

		JLabel lblName = new JLabel("Name*");
		lblName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblName.setBounds(58, 120, 119, 25);
		panel.add(lblName);

		JComboBox name = new JComboBox();
		name.setEnabled(true);
		name.addItem("SELECT");
		name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		name.setBounds(191, 120, 193, 25);
		panel.add(name);

		JLabel lblDate = new JLabel("Date*");
		lblDate.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblDate.setBounds(58, 155, 113, 25);
		panel.add(lblDate);
		Date date=new Date();

		JLabel lblAmount = new JLabel("Amount*");
		lblAmount.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAmount.setBounds(58, 190, 123, 25);
		panel.add(lblAmount);

		amount = new JTextField();
		amount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char c = e.getKeyChar();
                if((Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) || (c=='.'))){
                	amount.setText((amount.getText()));
                    e.consume();
                }
			}
		});
		amount.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		amount.setBounds(191, 190, 193, 25);
		panel.add(amount);
		amount.setColumns(10);

		JLabel lblPaymentType = new JLabel("Payment Type*");
		lblPaymentType.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblPaymentType.setBounds(58, 225, 123, 25);
		panel.add(lblPaymentType);

		JComboBox payment = new JComboBox();
		payment.addItem("Cash");
		payment.addItem("Cheque");
		payment.addItem("Net Banking");
		payment.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		payment.setEnabled(true);
		payment.setBounds(191, 225, 193, 25);
		panel.add(payment);

		JLabel lblChequeNumber = new JLabel("Cheque No.");
		lblChequeNumber.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblChequeNumber.setBounds(58, 260, 113, 25);
		panel.add(lblChequeNumber);

		cheque = new JTextField();
		cheque.setEnabled(false);
		cheque.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		cheque.setColumns(10);
		cheque.setBounds(191, 260, 193, 25);
		panel.add(cheque);

		JLabel lblDetails = new JLabel("Details");
		lblDetails.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblDetails.setBounds(58, 366, 119, 25);
		panel.add(lblDetails);

		textField_2 = new JTextArea();
		textField_2.setLineWrap(true);
		textField_2.setWrapStyleWord(true);
		textField_2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		textField_2.setColumns(10);
		textField_2.setBounds(191, 331, 193, 96);
		panel.add(textField_2);

		JLabel lblBankName = new JLabel("Bank Name*");
		lblBankName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblBankName.setBounds(58, 295, 119, 25);
		panel.add(lblBankName);

		JComboBox bank = new JComboBox();
		bank.setEnabled(false);
		bank.addItem("SELECT");
		bank.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		bank.setBounds(191, 295, 193, 25);
		panel.add(bank);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{	
					Date startDate = null;

	                try 
	                {
	                	SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
	                    startDate = sm.parse(textField.getText());
	                }
	                catch (ParseException ae) {
	                    ae.printStackTrace();
	                }

	                SimpleDateFormat sm2 = new SimpleDateFormat("dd-MM-yyyy");
	                String strDate = sm2.format(startDate);

					String mobile=null;
					if(type.getSelectedItem().toString().equals("SELECT") ||account.getSelectedItem().toString().equals("SELECT")||name.getSelectedItem().toString().equals("SELECT")||textField.getText().equals("")||amount.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please Fill Up Details");
					}
					//Retail start

					//client start
					else if(type.getSelectedItem().toString().equals("Credit") && account.getSelectedItem().toString().equals("Client"))
					{
						if(payment.getSelectedItem().toString().equals("Cash"))
						{
							amount.setText((amount.getText()));
							
							String query="insert into Voucher (Type,Account,Name,Date,Amount,Payment,Detail,Company) VALUES ('"+type.getSelectedItem().toString()+"','"+account.getSelectedItem().toString()+"','"+name.getSelectedItem().toString()+"','"+textField.getText()+"','"+amount.getText()+"','"+payment.getSelectedItem().toString()+"','"+textField_2.getText()+"','"+company+"')";
		                    PreparedStatement pmt = connection.prepareStatement(query);
		                    pmt.executeUpdate();
		                    pmt.close();  

		                    double t2 = 0;
		                    String query3 = "select * from Client where Name ='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
		                    PreparedStatement pmt3 = connection.prepareStatement(query3);
		                    ResultSet rs3 = pmt3.executeQuery();
		                    while(rs3.next())
		                    {
		                         t2 = rs3.getFloat("Balance");
		                    }
		                    pmt3.close();
		                    rs3.close();
		                    double t4 = t2 - Double.parseDouble(amount.getText());

		                    String query4 = "UPDATE Client set Balance ="+t4+" where Name ='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
		                    PreparedStatement pmt4 = connection.prepareStatement(query4);
		                    pmt4.executeUpdate();
		                    pmt4.close();
		                    
		                   
						}
						else
						{
							if(bank.getSelectedItem().toString().equals("SELECT"))
							{
								JOptionPane.showMessageDialog(null, "Please Fill up Details");
							}
							else
							{
								amount.setText((amount.getText()));
								
								String query="insert into Voucher (Type,Account,Name,Date,Amount,Payment,Cheque,Bank,Detail,Bank_name,Company) VALUES ('"+type.getSelectedItem().toString()+"','"+account.getSelectedItem().toString()+"','"+name.getSelectedItem().toString()+"','"+textField.getText()+"','"+amount.getText()+"','"+payment.getSelectedItem().toString()+"','"+cheque.getText()+"','"+bank.getSelectedItem().toString()+"','"+textField_2.getText()+"','-','"+company+"')";
			                    PreparedStatement pmt = connection.prepareStatement(query);
			                    pmt.executeUpdate();
			                    pmt.close();

			                    double t2 = 0;
			                    String query3 = "select * from Client where Name ='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
			                    PreparedStatement pmt3 = connection.prepareStatement(query3);
			                    ResultSet rs3 = pmt3.executeQuery();
			                    while(rs3.next())
			                    {
			                         t2 = rs3.getFloat("Balance");
			                    }
			                    pmt3.close();
			                    rs3.close();
			                    double t4 = t2 - Double.parseDouble(amount.getText());

			                    String query4 = "UPDATE Client set Balance ="+t4+" where Name ='"+name.getSelectedItem().toString()+"'  and Company='"+company+"'";
			                    PreparedStatement pmt4 = connection.prepareStatement(query4);
			                    pmt4.executeUpdate();
			                    pmt4.close();

			                    double t5 = 0;
			                    String query5 = "select * from Bank where Bank_name ='"+bank.getSelectedItem().toString()+"'  and Company='"+company+"'";
			                    PreparedStatement pmt5 = connection.prepareStatement(query5);
			                    ResultSet rs5 = pmt5.executeQuery();
			                    while(rs5.next())
			                    {
			                         t5 = rs5.getFloat("Balance");
			                    }
			                    pmt5.close();
			                    rs5.close();
			                    double t6 = t5 + Double.parseDouble(amount.getText());

			                    String query6 = "UPDATE Bank set Balance ="+t6+" where Bank_name ='"+bank.getSelectedItem().toString()+"'  and Company='"+company+"'";
			                    PreparedStatement pmt6 = connection.prepareStatement(query6);
			                    pmt6.executeUpdate();
			                    pmt6.close();
			                    
							}
						}
					}//client end
					
					//creditor start
					else if(type.getSelectedItem().toString().equals("Debit") && account.getSelectedItem().toString().equals("Creditor"))
					{
						if(payment.getSelectedItem().toString().equals("Cash"))
						{
							amount.setText((amount.getText()));
							
							String query="insert into Voucher (Type,Account,Name,Date,Amount,Payment,Detail,Company) VALUES ('"+type.getSelectedItem().toString()+"','"+account.getSelectedItem().toString()+"','"+name.getSelectedItem().toString()+"','"+textField.getText()+"','"+amount.getText()+"','"+payment.getSelectedItem().toString()+"','"+textField_2.getText()+"','"+company+"')";
		                    PreparedStatement pmt = connection.prepareStatement(query);
		                    pmt.executeUpdate();
		                    pmt.close();  

		                    double t2 = 0;
		                    String query3 = "select * from Creditor where Name ='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
		                    PreparedStatement pmt3 = connection.prepareStatement(query3);
		                    ResultSet rs3 = pmt3.executeQuery();
		                    while(rs3.next())
		                    {
		                         t2 = rs3.getFloat("Balance");
		                    }
		                    pmt3.close();
		                    rs3.close();
		                    double t4 = t2 + Double.parseDouble(amount.getText());

		                    String query4 = "UPDATE Creditor set Balance ="+t4+" where Name ='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
		                    PreparedStatement pmt4 = connection.prepareStatement(query4);
		                    pmt4.executeUpdate();
		                    pmt4.close();
		                    
		                   
						}
						else
						{
							if(bank.getSelectedItem().toString().equals("SELECT"))
							{
								JOptionPane.showMessageDialog(null, "Please Fill up Details");
							}
							else
							{
								amount.setText((amount.getText()));
								
								String query="insert into Voucher (Type,Account,Name,Date,Amount,Payment,Cheque,Bank,Detail,Bank_name,Company) VALUES ('"+type.getSelectedItem().toString()+"','"+account.getSelectedItem().toString()+"','"+name.getSelectedItem().toString()+"','"+textField.getText()+"','"+amount.getText()+"','"+payment.getSelectedItem().toString()+"','"+cheque.getText()+"','"+bank.getSelectedItem().toString()+"','"+textField_2.getText()+"','-','"+company+"')";
			                    PreparedStatement pmt = connection.prepareStatement(query);
			                    pmt.executeUpdate();
			                    pmt.close();

			                    double t2 = 0;
			                    String query3 = "select * from Creditor where Name ='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
			                    PreparedStatement pmt3 = connection.prepareStatement(query3);
			                    ResultSet rs3 = pmt3.executeQuery();
			                    while(rs3.next())
			                    {
			                         t2 = rs3.getFloat("Balance");
			                    }
			                    pmt3.close();
			                    rs3.close();
			                    double t4 = t2 + Double.parseDouble(amount.getText());

			                    String query4 = "UPDATE Creditor set Balance ="+t4+" where Name ='"+name.getSelectedItem().toString()+"'  and Company='"+company+"'";
			                    PreparedStatement pmt4 = connection.prepareStatement(query4);
			                    pmt4.executeUpdate();
			                    pmt4.close();

			                    double t5 = 0;
			                    String query5 = "select * from Bank where Bank_name ='"+bank.getSelectedItem().toString()+"'  and Company='"+company+"'";
			                    PreparedStatement pmt5 = connection.prepareStatement(query5);
			                    ResultSet rs5 = pmt5.executeQuery();
			                    while(rs5.next())
			                    {
			                         t5 = rs5.getFloat("Balance");
			                    }
			                    pmt5.close();
			                    rs5.close();
			                    double t6 = t5 - Double.parseDouble(amount.getText());

			                    String query6 = "UPDATE Bank set Balance ="+t6+" where Bank_name ='"+bank.getSelectedItem().toString()+"'  and Company='"+company+"'";
			                    PreparedStatement pmt6 = connection.prepareStatement(query6);
			                    pmt6.executeUpdate();
			                    pmt6.close();
			                    
							}
						}
					}//creditor end
					
					
					//employee start
					else if(type.getSelectedItem().toString().equals("Debit") && account.getSelectedItem().toString().equals("Driver"))
					{
						if(payment.getSelectedItem().toString().equals("Cash"))
						{
							amount.setText((amount.getText()));
							
							String query="insert into Voucher (Type,Account,Name,Date,Amount,Payment,Detail,Company) VALUES ('"+type.getSelectedItem().toString()+"','"+account.getSelectedItem().toString()+"','"+name.getSelectedItem().toString()+"','"+textField.getText()+"','"+amount.getText()+"','"+payment.getSelectedItem().toString()+"','"+textField_2.getText()+"','"+company+"')";
		                    PreparedStatement pmt = connection.prepareStatement(query);
		                    pmt.executeUpdate();
		                    pmt.close();  

		                    double t2 = 0;
		                    String query3 = "select * from Driver where Name ='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
		                    PreparedStatement pmt3 = connection.prepareStatement(query3);
		                    ResultSet rs3 = pmt3.executeQuery();
		                    while(rs3.next())
		                    {
		                         t2 = rs3.getFloat("Balance");
		                    }
		                    pmt3.close();
		                    rs3.close();
		                    double t4 = t2 - Double.parseDouble(amount.getText());

		                    String query4 = "UPDATE Driver set Balance ="+t4+" where Name ='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
		                    PreparedStatement pmt4 = connection.prepareStatement(query4);
		                    pmt4.executeUpdate();
		                    pmt4.close();
		                    
		                   
						}
						else
						{
							if(bank.getSelectedItem().toString().equals("SELECT"))
							{
								JOptionPane.showMessageDialog(null, "Please Fill up Details");
							}
							else
							{
								amount.setText((amount.getText()));
								
								String query="insert into Voucher (Type,Account,Name,Date,Amount,Payment,Cheque,Bank,Detail,Bank_name,Company) VALUES ('"+type.getSelectedItem().toString()+"','"+account.getSelectedItem().toString()+"','"+name.getSelectedItem().toString()+"','"+textField.getText()+"','"+amount.getText()+"','"+payment.getSelectedItem().toString()+"','"+cheque.getText()+"','"+bank.getSelectedItem().toString()+"','"+textField_2.getText()+"','-','"+company+"')";
			                    PreparedStatement pmt = connection.prepareStatement(query);
			                    pmt.executeUpdate();
			                    pmt.close();

			                    double t2 = 0;
			                    String query3 = "select * from Driver where Name ='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
			                    PreparedStatement pmt3 = connection.prepareStatement(query3);
			                    ResultSet rs3 = pmt3.executeQuery();
			                    while(rs3.next())
			                    {
			                         t2 = rs3.getFloat("Balance");
			                    }
			                    pmt3.close();
			                    rs3.close();
			                    double t4 = t2 - Double.parseDouble(amount.getText());

			                    String query4 = "UPDATE Driver set Balance ="+t4+" where Name ='"+name.getSelectedItem().toString()+"'  and Company='"+company+"'";
			                    PreparedStatement pmt4 = connection.prepareStatement(query4);
			                    pmt4.executeUpdate();
			                    pmt4.close();

			                    double t5 = 0;
			                    String query5 = "select * from Bank where Bank_name ='"+bank.getSelectedItem().toString()+"'  and Company='"+company+"'";
			                    PreparedStatement pmt5 = connection.prepareStatement(query5);
			                    ResultSet rs5 = pmt5.executeQuery();
			                    while(rs5.next())
			                    {
			                         t5 = rs5.getFloat("Balance");
			                    }
			                    pmt5.close();
			                    rs5.close();
			                    double t6 = t5 - Double.parseDouble(amount.getText());

			                    String query6 = "UPDATE Bank set Balance ="+t6+" where Bank_name ='"+bank.getSelectedItem().toString()+"'  and Company='"+company+"'";
			                    PreparedStatement pmt6 = connection.prepareStatement(query6);
			                    pmt6.executeUpdate();
			                    pmt6.close();
			                    
							}
						}
					}//employee end

					//expense start
					else if(type.getSelectedItem().toString().equals("Debit") && account.getSelectedItem().toString().equals("Expense"))
					{
						if(payment.getSelectedItem().toString().equals("Cash"))
						{
							
							amount.setText((amount.getText()));
							
							String query="insert into Voucher (Type,Account,Name,Date,Amount,Payment,Detail,Company) VALUES ('"+type.getSelectedItem().toString()+"','"+account.getSelectedItem().toString()+"','"+name.getSelectedItem().toString()+"','"+textField.getText()+"','"+amount.getText()+"','"+payment.getSelectedItem().toString()+"','"+textField_2.getText()+"','"+company+"')";
		                    PreparedStatement pmt = connection.prepareStatement(query);
		                    pmt.executeUpdate();
		                    pmt.close();  
		                }
						else
						{
							if(bank.getSelectedItem().toString().equals("SELECT"))
							{
								JOptionPane.showMessageDialog(null, "Please Fill up Details");
							}
							else
							{
								
								amount.setText((amount.getText()));
								
								
								String query="insert into Voucher (Type,Account,Name,Date,Amount,Payment,Cheque,Bank,Detail,Company) VALUES ('"+type.getSelectedItem().toString()+"','"+account.getSelectedItem().toString()+"','"+name.getSelectedItem().toString()+"','"+textField.getText()+"','"+amount.getText()+"','"+payment.getSelectedItem().toString()+"','"+cheque.getText()+"','"+bank.getSelectedItem().toString()+"','"+textField_2.getText()+"','"+company+"')";
			                    PreparedStatement pmt = connection.prepareStatement(query);
			                    pmt.executeUpdate();
			                    pmt.close();

			                    double t5 = 0;
			                    String query5 = "select * from Bank where Bank_name ='"+bank.getSelectedItem().toString()+"' and Company='"+company+"'";
			                    PreparedStatement pmt5 = connection.prepareStatement(query5);
			                    ResultSet rs5 = pmt5.executeQuery();
			                    while(rs5.next())
			                    {
			                         t5 = rs5.getFloat("Balance");
			                    }
			                    pmt5.close();
			                    rs5.close();
			                    double t6 = t5 - Double.parseDouble(amount.getText());

			                    String query6 = "UPDATE Bank set Balance ="+t6+" where Bank_name ='"+bank.getSelectedItem().toString()+"' and Company='"+company+"'";
			                    PreparedStatement pmt6 = connection.prepareStatement(query6);
			                    pmt6.executeUpdate();
			                    pmt6.close();
			                }
						}
					}//expense end

					//credit bank start
					else if(type.getSelectedItem().toString().equals("Credit") && account.getSelectedItem().toString().equals("Bank"))
					{
						
						amount.setText((amount.getText()));
						
						
						String query="insert into Voucher (Type,Account,Name,Date,Amount,Payment,Detail,Bank_name,Company) VALUES ('"+type.getSelectedItem().toString()+"','"+account.getSelectedItem().toString()+"','"+name.getSelectedItem().toString()+"','"+textField.getText()+"','"+amount.getText()+"','Cash','"+textField_2.getText()+"','-','"+company+"')";
	                    PreparedStatement pmt = connection.prepareStatement(query);
	                    pmt.executeUpdate();
	                    pmt.close();

	                    String name1=null,no=null,b_name=null;
	                    double t5 = 0;
	                    String query5 = "select * from Bank where Bank_name ='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
	                    PreparedStatement pmt5 = connection.prepareStatement(query5);
	                    ResultSet rs5 = pmt5.executeQuery();
	                    while(rs5.next())
	                    {
	                         t5 = rs5.getFloat("Balance");
	                         name1 = rs5.getString("Bank_name");
	                         no = rs5.getString("Account_number");
//	                         /b_name = rs5.getString("Branch_name");
	                    }
	                    pmt5.close();
	                    rs5.close();
	                    double t6 = t5 + Double.parseDouble(amount.getText());

	                    String query6 = "UPDATE Bank set Balance ="+t6+" where Bank_name ='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
	                    PreparedStatement pmt6 = connection.prepareStatement(query6);
	                    pmt6.executeUpdate();
	                    pmt6.close();
			        }//product end
					else
					{
						
						amount.setText((amount.getText()));
						
						
						String query="insert into Voucher (Type,Account,Name,Date,Amount,Payment,Detail,Company) VALUES ('"+type.getSelectedItem().toString()+"','"+account.getSelectedItem().toString()+"','"+name.getSelectedItem().toString()+"','"+textField.getText()+"','"+amount.getText()+"','Cash','"+textField_2.getText()+"','"+company+"')";
	                    PreparedStatement pmt = connection.prepareStatement(query);
	                    pmt.executeUpdate();
	                    pmt.close();

	                    double t5 = 0;
	                    String query5 = "select * from Bank where Bank_name ='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
	                    PreparedStatement pmt5 = connection.prepareStatement(query5);
	                    ResultSet rs5 = pmt5.executeQuery();
	                    while(rs5.next())
	                    {
	                         t5 = rs5.getFloat("Balance");
	                    }
	                    pmt5.close();
	                    rs5.close(); 

	                    double t6 = t5 - Double.parseDouble(amount.getText());

	                    String query6 = "UPDATE Bank set Balance ="+t6+" where Bank_name ='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
	                    PreparedStatement pmt6 = connection.prepareStatement(query6);
	                    pmt6.executeUpdate();
	                    pmt6.close();
	                }
				}
				catch (Exception ae) 
				{
				    ae.printStackTrace();
				}
				
				type.setSelectedIndex(0);
				account.setSelectedIndex(0);
				name.setSelectedIndex(0);
				amount.setText("");
				payment.setEnabled(true);
				bank.setEnabled(false);
				payment.setModel(new DefaultComboBoxModel(new String[] {"Cash","Cheque","Net Banking"}));
				cheque.setEnabled(false);
				cheque.setText("");
				textField_2.setText("");
				textField.setText("");
			}
		});
		btnSubmit.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnSubmit.setBounds(171, 438, 89, 25);
		panel.add(btnSubmit);
		
		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				try
				{
					String s5=date(textField.getText());

					//JOptionPane.showMessageDialog(null, s5);
					java.util.Date startDate = null;
					SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");

					try 
					{
						startDate = sm.parse(s5);
		           }
		           catch (ParseException ae) {
		               ae.printStackTrace();
		           }

					String strDate = sm.format(startDate);

		           //textField.requestFocus();
					textField.setText(strDate);
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
			public void focusGained(FocusEvent arg0) {
				
				if (empty(textField.getText())) {
					
				} 
				else 
				{
					try 
					{
			            SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd");
					    Date date2=formatter2.parse(textField.getText());  

			            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
			            String strDate = dateFormat.format(date2);  

			            textField.setText(strDate);
					} 
					catch (Exception ae) 
					{
						ae.printStackTrace();
						// TODO: handle exception
					}
					
				}
			}
		});
		textField.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(191, 155, 193, 25);
		panel.add(textField);

		//Account combobox start
		type.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					account.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
					name.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
					if(type.getSelectedItem().toString().equals("SELECT"))
					{
						payment.setModel(new DefaultComboBoxModel(new String[] {"Cash","Cheque","Net Banking"}));
						payment.setEnabled(true);
						cheque.setEnabled(false);
					}
					else if(type.getSelectedItem().toString().equals("Credit"))
					{
						payment.setModel(new DefaultComboBoxModel(new String[] {"Cash","Cheque","Net Banking"}));
						payment.setEnabled(true);
						cheque.setEnabled(false);
						account.addItem("Client");
						account.addItem("Bank");
					}
					else
					{
						payment.setModel(new DefaultComboBoxModel(new String[] {"Cash","Cheque","Net Banking"}));
						payment.setEnabled(true);
						cheque.setEnabled(false);
						account.addItem("Driver");
						account.addItem("Creditor");
						account.addItem("Bank");
						account.addItem("Expense");
					}
				}
				catch (Exception ae) 
				{
				    ae.printStackTrace();
				}
			}
		});
		//Account combobox end

		//Name combobox start
		account.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try
				{
					name.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));

					if(type.getSelectedItem().toString().equals("SELECT")||account.getSelectedItem().toString().equals("SELECT"))
					{
						payment.setModel(new DefaultComboBoxModel(new String[] {"Cash","Cheque","Net Banking"}));
						payment.setEnabled(true);
						cheque.setEnabled(false);
						
					}
				else if(account.getSelectedItem().toString().equals("Driver"))
				{
					payment.setModel(new DefaultComboBoxModel(new String[] {"Cash","Cheque","Net Banking"}));
					payment.setEnabled(true);
					cheque.setEnabled(false);

					String query1 = "select * from Driver where Company='"+company+"'";
				    PreparedStatement pmt1 = connection.prepareStatement(query1);
				    ResultSet rs = pmt1.executeQuery();
				    while(rs.next())
				    {
				    	name.addItem(rs.getString("Name"));
				    }
				    rs.close();
				    pmt1.close();
				}
				else if(account.getSelectedItem().toString().equals("Bank"))
				{
					payment.setModel(new DefaultComboBoxModel(new String[] {"Cash","Cheque","Net Banking"}));
					bank.setEnabled(false);
					payment.setEnabled(false);
					cheque.setEnabled(false);

					String query1 = "select * from Bank where Company='"+company+"'";
				    PreparedStatement pmt1 = connection.prepareStatement(query1);
				    ResultSet rs = pmt1.executeQuery();
				    while(rs.next())
				    {
				    	name.addItem(rs.getString("Bank_name"));
				    }
				    rs.close();
				    pmt1.close();
				}
				else if(account.getSelectedItem().toString().equals("Client"))
				{
					payment.setModel(new DefaultComboBoxModel(new String[] {"Cash","Cheque","Net Banking"}));
					payment.setEnabled(true);
					cheque.setEnabled(false);

					String query1 = "select * from Client where Company='"+company+"'";
				    PreparedStatement pmt1 = connection.prepareStatement(query1);
				    ResultSet rs = pmt1.executeQuery();
				    while(rs.next())
				    {
				    	name.addItem(rs.getString("Name"));
				    }
				    rs.close();
				    pmt1.close();
				}
				else if(account.getSelectedItem().toString().equals("Creditor"))
				{
					payment.setModel(new DefaultComboBoxModel(new String[] {"Cash","Cheque","Net Banking"}));
					payment.setEnabled(true);
					cheque.setEnabled(false);

					String query1 = "select * from Creditor where Company='"+company+"'";
				    PreparedStatement pmt1 = connection.prepareStatement(query1);
				    ResultSet rs = pmt1.executeQuery();
				    while(rs.next())
				    {
				    	name.addItem(rs.getString("Name"));
				    }
				    rs.close();
				    pmt1.close();
				}
				else
				{
					payment.setModel(new DefaultComboBoxModel(new String[] {"Cash","Cheque","Net Banking"}));
					payment.setEnabled(true);
					cheque.setEnabled(false);
					
					String query1 = "select * from Expense where Company='"+company+"'";
				    PreparedStatement pmt1 = connection.prepareStatement(query1);
				    ResultSet rs = pmt1.executeQuery();
				    while(rs.next())
				    {
				    	name.addItem(rs.getString("Name"));
				    }
				    rs.close();
				    pmt1.close();
				}
				}
				catch (Exception ae) 
				{
				    ae.printStackTrace();
				}
			}
		});

		//Name combobox end
		payment.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(payment.getSelectedItem().toString().equals("SELECT"))
				{
					bank.setEnabled(false);
					cheque.setEnabled(false);
					
				}
				else if(payment.getSelectedItem().toString().equals("Cheque")||payment.getSelectedItem().toString().equals("ECS")||payment.getSelectedItem().toString().equals("NEFT")||payment.getSelectedItem().toString().equals("RTG"))
				{	
					cheque.setEnabled(true);
					try
					{	
						bank.setEnabled(true);

						bank.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));

						String query1 = "select * from Bank where Company='"+company+"'";
					    PreparedStatement pmt1 = connection.prepareStatement(query1);
					    ResultSet rs = pmt1.executeQuery();
					    while(rs.next())
					    {
					    	bank.addItem(rs.getString("Bank_name"));
					    }
					    rs.close();
					    pmt1.close();
					}
					catch (Exception ae) {
					    ae.printStackTrace();
					}
				}
				else if(payment.getSelectedItem().toString().equals("Net Banking"))
				{	
					
					try
					{	cheque.setEnabled(false);
						bank.setEnabled(true);

						bank.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));

						String query1 = "select * from Bank where Company='"+company+"'";
					    PreparedStatement pmt1 = connection.prepareStatement(query1);
					    ResultSet rs = pmt1.executeQuery();
					    while(rs.next())
					    {
					    	bank.addItem(rs.getString("Bank_name"));
					    }
					    rs.close();
					    pmt1.close();
					}
					catch (Exception ae) {
					    ae.printStackTrace();
					}
				}
				else
				{
					bank.setEnabled(false);
					bank.setModel(new DefaultComboBoxModel(new String[] {"SELECT"}));
					cheque.setEnabled(false);
				}
			}
		});
	}
	
	public static String date( final String s) 
	{
		//int aInt = Integer.parseInt(year);
		//int aInt1 = Integer.parseInt(year1);

		//int aInt=Calendar.getInstance().get(Calendar.YEAR);

		ArrayList<String> comboBox_111 = new ArrayList<String>();

		String[] temp = null;
		if(s.contains("-"))
		{
			temp=s.split("-");
		}
		if(s.contains("/"))
		{
			temp=s.split("/");
		}
		if(s.contains("."))
		{
			temp=s.split("\\.");
		}
		if(s.contains("*"))
		{
			temp=s.split("\\*");
		}
		
        for(int j=0;j<temp.length;j++)
        {
        	comboBox_111.add(temp[j]);
        }

        String mill=null,pano=null,pano1=null;
        mill=temp[0];
        pano=temp[1];
        pano1=temp[2];

        if(pano1.length()==2)
        {
        	pano1="20"+pano1;
        }

        SimpleDateFormat dateFormat6 = new SimpleDateFormat("yyyy-MM-dd");
        int aInt2 = Integer.parseInt(mill);
		int aInt3 = Integer.parseInt(pano);
		int aInt4 = Integer.parseInt(pano1);
					
		Calendar cal6 = Calendar.getInstance();

		String sm =null;

		if(empty(Integer.toString(aInt2)))
		{
			JOptionPane.showMessageDialog(null, "Please Enter Day");
		}
		else if(empty(Integer.toString(aInt3)))
		{
			JOptionPane.showMessageDialog(null, "Please Enter month");
		}
		else if(empty(Integer.toString(aInt4)))
		{
			JOptionPane.showMessageDialog(null, "Please Enter Year");
		}
                    
		else if(aInt3==1 || aInt3==2 || aInt3==3||aInt3==4 || aInt3==5 || aInt3==6||aInt3==7 || aInt3==8 || aInt3==9||aInt3==10 || aInt3==11 || aInt3==12)
		{
			if(aInt3==1||aInt3==3||aInt3==5||aInt3==7||aInt3==8||aInt3==10||aInt3==12)
			{
				if(1<= aInt2 && aInt2<=31)
				{
						cal6.set(aInt4, (aInt3-1), aInt2);
    					sm = dateFormat6.format(cal6.getTime());
    					return sm;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Enter Correct Date");
				}
			}
			else
			{
				if(1<= aInt2 && aInt2<=30)
				{
						cal6.set(aInt4, (aInt3-1), aInt2);
    					sm = dateFormat6.format(cal6.getTime());
    					return sm;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Enter Correct Date");
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Fill correct Month");
		}
		return sm;
    }

	public static boolean empty( final String s ) 
	{
		return s == null || s.trim().isEmpty();
	}
}