import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

public class Detail_order extends JInternalFrame {
	public JTable table;
	public JLabel name,no,total,remark,transport_type,driver_name,amount,vehicle_no;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Detail_order frame = new Detail_order();
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
	public Detail_order() {getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
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
        
		setBounds((w-1200)/2, (h-380)/2,1200, 380);
		getContentPane().setLayout(null);
		setClosable(true);
	
	JLabel lblPurchase = new JLabel("Order");
	lblPurchase.setFont(new Font("Book Antiqua", Font.BOLD, 25));
	lblPurchase.setBounds(202, 0, 130, 25);
	getContentPane().add(lblPurchase);
	
	JLabel lblNo = new JLabel("No:");
	lblNo.setFont(new Font("Book Antiqua", Font.BOLD, 16));
	lblNo.setBounds(10, 40, 46, 25);
	getContentPane().add(lblNo);
	
	 no = new JLabel("");
	 no.setForeground(Color.RED);
	no.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	no.setBounds(100, 40, 273, 25);
	getContentPane().add(no);
	
	JLabel lblName = new JLabel("Name");
	lblName.setFont(new Font("Book Antiqua", Font.BOLD, 16));
	lblName.setBounds(398, 40, 64, 25);
	getContentPane().add(lblName);
	
	 name = new JLabel("");
	 name.setForeground(Color.RED);
	name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	name.setBounds(452, 40, 722, 25);
	getContentPane().add(name);
	
	JLabel lblTotal = new JLabel("Total");
	lblTotal.setFont(new Font("Book Antiqua", Font.BOLD, 16));
	lblTotal.setBounds(802, 0, 110, 25);
	getContentPane().add(lblTotal);
	
	 total = new JLabel("");
	 total.setForeground(Color.RED);
	total.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	total.setBounds(872, 0, 302, 25);
	getContentPane().add(total);
	
	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(10, 241, 1164, 99);
	getContentPane().add(scrollPane);
	
	table = new JTable();
	table.setFont(new Font("Book Antiqua", Font.PLAIN, 12));
	scrollPane.setViewportView(table);
	
	JLabel lblRemark = new JLabel("Remark:");
	lblRemark.setFont(new Font("Book Antiqua", Font.BOLD, 16));
	lblRemark.setBounds(10, 75, 140, 25);
	getContentPane().add(lblRemark);
	
	 remark = new JLabel("");
	 remark.setForeground(Color.RED);
	remark.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	remark.setBounds(100, 75, 1074, 25);
	getContentPane().add(remark);
	
	table.setAutoCreateRowSorter(true);
	
	JLabel lblTransportType = new JLabel("Transport Type:");
	lblTransportType.setFont(new Font("Book Antiqua", Font.BOLD, 16));
	lblTransportType.setBounds(10, 100, 140, 25);
	getContentPane().add(lblTransportType);
	
	 transport_type = new JLabel("");
	transport_type.setForeground(Color.RED);
	transport_type.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	transport_type.setBounds(146, 100, 1028, 25);
	getContentPane().add(transport_type);
	
	 driver_name = new JLabel("");
	driver_name.setForeground(Color.RED);
	driver_name.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	driver_name.setBounds(146, 132, 1028, 25);
	getContentPane().add(driver_name);
	
	JLabel lblDriverName = new JLabel("Driver Name:");
	lblDriverName.setFont(new Font("Book Antiqua", Font.BOLD, 16));
	lblDriverName.setBounds(10, 132, 140, 25);
	getContentPane().add(lblDriverName);
	
	 amount = new JLabel("");
	amount.setForeground(Color.RED);
	amount.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	amount.setBounds(146, 205, 1028, 25);
	getContentPane().add(amount);
	
	JLabel lblDriverAmount = new JLabel("Driver Amount:");
	lblDriverAmount.setFont(new Font("Book Antiqua", Font.BOLD, 16));
	lblDriverAmount.setBounds(10, 205, 140, 25);
	getContentPane().add(lblDriverAmount);
	
	JLabel lblVehicleNo = new JLabel("Vehicle No.:");
	lblVehicleNo.setFont(new Font("Book Antiqua", Font.BOLD, 16));
	lblVehicleNo.setBounds(10, 169, 140, 25);
	getContentPane().add(lblVehicleNo);
	
	 vehicle_no = new JLabel("");
	vehicle_no.setForeground(Color.RED);
	vehicle_no.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
	vehicle_no.setBounds(146, 169, 1028, 25);
	getContentPane().add(vehicle_no);
}

}
