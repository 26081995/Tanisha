import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class Add_driver_expense extends JInternalFrame {
	String company=null;
	private JTextField chadate;
	private JTextField vehicle_no;
	private JTextField amount;
	
	
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_driver_expense frame = new Add_driver_expense();
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
	public Add_driver_expense() {
		
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
		
		setBounds((w-450)/2, (h-326)/2, 450, 326);
		setClosable(true);
		getContentPane().setLayout(null);
		
		
		final Connection connection = Databaseconnection.connection2();
		
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
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 434, 285);
		getContentPane().add(panel);
		
		JLabel lblAddLeave = new JLabel("Add Driver Expense");
		lblAddLeave.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblAddLeave.setBounds(88, 10, 268, 24);
		panel.add(lblAddLeave);
		
		
		JLabel lblName = new JLabel("Name:*");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName.setBounds(32, 50, 66, 25);
		panel.add(lblName);
		
		JComboBox name = new JComboBox();
		name.addItem("SELECT");
		name.setFont(new Font("Tahoma", Font.PLAIN, 16));
		name.setBounds(186, 50, 199, 25);
		panel.add(name);
		
		try
		{
			String query0 = "select * from Driver where Company='"+company+"'";
            PreparedStatement pmt0 = connection.prepareStatement(query0);
            ResultSet rs0 = pmt0.executeQuery();
            while(rs0.next())
            {
           	 	name.addItem(rs0.getString("Name"));
           	}
            pmt0.close();
            rs0.close();
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
		

		
		vehicle_no = new JTextField();
		vehicle_no.setColumns(10);
		vehicle_no.setBounds(186, 86, 199, 25);
		panel.add(vehicle_no);
		
		name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				vehicle_no.setText("");
				
				try
				{
					String query0 = "select * from Driver where Company='"+company+"' and Name='"+name.getSelectedItem().toString()+"'";
		            PreparedStatement pmt0 = connection.prepareStatement(query0);
		            ResultSet rs0 = pmt0.executeQuery();
		            while(rs0.next())
		            {
		           	 	vehicle_no.setText(rs0.getString("Vehicle_no"));
		           	}
		            pmt0.close();
		            rs0.close();
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
				
			}
		});
		
		
		JLabel lblVehicleNo = new JLabel("Vehicle No.:*");
		lblVehicleNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblVehicleNo.setBounds(32, 86, 116, 25);
		panel.add(lblVehicleNo);
		
		JLabel lblExpense = new JLabel("Expense:*");
		lblExpense.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblExpense.setBounds(32, 160, 81, 25);
		panel.add(lblExpense);
		
		JComboBox expense = new JComboBox();
		expense.setFont(new Font("Tahoma", Font.PLAIN, 16));
		expense.addItem("SELECT");
		expense.setFont(new Font("Tahoma", Font.PLAIN, 16));
		expense.setBounds(186, 160, 199, 25);
		panel.add(expense);
		
		try
		{
			String query0 = "select * from Expense where Company='"+company+"'";
            PreparedStatement pmt0 = connection.prepareStatement(query0);
            ResultSet rs0 = pmt0.executeQuery();
            while(rs0.next())
            {
           	 	expense.addItem(rs0.getString("Name"));
           	}
            pmt0.close();
            rs0.close();
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
		
		JLabel lblAmount = new JLabel("Amount:*");
		lblAmount.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAmount.setBounds(32, 199, 116, 25);
		panel.add(lblAmount);
		
		amount = new JTextField();
		amount.setColumns(10);
		amount.setBounds(186, 199, 199, 25);
		panel.add(amount);
		
		JLabel lblDateOfLeave = new JLabel("Date:*");
		lblDateOfLeave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDateOfLeave.setBounds(32, 122, 116, 25);
		panel.add(lblDateOfLeave);
		
		chadate = new JTextField();
		chadate.addFocusListener(new FocusAdapter() {


			@Override
			public void focusLost(FocusEvent arg0) {
				try
				{
					String s5=date(chadate.getText());

					//JOptionPane.showMessageDialog(null, s5);
					java.util.Date startDate = null;
					SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");

					startDate = sm.parse(s5);

					String strDate = sm.format(startDate);

		           //textField.requestFocus();
					chadate.setText(strDate);
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
			public void focusGained(FocusEvent arg0) {
				
				if (empty(chadate.getText())) {
					
				} 
				else 
				{
					try 
					{
			            SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd");
					    Date date2=formatter2.parse(chadate.getText());  

			            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
			            String strDate = dateFormat.format(date2);  

			            chadate.setText(strDate);
					} 
					catch (Exception ae) 
					{
						ae.printStackTrace();
						// TODO: handle exception
					}
					
				}
			}
		
		
		});
		chadate.setBounds(186, 122, 199, 25);
		panel.add(chadate);
		chadate.setColumns(10);
		
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					
                    if(name.getSelectedItem().toString().equals("SELECT") || chadate.getText().equals("")||vehicle_no.getText().equals("")||expense.getSelectedItem().toString().equals("SELECT")||amount.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    else 
                    {
                        String query="insert into Driver_expense (Company,Name,Vehicle_no,Date,Expense,Amount) VALUES ('"+company+"','"+name.getSelectedItem().toString()+"','"+vehicle_no.getText()+"','"+chadate.getText()+"','"+expense.getSelectedItem().toString()+"','"+amount.getText()+"')";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                        
        				name.setSelectedIndex(0);
        				chadate.setText("");
        				vehicle_no.setText("");
        				expense.setSelectedIndex(0);
        				amount.setText("");
        				name.requestDefaultFocus();
                    }
                }
               catch (SQLException ae) {
                   ae.printStackTrace();
               }
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBounds(170, 251, 89, 23);
		panel.add(button);
		
		
		amount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					
                    if(name.getSelectedItem().toString().equals("SELECT") || chadate.getText().equals("")||vehicle_no.getText().equals("")||expense.getSelectedItem().toString().equals("SELECT")||amount.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    else 
                    {
                        String query="insert into Driver_expense (Company,Name,Vehicle_no,Date,Expense,Amount) VALUES ('"+company+"','"+name.getSelectedItem().toString()+"','"+vehicle_no.getText()+"','"+chadate.getText()+"','"+expense.getSelectedItem().toString()+"','"+amount.getText()+"')";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                        
        				name.setSelectedIndex(0);
        				chadate.setText("");
        				vehicle_no.setText("");
        				expense.setSelectedIndex(0);
        				amount.setText("");
        				name.requestDefaultFocus();
                    }
                }
               catch (SQLException ae) {
                   ae.printStackTrace();
               }
			}
		});
	}
}
