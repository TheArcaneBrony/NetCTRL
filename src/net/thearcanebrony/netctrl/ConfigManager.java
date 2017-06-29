package net.thearcanebrony.netctrl;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class ConfigManager {
	public static String ver() {
		return "1.2.5a";
	}
	public static String ip() {
		return "81.11.181.";
	}
	public static String lip() {
		String lip = "";
		try {
			int c = 0;
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements() && c == 0) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				String nn = n.getDisplayName();
				if (!n.isLoopback() && !nn.contains("Virtual") && !nn.contains("ISATAP") && !nn.contains("Interface")) {
					Enumeration<InetAddress> ee = n.getInetAddresses();
					while (ee.hasMoreElements() && c == 0) {
						InetAddress i = (InetAddress) ee.nextElement();
						String tmp = i.getHostAddress();
						if (!tmp.contains("127.0.0.1") && !tmp.contains(":") && c == 0) {
							c++;
							if (c == 1) {
								lip = tmp;
							}
						}
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return lip;
	}
}
