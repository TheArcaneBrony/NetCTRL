package net.thearcanebrony.netctrl;

import java.util.Arrays;

public class StringManager {
	//String ver = "Broken";
	String[][] strings = 
						{
						{"title","online","offline"},
						{"NetCTRL " + Main.ver(),"Online", "Offline","Current stats: "},
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
						{"",""},
						{"Invalid language selection!!!",""}
						};

	public String getTranslation(String lang, String key) {
		int ln = 0;
		switch (lang) {
		case "en":
			ln=1;
		case "nl":
			ln=2;
		case "my ass":
			ln=3;
		default:

		}
		
		String str = strings[ln][Arrays.asList(strings[0]).indexOf(key)];
		if(str=="") {
			return "String not found!!" + ln + " " + key;
		}
		return str;
	}

}
