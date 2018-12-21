import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.itextpdf.text.BaseColor;
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
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

public class Employee_report extends JInternalFrame {
	String company=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Employee_report frame = new Employee_report();
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
	public Employee_report() {
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
	        setBounds((w-450)/2, (h-234)/2, 450, 234);
			getContentPane().setLayout(null);
			setClosable(true);

		JLabel lblEmployeeReport = new JLabel("Employee Report");
		lblEmployeeReport.setFont(new Font("Book Antiqua", Font.BOLD, 20));
		lblEmployeeReport.setBounds(132, 11, 173, 25);
		getContentPane().add(lblEmployeeReport);

		final Connection connection = Databaseconnection.connection2();
		try
		{
			String query1 = "select * from Company_temp";
		    PreparedStatement pmt1 = connection.prepareStatement(query1);
		    ResultSet rs1 = pmt1.executeQuery();
		    while(rs1.next())
		    {
		    	company=rs1.getString("Name");
		    }
		    rs1.close();
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
		
		JLabel lblName = new JLabel("Name:*");
		lblName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblName.setBounds(26, 61, 79, 14);
		getContentPane().add(lblName);
		
		JComboBox name = new JComboBox();
		name.addItem("SELECT");
		name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		name.setBounds(123, 58, 229, 20);
		getContentPane().add(name);
		
		try
		{
			String query0 = "select * from Driver where Company='"+company+"'";
            PreparedStatement pmt0 = connection.prepareStatement(query0);
            ResultSet rs0 = pmt0.executeQuery();
            while(rs0.next())
            {
           	 	name.addItem(rs0.getString("Name"));
           	}
            pmt0.close();
            rs0.close();
		}
		catch(Exception ae)
		{
			ae.printStackTrace();
		}
		

		

		JMonthChooser monthChooser = new JMonthChooser();
		monthChooser.setToolTipText("SELECT");
		monthChooser.setMonth(0);
		monthChooser.setBounds(114, 89, 111, 20);
		getContentPane().add(monthChooser);

		JLabel label_1 = new JLabel("Month:");
		label_1.setToolTipText("SELECT");
		label_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_1.setBounds(40, 89, 64, 14);
		getContentPane().add(label_1);

		JLabel label_2 = new JLabel("Year:");
		label_2.setToolTipText("");
		label_2.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_2.setBounds(245, 95, 38, 14);
		getContentPane().add(label_2);

		JYearChooser yearChooser = new JYearChooser();
		yearChooser.setBounds(305, 89, 47, 20);
		getContentPane().add(yearChooser);

		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					if(name.getSelectedItem().toString().equals("SELECT"))
					{
						JOptionPane.showMessageDialog(null, "Please Fill Details");
					}
					else
					{
						int year = yearChooser.getYear();
						int month = monthChooser.getMonth();
						
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Calendar c = Calendar.getInstance();
						c.set(year, month, 1); // Specify day of month
						String date_from = dateFormat.format(c.getTime());

						Calendar cal = Calendar.getInstance();
						DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
						Date d = null;
						cal.set(year, month, 1);
				        cal.add(Calendar.MONTH, 1);
				        cal.add(Calendar.DAY_OF_MONTH, -1);

				        String date_to = dateFormat1.format(cal.getTime());

				        DateFormat dateFormat5 = new SimpleDateFormat("yyyy-MM-dd");
						Date h = null;
						try
						{
							h = (Date) dateFormat5.parse(date_from);
						}
						catch (ParseException e2) 
						{
							e2.printStackTrace();
						}

						Calendar cal6 = Calendar.getInstance();
						DateFormat dateFormat6 = new SimpleDateFormat("MMMM");
						cal6.set(year, month, 1);

						String date_to1 = dateFormat6.format(cal6.getTime());

				        String salary=null,leave="";
				        String query11="select * from Salary where Name='"+name.getSelectedItem().toString()+"' and Year='"+year+"' and Month='"+date_to1+"' and Company='"+company+"'";
		                PreparedStatement pmt11 = connection.prepareStatement(query11);
		                ResultSet rs11 = pmt11.executeQuery();
		                while(rs11.next())
		                {
		                	salary=rs11.getString("Salary");
		                }
		                rs11.close();
		                pmt11.close();

		                SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
		                
		                double total99=0;

		                ArrayList<String> name1 = new ArrayList<String>();
						ArrayList<String> leave1 = new ArrayList<String>();
	                    ArrayList<String> date = new ArrayList<String>();
	                    ArrayList<String> remark = new ArrayList<String>();
	                    ArrayList<String> total = new ArrayList<String>();

	                    String query1 = "Select * from Voucher where ('"+date_from+"'<= Date and'"+date_to+"'>= Date) and Company='"+company+"' and Type='Debit' and Account='Driver' and Name='"+name.getSelectedItem().toString()+"'";
	                    java.sql.PreparedStatement pmt1 = connection.prepareStatement(query1);
	                    ResultSet rs1 = pmt1.executeQuery();
	                    while(rs1.next())
	                    {
	                    	name1.add(rs1.getString("Name"));
	                        date.add(rs1.getString("Date"));
	                        remark.add(rs1.getString("Detail"));
	                        total.add(rs1.getString("Amount"));
	                        total99+=rs1.getDouble("Amount");
	                    }
	                    rs1.close();
	                    pmt1.close();

	                    String query2 = "Select * from Leave where ('"+date_from+"'<= Date and'"+date_to+"'>= Date) and Company='"+company+"' and Name='"+name.getSelectedItem().toString()+"'";
	                    java.sql.PreparedStatement pmt2 = connection.prepareStatement(query2);
	                    ResultSet rs2 = pmt2.executeQuery();
	                    while(rs2.next())
	                    {
	                    	leave1.add(rs2.getString("Date"));
	                    }
	                    rs2.close();
	                    pmt2.close();
	                    
	                    
	                    for(int j = 0;j<leave1.size();j++)
	                    {
	                    	Date startDate = null;

	                    	try 
		                    {
	                    		SimpleDateFormat sm1 = new SimpleDateFormat("yyyy-MM-dd");
		                    	startDate = sm1.parse(leave1.get(j));
		                    } 
		                    catch (ParseException ae) 
		                    {
		                        ae.printStackTrace();
		                    } 

	                    	SimpleDateFormat sm2 = new SimpleDateFormat("dd-MM-yyyy");
		                    String strDate = sm2.format(startDate);

	                    	leave+=strDate+",";
	                    }

	                    String balance=null;

	                    String query12="select * from Driver where Name='"+name.getSelectedItem().toString()+"' and Company='"+company+"'";
		                PreparedStatement pmt12 = connection.prepareStatement(query12);
		                ResultSet rs12 = pmt12.executeQuery();
		                while(rs12.next())
		                {
		                	balance=(rs12.getString("Balance"));
		                }
		                rs11.close();
		                pmt11.close();
		                
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
		                
		                BaseFont bf = null,bf1;
	                    com.itextpdf.text.Font font1,font11;
						bf1 = BaseFont.createFont("book_b.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
		                bf = BaseFont.createFont("book.ttf", BaseFont.WINANSI, BaseFont.EMBEDDED);
		                font1 = new com.itextpdf.text.Font(bf1,12);
		                font11 = new com.itextpdf.text.Font(bf1,20);
		                    
		                com.itextpdf.text.Font font2,font3;
		                font2 = new com.itextpdf.text.Font(bf, 12);
		                font3 = new com.itextpdf.text.Font(bf, 20);
		                

	                    Document doc = new Document(PageSize.A4,10,10,10,20);
	                    PdfWriter writer = PdfWriter.getInstance(doc,new FileOutputStream("Employee Report.pdf"));
	                    writer.setBoxSize("art", new Rectangle(36, 54, 559, 788));
	                    doc.open();

	                    PdfPCell cell;
	                    PdfPCell blankcell = new PdfPCell(new Phrase(" "));
	                    blankcell.setBorder(Rectangle.NO_BORDER);
	                    Paragraph p;

	                    PdfPTable table = new PdfPTable(1);
	                    table.setWidthPercentage(100);
	                    
	                    PdfPTable table2 = new PdfPTable(1);
	                    table2.setWidthPercentage(100);

	                    
	                    p =new Paragraph(" "+company,font11);
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table.addCell(cell);
	                    
	                    p =new Paragraph(" "+address1,font2);
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table.addCell(cell);
	                    
	                    p =new Paragraph(" "+address2,font2);
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table.addCell(cell);
	                    
	                    p =new Paragraph(" "+address3+","+city+","+state,font2);
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table.addCell(cell);
	                    
	                    p =new Paragraph(" GST: "+gst99,font2);
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table.addCell(cell);
	                    
	                    p =new Paragraph(" Mobile: "+mobile99,font2);
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table.addCell(cell);
	                    
	                    p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,16,Font.BOLD,BaseColor.BLACK));
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table.addCell(cell);
	                    
	                    p =new Paragraph("Employee Report",font11);
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table.addCell(cell);

	                    p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table.addCell(cell);

	                    p =new Paragraph("Name:"+name.getSelectedItem().toString(),font1);
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table.addCell(cell);

	                    p =new Paragraph("Month:"+date_to1,font1);
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table.addCell(cell);

	                    p =new Paragraph("Year:"+year,font1);
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table.addCell(cell);

	                    p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table2.addCell(cell);

	                    p =new Paragraph("Leave Days:"+leave,font1);
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table2.addCell(cell);

	                    
	                    p =new Paragraph("Salary:"+salary,font1);
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table2.addCell(cell);

	                    p =new Paragraph("Current Balance:"+balance,font1);
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table2.addCell(cell);

	                    p =new Paragraph(" ",FontFactory.getFont(FontFactory.HELVETICA,14,Font.BOLD,BaseColor.BLACK));
	                    cell = new PdfPCell(p);
	                    cell.setUseBorderPadding(true);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                    cell.setBorderWidthTop(0f);
	                    cell.setBorderWidthRight(0);
	                    cell.setBorderWidthLeft(0f);
	                    cell.setBorderWidthBottom(0);
	                    table.addCell(cell);

	                    PdfPTable table3 = new PdfPTable(4);
	                    table3.setWidthPercentage(100);
	                    table3.setWidths(new int[]{1,2,2,2});

	                    p = new Paragraph("ID",font1);
	                    cell = new PdfPCell(p);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    cell.setBorderWidthTop(1f);
	                    cell.setBorderWidthRight(1f);
	                    cell.setBorderWidthLeft(1f);
	                    cell.setBorderWidthBottom(1f);
	                    table3.addCell(cell);

	                    p = new Paragraph("Name",font1);
	                    cell = new PdfPCell(p);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    cell.setBorderWidthTop(1f);
	                    cell.setBorderWidthRight(1f);
	                    cell.setBorderWidthLeft(0);
	                    cell.setBorderWidthBottom(1f);
	                    table3.addCell(cell);

	                    p = new Paragraph("Date",font1);
	                    cell = new PdfPCell(p);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    cell.setBorderWidthTop(1f);
	                    cell.setBorderWidthRight(1f);
	                    cell.setBorderWidthLeft(0);
	                    cell.setBorderWidthBottom(1f);
	                    table3.addCell(cell);

	                    p = new Paragraph("Total",font1);
	                    cell = new PdfPCell(p);
	                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                    cell.setBorderWidthTop(1f);
	                    cell.setBorderWidthRight(1f);
	                    cell.setBorderWidthLeft(0);
	                    cell.setBorderWidthBottom(1f);
	                    table3.addCell(cell);

	                    for(int j = 0;j<name1.size();j++)
	                    {
	                        p =new Paragraph(" "+(j+1),font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setFixedHeight(20f);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(1f);
	                        cell.setBorderWidthLeft(1f);
	                        cell.setBorderWidthBottom(1f);
	                        table3.addCell(cell);

	                        p =new Paragraph(name1.get(j),font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(1f);
	                        cell.setBorderWidthLeft(0);
	                        cell.setBorderWidthBottom(1f);
	                        table3.addCell(cell);

	                        Date startDate2 = null;

		                    try {
		                    	
		                        startDate2 = sm.parse(date.get(j));
		                    }
		                    catch (ParseException ae) {
		                        ae.printStackTrace();
		                    }
		                    SimpleDateFormat sm2 = new SimpleDateFormat("dd-MM-yyyy");
		                    String strDate2 = sm2.format(startDate2);

		                    p =new Paragraph(""+strDate2,font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(1f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(1f);
	                        table3.addCell(cell);
	                        
	                        p =new Paragraph(""+total.get(j),font1);
	                        cell = new PdfPCell(p);
	                        cell.setUseBorderPadding(true);
	                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
	                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                        cell.setBorderWidthTop(0f);
	                        cell.setBorderWidthRight(1f);
	                        cell.setBorderWidthLeft(0f);
	                        cell.setBorderWidthBottom(1f);
	                        table3.addCell(cell);
	                    }
	                    
	                    p =new Paragraph(" Total",font1);
                        cell = new PdfPCell(p);
                        cell.setColspan(3);
                        cell.setUseBorderPadding(true);
                        cell.setFixedHeight(20f);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(1f);
                        cell.setBorderWidthLeft(1f);
                        cell.setBorderWidthBottom(1f);
                        table3.addCell(cell);

                        
                        
                        p =new Paragraph(""+total99,font1);
                        cell = new PdfPCell(p);
                        cell.setUseBorderPadding(true);
                        cell.setVerticalAlignment(Element.ALIGN_CENTER);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setBorderWidthTop(0f);
                        cell.setBorderWidthRight(1f);
                        cell.setBorderWidthLeft(0f);
                        cell.setBorderWidthBottom(1f);
                        table3.addCell(cell);

	                    doc.add(table);
	                    doc.add(table3);
	                    doc.add(table2);
	                    doc.close();
					}
				}
				catch(Exception ae)
				{
					ae.printStackTrace();
				}
				
				try
                {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+"Employee Report.pdf");
                }
                catch(Exception ae)
                {
                    ae.printStackTrace();
                }
			}
		});
		btnPrint.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnPrint.setBounds(162, 144, 89, 23);
		getContentPane().add(btnPrint);
		
		

	}
}
