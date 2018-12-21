import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JYearChooser;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JPasswordField;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;

public class Add_company1 extends JFrame {

	private JPanel contentPane;
	private JTextField name;
	private JTextField address1;
	private JTextField address2;
	private JTextField mobile;
	public String stateid, countryid;
	private JTextField gst;
	private JTextField email;
	private JTextField website;
	private JTextField pan;
	private JTextField mobile1;
	private JTextField landline;
	private JTextField landline1;
	String k=null,k1=null;
	public JLabel pic,pic1;
	private JTextField address3;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_company1 frame = new Add_company1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
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

	public Add_company1() {
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

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        int w = (int)(width);
        int h = (int)(height);
        setBounds((w-639)/2, (h-647)/2, 639, 605);
		getContentPane().setLayout(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAddress_2 = new JLabel("Address 3:");
		lblAddress_2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress_2.setBounds(10, 146, 138, 25);
		contentPane.add(lblAddress_2);
		
		address3 = new JTextField();
		address3.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address3.setColumns(10);
		address3.setBounds(145, 146, 239, 25);
		contentPane.add(address3);

		final java.sql.Connection connection = Databaseconnection.connection2();

		JButton btnUploadLogo = new JButton("Upload Logo");
		btnUploadLogo.addActionListener(new ActionListener() {
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
		btnUploadLogo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnUploadLogo.setBounds(416, 62, 184, 25);
		contentPane.add(btnUploadLogo);

		 pic = new JLabel(" ");
		 pic.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		pic.setBounds(438, 99, 162, 92);
		contentPane.add(pic);
		
		 pic1 = new JLabel(" ");
		 pic1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		pic1.setBounds(438, 255, 162, 92);
		contentPane.add(pic1);
		
		JButton btnUploadSignature = new JButton("Upload Signature");
		btnUploadSignature.addActionListener(new ActionListener() {
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
		btnUploadSignature.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnUploadSignature.setBounds(416, 218, 184, 25);
		contentPane.add(btnUploadSignature);

		JLabel lblCompany = new JLabel("Add Company");
		lblCompany.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblCompany.setBounds(158, 0, 168, 42);
		contentPane.add(lblCompany);

		JLabel lblCompanyName = new JLabel("Company Name:*");
		lblCompanyName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblCompanyName.setBounds(10, 40, 138, 25);
		contentPane.add(lblCompanyName);

		name = new JTextField();
		name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		name.setColumns(10);
		name.setBounds(147, 40, 237, 25);
		contentPane.add(name);

		JLabel lblAddress_1 = new JLabel("Address 1:");
		lblAddress_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress_1.setBounds(10, 75, 138, 25);
		contentPane.add(lblAddress_1);

		address1 = new JTextField();
		address1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address1.setColumns(10);
		address1.setBounds(147, 75, 239, 25);
		contentPane.add(address1);

		JLabel lblAddress = new JLabel("Address 2:");
		lblAddress.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAddress.setBounds(10, 110, 138, 25);
		contentPane.add(lblAddress);
		
		address2 = new JTextField();
		address2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address2.setColumns(10);
		address2.setBounds(145, 110, 239, 25);
		contentPane.add(address2);

		JLabel lblState = new JLabel("State:*");
		lblState.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblState.setBounds(10, 182, 138, 25);
		contentPane.add(lblState);

		JComboBox state = new JComboBox();
		state.addItem("SELECT");
		state.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		state.setBounds(147, 182, 237, 25);
		contentPane.add(state);

		JLabel lblCity = new JLabel("City:*");
		lblCity.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblCity.setBounds(10, 217, 138, 25);
		contentPane.add(lblCity);

		JComboBox city = new JComboBox();
		city.addItem("SELECT");
		city.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		city.setBounds(147, 217, 237, 25);
		contentPane.add(city);

		JLabel lblMobileNo = new JLabel("Mobile 1:");
		lblMobileNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblMobileNo.setBounds(10, 252, 138, 25);
		contentPane.add(lblMobileNo);

		mobile = new JTextField();
		mobile.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		mobile.setColumns(10);
		mobile.setBounds(147, 252, 237, 25);
		contentPane.add(mobile);

		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnAdd.setBounds(180, 532, 119, 25);
		contentPane.add(btnAdd);
		
		JLabel lblGstNo = new JLabel("GST No:");
		lblGstNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblGstNo.setBounds(10, 392, 138, 25);
		contentPane.add(lblGstNo);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblEmail.setBounds(10, 427, 138, 25);
		contentPane.add(lblEmail);
		
		email = new JTextField();
		email.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		email.setColumns(10);
		email.setBounds(147, 427, 237, 25);
		contentPane.add(email);
		
		website = new JTextField();
		website.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		website.setColumns(10);
		website.setBounds(145, 462, 239, 25);
		contentPane.add(website);
		
		JLabel lblWebSite = new JLabel("Web site:");
		lblWebSite.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblWebSite.setBounds(10, 462, 138, 25);
		contentPane.add(lblWebSite);
		
		JLabel lblPanNo = new JLabel("PAN No:");
		lblPanNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblPanNo.setBounds(10, 497, 138, 25);
		contentPane.add(lblPanNo);
		
		pan = new JTextField();
		pan.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		pan.setColumns(10);
		pan.setBounds(147, 497, 237, 25);
		contentPane.add(pan);
		
		gst = new JTextField();
		gst.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		gst.setColumns(10);
		gst.setBounds(145, 392, 239, 25);
		contentPane.add(gst);
		
		JLabel lblMobileOther = new JLabel("Mobile 2:");
		lblMobileOther.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblMobileOther.setBounds(10, 287, 138, 25);
		contentPane.add(lblMobileOther);
		
		mobile1 = new JTextField();
		mobile1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		mobile1.setColumns(10);
		mobile1.setBounds(147, 287, 237, 25);
		contentPane.add(mobile1);
		
		JLabel lblLandline = new JLabel("Landline 1:");
		lblLandline.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblLandline.setBounds(10, 322, 138, 25);
		contentPane.add(lblLandline);
		
		JLabel lblLandlineOther = new JLabel("Landline 2:");
		lblLandlineOther.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblLandlineOther.setBounds(10, 357, 138, 25);
		contentPane.add(lblLandlineOther);
		
		landline = new JTextField();
		landline.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		landline.setColumns(10);
		landline.setBounds(147, 322, 237, 25);
		contentPane.add(landline);
		
		landline1 = new JTextField();
		landline1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		landline1.setColumns(10);
		landline1.setBounds(147, 357, 237, 25);
		contentPane.add(landline1);

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
						/*String querydf="insert into Company_detail(Company) Values ('"+name.getText()+"')";
			            PreparedStatement pmtdf = connection.prepareStatement(querydf);
			            pmtdf.executeUpdate();
			            pmtdf.close();
						*/
						String query="insert into Company(Name,Mobile,Address1,Address2,City,State,GST,Email,Website,Pan_no,Mobile1,Landline,Landline1,Address3) Values ('"+name.getText()+"','"+mobile.getText()+"','"+address1.getText()+"','"+address2.getText()+"','"+city.getSelectedItem().toString()+"','"+state.getSelectedItem().toString()+"','"+gst.getText()+"','"+email.getText()+"','"+website.getText()+"','"+pan.getText()+"','"+mobile1.getText()+"','"+landline.getText()+"','"+landline1.getText()+"','"+address3.getText()+"')";
			            PreparedStatement pmt = connection.prepareStatement(query);
			            pmt.executeUpdate();
			            pmt.close();
			            
			            int id=0;

                        ResultSet rsid = pmt.getGeneratedKeys();
                        id=rsid.getInt(1);
    					pmt.close();
    					rsid.close();
    					
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

			            Login log=new Login();
		        		log.show();
		                dispose();
					}
				}
				catch (Exception ae) {
					ae.printStackTrace();
				}
			}
		});
		
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
	}

	public static boolean empty( final String s ) 
	{
		return s == null || s.trim().isEmpty();
	}
}