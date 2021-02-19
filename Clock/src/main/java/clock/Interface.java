package clock;

import clock.vo.PriceVo;
import clock.vo.RegionVo;
import util.StringUtil;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


public class Interface {

    public static JFrame jframe;
    public static JFrame frame;
    public static JTabbedPane jTabbedPane;
    public static JPanel jPanel,goodsPanel;
    public static JTextField textField, date, alarmClock_hour, alarmClock_minute;
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
    DateFormat dateFromat = DateFormat.getDateTimeInstance();

    public Count count = new Count();

    private static PlayUtil playUtil = new PlayUtil();
    private static java.util.List<String> minuteArr = Arrays.asList("9", "19", "29", "39", "49", "59");

    //初始化；
    @SuppressWarnings("deprecation")
    public Interface() {
        Time.setHour(now.getHours());
        Time.setMinute(now.getMinutes());
        Time.setSecond(now.getSeconds());
        initialize();
    }

    private void initialize() {
        jTabbedPane = new JTabbedPane();
        jPanel = new JPanel();
        goodsPanel = new JPanel();
        frame = new JFrame("简易时钟");
        jTabbedPane.addTab("闹铃",jPanel);
        jTabbedPane.addTab("物价",goodsPanel);
        frame.setContentPane(jTabbedPane);
        try {
            initRegionPanel(goodsPanel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.setBounds(100, 100, 480, 380);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jPanel.setLayout(null);
        jPanel.setBounds(100, 100, 480, 350);

        img = new ImageIcon();
        background = new JLabel(img);
        background_p = new JPanel();
        background_p.setLayout(null);
        background_p.setBounds(0, 0, 480, 350);
        background.setBounds(0, 0, 480, 350);
        background_p.setOpaque(false);
        background_p.add(background);
        jPanel.add(background_p);

        textField = new JTextField();
        textField.setBounds(70, 98, 344, 80);
        jPanel.add(textField);
        textField.setColumns(10);
        textField.setBackground(Color.white);
        textField.setOpaque(false);
        textField.setBorder(null);
        textField.setFont(new java.awt.Font("Dialog", 1, 50));

        date = new JTextField();
        date.setBounds(70, 48, 250, 50);
        jPanel.add(date);
        date.setBackground(Color.white);
        date.setOpaque(false);
        date.setBorder(null);
        date.setText(dateFromat.format(now));
        date.setFont(new java.awt.Font("Dialog", 1, 30));

        JButton buttonStart = new JButton("加10");
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
        jPanel.add(buttonStart);
        JButton subStart = new JButton("减10");
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
        jPanel.add(subStart);

        JButton buttonStop = new JButton("加30");
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
        jPanel.add(buttonStop);

        JButton resbutton = new JButton("重置");
        resbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LocalTime localNow = LocalTime.now().withNano(0);
                Time.setHour(localNow.getHour());
                Time.setMinute(localNow.getMinute());
                Time.setSecond(localNow.getSecond());
            }
        });
        resbutton.setBounds(337, 265, 80, 26);
        jPanel.add(resbutton);

        //初始化时间；
        Interface.setTime();

        //闹钟功能块；
        alarmClockTip = new JLabel("请依次输入闹钟的时分，并按“开启闹钟”开启！");
        alarmClockTip.setBounds(80, 160, 360, 26);
        alarmClockTip.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClockTip.setOpaque(false);
        alarmClockTip.setBorder(null);
        jPanel.add(alarmClockTip);

        alarmClock_Hour = new JLabel("时：");
        alarmClock_Hour.setBounds(80, 200, 40, 26);
        alarmClock_Hour.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClock_Hour.setOpaque(false);
        alarmClock_Hour.setBorder(null);
        jPanel.add(alarmClock_Hour);

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
        jPanel.add(alarmClock_hour);

        alarmClock_Minute = new JLabel("分：");
        alarmClock_Minute.setBounds(190, 200, 40, 26);
        alarmClock_Minute.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClock_Minute.setOpaque(false);
        alarmClock_Minute.setBorder(null);
        jPanel.add(alarmClock_Minute);

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
        jPanel.add(alarmClock_minute);

        alarmClockTextTip = new JLabel("闹钟尚未开启！");
        alarmClockTextTip.setBounds(80, 230, 360, 26);
        alarmClockTextTip.setFont(new java.awt.Font("Dialog", 1, 15));
        alarmClockTextTip.setOpaque(false);
        alarmClockTextTip.setBorder(null);
        jPanel.add(alarmClockTextTip);

        final JButton alarmClock = new JButton("开启闹钟");
        alarmClock.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                control = !control;
                if (control) {
                    if (!alarmClock_hour.getText().equals("Null") && !alarmClock_minute.getText().equals("Null")) {
                        String str_hour = alarmClock_hour.getText();
                        Hour = Integer.parseInt(str_hour);
                        String str_minute = alarmClock_minute.getText();
                        Minute = Integer.parseInt(str_minute);
                        alarmClock.setText("关闭闹钟");
                        count.Window = true;
                        alarmClockTextTip.setText("闹钟已开启！");
                    } else {
                        alarmClockTextTip.setText("输入有误，请重新输入！");
                        control = !control;
                    }
                } else {
                    alarmClock.setText("开启闹钟");
                    alarmClockTextTip.setText("闹钟已关闭！");
                    playUtil.closeAll();
                }
            }
        });
        alarmClock.setBounds(300, 200, 100, 26);
        jPanel.add(alarmClock);

        frame.addWindowListener(new WindowAdapter() { // 窗口关闭事件
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

            public void windowIconified(WindowEvent e) { // 窗口最小化事件
                frame.setVisible(false);
                Interface.miniTray();

            }

        });

    }

    private static void miniTray() { // 窗口最小化到任务栏托盘

        ImageIcon trayImg = new ImageIcon("D:\\background.png");// 托盘图标
        trayIcon = new TrayIcon(trayImg.getImage(), "闹铃", new PopupMenu());
        trayIcon.setImageAutoSize(true);
        trayIcon.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {// 单击 1 双击 2
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

    public static void initRegionPanel(JPanel jPanelGoods) throws Exception{
        jPanelGoods.setLayout(null);
        jPanelGoods.setBounds(100, 100, 480, 350);

        JLabel jLabelRegion = new JLabel("大区：");
        jLabelRegion.setBounds(10, 10, 50, 26);
        jLabelRegion.setFont(new java.awt.Font("Dialog", 1, 15));
        jLabelRegion.setOpaque(false);
        jLabelRegion.setBorder(null);
        jPanelGoods.add(jLabelRegion);
        SqliteUtil sqliteUtil = new SqliteUtil();
        List<String> regionList =sqliteUtil.queryAllRegion();
        JComboBox  jComboBoxRegion = new JComboBox (regionList.toArray());
        jComboBoxRegion.setBounds(60, 10, 80, 26);
        jPanelGoods.add(jComboBoxRegion);
        JLabel jLabelArea = new JLabel("小区：");
        jLabelArea.setBounds(140, 10, 50, 26);
        jLabelArea.setFont(new java.awt.Font("Dialog", 1, 15));
        jLabelArea.setOpaque(false);
        jLabelArea.setBorder(null);
        jPanelGoods.add(jLabelArea);
        JComboBox  jComboBoxArea = new JComboBox (regionList.toArray());
        jComboBoxArea.setBounds(190, 10, 80, 26);
        jPanelGoods.add(jComboBoxArea);

        Map<String,String> regionMap = new HashMap<>();
        jComboBoxRegion.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        List<RegionVo> regionAreaList =sqliteUtil.queryAreaByRegion(e.getItem().toString());
                        regionAreaList.forEach(i->{
                            regionMap.put(i.getArea(),i.getRegionCode());
                        });
                        List<String> areaList = regionAreaList.stream().map(RegionVo::getArea).collect(Collectors.toList());
                        jComboBoxArea.removeAllItems();
                        areaList.forEach(x->{
                            jComboBoxArea.addItem(x);
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        jComboBoxArea.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        List<PriceVo> regionList =sqliteUtil.queryPriceByRegion(regionMap.get(e.getItem()));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JTextField textRegionField = new JTextField();
        textRegionField.setBounds(270, 10, 60, 26);
        jPanelGoods.add(textRegionField);
        textRegionField.setColumns(10);
        textRegionField.setToolTipText("大区");
        textRegionField.setFont(new java.awt.Font("Dialog", 1, 15));

        JTextField textAreaField = new JTextField();
        textAreaField.setBounds(330, 10, 60, 26);
        jPanelGoods.add(textAreaField);
        textAreaField.setColumns(10);
        textAreaField.setToolTipText("小区");
        textAreaField.setFont(new java.awt.Font("Dialog", 1, 15));

        final JButton addRegion= new JButton("加区");
        addRegion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String regionText = textRegionField.getText();
                String areaText = textAreaField.getText();
                if(StringUtil.isEmpty(regionText)||StringUtil.isEmpty(areaText))
                    return;
                String regionCode = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                RegionVo regionVo = new RegionVo();
                regionVo.setRegion(regionText);
                regionVo.setArea(areaText);
                regionVo.setRegionCode(regionCode);
                List<RegionVo> regionVos = new ArrayList<>();
                regionVos.add(regionVo);
                try {
                    sqliteUtil.insertRegion(regionVos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        addRegion.setBounds(390, 10, 80, 26);
        jPanelGoods.add(addRegion);
        final String[] title = { "物品名", "价格", "数量"};
        StudentTableModel dataModel = new StudentTableModel();
        JTable table= new JTable(dataModel);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(10,50,440,320);
        jPanelGoods.add(scrollpane);
    }

    static class StudentTableModel extends AbstractTableModel {
        List<PriceVo> priceList = new ArrayList();
        public void setPrice(final List<PriceVo> list){
            //invokeLater()方法:导致 doRun.run() 在 AWT 事件指派线程上异步执行。在所有挂起的 AWT 事件被处理后才发生。
            //此方法应该在应用程序线程需要更新该 GUI 时使用
            SwingUtilities.invokeLater(new Runnable(){

                public void run() {
                    priceList = list;
                    fireTableDataChanged();  //通知JTable数据对象已更改,重绘界面
                }

            });

        }
        public int getColumnCount() {
            return 3;
        }
        public int getRowCount() {
            return priceList.size();
        }
        public Object getValueAt(int rowIndex, int columnIndex) {
            PriceVo priceVo = priceList.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return priceVo.getItemName();
                case 1:
                    return priceVo.getItemPrice();
                case 2:
                    return priceVo.getItemNum();
                default:
                    break;
            }
            return null;
        }
    }

//    public static AbstractTableModel getTableModel(String regionCode) {
//
//        try {
//            return new AbstractTableModel() {
//                SqliteUtil sqliteUtil = new SqliteUtil();
//                List<PriceVo> regionList =sqliteUtil.queryPriceByRegion(regionCode);
//                public int getColumnCount() {
//                    return 3;
//                }
//
//                @Override
//                public Object getValueAt(int rowIndex, int columnIndex) {
//                    return null;
//                }
//
//                public int getRowCount() {
//                    return regionList.size();
//                }
//            };
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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
        tip.setText("闹钟响了！");
        tip.setFont(new java.awt.Font("Dialog", 1, 10));
        jframe.getContentPane().add(tip);

        final JButton button_MoreSleep = new JButton("关闭");
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
        String s = dateFromat.format(now);
        date.setText(s);
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
    void pauseThread() {
        pause = true;
    }


    /**
     * 调用该方法实现恢复线程的运行
     */
    void resumeThread() {
        pause = false;
        synchronized (lock) {
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
        while (true) {
            while (pause) {
                onPause();
            }
            try {
                Time.run();
                Interface.setTime();
                if (Window && Interface.Hour == Time.h && Interface.Minute == Time.m && Time.s == 15) {
                    //System.out.println("闹钟响了");
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



