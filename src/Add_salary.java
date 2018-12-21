import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import com.toedter.components.JLocaleChooser;
import com.toedter.components.JSpinField;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
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
import java.util.Calendar;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import com.sun.deploy.net.URLEncoder;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.awt.event.ActionEvent;

public class Add_salary extends JInternalFrame {
	String company=null,company1=null,mobile=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_salary frame = new Add_salary();
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
	public Add_salary() {
		
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
		
		setBounds((w-450)/2, (h-365)/2, 450, 365);
		setClosable(true);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 412);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblAddSalary = new JLabel("Add Salary");
		lblAddSalary.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblAddSalary.setBounds(145, 10, 144, 25);
		panel.add(lblAddSalary);
		
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
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblName.setBounds(58, 50, 74, 25);
		panel.add(lblName);
		
		
		JComboBox name = new JComboBox();
		name.addItem("SELECT");
		name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		name.setBounds(145, 50, 229, 25);
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

		JMonthChooser monthChooser = new JMonthChooser();
		monthChooser.setMonth(0);
		monthChooser.setToolTipText("SELECT");
		monthChooser.setBounds(132, 85, 111, 25);
		panel.add(monthChooser);

		JLabel lblMonth = new JLabel("Month:");
		lblMonth.setToolTipText("SELECT");
		lblMonth.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblMonth.setBounds(58, 85, 64, 25);
		panel.add(lblMonth);
		
		JLabel lblYear = new JLabel("Year:");
		lblYear.setToolTipText("");
		lblYear.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblYear.setBounds(263, 85, 38, 25);
		panel.add(lblYear);
		
		JYearChooser yearChooser = new JYearChooser();
		yearChooser.setBounds(323, 85, 47, 25);
		panel.add(yearChooser);
		
		JLabel lblTotalDays = new JLabel("Total Days :");
		lblTotalDays.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblTotalDays.setBounds(58, 155, 111, 25);
		panel.add(lblTotalDays);
		
		JLabel lblAtemptDays = new JLabel("Atempt Days :");
		lblAtemptDays.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAtemptDays.setBounds(58, 190, 125, 25);
		panel.add(lblAtemptDays);
		
		JLabel lblLeaveDays = new JLabel("Leave Days :");
		lblLeaveDays.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblLeaveDays.setBounds(58, 225, 115, 25);
		panel.add(lblLeaveDays);
		
		JLabel lblSalary = new JLabel("Salary:");
		lblSalary.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblSalary.setBounds(58, 260, 49, 25);
		panel.add(lblSalary);
		
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblNewLabel.setBounds(222, 155, 152, 25);
		panel.add(lblNewLabel);
		
		JLabel label = new JLabel("");
		label.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label.setBounds(222, 190, 152, 25);
		panel.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_1.setBounds(222, 225, 152, 25);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_2.setBounds(222, 260, 152, 25);
		panel.add(label_2);

		
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = yearChooser.getYear();
				int month = monthChooser.getMonth();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				c.set(year, month, 1); // Specify day of month
				String date_from = dateFormat.format(c.getTime());
				
				Calendar cal = Calendar.getInstance();
				DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
				Date d = null;
				cal.set(year, month, 1);
		        cal.add(Calendar.MONTH, 1);
		        cal.add(Calendar.DAY_OF_MONTH, -1);
		        
		        String date_to = dateFormat1.format(cal.getTime());
		        
		        
		        		        
		        DateFormat dateFormat5 = new SimpleDateFormat("yyyy-MM-dd");
				Date h = null;
				try
				{
					h = (Date) dateFormat5.parse(date_from);
					
				}
				catch (ParseException e2) 
				{
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
		        
				
				Calendar cal6 = Calendar.getInstance();
				DateFormat dateFormat6 = new SimpleDateFormat("MMMM");
				cal6.set(year, month, 1);
		        
				
		        String date_to1 = dateFormat6.format(cal6.getTime());
		        
		        
		        
				try
				{
					int i=0;
					String query11="select * from Salary where Name='"+name.getSelectedItem().toString()+"' and Year='"+year+"' and Month='"+date_to1+"' and Company='"+company+"'";
	                PreparedStatement pmt11 = connection.prepareStatement(query11);
	                ResultSet rs11 = pmt11.executeQuery();
	                while(rs11.next())
	                {
	                	//from.add(rs11.getDate("Date_from"));
	                	//to.add(rs11.getDate("Date_to"));
	                    i++;
	                }
	                
	                rs11.close();
	                pmt11.close();
	                
				
	            double m=0;
				if(name.getSelectedItem().toString().equals("SELECT"))
                {
                    JOptionPane.showMessageDialog(null,"Please Fillup Details.");
                }
				else if(i>0)
				{
					JOptionPane.showMessageDialog(null, "Salary Already Generated");
				}
				else 
				{
					String joining="1995-08-26";
							
					String query12="select * from Driver where Name='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
	                PreparedStatement pmt12 = connection.prepareStatement(query12);
	                ResultSet rs12 = pmt12.executeQuery();
	                while(rs12.next())
	                {
	                	m=(rs12.getDouble("Salary"));
	                	//company1=rs12.getString("Message");
	                	mobile=rs12.getString("Mobile");
	                	//joining=rs12.getString("Joining_date");
	                }
	                rs11.close();
	                pmt11.close();
	                
	                Calendar cal61 = Calendar.getInstance();
                    Calendar cal7 = Calendar.getInstance();
    				DateFormat dateFormat61 = new SimpleDateFormat("MMMM");
    				DateFormat dateFormat7 = new SimpleDateFormat("YYYY");
    				cal61.set(year, month, 1);
    				cal7.set(year, month, 1);
    				
    				String sm = dateFormat61.format(cal61.getTime());
    		        String sy = dateFormat7.format(cal7.getTime());
    		        
    		        java.util.Date startDate = null;
					java.util.Date startDate1 = null;
                    //String newDateString=null,newDateString1=null;
                    try {
                    	SimpleDateFormat sm12 = new SimpleDateFormat("yyyy-MM-dd");
                        startDate =  sm12.parse(joining);
                        //startDate1 =sm.parse(leavi);
                    } catch (ParseException ae) {
                        ae.printStackTrace();
                    }
                    
                    
                    String jm = dateFormat61.format(startDate);
    		        String jy = dateFormat7.format(startDate);
    		        
    		        if(jm.equals(sm) && jy.equals(sy))
    		        {
    		        	year = yearChooser.getYear();
       				 	month = monthChooser.getMonth();
                		date_from=joining;
       				
       				 	Calendar cal1 = Calendar.getInstance();
       				 	DateFormat dateFormat11 = new SimpleDateFormat("yyyy-MM-dd");
       				 	cal1.set(year, month, 1);
       				 	cal1.add(Calendar.MONTH, 1);
       				 	cal1.add(Calendar.DAY_OF_MONTH, -1);
       		        
       				 	date_to = dateFormat11.format(cal1.getTime());
    		        }
    		        else
    		        {
    		        	year = yearChooser.getYear();
       				 	month = monthChooser.getMonth();
       				 	SimpleDateFormat dateFormat9 = new SimpleDateFormat("yyyy-MM-dd");
       				 	Calendar c1 = Calendar.getInstance();
       				 	c1.set(year, month, 1); // Specify day of month
       				 	date_from = dateFormat9.format(c1.getTime());
       				 	
       				
       				 	Calendar cal1 = Calendar.getInstance();
       				 	
       				 	cal1.set(year, month, 1);
       				 	cal1.add(Calendar.MONTH, 1);
       				 	cal1.add(Calendar.DAY_OF_MONTH, -1);
       		        
       				 	date_to = dateFormat9.format(cal1.getTime());
    		        }

	                DateFormat dateFormat2= new SimpleDateFormat("yyyy-MM-dd");
	                
	                year = yearChooser.getYear();
   				 	month = monthChooser.getMonth();
   				 	SimpleDateFormat dateFormat9 = new SimpleDateFormat("yyyy-MM-dd");
   				 	Calendar c9 = Calendar.getInstance();
   				 	c9.set(year, month, 1); // Specify day of month
   				 	String date_from2 = dateFormat9.format(c9.getTime());
   				
   				 	Calendar cal9 = Calendar.getInstance();
   				 	cal9.set(year, month, 1);
   				 	cal9.add(Calendar.MONTH, 1);
   				 	cal9.add(Calendar.DAY_OF_MONTH, -1);
   		        
   				 	String	date_to2 = dateFormat9.format(cal9.getTime());
   				 	
					Date z = null,x=null,z1=null,x1=null;;
					try
					{
						z1 = (Date) dateFormat2.parse(date_from2);
						x1 = (Date) dateFormat2.parse(date_to2);
						z = (Date) dateFormat2.parse(date_from);
						x = (Date) dateFormat2.parse(date_to);
					}
					catch (ParseException e2) 
					{
						e2.printStackTrace();
					}
			        
					long diff1=x1.getTime()-z1.getTime();
			        double noofday1=(int)(diff1/(1000*24*60*60));
			        double noofdays1=noofday1+1;
			        double perday=m/noofdays1;
			        
			        long diff=x.getTime()-z.getTime();
			        double noofday=(int)(diff/(1000*24*60*60));
			        double noofdays=noofday+1;

			        String query13="select count(ID) as m from Leave where Name='"+name.getSelectedItem().toString()+"' and ('"+date_from+"'<= Date and'"+date_to+"'>= Date) and Company='"+company+"'";
	                PreparedStatement pmt13 = connection.prepareStatement(query13);
	                ResultSet rs13 = pmt13.executeQuery();
	                while(rs13.next())
	                {
	                	label_1.setText(rs13.getString("m"));
	                }
	                rs13.close();
	                pmt13.close();
	                
	                double t=0, t_leave=0,total=0,t_day=0;
	                
	                t_leave=Double.parseDouble(label_1.getText());
	                
	               t=noofdays-t_leave;//attempeted days
	               
	               total=(t*perday);
	               
	               int i1 = (int) total;
	               
	               //label_2.setText(Double.toString(total));
	               label_2.setText(Integer.toString(i1));
	               label.setText(Double.toString(t));
	               lblNewLabel.setText(Double.toString(noofdays));
	            }
			}
	        catch (SQLException ae) {
			    ae.printStackTrace();
			}
			}
		});
		btnAdd.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnAdd.setBounds(179, 120, 89, 25);
		panel.add(btnAdd);
		
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
				{
					if(name.getSelectedItem().toString().equals("SELECT"))
                    {
                        JOptionPane.showMessageDialog(null,"Please Fillup Details.");
                    }
					else
					{
						int year = yearChooser.getYear();
						int month = monthChooser.getMonth();

						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Calendar c = Calendar.getInstance();
						c.set(year, month, 1); // Specify day of month
						String date_from = dateFormat.format(c.getTime());

						Calendar cal = Calendar.getInstance();
						DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
						Date d = null;

						cal.set(year, month, 1);
				        cal.add(Calendar.MONTH, 1);
				        cal.add(Calendar.DAY_OF_MONTH, -1);

				        String date_to = dateFormat1.format(cal.getTime());

				        DateFormat dateFormat5 = new SimpleDateFormat("yyyy-MM-dd");
						Date h = null;
						try
						{
							h = (Date) dateFormat5.parse(date_from);
						}
						catch (ParseException e2) 
						{
							e2.printStackTrace();
						}

						Calendar cal6 = Calendar.getInstance();
						DateFormat dateFormat6 = new SimpleDateFormat("MMMM");
						cal6.set(year, month, 1);

						String date_to1 = dateFormat6.format(cal6.getTime());

						double t2=0;
						String query14="insert into Salary (Company,Name,Month,Year,Salary,Leave_day) VALUES ('"+company+"','"+name.getSelectedItem().toString()+"','"+date_to1+"','"+year+"','"+label_2.getText()+"','"+label_1.getText()+"')";
	                    PreparedStatement pmt14 = connection.prepareStatement(query14);
	                    pmt14.executeUpdate();
	                    pmt14.close();

	                    String query6 = "UPDATE Driver set Balance =Balance+'"+label_2.getText()+"' where Name ='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
	                    PreparedStatement pmt6 = connection.prepareStatement(query6);
	                    pmt6.executeUpdate();
	                    pmt6.close();
	                    
	                    /*if(company1.equals("Yes"))
	                    {
	                    	String msg=null;
							try
							{
								msg = URLEncoder.encode("Mr."+name.getSelectedItem().toString()+" Your Salary of "+date_to1+"-"+year+" is Generated Of amount "+label_2.getText()+"","UTF-8");
							}
							catch (UnsupportedEncodingException e1) 
							{
								e1.printStackTrace();
							}
			                  //String url_str = "http://msg.supersoftsolutions.com/API/WebSMS/Http/v1.0a/index.php?username=rcad&password=rcad123&sender=Octans&to="+mobile+"&message="+msg+"&reqid=1&format={json|text}&route_id=185";
								//String url_str = "http://msg.supersoftsolutions.com/API/WebSMS/Http/v1.0a/index.php?username=radhe1&password=radhe&sender=RadheI&to="+mobile+"&message="+msg+"&reqid=1&format={json|text}&route_id=185";
							String url_str = "ssd";
			                  try{
			                      URL url2=new URL(url_str); 

			                      HttpURLConnection connection1 = (HttpURLConnection) url2.openConnection(); 
			                      connection1.setDoOutput(false); 
			                      connection1.setDoInput(true); 

			                    String res=connection1.getResponseMessage(); 

			                      int code = connection1.getResponseCode() ; 

			                      if ( code == HttpURLConnection.HTTP_OK ) 
			                      { 
			                    	  connection1.disconnect() ;
			                      }
			                  }
			                  catch(Exception ae){
			                      JOptionPane.showMessageDialog(null, "Unable to send SMS");
			                  }
	                    }*/
	                    name.setSelectedIndex(0);
	                    label.setText("");
	                    label_1.setText("");
	                    label_2.setText("");
	                    lblNewLabel.setText("");
					}
				}
				catch (SQLException ae) {
				    ae.printStackTrace();
				}
			}
		});
		btnSubmit.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnSubmit.setBounds(179, 300, 89, 25);
		panel.add(btnSubmit);
		
		
	}
}