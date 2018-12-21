import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
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

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Detail_company extends JInternalFrame {
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
	private JTextField city;
	private JTextField state;
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
					Detail_company frame = new Detail_company();
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
	public Detail_company() {
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
			setBounds((w-639)/2, (h-611)/2, 639, 611);
			getContentPane().setLayout(null);
		
		final java.sql.Connection connection = Databaseconnection.connection2();
		
		 pic = new JLabel(" ");
		pic.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		pic.setBounds(438, 99, 162, 92);
		getContentPane().add(pic);
		
		pic1 = new JLabel(" ");
		pic1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		pic1.setBounds(438, 255, 162, 92);
		getContentPane().add(pic1);
		
		JLabel lblEditCompany = new JLabel("Detail Company");
		lblEditCompany.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblEditCompany.setBounds(158, 0, 198, 42);
		getContentPane().add(lblEditCompany);
		
		JLabel label_4 = new JLabel("Company Name:*");
		label_4.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_4.setBounds(10, 40, 138, 25);
		getContentPane().add(label_4);
		
		name = new JTextField();
		name.setEditable(false);
		name.setForeground(Color.RED);
		name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		name.setColumns(10);
		name.setBounds(147, 40, 237, 25);
		getContentPane().add(name);
		
		JLabel label_5 = new JLabel("Address 1:*");
		label_5.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_5.setBounds(10, 75, 138, 25);
		getContentPane().add(label_5);
		
		address1 = new JTextField();
		address1.setEditable(false);
		address1.setForeground(Color.RED);
		address1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address1.setColumns(10);
		address1.setBounds(147, 75, 239, 25);
		getContentPane().add(address1);
		
		JLabel label_6 = new JLabel("Address 2:*");
		label_6.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_6.setBounds(10, 110, 138, 25);
		getContentPane().add(label_6);
		
		address2 = new JTextField();
		address2.setEditable(false);
		address2.setForeground(Color.RED);
		address2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		address2.setColumns(10);
		address2.setBounds(145, 110, 239, 25);
		getContentPane().add(address2);
		
		JLabel label_7 = new JLabel("State:*");
		label_7.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_7.setBounds(10, 180, 138, 25);
		getContentPane().add(label_7);
		
		JLabel label_8 = new JLabel("City:*");
		label_8.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_8.setBounds(10, 215, 138, 25);
		getContentPane().add(label_8);
		
		JLabel label_9 = new JLabel("Mobile 1:*");
		label_9.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_9.setBounds(10, 250, 138, 25);
		getContentPane().add(label_9);
		
		mobile = new JTextField();
		mobile.setEditable(false);
		mobile.setForeground(Color.RED);
		mobile.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		mobile.setColumns(10);
		mobile.setBounds(147, 250, 237, 25);
		getContentPane().add(mobile);
		
		JLabel label_10 = new JLabel("GST No:");
		label_10.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_10.setBounds(10, 390, 138, 25);
		getContentPane().add(label_10);
		
		JLabel label_11 = new JLabel("Email:");
		label_11.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_11.setBounds(10, 425, 138, 25);
		getContentPane().add(label_11);
		
		email = new JTextField();
		email.setEditable(false);
		email.setForeground(Color.RED);
		email.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		email.setColumns(10);
		email.setBounds(147, 425, 237, 25);
		getContentPane().add(email);
		
		website = new JTextField();
		website.setEditable(false);
		website.setForeground(Color.RED);
		website.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		website.setColumns(10);
		website.setBounds(145, 460, 239, 25);
		getContentPane().add(website);
		
		JLabel label_12 = new JLabel("Web site:");
		label_12.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_12.setBounds(10, 460, 138, 25);
		getContentPane().add(label_12);
		
		JLabel lblPanNo = new JLabel("PAN No:");
		lblPanNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblPanNo.setBounds(10, 495, 138, 25);
		getContentPane().add(lblPanNo);
		
		pan = new JTextField();
		pan.setEditable(false);
		pan.setForeground(Color.RED);
		pan.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		pan.setColumns(10);
		pan.setBounds(147, 495, 237, 25);
		getContentPane().add(pan);
		
		gst = new JTextField();
		gst.setEditable(false);
		gst.setForeground(Color.RED);
		gst.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		gst.setColumns(10);
		gst.setBounds(145, 390, 239, 25);
		getContentPane().add(gst);
		
		JLabel label_14 = new JLabel("Mobile 2:");
		label_14.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_14.setBounds(10, 285, 138, 25);
		getContentPane().add(label_14);
		
		mobile1 = new JTextField();
		mobile1.setEditable(false);
		mobile1.setForeground(Color.RED);
		mobile1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		mobile1.setColumns(10);
		mobile1.setBounds(147, 285, 237, 25);
		getContentPane().add(mobile1);
		
		JLabel label_15 = new JLabel("Landline 1:");
		label_15.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_15.setBounds(10, 320, 138, 25);
		getContentPane().add(label_15);
		
		JLabel label_16 = new JLabel("Landline 2:");
		label_16.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_16.setBounds(10, 355, 138, 25);
		getContentPane().add(label_16);
		
		landline = new JTextField();
		landline.setEditable(false);
		landline.setForeground(Color.RED);
		landline.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		landline.setColumns(10);
		landline.setBounds(147, 320, 237, 25);
		getContentPane().add(landline);
		
		landline1 = new JTextField();
		landline1.setEditable(false);
		landline1.setForeground(Color.RED);
		landline1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		landline1.setColumns(10);
		landline1.setBounds(147, 355, 237, 25);
		getContentPane().add(landline1);
		
		JLabel lblLogo = new JLabel("Logo");
        lblLogo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
        lblLogo.setBounds(480, 40, 46, 25);
        getContentPane().add(lblLogo);
        
        JLabel lblSignature = new JLabel("Signature");
        lblSignature.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
        lblSignature.setBounds(469, 219, 84, 25);
        getContentPane().add(lblSignature);
        
        city = new JTextField();
        city.setEditable(false);
        city.setForeground(Color.RED);
        city.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
        city.setColumns(10);
        city.setBounds(143, 215, 239, 25);
        getContentPane().add(city);
        
        state = new JTextField();
        state.setEditable(false);
        state.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
        state.setForeground(Color.RED);
        state.setColumns(10);
        state.setBounds(145, 180, 239, 25);
        getContentPane().add(state);
		

        JLabel lblAddress = new JLabel("Address 3:*");
        lblAddress.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
        lblAddress.setBounds(8, 144, 138, 25);
        getContentPane().add(lblAddress);
        
        address3 = new JTextField();
        address3.setForeground(Color.RED);
        address3.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
        address3.setEditable(false);
        address3.setColumns(10);
        address3.setBounds(143, 144, 239, 25);
        getContentPane().add(address3);
        
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
            	state.setText(rs.getString("State"));
            	city.setText(rs.getString("City"));
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
		
		
		
	}
	public static boolean empty( final String s ) 
	{
		return s == null || s.trim().isEmpty();
	}
}