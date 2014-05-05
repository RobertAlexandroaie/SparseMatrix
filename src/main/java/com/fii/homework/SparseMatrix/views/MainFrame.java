/**
 * 
 */

package com.fii.homework.SparseMatrix.views;


import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

/**
 * @author Robert
 */
public class MainFrame extends JFrame {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final JPanel contentPane;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		try {
		    MainFrame frame = new MainFrame();
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
    public MainFrame() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(new BorderLayout(0, 0));
	
	JPanel pnlRadio = new JPanel();
	contentPane.add(pnlRadio, BorderLayout.NORTH);
	
	JRadioButton rdbtnA = new JRadioButton("A");
	pnlRadio.add(rdbtnA);
	
	JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");
	pnlRadio.add(rdbtnNewRadioButton);
    }
    
}
