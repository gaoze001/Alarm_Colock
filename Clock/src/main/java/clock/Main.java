package clock;


public class Main {

    public static void main(String[] args) {

        Interface window = new Interface();
//		window.AlarmClocks_initialize();
        window.frame.setVisible(true);
        window.count.run();
        try {
            SqliteUtil.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

