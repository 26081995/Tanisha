import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.io.FileOutputStream;
import java.io.OutputStream;
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

public class View_order extends JInternalFrame {
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
					View_order frame = new View_order();
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
	public View_order() {
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
                	 	String query = "delete from Order_id";
	                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
	                    pmt.executeUpdate();
	                    pmt.close();

	                    String query1 = "Insert Into Order_id(ID) Values ('"+id+"')";
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
		                String query = "select ID,Order_no,Name,Date,Total from Order1 where Company='"+company+"'";
		                java.sql.PreparedStatement pmt = connection.prepareStatement(query);
		                ResultSet rs = pmt.executeQuery();
		                table.setModel(DbUtils.resultSetToTableModel(rs));
		                pmt.close();
		                rs.close();
		                
		                javax.swing.table.TableColumn col = null;
	                    for(int j=0;j<5;j++)
	                    {
	                        col = table.getColumnModel().getColumn(j);
	                        if(j==0)
	                        {
	                            col.setPreferredWidth(0);
	                        }
	                        else if(j==2)
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
		                table.getColumnModel().getColumn(3).setCellRenderer(dcenter);
		                
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
	                    String query = "select ID,Order_no,Name,Date,Total from order1 where (order_no like '%"+textField.getText()+"%'  or Name like '%"+textField.getText()+"%' or Date like '%"+textField.getText()+"%'  or Total like '%"+textField.getText()+"%') and Company='"+company+"'";
	                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
	                    ResultSet rs = pmt.executeQuery();
	                    table.setModel(DbUtils.resultSetToTableModel(rs));
	                    pmt.close();
	                    rs.close();
	                    
	                    
	                    javax.swing.table.TableColumn col = null;
	                    for(int j=0;j<5;j++)
	                    {
	                        col = table.getColumnModel().getColumn(j);
	                        if(j==0)
	                        {
	                            col.setPreferredWidth(0);
	                        }
	                        else if(j==2)
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
		                table.getColumnModel().getColumn(3).setCellRenderer(dcenter);
		                
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

			JLabel lblViewExtraHour = new JLabel("Order");
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
								String status=null;
								
								String query1 = "select * from Order1 where  Company='"+company+"' and ID='"+id+"'";
	    	                    PreparedStatement pmt1 = connection.prepareStatement(query1);
	    	                    ResultSet rs1 = pmt1.executeQuery();
	    	                    while(rs1.next())
	    	                    {
	    	                    	//Project_name
	    	                    	status=rs1.getString("Status");
	    	        	        }
	    	                    pmt1.close();
	    	                    rs1.close();
	    	                    
	    	                    if(status.equals("Complete"))
	    	                    {
	    	                    	JOptionPane.showMessageDialog(null, "You cann't delete this order");
	    	                    }
	    	                    else
	    	                    {
	    	                    	String query = "delete from Sale where ID="+id;
				                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
				                    pmt.executeUpdate();
				                    pmt.close();

				                    String query1q = "delete from Sale_detail where S_id="+id;
				                    java.sql.PreparedStatement pmt1q = connection.prepareStatement(query1q);
				                    pmt1q.executeUpdate();
				                    pmt1q.close();
	    	                    }
			                    

			                    View_order b = new View_order();
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
						Detail_order s = new Detail_order();
	                    String date=null;
	                    
	                    

	                    String query3 = "select * from Order1 where ID ='"+id+"'";
	                    java.sql.PreparedStatement pmt3 = connection.prepareStatement(query3);
	                    ResultSet rs3 = pmt3.executeQuery();
	                    while(rs3.next())
	                    {
	                    	s.total.setText((rs3.getString("Total")));
	                    	s.no.setText(rs3.getString("Order_no"));
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
	                    
	                    
	                    String query = "select ID,Product,Rate,Qty,Total from Order_detail where S_id ="+id+"";
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
			
			
			JButton btnEdit = new JButton("Edit");
			btnEdit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					
					String status=null;
					
					
                    
                    try
                    {
                    	String query1 = "select * from Order1 where  Company='"+company+"' and ID='"+id+"'";
                        PreparedStatement pmt1 = connection.prepareStatement(query1);
                        ResultSet rs1 = pmt1.executeQuery();
                        while(rs1.next())
                        {
                        	//Project_name
                        	status=rs1.getString("Status");
            	        }
                        pmt1.close();
                        rs1.close();
                    }
                    catch(Exception ae)
                    {
                    	ae.printStackTrace();
                    }
                    
                    if(status.equals("Complete"))
                    {
                    	JOptionPane.showMessageDialog(null, "You cann't delete this order");
                    }
                    
                    else
                    {
                    	Edit_order b = new Edit_order();
    	                JDesktopPane desktopPane = getDesktopPane();
    	                desktopPane.add(b);
    	                b.show();
    	                dispose();
                    }
					
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