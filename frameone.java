package frames;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
public class frameone extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 45L;
	JFrame frame;
	JPanel cards = new JPanel(new CardLayout());
	CardLayout cardlayout = (CardLayout) cards.getLayout();
	public frameone() {
		setTitle("Hotel Management System");
		setSize(600, 200);
		JPanel main_panel = new JPanel();
		JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		JLabel l1 = new JLabel("Welcome to a Hotel Management System!");
		l1.setForeground(Color.green);
		l1.setFont(new Font("Verdana", 1, 20));
		JPanel PO = panelBuilder(Color.black, "Partners and Owners", 2, new String[]{"View Partnership Details", "View Ownership Details"});
		JPanel BR = panelBuilder(Color.black, "Buildings and Rooms", 3, new String[]{"View Building Details", "View Available Rooms", "View Room Details"});
		JPanel CB = panelBuilder(Color.black, "Customers and Bookings", 2, new String[]{"Customer Details", "Booking Details"});
		JPanel T = panelBuilder(Color.black, "Transactions", 2, new String[] {"Enter New Transaction", "View Previous Transactions"});
		cards.add(main_panel, "Home");
		cards.add(PO, "ParOwnerDetails");
		cards.add(BR, "BuildRoomDetails");
		cards.add(T, "Transactions");
		add(cards);
		cardlayout.show(cards, "Home");
		cards.add(CB, "CustomerBooking");
		main_panel.add(l1);
		String optionText[] = {"Partners and OwnerShips", 
				"Buildings and Rooms", 
				"Customers and Bookings", 
				"Transactions"};
		JButton op[] = new JButton[4];
		for(int i = 0; i < 4; i++) {
			op[i] = new JButton(optionText[i]);
			buttonPanel.add(op[i]);
			op[i].setBackground(Color.green);
		}
		op[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardlayout.show(cards, "ParOwnerDetails");
				setSize(600, 200);
			}
		});
		op[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardlayout.show(cards, "BuildRoomDetails");
				setSize(600, 300);
			}
		});
		op[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardlayout.show(cards, "CustomerBooking");
			}
		});
		op[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardlayout.show(cards, "Transactions");
			}
		});
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		main_panel.add(buttonPanel);
		main_panel.setBackground(Color.black);
		buttonPanel.setBackground(Color.black);
		setVisible(true);
	}
		
	public JPanel panelBuilder(Color color, String label, int buttonquantity, String[] buttonNames) {
		JPanel p = new JPanel(new FlowLayout());
		JLabel l1 = new JLabel(label);
		l1.setFont(new Font("Verdana", 1, 20));
		l1.setForeground(Color.green);
		JPanel main_panel = new JPanel();
		p.add(l1);
		JPanel button_panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		JButton back = new JButton("Back");
		back.setPreferredSize(new Dimension(120, 30));
		int rows = 0;
		int remainingButton = buttonquantity % 2;
		JButton op[] = new JButton[buttonquantity];
		for(int i = 0; i < buttonquantity - remainingButton; i++) {
			op[i] = new JButton(buttonNames[i]);
			op[i].setBackground(Color.green);
			if(i % 2 == 1) {
				c.gridx = 2;
				c.gridy = rows;
				rows += 2;
				button_panel.add(op[i], c);
			}
			else {
				c.gridx = 0;
				c.gridy = rows;
				button_panel.add(op[i], c);
			}
		}
		if(remainingButton == 1) {
			op[buttonquantity-1] = new JButton(buttonNames[buttonquantity-1]);
			op[buttonquantity-1].setBackground(Color.green);
			c.gridx = 1;
			c.gridy = rows;
			button_panel.add(op[buttonquantity-1], c);
		}
		back.setBackground(Color.red);
		JPanel buttonFrame = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		buttonFrame.add(back);
		c.gridx = 1;
		c.gridy = rows + 2;
		button_panel.add(buttonFrame, c);
		button_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		button_panel.setBackground(color);
		main_panel.setBackground(color);
		buttonFrame.setBackground(color);
		main_panel.add(button_panel);
		p.setBackground(color);
		p.add(main_panel);
		back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardlayout.show(cards, "Home");
				setSize(600, 200);
			}
		});
		return p;
	}
}
