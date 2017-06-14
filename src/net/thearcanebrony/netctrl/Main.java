package net.thearcanebrony.netctrl;

import javax.swing.*;
import javax.swing.colorchooser.ColorChooserComponentFactory;
import javax.swing.plaf.ColorChooserUI;
import javax.swing.text.AttributeSet.ColorAttribute;
import javax.swing.text.StyleConstants.ColorConstants;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Main extends JPanel {
	Timer t = new Timer(60001, new Listener());
	Timer t_ping = new Timer(1, new Listener_ping());
	static String ip = "";
	int i=0;
	double sinv = 1.411764705882353/57;

	public static void main(String[] args) {
		JFrame frame = new JFrame("NetCTRL 1.0.0a");
		frame.setSize(512, 512);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Main panel = new Main();
		frame.add(panel);
		try {
			int c = 0;
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements() && c == 0) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				String nn = n.getDisplayName();
				if(!n.isLoopback() && !nn.contains("Virtual") && !nn.contains("ISATAP") && !nn.contains("Interface")) {
					Enumeration<InetAddress> ee = n.getInetAddresses();
					while (ee.hasMoreElements() && c == 0) {
						InetAddress i = (InetAddress) ee.nextElement();
						String tmp = i.getHostAddress();
						if(!tmp.contains("127.0.0.1") && !tmp.contains(":") && c == 0) {
							c++;
							if(c == 1) {
								ip = tmp;
							}
						} 
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.exit(202);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public Main(){
		super();
		t.start();
		t_ping.start();
		addKeyListener(new Key());
		setFocusable(true);
	}
	
	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			repaint();				
		}
	}
	
	private class Listener_ping implements ActionListener {
		public void actionPerformed(ActionEvent e){
			Graphics g = getGraphics();
			netping(g, "127.0.0."+ i);//178.116.55
			i++;
			g.setColor(Color.YELLOW);
			//getGraphics().draw3DRect(0+i, 0+i, 256-i, 256+i, true);		
			if(i==256) {
				i=0;
			}
		} 
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		System.out.println("Draw called");
		g.drawLine(100, 50, 100, 55);
		g.drawLine(150, 50, 150, 55);
	}
	
	public void netping(Graphics g, String ip) {
		Boolean ping=false;
		long Start = 0,End=0;
		try {
			Start = System.currentTimeMillis();
			ping = InetAddress.getByName(ip).isReachable(500);
			End = System.currentTimeMillis();
		} catch (IOException f) {
				f.printStackTrace();
		}
		g.setColor(getBackground());
		g.fillRect(60, 0, 300, 20);
		g.fillRect(100, 85, 100, 15);
		g.setColor(Color.black);
		g.drawString(ip + "   " + (End-Start)+ " ms   ", 60, 20);
		g.setColor(ping?Color.green:Color.red);
		int x = (int) (60+Math.sin(i*sinv)*32);
		int y = (int) (60-Math.cos(i*sinv)*32);
		//g.drawLine((i%3)*251, 0, 256-(i%350)+100, 475);
		g.drawLine(60, 60, x, y);
		for (int k = 60; k > 0; k--) {
			int x2 = (int) (60+Math.sin((i-k)*sinv)*32);
			int y2 = (int) (60-Math.cos((i-k)*sinv)*32);
			Color c = new Color(200, 255, 200, 25).brighter();
			g.setColor(c);
			g.drawLine(60, 60, x2, y2);
		}
		g.drawString(ip+"  ", 100, 100);
		g.drawLine((int)Math.round(100+(i/2.55)), 53, (int)Math.round(100+(i/2.55)), 53);
	}
	
	private class Key extends KeyAdapter {
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
			}
		}
		public void keyReleased(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
			}
		}
	}
}