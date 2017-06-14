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
	Timer t_ping = new Timer(1, new Listener_ping());
	static String ip = "";
	int i=0;

	double sinv = 1.411764705882353/57;

	
	public static void main(String[] args)
	{
		try {
			int c = 0;
			
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			
			while(e.hasMoreElements() && c == 0)
			{
			    NetworkInterface n = (NetworkInterface) e.nextElement();
			    String nn = n.getDisplayName();
			    if(!n.isLoopback() && !nn.contains("Virtual") && !nn.contains("ISATAP") && !nn.contains("Interface")) {
			    Enumeration<InetAddress> ee = n.getInetAddresses();
			    while (ee.hasMoreElements() && c == 0)
			    {		    	
			    	InetAddress i = (InetAddress) ee.nextElement();
			    	String tmp = i.getHostAddress();
			    	if(!tmp.contains("127.0.0.1") && !tmp.contains(":") && c == 0) {
			    	 
				    	c++;
				        if(c == 1) {
				        	ip = tmp;
				        }
			    	} 
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
			if(i==256) {
				i=0;
				repaint();
			}
			} 
		}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		System.out.println("Draw called");
		g.drawLine(100, 50, 100, 55);
		g.drawLine(150, 50, 150, 55);
		
	}
	
	public void netping(Graphics g, String subnet, int i) {
					ip = subnet+i;
					Boolean ping=false;
					long Start = 0,End=0;
					try {
				    	Start = System.currentTimeMillis();
						ping = InetAddress.getByName(ip).isReachable(500);
				        End = System.currentTimeMillis();
					} catch (IOException f) {
						// TODO Auto-generated catch block
						f.printStackTrace();
					}
					g.setColor(getBackground());
					g.fillRect(60, 0, 300, 20);
					g.fillRect(100, 85, 100, 15);
					g.setColor(Color.black);
					g.drawString(ip + "   " + (End-Start)+ " ms   ", 60, 20);
					g.setColor(ping?Color.green:Color.red);
					double x = Math.sin(i*sinv)*128;
					double y = -Math.cos(i*sinv)*128;
					//g.drawLine((i%3)*251, 0, 256-(i%350)+100, 475);
					g.drawLine(256, 256, 256+(int)x, 256+(int)y);
					//g.drawLine(256, 256, 256+(int)x+2, 256+(int)y+2);
					g.drawString(ip+"  ", 100, 100);
					g.drawLine((int)Math.round(100+(i/2.55)), 53, (int)Math.round(100+(i/2.55)), 53);

				
	}
	
	
	private class Key extends KeyAdapter {
		public void keyPressed(KeyEvent e){
			getGraphics().setColor(getBackground());;
			getGraphics().fillRect(0, 0, 512, 512);
			getGraphics().setColor(Color.black);
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				//div--;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				//div++;
			}
			
		}
		public void keyReleased(KeyEvent e){
			getGraphics().setColor(getForeground());;
			getGraphics().fillRect(0, 0, 512, 512);
			getGraphics().setColor(Color.black);
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				//div--;
			}
		}
	}
}