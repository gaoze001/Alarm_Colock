package clock;

import clock.vo.PriceVo;
import clock.vo.RegionVo;
import sun.security.util.Resources;
import util.StringUtil;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.DateFormat;

import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Interface {

    public static JFrame jframe;
    public static JFrame frame;
    public static JTabbedPane jTabbedPane;
    public static JPanel jPanel, goodsPanel;
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

    private static PlayUtil playUtil = new PlayUtil();
    private static java.util.List<String> minuteArr = Arrays.asList("9", "19", "29", "39", "49", "59");

    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

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
        jTabbedPane.addTab("闹铃", jPanel);
        jTabbedPane.addTab("物价", goodsPanel);
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

//        String xmlfilePath = new Object() {
//            public String getPath() {
//                return this.getClass().getResource("/background.png").getPath();
//            }
//        }.getPath().substring(1);

        ImageIcon trayImg = new ImageIcon(ClassLoader.getSystemResource("background.png"));// 托盘图标
        trayIcon = new TrayIcon(trayImg.getImage(), "工具箱", new PopupMenu());
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

    public static void initRegionPanel(JPanel jPanelGoods) throws Exception {
        jPanelGoods.setLayout(null);
        jPanelGoods.setBounds(100, 100, 480, 350);

        SqliteUtil sqliteUtil = new SqliteUtil();
        List<String> regionList = sqliteUtil.queryAllRegion();
        JComboBox jComboBoxRegion = new JComboBox(regionList.toArray());
        jComboBoxRegion.setBounds(10, 10, 80, 26);
        jPanelGoods.add(jComboBoxRegion);
        JComboBox jComboBoxArea = new JComboBox(regionList.toArray());
        jComboBoxArea.setBounds(90, 10, 80, 26);
        jPanelGoods.add(jComboBoxArea);

        Map<String, String> regionMap = new HashMap<>();
        jComboBoxRegion.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        List<RegionVo> regionAreaList = sqliteUtil.queryAreaByRegion(e.getItem().toString());
                        regionAreaList.forEach(i -> {
                            regionMap.put(i.getArea(), i.getRegionCode());
                        });
                        List<String> areaList = regionAreaList.stream().map(RegionVo::getArea).collect(Collectors.toList());
                        jComboBoxArea.removeAllItems();
                        areaList.forEach(x -> {
                            jComboBoxArea.addItem(x);
                        });
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        Vector vect = new Vector();
        AbstractTableModel dataModel = new AbstractTableModel() {
            final String[] title = {"物品名", "价格", "数量"};

            public int getColumnCount() {
                return title.length;
            }

            public int getRowCount() {
                return vect.size();
            }

            public Object getValueAt(int row, int column) {
                if (!vect.isEmpty())
                    return ((Vector) vect.elementAt(row)).elementAt(column);
                else
                    return null;
            }

            public String getColumnName(int column) {
                return title[column];
            }

            public void setValueAt(Object value, int row, int column) {
            }

            public Class getColumnClass(int c) {
                return getValueAt(0, c).getClass();
            }

            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        jComboBoxArea.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        List<PriceVo> regionList = sqliteUtil.queryPriceByRegion(regionMap.get(e.getItem()));
                        vect.removeAllElements();//初始化向量对象
                        regionList.forEach(i -> {
                            Vector rec_vector = new Vector();
                            rec_vector.addElement(i.getItemName());
                            rec_vector.addElement(i.getItemPrice());
                            rec_vector.addElement(i.getItemNum());
                            vect.addElement(rec_vector);
                        });
                        dataModel.fireTableStructureChanged();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        JTextField textRegionField = new JTextField(8);
        JTextField textAreaField = new JTextField(8);

        JPanel regionPanel = new JPanel();
        regionPanel.add(new JLabel("大区:"));
        regionPanel.add(textRegionField);
        regionPanel.add(Box.createHorizontalStrut(15)); // a spacer
        regionPanel.add(new JLabel("小区:"));
        regionPanel.add(textAreaField);

        final JButton addRegion = new JButton("加区");
        addRegion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int result = JOptionPane.showConfirmDialog(null, regionPanel,
                        "添加大区", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    String regionText = textRegionField.getText();
                    String areaText = textAreaField.getText();
                    if (StringUtil.isEmpty(regionText) || StringUtil.isEmpty(areaText))
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
            }
        });
        addRegion.setBounds(170, 10, 60, 26);
        jPanelGoods.add(addRegion);

        JComboBox textItemNameField = new JComboBox();
        JTextField textItemPriceField = new JTextField(6);
        JTextField textItemNumField = new JTextField(6);
        JPanel addItemPanel = new JPanel(new GridLayout(0, 2, 3, 3));
        addItemPanel.add(new JLabel("物品名"));
        addItemPanel.add(textItemNameField);
        addItemPanel.add(new JLabel("物品价格"));
        addItemPanel.add(textItemPriceField);
        addItemPanel.add(new JLabel("物品数量"));
        addItemPanel.add(textItemNumField);

        final JButton addPrice = new JButton("加物");
        addPrice.setBounds(230, 10, 60, 26);
        jPanelGoods.add(addPrice);
        addPrice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    List<String> itemList = sqliteUtil.queryAllItem();
                    itemList.forEach(i -> {
                        textItemNameField.addItem(i);
                    });

                    int result = JOptionPane.showConfirmDialog(null, addItemPanel,
                            "添加物品", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        String itemName = textItemNameField.getSelectedItem().toString();
                        String itemPrice = textItemPriceField.getText();
                        String itemNum = textItemNumField.getText();
                        if (StringUtil.isEmpty(itemName) || StringUtil.isEmpty(itemPrice) || StringUtil.isEmpty(itemNum))
                            return;
                        PriceVo priceVo = new PriceVo();
                        priceVo.setItemNum(Integer.parseInt(itemNum));
                        priceVo.setItemPrice(Integer.parseInt(itemPrice));
                        priceVo.setItemName(itemName);
                        priceVo.setRegionCode(regionMap.get(jComboBoxArea.getSelectedItem()));

                        sqliteUtil.insertPrice(priceVo);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        final JButton addItem = new JButton("增物");
        addItem.setBounds(290, 10, 60, 26);
        jPanelGoods.add(addItem);
        addItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String input = JOptionPane.showInputDialog("物品名:");
                if (StringUtil.isEmpty(input))
                    return;
                try {
                    sqliteUtil.insertItemEnum(input);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JTable table = new JTable(dataModel);
        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setBounds(10, 40, 140, 270);
        jPanelGoods.add(scrollpane);
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
        tip.setText("闹钟响了！");
        tip.setFont(new java.awt.Font("Dialog", 1, 10));
        jframe.getContentPane().add(tip);
        cachedThreadPool.execute(new Runnable() {
            public void run() {
                try {
                    playUtil.playMp3(ClassLoader.getSystemResource("mp.mp3").getPath());
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        final JButton button_MoreSleep = new JButton("关闭");
        button_MoreSleep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jframe.dispose();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //计时器；
    public static void runCount() {
        cachedThreadPool.execute(new Runnable() {
            public void run() {
                Interface.countThread();
            }
        });
    }

    public static void countThread() {
        try {
            Time.run();
            Interface.setTime();
            if (Interface.Hour == Time.h && Interface.Minute == Time.m && Time.s == 15) {
                //System.out.println("闹钟响了");
                cachedThreadPool.execute(new Runnable() {
                    public void run() {
                        Interface.AlarmClocks_initialize();
                    }
                });
            }
            Thread.sleep(1000);
            countThread();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





