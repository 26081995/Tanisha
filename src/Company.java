import java.awt.BorderLayout;
import java.awt.Color;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JYearChooser;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Company extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	int id=0;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{
					Company frame = new Company();
					frame.setVisible(true);
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	public Company() {
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds((w-450)/2, (h-300)/2, 450, 331);
	
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 424, 250);
		contentPane.add(panel);
		panel.setLayout(null);
		
		final Connection connection = Databaseconnection.connection2();
		JLabel lblLogin = new JLabel("Company");
		lblLogin.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblLogin.setBounds(133, 11, 161, 33);
		panel.add(lblLogin);
		
		JLabel lblCompanyName = new JLabel("Company Name:");
		lblCompanyName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblCompanyName.setBounds(10, 77, 130, 14);
		panel.add(lblCompanyName);
		
		JLabel label = new JLabel("Accouting Year");
		label.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label.setBounds(10, 117, 113, 14);
		panel.add(label);
		
		JYearChooser yearChooser = new JYearChooser();
		yearChooser.setBounds(140, 117, 264, 20);
		panel.add(yearChooser);
		
		JComboBox comboBox = new JComboBox();
		try
		{
			comboBox.addItem("SELECT");
			String query2 = "select * from Company";
		    PreparedStatement pmt2 = connection.prepareStatement(query2);
		    ResultSet rs2 = pmt2.executeQuery();
		    
		    while(rs2.next())
		    {
		    	comboBox.addItem(rs2.getString("Name"));
		    }
		    rs2.close();
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
		comboBox.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		comboBox.setBounds(140, 76, 264, 25);
		panel.add(comboBox);
		
		//Date date=new Date(); 
		
		SimpleDateFormat dateFormat1234 = new SimpleDateFormat("MM");
		Calendar c = Calendar.getInstance();
		
		String date_from = dateFormat1234.format(c.getTime());
		
		if(date_from.equals("01")||date_from.equals("02")||date_from.equals("03"))
		{
			int year = yearChooser.getYear();
			year=year-1;
			yearChooser.setYear(year);
		}
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					if(comboBox.getSelectedItem().toString().equals("SELECT"))
					{
						JOptionPane.showMessageDialog(null, "Plese Select Company!!"); 
					}
					else
					{
						String query="delete from Company_temp";
	                    PreparedStatement pmt = connection.prepareStatement(query);
	                    pmt.executeUpdate();
	                    pmt.close();
	                    
	                    int year = yearChooser.getYear();

	                	SimpleDateFormat dateFormat123 = new SimpleDateFormat("yyyy-MM-dd");
	    				Calendar c = Calendar.getInstance();

	    				c.set(year, 3, 1); // Specify day of month
	    				String date_from = dateFormat123.format(c.getTime());

	    				Calendar cal12 = Calendar.getInstance();
	    				DateFormat dateFormat145 = new SimpleDateFormat("yyyy-MM-dd");
	    				cal12.set(year, 2, 31);
	    		        cal12.add(Calendar.YEAR, 1);

	    		        String date_to = dateFormat145.format(cal12.getTime());
	    		        
	    		        
	                    String query1="insert into Company_temp (Name,Date_from,Date_to) VALUES ('"+comboBox.getSelectedItem().toString()+"','"+date_from+"','"+date_to+"')";
	                    PreparedStatement pmt1 = connection.prepareStatement(query1);
	                    pmt1.executeUpdate();
	                    pmt1.close();
	                    
	                    
                    	Viral s2 = new Viral();
	                    s2.show();
	                    dispose();
	                     
					}
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
		});
		btnSubmit.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnSubmit.setBounds(140, 216, 89, 23);
		panel.add(btnSubmit);
		
		JLabel my = new JLabel("");
		my.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		my.setBounds(13, 162, 391, 33);
		panel.add(my);
		
		yearChooser.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				
				int year = yearChooser.getYear();
            	
            	SimpleDateFormat dateFormat123 = new SimpleDateFormat("yyyy-MM-dd");
				Calendar c = Calendar.getInstance();
				
				
				c.set(year, 3, 1); // Specify day of month
				String date_from = dateFormat123.format(c.getTime());
				
				Calendar cal12 = Calendar.getInstance();
				DateFormat dateFormat145 = new SimpleDateFormat("yyyy-MM-dd");
				cal12.set(year, 2, 31);
		        cal12.add(Calendar.YEAR, 1);
		        
		        
		        String date_to = dateFormat145.format(cal12.getTime());
		        
		        Date startDate = null,startDate1 = null;

                try 
                {
                	SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
                    startDate = sm.parse(date_from);
                    startDate1 = sm.parse(date_to);
                }
                catch (ParseException ae) {
                    ae.printStackTrace();
                }
                
                SimpleDateFormat sm2 = new SimpleDateFormat("dd-MM-yyyy");
                String strDate = sm2.format(startDate);
                String strDate1 = sm2.format(startDate1);
                
            
		        my.setText(strDate+" To "+strDate1);
			}
		});
		
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBounds(196, 259, 228, 22);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnAddANew = new JButton("Add A New Company?");
		btnAddANew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Add_company1 s2 = new Add_company1();
                s2.show();
                dispose();
			}
		});
		btnAddANew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Add_company1 s2 = new Add_company1();
                s2.show();
                dispose();
			}
		});
		btnAddANew.setBorder(null);
		btnAddANew.setBackground(Color.GRAY);
		btnAddANew.setForeground(Color.WHITE);
		btnAddANew.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnAddANew.setBounds(10, 0, 208, 23);
		panel_1.add(btnAddANew);
	
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "Cancel1"); //$NON-NLS-1$
	        getRootPane().getActionMap().put("Cancel1", new AbstractAction(){ //$NON-NLS-1$
	            public void actionPerformed(ActionEvent e)
	            {
	            	Add_company1 s2 = new Add_company1();
	                s2.show();
	                dispose();
	            }
	        });
	}
} 