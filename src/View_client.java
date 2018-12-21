import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;



import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import net.proteanit.sql.DbUtils;
import javax.swing.JComboBox;

public class View_client extends JInternalFrame {
	private JTextField textField;
	private JTable table;
	private int id;
	String company=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_client frame = new View_client();
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
	public View_client() {
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
		setBounds((w-1200)/2, (h-600)/2, 1200, 600);
		getContentPane().setLayout(null);
		
		JLabel lblViewExpense = new JLabel("Client");
		lblViewExpense.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblViewExpense.setBounds(536, 0, 122, 25);
		getContentPane().add(lblViewExpense);
		
		final java.sql.Connection connection = Databaseconnection.connection2();
		
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
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 91, 1164, 469);
		getContentPane().add(scrollPane);
		
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

		scrollPane.setViewportView(table);
		try {
		    // pull data from the database 
		  
		    String query = "select ID,Name,Mobile,Balance from Client where Company='"+company+"'";
		    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
		    ResultSet rs = pmt.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
		    
		   javax.swing.table.TableColumn col = null;
           for(int j=0;j<4;j++)
            {
                col = table.getColumnModel().getColumn(j);
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
           
           /*DefaultTableCellRenderer right=new DateRenderer1();
           right.setHorizontalAlignment(SwingConstants.RIGHT);
           
           DefaultTableCellRenderer left=new DefaultTableCellRenderer();
           left.setHorizontalAlignment(SwingConstants.LEFT);
           
           DefaultTableCellRenderer center=new DefaultTableCellRenderer();
           center.setHorizontalAlignment(SwingConstants.CENTER);
           */
         
           Dimension d=new Dimension(10,0);
           
           table.setIntercellSpacing(d);
                     
           
           /*table.getColumnModel().getColumn(0).setCellRenderer(center);
           table.getColumnModel().getColumn(3).setCellRenderer(center);
           table.getColumnModel().getColumn(4).setCellRenderer(right);
           table.getColumnModel().getColumn(5).setCellRenderer(right);
           table.getColumnModel().getColumn(6).setCellRenderer(right);
           table.getColumnModel().getColumn(7).setCellRenderer(right);
           */
          
		    table.addMouseListener(new MouseAdapter() {
	            public void mouseClicked(MouseEvent arg0) {
	                int r = table.getSelectedRow();
	                String i = (table.getModel().getValueAt(r,0).toString());
	                id = Integer.parseInt(i);
	                
	                
	                try
	                 {
	                	 	String query = "delete from Client_id";
		                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
		                    pmt.executeUpdate();
		                    pmt.close();
		                    
		                    String query1 = "Insert Into Client_id(ID) Values ('"+id+"')";
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
		}
		catch (SQLException e) {
		    e.printStackTrace();
		}
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				try 
				{
					String query = "select ID,Name,Mobile,Balance from Client where (Name like '%"+textField.getText()+"%'  or Mobile like '%"+textField.getText()+"%'  or Balance like '%"+textField.getText()+"%'  ) and Company='"+company+"'";
                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
                    ResultSet rs = pmt.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                    pmt.close();
                    rs.close();
                    
                    javax.swing.table.TableColumn col = null;
                    for(int j=0;j<4;j++)
                    {
                        col = table.getColumnModel().getColumn(j);
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
                    /*DefaultTableCellRenderer right=new DateRenderer1();
                    right.setHorizontalAlignment(SwingConstants.RIGHT);
                    
                    DefaultTableCellRenderer left=new DefaultTableCellRenderer();
                    left.setHorizontalAlignment(SwingConstants.LEFT);
                    
                    DefaultTableCellRenderer center=new DefaultTableCellRenderer();
                    center.setHorizontalAlignment(SwingConstants.CENTER);
                    */
                  
                    Dimension d=new Dimension(10,0);
                    
                    table.setIntercellSpacing(d);
                              
                    /*
                    table.getColumnModel().getColumn(0).setCellRenderer(center);
                    table.getColumnModel().getColumn(3).setCellRenderer(center);
                    table.getColumnModel().getColumn(4).setCellRenderer(right);
                    table.getColumnModel().getColumn(5).setCellRenderer(right);
                    table.getColumnModel().getColumn(6).setCellRenderer(right);
                    table.getColumnModel().getColumn(7).setCellRenderer(right);*/
                } 
				catch (SQLException ae) {
                    ae.printStackTrace();
                }
			}
		});
		
		textField.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBounds(511, 40, 143, 25);
		getContentPane().add(textField);
		
		JButton button = new JButton("Delete");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are You Sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
					
						String query = "delete from Client where ID="+id;
	                    java.sql.PreparedStatement pmt = connection.prepareStatement(query);
	                    pmt.executeUpdate();
	                    pmt.close();
	                    
	                    
	                    String query1 = "select ID,Name,Mobile,Balance from Client where Company='"+company+"'";
	        		    java.sql.PreparedStatement pmt1 = connection.prepareStatement(query1);
	        		    ResultSet rs1 = pmt1.executeQuery();
	        		    table.setModel(DbUtils.resultSetToTableModel(rs1));
	        		    
	        		   javax.swing.table.TableColumn col = null;
	                   for(int j=0;j<4;j++)
	                    {
	                        col = table.getColumnModel().getColumn(j);
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
	                   /*DefaultTableCellRenderer right=new DateRenderer1();
	                    right.setHorizontalAlignment(SwingConstants.RIGHT);
	                    
	                    DefaultTableCellRenderer left=new DefaultTableCellRenderer();
	                    left.setHorizontalAlignment(SwingConstants.LEFT);
	                    
	                    DefaultTableCellRenderer center=new DefaultTableCellRenderer();
	                    center.setHorizontalAlignment(SwingConstants.CENTER);
	                    */
	                  
	                    Dimension d=new Dimension(10,0);
	                    
	                    table.setIntercellSpacing(d);
	                              
	                  /*  
	                    table.getColumnModel().getColumn(0).setCellRenderer(center);
	                    table.getColumnModel().getColumn(3).setCellRenderer(center);
	                    table.getColumnModel().getColumn(4).setCellRenderer(right);
	                    table.getColumnModel().getColumn(5).setCellRenderer(right);
	                    table.getColumnModel().getColumn(6).setCellRenderer(right);
	                    table.getColumnModel().getColumn(7).setCellRenderer(right);*/
					}
                }
               catch (Exception ae) {
                   ae.printStackTrace();
               }
			}
		});
		
		button.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		button.setBounds(1032, 40, 89, 25);
		getContentPane().add(button);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Edit_client b = new Edit_client();
                JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(b);
                b.show();
                dispose();
			}
		});
		btnEdit.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnEdit.setBounds(89, 40, 89, 25);
		getContentPane().add(btnEdit);
		
		JButton btnDetail = new JButton("Detail");
		btnDetail.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Detail_client b = new Detail_client();
                JDesktopPane desktopPane = getDesktopPane();
                desktopPane.add(b);
                b.show();
                
			}
		});
		btnDetail.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnDetail.setBounds(274, 40, 89, 25);
		getContentPane().add(btnDetail);
		
		JLabel label = new JLabel("Search");
		label.setFont(new Font("Book Antiqua", Font.BOLD, 16));
		label.setBounds(438, 40, 63, 25);
		getContentPane().add(label);
		
		/*JButton btnNewButton = new JButton("Print");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String pdf="";
				try
				{
					if(combobox.getSelectedItem().toString().equals("All"))
					{
						ArrayList<String> project_name = new ArrayList<String>();
						ArrayList<String> client_name = new ArrayList<String>();
						ArrayList<String> architech_name = new ArrayList<String>();
						
						String query0 = "select * from Project where Company='"+company+"'";
	                    PreparedStatement pmt0 = connection.prepareStatement(query0);
	                    ResultSet rs0 = pmt0.executeQuery();
	                    while(rs0.next())
	                    {
	                    	project_name.add(rs0.getString("Project_name"));
	                    	client_name.add(rs0.getString("Name"));
	                    	architech_name.add(rs0.getString("Architech_name"));
	                    }
	                    pmt0.close();
	                    rs0.close();
	                    
	                    
						String companame1 = null, address1 = null, address2 = null,state = null, city = null, no1 = null;

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
						}
						pmt51580.close();
						rs5580.close();
						
						Document doc;
						doc = new Document(PageSize.A4, 50, 50, 30, 30);
						
						pdf="Clients.pdf";
						
						PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(pdf));
						writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
						
						PdfPCell cell;
						PdfPCell blankcell = new PdfPCell(new Phrase(" "));
						blankcell.setBorder(Rectangle.NO_BORDER);
						Paragraph p;
					
						PdfPTable table1 = new PdfPTable(4);
						table1.setWidthPercentage(100);
						table1.setWidths(new int[] {1, 3,3,3});
						
						BaseFont bf,bf1,bf2;
	                    com.itextpdf.text.Font font,font1,font2;
	                    bf = BaseFont.createFont("book_b.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
	                    bf1 = BaseFont.createFont("book_b.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
	                    bf2 = BaseFont.createFont("book.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
	                    
	                    font = new com.itextpdf.text.Font(bf, 16);
	                    font1 = new com.itextpdf.text.Font(bf2, 10);
	                    
						
						doc.open();
						
						
						
						
						p = new Paragraph(" "+companame1,font);
						cell = new PdfPCell(p);
						cell.setColspan(4);
						cell.setUseBorderPadding(true);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorderWidthTop(0f);
						cell.setBorderWidthRight(0);
						cell.setBorderWidthLeft(0f);
						cell.setBorderWidthBottom(0);
						table1.addCell(cell);
						
						p = new Paragraph(" "+address1+","+address2,font1);
						cell = new PdfPCell(p);
						cell.setColspan(4);
						cell.setUseBorderPadding(true);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorderWidthTop(0f);
						cell.setBorderWidthRight(0);
						cell.setBorderWidthLeft(0f);
						cell.setBorderWidthBottom(0);
						table1.addCell(cell);
						
						p = new Paragraph(" "+no1,font1);
						cell = new PdfPCell(p);
						cell.setColspan(4);
						cell.setUseBorderPadding(true);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorderWidthTop(0f);
						cell.setBorderWidthRight(0);
						cell.setBorderWidthLeft(0f);
						cell.setBorderWidthBottom(0);
						table1.addCell(cell);
						
						p = new Paragraph(" ",font1);
						cell = new PdfPCell(p);
						cell.setColspan(4);
						cell.setUseBorderPadding(true);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorderWidthTop(0f);
						cell.setBorderWidthRight(0);
						cell.setBorderWidthLeft(0f);
						cell.setBorderWidthBottom(0);
						table1.addCell(cell);
						
						p =new Paragraph("Sr.",font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0.1f);
					     cell.setBorderWidthBottom(0.1f);
					     table1.addCell(cell);
					     
					     p =new Paragraph("Project Name",font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0.1f);
					     table1.addCell(cell);
					     
					     p =new Paragraph("Client Name",font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0.1f);
					     table1.addCell(cell);
					     
					     p =new Paragraph("Architect Name",font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0.1f);
					     table1.addCell(cell);
					     
					     for(int i = 0;i<client_name.size();i++)
				         {
				         	p =new Paragraph(""+(i+1),font1);
				             cell = new PdfPCell(p);
				             cell.setUseBorderPadding(true);
				             cell.setVerticalAlignment(Element.ALIGN_CENTER);
				             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				             cell.setBorderWidthTop(0f);
				             cell.setBorderWidthRight(0.1f);
				             cell.setBorderWidthLeft(0.1f);
				             cell.setBorderWidthBottom(0.1f);
				             table1.addCell(cell);
				             
				             p =new Paragraph(""+project_name.get(i),font1);
				             cell = new PdfPCell(p);
				             cell.setUseBorderPadding(true);
				             cell.setVerticalAlignment(Element.ALIGN_CENTER);
				             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				             cell.setBorderWidthTop(0f);
				             cell.setBorderWidthRight(0.1f);
				             cell.setBorderWidthLeft(0f);
				             cell.setBorderWidthBottom(0.1f);
				             table1.addCell(cell);

				             p =new Paragraph(""+client_name.get(i),font1);
				             cell = new PdfPCell(p);
				             cell.setUseBorderPadding(true);
				             cell.setVerticalAlignment(Element.ALIGN_CENTER);
				             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				             cell.setBorderWidthTop(0f);
				             cell.setBorderWidthRight(0.1f);
				             cell.setBorderWidthLeft(0f);
				             cell.setBorderWidthBottom(0.1f);
				             table1.addCell(cell);
				             
				             if(empty(architech_name.get(i)) || architech_name.get(i).equals("SELECT") )
				             {
				            	 p =new Paragraph(" ",font1);
					             cell = new PdfPCell(p);
					             cell.setUseBorderPadding(true);
					             cell.setVerticalAlignment(Element.ALIGN_CENTER);
					             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					             cell.setBorderWidthTop(0f);
					             cell.setBorderWidthRight(0.1f);
					             cell.setBorderWidthLeft(0f);
					             cell.setBorderWidthBottom(0.1f);
					             table1.addCell(cell);
				             }
				             else
				             {
				            	 p =new Paragraph(""+architech_name.get(i),font1);
					             cell = new PdfPCell(p);
					             cell.setUseBorderPadding(true);
					             cell.setVerticalAlignment(Element.ALIGN_CENTER);
					             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					             cell.setBorderWidthTop(0f);
					             cell.setBorderWidthRight(0.1f);
					             cell.setBorderWidthLeft(0f);
					             cell.setBorderWidthBottom(0.1f);
					             table1.addCell(cell);
				             }
				             
				             
				         }
					     
					     p =new Paragraph(" ",font1);
			             cell = new PdfPCell(p);
			             cell.setColspan(4);
			             cell.setUseBorderPadding(true);
			             cell.setVerticalAlignment(Element.ALIGN_CENTER);
			             cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			             cell.setBorderWidthTop(0f);
			             cell.setBorderWidthRight(0f);
			             cell.setBorderWidthLeft(0f);
			             cell.setBorderWidthBottom(0f);
			             table1.addCell(cell);
			             
			             doc.add(table1);
		                    doc.close();
					     
					     
					}
					else
					{

						ArrayList<String> project_name = new ArrayList<String>();
						ArrayList<String> client_name = new ArrayList<String>();
						ArrayList<String> architech_name = new ArrayList<String>();
						
						String query0 = "select * from Project where Company='"+company+"' and Category='"+combobox.getSelectedItem().toString()+"'";
	                    PreparedStatement pmt0 = connection.prepareStatement(query0);
	                    ResultSet rs0 = pmt0.executeQuery();
	                    while(rs0.next())
	                    {
	                    	project_name.add(rs0.getString("Project_name"));
	                    	client_name.add(rs0.getString("Name"));
	                    	architech_name.add(rs0.getString("Architech_name"));
	                    }
	                    pmt0.close();
	                    rs0.close();
	                    
	                    
						String companame1 = null, address1 = null, address2 = null,state = null, city = null, no1 = null;

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
						}
						pmt51580.close();
						rs5580.close();
						
						Document doc;
						doc = new Document(PageSize.A4, 50, 50, 30, 30);
						
						pdf=combobox.getSelectedItem().toString()+" Clients.pdf";
						
						PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(pdf));
						writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
						
						PdfPCell cell;
						PdfPCell blankcell = new PdfPCell(new Phrase(" "));
						blankcell.setBorder(Rectangle.NO_BORDER);
						Paragraph p;
					
						PdfPTable table1 = new PdfPTable(4);
						table1.setWidthPercentage(100);
						table1.setWidths(new int[] {1, 3,3,3});
						
						BaseFont bf,bf2;
	                    com.itextpdf.text.Font font,font1;
	                    bf = BaseFont.createFont("book_b.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
	                    bf2 = BaseFont.createFont("book.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
	                    
	                    font = new com.itextpdf.text.Font(bf, 16);
	                    font1 = new com.itextpdf.text.Font(bf2, 10);
	                    
								doc.open();
						
						
						
						p = new Paragraph(" ",font);
						cell = new PdfPCell(p);
						cell.setColspan(4);
						cell.setUseBorderPadding(true);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorderWidthTop(0f);
						cell.setBorderWidthRight(0);
						cell.setBorderWidthLeft(0f);
						cell.setBorderWidthBottom(0);
						table1.addCell(cell);
						
						p = new Paragraph(" "+companame1,font);
						cell = new PdfPCell(p);
						cell.setColspan(4);
						cell.setUseBorderPadding(true);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorderWidthTop(0f);
						cell.setBorderWidthRight(0);
						cell.setBorderWidthLeft(0f);
						cell.setBorderWidthBottom(0);
						table1.addCell(cell);
						
						p = new Paragraph(" "+address1+","+address2,font1);
						cell = new PdfPCell(p);
						cell.setColspan(4);
						cell.setUseBorderPadding(true);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorderWidthTop(0f);
						cell.setBorderWidthRight(0);
						cell.setBorderWidthLeft(0f);
						cell.setBorderWidthBottom(0);
						table1.addCell(cell);
						
						p = new Paragraph(" "+no1,font1);
						cell = new PdfPCell(p);
						cell.setColspan(4);
						cell.setUseBorderPadding(true);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorderWidthTop(0f);
						cell.setBorderWidthRight(0);
						cell.setBorderWidthLeft(0f);
						cell.setBorderWidthBottom(0);
						table1.addCell(cell);
						
						p = new Paragraph(" ",font1);
						cell = new PdfPCell(p);
						cell.setColspan(4);
						cell.setUseBorderPadding(true);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorderWidthTop(0f);
						cell.setBorderWidthRight(0);
						cell.setBorderWidthLeft(0f);
						cell.setBorderWidthBottom(0);
						table1.addCell(cell);
						
						p = new Paragraph(" "+combobox.getSelectedItem().toString()+" Clients" ,font);
						cell = new PdfPCell(p);
						cell.setColspan(4);
						cell.setUseBorderPadding(true);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorderWidthTop(0f);
						cell.setBorderWidthRight(0);
						cell.setBorderWidthLeft(0f);
						cell.setBorderWidthBottom(0);
						table1.addCell(cell);
						
						p = new Paragraph(" " ,font1);
						cell = new PdfPCell(p);
						cell.setColspan(4);
						cell.setUseBorderPadding(true);
						cell.setVerticalAlignment(Element.ALIGN_CENTER);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setBorderWidthTop(0f);
						cell.setBorderWidthRight(0);
						cell.setBorderWidthLeft(0f);
						cell.setBorderWidthBottom(0);
						table1.addCell(cell);
						
						p =new Paragraph("Sr.",font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0.1f);
					     cell.setBorderWidthBottom(0.1f);
					     table1.addCell(cell);
					     
					     p =new Paragraph("Project Name",font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0.1f);
					     table1.addCell(cell);
					     
					     p =new Paragraph("Client Name",font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0.1f);
					     table1.addCell(cell);
					     
					     p =new Paragraph("Architect Name",font1);
					     cell = new PdfPCell(p);
					     cell.setUseBorderPadding(true);
					     cell.setVerticalAlignment(Element.ALIGN_CENTER);
					     cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					     cell.setBorderWidthTop(0.1f);
					     cell.setBorderWidthRight(0.1f);
					     cell.setBorderWidthLeft(0f);
					     cell.setBorderWidthBottom(0.1f);
					     table1.addCell(cell);
					     
					     for(int i = 0;i<client_name.size();i++)
				         {
				         	p =new Paragraph(""+(i+1),font1);
				             cell = new PdfPCell(p);
				             cell.setUseBorderPadding(true);
				             cell.setVerticalAlignment(Element.ALIGN_CENTER);
				             cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				             cell.setBorderWidthTop(0f);
				             cell.setBorderWidthRight(0.1f);
				             cell.setBorderWidthLeft(0.1f);
				             cell.setBorderWidthBottom(0.1f);
				             table1.addCell(cell);
				             
				             p =new Paragraph(""+project_name.get(i),font1);
				             cell = new PdfPCell(p);
				             cell.setUseBorderPadding(true);
				             cell.setVerticalAlignment(Element.ALIGN_CENTER);
				             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				             cell.setBorderWidthTop(0f);
				             cell.setBorderWidthRight(0.1f);
				             cell.setBorderWidthLeft(0f);
				             cell.setBorderWidthBottom(0.1f);
				             table1.addCell(cell);

				             p =new Paragraph(""+client_name.get(i),font1);
				             cell = new PdfPCell(p);
				             cell.setUseBorderPadding(true);
				             cell.setVerticalAlignment(Element.ALIGN_CENTER);
				             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				             cell.setBorderWidthTop(0f);
				             cell.setBorderWidthRight(0.1f);
				             cell.setBorderWidthLeft(0f);
				             cell.setBorderWidthBottom(0.1f);
				             table1.addCell(cell);
				             
				             if(empty(architech_name.get(i)) || architech_name.get(i).equals("SELECT"))
				             {
				            	 p =new Paragraph(" ",font1);
					             cell = new PdfPCell(p);
					             cell.setUseBorderPadding(true);
					             cell.setVerticalAlignment(Element.ALIGN_CENTER);
					             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					             cell.setBorderWidthTop(0f);
					             cell.setBorderWidthRight(0.1f);
					             cell.setBorderWidthLeft(0f);
					             cell.setBorderWidthBottom(0.1f);
					             table1.addCell(cell);
				             }
				             else
				             {
				            	 p =new Paragraph(""+architech_name.get(i),font1);
					             cell = new PdfPCell(p);
					             cell.setUseBorderPadding(true);
					             cell.setVerticalAlignment(Element.ALIGN_CENTER);
					             cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					             cell.setBorderWidthTop(0f);
					             cell.setBorderWidthRight(0.1f);
					             cell.setBorderWidthLeft(0f);
					             cell.setBorderWidthBottom(0.1f);
					             table1.addCell(cell);
				             }
				             
				             
				         }
					     
					     p =new Paragraph(" ",font1);
			             cell = new PdfPCell(p);
			             cell.setColspan(4);
			             cell.setUseBorderPadding(true);
			             cell.setVerticalAlignment(Element.ALIGN_CENTER);
			             cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			             cell.setBorderWidthTop(0f);
			             cell.setBorderWidthRight(0f);
			             cell.setBorderWidthLeft(0f);
			             cell.setBorderWidthBottom(0f);
			             table1.addCell(cell);
			             
			             doc.add(table1);
		                    doc.close();
					     
					     
					
					}
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
				
				try 
				{
					Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+pdf);
				} 
				catch (Exception ae) 
				{
					ae.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnNewButton.setBounds(878, 43, 89, 25);
		getContentPane().add(btnNewButton);
		*/
			table.setAutoCreateRowSorter(true);
		
	}
	
	
	
	public static boolean empty( final String s ) 
	{
		return s == null || s.trim().isEmpty();
	}
}