

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.crypto.Cipher;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.event.ActionEvent;

public class Restore extends JInternalFrame {
	private JTextField path;
	File selectedFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Restore frame = new Restore();
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
	public Restore() {
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
		setBounds((w-433)/2, (h-186)/2, 433, 186);
		getContentPane().setLayout(null);
		setClosable(true);
		
		JLabel lblRestoreSetting = new JLabel("Restore Setting");
		lblRestoreSetting.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblRestoreSetting.setBounds(77, 20, 208, 24);
		getContentPane().add(lblRestoreSetting);
		
		JLabel label_1 = new JLabel("Path");
		label_1.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		label_1.setBounds(22, 69, 44, 21);
		getContentPane().add(label_1);
		
		path = new JTextField();
		path.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		path.setColumns(10);
		path.setBounds(77, 66, 217, 24);
		getContentPane().add(path);

		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				 JFileChooser folder = new JFileChooser();
				 folder.setCurrentDirectory(new File(System.getProperty("user.home")));
				 FileNameExtensionFilter filter = new FileNameExtensionFilter("*.encrypted", "encrypted");
				 folder.addChoosableFileFilter(filter);
				 folder.setFileSelectionMode(JFileChooser.FILES_ONLY);

				 int result = folder.showSaveDialog(null);

		         if(result == JFileChooser.APPROVE_OPTION)
		         {
		        	 selectedFile = folder.getSelectedFile();
		        	
		        	 path.setText(folder.getSelectedFile().getAbsolutePath());
		         }
		         else if(result == JFileChooser.CANCEL_OPTION)
		         {
		             JOptionPane.showMessageDialog(null, "No Data");
		         }
			}
		});
		btnSelect.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnSelect.setBounds(307, 65, 88, 23);
		getContentPane().add(btnSelect);

		JButton btnRestore = new JButton("Restore");
		btnRestore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					 if(path.getText().equals(""))
                     {
                         JOptionPane.showMessageDialog(null,"Please Select Path.");
                     }
					 else 
                     {
                    	 	String key = "This is a secret";
							
							File decryptedFile = new File("Viral.db");
							 System.out.println(decryptedFile);
							try 
							{
								Backup.fileProcessor(Cipher.DECRYPT_MODE,key,selectedFile,decryptedFile);
							     System.out.println("Sucess Decrypt");
							}
							catch (Exception ex) 
							{
							     System.out.println(ex.getMessage());
						             ex.printStackTrace();
							 }
							System.exit(0);
                     	}
				}
				catch (Exception ae) {
					ae.printStackTrace();
				}
			}
		});
		btnRestore.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnRestore.setBounds(151, 112, 95, 27);
		getContentPane().add(btnRestore);
	}
}