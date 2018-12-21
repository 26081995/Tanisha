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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class View_average extends JInternalFrame {
	private JTextField textField;
	private JTable table;
	public int id;
	String company=null,date_from=null,date_to=null;

	
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
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View_average frame = new View_average();
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
	public View_average() {
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
                    
                    String query = "select ID,Name,Vehicle_no,Date,From99 as From_km,To99 as To_km ,Ltr,Rs_per_ltr,Average,Total from Average where (Name like '%"+textField.getText()+"%' or Vehicle_no like '%"+textField.getText()+"%' or Date like '%"+textField.getText()+"%' or From99 like '%"+textField.getText()+"%' or To99 like '%"+textField.getText()+"%' or Ltr like '%"+textField.getText()+"%' or Rs_per_ltr like '%"+textField.getText()+"%' or Average like '%"+textField.getText()+"%' or Total like '%"+textField.getText()+"%') and Company='"+company+"'";
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
                        else if(j==2)
                        {
                            col.setPreferredWidth(300);
                        }
                        else if(j==1|| j==3)
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
		
		JLabel lblViewVoucher = new JLabel("Average");
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
	                String query1 = "select ID,Name,Vehicle_no,Date,From99 as From_km,To99 as To_km ,Ltr,Rs_per_ltr,Average,Total from Average where Company='"+company+"'";
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
                        else if(j==2)
                        {
                            col.setPreferredWidth(300);
                        }
                        else if(j==1|| j==3)
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
					
						String query = "delete from Average where ID="+id;
		                java.sql.PreparedStatement pmt = connection.prepareStatement(query);
		                pmt.executeUpdate();
		                pmt.close();
					}  
                } 
                catch (SQLException ae)
                {
                    ae.printStackTrace();
                }

                View_average b = new View_average();
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