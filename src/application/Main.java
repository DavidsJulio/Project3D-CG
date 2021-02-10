package application;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.View;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

public class Main {

	static JFrame frame = new JFrame("Projeto 3D");
	static Java3D projeto;

	
	private static void createAndShowUI() {

		Java3D projeto = new Java3D();
		frame.getContentPane().add(projeto);

		frame.setJMenuBar(Main.methodThatReturnsJMenuBar());
		frame.setPreferredSize(new Dimension(700, 700));
		frame.setTitle("Java3D - Project");
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				createAndShowUI();
			}
		});
	}

	
	public static JMenuBar methodThatReturnsJMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Menu");
		
		JMenuItem inst = new JMenuItem("Instructions");
		inst.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Interaja com os objetos, selecione a carrinha:\n"
						+ "Para poder movimentar a partir das setas do teclado, assim como a tecla P para buzinar! Cuidado ao bater em diferentes objetos!\n"
						+ "Desligue e ligue as luzes a partir do Botão ON/OFF!",
						"Instruções", JOptionPane.INFORMATION_MESSAGE);

			}

		});
		menu.add(inst);
		
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Project 3D - Game for Kids",
						"Informações", JOptionPane.INFORMATION_MESSAGE);
			
			}

		});
		
		
		JMenuItem sair = new JMenuItem("Sair");
		sair.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
		

		
		
		menu.add(about);
		menu.add(new JSeparator());
		menu.add(sair);
		menuBar.add(menu);

		return menuBar;
	}
	
}
