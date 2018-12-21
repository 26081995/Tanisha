import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;


import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Detail_client extends JInternalFrame {
	private JTextField name;
	private JTextField address1;
	private JTextField address2;
	private JTextField mobile;
	private JTextField email;
	private JTextField website;
	private JTextField gst;
	String company=null,company1=null;
	private JTextField pan;
	private JTextField state;
	private JTextField city;
	private JTextField mobile1;
	private JTextField landline;
	private JTextField landline1;
	private JTextField address3;
	private JTextField pending;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Detail_client frame = new Detail_client();
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
	public Detail_client() {
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
	        setBounds((w-460)/2, (h-631)/2, 460, 631);
			getContentPane().setLayout(null);
			
			DecimalFormat dc=new DecimalFormat("0.00");
			
			
			final java.sql.Connection connection = Databaseconnection.connection2();
			
			try
			{
				String query1 = "select * from Client_id";
			    PreparedStatement pmt1 = connection.prepareStatement(query1);
			    ResultSet rs1 = pmt1.executeQuery();
			    while(rs1.next())
			    {
			    	company=rs1.getString("ID");
			    }
			    rs1.close();
			}
			catch(Exception ae)
			{
				ae.printStackTrace();
			}
			
			
			try
			{
				String query1 = "select * from Company_temp";
			    PreparedStatement pmt1 = connection.prepareStatement(query1);
			    ResultSet rs1 = pmt1.executeQuery();
			    while(rs1.next())
			    {
			    	company1=rs1.getString("Name");
			    }
			    rs1.close();
			}
			catch(Exception ae)
			{
				ae.printStackTrace();
			}
			
			
			
			JLabel lblAddClient = new JLabel("Detail Client");
		lblAddClient.setBounds(135, 11, 154, 36);
		lblAddClient.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		getContentPane().add(lblAddClient);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName.setBounds(10, 75, 128, 25);
		getContentPane().add(lblName);
		
		name = new JTextField();
		name.setEditable(false);
		name.setForeground(Color.RED);
		name.setFont(new Font("Tahoma", Font.PLAIN, 15));
		name.setColumns(10);
		name.setBounds(135, 75, 282, 25);
		getContentPane().add(name);
		
		JLabel lblAddress = new JLabel("Address 1:");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAddress.setBounds(10, 110, 108, 25);
		getContentPane().add(lblAddress);
		
		address1 = new JTextField();
		address1.setEditable(false);
		address1.setForeground(Color.RED);
		address1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		address1.setColumns(10);
		address1.setBounds(135, 110, 282, 25);
		getContentPane().add(address1);
		
		JLabel lblAddress_1 = new JLabel("Address 2:");
		lblAddress_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAddress_1.setBounds(10, 145, 108, 25);
		getContentPane().add(lblAddress_1);
		
		address2 = new JTextField();
		address2.setEditable(false);
		address2.setForeground(Color.RED);
		address2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		address2.setColumns(10);
		address2.setBounds(135, 145, 282, 25);
		getContentPane().add(address2);
		
		JLabel lblState = new JLabel("State:");
		lblState.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblState.setBounds(10, 218, 79, 25);
		getContentPane().add(lblState);
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCity.setBounds(10, 253, 53, 25);
		getContentPane().add(lblCity);
		
		JLabel lblMobileNo = new JLabel("Mobile 1:");
		lblMobileNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMobileNo.setBounds(10, 288, 108, 25);
		getContentPane().add(lblMobileNo);
		
		mobile = new JTextField();
		mobile.setEditable(false);
		mobile.setForeground(Color.RED);
		mobile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mobile.setColumns(10);
		mobile.setBounds(135, 288, 282, 25);
		getContentPane().add(mobile);
		
		JLabel label_6 = new JLabel("Email:");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_6.setBounds(10, 428, 66, 25);
		getContentPane().add(label_6);
		
		email = new JTextField();
		email.setEditable(false);
		email.setForeground(Color.RED);
		email.setFont(new Font("Tahoma", Font.PLAIN, 15));
		email.setColumns(10);
		email.setBounds(135, 428, 282, 25);
		getContentPane().add(email);
		
		JLabel label_7 = new JLabel("Website:");
		label_7.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label_7.setBounds(10, 463, 108, 25);
		getContentPane().add(label_7);
		
		website = new JTextField();
		website.setEditable(false);
		website.setForeground(Color.RED);
		website.setFont(new Font("Tahoma", Font.PLAIN, 15));
		website.setColumns(10);
		website.setBounds(135, 463, 282, 25);
		getContentPane().add(website);
		
		JLabel lblGstNo = new JLabel("GST No:");
		lblGstNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblGstNo.setBounds(10, 498, 108, 25);
		getContentPane().add(lblGstNo);
		
		gst = new JTextField();
		gst.setEditable(false);
		gst.setForeground(Color.RED);
		gst.setFont(new Font("Tahoma", Font.PLAIN, 15));
		gst.setColumns(10);
		gst.setBounds(135, 498, 282, 25);
		getContentPane().add(gst);
		
		JLabel lblPanNo = new JLabel("PAN No:");
		lblPanNo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblPanNo.setBounds(10, 533, 108, 25);
		getContentPane().add(lblPanNo);
		
		pan = new JTextField();
		pan.setEditable(false);
		pan.setForeground(Color.RED);
		pan.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pan.setColumns(10);
		pan.setBounds(135, 533, 282, 25);
		getContentPane().add(pan);
		
		state = new JTextField();
		state.setEditable(false);
		state.setForeground(Color.RED);
		state.setFont(new Font("Tahoma", Font.PLAIN, 15));
		state.setColumns(10);
		state.setBounds(135, 218, 282, 25);
		getContentPane().add(state);
		
		city = new JTextField();
		city.setEditable(false);
		city.setForeground(Color.RED);
		city.setFont(new Font("Tahoma", Font.PLAIN, 15));
		city.setColumns(10);
		city.setBounds(135, 253, 282, 25);
		getContentPane().add(city);
		
		JLabel lblMobile = new JLabel("Mobile 2:");
		lblMobile.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMobile.setBounds(10, 323, 108, 25);
		getContentPane().add(lblMobile);
		
		mobile1 = new JTextField();
		mobile1.setEditable(false);
		mobile1.setForeground(Color.RED);
		mobile1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		mobile1.setColumns(10);
		mobile1.setBounds(135, 323, 282, 25);
		getContentPane().add(mobile1);
		
		JLabel lblLandline = new JLabel("Landline 1:");
		lblLandline.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLandline.setBounds(10, 358, 108, 25);
		getContentPane().add(lblLandline);
		
		landline = new JTextField();
		landline.setEditable(false);
		landline.setForeground(Color.RED);
		landline.setFont(new Font("Tahoma", Font.PLAIN, 15));
		landline.setColumns(10);
		landline.setBounds(135, 358, 282, 25);
		getContentPane().add(landline);
		
		JLabel lblLandline_1 = new JLabel("Landline 2:");
		lblLandline_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLandline_1.setBounds(10, 393, 120, 25);
		getContentPane().add(lblLandline_1);
		
		landline1 = new JTextField();
		landline1.setEditable(false);
		landline1.setForeground(Color.RED);
		landline1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		landline1.setColumns(10);
		landline1.setBounds(135, 393, 282, 25);
		getContentPane().add(landline1);
		
		JLabel lblAddress_2 = new JLabel("Address 3:");
		lblAddress_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAddress_2.setBounds(10, 181, 108, 25);
		getContentPane().add(lblAddress_2);
		
		address3 = new JTextField();
		address3.setForeground(Color.RED);
		address3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		address3.setEditable(false);
		address3.setColumns(10);
		address3.setBounds(135, 181, 282, 25);
		getContentPane().add(address3);
		
		TableModel model = null;
		
		
		JLabel lblBalance = new JLabel("Balance:");
	    lblBalance.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	    lblBalance.setBounds(10, 569, 126, 25);
	    getContentPane().add(lblBalance);
	    
	    pending = new JTextField();
	    pending.setText((String) null);
	    pending.setHorizontalAlignment(SwingConstants.RIGHT);
	    pending.setForeground(Color.RED);
	    pending.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	    pending.setEditable(false);
	    pending.setColumns(10);
	    pending.setBounds(137, 569, 280, 25);
	    getContentPane().add(pending);
	    
		
		try
		{
			String query1 = "select * from Client where ID='"+company+"'";
		    PreparedStatement pmt1 = connection.prepareStatement(query1);
		    ResultSet rs1 = pmt1.executeQuery();
		    while(rs1.next())
		    {
		    	name.setText(rs1.getString("Name"));
		    	address1.setText(rs1.getString("Address1"));
		    	address2.setText(rs1.getString("Address2"));
		    	address3.setText(rs1.getString("Address3"));
		    	state.setText(rs1.getString("State"));
		    	city.setText(rs1.getString("City"));
		    	mobile.setText(rs1.getString("Mobile"));
		    	email.setText(rs1.getString("Email"));
		    	website.setText(rs1.getString("Website"));
		    	gst.setText(rs1.getString("GST"));
		    	pan.setText(rs1.getString("Pan_no"));
		    	mobile1.setText(rs1.getString("Mobile1"));
		    	landline.setText(rs1.getString("Landline"));
		    	landline1.setText(rs1.getString("Landline1"));
		    	pending.setText(rs1.getString("Balance"));
		    }
		    rs1.close();

		    
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
		
		
		
		
	}
	public static boolean empty( final String s ) 
	{
		return s == null || s.trim().isEmpty();
	}
}
