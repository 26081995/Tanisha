import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;

import com.itextpdf.text.DocumentException;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;

public class Viral extends JFrame {

	private JPanel contentPane;
	public JLabel label1,label;
	String company=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Viral frame = new Viral();
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
	public Viral() throws IOException, DocumentException {
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
        double height = screenSize.getHeight();
        int w = (int)(width);
        int h = (int)(height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, w, h-120);
		
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        
		//setLocationByPlatform(true);
       //setUndecorated(true);
        contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		

		final JDesktopPane desktopPane = new JDesktopPane();
        desktopPane.setBackground(Color.LIGHT_GRAY);
		desktopPane.setBounds(0, 0, w, h-120);
        contentPane.add(desktopPane);
        
        /*DesktopListener desktopListener = new DesktopListener();
        
        desktopPane.addContainerListener(desktopListener);

        //initComponents();

        SwitchDispatcher<JInternalFrame> switchDispatcher
                = new SwitchDispatcher<>(new DesktopSwitcher(desktopPane), desktopListener);
        switchDispatcher.start();*/

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(Color.LIGHT_GRAY);
        panel_1.setBounds(w-400, h-150, 400, 30);
        desktopPane.add(panel_1);
        panel_1.setLayout(null);

        JLabel lblNewLabel_11 = new JLabel("\u00a9 copyright Designed And Developed By Soft Solutions");
        lblNewLabel_11.setFont(new Font("Book Antiqua", Font.PLAIN, 14));
        lblNewLabel_11.setBounds(0, 0, 400, 25);
        panel_1.add(lblNewLabel_11);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnMaster = new JMenu("Master");
        mnMaster.setFont(new Font("Book Antiqua", Font.BOLD, 25));
        mnMaster.setMnemonic(KeyEvent.VK_M);
        menuBar.add(mnMaster);
        
        JMenu mnProduct = new JMenu("Product");
        mnProduct.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnMaster.add(mnProduct);
        
        JMenuItem mntmAddProduct = new JMenuItem("Add Product");
        mntmAddProduct.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_product p=new Add_product();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmAddProduct.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnProduct.add(mntmAddProduct);
        
        JMenuItem mntmViewProduct = new JMenuItem("View Product");
        mntmViewProduct.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_product p=new View_product();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmViewProduct.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnProduct.add(mntmViewProduct);
        
        JMenu mnClient = new JMenu("Client");
        mnClient.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnMaster.add(mnClient);
        
        JMenuItem mntmAddClient = new JMenuItem("Add Client");
        mntmAddClient.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_client p=new Add_client();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmAddClient.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnClient.add(mntmAddClient);
        
        JMenuItem mntmViewClient = new JMenuItem("View Client");
        mntmViewClient.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_client p=new View_client();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmViewClient.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnClient.add(mntmViewClient);
        
        JMenu mnCredi = new JMenu("Creditor");
        mnCredi.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnMaster.add(mnCredi);
        
        JMenuItem mntmAddCreditor = new JMenuItem("Add Creditor");
        mntmAddCreditor.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_creditor p=new Add_creditor();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmAddCreditor.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnCredi.add(mntmAddCreditor);
        
        JMenuItem mntmViewCreditor = new JMenuItem("View Creditor");
        mntmViewCreditor.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_creditor p=new View_creditor();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmViewCreditor.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnCredi.add(mntmViewCreditor);
        
        JMenu mnDriver = new JMenu("Driver");
        mnDriver.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnMaster.add(mnDriver);
        
        JMenuItem mntmAddDriver = new JMenuItem("Add Driver");
        mntmAddDriver.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_driver p=new Add_driver();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmAddDriver.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnDriver.add(mntmAddDriver);
        
        JMenuItem mntmViewDriver = new JMenuItem("View Driver");
        mntmViewDriver.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_driver p=new View_driver();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmViewDriver.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnDriver.add(mntmViewDriver);
        
        JMenu mnExpense = new JMenu("Expense");
        mnExpense.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnMaster.add(mnExpense);
        
        JMenuItem mntmAddExpense = new JMenuItem("Add Expense");
        mntmAddExpense.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_expanse p=new Add_expanse();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmAddExpense.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnExpense.add(mntmAddExpense);
        
        JMenuItem mntmNewMenuItem = new JMenuItem("View Expense");
        mntmNewMenuItem.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_expanse p=new View_expanse();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmNewMenuItem.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnExpense.add(mntmNewMenuItem);
        
        JMenu mnNewMenu = new JMenu("Bank");
        mnNewMenu.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnMaster.add(mnNewMenu);
        
        JMenuItem mntmAddBank = new JMenuItem("Add Bank");
        mntmAddBank.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_bank p=new Add_bank();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmAddBank.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnNewMenu.add(mntmAddBank);
        
        JMenuItem mntmViewBank = new JMenuItem("View Bank");
        mntmViewBank.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_bank p=new View_bank();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmViewBank.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnNewMenu.add(mntmViewBank);
        
        JMenu mnState = new JMenu("State");
        mnState.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnMaster.add(mnState);
        
        JMenuItem mntmAddState = new JMenuItem("Add State");
        mntmAddState.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_state p=new Add_state();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmAddState.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnState.add(mntmAddState);
        
        JMenuItem mntmViewState = new JMenuItem("View State");
        mntmViewState.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_state p=new View_state();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmViewState.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnState.add(mntmViewState);
        
        JMenu mnCity = new JMenu("City");
        mnCity.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnMaster.add(mnCity);
        
        JMenuItem mntmAddCity = new JMenuItem("Add City");
        mntmAddCity.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_city p=new Add_city();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmAddCity.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnCity.add(mntmAddCity);
        
        JMenuItem mntmViewCity = new JMenuItem("View City");
        mntmViewCity.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_city p=new View_city();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmViewCity.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnCity.add(mntmViewCity);
        
        JMenu mnTransaction = new JMenu("Transaction");
        mnTransaction.setFont(new Font("Book Antiqua", Font.BOLD, 25));
        mnTransaction.setMnemonic(KeyEvent.VK_T);
        menuBar.add(mnTransaction);
        
        JMenu mnPurchase = new JMenu("Purchase");
        mnPurchase.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnTransaction.add(mnPurchase);
        
        JMenuItem mntmAddPurchase = new JMenuItem("Add Purchase");
        mntmAddPurchase.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_purchase p=new Add_purchase();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmAddPurchase.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnPurchase.add(mntmAddPurchase);
        
        JMenuItem mntmViewPurchase = new JMenuItem("View Purchase");
        mntmViewPurchase.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_purchase p=new View_purchase();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmViewPurchase.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnPurchase.add(mntmViewPurchase);
        
        JMenu mnOrder = new JMenu("Order");
        mnOrder.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnTransaction.add(mnOrder);
        
        JMenuItem mntmAddOrder = new JMenuItem("Add order");
        mntmAddOrder.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_order p=new Add_order();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmAddOrder.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnOrder.add(mntmAddOrder);
        
        JMenuItem mntmViewOrder = new JMenuItem("View Order");
        mntmViewOrder.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_order p=new View_order();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmViewOrder.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnOrder.add(mntmViewOrder);
        
        JMenu mnSale = new JMenu("Sale");
        mnSale.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnTransaction.add(mnSale);
        
        JMenuItem mntmAddSale = new JMenuItem("Add Sale");
        mntmAddSale.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_sale p=new Add_sale();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmAddSale.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnSale.add(mntmAddSale);
        
        JMenuItem mntmViewSale = new JMenuItem("View Sale");
        mntmViewSale.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_sale p=new View_sale();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmViewSale.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnSale.add(mntmViewSale);
        
        JMenu mnDriverExpense = new JMenu("Driver Expense");
        mnDriverExpense.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnTransaction.add(mnDriverExpense);
        
        JMenuItem mntmAddExpense_1 = new JMenuItem("Add Expense");
        mntmAddExpense_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_driver_expense p=new Add_driver_expense();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmAddExpense_1.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnDriverExpense.add(mntmAddExpense_1);
        
        JMenuItem mntmViewExpense = new JMenuItem("View Expense");
        mntmViewExpense.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_driver_expense p=new View_driver_expense();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmViewExpense.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnDriverExpense.add(mntmViewExpense);
        
        JMenu mnAverage = new JMenu("Average");
        mnAverage.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnTransaction.add(mnAverage);
        
        JMenuItem mntmAddAverage = new JMenuItem("Add Average");
        mntmAddAverage.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_average p=new Add_average();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmAddAverage.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnAverage.add(mntmAddAverage);
        
        JMenuItem mntmViewAverage = new JMenuItem("View Average");
        mntmViewAverage.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_average p=new View_average();
        		desktopPane.add(p);
        		p.show();
        	}
        });
        mntmViewAverage.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnAverage.add(mntmViewAverage);
        
        
        
        JMenu mnStaff_1 = new JMenu("Staff");
        mnStaff_1.setMnemonic(KeyEvent.VK_S);
        mnStaff_1.setFont(new Font("Book Antiqua", Font.BOLD, 25));
        menuBar.add(mnStaff_1);
        
        
        JMenu mnLeave = new JMenu("Leave");
        mnStaff_1.add(mnLeave);
        mnLeave.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        
        JMenuItem mntmAddLeave = new JMenuItem("Add Leave");
        mntmAddLeave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_leave as11=new Add_leave();
        		desktopPane.add(as11);
                as11.show();
        	}
        });
        mntmAddLeave.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnLeave.add(mntmAddLeave);
        
        JMenuItem mntmViewLeave = new JMenuItem("View Leave");
        mntmViewLeave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_leave as111=new View_leave();
        		desktopPane.add(as111);
                as111.show();
        	}
        });
        mntmViewLeave.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnLeave.add(mntmViewLeave);
        
        JMenu mnSalary = new JMenu("Salary");
        mnStaff_1.add(mnSalary);
        mnSalary.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        
        JMenuItem mntmAddSalary = new JMenuItem("Add Salary");
        mntmAddSalary.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_salary as1131=new Add_salary();
        		desktopPane.add(as1131);
                as1131.show();
        	}
        });
        mntmAddSalary.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnSalary.add(mntmAddSalary);
        
        JMenuItem mntmViewSalary = new JMenuItem("View Salary");
        mntmViewSalary.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_salary as1141=new View_salary();
        		desktopPane.add(as1141);
                as1141.show();
        	}
        });
        mntmViewSalary.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnSalary.add(mntmViewSalary);
        
        JMenuItem mntmEmployeeReport = new JMenuItem("Employee Report");
        mntmEmployeeReport.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Employee_report as1141=new Employee_report();
        		desktopPane.add(as1141);
                as1141.show();
        	}
        });
        mntmEmployeeReport.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnStaff_1.add(mntmEmployeeReport);
        
        JMenu mnVoucher = new JMenu("Voucher");
        mnVoucher.setMnemonic(KeyEvent.VK_V);
        mnVoucher.setFont(new Font("Book Antiqua", Font.BOLD, 25));
        menuBar.add(mnVoucher);
        
        JMenuItem mntmAddVoucher = new JMenuItem("Add Voucher");
        mntmAddVoucher.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Add_voucher av=new Add_voucher();
        		desktopPane.add(av);
                av.show();
        	}
        });
        mntmAddVoucher.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnVoucher.add(mntmAddVoucher);
        
        JMenuItem mntmViewVoucher = new JMenuItem("View Voucher");
        mntmViewVoucher.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_voucher av1=new View_voucher();
        		desktopPane.add(av1);
                av1.show();
        	}
        });
        mntmViewVoucher.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnVoucher.add(mntmViewVoucher);
        
        
        
        JMenu mnAdmin = new JMenu("Admin");
        mnAdmin.setMnemonic(KeyEvent.VK_A);
        mnAdmin.setFont(new Font("Book Antiqua", Font.BOLD, 25));
        menuBar.add(mnAdmin);

        JMenuItem mntmChangePassword = new JMenuItem("Change Password");
        mntmChangePassword.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Change_password pr5=new Change_password();
        		desktopPane.add(pr5);
                pr5.show();
        	}
        });
        mntmChangePassword.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnAdmin.add(mntmChangePassword);

        JMenuItem mntmViewCompany = new JMenuItem("View Company");
        mntmViewCompany.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		View_company pr5=new View_company();
        		desktopPane.add(pr5);
                pr5.show();
        	}
        });
        mntmViewCompany.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnAdmin.add(mntmViewCompany);

        

        /*JMenuItem mntmBankDetail = new JMenuItem("Bank Detail");
        mntmBankDetail.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Bank_detail pr5=new Bank_detail();
        		desktopPane.add(pr5);
                pr5.show();
             }
        });
        mntmBankDetail.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnAdmin.add(mntmBankDetail);
        
        JMenuItem mntmChangeYear = new JMenuItem("Change Year");
        mntmChangeYear.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Change_year pr5=new Change_year();
        		desktopPane.add(pr5);
                pr5.show();
        	}
        });
        mntmChangeYear.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnAdmin.add(mntmChangeYear);
        */
        
        JMenuItem mntmBackup = new JMenuItem("Backup");
        mntmBackup.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Backup pr5=new Backup();
        		desktopPane.add(pr5);
                pr5.show();
        	}
        });
        mntmBackup.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnAdmin.add(mntmBackup);
        
        JMenuItem mntmRestore = new JMenuItem("Restore");
        mntmRestore.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		Restore pr5=new Restore();
        		desktopPane.add(pr5);
                pr5.show();
        	}
        });
        mntmRestore.setFont(new Font("Book Antiqua", Font.BOLD, 20));
        mnAdmin.add(mntmRestore);
	}
}
