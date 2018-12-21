

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
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;

public class Backup extends JInternalFrame {
	private JTextField backuppath;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Backup frame = new Backup();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	static void fileProcessor(int cipherMode,String key,File inputFile,File outputFile)
	{
		 try {
		       Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
		       Cipher cipher = Cipher.getInstance("AES");
		       cipher.init(cipherMode, secretKey);

		       FileInputStream inputStream = new FileInputStream(inputFile);
		       byte[] inputBytes = new byte[(int) inputFile.length()];
		       inputStream.read(inputBytes);

		       byte[] outputBytes = cipher.doFinal(inputBytes);

		       FileOutputStream outputStream = new FileOutputStream(outputFile);
		       outputStream.write(outputBytes);

		       inputStream.close();
		       outputStream.close();

		    } catch (NoSuchPaddingException | NoSuchAlgorithmException 
	                     | InvalidKeyException | BadPaddingException
		             | IllegalBlockSizeException | IOException e) {
			e.printStackTrace();
	            }
	

	}

	public Backup() {
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
		
		JLabel lblBackupSetting = new JLabel("Backup Setting");
		lblBackupSetting.setFont(new Font("Book Antiqua", Font.BOLD, 25));
		lblBackupSetting.setBounds(89, 20, 196, 24);
		getContentPane().add(lblBackupSetting);
		
		JLabel lblPath = new JLabel("Path");
		lblPath.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		lblPath.setBounds(22, 69, 44, 21);
		getContentPane().add(lblPath);
		
		backuppath = new JTextField();
		backuppath.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		backuppath.setColumns(10);
		backuppath.setBounds(77, 66, 217, 24);
		getContentPane().add(backuppath);
		
		JButton btnOpen = new JButton("Select");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				 JFileChooser folder = new JFileChooser();
				 folder.setCurrentDirectory(new File(System.getProperty("user.home")));
				 folder.setDialogTitle("choosertitle");
				 folder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				 folder.setAcceptAllFileFilterUsed(false);
		         int result = folder.showSaveDialog(null);
		         if(result == JFileChooser.APPROVE_OPTION)
		         {
		             backuppath.setText(folder.getSelectedFile().getAbsolutePath());
		         }
		         else if(result == JFileChooser.CANCEL_OPTION)
		         {
		             JOptionPane.showMessageDialog(null, "No Data");
		         }
			}
		});
		btnOpen.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnOpen.setBounds(307, 65, 88, 23);
		getContentPane().add(btnOpen);
		
		JButton btnBackup = new JButton("Backup");
		btnBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try
				{
					 if(backuppath.getText().equals(""))
                     {
                         JOptionPane.showMessageDialog(null,"Please Select Path.");
                     }
                     
                     else {
							String key = "This is a secret";
							File inputFile = new File("Hydrevo_Design.db");
							File encryptedFile = new File(backuppath.getText()+"\\Viral.encrypted");
							
							try
							{
								Backup.fileProcessor(Cipher.ENCRYPT_MODE,key,inputFile,encryptedFile);
							} 
							catch (Exception ex) 
							{
							     ex.printStackTrace();
							}
							JOptionPane.showMessageDialog(null, "Backup Successfully");
							dispose();
	                     }
				}
				catch (Exception ae) {
					ae.printStackTrace();
				}
			}
		});
		btnBackup.setFont(new Font("Book Antiqua", Font.PLAIN, 16));
		btnBackup.setBounds(151, 112, 95, 27);
		getContentPane().add(btnBackup);
	}
}