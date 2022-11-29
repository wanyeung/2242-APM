import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JOptionPane;

public class WithdrawalCardFrame extends JFrame
{
	private int currentAccountNumber; // current user's account number
	private BankDatabase bankDatabase; // account information database
	private int note100Required,note500Required,note1000Required;
	
	private JPanel north, center, south, west, east; //declare all panels in the ATMFrame container
	private JPanel keypadPanel, actionButtonPanel, dispenserPanel; //declare all panels in south
	private JPanel eastButtonPanel,westButtonPanel; //declare panels for 4 shortcut buttons in each side of screen
	private JPanel cardInsertPanel; //declare panel for card inserter on the most right of screen
	
	private JButton[] keypad;
	private JButton buttonEnter,buttonCancel,buttonClear; //action buttons
	private JButton[][] buttons; //4 shortcut buttons
	
	private JLabel message1,message2;
	private JLabel withdrawlAmount;
	
	public WithdrawalCardFrame(int userAccountNumber, BankDatabase atmBankDatabase, int amount100, int amount500, int amount1000) {
		super( "ATM - Take Your Card " ); //title for the frame
		buttons = new JButton[4][2]; //create button 2D array, one column for west, one column for east
		currentAccountNumber = userAccountNumber; // no current account number to start
	    bankDatabase = atmBankDatabase; // create account info database
	    note100Required = amount100;
	    note500Required = amount500;
	    note1000Required = amount1000;
	
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
			cardInsertPanel = slotPanelCreater("Take Your Card Here", true);
			cardInsertPanel.setLayout(new GridLayout(4, 1, 0, 6));
			cardInsertPanel.getComponent(1).setBackground(new Color(209, 185, 250));
			cardInsertPanel.getComponent(1).addMouseListener(
					new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							
							CashDispenseFrame cashDispenseFrame = new CashDispenseFrame(currentAccountNumber, bankDatabase,
									note100Required, note500Required ,note1000Required); // create ATMFrame
								cashDispenseFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
								cashDispenseFrame.setSize( 700, 500 ); // set frame size
								cashDispenseFrame.setVisible( true ); // display frame
						 
							setVisible(false);
							dispose();
						}
					}
				);
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
		center.setLayout(new GridLayout(4, 1, 0, 10));
	//Create and add display message for the Center panel
		message1 = new JLabel("Your cash will be dispensed after taking card.");
			message1.setFont(new Font("Tahoma", Font.BOLD, 15));
			message1.setForeground(new Color(255, 255, 255));
			center.add(message1);
	
		message2 = new JLabel("Please take your card");
		message2.setFont(new Font("Tahoma", Font.BOLD, 15));
		message2.setForeground(new Color(255, 255, 255));
		center.add(message2);
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
			keypadPanel.add(keypad[i]);
		}
		
		south.add(keypadPanel);
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
			JLabel innerText = new JLabel("Card ejected");
			innerText.setHorizontalAlignment(SwingConstants.CENTER);
			slot.add(innerText);
			slot.setLayout(new FlowLayout(FlowLayout.CENTER,25,0));
		}
		
		slotPanel.add(outerText);
		slotPanel.add(slot);
			
		return slotPanel;	
	}
}
