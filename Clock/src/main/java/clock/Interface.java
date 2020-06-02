package clock;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.DateFormat;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.util.Date;


public class Interface {

	public static JFrame jframe;
	public static JFrame frame;
	public static JTextField textField,Date,alarmClock_hour,alarmClock_minute;
	ImageIcon img;
	JLabel background;
	JLabel alarmClockTip,alarmClockTip,alarmClock_Hour,alarmClock_Minute;
	JPanel background_p;
    private static TrayIcon trayIcon = null;
    static SystemTray tray = SystemTray.getSystemTray();

	public Boolean control = false;
	public int time_interval=0;
	public static int Hour=0,Minute=0;
	
	Date now = new Date();
	DateFormat date = DateFormat.getDateTimeInstance(); 
	
	public Count count=new Count();

	private static PlayUtil playUtil = new PlayUtil();
	
	//��ʼ����
	@SuppressWarnings("deprecation")
	public Interface() {
		Time.setHour(now.getHours());
		Time.setMinute(now.getMinutes());
		Time.setSecond(now.getSeconds());
        initialize();
    }
	
	private void initialize() {
		frame=new JFrame("����ʱ��");
		frame.setBounds(100, 100, 480, 650);
		frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        img = new ImageIcon("Clock\\images\\background.png");
        background = new JLabel(img);
        background_p = new JPanel();
        background_p.setLayout(null);
        background_p.setBounds(0, 0, 480, 650);
        background.setBounds(0, 0, 480, 650);
        background_p.setOpaque(false);
        background_p.add(background);
        frame.getContentPane().add(background_p);
        
        textField = new JTextField();
        textField.setBounds(70, 198, 344, 80);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        textField.setBackground(Color.white);
        textField.setOpaque(false);
        textField.setBorder(null);
        textField.setFont(new java.awt.Font("Dialog", 1, 50));
        
        JTextField Date = new JTextField();
        Date.setBounds(70, 148, 200, 50);
        frame.getContentPane().add(Date);
        Date.setBackground(Color.white);
        Date.setOpaque(false);
        Date.setBorder(null);
        Date.setText("2019-9-1");
        Date.setFont(new java.awt.Font("Dialog", 1, 30));
        
        JButton buttonStart = new JButton("��ʼ");
        buttonStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	count.resumeThread();
            }
        });
        buttonStart.setBounds(82, 305, 150, 26);
        frame.getContentPane().add(buttonStart);
        
        JButton buttonStop = new JButton("��ͣ");
        buttonStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {                
                count.pauseThread();             
            }
        });
        buttonStop.setBounds(252, 305, 150, 26);
        frame.getContentPane().add(buttonStop);
        
        //��ʼ��ʱ�䣻
        Interface.setTime();
        
        //���ӹ��ܿ飻
        alarmClockTip = new JLabel("�������������ӵ�ʱ�֣��������������ӡ�������");
        alarmClockTip.setBounds(80, 360, 360, 26);
        alarmClockTip.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClockTip.setOpaque(false);
        alarmClockTip.setBorder(null);
        frame.getContentPane().add(alarmClockTip);
        
        alarmClock_Hour =new JLabel("ʱ��");
        alarmClock_Hour.setBounds(80, 420, 40, 26);
        alarmClock_Hour.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClock_Hour.setOpaque(false);
        alarmClock_Hour.setBorder(null);
        frame.getContentPane().add(alarmClock_Hour);
        
        alarmClock_hour = new JTextField("Null");
        alarmClock_hour.setBounds(120, 420, 60, 26);
        alarmClock_hour.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClock_hour.setBorder(null);
        frame.getContentPane().add(alarmClock_hour);
        
        alarmClock_Minute =new JLabel("�֣�");
        alarmClock_Minute.setBounds(190, 420, 40, 26);
        alarmClock_Minute.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClock_Minute.setOpaque(false);
        alarmClock_Minute.setBorder(null);
        frame.getContentPane().add(alarmClock_Minute);
        
        alarmClock_minute = new JTextField("Null");
        alarmClock_minute.setBounds(230, 420, 60, 26);
        alarmClock_minute.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClock_minute.setBorder(null);
        frame.getContentPane().add(alarmClock_minute);
        
        alarmClockTip = new JLabel("������δ������");
        alarmClockTip.setBounds(80, 480, 360, 26);
        alarmClockTip.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClockTip.setOpaque(false);
        alarmClockTip.setBorder(null);
        frame.getContentPane().add(alarmClockTip);
        
        final JButton alarmClock = new JButton("��������");
        alarmClock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	control = !control;
            	if(control) {
            		if(!alarmClock_hour.getText().equals("Null")&&!alarmClock_minute.getText().equals("Null")) {
            			String str_hour = alarmClock_hour.getText();
                		Hour = Integer.parseInt(str_hour);
                		String str_minute = alarmClock_minute.getText();
                		Minute = Integer.parseInt(str_minute);
                		alarmClock.setText("�ر�����");
                		count.Window = true;
                		alarmClockTip.setText("�����ѿ�����");
            		}
            		else {
            			alarmClockTip.setText("�����������������룡");
            			control = !control;
            		}
            	}
            	else {
            		alarmClock.setText("��������");
            		alarmClockTip.setText("�����ѹرգ�");
                    playUtil.closeAll();
            	}
            }
        });
        alarmClock.setBounds(300, 420, 100, 26);
        frame.getContentPane().add(alarmClock);
        frame.addWindowListener(new WindowAdapter() { // ���ڹر��¼�
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            };
            public void windowIconified(WindowEvent e) { // ������С���¼�
                frame.setVisible(false);
                Interface.miniTray();

            }

        });

	}
    private static void miniTray(){ // ������С��������������

        ImageIcon trayImg = new ImageIcon("background.png");// ����ͼ��

        trayIcon = new TrayIcon(trayImg.getImage(), "test", new PopupMenu());
        trayIcon.setImageAutoSize(true);

        trayIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {// ���� 1 ˫�� 2
                    tray.remove(trayIcon);
                    frame.setVisible(true);
                    frame.setExtendedState(JFrame.NORMAL);
                    frame.toFront();
                }

            }

        });

        try {
            tray.add(trayIcon);
        } catch (AWTException e1) {
            e1.printStackTrace();
        }

    }
	public static void setTime() {
		String s="";
		s=" "+Integer.toString(Time.getHour())+"  :  "
		+Integer.toString(Time.getMinute())+"  :  "
		+Integer.toString(Time.getSecond());
		System.out.println(s);
		textField.setText(s);
	}
	
	public static void AlarmClocks_initialize() {



		jframe = new JFrame();
		jframe.setBounds(80, 80, 400, 360);
		jframe.setFocusable(true);
        jframe.getContentPane().setLayout(null);
        jframe.setVisible(true);

        final JLabel tip = new JLabel();
        tip.setBounds(40, 40, 320, 60);
        tip.setOpaque(false);
        tip.setBorder(null);
        tip.setText("�������ˣ�");
        tip.setFont(new java.awt.Font("Dialog", 1, 50));
        jframe.getContentPane().add(tip);

        final JButton button_MoreSleep = new JButton("�ر�");
        button_MoreSleep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playUtil.closeAll();
//                jframe.getContentPane()
//                tip.setText("̰˯����ӣ�");
//                button_MoreSleep.setVisible(false);
//                if(Minute+5<=59) {
//                	Minute+=5;
//                }
//                else {
//                	if(Hour==23) {
//                		Hour=1;
//                	}
//                	else {
//                		Hour+=1;
//                	}
//                	Minute = Minute+5-59;
//                }
            }
        });
        button_MoreSleep.setBounds(140, 240, 120, 30);
        jframe.getContentPane().add(button_MoreSleep);
        try {
            String path =  Interface.class.getResource("/").getPath()+ "mp.mp3";
            playUtil.playMp3(path);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public void upDate() {
		now = new Date();
		String s = date.format(now);
		Date.setText(s);
	}
}



//��ʱ����

class Count extends Thread {

    private final Object lock = new Object();

    public Boolean Window = false;
    
    private boolean pause = false;

    /**
     * ���ø÷���ʵ���̵߳���ͣ
     */
    void pauseThread(){
        pause = true;
    }


    /**
     *���ø÷���ʵ�ָֻ��̵߳�����
     */
    void resumeThread(){
        pause =false;
        synchronized (lock){
            lock.notify();
        }
    }

    /**
     * �������ֻ����run ������ʵ�֣���Ȼ���������̣߳�����ҳ������Ӧ
     */
    void onPause() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        super.run();
        while(true){
            while (pause){
                onPause();
            }
            try {
                Time.run();
                Interface.setTime();
                if(Window&&Interface.Hour==Time.h&&Interface.Minute==Time.m&&Time.s==0) {
                	//System.out.println("��������");
                	Interface.AlarmClocks_initialize();
                }
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
    }
}



