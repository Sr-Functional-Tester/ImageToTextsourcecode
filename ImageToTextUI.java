package com.automation.framework.imagetotext;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ImageToTextUI {
	// private static boolean isTakingScreenshots = false; // Flag to check if
	// screenshots are being taken
	private static Thread screenshotThread; // Thread for taking screenshots
	// private static AutomatedScreenshots screenshotHandler = new
	// AutomatedScreenshots(); // Create an instance of
	// screenshot handler

	public static void main(String[] args) {
		// Create frame
		JFrame frame = new JFrame("Image Edit App");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(340, 200); // Set frame size
		// frame.set

		// Create a panel to display the background image
		JPanel panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// Load and draw the background image
				ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/background.jpg")); // Path to image
				Image img = backgroundImage.getImage();
				g.drawImage(img, 0, 0, getWidth(), getHeight(), this); // Stretch image to fill the entire panel
			}
		};

		// Set the panel as the content pane and use null layout for absolute
		// positioning of components
		frame.setContentPane(panel);
		panel.setLayout(null); // Use null layout to manually position components

		// ImageIcon icon = new
		// ImageIcon(ScreenshotUI.class.getResource("/3tasks.png"));

		// frame.setIconImage(icon.getImage());

		// Make the panel background transparent
		panel.setOpaque(false);

//        panel.setMaximumSize(new Dimension(400,200));
//        panel.setSize(new Dimension(400,200));
//        panel.setAutoscrolls(false);

		// Create checkboxes
		JCheckBox checkbox1 = new JCheckBox("Reduce Image Size");
		JCheckBox checkbox2 = new JCheckBox("Image To Text");
		JCheckBox checkbox3 = new JCheckBox("Image Conversion");
		// checkbox3.setToolTipText("<html>Select this option to organize your
		// files.<br>Choose folder to backup (default is desktop folder)</html>");
		// Set checkbox positions using setBounds(x, y, width, height)
		checkbox1.setBounds(90, 20, 200, 20);
		checkbox2.setBounds(90, 40, 200, 20);
		checkbox3.setBounds(90, 60, 200, 20);

		// Set transparency for checkboxes and change text color for visibility
		checkbox1.setOpaque(false);
		checkbox2.setOpaque(false);
		checkbox3.setOpaque(false);
		checkbox1.setForeground(Color.WHITE); // Set checkbox text color to white
		checkbox2.setForeground(Color.WHITE);
		checkbox3.setForeground(Color.WHITE);

		// Add checkboxes to the panel
		panel.add(checkbox1);
		panel.add(checkbox2);
		panel.add(checkbox3);

		// Create a text field (initially disabled)
		JTextField textField = new JTextField(15);
		textField.setEnabled(false);
		textField.setOpaque(false);
		textField.setText("");
		textField.setForeground(Color.BLACK); // Set text color to white
		textField.setBounds(20, 90, 310, 20);
		; // Position the text field

		 // Create the menu bar with a Help menu
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpMenuItem = new JMenuItem("Help Documentation");
        helpMenuItem.addActionListener(e -> showHelpDialog(frame));
        helpMenu.add(helpMenuItem);
        menuBar.add(helpMenu);
        frame.setJMenuBar(menuBar);
		// Make the text field background transparent and set border
		// textField.setOpaque(false);
		textField.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Add a white border

		// Add the text field to the panel
		panel.add(textField);

		// Create a submit button (initially disabled)
		JButton submitButton = new JButton("Submit");
		submitButton.setEnabled(false); // Initially disable the submit button
		submitButton.setBounds(70, 120, 90, 25); // Position the submit button

		// Make the submit button background transparent, add border, and change text
		// color
		// submitButton.setOpaque(false);
		// submitButton.setContentAreaFilled(false);
		submitButton.setBorderPainted(true);
		submitButton.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Add a white border
		submitButton.setForeground(Color.BLUE); // Set button text color to white

		// Add the submit button to the panel
		panel.add(submitButton);

		// Create a submit button (initially disabled)
		JButton stopButton = new JButton("Stop");
		stopButton.setEnabled(false); // Initially disable the submit button
		stopButton.setBounds(180, 120, 90, 25); // Position the submit button

		// Make the submit button background transparent, add border, and change text
		// color
		// stopButton.setOpaque(false);
		// stopButton.setContentAreaFilled(false);
		stopButton.setBorderPainted(true);
		stopButton.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Add a white border
		stopButton.setForeground(Color.BLUE); // Set button text color to white

		// Add the submit button to the panel
		panel.add(stopButton);

		// Group checkboxes to allow only one selection
		checkbox1.addActionListener(e -> {
			checkbox2.setSelected(false);
			checkbox3.setSelected(false);
			textField.setEnabled(true);
			textField.setText("Image size to reduce(eg.44KB),  source image file path");
			textField.setForeground(Color.WHITE); // Ensure text is white
			submitButton.setEnabled(false); // Enable the submit button when option 1 is selected
			stopButton.setEnabled(false);
		});

		checkbox2.addActionListener(e -> {
			checkbox1.setSelected(false);
			checkbox3.setSelected(false);
			textField.setEnabled(true);
			textField.setText("Enter source image file path");
			textField.setForeground(Color.WHITE); // Ensure text is white
			submitButton.setEnabled(false); // Disable submit button until data is entered
			stopButton.setEnabled(false);
		});

		checkbox3.addActionListener(e -> {
			checkbox1.setSelected(false);
			checkbox2.setSelected(false);
			textField.setEnabled(true);
			textField.setText("Image conversion type(png to jpg), source image path");
			textField.setForeground(Color.WHITE); // Ensure text is white
			submitButton.setEnabled(false); // Disable submit button until data is entered
			stopButton.setEnabled(false);
		});

		// Add focus listener to remove the default message when user clicks into the
		// text field
		textField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textField.getText().contains("image")) {
					textField.setText("");
					textField.setForeground(Color.WHITE); // Ensure text is white when user starts typing
				}
			}
		});

		// Add mouse listener to the text field
		textField.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// If the text field contains placeholder text, clear it when clicked
				if (textField.getText().contains("image")) {
					textField.setText(""); // Clear the text field
					textField.setForeground(Color.WHITE); // Set text color to white
				}
			}
		});

		// Create a MouseListener to detect clicks on the panel
		panel.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				// Get the position of the mouse click
				Point clickPoint = e.getPoint();

				// Check if the click was not on the checkboxes or the submit button
				if (textField.getText().isEmpty() && !checkbox1.getBounds().contains(clickPoint)
						&& !checkbox2.getBounds().contains(clickPoint) && !checkbox3.getBounds().contains(clickPoint)
						&& !textField.getBounds().contains(clickPoint) && !submitButton.getBounds().contains(clickPoint)
						&& !stopButton.getBounds().contains(clickPoint)) {

					// Trigger event (e.g., set text in the text field)
					if (checkbox1.isSelected())
						textField.setText("Image size to reduce(eg.44KB),  source image file path");
					else if (checkbox2.isSelected())
						textField.setText("Enter source image file path");
					else if (checkbox3.isSelected())
						textField.setText("Image conversion type(png to jpg), source image path");
					textField.setForeground(Color.WHITE); // Ensure the text color is white
					if (textField.getText().contains("image")) {
						submitButton.setEnabled(false);
						stopButton.setEnabled(false);
					}
				}
			}
		});

		// Add key listener to remove the default message when the user starts typing
//		textField.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyPressed(KeyEvent e) {
//				if (textField.getText().contains("eg")) {
//					textField.setText("");
//					textField.setForeground(Color.WHITE); // Ensure text is white when user starts typing
//					submitButton.setForeground(Color.BLUE);
//
//				}
//			}
//		});

		// Enable the submit button when the user enters data
		textField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
			@Override
			public void insertUpdate(javax.swing.event.DocumentEvent e) {
				enableSubmitButton();
			}

			@Override
			public void removeUpdate(javax.swing.event.DocumentEvent e) {
				enableSubmitButton();
			}

			@Override
			public void changedUpdate(javax.swing.event.DocumentEvent e) {
				enableSubmitButton();
			}

			private void enableSubmitButton() {
				if (textField.getText().length() > 0) {
					// textField.setText("");
					submitButton.setEnabled(true); // Enable submit button when text is entered
					submitButton.setForeground(Color.BLUE);
					stopButton.setEnabled(true);
				} else {
					submitButton.setEnabled(false);
					stopButton.setEnabled(false);

				}
			}
		});

//        // Add action listener to submit button
//        submitButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String selectedOption = "";
//                if (checkbox1.isSelected()) {
//                    try {
//                    	if(textField.getText().contains("Enter"))
//                    		   textField.setText("");
//                    	AutomatedScreenshots.takeScreenshotByNum(textField.getText());
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                    }
//                } else if (checkbox2.isSelected()) {
//                    try {
//                    	AutomatedScreenshots.takeScreenshotByTime(textField.getText());
//                    } catch (Exception e1) {
//                        e1.printStackTrace();
//                    }
//                } 
//                // Print the result to the console
//                System.out.println("Selected: " + selectedOption);
//            }
//        });

//        stopButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            	AutomatedScreenshots.stopScreenshots(); // Call stopScreenshots() when the button is clicked
//            }
//        });

		// Add action listener to submit button
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String time = textField.getText(); // Get the time from textField
				submitButton.setEnabled(false);
				stopButton.setEnabled(true); // Enable the stop button

				// Start a new thread to take screenshots
				screenshotThread = new Thread(() -> {
					if (checkbox1.isSelected()) {
						 try {
							String status = ReduceImageSize.reduceSize(time);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
					} else if (checkbox2.isSelected()) {
						 try {
								ImageToText.imageToText(time);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} 
					} else if (checkbox3.isSelected()) {
						 try {
								String status = ImageConversion.convertImage(time);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} 
					}
				});
				screenshotThread.start(); // Start the screenshot-taking process
			}
		});

		// Add action listener to stop button
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if (screenshotThread != null && screenshotThread.isAlive()) {
					screenshotThread.interrupt(); // Interrupt the screenshot-taking thread
				}
				stopButton.setEnabled(false); // Disable the stop button after stopping
			}
		});

		// Make the frame visible
		frame.setVisible(true);
		// screenshotHandler = null;
	}
	
	 private static void showHelpDialog(JFrame parentFrame) {
	    	
			String helpContent = "Help Documentation:\n\n"
					+ "1. To use this application, enter your query in the input box.\n"
					+ "2. After typing your query, click the Submit button or press Enter.\n"
					+ "3. The application will process your task and display the result.\n"
					+ "4. Below are the list of commands to be used to achieve tasks automation.\n" 
					+ "   Clear desktop \n"
					+ "   Desktop backup to path c:\\your_backup_folder_name \n" 
					+ "   Clean system \n"
					+ "   Close all apps \n" 
					+ "   Close idle apps \n"
					+ "   Schedule a reminder at 7:00 am about my breakfast \n"
					+ "   Schedule a reminder at 8:00 am next Tuesday about my video call with client \n"
					+ "   Schedule a reminder at 3:30 pm about my meeting \n"
					+ "   Schedule a reminder at 5:00 pm tomorrow about client meeting \n"
					+ "   Shutdown or restart or logout or sleep or hibernate \n"
			        + "   convert to pdf C:\\Documents\\sample.docx \n"
			        + "   merge pdf files into D:\\Documents\\output.pdf from C:\\Documents\\sample1.pdf, C:\\Documents\\sample2.pdf \n"
			        + "   image to text C:\\Documents\\image.png \n"
			        + "   search folder C:\\Documents\\folder with keyword hello world \n"
					+ "   reduce image to size 50KB C:\\Documents\\image.jpg (or you can give .png file as well) \n"
			        + "   convert image from png to jpg C:\\Documents\\image.png \n"
			        + "   convert image from jpg to png C:\\Documents\\image.jpg \n"
			        + "   play youtube video for relaxing music \n"		        
			        ;
			        
			//helpContent=null;
	        JTextArea helpTextArea = new JTextArea(helpContent);
	        helpTextArea.setEditable(false);
	        helpTextArea.setBackground(Color.WHITE);
	        helpTextArea.setFont(new Font("Calibri", Font.BOLD, 17));

	        JScrollPane helpScrollPane = new JScrollPane(helpTextArea);
	        JOptionPane.showMessageDialog(parentFrame, helpScrollPane, "Help", JOptionPane.INFORMATION_MESSAGE);
	    }

}
