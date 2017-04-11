package UserServlet;

import java.text.SimpleDateFormat;

public class StringToDateTime {
	public String dateStart;
	public String dateStop;
	public StringToDateTime(String dateStart,String dateStop){
		this.dateStart=dateStart;
		this.dateStop=dateStop;
	}
	public double calcuTime(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        java.util.Date d1 = null;
        java.util.Date d2 = null;
        double diffHours = 0;
        double diff;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //毫秒ms
            diff = d2.getTime() - d1.getTime();
            diffHours = (diff / (60.0 * 60.0 * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
		return diffHours;
	}

}
