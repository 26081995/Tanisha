import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
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

import net.proteanit.sql.DbUtils;

public class View_sale extends JInternalFrame {
	
	private JTextField textField;
	private JTable table;
	public int id;
	String company=null,date_from=null,date_to=null,pdf=null;
	
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
	
	class DateRenderer extends DefaultTableCellRenderer 
	{
		private static final long serialVersionUID = 1L;
		private Date dateValue;
		private SimpleDateFormat sdfNewValue = new SimpleDateFormat("dd-MM-yyyy");
		private String valueToString = "";

		@Override
		public void setValue(Object value) {
		    if ((value != null)) {
		        String stringFormat = value.toString();
		        try {
		            dateValue = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(stringFormat);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        valueToString = sdfNewValue.format(dateValue);
		        value = valueToString;
		    }
		    super.setValue(value);
		}
		}
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_sale frame = new View_sale();
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
	public View_sale() {
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
	        setBounds((w-1300)/2, (h-502)/2, 1300, 502);
			getContentPane().setLayout(null);
			setClosable(true);

			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setBounds(10, 0, 1264, 462);
			getContentPane().add(panel);

			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 106, 1244, 345);
			panel.add(scrollPane);

			final Connection connection = Databaseconnection.connection2();

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
			
			
			DecimalFormat dc=new DecimalFormat("0.00");

			TableModel model = null;
			table = new JTable(model){
				private static final long serialVersionUID = 1L;

					public boolean isCellEditable(int rowIndex, int colIndex) {
		            return false; //Disallow the editing of any cell
		            }
		            };
			table.setFont(new Font("Book Antiqua", Font.PLAIN, 12));
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {

					 int r = table.getSelectedRow();
	                 String i = (table.getModel().getValueAt(r,0).toString());
	                 id = Integer.parseInt(i);

	                 try
	                 {
                	 	String query = "delete from Sale_id";
	                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
	                    pmt.executeUpdate();
	                    pmt.close();

	                    String query1 = "Insert Into Sale_id(ID) Values ('"+id+"')";
	                    java.sql.PreparedStatement pmt1 = connection.prepareStatement(query1);
	                    pmt1.executeUpdate();
	                    pmt1.close();
	                 }
	                 catch(Exception ae)
	                 {
	                	 ae.printStackTrace();
	                 }
				}
			});
		            try 
		            {
		                String query = "select ID,Sale_type,Sale_no,Name,Date,CGST,SGST,IGST,Total from Sale where Company='"+company+"'";
		                java.sql.PreparedStatement pmt = connection.prepareStatement(query);
		                ResultSet rs = pmt.executeQuery();
		                table.setModel(DbUtils.resultSetToTableModel(rs));
		                pmt.close();
		                rs.close();
		                
		                javax.swing.table.TableColumn col = null;
	                    for(int j=0;j<9;j++)
	                    {
	                        col = table.getColumnModel().getColumn(j);
	                        if(j==0)
	                        {
	                            col.setPreferredWidth(0);
	                        }
	                        else if(j==3)
	                        {
	                            col.setPreferredWidth(200);
	                        }
	                        else if(j==2)
	                        {
	                            col.setPreferredWidth(150);
	                        }
	                        else
	                        {
	                            col.setPreferredWidth(100);
	                        }
	                    }
		                
		               
		                DefaultTableCellRenderer dcenter=new DateRenderer();
		                dcenter.setHorizontalAlignment(SwingConstants.CENTER);
		                
		                /*DefaultTableCellRenderer right=new DateRenderer1();
		                right.setHorizontalAlignment(SwingConstants.RIGHT);
		                
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
		                table.getColumnModel().getColumn(4).setCellRenderer(dcenter);
		                
		            }
		            catch (SQLException ae) {
		                ae.printStackTrace();
		            }
		            scrollPane.setViewportView(table);

			textField = new JTextField();
			textField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					try 
					{
	                    String query = "select ID,Sale_type,Sale_no,Name,Date,CGST,SGST,IGST,Total from Sale where (Sale_type like '%"+textField.getText()+"%' or Sale_no like '%"+textField.getText()+"%'  or Name like '%"+textField.getText()+"%' or Date like '%"+textField.getText()+"%'  or Total like '%"+textField.getText()+"%') and Company='"+company+"'";
	                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
	                    ResultSet rs = pmt.executeQuery();
	                    table.setModel(DbUtils.resultSetToTableModel(rs));
	                    pmt.close();
	                    rs.close();
	                    
	                    
	                    javax.swing.table.TableColumn col = null;
	                    for(int j=0;j<9;j++)
	                    {
	                        col = table.getColumnModel().getColumn(j);
	                        if(j==0)
	                        {
	                            col.setPreferredWidth(0);
	                        }
	                        else if(j==3)
	                        {
	                            col.setPreferredWidth(200);
	                        }
	                        else if(j==2)
	                        {
	                            col.setPreferredWidth(150);
	                        }
	                        else
	                        {
	                            col.setPreferredWidth(100);
	                        }
	                    }

	                    DefaultTableCellRenderer dcenter=new DateRenderer();
		                dcenter.setHorizontalAlignment(SwingConstants.CENTER);
		                
		                /*DefaultTableCellRenderer right=new DateRenderer1();
		                right.setHorizontalAlignment(SwingConstants.RIGHT);
		                
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
		                table.getColumnModel().getColumn(4).setCellRenderer(dcenter);
		                
	                }
					catch (SQLException ae) {
	                    ae.printStackTrace();
	                }
				}
			});
			textField.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
			textField.setColumns(10);
			textField.setBounds(552, 40, 146, 25);
			panel.add(textField);

			JLabel lblViewExtraHour = new JLabel("Sale");
			lblViewExtraHour.setFont(new Font("Tahoma", Font.BOLD, 18));
			lblViewExtraHour.setBounds(574, 0, 129, 25);
			panel.add(lblViewExtraHour);

			JButton button = new JButton("Delete");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try
	                {
						
	                    
	                    	int dialogButton = JOptionPane.YES_NO_OPTION;
							int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
							if(dialogResult == JOptionPane.YES_OPTION)
							{
								String sale_type=null,name=null;
								double total=0;
								
								String query1 = "select * from Sale where  Company='"+company+"' and ID='"+id+"'";
	    	                    PreparedStatement pmt1 = connection.prepareStatement(query1);
	    	                    ResultSet rs1 = pmt1.executeQuery();
	    	                    while(rs1.next())
	    	                    {
	    	                    	//Project_name
	    	                    	name=rs1.getString("Name");
	    	                    	total=rs1.getDouble("Total");
	    	                    	sale_type=rs1.getString("Sale_type");
	    	        	        }
	    	                    pmt1.close();
	    	                    rs1.close();
	    	                    
	    	                    if(sale_type.equals("Account"))
	    	                    {
	    	                    	String query123="update Client set Balance=Balance - '"+total+"' where Name='"+name+"' and Company='"+company+"'";
				                    PreparedStatement pmt123 = connection.prepareStatement(query123);
				                    pmt123.executeUpdate();
	    	                    }
	    	                    
	    	                    ArrayList<String> product1 = new ArrayList<String>();
								ArrayList<String> qty1 = new ArrayList<String>();
							 	ArrayList<String> rate1 = new ArrayList<String>();
				                ArrayList<String> total1 = new ArrayList<String>();

				                String query0 = "select * from Sale_detail where S_id='"+id+"'";
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
			                    
			                    for(int i=0; i<product1.size();i++)
			                    {
			                        
			                        String query123="update Product set Stock=Stock + '"+qty1.get(i)+"' where Name='"+product1.get(i)+"' and Company='"+company+"'";
				                    PreparedStatement pmt123 = connection.prepareStatement(query123);
				                    pmt123.executeUpdate();
			                    }
								
			                    String query = "delete from Sale where ID="+id;
			                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
			                    pmt.executeUpdate();
			                    pmt.close();

			                    String query1q = "delete from Sale_detail where S_id="+id;
			                    java.sql.PreparedStatement pmt1q = connection.prepareStatement(query1q);
			                    pmt1q.executeUpdate();
			                    pmt1q.close();

			                    View_sale b = new View_sale();
				                JDesktopPane desktopPane = getDesktopPane();
				                desktopPane.add(b);
				                b.show();
				                dispose();
							}
	                    
					}
	                catch (SQLException ae)
	                {
	                    ae.printStackTrace();
	                }
				}
			});
			button.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
			button.setBounds(1165, 63, 89, 25);
			panel.add(button);

			JButton btnDetail = new JButton("Detail");
			btnDetail.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try
	                {
						Detail_sale s = new Detail_sale();
	                    String date=null;
	                    
	                    

	                    String query3 = "select * from Sale where ID ='"+id+"'";
	                    java.sql.PreparedStatement pmt3 = connection.prepareStatement(query3);
	                    ResultSet rs3 = pmt3.executeQuery();
	                    while(rs3.next())
	                    {
	                    	s.total.setText((rs3.getString("Total")));
	                    	s.no.setText(rs3.getString("Sale_no"));
	                    	s.name.setText(rs3.getString("Name"));
	                    	s.remark.setText(rs3.getString("Remark"));
	                    	s.transport_type.setText(rs3.getString("Transport_type"));
	                    	s.driver_name.setText(rs3.getString("Driver_name"));
	                    	s.amount.setText(rs3.getString("Driver_amount"));
	                    	s.vehicle_no.setText(rs3.getString("Vehicle_no"));
	                    }
	                    pmt3.close();
	                    rs3.close();
	                    
	                    String cost=null;
	                    
	                    
	                    String query = "select ID,Product,Rate,Qty,Total from Sale_detail where S_id ="+id+"";
	                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
	                    ResultSet rs = pmt.executeQuery();
	                    s.table.setModel(DbUtils.resultSetToTableModel(rs));

	                    javax.swing.table.TableColumn col = null;
	                    for(int j=0;j<5;j++)
	                    {
	                        col = s.table.getColumnModel().getColumn(j);
	                        if(j==0)
	                        {
	                            col.setPreferredWidth(0);
	                        }
	                        else if(j==1)
	                        {
	                            col.setPreferredWidth(300);
	                        }
	                        else
	                        {
	                            col.setPreferredWidth(100);
	                        }
	                    }

	                    
		                
		              
		                Dimension d=new Dimension(10,0);
		                
		                s.table.setIntercellSpacing(d);
		                //table.removeColumn(table.getColumn(1));     
		                
		                JDesktopPane desktopPane = getDesktopPane();
	                    desktopPane.add(s);
	                    s.show();
	                    pmt.close();
	                    rs.close();
	                }
					catch(Exception ae)
					{
						ae.printStackTrace();
					}
				}
			});
			btnDetail.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
			btnDetail.setBounds(34, 63, 89, 25);
			panel.add(btnDetail);

			JLabel label = new JLabel("Search");
			label.setFont(new Font("Book Antiqua", Font.BOLD, 16));
			label.setBounds(479, 40, 63, 25);
			panel.add(label);
			
			table.setAutoCreateRowSorter(true);
			
			JButton btnPrint = new JButton("Print");
			btnPrint.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try
					{

						String name=null,date=null,mobile=null,sale_no=null;
	                    String rem3=null,mob=null,driver=null,driver_no=null;
	                    double total=0,exch2=0,cgst1=0,sgst1=0,igst1=0;
	                    int id12=0;

	                    String query1 = "select * from Sale where  Company='"+company+"'";
	                    PreparedStatement pmt1 = connection.prepareStatement(query1);
	                    ResultSet rs1 = pmt1.executeQuery();
	                    while(rs1.next())
	                    {
	                    	//Project_name
	                    	id12=rs1.getInt("ID");
	                    	sale_no=rs1.getString("Sale_no");
	                    	name=rs1.getString("Name");
	                    	date=rs1.getString("Date");
	                    	total=rs1.getDouble("Total");
	                    	rem3=rs1.getString("Remark");
	                    	driver=rs1.getString("Driver_name");
	                    	cgst1=rs1.getDouble("CGST");
	                    	sgst1=rs1.getDouble("SGST");
	                    	igst1=rs1.getDouble("IGST");
	        	        }
	                    pmt1.close();
	                    rs1.close();
	                    
	                    String query1w = "select * from Driver where  Company='"+company+"' and Name='"+driver+"'";
	                    PreparedStatement pmt1w = connection.prepareStatement(query1w);
	                    ResultSet rs1w = pmt1w.executeQuery();
	                    while(rs1w.next())
	                    {
	                    	//Project_name
	                    	driver_no=rs1w.getString("Vehicle_no");
	        	        }
	                    pmt1w.close();
	                    rs1w.close();
	                    
	                    
	                    ArrayList<String> product1 = new ArrayList<String>();
						ArrayList<Double> total1 = new ArrayList<Double>();
						ArrayList<Double> rate1 = new ArrayList<Double>();
						ArrayList<Double> qty1 = new ArrayList<Double>();
		                
		                double sub_total=0;

						String query0 = "select * from Sale_detail where S_id='"+id12+"'";
	                    PreparedStatement pmt0 = connection.prepareStatement(query0);
	                    ResultSet rs0 = pmt0.executeQuery();
	                    while(rs0.next())
	                    {
	                    	product1.add(rs0.getString("Product"));
		                   	total1.add(rs0.getDouble("Total"));
		                   	rate1.add(rs0.getDouble("Rate"));
		                   	qty1.add(rs0.getDouble("Qty"));
		                   	
	                    }
	                    pmt0.close();
	                    rs0.close();
	                    
	                   
	                    
	                    String cname = null, caddress1 = null, caddress2 = null, cmobile1 = null, clandline = null,
	                    		clandline1 = null, cstate = null, ccity = null, cno1 = null, cemail = null ,cwebsite = null, cgst = null, cpanno = null;;

	                    String query11 = "select * from Client where Name='"+name+"' and Company='"+company+"'";
	                    PreparedStatement pmt11 = connection.prepareStatement(query11);
	                    ResultSet rs11 = pmt11.executeQuery();
	                    while(rs11.next())
	                    {
	                    	cname = rs11.getString("Name");
							caddress1 = rs11.getString("Address1");
							caddress2 = rs11.getString("Address2");
							cstate = rs11.getString("State");
							ccity = rs11.getString("City");
							cno1 = rs11.getString("Mobile");
							cmobile1 = rs11.getString("Mobile1");
							clandline = rs11.getString("Landline");
							clandline1 = rs11.getString("Landline1");
							cemail = rs11.getString("Email");
							cwebsite = rs11.getString("Website");
							cgst = rs11.getString("GST");
							cpanno = rs11.getString("Pan_no");
	                    }
	                    pmt11.close();
	                    rs11.close();

                    	byte[] img = null,sign=null;
						String companame1 = null, address1 = null, address2 = null, mobile1 = null, landline = null,
							   landline1 = null, state = null, city = null, no1 = null, email = null ,website = null, gst = null, panno = null;

						String query51580 = "select * from Company where Name='"+company+"'";
						PreparedStatement pmt51580 = connection.prepareStatement(query51580);
						ResultSet rs5580 = pmt51580.executeQuery();
						while (rs5580.next()) 
						{
							companame1 = rs5580.getString("Name");
							address1 = rs5580.getString("Address1");
							address2 = rs5580.getString("Address2");
							state = rs5580.getString("State");
							city = rs5580.getString("City");
							no1 = rs5580.getString("Mobile");
							mobile1 = rs5580.getString("Mobile1");
							landline = rs5580.getString("Landline");
							landline1 = rs5580.getString("Landline1");
							email = rs5580.getString("Email");
							website = rs5580.getString("Website");
							gst = rs5580.getString("GST");
							panno = rs5580.getString("Pan_no");
							img = rs5580.getBytes("Logo");
							sign = rs5580.getBytes("Signature");
						}
						pmt51580.close();
						rs5580.close();
						
						Document doc;
						doc = new Document(PageSize.A4, 50, 50, 30, 30);
						
						
						
						    
			             
			        		 
			        		 Date startDate = null;
						     //String newDateString=null,newDateString1=null;
						     try {
						     	SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
						         startDate = sm.parse(date);
						     } catch (ParseException ae) {
						         ae.printStackTrace();
						     }

						     SimpleDateFormat sm2 = new SimpleDateFormat("dd-MM-yyyy");
						     String strDate = sm2.format(startDate);
						     
						     
			        		 
						
						
						
						PdfWriter writer1 = PdfWriter.getInstance(doc, new FileOutputStream("Sale.pdf"));
						writer1.setBoxSize("art", new Rectangle(36, 54, 559, 788));

						doc.open();
						
						PdfPCell cell;
						PdfPCell blankcell = new PdfPCell(new Phrase(" "));
						blankcell.setBorder(Rectangle.NO_BORDER);
						Paragraph p;

						PdfPTable table1 = new PdfPTable(3);
						table1.setWidthPercentage(100);
						table1.setWidths(new int[] { 2,5,4});
						
						BaseFont bf,bf1,bf11;
	                    com.itextpdf.text.Font font,font5;
	                    bf = BaseFont.createFont("book_b.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
	                    font = new com.itextpdf.text.Font(bf, 20);
	                    font5 = new com.itextpdf.text.Font(bf, 22);
						
						p = new Paragraph("Sale",font);
						cell = new PdfPCell(p);
						cell.setColspan(2);
						cell.setUseBorderPadding(true);
						cell.setVerticalAlignment(Element.ALIGN_LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setBorderWidthTop(0f);
						cell.setBorderWidthRight(0);
						cell.setBorderColor(BaseColor.RED);
						cell.setBorderWidthLeft(5f);
						cell.setBorderWidthBottom(0);
						table1.addCell(cell);
						
						
						 p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,10,Font.PLAIN,BaseColor.BLACK));
                         cell = new PdfPCell(p);
                         cell.setRowspan(2);
                         cell.setUseBorderPadding(true);
                         cell.setVerticalAlignment(Element.ALIGN_CENTER);
                         cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                         cell.setBorderWidthTop(0f);
                         cell.setBorderWidthRight(0f);
                         cell.setBorderWidthLeft(0f);
                         cell.setBorderWidthBottom(0);
                         cell.setFixedHeight(40);
                         table1.addCell(cell);
						
						/*if(img!=null) {
      				    	 OutputStream targetfile=new FileOutputStream("Image/Logo.png");
   	                    	targetfile.write(img);
   	                    	targetfile.close();

   	                    	Image image = Image.getInstance("Image/Logo.png");
   	                        image.setScaleToFitHeight(true);

                        	cell = new PdfPCell(image);
                        	cell.setRowspan(2);
                        	cell.setUseBorderPadding(true);
                           cell.setVerticalAlignment(Element.ALIGN_CENTER);
                           cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                           cell.setBorderWidthTop(0f);
                           cell.setBorderWidthRight(0f);
                           cell.setBorderWidthLeft(0f);
                           cell.setBorderWidthBottom(0f);
                           cell.setFixedHeight(40);
                           table1.addCell(cell);
      				    }
      				    else
      				    {
      				    	 p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,10,Font.PLAIN,BaseColor.BLACK));
                           cell = new PdfPCell(p);
                           cell.setRowspan(2);
                           cell.setUseBorderPadding(true);
                           cell.setVerticalAlignment(Element.ALIGN_CENTER);
                           cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                           cell.setBorderWidthTop(0f);
                           cell.setBorderWidthRight(0f);
                           cell.setBorderWidthLeft(0f);
                           cell.setBorderWidthBottom(0);
                           cell.setFixedHeight(40);
                           table1.addCell(cell);
      				    }*/

						p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,10,Font.PLAIN,BaseColor.BLACK));
                        cell = new PdfPCell(p);
                        cell.setColspan(2);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(0f);
                        cell.setBorderWidthBottom(0);
                        table1.addCell(cell);
                        
                        
						 com.itextpdf.text.Font font1,font11;
						 bf11 = BaseFont.createFont("book_b.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
		                    bf1 = BaseFont.createFont("book.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
		                    font1 = new com.itextpdf.text.Font(bf1,12);
		                    font11 = new com.itextpdf.text.Font(bf11,12);
		                    
		                    
		                    com.itextpdf.text.Font font2,font3;
		                    font2 = new com.itextpdf.text.Font(bf, 12);
		                    font3 = new com.itextpdf.text.Font(bf, 12);
						
						p =new Paragraph("Date:",font1);
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(0f);
                        cell.setBorderWidthBottom(0);
                        table1.addCell(cell);
                        
                        
                        
                        p =new Paragraph(""+strDate,font1);
                        cell = new PdfPCell(p);
                        //cell.setColspan(2);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(0f);
                        cell.setBorderWidthBottom(0);
                        table1.addCell(cell);

                        p =new Paragraph(""+company,font5);
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(0f);
                        cell.setBorderWidthBottom(0);
                        table1.addCell(cell);
                        
                        p =new Paragraph("Sale No.:",font1);
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(0f);
                        cell.setBorderWidthBottom(0);
                        table1.addCell(cell);

                        p =new Paragraph(""+sale_no,font1);
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(0f);
                        cell.setBorderWidthBottom(0);
                        table1.addCell(cell);
                        
                        if(empty(address1))
	                    {
                        	p =new Paragraph(" ",font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);
	                    }
	                    else
	                    {
	                    	p =new Paragraph(""+address1,font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);
	                    }
                        
                        p =new Paragraph(" ",font1);
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(0f);
                        cell.setBorderWidthBottom(0);
                        table1.addCell(cell);

                        p =new Paragraph(" ",font1);
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(0f);
                        cell.setBorderWidthBottom(0);
                        table1.addCell(cell);
                        
                        if(empty(address2))
	                    {
	                    	
	                    }
	                    else
	                    {
	                    	p =new Paragraph(""+address2,font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);
	                    }
                        
                      
                        
                      
	                    
	                    if(empty(state))
	                    {
	                    	
	                    }
	                    else
	                    {
	                    	p =new Paragraph(" ",font1);
	                        cell = new PdfPCell(p);
	                        cell.setColspan(2);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);

	                        p =new Paragraph(""+city+","+state,font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);
	                    }
	                    
	                    if(empty(no1))
	                    {
	                    	
	                    }
	                    else
	                    {
	                    	p =new Paragraph(" ",font1);
	                        cell = new PdfPCell(p);
	                        cell.setColspan(2);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);

	                        p =new Paragraph("+91- "+no1,font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);
	                    }
	                    
	                    if(empty(mobile1))
	                    {
	                    	
	                    }
	                    else
	                    {
	                    	p =new Paragraph(" ",font1);
	                        cell = new PdfPCell(p);
	                        cell.setColspan(2);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);

	                        p =new Paragraph("+91- "+mobile1,font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);
	                    }
	                    
	                    if(empty(gst))
	                    {
	                    	p =new Paragraph(" ",font1);
	                        cell = new PdfPCell(p);
	                        cell.setColspan(2);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);

	                        p =new Paragraph("GST No. : Not Applicable",font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);
	                    }
	                    else
	                    {
	                    	p =new Paragraph(" ",font1);
	                        cell = new PdfPCell(p);
	                        cell.setColspan(2);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);

	                        p =new Paragraph("GST No. : "+gst,font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);
	                    }
	                    
	                    if(empty(panno))
	                    {
	                    	p =new Paragraph(" ",font1);
	                        cell = new PdfPCell(p);
	                        cell.setColspan(2);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);

	                        p =new Paragraph("PAN No. : Not Applicable",font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);
	                    }
	                    else
	                    {
	                    	p =new Paragraph(" ",font1);
	                        cell = new PdfPCell(p);
	                        cell.setColspan(2);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);

	                        p =new Paragraph("PAN No. : "+panno,font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0);
	                        table1.addCell(cell);
	                    }
	                    
	                    /*p =new Paragraph(" ",font1);
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(0f);
                        cell.setBorderWidthBottom(0);
                        table1.addCell(cell);*/

                        /*p =new Paragraph(" ",font1);
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(0f);
                        cell.setBorderWidthBottom(0);
                        table1.addCell(cell);*/
                        
	                    p =new Paragraph(" Sale TO:",font11);
                        cell = new PdfPCell(p);
                        cell.setColspan(3);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderColor(BaseColor.RED);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(5f);
                        cell.setBorderWidthBottom(0);
                        table1.addCell(cell);
                        
                        
                        if(empty(cname))
                        {
                        	
                        }
                        else
                        {
                        	p =new Paragraph(" "+cname,font1);
                            cell = new PdfPCell(p);
                            cell.setColspan(3);
                            cell.setUseBorderPadding(true);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell.setBorderColor(BaseColor.RED);
                            cell.setBorderWidthTop(0f);
                            cell.setBorderWidthRight(0f);
                            cell.setBorderWidthLeft(5f);
                            cell.setBorderWidthBottom(0);
                            table1.addCell(cell);
                        }
                        
                        
                        if(empty(caddress1))
                        {
                        	
                        }
                        else
                        {
                        	p =new Paragraph(" "+caddress1,font1);
                            cell = new PdfPCell(p);
                            cell.setColspan(3);
                            cell.setUseBorderPadding(true);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell.setBorderColor(BaseColor.RED);
                            cell.setBorderWidthTop(0f);
                            cell.setBorderWidthRight(0f);
                            cell.setBorderWidthLeft(5f);
                            cell.setBorderWidthBottom(0);
                            table1.addCell(cell);
                        }
                        
                        if(empty(caddress2))
                        {
                        	
                        }
                        else
                        {
                        	p =new Paragraph(" "+caddress2,font1);
                            cell = new PdfPCell(p);
                            cell.setColspan(3);
                            cell.setUseBorderPadding(true);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell.setBorderColor(BaseColor.RED);
                            cell.setBorderWidthTop(0f);
                            cell.setBorderWidthRight(0f);
                            cell.setBorderWidthLeft(5f);
                            cell.setBorderWidthBottom(0);
                            table1.addCell(cell);
                        }
                        
                        if(empty(ccity))
                        {
                        	
                        }
                        else
                        {
                        	p =new Paragraph(" "+ccity+" , "+cstate,font1);
                            cell = new PdfPCell(p);
                            cell.setColspan(3);
                            cell.setUseBorderPadding(true);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell.setBorderColor(BaseColor.RED);
                            cell.setBorderWidthTop(0f);
                            cell.setBorderWidthRight(0f);
                            cell.setBorderWidthLeft(5f);
                            cell.setBorderWidthBottom(0);
                            table1.addCell(cell);
                        }
                        
                        if(empty(cgst))
                        {
                        	
                        }
                        else
                        {
                        	p =new Paragraph(" GST No. : "+cgst,font1);
                            cell = new PdfPCell(p);
                            cell.setColspan(3);
                            cell.setUseBorderPadding(true);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell.setBorderColor(BaseColor.RED);
                            cell.setBorderWidthTop(0f);
                            cell.setBorderWidthRight(0f);
                            cell.setBorderWidthLeft(5f);
                            cell.setBorderWidthBottom(0);
                            table1.addCell(cell);
                        }
                        
                        if(empty(cpanno))
                        {
                        	
                        }
                        else
                        {
                        	p =new Paragraph(" PAN No. : "+cpanno,font1);
                            cell = new PdfPCell(p);
                            cell.setColspan(3);
                            cell.setUseBorderPadding(true);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell.setBorderColor(BaseColor.RED);
                            cell.setBorderWidthTop(0f);
                            cell.setBorderWidthRight(0f);
                            cell.setBorderWidthLeft(5f);
                            cell.setBorderWidthBottom(0);
                            table1.addCell(cell);
                        }
                        
                        p =new Paragraph(" ",font11);
                        cell = new PdfPCell(p);
                        cell.setColspan(3);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderColor(BaseColor.RED);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(5f);
                        cell.setBorderWidthBottom(0);
                        table1.addCell(cell);
                        
                        p =new Paragraph(" Driver Details:",font11);
                        cell = new PdfPCell(p);
                        cell.setColspan(3);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderColor(BaseColor.RED);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(5f);
                        cell.setBorderWidthBottom(0);
                        table1.addCell(cell);
                        
                        if(empty(driver))
                        {
                        	
                        }
                        else
                        {
                        	p =new Paragraph(" Driver Name : "+driver,font1);
                            cell = new PdfPCell(p);
                            cell.setColspan(3);
                            cell.setUseBorderPadding(true);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell.setBorderColor(BaseColor.RED);
                            cell.setBorderWidthTop(0f);
                            cell.setBorderWidthRight(0f);
                            cell.setBorderWidthLeft(5f);
                            cell.setBorderWidthBottom(0);
                            table1.addCell(cell);
                        }
                        if(empty(driver_no))
                        {
                        	
                        }
                        else
                        {
                        	p =new Paragraph(" Vehicle No. : "+driver_no,font1);
                            cell = new PdfPCell(p);
                            cell.setColspan(3);
                            cell.setUseBorderPadding(true);
                            cell.setVerticalAlignment(Element.ALIGN_CENTER);
                            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                            cell.setBorderColor(BaseColor.RED);
                            cell.setBorderWidthTop(0f);
                            cell.setBorderWidthRight(0f);
                            cell.setBorderWidthLeft(5f);
                            cell.setBorderWidthBottom(0);
                            table1.addCell(cell);
                        }
                        
                        
                        
                        PdfPTable table4 = new PdfPTable(6);
					     table4.setWidthPercentage(100);
					     table4.setWidths(new int[]{1,4,3,2,1,3});
					     
					     p =new Paragraph(" ",font1);
					     cell = new PdfPCell(p);
					     cell.setColspan(6);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0f);
					     cell.setBorderWidthRight(0f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0f);
					     table4.addCell(cell);
					     
					     p =new Paragraph("Sr.",font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0.1f);
					     cell.setBorderWidthBottom(0.1f);
					     table4.addCell(cell);
					     
					     p =new Paragraph("Product",font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0.1f);
					     table4.addCell(cell);
					     
					     p =new Paragraph("HSN",font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0.1f);
					     table4.addCell(cell);
					     
					     p =new Paragraph("Rate",font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0.1f);
					     table4.addCell(cell);
					     
					     p =new Paragraph("Qty",font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0.1f);
					     table4.addCell(cell);
					     
					     
					     p =new Paragraph("Total",font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0.1f);
					     table4.addCell(cell);
					     
	                    
	                    double cgst2=0,sgst2=0,igst2=0,total2=0;
	                    
	                    
	                    
	                    
	                    for(int i = 0;i<product1.size();i++)
				         {
	                    	
	                    	
	                    		p =new Paragraph(""+(i+1),font1);
					             cell = new PdfPCell(p);
					             cell.setUseBorderPadding(true);
					             cell.setVerticalAlignment(Element.ALIGN_CENTER);
					             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					             cell.setBorderWidthTop(0f);
					             cell.setBorderWidthRight(0.1f);
					             cell.setBorderWidthLeft(0.1f);
					             cell.setBorderWidthBottom(0f);
					             table4.addCell(cell);
	                    	
				             
				              
				             
				            	 p =new Paragraph(""+product1.get(i),font1);
					             cell = new PdfPCell(p);
					             cell.setUseBorderPadding(true);
					             cell.setVerticalAlignment(Element.ALIGN_CENTER);
					             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					             cell.setBorderWidthTop(0f);
					             cell.setBorderWidthRight(0.1f);
					             cell.setBorderWidthLeft(0f);
					             cell.setBorderWidthBottom(0f);
					             table4.addCell(cell);
					             
					             String hsn="";
					             
					             try
					     		{
					             	String query = "select * from Product where Company='"+company+"'and Name='"+product1.get(i)+"'";
					     		    PreparedStatement pmt =  connection.prepareStatement(query);
					     		    ResultSet rs = pmt.executeQuery();
					     		    while(rs.next())
					     		    {
					     		    	hsn=rs.getString("HSN");
					     		    }
					     		    rs.close();
					     		}
					             catch (Exception ae) {
					     		    ae.printStackTrace();
					             }
				             
				             
				             
				             p =new Paragraph(""+hsn,font1);
				             cell = new PdfPCell(p);
				             cell.setUseBorderPadding(true);
				             cell.setVerticalAlignment(Element.ALIGN_CENTER);
				             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				             cell.setBorderWidthTop(0f);
				             cell.setBorderWidthRight(0.1f);
				             cell.setBorderWidthLeft(0f);
				             cell.setBorderWidthBottom(0f);
				             table4.addCell(cell);
				             
				             
				             
				             p =new Paragraph(""+rate1.get(i),font1);
				             cell = new PdfPCell(p);
				             cell.setUseBorderPadding(true);
				             cell.setVerticalAlignment(Element.ALIGN_CENTER);
				             cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				             cell.setBorderWidthTop(0f);
				             cell.setBorderWidthRight(0.1f);
				             cell.setBorderWidthLeft(0f);
				             cell.setBorderWidthBottom(0f);
				             table4.addCell(cell);
				             
				             
				             
				             
				             p =new Paragraph(""+qty1.get(i),font1);
				             cell = new PdfPCell(p);
				             cell.setUseBorderPadding(true);
				             cell.setVerticalAlignment(Element.ALIGN_CENTER);
				             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				             cell.setBorderWidthTop(0f);
				             cell.setBorderWidthRight(0.1f);
				             cell.setBorderWidthLeft(0f);
				             cell.setBorderWidthBottom(0f);
				             table4.addCell(cell);

				             

				             total2+=total1.get(i);

				             //p =new Paragraph(""+f.format(total1.get(i)),font1);
				             p =new Paragraph(""+total1.get(i),font1);
				             cell = new PdfPCell(p);
				             cell.setUseBorderPadding(true);
				             cell.setVerticalAlignment(Element.ALIGN_CENTER);
				             cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				             cell.setBorderWidthTop(0f);
				             cell.setBorderWidthRight(0.1f);
				             cell.setBorderWidthLeft(0f);
				             cell.setBorderWidthBottom(0f);
				             table4.addCell(cell);
				         }
	                    
	                    for(int i = 0;i<12-total1.size();i++)
	                    {
	                    	p =new Paragraph(" ",font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0.1f);
	                        cell.setBorderWidthLeft(0.1f);
	                        cell.setBorderWidthBottom(0f);
	                        table4.addCell(cell);
	                        p =new Paragraph(" ",font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0.1f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0f);
	                        table4.addCell(cell);
	                        p =new Paragraph(" ",font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0.1f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0f);
	                        table4.addCell(cell);

	                        p =new Paragraph(" ",font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0.1f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0f);
	                        table4.addCell(cell);

	                        p =new Paragraph(" ",font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0.1f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0f);
	                        table4.addCell(cell);

	                       p =new Paragraph(" ",font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(0.1f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(0f);
	                        table4.addCell(cell);
	                    }

	                    
                        
                        
                        

	                    p =new Paragraph(" Remark: ",font1);
	                    cell = new PdfPCell(p);
	                    cell.setPaddingTop(10);
                        cell.setColspan(6);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0.1f);
                        cell.setBorderWidthRight(0.1f);
                        cell.setBorderWidthLeft(0.1f);
                        cell.setBorderWidthBottom(0f);
                        table4.addCell(cell);

                        p =new Paragraph(""+rem3,font1);
                        cell = new PdfPCell(p);
                        cell.setPaddingLeft(50);
                        cell.setColspan(6);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0.1f);
                        cell.setBorderWidthLeft(0.1f);
                        cell.setBorderWidthBottom(0f);
                        table4.addCell(cell);
                        
                        DecimalFormat f = new DecimalFormat("0.00");
                        
                        	p =new Paragraph(" ",font3);
   					     cell = new PdfPCell(p);
   					     cell.setColspan(3);
   					     cell.setUseBorderPadding(true);
   					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
   					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
   					     cell.setBorderWidthTop(0.1f);
   					     cell.setBorderWidthRight(0f);
   					     cell.setBorderWidthLeft(0.1f);
   					     cell.setBorderWidthBottom(0f);
   					     table4.addCell(cell);
                        
                        

                        p =new Paragraph("Total Amount (Rs.)",font1);
					     cell = new PdfPCell(p);
					     cell.setColspan(2);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0.1f);
					     cell.setBorderWidthBottom(0f);
					     table4.addCell(cell);

					     //p =new Paragraph(" "+f.format(sub_total),font1);
					     p =new Paragraph(" "+(f.format(total2)),font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0f);
					     table4.addCell(cell);
					     
					        p =new Paragraph(" ",font1);
	   					     cell = new PdfPCell(p);
	   					     cell.setColspan(3);
	   					     cell.setUseBorderPadding(true);
	   					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
	   					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	   					     cell.setBorderWidthTop(0f);
	   					     cell.setBorderWidthRight(0f);
	   					     cell.setBorderWidthLeft(0.1f);
	   					     cell.setBorderWidthBottom(0f);
	   					     table4.addCell(cell);
	                        	                            
					     
					     p =new Paragraph("CGST "+cgst1+" % (Rs.) ",font1);
					     cell = new PdfPCell(p);
					     cell.setColspan(2);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0.1f);
					     cell.setBorderWidthBottom(0f);
					     table4.addCell(cell);
					     
					     
					     p =new Paragraph(" "+(f.format(cgst1*total2*0.01)),font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0f);
					     table4.addCell(cell);
					     
					        	p =new Paragraph(" ",font1);
	   					     cell = new PdfPCell(p);
	   					     cell.setColspan(3);
	   					     cell.setUseBorderPadding(true);
	   					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
	   					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	   					     cell.setBorderWidthTop(0f);
	   					     cell.setBorderWidthRight(0f);
	   					     cell.setBorderWidthLeft(0.1f);
	   					     cell.setBorderWidthBottom(0f);
	   					     table4.addCell(cell);
	                        
					     p =new Paragraph("SGST "+f.format(sgst1)+" % (Rs.) ",font1);
					     cell = new PdfPCell(p);
					     cell.setColspan(2);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0.1f);
					     cell.setBorderWidthBottom(0f);
					     table4.addCell(cell);
					     
					     
					     p =new Paragraph(" "+(f.format(sgst1*total2*0.01)),font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0f);
					     table4.addCell(cell);
					     
					        	p =new Paragraph(" ",font1);
	   					     cell = new PdfPCell(p);
	   					     cell.setColspan(3);
	   					     cell.setUseBorderPadding(true);
	   					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
	   					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	   					     cell.setBorderWidthTop(0f);
	   					     cell.setBorderWidthRight(0f);
	   					     cell.setBorderWidthLeft(0.1f);
	   					     cell.setBorderWidthBottom(0f);
	   					     table4.addCell(cell);

					     /*p =new Paragraph("Grand Total (Rs.) ",font3);
					     cell = new PdfPCell(p);
					     cell.setColspan(2);
					     cell.setRowspan(2);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0.1f);
					     cell.setBorderWidthBottom(0.1f);
					     table4.addCell(cell);
					     
					     p =new Paragraph(" "+f.format(total),font3);
					     cell = new PdfPCell(p);
					     cell.setRowspan(2);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0.1f);
					     table4.addCell(cell);*/

					     p =new Paragraph("IGST "+f.format(igst1)+" % (Rs.) ",font1);
					     cell = new PdfPCell(p);
					     cell.setColspan(2);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0.1f);
					     cell.setBorderWidthBottom(0f);
					     table4.addCell(cell);

					     p =new Paragraph(" "+(f.format(igst1*total2*0.01)),font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0f);
					     table4.addCell(cell);
					     
					        	p =new Paragraph(" ",font1);
	   					     cell = new PdfPCell(p);
	   					     cell.setColspan(3);
	   					     cell.setUseBorderPadding(true);
	   					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
	   					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	   					     cell.setBorderWidthTop(0f);
	   					     cell.setBorderWidthRight(0f);
	   					     cell.setBorderWidthLeft(0.1f);
	   					     cell.setBorderWidthBottom(0.1f);
	   					     table4.addCell(cell);
	                        
					     /*p =new Paragraph("Grand Total ",font3);
					     cell = new PdfPCell(p);
					     cell.setColspan(2);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0.1f);
					     cell.setBorderWidthBottom(0.1f);
					     table4.addCell(cell);
					     
					     p =new Paragraph(" "+f.format(total+total44),font3);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0.1f);
					     table4.addCell(cell);*/
					     
					     p =new Paragraph("Grand Total ",font3);
					     cell = new PdfPCell(p);
					     cell.setColspan(2);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0.1f);
					     cell.setBorderWidthBottom(0.1f);
					     table4.addCell(cell);
					     
					     
					     p =new Paragraph(" "+(f.format(Math.round(total))),font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0.1f);
					     table4.addCell(cell);
					     
					     PdfPTable table5 = new PdfPTable(2);
		                    table5.setWidthPercentage(100);
		                    table5.setWidths(new float[]{6,(float)2.5});
		                    
		                    int k2 = (int) Math.round(total);
		                    
		                    
		                    
		                    
		                    
		                    p =new Paragraph(" Amount In Words: Rs "+convert(k2)+" Only.",font3);
		                    cell = new PdfPCell(p);
		                    cell.setColspan(2);
		                    cell.setUseBorderPadding(true);
		                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
		                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		                    cell.setBorderWidthTop(0f);
		                    cell.setBorderWidthRight(0.1f);
		                    cell.setBorderWidthLeft(0.1f);
		                    cell.setBorderWidthBottom(0.1f);
		                    table5.addCell(cell);
		                    
		                    p =new Paragraph(" ",font1);
		                    cell = new PdfPCell(p);
		                    cell.setUseBorderPadding(true);
		                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
		                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		                    cell.setBorderWidthTop(0f);
		                    cell.setBorderWidthRight(0.1f);
		                    cell.setBorderWidthLeft(0.1f);
		                    cell.setBorderWidthBottom(0f);
		                    table5.addCell(cell);
		                    
		                    p =new Paragraph(" ",font1);
		                    cell = new PdfPCell(p);
		                    cell.setUseBorderPadding(true);
		                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
		                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		                    cell.setBorderWidthTop(0f);
		                    cell.setBorderWidthRight(0.1f);
		                    cell.setBorderWidthLeft(0f);
		                    cell.setBorderWidthBottom(0f);
		                    table5.addCell(cell);
		                    
		                    p =new Paragraph("   Please contact us about payment in case of any query.",font1);
		                    cell = new PdfPCell(p);
		                    cell.setUseBorderPadding(true);
		                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
		                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		                    cell.setBorderWidthTop(0f);
		                    cell.setBorderWidthRight(0.1f);
		                    cell.setBorderWidthLeft(0.1f);
		                    cell.setBorderWidthBottom(0f);
		                    table5.addCell(cell);

		                    if(sign!=null) 
		                    {
  				    	 OutputStream targetfile=new FileOutputStream("sign.png");
	                    	targetfile.write(sign);
	                    	targetfile.close();

	                    	Image image = Image.getInstance("sign.png");
	                        image.setScaleToFitHeight(true);

	                        cell = new PdfPCell(image);
                           cell.setUseBorderPadding(true);
                           cell.setRowspan(3);
                           cell.setVerticalAlignment(Element.ALIGN_CENTER);
                           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cell.setBorderWidthTop(0f);
                           cell.setBorderWidthRight(0.1f);
                           cell.setBorderWidthLeft(0f);
                           cell.setBorderWidthBottom(0f);
                           cell.setFixedHeight(60);
                           cell.setPaddingTop(1f);
                           cell.setPaddingBottom(1f);
                           table5.addCell(cell);
  				    }
  				    else
  				    {
  				    	 p =new Paragraph(" ",font1);
                           cell = new PdfPCell(p);
                           cell.setRowspan(3);
                           cell.setUseBorderPadding(true);
                           cell.setVerticalAlignment(Element.ALIGN_CENTER);
                           cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                           cell.setBorderWidthTop(0f);
                           cell.setBorderWidthRight(0.1f);
                           cell.setBorderWidthLeft(0f);
                           cell.setBorderWidthBottom(0f);
                           cell.setFixedHeight(20);
                           cell.setPaddingTop(2f);
                           cell.setPaddingBottom(2f);
                           table5.addCell(cell);
  				    }
		                    
		                    p =new Paragraph("   Thank you for your business. ",font1);
		                    cell = new PdfPCell(p);
		                    cell.setUseBorderPadding(true);
		                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
		                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		                    cell.setBorderWidthTop(0f);
		                    cell.setBorderWidthRight(0.1f);
		                    cell.setBorderWidthLeft(0.1f);
		                    cell.setBorderWidthBottom(0f);
		                    table5.addCell(cell);
		                    
		                    p =new Paragraph("   Subject To Ahmedabad Jurisdiction. ",font1);
		                    cell = new PdfPCell(p);
		                    cell.setUseBorderPadding(true);
		                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
		                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		                    cell.setBorderWidthTop(0f);
		                    cell.setBorderWidthRight(0.1f);
		                    cell.setBorderWidthLeft(0.1f);
		                    cell.setBorderWidthBottom(0f);
		                    table5.addCell(cell);
		                    
		                    p =new Paragraph("   ",font1);
		                    cell = new PdfPCell(p);
		                    cell.setUseBorderPadding(true);
		                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
		                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		                    cell.setBorderWidthTop(0f);
		                    cell.setBorderWidthRight(0.1f);
		                    cell.setBorderWidthLeft(0.1f);
		                    cell.setBorderWidthBottom(0.1f);
		                    table5.addCell(cell);
		                    
		                    p =new Paragraph("Authourised Signatory.",font1);
		                    cell = new PdfPCell(p);
		                    cell.setUseBorderPadding(true);
		                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
		                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		                    cell.setBorderWidthTop(0f);
		                    cell.setBorderWidthRight(0.1f);
		                    cell.setBorderWidthLeft(0f);
		                    cell.setBorderWidthBottom(0.1f);
		                    table5.addCell(cell);

					     doc.add(table1);
	                    doc.add(table4);
	                    doc.add(table5);
	                    doc.close();
						
						
			        
					}
					catch(Exception ae)
					{
						ae.printStackTrace();
					}
					
					try 
					{
						Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"Sale.pdf");
					} 
					catch (Exception ae) 
					{
						ae.printStackTrace();
					}
				}
			});
			btnPrint.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
			btnPrint.setBounds(233, 63, 89, 25);
			panel.add(btnPrint);
			
			JButton btnEdit = new JButton("Edit");
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					Edit_sale b = new Edit_sale();
	                JDesktopPane desktopPane = getDesktopPane();
	                desktopPane.add(b);
	                b.show();
	                dispose();
				}
			});
			btnEdit.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
			btnEdit.setBounds(923, 65, 89, 25);
			panel.add(btnEdit);
			
			
			
			
	}
	public static boolean empty( final String s ) 
	{
		return s == null || s.trim().isEmpty();
	}
}