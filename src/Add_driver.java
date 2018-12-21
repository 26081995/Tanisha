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
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class Add_driver extends JInternalFrame {
	private JTextField name;
	private JTextField address1;
	private JTextField address2;
	private JTextField mobile;
	String company=null;
	private JTextField mobile1;
	private JTextField salary;
	private JTextField address3;
	private JTextField vehicle_no;
	
	
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_driver frame = new Add_driver();
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
	public Add_driver() {
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
	        setBounds((w-600)/2, (h-447)/2, 600, 447);
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
			
			JLabel lblGstNo = new JLabel("Vehicle No:");
			lblGstNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
			lblGstNo.setBounds(22, 300, 108, 25);
			getContentPane().add(lblGstNo);
			
			vehicle_no = new JTextField();
			vehicle_no.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
			vehicle_no.setColumns(10);
			vehicle_no.setBounds(147, 300, 427, 25);
			getContentPane().add(vehicle_no);
			
			
			JLabel lblAddress_2 = new JLabel("Address 3:");
			lblAddress_2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
			lblAddress_2.setBounds(22, 157, 108, 25);
			getContentPane().add(lblAddress_2);
			
			address3 = new JTextField();
			address3.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
			address3.setColumns(10);
			address3.setBounds(147, 157, 427, 25);
			getContentPane().add(address3);
			
			JLabel lblAddClient = new JLabel("Add Driver");
			lblAddClient.setBounds(254, 0, 161, 36);
			lblAddClient.setFont(new Font("Book Antiqua", Font.BOLD, 25));
			getContentPane().add(lblAddClient);
			
			JLabel lblName = new JLabel("Name:*");
			lblName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
			lblName.setBounds(22, 47, 128, 25);
			getContentPane().add(lblName);
			
			name = new JTextField();
			name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
			name.setColumns(10);
			name.setBounds(147, 47, 427, 25);
			getContentPane().add(name);
		
		JLabel lblMobile_1 = new JLabel("Mobile 2:");
		lblMobile_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblMobile_1.setBounds(321, 264, 108, 25);
		getContentPane().add(lblMobile_1);
		
		mobile1 = new JTextField();
		mobile1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		mobile1.setColumns(10);
		mobile1.setBounds(424, 263, 150, 25);
		getContentPane().add(mobile1);
		
		JLabel lblAddress = new JLabel("Address 1:");
		lblAddress.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress.setBounds(22, 82, 108, 25);
		getContentPane().add(lblAddress);
		
		address1 = new JTextField();
		address1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address1.setColumns(10);
		address1.setBounds(147, 82, 427, 25);
		getContentPane().add(address1);
		
		JLabel lblAddress_1 = new JLabel("Address 2:");
		lblAddress_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress_1.setBounds(22, 117, 108, 25);
		getContentPane().add(lblAddress_1);
		
		address2 = new JTextField();
		address2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address2.setColumns(10);
		address2.setBounds(147, 117, 427, 25);
		getContentPane().add(address2);
		
		JLabel label_3 = new JLabel("State:*");
		label_3.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_3.setBounds(22, 193, 79, 25);
		getContentPane().add(label_3);
		
		JComboBox state = new JComboBox();
		state.addItem("SELECT");
		state.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		state.setBounds(149, 193, 425, 25);
		getContentPane().add(state);
		
		JLabel label_4 = new JLabel("City:*");
		label_4.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_4.setBounds(22, 228, 53, 25);
		getContentPane().add(label_4);
		
		JComboBox city = new JComboBox();
		city.addItem("SELECT");
		city.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		city.setBounds(149, 228, 425, 25);
		getContentPane().add(city);
		
		JLabel lblMobile = new JLabel("Mobile 1:");
		lblMobile.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblMobile.setBounds(22, 263, 108, 25);
		getContentPane().add(lblMobile);
		
		mobile = new JTextField();
		mobile.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		mobile.setColumns(10);
		mobile.setBounds(149, 263, 150, 25);
		getContentPane().add(mobile);
		
		JLabel lblSalary = new JLabel("Salary:*");
		lblSalary.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblSalary.setBounds(22, 337, 108, 25);
		getContentPane().add(lblSalary);
		
		salary = new JTextField();
		salary.setText("0");
		salary.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		salary.setColumns(10);
		salary.setBounds(147, 337, 427, 25);
		getContentPane().add(salary);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					String text = mobile.getText();
		            int counter = text.length();
		            if(counter==0)
		            {
		            	counter=10;
		            }
		            if(name.getText().equals("")||salary.getText().equals("")   || city.getSelectedItem().toString().equals("SELECT"))
    				{
    					JOptionPane.showMessageDialog(null, "please enter details");
    				}
					else
					{
						String query="insert into Driver(Company,Name,Address1,Address2,City,State,Mobile,Mobile1,Salary,Address3,Vehicle_no) Values ('"+company+"','"+name.getText()+"','"+address1.getText()+"','"+address2.getText()+"','"+city.getSelectedItem().toString()+"','"+state.getSelectedItem().toString()+"','"+mobile.getText()+"','"+mobile1.getText()+"','"+salary.getText()+"','"+address3.getText()+"','"+vehicle_no.getText()+"')";
			            PreparedStatement pmt = connection.prepareStatement(query);
			            pmt.executeUpdate();
			            pmt.close();
			            
			            Add_driver b = new Add_driver();
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
		btnSubmit.setBounds(239, 377, 89, 26);
		getContentPane().add(btnSubmit);
		
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
		
		
		
		
		
		salary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					String text = mobile.getText();
		            int counter = text.length();
		            if(counter==0)
		            {
		            	counter=10;
		            }
		            if(name.getText().equals("")||salary.getText().equals("")   || city.getSelectedItem().toString().equals("SELECT"))
    				{
    					JOptionPane.showMessageDialog(null, "please enter details");
    				}
					else
					{
						String query="insert into Driver(Company,Name,Address1,Address2,City,State,Mobile,Mobile1,Salary,Address3,Vehicle_no) Values ('"+company+"','"+name.getText()+"','"+address1.getText()+"','"+address2.getText()+"','"+city.getSelectedItem().toString()+"','"+state.getSelectedItem().toString()+"','"+mobile.getText()+"','"+mobile1.getText()+"','"+salary.getText()+"','"+address3.getText()+"','"+vehicle_no.getText()+"')";
			            PreparedStatement pmt = connection.prepareStatement(query);
			            pmt.executeUpdate();
			            pmt.close();
			            
			            Add_driver b = new Add_driver();
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
		
		
	}
	public static boolean empty( final String s ) 
	{
		return s == null || s.trim().isEmpty();
	}
}