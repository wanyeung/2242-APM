import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class LoginFrame extends JFrame 
{
	private JLabel pinLabel;
	private JPasswordField pinInput;

	private boolean userAuthenticated; // whether user is authenticated
	private int currentAccountNumber; // current user's account number
	private BankDatabase bankDatabase; // account information database
	
	private JPanel north, center, south, west, east; //declare all panels in the ATMFrame container
	private JPanel keypadPanel, actionButtonPanel, dispenserPanel; //declare all panels in south
	private JPanel eastButtonPanel,westButtonPanel; //declare panels for 4 shortcut buttons in each side of screen
	private JPanel cardInsertPanel; //declare panel for card inserter on the most right of screen
	
	private JButton[] keypad;
	private JButton buttonEnter,buttonCancel,buttonClear; //action buttons
	private JButton[][] buttons; //4 shortcut buttons
	
	private JLabel message,withdrawlAmount;
	private String accountNumber,pin;
	private JLabel meassage;
	
	public LoginFrame(BankDatabase atmbankDatabase, String accountNumber) {
		super( "ATM - Login" ); //title for the frame
		atmUser(atmbankDatabase, accountNumber);
		buttons = new JButton[4][2]; //create button 2D array, one column for west, one column for east
		
		//North Panel for ATM title
		north = new JPanel();
			north.setBackground(new Color(0, 0, 0));
		//Create and add ATM title for the north panel 
		JLabel title = new JLabel("ATM Machine");
			title.setFont(new Font("Tahoma", Font.BOLD, 15));
			title.setForeground(new Color(255, 255, 255));
			north.add(title);
		
		//Center Panel for displaying
		centerCreater();
		
		//South Panel for keypad, buttons and cash withdrawal slot
		south = new JPanel();
			south.setBorder(new LineBorder(new Color(0, 0, 0), 3, true));
			south.setLayout(new BoxLayout(south, BoxLayout.X_AXIS));
		//Create and add keypad, buttons and cash withdrawal slot
		keypadPanelCreater();
		actionButtonPanelCreater();
		dispenserPanel = new JPanel();
		dispenserPanel = slotPanelCreater("Withdrawl Cash Slot", false);
		south.add(dispenserPanel);
		
		//West Panel for four shortcut Buttons
		west = new JPanel();
			west.setBorder(new MatteBorder(0, 10, 0, 0, (Color) new Color(0, 0, 0)));
			west.setLayout(new GridLayout(0, 1, 0, 5));
		//Create and add four shortcut Buttons
		westButtonPanel = new JPanel(); 
			westButtonPanel = buttonPanelCreater(0);
			west.add(westButtonPanel);
			
		//East Panel for four shortcut Buttons, and cash inserter
		east = new JPanel();
			east.setBorder(new MatteBorder(0, 0, 0, 10, (Color) new Color(0, 0, 0)));
			east.setLayout(new BoxLayout(east, BoxLayout.X_AXIS));
		//Create and add four shortcut Buttons
		eastButtonPanel = new JPanel(); 
			eastButtonPanel = buttonPanelCreater(1);
			east.add(eastButtonPanel);
		//Create and add inserter 
		cardInsertPanel = new JPanel();
			cardInsertPanel = slotPanelCreater("Inserted", true);
			cardInsertPanel.getComponent(1).setBackground(new Color(140, 40, 250));
			cardInsertPanel.setLayout(new GridLayout(4, 1, 0, 6));
			east.add(cardInsertPanel);
			
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
		add(west, BorderLayout.WEST);
		add(east, BorderLayout.EAST);
	}

	private void centerCreater() {
		center = new JPanel();
			center.setBackground(new Color(0, 0, 128));
			center.setBorder(new LineBorder(new Color(192, 192, 192), 10, true));
			center.setLayout(new GridLayout(0, 1, 0, 10));
			
	//Create and add display message for the Center panel
		meassage = new JLabel("Your Account Number: " + accountNumber);
			meassage.setFont(new Font("Tahoma", Font.BOLD, 15));
			meassage.setForeground(new Color(255, 255, 255));
				center.add(meassage);
			
		pinLabel = new JLabel("Enter your PIN:");
			pinLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
			pinLabel.setForeground(new Color(255, 255, 255));
			center.add(pinLabel);
		
		pinInput = new JPasswordField(5);
			pinInput.setFocusable(false);
			center.add(pinInput);
		
		message = new JLabel(" | Enter to continue | Cancel to take your card | Clear to remove |");
			message.setFont(new Font("Tahoma", Font.BOLD, 10));
			message.setForeground(new Color(255, 255, 255));
			center.add(message);
	}
	
	private void keypadPanelCreater() {
		keypadPanel = new JPanel(); //set up panel
		keypadPanel.setBorder(new CompoundBorder(new EmptyBorder(20, 20, 20, 20), new LineBorder(new Color(128, 128, 128), 3, true)));
		keypadPanel.setLayout(new GridLayout(0, 3, 5, 10));
	
		keypad = new JButton[12]; //create button array
		for (int i = 0; i < keypad.length; i++) {
			if(i < 9) 
				keypad[i] = new JButton("" + (i+1));
			else if(i == 9){
				keypad[9] = new JButton(".");
				keypad[10] = new JButton("0");
				keypad[11] = new JButton("00");
			}
			
			keypad[i].setBackground(new Color(220, 220, 220));
			keypad[i].setFont(new Font("Tahoma", Font.BOLD, 20));
			keypad[i].addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(e.getSource() != keypad[9] && e.getSource() != keypad[11] 
								&& pin.length()<5){
									pin += e.getActionCommand();
									pinInput.setText(pin);
							}
						}		
					}
			);
			keypadPanel.add(keypad[i]);
		}
		
		south.add(keypadPanel);
	}
	
	private void atmUser(BankDatabase atmbankDatabase, String atmAccountNumber) {
		userAuthenticated = false; // user is not authenticated to start
	    currentAccountNumber = 0; // no current account number to start
	    bankDatabase = atmbankDatabase; // create account info database
	    accountNumber = atmAccountNumber;
	    pin = "";
	}
	
	private void actionButtonPanelCreater() {
		actionButtonPanel = new JPanel();
		actionButtonPanel.setBorder(new EmptyBorder(20, 0, 50, 20));
		actionButtonPanel.setLayout(new GridLayout(0, 1, 0, 20));
		
		//Create enter buttons with styles
		buttonEnter = new JButton("Enter");
			buttonEnter.setBackground(new Color(0, 128, 0));

		//Create cancel buttons with styles
		buttonCancel = new JButton("Cancel");
			buttonCancel.setBackground(new Color(165, 42, 42));

		//Create clear buttons with styles
		buttonClear = new JButton("Clear");
			buttonClear.setBackground(new Color(192, 192, 192));
		
		JButton[] actionButtons = new JButton[] {buttonEnter,buttonCancel,buttonClear}; 
		for (int i = 0; i < actionButtons.length; i++) {
			actionButtons[i].setForeground(new Color(255, 255, 255)); //font color
			actionButtons[i].setFont(new Font("Tahoma", Font.BOLD, 20)); //font style
			actionButtons[i].addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(e.getSource() == buttonEnter) {
								authenticateUser();
							}
							if(e.getSource() == buttonCancel) {
								QuitFrame quitFrame = new QuitFrame(bankDatabase); // create ATMFrame
									quitFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
									quitFrame.setSize( 700, 500 ); // set frame size
									quitFrame.setVisible( true ); // display frame
								 
								setVisible(false);
								dispose();
							}
							if(e.getSource() == buttonClear) {
								pin = "";
								pinInput.setText(pin);
							}
						}
					}
				);
			
			//Attach all buttons to the panel
			actionButtonPanel.add(actionButtons[i]);
			
		}
		
		south.add(actionButtonPanel);
	}
	
	private JPanel buttonPanelCreater(int region) { 
		JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(0, 1, 0, 5));
		
		for (int i = 0; i < buttons.length; i++) {
				buttons[i][region] = new JButton("     ");
				buttons[i][region].setBackground(new Color(192, 192, 192));
				buttons[i][region].setFont(new Font("Tahoma", Font.BOLD, 20));
				buttonPanel.add(buttons[i][region]);
			}
		
		return buttonPanel;
	}
	
	
	private JPanel slotPanelCreater(String labelText, boolean isWithdrawl) {
		JPanel slotPanel = new JPanel();
		slotPanel.setBorder(new EmptyBorder(10, 10, 70, 10));
		slotPanel.setLayout(new GridLayout(0, 1, 0, 5));
		
		//text label for instruction
		JLabel outerText = new JLabel(labelText);
			outerText.setHorizontalAlignment(SwingConstants.CENTER);
			
		//slot to stimulate real slot
		JPanel slot = new JPanel(new FlowLayout(FlowLayout.CENTER,60,0));
			slot.setBorder(new LineBorder(new Color(192, 192, 192), 5, true));
			slot.setBackground(new Color(0, 0, 0));
			slot.setForeground(new Color(64, 0, 128));
		
		//text within the slot
		if(!isWithdrawl) {
			withdrawlAmount  = new JLabel(" ");
			withdrawlAmount.setForeground(new Color(255, 255, 255));
			withdrawlAmount.setHorizontalAlignment(SwingConstants.CENTER);
			slot.add(withdrawlAmount);
		}
		else {
			JLabel innerText = new JLabel(" ");
			innerText.setHorizontalAlignment(SwingConstants.CENTER);
			slot.add(innerText);
		}
		
		slotPanel.add(outerText);
		slotPanel.add(slot);
			
		return slotPanel;	
	}
	
	// attempts to authenticate user against database
	   private void authenticateUser() 
	   {
	      // set userAuthenticated to boolean value returned by database
		  try {
			  userAuthenticated = 
				         bankDatabase.authenticateUser( Integer.parseInt(accountNumber), 
				        		 Integer.parseInt(pin) );
			  
			// check whether authentication succeeded
		      if ( userAuthenticated )
		      {
		         currentAccountNumber = Integer.parseInt(accountNumber); // save user's account #
		         ATMFrame atmFrame = new ATMFrame(currentAccountNumber, bankDatabase ); // create ATMFrame
		         atmFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		         atmFrame.setSize( 700, 500 ); // set frame size
		         atmFrame.setVisible( true ); // display frame
		         
		         setVisible(false);
		      } // end if
		      else {
		    	  if(pin.length()!= 5) 
		    		  throw new Exception();
		    	  
		    	  JOptionPane.showMessageDialog(null, "Invalid Account number or PIN. Please try again.", "Alert", JOptionPane.ERROR_MESSAGE);
		    	  pin = "";
		    	  pinInput.setText(pin);
		      }
		  } 
		  catch(Exception e) {
			  JOptionPane.showMessageDialog(null, "Please make sure you Account no. and PIN are 5 digits seperately.", "Alert", JOptionPane.ERROR_MESSAGE);
	    	  pin = "";
	    	  pinInput.setText(pin);
		  }
		  
	   } // end method authenticateUser
}
