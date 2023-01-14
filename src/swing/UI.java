package swing;

import OS.CPU;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {
    static JFrame jFrame =new JFrame("CPU&内存调度管理原型系统  Create  by  欧阳洪健 4191101009");
    private static String mesg="";

      static   CPU cpu;
    public static void main(String[] args) {
        JPanel jPanel =new JPanel();


        JLabel page_size=new JLabel(" 页面大小 单位字节 ：");
        final JTextField pagesize=new JTextField(5);

        JLabel ts=new JLabel("  分配物理块    \n");

        JLabel time=new JLabel("时间片大小 单位(ms):");
        final JTextField time_t=new JTextField(5);

        JLabel block_A=new JLabel("程序A:");
        final JTextField blockA=new JTextField(5);

        JLabel block_B=new JLabel("程序B:");
        final JTextField blockB=new JTextField(5);

        JLabel block_C=new JLabel("程序C:");
        final JTextField blockC=new JTextField(5);

        JLabel block_D=new JLabel("程序D:");
        final JTextField blockD=new JTextField(5);

        JLabel block_E=new JLabel("程序E:");
        final JTextField blockE=new JTextField(5);
        JLabel label1=new JLabel("调度算法：");    //创建标签

        JLabel cmbts=new JLabel("调度算法: ");
        JComboBox cmb=new JComboBox();    //创建JComboBox
        cmb.addItem("--请选择--");    //向下拉列表中添加一项
        cmb.addItem("先来先服务");
        cmb.addItem("时间片轮转");

        JLabel cmbts1=new JLabel("页面置换算法: ");
        JComboBox cmb1=new JComboBox();    //创建JComboBox
        cmb1.addItem("--请选择--");    //向下拉列表中添加一项
        cmb1.addItem("FIFO--现进先出");
        cmb1.addItem("LRU--最近最久");


        JButton jButton =new JButton("提交");
        JButton jButton1 =new JButton("重置");
        //设置分割能不能移动，拖动左右面板

        jPanel.add(cmbts);
        jPanel.add(cmb);
        jPanel.add(cmbts1);
        jPanel.add(cmb1);
        jPanel.add(time);
        jPanel.add(time_t);
        jPanel.add(page_size);
        jPanel.add(pagesize);
        jPanel.add(ts);
        jPanel.add(block_A);
        jPanel.add(blockA);
        jPanel.add(block_B);
        jPanel.add(blockB);
        jPanel.add(block_C);
        jPanel.add(blockC);
        jPanel.add(block_D);
        jPanel.add(blockD);
        jPanel.add(block_E);
        jPanel.add(blockE);
        jPanel.add(jButton);
        jPanel.add(jButton1);
        JTextArea textAreaOutput = new JTextArea(80, 120);

        textAreaOutput.setLineWrap(true); // 激活自动换行功能
        textAreaOutput.setWrapStyleWord(true); // 激活断行不断字功能
        textAreaOutput.setEnabled(false);
        jPanel.setBackground(Color.PINK);

        jPanel.add(textAreaOutput);
        JScrollPane sp=new JScrollPane(textAreaOutput);//创建滚动面板，给文本域添加滚动条
        sp.setBounds(300, 100, 1000, 600);
        textAreaOutput.setSelectedTextColor(Color.white);
        textAreaOutput.setBackground(Color.black);
        jFrame.add(sp);//容器添加滚动面板

        ActionListener action_listener=new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int ddsf,zhsf;
                ddsf=cmb.getSelectedIndex();
                zhsf=cmb1.getSelectedIndex();
                int timep=Integer.valueOf(time_t.getText());
                int pag=0;
               pag=Integer.valueOf(pagesize.getText());
                int noP[]=new int[5];
                noP[0]=Integer.valueOf(blockA.getText());
                noP[1]=Integer.valueOf(blockB.getText());
                noP[2]=Integer.valueOf(blockC.getText());
                noP[3]=Integer.valueOf(blockD.getText());
                noP[4]=Integer.valueOf(blockE.getText());
                {
                    cpu =new CPU();
                    mesg=cpu.RunP(ddsf,timep,pag,zhsf,noP);
                    textAreaOutput.setText(mesg);
                }
            }
        };

        ActionListener action_listener1=new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cmb.setSelectedIndex(0);
                cmb1.setSelectedIndex(0);
                time_t.setText("");
              pagesize.setText("");
                blockA.setText("");
                blockB.setText("");
                blockC.setText("");
                blockD.setText("");
                blockE.setText("");
                mesg=null;
                {
                    jButton.addActionListener(action_listener);
                    cpu=null;
                }
            }
        };
        jButton1.addActionListener(action_listener1);
        jButton.addActionListener(action_listener);
        jFrame.setSize(1900,900);
        jFrame.add(jPanel); jFrame.setVisible(true);
    }

}
