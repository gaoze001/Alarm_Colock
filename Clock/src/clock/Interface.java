package clock;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;

import javax.swing.*;
import java.util.Date;


public class Interface {

	public static JFrame jframe;
	public JFrame frame;
	public static JTextField textField,Date,Alarm_clock_hour,Alarm_clock_minute;
	ImageIcon img;
	JLabel background;
	JLabel Alarm_clock_tip,Alarm_clock_Tip,Alarm_clock_Hour,Alarm_clock_Minute;
	JPanel background_p;
	
	public Boolean control = false;
	public int time_interval=0;
	public static int Hour=0,Minute=0;
	
	Date now = new Date();
	DateFormat date = DateFormat.getDateTimeInstance(); 
	
	public Count count=new Count();
	
	//初始化；
	@SuppressWarnings("deprecation")
	public Interface() {
		Time.setHour(now.getHours());
		Time.setMinute(now.getMinutes());
		Time.setSecond(now.getSeconds());
        initialize();
    }
	
	private void initialize() {
		frame=new JFrame("简易时钟");
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
        
        JButton button_start = new JButton("开始");
        button_start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	count.resumeThread();
            }
        });
        button_start.setBounds(82, 305, 150, 26);
        frame.getContentPane().add(button_start);
        
        JButton button_stop = new JButton("暂停");
        button_stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {                
                count.pauseThread();             
            }
        });
        button_stop.setBounds(252, 305, 150, 26);
        frame.getContentPane().add(button_stop);
        
        //初始化时间；
        Interface.setTime();
        
        //闹钟功能块；
        Alarm_clock_tip = new JLabel("请依次输入闹钟的时分，并按“开启闹钟”开启！");
        Alarm_clock_tip.setBounds(80, 360, 360, 26);
        Alarm_clock_tip.setFont(new java.awt.Font("Dialog", 1, 15));
        Alarm_clock_tip.setOpaque(false);
        Alarm_clock_tip.setBorder(null);
        frame.getContentPane().add(Alarm_clock_tip);
        
        Alarm_clock_Hour =new JLabel("时：");
        Alarm_clock_Hour.setBounds(80, 420, 40, 26);
        Alarm_clock_Hour.setFont(new java.awt.Font("Dialog", 1, 15));
        Alarm_clock_Hour.setOpaque(false);
        Alarm_clock_Hour.setBorder(null);
        frame.getContentPane().add(Alarm_clock_Hour);
        
        Alarm_clock_hour = new JTextField("Null");
        Alarm_clock_hour.setBounds(120, 420, 60, 26);
        Alarm_clock_hour.setFont(new java.awt.Font("Dialog", 1, 15));
        Alarm_clock_hour.setBorder(null);
        frame.getContentPane().add(Alarm_clock_hour);
        
        Alarm_clock_Minute =new JLabel("分：");
        Alarm_clock_Minute.setBounds(190, 420, 40, 26);
        Alarm_clock_Minute.setFont(new java.awt.Font("Dialog", 1, 15));
        Alarm_clock_Minute.setOpaque(false);
        Alarm_clock_Minute.setBorder(null);
        frame.getContentPane().add(Alarm_clock_Minute);
        
        Alarm_clock_minute = new JTextField("Null");
        Alarm_clock_minute.setBounds(230, 420, 60, 26);
        Alarm_clock_minute.setFont(new java.awt.Font("Dialog", 1, 15));
        Alarm_clock_minute.setBorder(null);
        frame.getContentPane().add(Alarm_clock_minute);
        
        Alarm_clock_Tip = new JLabel("闹钟尚未开启！");
        Alarm_clock_Tip.setBounds(80, 480, 360, 26);
        Alarm_clock_Tip.setFont(new java.awt.Font("Dialog", 1, 15));
        Alarm_clock_Tip.setOpaque(false);
        Alarm_clock_Tip.setBorder(null);
        frame.getContentPane().add(Alarm_clock_Tip);
        
        JButton Alarm_Clock = new JButton("开启闹钟");
        Alarm_Clock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	control = !control;
            	if(control) {
            		if(!Alarm_clock_hour.getText().equals("Null")&&!Alarm_clock_minute.getText().equals("Null")) {
            			String str_hour = Alarm_clock_hour.getText();
                		Hour = Integer.parseInt(str_hour);
                		String str_minute = Alarm_clock_minute.getText();
                		Minute = Integer.parseInt(str_minute);
                		Alarm_Clock.setText("关闭闹钟");
                		count.Window = true;
                		Alarm_clock_Tip.setText("闹钟已开启！");
            		}
            		else {
            			Alarm_clock_Tip.setText("输入有误，请重新输入！");
            			control = !control;
            		}
            	}
            	else {
            		Alarm_Clock.setText("开启闹钟");
            		Alarm_clock_Tip.setText("闹钟已关闭！");
            	}
            }
        });
        Alarm_Clock.setBounds(300, 420, 100, 26);
        frame.getContentPane().add(Alarm_Clock);
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
        
        JLabel tip = new JLabel();
        tip.setBounds(40, 40, 320, 60);
        tip.setOpaque(false);
        tip.setBorder(null);
        tip.setText("闹钟响了！");
        tip.setFont(new java.awt.Font("Dialog", 1, 50));
        jframe.getContentPane().add(tip);
        
        JButton button_MoreSleep = new JButton("贪睡");
        button_MoreSleep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {                
                tip.setText("贪睡五分钟！");
                button_MoreSleep.setVisible(false);
                if(Minute+5<=59) {
                	Minute+=5;
                }
                else {
                	if(Hour==23) {
                		Hour=1;
                	}
                	else {
                		Hour+=1;
                	}
                	Minute = Minute+5-59;
                }
            }
        });
        button_MoreSleep.setBounds(140, 240, 120, 30);
        jframe.getContentPane().add(button_MoreSleep);
        
	}
	
	public void upDate() {
		now = new Date();
		String s = date.format(now);
		Date.setText(s);
	}
}



//计时器；

class Count extends Thread {

    private final Object lock = new Object();

    public Boolean Window = false;
    
    private boolean pause = false;

    /**
     * 调用该方法实现线程的暂停
     */
    void pauseThread(){
        pause = true;
    }


    /**
     *调用该方法实现恢复线程的运行
     */
    void resumeThread(){
        pause =false;
        synchronized (lock){
            lock.notify();
        }
    }

    /**
     * 这个方法只能在run 方法中实现，不然会阻塞主线程，导致页面无响应
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
                	//System.out.println("闹钟响了");
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



