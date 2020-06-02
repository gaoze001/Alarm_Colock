package clock;

public class Time {
	public static int h=0,m=0,s=0;
	
	Time(int hour,int minute,int second){
		h=hour;
		m=minute;
		s=second;
	}
	
	public static void setHour(int hour) {
		h=hour;
	}
	
	public static int getHour() {
		return h;
	}
	
	public static void setMinute(int minute) {
		m=minute;
	}
	
	public static int getMinute() {
		return m;
	}
	
	public static void setSecond(int second) {
		s=second;
	}
	
	public static int getSecond() {
		return s;
	}
	
	public static void run() {
		if(s==59) {
			s=0;
			if(m==59) {
				m=0;
				if(h==23) {
					h=0;
				}
				else 
					h++;
			}
			else
				m++;
		}
		else
			s++;
	}
}
