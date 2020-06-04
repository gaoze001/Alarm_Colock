package clock;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class Interface {

    public static JFrame jframe;
    public static JFrame frame;
    public static JTextField textField, Date, alarmClock_hour, alarmClock_minute;
    ImageIcon img;
    JLabel background;
    JLabel alarmClockTip, alarmClockTextTip, alarmClock_Hour, alarmClock_Minute;
    JPanel background_p;
    private static TrayIcon trayIcon = null;
    static SystemTray tray = SystemTray.getSystemTray();

    public Boolean control = false;
    public int time_interval = 0;
    public static int Hour = 0, Minute = 0;

    Date now = new Date();
    DateFormat date = DateFormat.getDateTimeInstance();

    public Count count = new Count();

    private static PlayUtil playUtil = new PlayUtil();
    private static java.util.List<String> minuteArr = Arrays.asList("9", "19", "29", "39", "49", "59");

    //��ʼ����
    @SuppressWarnings("deprecation")
    public Interface() {
        Time.setHour(now.getHours());
        Time.setMinute(now.getMinutes());
        Time.setSecond(now.getSeconds());
        initialize();
    }

    private void initialize() {
        frame = new JFrame("����ʱ��");
        frame.setBounds(100, 100, 480, 350);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        img = new ImageIcon();
        background = new JLabel(img);
        background_p = new JPanel();
        background_p.setLayout(null);
        background_p.setBounds(0, 0, 480, 350);
        background.setBounds(0, 0, 480, 350);
        background_p.setOpaque(false);
        background_p.add(background);
        frame.getContentPane().add(background_p);

        textField = new JTextField();
        textField.setBounds(70, 98, 344, 80);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        textField.setBackground(Color.white);
        textField.setOpaque(false);
        textField.setBorder(null);
        textField.setFont(new java.awt.Font("Dialog", 1, 50));

        JTextField Date = new JTextField();
        Date.setBounds(70, 48, 250, 50);
        frame.getContentPane().add(Date);
        Date.setBackground(Color.white);
        Date.setOpaque(false);
        Date.setBorder(null);
        Date.setText(date.format(now));
        Date.setFont(new java.awt.Font("Dialog", 1, 30));

        JButton buttonStart = new JButton("��10");
        buttonStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Integer m = Integer.parseInt(alarmClock_minute.getText().trim());
                if (minuteArr.contains(((Integer) (m + 10)).toString())) {
                    alarmClock_minute.setText(((Integer) (m + 10)).toString());
                } else {
                    int index = minuteArr.indexOf(alarmClock_minute.getText().trim());
                    int x = 1 - (6 - index);
                    alarmClock_minute.setText(minuteArr.get(x));
                    Integer integer = Integer.parseInt(alarmClock_hour.getText().trim()) + 1;
                    alarmClock_hour.setText(integer.toString());
                }
//                count.resumeThread();
            }
        });
        buttonStart.setBounds(37, 265, 80, 26);
        frame.getContentPane().add(buttonStart);
        JButton subStart = new JButton("��10");
        subStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Integer m = Integer.parseInt(alarmClock_minute.getText().trim());
                if (minuteArr.contains(((Integer) (m - 10)).toString())) {
                    alarmClock_minute.setText(((Integer) (m - 10)).toString());
                } else {
                    int index = minuteArr.indexOf(alarmClock_minute.getText().trim());
                    int x = 6 - (index + 1);
                    alarmClock_minute.setText(minuteArr.get(x));
                    Integer integer = Integer.parseInt(alarmClock_hour.getText().trim()) - 1;
                    alarmClock_hour.setText(integer.toString());
                }
//                count.resumeThread();
            }
        });
        subStart.setBounds(137, 265, 80, 26);
        frame.getContentPane().add(subStart);

        JButton buttonStop = new JButton("��30");
        buttonStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Integer m = Integer.parseInt(alarmClock_minute.getText().trim());
                if (minuteArr.contains(((Integer) (m + 30)).toString())) {
                    alarmClock_minute.setText(((Integer) (m + 30)).toString());
                } else {
                    int index = minuteArr.indexOf(alarmClock_minute.getText().trim());
                    int x = 3 - (6 - index);
                    alarmClock_minute.setText(minuteArr.get(x));
                    Integer integer = Integer.parseInt(alarmClock_hour.getText().trim()) + 1;
                    alarmClock_hour.setText(integer.toString());
                }
//                count.pauseThread();
            }
        });
        buttonStop.setBounds(237, 265, 80, 26);
        frame.getContentPane().add(buttonStop);

        JButton resbutton = new JButton("����");
        resbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LocalTime localNow = LocalTime.now().withNano(0);
                Time.setHour(localNow.getHour());
                Time.setMinute(localNow.getMinute());
                Time.setSecond(localNow.getSecond());
            }
        });
        resbutton.setBounds(337, 265, 80, 26);
        frame.getContentPane().add(resbutton);

        //��ʼ��ʱ�䣻
        Interface.setTime();

        //���ӹ��ܿ飻
        alarmClockTip = new JLabel("�������������ӵ�ʱ�֣��������������ӡ�������");
        alarmClockTip.setBounds(80, 160, 360, 26);
        alarmClockTip.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClockTip.setOpaque(false);
        alarmClockTip.setBorder(null);
        frame.getContentPane().add(alarmClockTip);

        alarmClock_Hour = new JLabel("ʱ��");
        alarmClock_Hour.setBounds(80, 200, 40, 26);
        alarmClock_Hour.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClock_Hour.setOpaque(false);
        alarmClock_Hour.setBorder(null);
        frame.getContentPane().add(alarmClock_Hour);

        alarmClock_hour = new JTextField();
        alarmClock_hour.setBounds(120, 200, 60, 26);
        alarmClock_hour.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClock_hour.setBorder(null);
        alarmClock_hour.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String str_hour = alarmClock_hour.getText().trim();
                Hour = Integer.parseInt(str_hour);
                System.out.println("Hour:" + Hour);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        frame.getContentPane().add(alarmClock_hour);

        alarmClock_Minute = new JLabel("�֣�");
        alarmClock_Minute.setBounds(190, 200, 40, 26);
        alarmClock_Minute.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClock_Minute.setOpaque(false);
        alarmClock_Minute.setBorder(null);
        frame.getContentPane().add(alarmClock_Minute);

        alarmClock_minute = new JTextField();
        alarmClock_minute.setBounds(230, 200, 60, 26);
        alarmClock_minute.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClock_minute.setBorder(null);
        alarmClock_minute.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String str_minute = alarmClock_minute.getText();
                Minute = Integer.parseInt(str_minute);
                System.out.println("Minute:" + Minute);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        frame.getContentPane().add(alarmClock_minute);

        alarmClockTextTip = new JLabel("������δ������");
        alarmClockTextTip.setBounds(80, 230, 360, 26);
        alarmClockTextTip.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClockTextTip.setOpaque(false);
        alarmClockTextTip.setBorder(null);
        frame.getContentPane().add(alarmClockTextTip);

        final JButton alarmClock = new JButton("��������");
        alarmClock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                control = !control;
                if (control) {
                    if (!alarmClock_hour.getText().equals("Null") && !alarmClock_minute.getText().equals("Null")) {
                        String str_hour = alarmClock_hour.getText();
                        Hour = Integer.parseInt(str_hour);
                        String str_minute = alarmClock_minute.getText();
                        Minute = Integer.parseInt(str_minute);
                        alarmClock.setText("�ر�����");
                        count.Window = true;
                        alarmClockTextTip.setText("�����ѿ�����");
                    } else {
                        alarmClockTextTip.setText("�����������������룡");
                        control = !control;
                    }
                } else {
                    alarmClock.setText("��������");
                    alarmClockTextTip.setText("�����ѹرգ�");
                    playUtil.closeAll();
                }
            }
        });
        alarmClock.setBounds(300, 200, 100, 26);
        frame.getContentPane().add(alarmClock);
        frame.addWindowListener(new WindowAdapter() { // ���ڹر��¼�
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            public void windowIconified(WindowEvent e) { // ������С���¼�
                frame.setVisible(false);
                Interface.miniTray();

            }

        });

    }

    private static void miniTray() { // ������С��������������

        ImageIcon trayImg = new ImageIcon("D:\\background.png");// ����ͼ��
        trayIcon = new TrayIcon(trayImg.getImage(), "����", new PopupMenu());
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
        String s = "";
        s = " " + Integer.toString(Time.getHour()) + "  :  "
                + Integer.toString(Time.getMinute()) + "  :  "
                + Integer.toString(Time.getSecond());
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
        tip.setBounds(40, 40, 1320, 60);
        tip.setOpaque(false);
        tip.setBorder(null);
        tip.setText("�������ˣ�");
        tip.setFont(new java.awt.Font("Dialog", 1, 10));
        jframe.getContentPane().add(tip);

        final JButton button_MoreSleep = new JButton("�ر�");
        button_MoreSleep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jframe.setVisible(false);
                playUtil.closeAll();
            }
        });
        button_MoreSleep.setBounds(140, 240, 120, 30);
        jframe.getContentPane().add(button_MoreSleep);
        try {
            Integer m = Integer.parseInt(alarmClock_minute.getText().trim());
            if (minuteArr.contains(((Integer) (m + 30)).toString())) {
                alarmClock_minute.setText(((Integer) (m + 30)).toString());
            } else {
                int index = minuteArr.indexOf(alarmClock_minute.getText().trim());
                int x = 3 - (6 - index);
                alarmClock_minute.setText(minuteArr.get(x));
                Integer integer = Integer.parseInt(alarmClock_hour.getText().trim()) + 1;
                alarmClock_hour.setText(integer.toString());
            }
            playUtil.playMp3("D:\\mp.mp3");
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
    void pauseThread() {
        pause = true;
    }


    /**
     * ���ø÷���ʵ�ָֻ��̵߳�����
     */
    void resumeThread() {
        pause = false;
        synchronized (lock) {
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
        while (true) {
            while (pause) {
                onPause();
            }
            try {
                Time.run();
                Interface.setTime();
                if (Window && Interface.Hour == Time.h && Interface.Minute == Time.m && Time.s == 15) {
                    //System.out.println("��������");
                    Interface.AlarmClocks_initialize();
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }
}



