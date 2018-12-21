import java.awt.Dimension;
import java.awt.EventQueue;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


import net.proteanit.sql.DbUtils;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.awt.event.ActionEvent;
import javax.swing.JTable;

public class View_voucher extends JInternalFrame {
	private JTextField textField;
	private JTable table;
	public int id;
	String company=null,date_from=null,date_to=null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_voucher frame = new View_voucher();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	class DateRenderer extends DefaultTableCellRenderer 
	{
		private static final long serialVersionUID = 1L;
		private java.util.Date dateValue;
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

	public View_voucher() {
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
		setBounds((w-1200)/2, (h-482)/2, 1200, 482);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 1174, 442);
		getContentPane().add(panel);
		
		final Connection connection = Databaseconnection.connection2();
		
		try
		{
        	
            String query1 = "select * from Company_temp";
		    PreparedStatement pmt1 = connection.prepareStatement(query1);
		    ResultSet rs = pmt1.executeQuery();
		    
		    while(rs.next())
		    {
		    	company=rs.getString("Name");
		    	date_from=rs.getString("Date_from");
		    	date_to=rs.getString("Date_to");
		    }
		    rs.close();
		}
        catch (SQLException ae) {
		    ae.printStackTrace();
		}
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
                    
                    // pull data from the database 
                    
                    String query = "select ID,Type,Account,Name,Date,Amount,Payment,Cheque,Bank,Detail from Voucher where (Type like '%"+textField.getText()+"%' or Account like '%"+textField.getText()+"%' or Date like '%"+textField.getText()+"%' or Name like '%"+textField.getText()+"%' or Amount like '%"+textField.getText()+"%' or Payment like '%"+textField.getText()+"%' or Cheque like '%"+textField.getText()+"%' or Bank like '%"+textField.getText()+"%' or Detail like '%"+textField.getText()+"%') and Company='"+company+"'  and ('"+date_from+"'<=  Date and '"+date_to+"'>=  Date )";
                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    pmt.close();
                    rs.close();
                    
                   
                    
                    javax.swing.table.TableColumn col = null;
                    for(int j=0;j<10;j++)
                    {
                        col = table.getColumnModel().getColumn(j);
                        if(j==0)
                        {
                            col.setPreferredWidth(0);
                        }
                        else if(j==9)
                        {
                            col.setPreferredWidth(300);
                        }
                        else if(j==8 || j==3)
                        {
                            col.setPreferredWidth(200);
                        }
                        else
                        {
                            col.setPreferredWidth(100);
                        }
                    }
                    
    /*                DefaultTableCellRenderer dcenter=new DateRenderer();
	                dcenter.setHorizontalAlignment(SwingConstants.CENTER);
	                
	                DefaultTableCellRenderer right=new DateRenderer1();
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
	                /*table.getColumnModel().getColumn(4).setCellRenderer(dcenter);
	                table.getColumnModel().getColumn(0).setCellRenderer(center);
	                table.getColumnModel().getColumn(5).setCellRenderer(right);
	                table.getColumnModel().getColumn(6).setCellRenderer(center);
	                table.getColumnModel().getColumn(7).setCellRenderer(center);*/
                   
                } catch (SQLException ae) {
                    ae.printStackTrace();
                }
			}
		});
		
		
		textField.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(427, 40, 299, 20);
		panel.add(textField);
		
		JLabel lblViewVoucher = new JLabel("Voucher");
		lblViewVoucher.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblViewVoucher.setBounds(521, 0, 156, 24);
		panel.add(lblViewVoucher);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 75, 1154, 344);
		panel.add(scrollPane);
		
		
		TableModel model = null;
		table = new JTable(model){

	            /**
			 * 
			 */
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
                
                 
			}
		});
	            try 
	            {
	                String query1 = "select ID,Type,Account,Name,Date,Amount,Payment,Cheque,Bank,Detail from Voucher where Company='"+company+"'  and ('"+date_from+"'<=  Date and '"+date_to+"'>=  Date )";
	                java.sql.PreparedStatement pmt1 = connection.prepareStatement(query1);
	                ResultSet rs1 = pmt1.executeQuery();
	                table.setModel(DbUtils.resultSetToTableModel(rs1));
	                pmt1.close();
	                rs1.close();
	                
	                
	                javax.swing.table.TableColumn col = null;
                    for(int j=0;j<10;j++)
                    {
                        col = table.getColumnModel().getColumn(j);
                        if(j==0)
                        {
                            col.setPreferredWidth(0);
                        }
                        else if(j==9)
                        {
                            col.setPreferredWidth(300);
                        }
                        else if(j==8 || j==3)
                        {
                            col.setPreferredWidth(200);
                        }
                        else
                        {
                            col.setPreferredWidth(100);
                        }
                    }
                    
                    /*DefaultTableCellRenderer dcenter=new DateRenderer();
	                dcenter.setHorizontalAlignment(SwingConstants.CENTER);
	                
	                DefaultTableCellRenderer right=new DateRenderer1();
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
	              /*  table.getColumnModel().getColumn(4).setCellRenderer(dcenter);
	                table.getColumnModel().getColumn(0).setCellRenderer(center);
	                table.getColumnModel().getColumn(5).setCellRenderer(right);
	                table.getColumnModel().getColumn(6).setCellRenderer(center);
	                table.getColumnModel().getColumn(7).setCellRenderer(center);
	                
	             */   
	            }
	            catch (SQLException ae) 
	            {
	                ae.printStackTrace();
	            }

	            scrollPane.setViewportView(table);

		JButton button = new JButton("Delete");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
					
                    double amount = 0;
                    String type = null,account=null,name=null,payment=null,bank=null,mobile=null;
                    
                    String query3 = "select * from Voucher where ID ='"+id+"'";
                    java.sql.PreparedStatement pmt3 = connection.prepareStatement(query3);
                    ResultSet rs3 = pmt3.executeQuery();
                    while(rs3.next())
                    {
                         amount = rs3.getDouble("Amount");
                         type = rs3.getString("Type");
                         account = rs3.getString("Account");
                         name = rs3.getString("Name");
                         payment = rs3.getString("Payment");
                         bank = rs3.getString("Bank");
                    }
                    
                  //creditor start
                    if(type.equals("Debit") && account.equals("Creditor"))
                    {
                    	if(payment.equals("Cash"))
                    	{
                    		double t2 = 0;
		                    String query4 = "select Balance from Creditor where Name ='"+name+"' and Company='"+company+"'";
		                    PreparedStatement pmt4 = connection.prepareStatement(query4);
		                    ResultSet rs4 = pmt4.executeQuery();
		                    while(rs4.next())
		                    {
		                         t2 = rs4.getFloat("Balance");
		                    }
		                    pmt4.close();
		                    rs4.close();
		                    double t4 = t2 - amount;
		                   
		                    String query5 = "UPDATE Creditor set Balance ="+t4+" where Name ='"+name+"' and Company='"+company+"'";
		                    PreparedStatement pmt5 = connection.prepareStatement(query5);
		                    pmt5.executeUpdate();
		                    pmt5.close();
		                }
                    	else
                    	{
                    		double t2 = 0;
		                    String query4 = "select Balance from Creditor where Name ='"+name+"' and Company='"+company+"'";
		                    PreparedStatement pmt4 = connection.prepareStatement(query4);
		                    ResultSet rs4 = pmt4.executeQuery();
		                    while(rs4.next())
		                    {
		                         t2 = rs4.getFloat("Balance");
		                    }
		                    pmt3.close();
		                    rs3.close();
		                    
		                    double t4 = t2 - amount;
		                   
		                    String query5 = "UPDATE Creditor set Balance ="+t4+" where Name ='"+name+"' and Company='"+company+"'  and Category='In-House'";
		                    PreparedStatement pmt5 = connection.prepareStatement(query5);
		                    pmt5.executeUpdate();
		                    pmt5.close();
		                
		                    double t5 = 0;
		                    
		                    String query7 = "select Balance from Bank where Bank_name ='"+bank+"' and Company='"+company+"'";
		                    PreparedStatement pmt7 = connection.prepareStatement(query7);
		                    ResultSet rs7 = pmt7.executeQuery();
		                    while(rs7.next())
		                    {
		                         t5 = rs7.getFloat("Balance");
		                    }
		                    pmt7.close();
		                    rs7.close();
		                    
		                    double t6 = t5 + amount;
		                   
		                    String query6 = "UPDATE Bank set Balance ="+t6+" where Bank_name ='"+bank+"' and Company='"+company+"'";
		                    PreparedStatement pmt6 = connection.prepareStatement(query6);
		                    pmt6.executeUpdate();
		                    pmt6.close();
                    	}
                    }
                    //creditor end
                    
                  //client start
                    if(type.equals("Credit") && account.equals("Client"))
                    {
                    	if(payment.equals("Cash"))
                    	{
                    		double t2 = 0;
		                    String query4 = "select Balance from Client where Name ='"+name+"' and Company='"+company+"'";
		                    PreparedStatement pmt4 = connection.prepareStatement(query4);
		                    ResultSet rs4 = pmt4.executeQuery();
		                    while(rs4.next())
		                    {
		                         t2 = rs4.getFloat("Balance");
		                    }
		                    pmt4.close();
		                    rs4.close();
		                    double t4 = t2 + amount;
		                   
		                    String query5 = "UPDATE Client set Balance ="+t4+" where Name ='"+name+"' and Company='"+company+"'";
		                    PreparedStatement pmt5 = connection.prepareStatement(query5);
		                    pmt5.executeUpdate();
		                    pmt5.close();
		                }
                    	else
                    	{
                    		double t2 = 0;
		                    String query4 = "select Balance from Client where Name ='"+name+"' and Company='"+company+"'";
		                    PreparedStatement pmt4 = connection.prepareStatement(query4);
		                    ResultSet rs4 = pmt4.executeQuery();
		                    while(rs4.next())
		                    {
		                         t2 = rs4.getFloat("Balance");
		                    }
		                    pmt3.close();
		                    rs3.close();
		                    
		                    double t4 = t2 + amount;
		                   
		                    String query5 = "UPDATE Client set Balance ="+t4+" where Name ='"+name+"' and Company='"+company+"'  and Category='In-House'";
		                    PreparedStatement pmt5 = connection.prepareStatement(query5);
		                    pmt5.executeUpdate();
		                    pmt5.close();
		                
		                    double t5 = 0;
		                    
		                    String query7 = "select Balance from Bank where Bank_name ='"+bank+"' and Company='"+company+"'";
		                    PreparedStatement pmt7 = connection.prepareStatement(query7);
		                    ResultSet rs7 = pmt7.executeQuery();
		                    while(rs7.next())
		                    {
		                         t5 = rs7.getFloat("Balance");
		                    }
		                    pmt7.close();
		                    rs7.close();
		                    
		                    double t6 = t5 - amount;
		                   
		                    String query6 = "UPDATE Bank set Balance ="+t6+" where Bank_name ='"+bank+"' and Company='"+company+"'";
		                    PreparedStatement pmt6 = connection.prepareStatement(query6);
		                    pmt6.executeUpdate();
		                    pmt6.close();
                    	}
                    }
                    //creditor end
                    
                    
                    //client start
                    if(type.equals("Debit") && account.equals("Driver"))
                    {
                    	if(payment.equals("Cash"))
                    	{
                    		double t2 = 0;
		                    String query4 = "select Balance from Driver where Name ='"+name+"' and Company='"+company+"'";
		                    PreparedStatement pmt4 = connection.prepareStatement(query4);
		                    ResultSet rs4 = pmt4.executeQuery();
		                    while(rs4.next())
		                    {
		                         t2 = rs4.getFloat("Balance");
		                    }
		                    pmt4.close();
		                    rs4.close();
		                    double t4 = t2 + amount;
		                   
		                    String query5 = "UPDATE Driver set Balance ="+t4+" where Name ='"+name+"' and Company='"+company+"'";
		                    PreparedStatement pmt5 = connection.prepareStatement(query5);
		                    pmt5.executeUpdate();
		                    pmt5.close();
		                }
                    	else
                    	{
                    		double t2 = 0;
		                    String query4 = "select Balance from Driver where Name ='"+name+"' and Company='"+company+"'";
		                    PreparedStatement pmt4 = connection.prepareStatement(query4);
		                    ResultSet rs4 = pmt4.executeQuery();
		                    while(rs4.next())
		                    {
		                         t2 = rs4.getFloat("Balance");
		                    }
		                    pmt3.close();
		                    rs3.close();
		                    
		                    double t4 = t2 +amount;
		                   
		                    String query5 = "UPDATE Driver set Balance ="+t4+" where Name ='"+name+"' and Company='"+company+"'";
		                    PreparedStatement pmt5 = connection.prepareStatement(query5);
		                    pmt5.executeUpdate();
		                    pmt5.close();
		                
		                    double t5 = 0;
		                    
		                    String query7 = "select Balance from Bank where Bank_name ='"+bank+"' and Company='"+company+"'";
		                    PreparedStatement pmt7 = connection.prepareStatement(query7);
		                    ResultSet rs7 = pmt7.executeQuery();
		                    while(rs7.next())
		                    {
		                         t5 = rs7.getFloat("Balance");
		                    }
		                    pmt7.close();
		                    rs7.close();
		                    
		                    double t6 = t5 + amount;
		                   
		                    String query6 = "UPDATE Bank set Balance ="+t6+" where Bank_name ='"+bank+"' and Company='"+company+"'";
		                    PreparedStatement pmt6 = connection.prepareStatement(query6);
		                    pmt6.executeUpdate();
		                    pmt6.close();
                    	}
                    }
                    //Employee end
                    
                    
                    //expense start
                    if(type.equals("Debit") && account.equals("Expense"))
                    {
                    	if(payment.equals("Cash"))
                    	{
                    		
		                }
                    	else
                    	{
                    		double t5 = 0;
		                    String query7 = "select Balance from Bank where Bank_name ='"+bank+"' and Company='"+company+"'";
		                    PreparedStatement pmt7 = connection.prepareStatement(query7);
		                    ResultSet rs7 = pmt7.executeQuery();
		                    while(rs7.next())
		                    {
		                         t5 = rs7.getFloat("Balance");
		                    }
		                    pmt7.close();
		                    rs7.close();
		                    double t6 = t5 + amount;
		                   
		                    String query6 = "UPDATE Bank set Balance ="+t6+" where Bank_name ='"+bank+"' and Company='"+company+"'";
		                    PreparedStatement pmt6 = connection.prepareStatement(query6);
		                    pmt6.executeUpdate();
		                    pmt6.close();
                    	}
                    }
                    //product end
                    
                  //credit bank start
					else if(type.equals("Credit") && account.equals("Bank"))
					{
						double t5 = 0;
	                    String query5 = "select Balance from Bank where Bank_name ='"+name+"' and Company='"+company+"'";
	                    PreparedStatement pmt5 = connection.prepareStatement(query5);
	                    ResultSet rs5 = pmt5.executeQuery();
	                    while(rs5.next())
	                    {
	                         t5 = rs5.getFloat("Balance");
	                    }
	                    pmt5.close();
	                    rs5.close();

	                    double t6 = t5 - amount;

	                    String query6 = "UPDATE Bank set Balance ="+t6+" where Bank_name ='"+name+"' and Company='"+company+"'";
	                    PreparedStatement pmt6 = connection.prepareStatement(query6);
	                    pmt6.executeUpdate();
	                    pmt6.close();
			                
					}//credit bank end
                    
                  //debit bank start
					else
					{
						double t5 = 0;

						String query5 = "select Balance from Bank where Bank_name ='"+name+"' and Company='"+company+"'";
	                    PreparedStatement pmt5 = connection.prepareStatement(query5);
	                    ResultSet rs5 = pmt5.executeQuery();
	                    while(rs5.next())
	                    {
	                         t5 = rs5.getFloat("Balance");
	                    }
	                    pmt5.close();
	                    rs5.close();

	                    double t6 = t5 + amount;

	                    String query6 = "UPDATE Bank set Balance ="+t6+" where Bank_name ='"+name+"' and Company='"+company+"'";
	                    PreparedStatement pmt6 = connection.prepareStatement(query6);
	                    pmt6.executeUpdate();
	                    pmt6.close();
			        }//debit bank end
                    
		                String query = "delete from Voucher where ID="+id;
		                java.sql.PreparedStatement pmt = connection.prepareStatement(query);
		                pmt.executeUpdate();
		                pmt.close();
					}  
                } 
                catch (SQLException ae)
                {
                    ae.printStackTrace();
                }

                View_voucher b = new View_voucher();
                JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(b);
                b.show();
                dispose();
			}
		});
		button.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		button.setBounds(1050, 40, 89, 27);
		panel.add(button);
		
		JLabel label = new JLabel("Search");
		label.setFont(new Font("Book Antiqua", Font.BOLD, 16));
		label.setBounds(354, 40, 63, 25);
		panel.add(label);
		
		JButton btnDetail = new JButton("Detail");
		btnDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					
					DecimalFormat dc=new DecimalFormat("0.00");
					
					
					
					
					
					Detail_voucher s = new Detail_voucher();
                    String date=null;
                    double amount=0;
                    
                    String query3 = "select * from Voucher where ID ='"+id+"'";
                    java.sql.PreparedStatement pmt3 = connection.prepareStatement(query3);
                    ResultSet rs3 = pmt3.executeQuery();
                    while(rs3.next())
                    {
                    	s.type.setText(rs3.getString("Type"));
                    	s.account.setText(rs3.getString("Account"));
                    	s.name.setText(rs3.getString("Name"));
                    	//s.amount.setText(rs3.getString("Amount"));
                    	s.payment.setText(rs3.getString("Payment"));
                    	s.cheque.setText(rs3.getString("Cheque"));
                    	s.bank.setText(rs3.getString("Bank"));
                    	s.detail.setText(rs3.getString("Detail"));
                    	date=rs3.getString("Date");
                    	amount=rs3.getDouble("Amount");
                    }
                    pmt3.close();
                    rs3.close();
                    
                    s.amount.setText((dc.format(amount)));
                    
                    if (empty(date)) {
    					
    				} 
    				else 
    				{
    					try 
    					{
    			            SimpleDateFormat formatter2=new SimpleDateFormat("yyyy-MM-dd");
    					    java.util.Date date2= formatter2.parse(date);  

    			            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
    			            String strDate = dateFormat.format(date2);  

    			            s.textField.setText(strDate);
    					} 
    					catch (Exception ae) 
    					{
    						ae.printStackTrace();
    						// TODO: handle exception
    					}
    					
    				}
                    
                    JDesktopPane desktopPane = getDesktopPane();
                    desktopPane.add(s);
                    s.show();
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
			}
		});
		btnDetail.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnDetail.setBounds(60, 40, 89, 27);
		panel.add(btnDetail);
		
		JButton btnPri = new JButton("Print");
		btnPri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					BaseFont bf = null,bf1;
                    com.itextpdf.text.Font font1,font11;
					bf1 = BaseFont.createFont("book_b.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
	                bf = BaseFont.createFont("book.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
	                font1 = new com.itextpdf.text.Font(bf1,12);
	                font11 = new com.itextpdf.text.Font(bf1,20);
	                    
	                com.itextpdf.text.Font font2,font3;
	                font2 = new com.itextpdf.text.Font(bf, 12);
	                font3 = new com.itextpdf.text.Font(bf, 20);
	                
	                
	                String name=null,date=null,amount=null,payment=null,cheque=null,detail=null;
					
					String query6 = "select * from Voucher where Company='"+company+"' and ID='"+id+"'";
        		    PreparedStatement pmt6 = connection.prepareStatement(query6);
        		    ResultSet rs6 = pmt6.executeQuery();
        		    while(rs6.next())
        		    {
        		    	 name=rs6.getString("Name");
        		    	 date=rs6.getString("Date");
        		    	 amount=rs6.getString("Amount");
        		    	 payment=rs6.getString("Payment");
        		    	 cheque=rs6.getString("Cheque");
        		    	 detail=rs6.getString("Detail");
        		    }
        		    rs6.close();
        		    
        		    String address1=null,address2=null,address3=null,city=null,state=null,mobile99=null,gst99=null;

	                String queryas="select * from Company where Name='"+company+"'";
	                PreparedStatement pmtas= connection.prepareStatement(queryas);
	                ResultSet rsas = pmtas.executeQuery();
	                while(rsas.next())
	                {
	                	address1=rsas.getString("Address1");
	                	address2=rsas.getString("Address2");
	                	address3=rsas.getString("Address3");
	                	city=rsas.getString("City");
	                	state=rsas.getString("State");
	                	mobile99=rsas.getString("Mobile");
	                	gst99=rsas.getString("GST");
	                }
	                rsas.close();
	                pmtas.close();
	                
	                
	                Document doc = new Document(PageSize.A5.rotate(),10,10,10,20);
                    PdfWriter writer = PdfWriter.getInstance(doc,new FileOutputStream("Voucher.pdf"));
                    writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
                    doc.open();

                    PdfPCell cell;
                    PdfPCell blankcell = new PdfPCell(new Phrase(" "));
                    blankcell.setBorder(Rectangle.NO_BORDER);
                    Paragraph p;
                    
                    PdfPTable table1 = new PdfPTable(2);
                    table1.setWidthPercentage(100);
                    table1.setWidths(new int[]{8,2});

                    p =new Paragraph(company,font11);
                    cell = new PdfPCell(p);
                    cell.setColspan(2);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0);
                    table1.addCell(cell);
                    
                    p =new Paragraph(address1,font2);
                    cell = new PdfPCell(p);
                    cell.setColspan(2);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0);
                    table1.addCell(cell);
                    
                    p =new Paragraph(address2,font2);
                    cell = new PdfPCell(p);
                    cell.setColspan(2);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0f);
                    table1.addCell(cell);
                    
                    p =new Paragraph(address3+","+city+","+state,font2);
                    cell = new PdfPCell(p);
                    cell.setColspan(2);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0f);
                    table1.addCell(cell);
                    
                    p =new Paragraph("GST: "+gst99,font2);
                    cell = new PdfPCell(p);
                    cell.setColspan(2);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0);
                    table1.addCell(cell);
                    
                    

                    p =new Paragraph("No:"+id,font1);
                    cell = new PdfPCell(p);
                    cell.setColspan(2);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0f);
                    table1.addCell(cell);
                    
                    
                    java.util.Date startDate = null;
                    
                    try {
                    	SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
                        startDate = sm.parse(date);
                    }
                    catch (ParseException ae) {
                        ae.printStackTrace();
                    }

                    SimpleDateFormat sm2 = new SimpleDateFormat("dd-MM-yyyy");
                    String strDate = sm2.format(startDate);

                    p =new Paragraph("Date: "+strDate,font1);
                    cell = new PdfPCell(p);
                    cell.setColspan(2);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0);
                    table1.addCell(cell);

                    p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,16,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setColspan(2);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0);
                    table1.addCell(cell);
                    
                    
                    p =new Paragraph("Voucher",font11);
                    cell = new PdfPCell(p);
                    cell.setColspan(2);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0);
                    table1.addCell(cell);

                    p =new Paragraph(" Name:"+name,font1);
                    cell = new PdfPCell(p);
                    cell.setColspan(2);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0);
                    table1.addCell(cell);
                    
                    p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setColspan(2);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0);
                    table1.addCell(cell);
                    
                    p =new Paragraph("Detail",font1);
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(1f);
                    cell.setBorderWidthRight(1f);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(1f);
                    table1.addCell(cell);
                    
                    p =new Paragraph("Amount",font1);
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(1f);
                    cell.setBorderWidthRight(1f);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(1f);
                    table1.addCell(cell);
                    
                    p =new Paragraph(" "+detail,font1);
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(1f);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(1f);
                    table1.addCell(cell);
                    
                    p =new Paragraph(" "+amount,font1);
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(1f);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(1f);
                    table1.addCell(cell);
                    
                    p =new Paragraph("Total",font1);
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(1f);
                    cell.setBorderWidthLeft(1f);
                    cell.setBorderWidthBottom(1f);
                    table1.addCell(cell);
                    
                    p =new Paragraph(" "+amount,font1);
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(1f);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(1f);
                    table1.addCell(cell);
                    
                    float k1=Float.parseFloat(amount);
	                int k = Math.round(k1);

	                Phrase phrase9 = new Phrase();
                    phrase9.add(new Chunk("Amount in Words: ",font1));
                    phrase9.add(new Chunk(convert(k)+" INR Only",font2));
                    cell = new PdfPCell(phrase9);
                    cell.setUseBorderPadding(true);
	                cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                cell.setColspan(2);
	                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                cell.setBorderWidthTop(0f);
	                cell.setBorderWidthRight(0f);
	                cell.setBorderWidthLeft(0f);
	                cell.setBorderWidthBottom(0f);
	                table1.addCell(cell);
	                
	                p =new Paragraph(" Payment Type: "+payment,font1);
                    cell = new PdfPCell(p);
                    cell.setColspan(2);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0f);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0f);
                    table1.addCell(cell);
                    
                    if(payment.equals("Cash"))
                    {
                    	
                    }
                    else
                    {
                    	p =new Paragraph(" Cheque No: "+cheque,font1);
                        cell = new PdfPCell(p);
                        cell.setColspan(2);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(0f);
                        cell.setBorderWidthLeft(0f);
                        cell.setBorderWidthBottom(0f);
                        table1.addCell(cell);
                    }
                    
                    p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setColspan(2);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0f);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0f);
                    table1.addCell(cell);
                    
                    p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setColspan(2);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0f);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0f);
                    table1.addCell(cell);
                    
                    p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,10,Font.BOLD,BaseColor.BLACK));
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0f);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0f);
                    table1.addCell(cell);
                    
                    p =new Paragraph(" Authorised Sign.",font1);
                    cell = new PdfPCell(p);
                    cell.setUseBorderPadding(true);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setBorderWidthTop(0f);
                    cell.setBorderWidthRight(0f);
                    cell.setBorderWidthLeft(0f);
                    cell.setBorderWidthBottom(0f);
                    table1.addCell(cell);
                    
                    doc.add(table1);
                    doc.close();
	                
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
				
				try
                {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"Voucher.pdf");
                }
                catch(Exception ae)
                {
                    ae.printStackTrace();
                }
			}
		});
		btnPri.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnPri.setBounds(213, 40, 89, 27);
		panel.add(btnPri);
	}
	
	public static boolean empty( final String s ) 
	{
		return s == null || s.trim().isEmpty();
	}
	
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
}