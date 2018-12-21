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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class Add_city extends JInternalFrame {
	private JTextField city;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Add_city frame = new Add_city();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Add_city() {
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
		setBounds((w-450)/2, (h-215)/2, 450, 215);
		setClosable(true);
		getContentPane().setLayout(null);
		
		final Connection connection = Databaseconnection.connection2();
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 434, 271);
		getContentPane().add(panel);
		
		JLabel lblAddExtraHour = new JLabel("Add City");
		lblAddExtraHour.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblAddExtraHour.setBounds(160, 10, 146, 24);
		panel.add(lblAddExtraHour);
		
		
		final JComboBox state = new JComboBox();
		state.addItem("SELECT");
		state.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		state.setBounds(197, 50, 138, 25);
		panel.add(state);
		
		
		try{
        	
            String query1 = "select * from State";
		    PreparedStatement pmt1 = connection.prepareStatement(query1);
		    ResultSet rs = pmt1.executeQuery();
		    while(rs.next())
		    {
		    	state.addItem(rs.getString("State_name"));
		    }
		    rs.close();
		}
        catch (SQLException ae) {
		    ae.printStackTrace();
		}
		
		JLabel lblStateName = new JLabel("State Name:*");
		lblStateName.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblStateName.setBounds(62, 50, 100, 25);
		panel.add(lblStateName);
		
		
		
		
		JLabel lblAmount = new JLabel("City Name:*");
		lblAmount.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblAmount.setBounds(62, 85, 90, 25);
		panel.add(lblAmount);
		
		city = new JTextField();
		city.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		city.setBounds(197, 85, 136, 25);
		panel.add(city);
		city.setColumns(10);

		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					if(state.getSelectedItem().toString().equals("SELECT")||city.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    else {
                        String query="insert into City (State_name,City_name) VALUES ('"+state.getSelectedItem().toString()+"','"+city.getText()+"')";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                        city.setText("");
                        state.setSelectedIndex(0);
                    }
                
                }
               catch (SQLException ae) {
                   ae.printStackTrace();
               }
				
				
				
				
			}
		});
		button.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		button.setBounds(160, 125, 89, 23);
		panel.add(button);
		
		city.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
                {
					if(state.getSelectedItem().toString().equals("SELECT")||city.getText().equals(""))
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Details.");
                    }
                    else {
                        String query="insert into City (State_name,City_name) VALUES ('"+state.getSelectedItem().toString()+"','"+city.getText()+"')";
                        PreparedStatement pmt = connection.prepareStatement(query);
                        pmt.executeUpdate();
                        pmt.close();
                        
                        city.setText("");
                        state.setSelectedIndex(0);
                    }
                
                }
               catch (SQLException ae) {
                   ae.printStackTrace();
               }
			}
		});
	}
}