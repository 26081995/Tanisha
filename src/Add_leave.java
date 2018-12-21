import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JTextArea;

import com.orsoncharts.util.json.parser.ParseException;
import com.toedter.calendar.JDateChooser;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;

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
import java.awt.event.ActionEvent;

public class Add_leave extends JInternalFrame {
	String company=null;
	private JTextField chadate;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_leave frame = new Add_leave();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
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
	/**
	 * Create the frame.
	 */
	public Add_leave() {
		
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
		
		setBounds((w-450)/2, (h-206)/2, 450, 206);
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
		panel.setBounds(0, 0, 434, 271);
		getContentPane().add(panel);
		
		JLabel lblAddLeave = new JLabel("Add Leave");
		lblAddLeave.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblAddLeave.setBounds(141, 10, 153, 24);
		panel.add(lblAddLeave);
		
		
		JLabel label_1 = new JLabel("Name:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_1.setBounds(32, 50, 66, 25);
		panel.add(label_1);
		
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
		

		
		JLabel lblDateOfLeave = new JLabel("Date Of Leave:");
		lblDateOfLeave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDateOfLeave.setBounds(32, 85, 116, 25);
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
		chadate.setBounds(186, 85, 199, 25);
		panel.add(chadate);
		chadate.setColumns(10);
		
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					
                    if(name.getSelectedItem().toString().equals("SELECT") || chadate.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    else 
                    {
                        String query="insert into Leave (Company,Name,Date) VALUES ('"+company+"','"+name.getSelectedItem().toString()+"','"+chadate.getText()+"')";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                        
        				name.setSelectedIndex(0);
        				chadate.setText("");
        				name.requestDefaultFocus();
                    }
                }
               catch (SQLException ae) {
                   ae.printStackTrace();
               }
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 16));
		button.setBounds(154, 125, 89, 23);
		panel.add(button);
		
		
		
		
		
	}
}
