import javax.swing.Action;
import java.io.*;
import java.util.*;
import java.net.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.lang.System.out;

public class ChatClient2 extends JFrame implements ActionListener {

	String UNAME;
	PrintWriter pw;
	BufferedReader br;
	JTextArea taMessages;
	JTextField tfInput;
	JButton btnSend, btnExit;
	Socket client;
	String servername;
	private JLabel userImageLbl, EnterText;
	static String s;
	private JLabel textField, chatText;
	
	String userImageUrl = "https://cdn4.iconfinder.com/data/icons/tweetmyweb/512/gossip_birds.png";
	  String userImageUrl2 = "http://cdn1.iconfinder.com/data/icons/nuvola2/22x22/apps/personal.png";
	public ChatClient2(String s, String uname, String servername) throws Exception {

		super(uname); // set title for frame
		this.UNAME = uname;
		s = "*";
		client = new Socket(servername, 9998);
		br = new BufferedReader(new InputStreamReader(client.getInputStream()));
		pw = new PrintWriter(client.getOutputStream(), true);

		pw.println(s + uname);

		// send name to server
		buildInterface();
		new MessagesThread().start();
		// create thread for listening for messages
	}
	

    

	


	public void buildInterface() throws MalformedURLException {
		ImageIcon icon = new ImageIcon(userImageUrl);
		Image serImageUrl = icon.getImage().getScaledInstance(28, 28, Image.SCALE_DEFAULT);
	

        EnterText = new JLabel("Enter Text");
        tfInput  = new JTextField(30);
		textField = new JLabel();
		textField.setIcon(new ImageIcon(new URL(userImageUrl)));
	
		btnSend = new JButton("SEND");
		
		btnSend.setBackground(Color.BLUE);
		btnSend.setOpaque(true);
		btnSend.setFont(new Font("American Typewriter", Font.BOLD, 20));

		btnSend.setLocation(20, 30);
		
		btnExit = new JButton("EXIT");
		btnExit.setBackground(Color.BLUE);
		btnExit.setOpaque(true);
		btnExit.setFont(new Font("American Typewriter", Font.BOLD, 20));

		taMessages = new JTextArea();
		
		taMessages.setRows(10);
		taMessages.setColumns(32);
		taMessages.setEditable(false);
		taMessages.setBorder(BorderFactory.createLineBorder(Color.blue));
	
		taMessages.setFont(new Font("American Typewriter", Font.ITALIC, 20));
		tfInput = new JTextField(30);
		tfInput.setFont(new Font("American Typewriter", Font.ITALIC, 20));
		tfInput.setSize(150, 100);

		tfInput.setBackground(Color.orange);
		tfInput.setBorder(BorderFactory.createLineBorder(new Color(6, 6, 8, 8), 2));
		tfInput.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		JScrollPane sp = new JScrollPane(taMessages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(sp, "Center");
	
		JPanel bp = new JPanel(new FlowLayout());
		bp.setPreferredSize(new Dimension(30, 250));
		userImageLbl = new JLabel();
		userImageLbl.setIcon(new ImageIcon(new URL(userImageUrl)));
        bp.add(EnterText );
		btnSend.setSize(50, 50);
		btnExit.setSize(50, 50);
		bp.add(tfInput,"North");
		bp.add(userImageLbl);
		bp.add(btnSend,"Notrh");
        bp.setBackground(Color.lightGray);
		bp.add(btnExit);
		add(bp, "South");
		
		btnSend.addActionListener(this);
		btnExit.addActionListener(this);
	
		
		setVisible(true);
		pack();
		
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btnExit) {
			pw.println("end");
			System.exit(0);
		} else {

			pw.println(tfInput.getText());
		}
	}

   
	public static void main(String... args) {

		String name = JOptionPane.showInputDialog(null, "Enter your name :", "Username", JOptionPane.PLAIN_MESSAGE);
		String servername = "localhost";

		try {
			new ChatClient2(s, name, servername);
		} catch (Exception ex) {
			out.println("Error --> " + ex.getMessage());
		}

	}

	class MessagesThread extends Thread {
		public void run() {
			String line;
			try {
				while (true) {
					line = br.readLine();
					taMessages.append(line + "\n");
				}
			} catch (Exception ex) {
			}
		}
	}
}
