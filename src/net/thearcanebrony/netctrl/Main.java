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
	
	Timer t = new Timer(499, new Listener());
	Timer t_ping = new Timer(2, new Listener_ping());
	static String ip = "";
	
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
				    	
				        //g.drawString(tmp, 0, 16*c);
				        
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
			netping(getGraphics(), "127.0.0.");
			}
		}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		System.out.println("Draw called");
		g.drawString("test", 2, 2);
		g.drawLine(100, 50, 100, 55);
		g.drawLine(150, 50, 150, 55);
		
	}
	
	public void netping(Graphics g, String subnet) {
		//String subnet = tmp2[0] + "." + tmp2[1] + "." + tmp2[2] + ".";
				//String subnet = "127.0.0.";
				for (int i = 0; i < 256; i++) { 
					
					ip = subnet+i; 
					
					int ping=20;
					try {
						ping = InetAddress.getByName(ip).isReachable(100)?20:10;
					} catch (IOException f) {
						// TODO Auto-generated catch block
						f.printStackTrace();
					}
					g.setColor(Color.red);
					if(ping==10) {
						g.setColor(Color.GREEN);
					}
					 
					g.drawLine(10+i, 20, 10+i, 20);
					g.setColor(getBackground());
					g.fillRect(100, 85, 85, 15);
					g.setColor(Color.black);
					g.drawString(ip, 100, 100);
					g.drawLine((int)Math.round(100+(i/2.55)), 53, (int)Math.round(100+(i/2.55)), 53);
				}
	}
	
	
	private class Key extends KeyAdapter {
		public void keyPressed(KeyEvent e){
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				ldown = true;
			}
		}
		public void keyReleased(KeyEvent e){
			setlock = false;
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				ldown = false;
			}
		}
	}
}