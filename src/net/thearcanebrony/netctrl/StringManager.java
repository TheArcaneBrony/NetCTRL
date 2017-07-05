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

	public String getTranslation(String ln, String key) {
		int lni = 0;
		switch (ln) {
		case "dbg":
			lni=0;
			break;
		case "en":
			lni=1;
			break;
		case "nl":
			lni=2;
			break;
		default:
			return "Invalid language selection!!!";
		}
		
		String str = strings[lni][Arrays.asList(strings[0]).indexOf(key)];
		if(str=="") {
			return "String not found!!" + lni + " " + key;
		}
		return str;
	}

}
