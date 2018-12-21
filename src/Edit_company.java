import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.awt.Color;

public class Edit_company extends JInternalFrame {
	private JTextField name;
	private JTextField address1;
	private JTextField address2;
	private JTextField mobile;
	private JTextField email;
	private JTextField website;
	private JTextField pan;
	private JTextField gst;
	private JTextField mobile1;
	private JTextField landline;
	private JTextField landline1;
	String k=null,k1=null;
	public JLabel pic,pic1;
	int id=0;
	private JTextField address3;
	
	public byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            FileInputStream fis = new FileInputStream(f);
            byte[] buffer = new byte[115000];
            bos = new ByteArrayOutputStream();
            for (int len; (len = fis.read(buffer)) != -1;) {
                bos.write(buffer, 0, len);
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e2) {
            System.err.println(e2.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }
 
	public ImageIcon ResizeImage(String imgPath)
	{
		 ImageIcon MyImage = new ImageIcon(imgPath);
		 java.awt.Image img = MyImage.getImage();
		 java.awt.Image newImage = img.getScaledInstance(pic.getWidth(), pic.getHeight(),java.awt.Image.SCALE_SMOOTH);
		 ImageIcon image = new ImageIcon(newImage);
		 return image;
	}
	
	public ImageIcon ResizeImage1(String imgPath)
	{
		 ImageIcon MyImage = new ImageIcon(imgPath);
		 java.awt.Image img = MyImage.getImage();
		 java.awt.Image newImage = img.getScaledInstance(pic1.getWidth(), pic1.getHeight(),java.awt.Image.SCALE_SMOOTH);
		 ImageIcon image = new ImageIcon(newImage);
		 return image;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edit_company frame = new Edit_company();
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
	public Edit_company() {
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
	        setClosable(true);
			setBounds((w-639)/2, (h-647)/2, 639, 647);
			getContentPane().setLayout(null);
		
		final java.sql.Connection connection = Databaseconnection.connection2();
		
		JButton button = new JButton("Upload Logo");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
		         fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		         FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
		         fileChooser.addChoosableFileFilter(filter);
		         int result = fileChooser.showSaveDialog(null);
		         if(result == JFileChooser.APPROVE_OPTION)
		         {
		             File selectedFile = fileChooser.getSelectedFile();
		             String path = selectedFile.getAbsolutePath();
		             pic.setIcon(ResizeImage(path));
		             
		             k = path;
		         }
		         else if(result == JFileChooser.CANCEL_OPTION)
		         {
		             JOptionPane.showMessageDialog(null, "No Data");
		         }
			}
		});
		button.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		button.setBounds(416, 62, 184, 25);
		getContentPane().add(button);
		
		 pic = new JLabel(" ");
		pic.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		pic.setBounds(438, 99, 162, 92);
		getContentPane().add(pic);
		
		pic1 = new JLabel(" ");
		pic1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		pic1.setBounds(438, 255, 162, 92);
		getContentPane().add(pic1);
		
		JButton button_1 = new JButton("Upload Signature");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
		         fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		         FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg","gif","png");
		         fileChooser.addChoosableFileFilter(filter);
		         int result = fileChooser.showSaveDialog(null);
		         if(result == JFileChooser.APPROVE_OPTION)
		         {
		             File selectedFile = fileChooser.getSelectedFile();
		             String path = selectedFile.getAbsolutePath();
		             pic1.setIcon(ResizeImage1(path));
		             
		             k1 = path;
		         }
		         else if(result == JFileChooser.CANCEL_OPTION)
		         {
		             JOptionPane.showMessageDialog(null, "No Data");
		         }
			}
		});
		button_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		button_1.setBounds(416, 218, 184, 25);
		getContentPane().add(button_1);
		
		JLabel lblEditCompany = new JLabel("Edit Company");
		lblEditCompany.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblEditCompany.setBounds(158, 0, 168, 42);
		getContentPane().add(lblEditCompany);
		
		JLabel label_4 = new JLabel("Company Name:*");
		label_4.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_4.setBounds(10, 40, 138, 25);
		getContentPane().add(label_4);
		
		name = new JTextField();
		name.setForeground(Color.RED);
		name.setEnabled(false);
		name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		name.setColumns(10);
		name.setBounds(147, 40, 237, 25);
		getContentPane().add(name);
		
		JLabel lblAddress = new JLabel("Address 1:");
		lblAddress.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress.setBounds(10, 75, 138, 25);
		getContentPane().add(lblAddress);
		
		address1 = new JTextField();
		address1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address1.setColumns(10);
		address1.setBounds(147, 75, 239, 25);
		getContentPane().add(address1);
		
		JLabel lblAddress_1 = new JLabel("Address 2:");
		lblAddress_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress_1.setBounds(10, 110, 138, 25);
		getContentPane().add(lblAddress_1);
		
		address2 = new JTextField();
		address2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address2.setColumns(10);
		address2.setBounds(145, 110, 239, 25);
		getContentPane().add(address2);
		
		JLabel label_7 = new JLabel("State:*");
		label_7.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_7.setBounds(10, 179, 138, 25);
		getContentPane().add(label_7);
		
		JComboBox state = new JComboBox();
		state.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		state.setBounds(147, 179, 237, 25);
		getContentPane().add(state);
		
		JLabel label_8 = new JLabel("City:*");
		label_8.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_8.setBounds(10, 214, 138, 25);
		getContentPane().add(label_8);
		
		JComboBox city = new JComboBox();
		city.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		city.setBounds(147, 214, 237, 25);
		getContentPane().add(city);
		
		JLabel lblMobile = new JLabel("Mobile 1:");
		lblMobile.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblMobile.setBounds(10, 249, 138, 25);
		getContentPane().add(lblMobile);
		
		mobile = new JTextField();
		mobile.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		mobile.setColumns(10);
		mobile.setBounds(147, 249, 237, 25);
		getContentPane().add(mobile);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnUpdate.setBounds(178, 548, 119, 25);
		getContentPane().add(btnUpdate);
		
		JLabel label_10 = new JLabel("GST No:");
		label_10.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_10.setBounds(10, 389, 138, 25);
		getContentPane().add(label_10);
		
		JLabel label_11 = new JLabel("Email:");
		label_11.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_11.setBounds(10, 424, 138, 25);
		getContentPane().add(label_11);
		
		email = new JTextField();
		email.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		email.setColumns(10);
		email.setBounds(147, 424, 237, 25);
		getContentPane().add(email);
		
		website = new JTextField();
		website.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		website.setColumns(10);
		website.setBounds(145, 459, 239, 25);
		getContentPane().add(website);
		
		JLabel label_12 = new JLabel("Web site:");
		label_12.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_12.setBounds(10, 459, 138, 25);
		getContentPane().add(label_12);
		
		JLabel lblPanNo = new JLabel("PAN No:");
		lblPanNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblPanNo.setBounds(10, 494, 138, 25);
		getContentPane().add(lblPanNo);
		
		pan = new JTextField();
		pan.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		pan.setColumns(10);
		pan.setBounds(147, 494, 237, 25);
		getContentPane().add(pan);
		
		gst = new JTextField();
		gst.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		gst.setColumns(10);
		gst.setBounds(145, 389, 239, 25);
		getContentPane().add(gst);
		
		JLabel label_14 = new JLabel("Mobile 2:");
		label_14.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_14.setBounds(10, 284, 138, 25);
		getContentPane().add(label_14);
		
		mobile1 = new JTextField();
		mobile1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		mobile1.setColumns(10);
		mobile1.setBounds(147, 284, 237, 25);
		getContentPane().add(mobile1);
		
		JLabel label_15 = new JLabel("Landline 1:");
		label_15.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_15.setBounds(10, 319, 138, 25);
		getContentPane().add(label_15);
		
		JLabel label_16 = new JLabel("Landline 2:");
		label_16.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_16.setBounds(10, 354, 138, 25);
		getContentPane().add(label_16);
		
		landline = new JTextField();
		landline.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		landline.setColumns(10);
		landline.setBounds(147, 319, 237, 25);
		getContentPane().add(landline);
		
		landline1 = new JTextField();
		landline1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		landline1.setColumns(10);
		landline1.setBounds(147, 354, 237, 25);
		getContentPane().add(landline1);
		
		 JLabel lblAddress_2 = new JLabel("Address 3:");
         lblAddress_2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
         lblAddress_2.setBounds(10, 146, 138, 25);
         getContentPane().add(lblAddress_2);
         
         address3 = new JTextField();
         address3.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
         address3.setColumns(10);
         address3.setBounds(145, 146, 239, 25);
         getContentPane().add(address3);
         
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
		
		try
		{
			String query1 = "select * from Company_id";
            java.sql.PreparedStatement pmt1 = connection.prepareStatement(query1);
            ResultSet rs1 = pmt1.executeQuery();
            while(rs1.next())
            {
            	id=rs1.getInt("ID");
            }
            rs1.close();
            pmt1.close();
            
            
            byte[] logo = null,sign=null;
            
            String query = "select * from Company where ID='"+id+"'";
            java.sql.PreparedStatement pmt = connection.prepareStatement(query);
            ResultSet rs = pmt.executeQuery();
            while(rs.next())
            {
            	name.setText(rs.getString("Name"));
            	mobile.setText(rs.getString("Mobile"));
            	mobile1.setText(rs.getString("Mobile1"));
            	landline.setText(rs.getString("Landline"));
            	landline1.setText(rs.getString("Landline1"));
            	address1.setText(rs.getString("Address1"));
            	address2.setText(rs.getString("Address2"));
            	address3.setText(rs.getString("Address3"));
            	state.setSelectedItem(rs.getString("State"));
            	city.setSelectedItem(rs.getString("City"));
            	gst.setText(rs.getString("GST"));
            	email.setText(rs.getString("Email"));
            	website.setText(rs.getString("Website"));
            	pan.setText(rs.getString("Pan_no"));
            	logo = rs.getBytes("Logo");
            	sign = rs.getBytes("Signature");
            }
            rs.close();
            pmt.close();
            
            pic.setIcon(null);
            
            if(logo!=null) 
            {
            	ImageIcon image = new ImageIcon(logo);
                java.awt.Image im = image.getImage();
                java.awt.Image myImg = im.getScaledInstance(pic.getWidth(), pic.getHeight(),java.awt.Image.SCALE_SMOOTH);
                ImageIcon newImage = new ImageIcon(myImg);
                pic.setIcon(newImage);
            }
            
            pic1.setIcon(null);
            
            if(sign!=null) 
            {
            	ImageIcon image1 = new ImageIcon(sign);
                java.awt.Image im1 = image1.getImage();
                java.awt.Image myImg1 = im1.getScaledInstance(pic1.getWidth(), pic1.getHeight(),java.awt.Image.SCALE_SMOOTH);
                ImageIcon newImage1 = new ImageIcon(myImg1);
                pic1.setIcon(newImage1);
            }
		    
            
           
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					String text = mobile.getText();
		            int counter = text.length();
		            
		            if(name.getText().equals("") || city.getSelectedItem().toString().equals("SELECT"))
    				{
    					JOptionPane.showMessageDialog(null, "please enter details");
    				}
					else
					{
						//String query="insert into Company(Name,Mobile,Address1,Address2,City,State,GST,Email,Website,Pan_no,Mobile1,Landline,Landline1,Code) Values ('"+name.getText()+"','"+mobile.getText()+"','"+address1.getText()+"','"+address2.getText()+"','"+city.getSelectedItem().toString()+"','"+state.getSelectedItem().toString()+"','"+gst.getText()+"','"+email.getText()+"','"+website.getText()+"','"+pan.getText()+"','"+mobile1.getText()+"','"+landline.getText()+"','"+landline1.getText()+"','"+code.getText()+"')";
		            	String query="update Company set Name='"+name.getText()+"',Mobile='"+mobile.getText()+"',Mobile1='"+mobile1.getText()+"',Landline='"+landline.getText()+"',Landline1='"+landline1.getText()+"',Address1='"+address1.getText()+"',Address2='"+address2.getText()+"',City='"+city.getSelectedItem().toString()+"',State='"+state.getSelectedItem().toString()+"',GST='"+gst.getText()+"',Email='"+email.getText()+"',Website='"+website.getText()+"',Pan_no='"+pan.getText()+"',Address3='"+address3.getText()+"' where ID='"+id+"'";
			            PreparedStatement pmt = connection.prepareStatement(query);
			            pmt.executeUpdate();
			            pmt.close();

			            if(empty(k))
    					{

    					}
    					else
    					{
    						String query1="update Company set Logo=? where ID='"+id+"'";
    			            PreparedStatement pmt1 = connection.prepareStatement(query1);
    			            pmt1.setBytes(1, readFile(k));
                            pmt1.executeUpdate();
    			            pmt1.close();
    					}
    					if(empty(k1))
    					{

    					}
    					else
    					{
    						String query1="update Company set Signature=? where ID='"+id+"'";
    			            PreparedStatement pmt1 = connection.prepareStatement(query1);
    			            pmt1.setBytes(1, readFile(k1));
                            pmt1.executeUpdate();
    			            pmt1.close();
    					}
    					System.exit(0);
					}
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
		});
	}
	public static boolean empty( final String s ) 
	{
		return s == null || s.trim().isEmpty();
	}
}