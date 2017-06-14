package net.thearcanebrony.netctrl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class Main extends JPanel {
	
	Timer t = new Timer(60001, new Listener());
	Timer t_ping = new Timer(0, new Listener_ping());
	int div = 57;
	static String ip = "";
	int i=0;
	
	boolean setlock = false;
	boolean rdown, ldown;

	int preset[][] = {{0, 400, 135, 450,1}, {135, 450, 270, 400,1}, {270, 0, 300, 20, 1}, {291, 0, 291, 500, 1}, {-1, 0, 270, 0, 1}, {0, -1, 0, 500, 1}};
	int[][] balls = {{80, 80, 30, 50}, {230, 280, 20, 200}, {50, 200, 25, 100}, {200, 100, 10, 500}};
	int lines[][] = new int[100][5];
	
	public static void main(String[] args)
	{
		try {
			int c = 0;
			
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements() && c == 0)
			{
			    NetworkInterface n = (NetworkInterface) e.nextElement();
			    if(!n.isLoopback() && !n.getDisplayName().contains("Virtual") && !n.getDisplayName().contains("ISATAP") && !n.getDisplayName().contains("Interface")) {
			    Enumeration<InetAddress> ee = n.getInetAddresses();
			    while (ee.hasMoreElements() && c == 0)
			    {
			    	
			    	long Start = System.nanoTime();
			    	
			    	InetAddress i = (InetAddress) ee.nextElement();
			    	String tmp = i.getHostAddress();
			    	String tmp2 = "";
			    	if(!tmp.contains("127.0.0.1") && !tmp.contains(":")) {
			    	 
				    	c++;
				        if(c == 1) {
				        	ip = tmp;
				        	tmp2 = n.getDisplayName();
				        }
			    	} 
			        long End = System.nanoTime();
			        System.out.println(End-Start + " ns   " + tmp + "   " + tmp2 /*n.getDisplayName()*/);
			    }}
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.exit(202);
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	JFrame frame = new JFrame();
	frame.setSize(512, 512);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
	Main panel = new Main();
	frame.add(panel);
	
	
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
			//for (int i = 0; i < 256; i++) { 

			netping(getGraphics(), "127.0.0.", i);//178.116.55
			i++;
			if(i==256)
				i=0;
			}
		}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		System.out.println("Draw called");
		g.drawLine(100, 50, 100, 55);
		g.drawLine(150, 50, 150, 55);
		
	}
	
	public void netping(Graphics g, String subnet, int i) {
		//String subnet = tmp2[0] + "." + tmp2[1] + "." + tmp2[2] + ".";
				//String subnet = "127.0.0.";
		g.setColor(Color.blue);
		double sinv = 1.411764705882353/div;

									g.drawString(div+"", 0, 60);
					ip = subnet+i; 
					
					Boolean ping=false;
					try {
						ping = InetAddress.getByName(ip).isReachable(500);
					} catch (IOException f) {
						// TODO Auto-generated catch block
						f.printStackTrace();
					}
					g.setColor(ping?Color.green:Color.red);
					double x = Math.sin(i*sinv)*128;
					double y = -Math.cos(i*sinv)*128;
					//g.drawLine((i%3)*251, 0, 256-(i%350)+100, 475);
					g.drawLine(256, 256, 256+(int)x, 256+(int)y);
					g.drawLine(256, 256, 256+(int)x+2, 256+(int)y+2);
					g.setColor(getBackground());
					g.fillRect(100, 85, 100, 15);
					g.setColor(Color.black);
					g.drawString(ip+"  "+ping, 100, 100);
					g.drawLine((int)Math.round(100+(i/2.55)), 53, (int)Math.round(100+(i/2.55)), 53);
				
	}
	
	
	private class Key extends KeyAdapter {
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				div--;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				div++;
			}
			
		}
		public void keyReleased(KeyEvent e){
			getGraphics().setColor(getBackground());;
			getGraphics().fillRect(0, 0, 512, 512);
			getGraphics().setColor(Color.black);
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				div--;
			}
		}
	}
}