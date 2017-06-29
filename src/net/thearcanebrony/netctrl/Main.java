package net.thearcanebrony.netctrl;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Main extends JPanel {
	static ConfigManager cfg = new ConfigManager();
	static StringManager sm = new StringManager();

	Timer t = new Timer(60001, new Listener()), t_ThreadChecker = new Timer(2000, new ThreadChecker()),t_avgcalc = new Timer(2, new Listener_avgcalc());
	static String lip, a = "";
	int i, avg = 0;
	static Graphics g = null;
	static Graphics2D g2 = (Graphics2D) g;
	List<Integer> pingtime = new ArrayList<Integer>();
	Thread fgw = new Thread(new ForegroundWorker(), "ForegroundWorker");

	public static void main(String[] args) {
		JFrame frame = new JFrame(sm.getTranslation("en", "title"));
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		frame.setSize(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight() / 2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Main panel = new Main();
		frame.add(panel);
		String tmp = frame.getTitle().split("NetCTRL " + ConfigManager.ver() + " | Detected IP: ")[1];
		tmp = tmp.replace(".", "f").substring(0, tmp.length() + tmp.lastIndexOf("f"));
		frame.setTitle(frame.getTitle() + "/" + tmp);
	}

	public Main() {
		super();
		addKeyListener(new Key());
		setFocusable(true);
		//t.start();
		t_ThreadChecker.start();
		t_avgcalc.start();
		fgw.start();
	}

	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	}

	private class ThreadChecker implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!fgw.isAlive()) {
				System.out.println("Starting ForegroundWorker thread");
				fgw.start();
			}
		}
	}

	private class Listener_avgcalc implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (pingtime.size() > 0) {
				int sum = 0;
				if (pingtime.size() > 150) {
					pingtime.remove(1);
				}
				try {
					for (int d : pingtime)
						sum += d;
				} catch (Exception exc) {
					exc.printStackTrace();
				}
				avg = sum / pingtime.size();
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	private class Key extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			}
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			}
		}
	}

	private class ForegroundWorker implements Runnable {
		@Override
		public void run() {
			g = getRootPane().getGraphics();
			while (true) {
				g.drawLine(100, 50, 100, 55);
				g.drawLine(150, 50, 150, 55);
				if (Thread.activeCount() < 150) {
					Thread thread = new Thread(new PingThread(), "PingThread " + i);
					thread.start();
					i++;
					if (i == 256) {
						i = 0;
					}
				}
			}
		}
	}

	private class PingThread implements Runnable {
		Boolean ping = false;
		long Start = 0, End = 0;
		int ti = i;
		String pipi = "";

		@Override
		public void run() {
			pipi = ConfigManager.ip() + ti;// 81.11.181
			netping(Main.this.getGraphics(), pipi);
			
		}

		public void netping(Graphics g, String pip) {
			Thread.currentThread().setName(Thread.currentThread().getName() + " P");
			try {
				Start = System.currentTimeMillis();
				ping = InetAddress.getByName(pip).isReachable(500);
				End = System.currentTimeMillis();
			} catch (IOException f) {
				System.out.println(f.getMessage());
			}
			Thread.currentThread().setName(Thread.currentThread().getName() + " D");
			g.setColor(ping ? Color.green : Color.red);
			draw16(g, ti);
			drawRadar(g, ti);
			drawDbg(g);
		}

		public void draw16(Graphics g, int i) {
			g.fillRect(16 * (ti % 16), 150 + Math.round(ti / 16) * 16, 16, 16);
			g.drawLine((int) Math.round(100 + (ti % 51)), 53 + ti / 51, (int) Math.round(100 + (ti % 51)),
					53 + ti / 51);
		}

		public void drawRadar(Graphics g, int ti) {
			Color c = new Color(getForeground().getRed() - 10, getForeground().getGreen() - 10,
					getForeground().getBlue() - 10, 10);
			g.fillArc(27, 27, 66, 66, (int) -(ti * 1.411764705882353), -10);
			g.setColor(c);
			g.fillArc(27, 27, 66, 66, 0, 360);
		}

		public void drawDbg(Graphics g) {
			g.setColor(getBackground());
			g.fillRect(60, 100, 1000, 25);
			g.fillRect(301, 10, 1000, 10);
			g.drawRect(100, 5, 50, 20);
			g.setColor(Color.black);
			int pingi = (int) (End - Start);
			g.drawString(pipi + "   " + pingi + " ms  " + (float) 1000 / pingi + "/s", 60, 120);
			if (pingi > 0) {
				pingtime.add((int) (1000 / pingi));
			} else;
			g.fillRect(300, 10, avg, 10);

		}
	}
}