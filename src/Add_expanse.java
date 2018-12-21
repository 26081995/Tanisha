import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class Add_expanse extends JInternalFrame {
	private JTextField expense;
	String company=null;
	private JTextField remark;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_expanse frame = new Add_expanse();
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
	public Add_expanse() {
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
		setBounds((w-450)/2, (h-211)/2, 450, 211);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 271);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblAddExpanse = new JLabel("Add Expense");
		lblAddExpanse.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblAddExpanse.setBounds(133, 10, 157, 24);
		panel.add(lblAddExpanse);
		
		JLabel lblExpanse = new JLabel("Expense:*");
		lblExpanse.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblExpanse.setBounds(100, 50, 74, 25);
		panel.add(lblExpanse);
		
		expense = new JTextField();
		expense.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		expense.setColumns(10);
		expense.setBounds(196, 50, 190, 25);
		panel.add(expense);
		
		final Connection connection = Databaseconnection.connection2();
		
		try
		{
        	
            String query1 = "select * from Company_temp";
		    PreparedStatement pmt1 = connection.prepareStatement(query1);
		    ResultSet rs = pmt1.executeQuery();
		    
		    while(rs.next())
		    {
		    	company=rs.getString("Name");
		    }
		    rs.close();
		}
        catch (SQLException ae) {
		    ae.printStackTrace();
		}
		
		
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					if(expense.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    else 
                    {
                    	String query="insert into Expense (Company,Name,Remark) VALUES ('"+company+"','"+expense.getText()+"','"+remark.getText()+"')";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                        expense.setText("");
                        remark.setText("");
                        expense.requestFocus();
                    }
                }
               catch (SQLException ae) {
                   ae.printStackTrace();
               }
			}
		});
		btnSubmit.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnSubmit.setBounds(163, 130, 89, 25);
		panel.add(btnSubmit);
		
		JLabel lblRemark = new JLabel("Remark:");
		lblRemark.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblRemark.setBounds(100, 85, 74, 25);
		panel.add(lblRemark);
		
		remark = new JTextField();
		remark.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		remark.setColumns(10);
		remark.setBounds(196, 85, 190, 25);
		panel.add(remark);
		
		

		remark.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					if(expense.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    else 
                    {
                    	String query="insert into Expense (Company,Name,Remark) VALUES ('"+company+"','"+expense.getText()+"','"+remark.getText()+"')";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                        expense.setText("");
                        remark.setText("");
                        expense.requestFocus();
                    }
                }
               catch (SQLException ae) {
                   ae.printStackTrace();
               }
			}
		});
	}
}