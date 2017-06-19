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
import java.rmi.server.SkeletonNotFoundException;
import java.util.Arrays;
import java.util.Enumeration;

public class Main extends JPanel {
	Timer t = new Timer(60001, new Listener());
	Timer t_ping = new Timer(1, new Listener_ping());
	static String lip = "";
	int i=0;
	//Color c = new Color(10, 75, 15, 10);
	String a = "";
	Graphics g = getGraphics();

	public static void main(String[] args) {
		JFrame frame = new JFrame("NetCTRL 1.1.4a");
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
								lip = tmp;
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
			
			//String[] tmp = ip.replace(".", "k").split("k");
			//String subnet = tmp[0] + "." + tmp[1] + "." + tmp[2] + ".";

			
			if(Thread.activeCount() < 150) {
				Thread thread = new Thread(new PingThread(), "PingThread "+i);
			thread.start();
			
			
			 
			
			//netping(g, "172.213.12."+ i);//178.116.55
			i++;
			//getGraphics().draw3DRect(0+i, 0+i, 256-i, 256+i, true);		
			if(i==256) {
				i=0;
			}}
		} 
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		System.out.println("Draw called");
		g.drawLine(100, 50, 100, 55);
		g.drawLine(150, 50, 150, 55);
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
	
	private class PingThread implements Runnable{
		Boolean ping=false;
		long Start = 0,End=0; 
		int ti = i;
		Color c = new Color(0, 0, 0, 10);
		String pip = "";
		
		@Override
		  public void run(){  

		  	pip = "81.11.181."+ti;
			  	netping(Main.this.getGraphics(), pip);

			  } 
		

		public void netping(Graphics g, String ip) {
			try {
				Start = System.currentTimeMillis();
				ping = InetAddress.getByName(ip).isReachable(100);
				End = System.currentTimeMillis();
			} catch (IOException f) {
					f.printStackTrace();
			}

			g.setColor(ping?Color.green:Color.red);
			
			draw16(g, ti);
			drawRadar(g, ti);
			drawDbg(g);

			
					
		}
		public void draw16(Graphics g, int i) {
			g.fillRect(16*(ti%16), 150 + Math.round(ti/16)*16, 16, 16);
			g.drawLine((int)Math.round(100+(ti%51)), 53+ti/51, (int)Math.round(100+(ti%51)), 53+ti/51);
		}
		public void drawRadar(Graphics g, int ti) {		
			Color c = new Color(getForeground().getRed()-10, getForeground().getGreen()-10, getForeground().getBlue()-10, 10);
			g.fillArc(27, 27, 66, 66, (int) -(ti* 1.411764705882353), -10);
			g.setColor(c);
			//g.setColor(c);
			g.fillArc(27, 27, 66, 66, 0, 360);
		}
		public void drawDbg(Graphics g) {	
			g.setColor(getBackground());
			g.fillRect(60, 100, 1000, 25);
			//g.fillRect(100, 85, 100, 15);
			
			g.setColor(Color.black);
			g.drawString(pip + "   " + (End-Start)+ " ms  "+ (float) 1000/(End-Start)+"/s", 60, 120);
		}
	}
}