import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.OutputStream;
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

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.TableModel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.Statement;

import net.proteanit.sql.DbUtils;

public class Add_order extends JInternalFrame {
	private JTextField chadate;
	String company =null,pdf=null,date_from=null,date_to=null;
	private JTextField rate;
	private JTable table;
	int id=0;
	private JTextField total;
	private JTextField remark;
	private JTextField order_no;
	
	public static final String[] units = { "", "One", "Two", "Three", "Four",
			"Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve",
			"Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen",
			"Eighteen", "Nineteen" };

	public static final String[] tens = { 
			"", 		// 0
			"",		// 1
			"Twenty", 	// 2
			"Thirty", 	// 3
			"Forty", 	// 4
			"Fifty", 	// 5
			"Sixty", 	// 6
			"Seventy",	// 7
			"Eighty", 	// 8
			"Ninety" 	// 9
	};
	public JTextField project_no;
	private JTextField qty;
	private JTextField driver_amount;
	private JTextField vehicle_no;

	public static String convert(final int n) {
		if (n < 0) {
			return "Minus " + convert(-n);
		}

		if (n < 20) {
			return units[n];
		}

		if (n < 100) {
			return tens[n / 10] + ((n % 10 != 0) ? " " : "") + units[n % 10];
		}

		if (n < 1000) {
			return units[n / 100] + " Hundred" + ((n % 100 != 0) ? " " : "") + convert(n % 100);
		}

		if (n < 100000) {
			return convert(n / 1000) + " Thousand" + ((n % 10000 != 0) ? " " : "") + convert(n % 1000);
		}

		if (n < 10000000) {
			return convert(n / 100000) + " Lakh" + ((n % 100000 != 0) ? " " : "") + convert(n % 100000);
		}

		return convert(n / 10000000) + " Crore" + ((n % 10000000 != 0) ? " " : "") + convert(n % 10000000);
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
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_order frame = new Add_order();
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
	public Add_order() {


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
	        setBounds((w-1000)/2, (h-587)/2, 1000, 587);
			getContentPane().setLayout(null);
			setClosable(true);
			
			DecimalFormat dc=new DecimalFormat("0.00");
			
		
		JLabel lblQuotation = new JLabel("Order");
		lblQuotation.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblQuotation.setBounds(410, 10, 126, 25);
		getContentPane().add(lblQuotation);

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
		
		order_no = new JTextField();
		order_no.setForeground(Color.RED);
		order_no.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		order_no.setBounds(141, 9, 156, 25);
		getContentPane().add(order_no);
		
		try
		{
			//CREATE TABLE sqlite_sequence(name,seq)
			int m=0;
			
			String queryf = "select * from Order1 where Company='"+company+"'";
		    PreparedStatement pmtf =  connection.prepareStatement(queryf);
		    ResultSet rsf = pmtf.executeQuery();
		    while(rsf.next())
		    {
		    	m=rsf.getInt("Order_no");
		    }
		    rsf.close();
		    
		    
            
		    if(m==0)
		    {
		    	order_no.setText("1");
				    
		    }
		    else
		    {
		    	order_no.setText(Double.toString(m+1)); 	
		    }
           
		    
		}
        catch (Exception ae) {
		    ae.printStackTrace();
		}

		JLabel lblClientCategory = new JLabel("Client Name:*");
		lblClientCategory.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblClientCategory.setBounds(10, 46, 135, 25);
		getContentPane().add(lblClientCategory);
		
		JComboBox client_name = new JComboBox();
		client_name.addItem("SELECT");
		client_name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		client_name.setBounds(141, 46, 823, 25);
		getContentPane().add(client_name);
		
		try
		{
        	String query = "select * from Client where Company='"+company+"'  order by Name ASC";
		    PreparedStatement pmt =  connection.prepareStatement(query);
		    ResultSet rs = pmt.executeQuery();
		    while(rs.next())
		    {
		    	client_name.addItem(rs.getString("Name"));
		    }
		    rs.close();
		}
        catch (Exception ae) {
		    ae.printStackTrace();
        }
		
		
		chadate = new JTextField();
		chadate.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		chadate.setColumns(10);
		chadate.setBounds(751, 10, 210, 25);
		getContentPane().add(chadate);
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		// Get the date today using Calendar object.
		Date today99 = Calendar.getInstance().getTime();        
		String date99 = df.format(today99);
		chadate.setText(date99);
		
		
		
		
		JLabel lblMepfService = new JLabel("Product:*");
		lblMepfService.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblMepfService.setBounds(10, 82, 121, 25);
		getContentPane().add(lblMepfService);

		JLabel label_3 = new JLabel("Rate:*");
		label_3.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_3.setBounds(10, 118, 89, 25);
		getContentPane().add(label_3);

		JComboBox product = new JComboBox();
		product.addItem("SELECT");
		product.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		product.setBounds(141, 82, 823, 25);
		getContentPane().add(product);
		
		try
		{
        	String query = "select * from Product where Company='"+company+"'  order by Name ASC";
		    PreparedStatement pmt =  connection.prepareStatement(query);
		    ResultSet rs = pmt.executeQuery();
		    while(rs.next())
		    {
		    	product.addItem(rs.getString("Name"));
		    }
		    rs.close();
		}
        catch (Exception ae) {
		    ae.printStackTrace();
		}

		rate = new JTextField();
		rate.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
			
				char c = arg0.getKeyChar();
                if((Character.isDigit(c) || (c==KeyEvent.VK_BACK_SPACE) || (c==KeyEvent.VK_DELETE) || (c=='.'))){
                	rate.setText((rate.getText()));
                    arg0.consume();
                }
                
				
			}
		});
		rate.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		rate.setText("0");
		rate.setColumns(10);
		rate.setBounds(141, 118, 270, 25);
		getContentPane().add(rate);

		JButton button = new JButton("Add");
		button.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		button.setBounds(363, 154, 89, 25);
		getContentPane().add(button);
		
		JButton button_1 = new JButton("Delete");
		button_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		button_1.setBounds(474, 154, 89, 25);
		getContentPane().add(button_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 190, 954, 177);
		getContentPane().add(scrollPane);
		
		TableModel model = null;
		table = new JTable(model){

			private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int rowIndex, int colIndex) {
	            return false; //Disallow the editing of any cell
	            }
	            };
		table.setFont(new Font("Book Antiqua", Font.PLAIN, 12));

		scrollPane.setViewportView(table);
		table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                int r = table.getSelectedRow();
                String i = (table.getModel().getValueAt(r,0).toString());
                id = Integer.parseInt(i);
            }
        });
		
		qty = new JTextField();
		qty.setText("0");
		qty.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		qty.setColumns(10);
		qty.setBounds(678, 118, 270, 25);
		getContentPane().add(qty);
		
		JLabel lblQty = new JLabel("Qty:*");
		lblQty.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblQty.setBounds(547, 118, 89, 25);
		getContentPane().add(lblQty);

		JLabel lblVehicleNo = new JLabel("Vehicle No.:*");
		lblVehicleNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblVehicleNo.setBounds(10, 427, 121, 25);
		getContentPane().add(lblVehicleNo);
		
		vehicle_no = new JTextField();
		vehicle_no.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		vehicle_no.setColumns(10);
		vehicle_no.setBounds(146, 425, 268, 25);
		getContentPane().add(vehicle_no);
		
		JLabel lblDriverType = new JLabel("Driver Type:*");
		lblDriverType.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblDriverType.setBounds(10, 392, 121, 25);
		getContentPane().add(lblDriverType);
		
		driver_amount = new JTextField();
		driver_amount.setEnabled(false);
		driver_amount.setText("0");
		driver_amount.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		driver_amount.setColumns(10);
		driver_amount.setBounds(693, 427, 268, 25);
		getContentPane().add(driver_amount);
		
		JComboBox driver_type = new JComboBox();
		driver_type.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(driver_type.getSelectedItem().toString().equals("Salary"))
				{
					driver_amount.setEnabled(false);
					driver_amount.setText("0");
				}
				else
				{
					driver_amount.setEnabled(true);
				}
			}
		});
		driver_type.addItem("Salary");
		driver_type.addItem("Trip");
		driver_type.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		driver_type.setBounds(141, 392, 270, 25);
		getContentPane().add(driver_type);
		
		JComboBox driver_name = new JComboBox();
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
		driver_name.addItem("SELECT");
		driver_name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		driver_name.setBounds(694, 392, 270, 25);
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
		
		JLabel lblDriverName = new JLabel("Driver Name:*");
		lblDriverName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblDriverName.setBounds(547, 392, 137, 25);
		getContentPane().add(lblDriverName);
		
		JLabel lblAmount = new JLabel("Amount:*");
		lblAmount.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAmount.setBounds(547, 429, 89, 25);
		getContentPane().add(lblAmount);
		
		
		
		try
		{
			 String query11="delete from Order_temp";
             PreparedStatement pmt11 = connection.prepareStatement(query11);
             pmt11.executeUpdate();
             pmt11.close();
             
             String query12="select * from Order_temp";
             PreparedStatement pmt12 = connection.prepareStatement(query12);
             ResultSet rs12 = pmt12.executeQuery();
             table.setModel(DbUtils.resultSetToTableModel(rs12));
             pmt12.close();
             rs12.close();
             
             javax.swing.table.TableColumn col = null;
             for(int j1=0;j1<5;j1++)
             {
                 col = table.getColumnModel().getColumn(j1);
                 if(j1==0)
                 {
                     col.setPreferredWidth(0);
                 }
                 else if(j1==1)
                 {
                     col.setPreferredWidth(300);
                 }
                 else
                 {
                     col.setPreferredWidth(100);
                 }
             }
             
             /*DefaultTableCellRenderer right=new DateRenderer1();
             right.setHorizontalAlignment(SwingConstants.RIGHT);
             
             DefaultTableCellRenderer right1=new DefaultTableCellRenderer();
             right1.setHorizontalAlignment(SwingConstants.RIGHT);
             
             DefaultTableCellRenderer left=new DefaultTableCellRenderer();
             left.setHorizontalAlignment(SwingConstants.LEFT);
             
             DefaultTableCellRenderer center=new DefaultTableCellRenderer();
             center.setHorizontalAlignment(SwingConstants.CENTER);
             
             DefaultTableCellRenderer vi=new DefaultTableCellRenderer();
             vi.setVisible(false);
            */
           
             Dimension d=new Dimension(10,0);
             
             table.setIntercellSpacing(d);
             //table.removeColumn(table.getColumn(1));     
             
             /*table.getColumnModel().getColumn(0).setCellRenderer(center);
             table.getColumnModel().getColumn(2).setCellRenderer(right);
             table.getColumnModel().getColumn(3).setCellRenderer(right);
             table.getColumnModel().getColumn(4).setCellRenderer(right1);
             table.getColumnModel().getColumn(5).setCellRenderer(right1);
             table.getColumnModel().getColumn(6).setCellRenderer(right1);
             table.getColumnModel().getColumn(7).setCellRenderer(right);*/
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
		
		total = new JTextField();
		total.setHorizontalAlignment(SwingConstants.RIGHT);
		total.setForeground(Color.RED);
		total.setEditable(false);
		total.setText("0.00");
		total.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		total.setColumns(10);
		total.setBounds(736, 521, 228, 25);
		getContentPane().add(total);
		
		JLabel label_4 = new JLabel("Remark:");
		label_4.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_4.setBounds(10, 486, 60, 25);
		getContentPane().add(label_4);
		
		remark = new JTextField();
		remark.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		remark.setColumns(10);
		remark.setBounds(94, 486, 867, 25);
		getContentPane().add(remark);
		
		JButton button_2 = new JButton("Submit");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					if(total.getText().equals("0")||total.getText().equals("0.0")||driver_name.getSelectedItem().toString().equals(""))
					{
						JOptionPane.showMessageDialog(null, "Please Fill Details");
					}
					else
					{
						total.setText((total.getText()));
						try
						{
							ArrayList<String> product1 = new ArrayList<String>();
							ArrayList<String> qty1 = new ArrayList<String>();
						 	ArrayList<String> rate1 = new ArrayList<String>();
			                ArrayList<String> total1 = new ArrayList<String>();

			                String query0 = "select * from Order_temp";
		                    PreparedStatement pmt0 = connection.prepareStatement(query0);
		                    ResultSet rs0 = pmt0.executeQuery();
		                    while(rs0.next())
		                    {
		                   	 	product1.add(rs0.getString("Product"));
		                   	 	qty1.add(rs0.getString("Qty"));
		                   	 	rate1.add(rs0.getString("Rate"));
			                   	total1.add(rs0.getString("Total"));
			                }
		                    pmt0.close();
		                    rs0.close();

		                   	String query12="insert into Order1 (Company,order_no,Name,Date,Transport_type,Driver_name,Driver_amount,Remark,Total,Vehicle_no,Status) VALUES ('"+company+"','"+order_no.getText()+"','"+client_name.getSelectedItem().toString()+"','"+chadate.getText()+"','"+driver_type.getSelectedItem().toString()+"','"+driver_name.getSelectedItem().toString()+"','"+driver_amount.getText()+"','"+remark.getText()+"','"+total.getText()+"','"+vehicle_no.getText()+"','Pending')";
		                    PreparedStatement pmt12 = connection.prepareStatement(query12,Statement.RETURN_GENERATED_KEYS);
		                    pmt12.executeUpdate();

		                    int id1=0;

		                    ResultSet rsid = pmt12.getGeneratedKeys();
		                    id1=rsid.getInt(1);
		                    pmt12.close();
		                    rsid.close();

		                    
		                    for(int i=0; i<product1.size();i++)
		                    {
		                        String querydetais="insert into Order_detail (S_id, Product,Rate,Qty,Total) VALUES ('"+id1+"','"+product1.get(i)+"','"+dc.format(Double.parseDouble(rate1.get(i)))+"','"+dc.format(Double.parseDouble(qty1.get(i)))+"','"+dc.format(Double.parseDouble(total1.get(i)))+"')";
		                        PreparedStatement pmtdetails = connection.prepareStatement(querydetais);
		                        pmtdetails.executeUpdate();
		                        pmtdetails.close();
		                    }

		                }
						catch(Exception ae)
						{
							ae.printStackTrace();
						}

							

	    			    Add_order b = new Add_order();
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
		button_2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		button_2.setBounds(459, 521, 89, 25);
		getContentPane().add(button_2);

		JLabel label = new JLabel("Date");
		label.setFont(new Font("Book Antiqua", Font.BOLD, 16));
		label.setBounds(693, 10, 49, 25);
		getContentPane().add(label);

		JLabel lblQuotationNo = new JLabel("Order No.:*");
		lblQuotationNo.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblQuotationNo.setBounds(8, 0, 123, 34);
		getContentPane().add(lblQuotationNo);

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

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					if(client_name.getSelectedItem().toString().equals("SELECT")||product.getSelectedItem().toString().equals("SELECT")||rate.getText().equals("0")||qty.getText().equals("0"))
					{
						JOptionPane.showMessageDialog(null, "Please Fill Details");
					}
					else
					{
						rate.setText((rate.getText()));
						
						double rate1 = Double.parseDouble(rate.getText());
						double qty1 = Double.parseDouble(qty.getText());
                        double total1 = rate1*qty1;

                        

                        
                        
                        double a=total1;

                        int mi=0;
                		String query1 = "select * from Order_temp";
					    PreparedStatement pmt1 =  connection.prepareStatement(query1);
					    ResultSet rs1 = pmt1.executeQuery();
					    while(rs1.next())
					    {
					    	mi=rs1.getInt("ID");
					    }
					    rs1.close();

					    mi=mi+1;

				    	  //String query11="insert into Quotation_temp (ID,Service,Rate,CGST,SGST,IGST,Total) VALUES ('"+mi+"','"+service.getSelectedItem().toString()+"','"+rate.getText()+"','"+cgst1+"','"+sgst1+"','"+igst1+"','"+dc.format(a)+"')";
					    String query11="insert into Order_temp (ID,Product,Rate,Qty,Total) VALUES ('"+mi+"','"+product.getSelectedItem().toString()+"','"+rate.getText()+"','"+qty.getText()+"','"+dc.format(a)+"')";
	                      PreparedStatement pmt11 = connection.prepareStatement(query11);
	                      pmt11.executeUpdate();
	                      pmt11.close();

	                      String query12 = "select * from Order_temp";
	                      PreparedStatement pmt12 = connection.prepareStatement(query12);
	                      ResultSet rs12 = pmt12.executeQuery();
	                      table.setModel(DbUtils.resultSetToTableModel(rs12));
	                      pmt12.close();
	                      rs12.close();
	                      
	                      javax.swing.table.TableColumn col = null;
	                      for(int j1=0;j1<5;j1++)
	                      {
	                          col = table.getColumnModel().getColumn(j1);
	                          if(j1==0)
	                          {
	                              col.setPreferredWidth(0);
	                          }
	                          else if(j1==1)
	                          {
	                              col.setPreferredWidth(300);
	                          }
	                          else
	                          {
	                              col.setPreferredWidth(100);
	                          }
	                      }
	                      
	                      /*DefaultTableCellRenderer right=new DateRenderer1();
	                      right.setHorizontalAlignment(SwingConstants.RIGHT);
	                      
	                      DefaultTableCellRenderer right1=new DefaultTableCellRenderer();
	                      right1.setHorizontalAlignment(SwingConstants.RIGHT);
			                
			                DefaultTableCellRenderer left=new DefaultTableCellRenderer();
			                left.setHorizontalAlignment(SwingConstants.LEFT);
			                
			                DefaultTableCellRenderer center=new DefaultTableCellRenderer();
			                center.setHorizontalAlignment(SwingConstants.CENTER);
			                
			                DefaultTableCellRenderer vi=new DefaultTableCellRenderer();
			                vi.setVisible(false);
			               
			              */
			                Dimension d=new Dimension(10,0);
			                
			                table.setIntercellSpacing(d);
			                //table.removeColumn(table.getColumn(1));     
			                
			                /*table.getColumnModel().getColumn(0).setCellRenderer(center);
			                table.getColumnModel().getColumn(2).setCellRenderer(right);
			                table.getColumnModel().getColumn(3).setCellRenderer(right);
			                table.getColumnModel().getColumn(4).setCellRenderer(right1);
			                table.getColumnModel().getColumn(5).setCellRenderer(right1);
			                table.getColumnModel().getColumn(6).setCellRenderer(right1);
			                table.getColumnModel().getColumn(7).setCellRenderer(right);
*/
	                      double k=0;
	                      String query13 = "select sum(Total) as k1 from Order_temp";
						  PreparedStatement pmt13 =  connection.prepareStatement(query13);
						  ResultSet rs13 = pmt13.executeQuery();
						  while(rs13.next())
						  {
							  k=rs13.getDouble("k1");
			              }
						  rs13.close();

						  total.setText((dc.format(k)));
					    
						  product.setSelectedIndex(0);
						  rate.setText("0");
						  qty.setText("0");
						  product.requestFocus();
						  
					}//sq ft else end
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
		});
		
		rate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					if(client_name.getSelectedItem().toString().equals("SELECT")||product.getSelectedItem().toString().equals("SELECT")||rate.getText().equals("0")||qty.getText().equals("0"))
					{
						JOptionPane.showMessageDialog(null, "Please Fill Details");
					}
					else
					{
						rate.setText((rate.getText()));
						
						double rate1 = Double.parseDouble(rate.getText());
						double qty1 = Double.parseDouble(qty.getText());
                        double total1 = rate1*qty1;

                        
                        double a=total1;

                        int mi=0;
                		String query1 = "select * from Order_temp";
					    PreparedStatement pmt1 =  connection.prepareStatement(query1);
					    ResultSet rs1 = pmt1.executeQuery();
					    while(rs1.next())
					    {
					    	mi=rs1.getInt("ID");
					    }
					    rs1.close();

					    mi=mi+1;

				    	  //String query11="insert into Quotation_temp (ID,Service,Rate,CGST,SGST,IGST,Total) VALUES ('"+mi+"','"+service.getSelectedItem().toString()+"','"+rate.getText()+"','"+cgst1+"','"+sgst1+"','"+igst1+"','"+dc.format(a)+"')";
					    String query11="insert into Order_temp (ID,Product,Rate,Qty,Total) VALUES ('"+mi+"','"+product.getSelectedItem().toString()+"','"+rate.getText()+"','"+qty.getText()+"','"+dc.format(a)+"')";
	                      PreparedStatement pmt11 = connection.prepareStatement(query11);
	                      pmt11.executeUpdate();
	                      pmt11.close();

	                      String query12 = "select * from Order_temp";
	                      PreparedStatement pmt12 = connection.prepareStatement(query12);
	                      ResultSet rs12 = pmt12.executeQuery();
	                      table.setModel(DbUtils.resultSetToTableModel(rs12));
	                      pmt12.close();
	                      rs12.close();
	                      
	                      javax.swing.table.TableColumn col = null;
	                      for(int j1=0;j1<5;j1++)
	                      {
	                          col = table.getColumnModel().getColumn(j1);
	                          if(j1==0)
	                          {
	                              col.setPreferredWidth(0);
	                          }
	                          else if(j1==1)
	                          {
	                              col.setPreferredWidth(300);
	                          }
	                          else
	                          {
	                              col.setPreferredWidth(100);
	                          }
	                      }
	                      
	                      /*DefaultTableCellRenderer right=new DateRenderer1();
	                      right.setHorizontalAlignment(SwingConstants.RIGHT);
	                      
	                      DefaultTableCellRenderer right1=new DefaultTableCellRenderer();
	                      right1.setHorizontalAlignment(SwingConstants.RIGHT);
			                
			                DefaultTableCellRenderer left=new DefaultTableCellRenderer();
			                left.setHorizontalAlignment(SwingConstants.LEFT);
			                
			                DefaultTableCellRenderer center=new DefaultTableCellRenderer();
			                center.setHorizontalAlignment(SwingConstants.CENTER);
			                
			                DefaultTableCellRenderer vi=new DefaultTableCellRenderer();
			                vi.setVisible(false);
			               
			              */
			                Dimension d=new Dimension(10,0);
			                
			                table.setIntercellSpacing(d);
			                //table.removeColumn(table.getColumn(1));     
			                
			                /*table.getColumnModel().getColumn(0).setCellRenderer(center);
			                table.getColumnModel().getColumn(2).setCellRenderer(right);
			                table.getColumnModel().getColumn(3).setCellRenderer(right);
			                table.getColumnModel().getColumn(4).setCellRenderer(right1);
			                table.getColumnModel().getColumn(5).setCellRenderer(right1);
			                table.getColumnModel().getColumn(6).setCellRenderer(right1);
			                table.getColumnModel().getColumn(7).setCellRenderer(right);
*/
	                      double k=0;
	                      String query13 = "select sum(Total) as k1 from Sale_temp";
						  PreparedStatement pmt13 =  connection.prepareStatement(query13);
						  ResultSet rs13 = pmt13.executeQuery();
						  while(rs13.next())
						  {
							  k=rs13.getDouble("k1");
			              }
						  rs13.close();

						  total.setText((dc.format(k)));
					    
						  product.setSelectedIndex(0);
						  rate.setText("0");
						  qty.setText("0");
						  product.requestFocus();
						  
					}//sq ft else end
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
		});

		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					String query11="delete from Order_temp where ID='"+id+"'";
                    PreparedStatement pmt11 = connection.prepareStatement(query11);
                    pmt11.executeUpdate();
                    pmt11.close();

                    String query12 = "select * from Order_temp";
                    PreparedStatement pmt12 = connection.prepareStatement(query12);
                    ResultSet rs12 = pmt12.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs12));
                    pmt12.close();
                    rs12.close();
                    
                    javax.swing.table.TableColumn col = null;
                    for(int j1=0;j1<5;j1++)
                    {
                        col = table.getColumnModel().getColumn(j1);
                        if(j1==0)
                        {
                            col.setPreferredWidth(0);
                        }
                        else if(j1==1)
                        {
                            col.setPreferredWidth(300);
                        }
                        else
                        {
                            col.setPreferredWidth(100);
                        }
                    }
                    
                      Dimension d=new Dimension(10,0);
	                
	                table.setIntercellSpacing(d);
	                //table.removeColumn(table.getColumn(1));     
	                
	                double k=0;

                    String query13 = "select sum(Total) as k1 from Order_temp";
				    PreparedStatement pmt13 =  connection.prepareStatement(query13);
				    ResultSet rs13 = pmt13.executeQuery();
				    while(rs13.next())
				    {
				    	k=rs13.getDouble("k1");
	                }
				    rs13.close();
				    
				    double a=k;

				    total.setText((dc.format(a)));
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
		});
		
		table.setAutoCreateRowSorter(true);
	}
}