package net.thearcanebrony.netctrl;

import java.util.Arrays;

public class StringManager {
	//String ver = "Broken";
	String[][] strings = 
						{
						{"title","online","offline"},
						{"NetCTRL " + ConfigManager.ver() + " | Detected IP: " + ConfigManager.lip(),"Online", "Offline","Current stats: "},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""},
						{"",""}
						};

	public String getTranslation(String lang, String key) {
		int ln = 0;
		switch (lang) {
		case "en":
			ln=1;
			break;
		case "nl":
			ln=2;
			break;
		default:
			return "Invalid language selection!!!";
		}
		
		String str = strings[ln][Arrays.asList(strings[0]).indexOf(key)];
		if(str=="") {
			return "String not found!!" + ln + " " + key;
		}
		return str;
	}

}
