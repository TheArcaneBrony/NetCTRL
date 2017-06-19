package net.thearcanebrony.netctrl;

import java.util.Arrays;

public class StringManager {
	Main m = new Main();
	String[][] strings = 
						{
						{"title",""},
						{"NetCTRL " + m.ver,""},
						{"",""},
						{"",""}
						};
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public String getTranslation(String lang, String key) {
		int ln=0;
		switch (lang) {
		case "en":
			ln=1;

		default:
			break;
		}
		
		String str = strings[ln][Arrays.asList(strings[0]).indexOf(key)];
		if(str=="") {
			return "String not found!!";
		}
		return str;
	}

}
