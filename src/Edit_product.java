import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import java.awt.Color;

public class Edit_product extends JInternalFrame {
	String company=null,id=null;
	private JTextField hsn;
	private JTextField description;
	private JTextField stock;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Edit_product frame = new Edit_product();
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
	public Edit_product() {
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Cancel"); //$NON-NLS-1$
		getRootPane().getActionMap().put("Cancel", new AbstractAction()
		{
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
	setBounds((w-450)/2, (h-219)/2, 450, 250);
	setClosable(true);
	getContentPane().setLayout(null);
	
	
	final Connection connection = Databaseconnection.connection2();
	JPanel panel = new JPanel();
	panel.setLayout(null);
	panel.setBounds(0, 0, 434, 378);
	getContentPane().add(panel);
	
	JLabel lblAddExtraHour = new JLabel("Edit Product");
	lblAddExtraHour.setFont(new Font("Book Antiqua", Font.BOLD, 25));
	lblAddExtraHour.setBounds(130, 10, 176, 24);
	panel.add(lblAddExtraHour);
	
	try
	{
		String query51 = "select * from Company_temp";
        PreparedStatement pmt51 = connection.prepareStatement(query51);
        ResultSet rs51 = pmt51.executeQuery();
	    while(rs51.next())
	    {
	    	company=rs51.getString("Name");
        }
	    rs51.close();
	    pmt51.close();
	}
	catch(Exception ae)
	{
		ae.printStackTrace();
	}
	
	try
	{
		String query51 = "select * from Product_id";
        PreparedStatement pmt51 = connection.prepareStatement(query51);
        ResultSet rs51 = pmt51.executeQuery();
	    while(rs51.next())
	    {
	    	id=rs51.getString("ID");
        }
	    rs51.close();
	    pmt51.close();
	}
	catch(Exception ae)
	{
		ae.printStackTrace();
	}
	
	JTextField name = new JTextField();
	name.setForeground(Color.RED);
	name.setEditable(false);
	name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	name.setColumns(10);
	name.setBounds(110, 45, 314, 25);
	panel.add(name);

	JButton button = new JButton("Submit");
	button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try
            {
				if(name.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null,"Please Enter Details.");
                }
                else 
                {
                	
                	//String query="insert into Product (Company,Name,HSN,Description) VALUES ('"+company+"','"+name.getText()+"','"+hsn.getText()+"','"+description.getText()+"')";
                	String query="update Product set HSn='"+hsn.getText()+"',Description='"+description.getText()+"' where ID='"+id+"'";
                    PreparedStatement pmt = connection.prepareStatement(query);
                    pmt.executeUpdate();
                    pmt.close();
                    
                    View_product b = new View_product();
                    JDesktopPane desktopPane = getDesktopPane();
                    desktopPane.add(b);
                    b.show();
                    dispose();
                }
            }
           catch (SQLException ae) {
               ae.printStackTrace();
           }
		}
	});
	button.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	button.setBounds(164, 186, 89, 25);
	panel.add(button);
	
	JLabel lblName = new JLabel("Name:*");
	lblName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	lblName.setBounds(10, 45, 90, 25);
	panel.add(lblName);
	
	JLabel lblHsn = new JLabel("HSN:");
	lblHsn.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	lblHsn.setBounds(10, 80, 90, 25);
	panel.add(lblHsn);
	
	hsn = new JTextField();
	hsn.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	hsn.setColumns(10);
	hsn.setBounds(110, 80, 314, 25);
	panel.add(hsn);
	
	JLabel lblStyock = new JLabel("Description:");
	lblStyock.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	lblStyock.setBounds(10, 115, 90, 25);
	panel.add(lblStyock);
	
	description = new JTextField();
	description.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	description.setColumns(10);
	description.setBounds(110, 115, 314, 25);
	panel.add(description);
	
	JLabel lblStock = new JLabel("Stock:*");
	lblStock.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	lblStock.setBounds(10, 150, 90, 25);
	panel.add(lblStock);
	
	stock = new JTextField();
	stock.setForeground(Color.RED);
	stock.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	stock.setEditable(false);
	stock.setColumns(10);
	stock.setBounds(110, 150, 314, 25);
	panel.add(stock);
	
	description.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try
            {
				if(name.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null,"Please Enter Details.");
                }
                else 
                {
                	
                	String query="update Product set HSn='"+hsn.getText()+"',Description='"+description.getText()+"' where ID='"+id+"'";
                    PreparedStatement pmt = connection.prepareStatement(query);
                    pmt.executeUpdate();
                    pmt.close();
                    
                    View_product b = new View_product();
                    JDesktopPane desktopPane = getDesktopPane();
                    desktopPane.add(b);
                    b.show();
                    dispose();
                }
            }
           catch (SQLException ae) {
               ae.printStackTrace();
           }
		}
	});
	
	try
	{
		String query51 = "select * from Product where ID='"+id+"'";
        PreparedStatement pmt51 = connection.prepareStatement(query51);
        ResultSet rs51 = pmt51.executeQuery();
	    while(rs51.next())
	    {
	    	name.setText(rs51.getString("Name"));
	    	hsn.setText(rs51.getString("HSN"));
	    	description.setText(rs51.getString("Description"));
	    	stock.setText(rs51.getString("Stock"));
        }
	    rs51.close();
	    pmt51.close();
	}
	catch(Exception ae)
	{
		ae.printStackTrace();
	}
}
}