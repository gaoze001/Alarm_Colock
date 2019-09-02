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
        
        JButton button_start = new JButton("��ʼ");
        button_start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	count.resumeThread();
            }
        });
        button_start.setBounds(82, 305, 150, 26);
        frame.getContentPane().add(button_start);
        
        JButton button_stop = new JButton("��ͣ");
        button_stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {                
                count.pauseThread();             
            }
        });
        button_stop.setBounds(252, 305, 150, 26);
        frame.getContentPane().add(button_stop);
        
        //��ʼ��ʱ�䣻
        Interface.setTime();
        
        //���ӹ��ܿ飻
        Alarm_clock_tip = new JLabel("�������������ӵ�ʱ�֣��������������ӡ�������");
        Alarm_clock_tip.setBounds(80, 360, 360, 26);
        Alarm_clock_tip.setFont(new java.awt.Font("Dialog", 1, 15));
        Alarm_clock_tip.setOpaque(false);
        Alarm_clock_tip.setBorder(null);
        frame.getContentPane().add(Alarm_clock_tip);
        
        Alarm_clock_Hour =new JLabel("ʱ��");
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
        
        Alarm_clock_Minute =new JLabel("�֣�");
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
        
        Alarm_clock_Tip = new JLabel("������δ������");
        Alarm_clock_Tip.setBounds(80, 480, 360, 26);
        Alarm_clock_Tip.setFont(new java.awt.Font("Dialog", 1, 15));
        Alarm_clock_Tip.setOpaque(false);
        Alarm_clock_Tip.setBorder(null);
        frame.getContentPane().add(Alarm_clock_Tip);
        
        JButton Alarm_Clock = new JButton("��������");
        Alarm_Clock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	control = !control;
            	if(control) {
            		if(!Alarm_clock_hour.getText().equals("Null")&&!Alarm_clock_minute.getText().equals("Null")) {
            			String str_hour = Alarm_clock_hour.getText();
                		Hour = Integer.parseInt(str_hour);
                		String str_minute = Alarm_clock_minute.getText();
                		Minute = Integer.parseInt(str_minute);
                		Alarm_Clock.setText("�ر�����");
                		count.Window = true;
                		Alarm_clock_Tip.setText("�����ѿ�����");
            		}
            		else {
            			Alarm_clock_Tip.setText("�����������������룡");
            			control = !control;
            		}
            	}
            	else {
            		Alarm_Clock.setText("��������");
            		Alarm_clock_Tip.setText("�����ѹرգ�");
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
        tip.setText("�������ˣ�");
        tip.setFont(new java.awt.Font("Dialog", 1, 50));
        jframe.getContentPane().add(tip);
        
        JButton button_MoreSleep = new JButton("̰˯");
        button_MoreSleep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {                
                tip.setText("̰˯����ӣ�");
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



