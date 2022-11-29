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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class TransferAmountFrame extends JFrame {
    private JTextField amountInput;
    private int currentAccountNumber; // current user's account number
    private int transfereeAccountNumber;
    private double amount;
    private BankDatabase bankDatabase;

    private JPanel north, center, south, west, east; //declare all panels in the ATMFrame container
    private JPanel keypadPanel, actionButtonPanel, dispenserPanel; //declare all panels in south
    private JPanel eastButtonPanel, westButtonPanel; //declare panels for 4 shortcut buttons in each side of screen
    private JPanel cardInsertPanel; //declare panel for card inserter on the most right of screen

    private JButton[] keypad;
    private JButton buttonEnter, buttonCancel, buttonClear; //action buttons
    private JButton[][] buttons; //4 shortcut buttons

    private JLabel message[], withdrawlAmount;

    private String input;

    public TransferAmountFrame(int userAccountNumber, BankDatabase atmBankDatabase, int transfereeAccountNo) {
        super("ATM - Transfer to Account " + transfereeAccountNo); //title for the frame
        currentAccountNumber = userAccountNumber;
        bankDatabase = atmBankDatabase; // create account info database
        transfereeAccountNumber = transfereeAccountNo;
        input = "";

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
        dispenserPanel = slotPanelCreater("Withdrawal Cash Slot", false);
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
        center.setLayout(new GridLayout(4, 1, 0, 10));

        //Create and add display message for the Center panel
        message = new JLabel[3];

        message[0] = new JLabel("Enter transfer amount (in HKD): ");
        message[0].setFont(new Font("Tahoma", Font.BOLD, 15));
        message[0].setForeground(new Color(255, 255, 255));
        center.add(message[0]);

        message[1] = new JLabel("Tips: Amount with 2 decimal places are allowed.");
        message[1].setFont(new Font("Tahoma", Font.BOLD, 10));
        message[1].setForeground(new Color(255, 255, 255));
        center.add(message[1]);

        amountInput = new JTextField();
        amountInput.setFocusable(false);
        center.add(amountInput);

        message[2] = new JLabel(" | Enter to Continue | Cancel to Return | Clear to enter again |");
        message[2].setFont(new Font("Tahoma", Font.BOLD, 10));
        message[2].setForeground(new Color(255, 255, 255));
        center.add(message[2]);
    }

    private void keypadPanelCreater() {

        keypadPanel = new JPanel(); //set up panel
        keypadPanel.setBorder(new CompoundBorder(new EmptyBorder(20, 20, 20, 20), new LineBorder(new Color(128, 128, 128), 3, true)));
        keypadPanel.setLayout(new GridLayout(0, 3, 5, 10));

        keypad = new JButton[12]; //create button array
        for (int i = 0; i < keypad.length; i++) {
            if (i < 9)
                keypad[i] = new JButton("" + (i + 1));
            else if (i == 9) {
                keypad[9] = new JButton(".");
                keypad[10] = new JButton("0");
                keypad[11] = new JButton("00");
            }

            keypad[i].setBackground(new Color(220, 220, 220));
            keypad[i].setFont(new Font("Tahoma", Font.BOLD, 20));
            keypad[i].addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        if (input.length() == 0) {
                            if (e.getSource() != keypad[9]) {
                                input += e.getActionCommand();
                                amountInput.setText(input);
                            }
                        } else if (!input.contains(".")) {
                            input += e.getActionCommand();
                            amountInput.setText(input);
                        } else {
                            if (e.getSource() != keypad[9] &&
                                input.length() - input.indexOf('.') <= 2) {
                                input += e.getActionCommand();
                                amountInput.setText(input);
                            }
                        }
                    }
                }
            );
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

        JButton[] actionButtons = new JButton[] {
            buttonEnter,
            buttonCancel,
            buttonClear
        };
        for (int i = 0; i < actionButtons.length; i++) {
            actionButtons[i].setForeground(new Color(255, 255, 255)); //font color
            actionButtons[i].setFont(new Font("Tahoma", Font.BOLD, 20)); //font style
            actionButtons[i].addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == buttonEnter) {

                            if (!isValidateAmount(input) || !isAmountsAvaliable(amount)) {
                                input = "";
                                amount = 0;
                                amountInput.setText(input);
                            } else
                                transfer();

                        }

                        if (e.getSource() == buttonCancel)
                            quit();

                        if (e.getSource() == buttonClear) {
                            input = "";
                            transfereeAccountNumber = 0;
                            amountInput.setText(input);
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
        JPanel slot = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 0));
        slot.setBorder(new LineBorder(new Color(192, 192, 192), 5, true));
        slot.setBackground(new Color(0, 0, 0));
        slot.setForeground(new Color(64, 0, 128));

        //text within the slot
        if (isWithdrawl) {
            withdrawlAmount = new JLabel(" ");
            withdrawlAmount.setForeground(new Color(255, 255, 255));
            withdrawlAmount.setHorizontalAlignment(SwingConstants.CENTER);
            slot.add(withdrawlAmount);
        } else {
            JLabel innerText = new JLabel("");
            innerText.setHorizontalAlignment(SwingConstants.CENTER);
            slot.add(innerText);
        }

        slotPanel.add(outerText);
        slotPanel.add(slot);

        return slotPanel;
    }

    private boolean isValidateAmount(String input) {
        try {
            if (Double.parseDouble(input) == 0) {
                JOptionPane.showMessageDialog(null, "Plese input amount bigger than 0.", "Alert", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                amount = Double.parseDouble(input);
                return true;
            }

        } catch (Exception e) {
            if (input == "") {
                JOptionPane.showMessageDialog(null, "Plese use keypad to input amount.", "Alert", JOptionPane.ERROR_MESSAGE);
                return false;
            } else JOptionPane.showMessageDialog(null, "Please input a smaller number", "Alert", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }


    private boolean isAmountsAvaliable(double amount) {
        // get available balance of account involved
        double availableBalance = bankDatabase.getAvailableBalance(currentAccountNumber);

        if (amount > availableBalance) {
            if (availableBalance == 0) {
                JOptionPane.showMessageDialog(null, "No cash available in your account.", "Alert", JOptionPane.ERROR_MESSAGE);
                quit();
            } else {
                JOptionPane.showMessageDialog(null, "Insufficient funds in your account. \nPlease choose a smaller amount.", "Alert", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        return true;
    }

    private void quit() {
        TransferAccountFrame transferAccountFrame = new TransferAccountFrame(currentAccountNumber, bankDatabase); // create ATMFrame
        transferAccountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        transferAccountFrame.setSize(700, 500); // set frame size
        transferAccountFrame.setVisible(true); // display frame

        setVisible(false);
        dispose();
    }

    private void transfer() {

        TransferConfirmFrame transferConfirmFrame = new TransferConfirmFrame(currentAccountNumber, bankDatabase, transfereeAccountNumber, amount); // create ATMFrame
        transferConfirmFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        transferConfirmFrame.setSize(700, 500); // set frame size
        transferConfirmFrame.setVisible(true); // display frame

        setVisible(false);
        dispose();
    }
}