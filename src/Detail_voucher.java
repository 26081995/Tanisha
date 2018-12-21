import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

public class Detail_voucher extends JInternalFrame {
	public JTextField amount;
	public JTextField cheque;
	public JTextArea detail;
	String company=null,company1=null;
	public JTextField textField,bank,payment,name,account,type;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Detail_voucher frame = new Detail_voucher();
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
	public Detail_voucher() {
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
		setBounds((w-598)/2, (h-461)/2, 598, 461);
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
		panel.setBounds(0, 0, 572, 598);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel total11 = new JLabel("");
		total11.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		total11.setBounds(353, 277, 71, 25);
		panel.add(total11);

		JLabel lblVouchers = new JLabel("Detail Voucher");
		lblVouchers.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblVouchers.setBounds(227, 11, 174, 25);
		panel.add(lblVouchers);

		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblType.setBounds(58, 50, 119, 25);
		panel.add(lblType);

		 type = new JTextField();
		type.setEditable(false);
		type.setForeground(Color.RED);
		type.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		type.setBounds(191, 50, 371, 25);
		panel.add(type);

		JLabel lblAccount = new JLabel("Account");
		lblAccount.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAccount.setBounds(58, 85, 119, 25);
		panel.add(lblAccount);

		 account = new JTextField();
		account.setEditable(false);
		account.setForeground(Color.RED);
		account.setEnabled(true);
		account.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		account.setBounds(191, 85, 371, 25);
		panel.add(account);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblName.setBounds(58, 120, 119, 25);
		panel.add(lblName);

		 name = new JTextField();
		name.setForeground(Color.RED);
		name.setEditable(false);
		name.setEnabled(true);
		name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		name.setBounds(191, 120, 371, 25);
		panel.add(name);

		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblDate.setBounds(58, 155, 113, 25);
		panel.add(lblDate);
		Date date=new Date();

		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAmount.setBounds(58, 190, 123, 25);
		panel.add(lblAmount);

		amount = new JTextField();
		amount.setHorizontalAlignment(SwingConstants.RIGHT);
		amount.setForeground(Color.RED);
		amount.setEditable(false);
		amount.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				char c = e.getKeyChar();
                if(!(Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) || (c=='.'))){
                    getToolkit().beep();
                    e.consume();
                }
			}
		});
		amount.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		amount.setBounds(191, 190, 371, 25);
		panel.add(amount);
		amount.setColumns(10);

		JLabel lblPaymentType = new JLabel("Payment Type");
		lblPaymentType.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblPaymentType.setBounds(58, 225, 123, 25);
		panel.add(lblPaymentType);

		 payment = new JTextField();
		payment.setForeground(Color.RED);
		payment.setEditable(false);
		payment.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		payment.setEnabled(true);
		payment.setBounds(191, 225, 371, 25);
		panel.add(payment);

		JLabel lblChequeNumber = new JLabel("Cheque No.");
		lblChequeNumber.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblChequeNumber.setBounds(58, 260, 113, 25);
		panel.add(lblChequeNumber);

		cheque = new JTextField();
		cheque.setForeground(Color.RED);
		cheque.setEditable(false);
		cheque.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		cheque.setColumns(10);
		cheque.setBounds(191, 260, 371, 25);
		panel.add(cheque);

		JLabel lblDetails = new JLabel("Details");
		lblDetails.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblDetails.setBounds(58, 364, 119, 25);
		panel.add(lblDetails);

		detail = new JTextArea();
		detail.setLineWrap(true);
		detail.setWrapStyleWord(true);
		detail.setEditable(false);
		detail.setForeground(Color.RED);
		detail.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		detail.setColumns(10);
		detail.setBounds(191, 331, 371, 96);
		panel.add(detail);

		JLabel lblBankName = new JLabel("Bank Name");
		lblBankName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblBankName.setBounds(58, 295, 119, 25);
		panel.add(lblBankName);

		 bank = new JTextField();
		bank.setEditable(false);
		bank.setForeground(Color.RED);
		bank.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		bank.setBounds(191, 295, 371, 25);
		panel.add(bank);
		
		textField = new JTextField();
		textField.setForeground(Color.RED);
		textField.setEditable(false);
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
		           
		           //SimpleDateFormat sm2 = new SimpleDateFormat("dd-MM-yyyy");
		           String strDate = sm.format(startDate);
		           
		           

		           //textField.requestFocus();
		           textField.setText(strDate);
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
		});
		textField.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(191, 155, 371, 25);
		panel.add(textField);
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