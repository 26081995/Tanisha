import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.KeyStroke;

import com.mysql.jdbc.Statement;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;

public class Add_average extends JInternalFrame {
	private JTextField vehicle_no;
	private JTextField chadate;
	private JTextField from;
	private JTextField to;
	private JTextField ltr;
	private JTextField rs_per_ltr;
	private JTextField average;
	private JTextField total;
	String company=null,date_from=null,date_to=null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_average frame = new Add_average();
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

	public Add_average() {
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
	        setBounds((w-450)/2, (h-473)/2, 450, 473);
			getContentPane().setLayout(null);
			setClosable(true);
			
			DecimalFormat dc=new DecimalFormat("0.00");
			
			final Connection connection = Databaseconnection.connection2();
			
			try
			{
	        	String query = "select * from Company_temp";
			    PreparedStatement pmt =  connection.prepareStatement(query);
			    ResultSet rs = pmt.executeQuery();
			    while(rs.next())
			    {
			    	company=rs.getString("Name");
			    	date_from=rs.getString("Date_from");
			    	date_to=rs.getString("Date_to");
			    }
			    rs.close();
			}
	        catch (Exception ae) {
			    ae.printStackTrace();
			}
		
		JLabel lblAverage = new JLabel("Average");
		lblAverage.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblAverage.setBounds(154, 11, 126, 25);
		getContentPane().add(lblAverage);
		
		JLabel label = new JLabel("Driver Name:*");
		label.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label.setBounds(7, 64, 137, 25);
		getContentPane().add(label);
		
		JComboBox driver_name = new JComboBox();
		driver_name.addItem("SELECT");
		driver_name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		driver_name.setBounds(154, 64, 270, 25);
		getContentPane().add(driver_name);
		
		try
		{
        	String query = "select * from Driver where Company='"+company+"'";
		    PreparedStatement pmt =  connection.prepareStatement(query);
		    ResultSet rs = pmt.executeQuery();
		    while(rs.next())
		    {
		    	driver_name.addItem(rs.getString("Name"));
		    }
		    rs.close();
		}
        catch (Exception ae) {
		    ae.printStackTrace();
		}
		
		
		vehicle_no = new JTextField();
		vehicle_no.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		vehicle_no.setColumns(10);
		vehicle_no.setBounds(156, 100, 268, 25);
		getContentPane().add(vehicle_no);
		
		driver_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					vehicle_no.setText("");
					
					String query = "select * from Driver where Company='"+company+"' and Name='"+driver_name.getSelectedItem().toString()+"'";
				    PreparedStatement pmt =  connection.prepareStatement(query);
				    ResultSet rs = pmt.executeQuery();
				    while(rs.next())
				    {
				    	vehicle_no.setText(rs.getString("Vehicle_no"));
				    }
				    rs.close();
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
		});
		
		
		JLabel label_1 = new JLabel("Vehicle No.:*");
		label_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_1.setBounds(7, 102, 134, 25);
		getContentPane().add(label_1);
		
		JLabel lblDate = new JLabel("Date:*");
		lblDate.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblDate.setBounds(7, 137, 137, 25);
		getContentPane().add(lblDate);
		
		chadate = new JTextField();
		chadate.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		chadate.setColumns(10);
		chadate.setBounds(154, 136, 270, 25);
		getContentPane().add(chadate);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		// Get the date today using Calendar object.
		Date today99 = Calendar.getInstance().getTime();        
		String date99 = df.format(today99);
		chadate.setText(date99);
		
		chadate.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				try
				{
					String s5=date(chadate.getText());

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
		
		JLabel lblKmFrom = new JLabel("K.M. From:*");
		lblKmFrom.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblKmFrom.setBounds(7, 175, 134, 25);
		getContentPane().add(lblKmFrom);
		
		from = new JTextField();
		from.setText("0");
		from.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		from.setColumns(10);
		from.setBounds(156, 173, 268, 25);
		getContentPane().add(from);
		
		to = new JTextField();
		to.setText("0");
		to.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		to.setColumns(10);
		to.setBounds(156, 211, 268, 25);
		getContentPane().add(to);
		
		JLabel lblKmTo = new JLabel("K.M. To:*");
		lblKmTo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblKmTo.setBounds(7, 213, 134, 25);
		getContentPane().add(lblKmTo);
		
		JLabel lblLtr = new JLabel("LTR:*");
		lblLtr.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblLtr.setBounds(7, 249, 134, 25);
		getContentPane().add(lblLtr);
		
		ltr = new JTextField();
		ltr.setText("0");
		ltr.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		ltr.setColumns(10);
		ltr.setBounds(156, 247, 268, 25);
		getContentPane().add(ltr);
		
		rs_per_ltr = new JTextField();
		rs_per_ltr.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				double from99=0,to99=0,rs_per_ltr99=0,ltr99=0;
				
				if(empty(from.getText()))
				{
					
				}
				else
				{
					from99=Double.parseDouble(from.getText());
				}
				
				if(empty(to.getText()))
				{
					
				}
				else
				{
					to99=Double.parseDouble(to.getText());
				}
				
				if(empty(ltr.getText()))
				{
					
				}
				else
				{
					ltr99=Double.parseDouble(ltr.getText());	
				}
				
				if(empty(rs_per_ltr.getText()))
				{
					
				}
				else
				{
					rs_per_ltr99=Double.parseDouble(rs_per_ltr.getText());
				}
				
				
				double diff=to99-from99;
				double average99=diff/ltr99;
				double total99=ltr99*rs_per_ltr99;
				
				average.setText(Double.toString(average99));
				total.setText(Double.toString(total99));
				
			}
		});
		rs_per_ltr.setText("0");
		rs_per_ltr.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		rs_per_ltr.setColumns(10);
		rs_per_ltr.setBounds(156, 285, 268, 25);
		getContentPane().add(rs_per_ltr);
		
		
		
		ltr.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				double from99=0,to99=0,rs_per_ltr99=0,ltr99=0;
				
				if(empty(from.getText()))
				{
					
				}
				else
				{
					from99=Double.parseDouble(from.getText());
				}
				
				if(empty(to.getText()))
				{
					
				}
				else
				{
					to99=Double.parseDouble(to.getText());
				}
				
				if(empty(ltr.getText()))
				{
					
				}
				else
				{
					ltr99=Double.parseDouble(ltr.getText());	
				}
				
				if(empty(rs_per_ltr.getText()))
				{
					
				}
				else
				{
					rs_per_ltr99=Double.parseDouble(rs_per_ltr.getText());
				}
				
				
				double diff=to99-from99;
				double average99=diff/ltr99;
				double total99=ltr99*rs_per_ltr99;
				
				average.setText(Double.toString(average99));
				total.setText(Double.toString(total99));
				
			}
		});
		
		
		to.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				double from99=0,to99=0,rs_per_ltr99=0,ltr99=0;
				
				if(empty(from.getText()))
				{
					
				}
				else
				{
					from99=Double.parseDouble(from.getText());
				}
				
				if(empty(to.getText()))
				{
					
				}
				else
				{
					to99=Double.parseDouble(to.getText());
				}
				
				if(empty(ltr.getText()))
				{
					
				}
				else
				{
					ltr99=Double.parseDouble(ltr.getText());	
				}
				
				if(empty(rs_per_ltr.getText()))
				{
					
				}
				else
				{
					rs_per_ltr99=Double.parseDouble(rs_per_ltr.getText());
				}
				
				
				double diff=to99-from99;
				double average99=diff/ltr99;
				double total99=ltr99*rs_per_ltr99;
				
				average.setText(Double.toString(average99));
				total.setText(Double.toString(total99));
				
			}
		});
		
		
		from.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
				double from99=0,to99=0,rs_per_ltr99=0,ltr99=0;
				
				if(empty(from.getText()))
				{
					
				}
				else
				{
					from99=Double.parseDouble(from.getText());
				}
				
				if(empty(to.getText()))
				{
					
				}
				else
				{
					to99=Double.parseDouble(to.getText());
				}
				
				if(empty(ltr.getText()))
				{
					
				}
				else
				{
					ltr99=Double.parseDouble(ltr.getText());	
				}
				
				if(empty(rs_per_ltr.getText()))
				{
					
				}
				else
				{
					rs_per_ltr99=Double.parseDouble(rs_per_ltr.getText());
				}
				
				
				double diff=to99-from99;
				double average99=diff/ltr99;
				double total99=ltr99*rs_per_ltr99;
				
				average.setText(Double.toString(average99));
				total.setText(Double.toString(total99));
				
			}
		});
		
		
		JLabel lblRsPerLtr = new JLabel("Rs. Per LTR:*");
		lblRsPerLtr.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblRsPerLtr.setBounds(7, 287, 134, 25);
		getContentPane().add(lblRsPerLtr);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					if(driver_name.getSelectedItem().toString().equals("SELECT")||vehicle_no.getText().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please Fill Details");
					}
					else
					{
						String query12="insert into Average (Company,Name,Vehicle_no,Date,From99,To99,Ltr,Rs_per_ltr,Average,Total) VALUES ('"+company+"','"+driver_name.getSelectedItem().toString()+"','"+vehicle_no.getText()+"','"+chadate.getText()+"','"+from.getText()+"','"+to.getText()+"','"+ltr.getText()+"','"+rs_per_ltr.getText()+"','"+average.getText()+"','"+total.getText()+"')";
	                    PreparedStatement pmt12 = connection.prepareStatement(query12,Statement.RETURN_GENERATED_KEYS);
	                    pmt12.executeUpdate();
	                    pmt12.close();
	                    
	                    Add_average b = new Add_average();
		                JDesktopPane desktopPane = getDesktopPane();
		                desktopPane.add(b);
		                b.show();
		                dispose();
					}
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
		});
		button.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		button.setBounds(154, 407, 89, 25);
		getContentPane().add(button);
		
		JLabel lblAverage_1 = new JLabel("Average:*");
		lblAverage_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAverage_1.setBounds(7, 323, 134, 25);
		getContentPane().add(lblAverage_1);
		
		average = new JTextField();
		average.setEnabled(false);
		average.setText("0");
		average.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		average.setColumns(10);
		average.setBounds(156, 321, 268, 25);
		getContentPane().add(average);
		
		JLabel lblTotal = new JLabel("Total:*");
		lblTotal.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblTotal.setBounds(7, 361, 134, 25);
		getContentPane().add(lblTotal);
		
		total = new JTextField();
		total.setText("0");
		total.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		total.setEnabled(false);
		total.setColumns(10);
		total.setBounds(156, 359, 268, 25);
		getContentPane().add(total);

	}
}
