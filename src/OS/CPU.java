package OS;
import pojo.PCB;
import pojo.Res;
import pojo.Write;

import java.util.LinkedList;
import java.util.Queue;
public class CPU {
     PCB[] pcbs;
     Creat creat=new Creat();
    Queue<PCB>readyQ=new LinkedList<PCB>();//就绪队列
    Queue<PCB>waitQ=new LinkedList<PCB>();//等待队列
     Queue<PCB>IoQ=new LinkedList<PCB>();//Io队列
     int Alltime=0;
     int arr[]=new int[5];//记录程序状态  3运行 2阻塞 1就绪
     PCB runing=new PCB();
     int timesize;//时间片
     int tt;//
     Res res[] =new Res[5];
     page pages=new page();
     int pagesize;
     int nowpage;
     String mesage="";
     int block[]=new int[5];
     String mesageDo="";
    public   void Init(int sign,int tp,int ps,int np,int arr1[]){//t时间片大小
        pagesize=ps;
        nowpage=np;
      if(sign==2){
            timesize = tp;
            tt = tp;
        }
        else {
           timesize=999;
            tt=999;
        }

        for (int i = 0; i < 5; i++) {
            block[i]=arr1[i];
        }

        pcbs=creat.CreatPcb();
        int ar[]=creat.ReadyQ();
        readyQ.add(pcbs[arr[0]]);
        for (int i = 1; i < 5; i++) {
            res[i]= new Res();
            waitQ.add(pcbs[ar[i]]);
        }
        arr[0]=3;
        try {
            res[0]=new Res();
            res[0].setStarttime(pcbs[0].getProcess().getArrive());
            res[0].setRuntime(pcbs[0].getRuns()[5].getTime());
        }catch (Exception e){System.out.println(e.fillInStackTrace());}

        for (int i = 0; i < 5; i++) {
            if(nowpage==1)
            pcbs[i].setIsnopage(pages.Runpage(pagesize,i,1,block));
          else
              pcbs[i].setIsnopageLru(pages.Runpage(pagesize,i,2,block));

        }

        mesageDo+=pages.reMsg()+"\n";
    }

    /**
     * 时间片及先来先服务运行算法
     *
     * return /
     */
    public void Run() {
        runing = readyQ.remove();//运行进程
        int time = runing.getRuntime();//可恢复之前的执行进度
        boolean flag = false;
        boolean flag1 = false;
        int next = runing.getNextdo();
        String meg = "";//到达信息，跳转信息
        System.out.print(" 【  ");
        mesageDo += " 【  ";
        for (int i = 0; i < 5; i++) {//记录程序运行状态
            if (arr[i] == -1) {
                System.out.print(pcbs[i].getProcess().getJc() + " 执行完成 ");
                mesageDo += pcbs[i].getProcess().getJc() + " 执行完成 ";
            }
            if (arr[i] == 0) {
                System.out.print(pcbs[i].getProcess().getJc() + " 等待态 ");
                mesageDo += pcbs[i].getProcess().getJc() + " 等待态 ";
            }
            if (arr[i] == 1) {
                System.out.print(pcbs[i].getProcess().getJc() + " 就绪态 ");
                mesageDo += pcbs[i].getProcess().getJc() + " 就绪态 ";
            }
            if (arr[i] == 2) {
                System.out.print(pcbs[i].getProcess().getJc() + " 阻塞态 ");
                mesageDo += pcbs[i].getProcess().getJc() + " 阻塞态 ";
            }
            if (arr[i] == 3) {
                System.out.print(pcbs[i].getProcess().getJc() + " 运行态 ");
                mesageDo += pcbs[i].getProcess().getJc() + " 运行态 ";
            }

        }
        System.out.print(" 】  ");
        mesageDo += " 】  ";

        while (true) {//死循环

            for (int i = 0; i < 5; i++) {
                if (pcbs[i] == runing) {
                    arr[i] = 3;
                }
            }

            if (!waitQ.isEmpty() && waitQ.peek().getProcess().getArrive() == Alltime) {//按到达时间进入就绪队列
                for (int i = 0; i < 5; i++) {
                    if (pcbs[i] == waitQ.peek()) {
                        arr[i] = 1;
                        try {
                            res[i].setRuntime(waitQ.peek().getRuns()[5].getTime());
                            res[i].setStarttime(waitQ.peek().getProcess().getArrive());
                        } catch (Exception e) {
                            System.out.println(e.fillInStackTrace());
                        }
                    }
                }
                meg = "(" + waitQ.peek().getProcess().getJc() + "进入就绪队列 )";
                readyQ.add(waitQ.remove());
            }


            runing.setRuntime(time++);
            runing.setEndtime(runing.getRuns()[5].getTime() - runing.getRuntime());


            System.out.println(runing.getProcess().getJc() + "  已执行 "
                    + (runing.getRuntime()) + "ms " + "  还需运行时间 "
                    + (runing.getEndtime())+ "ms   " + "  当前时刻 " + Alltime + "ms  " + meg + "  ");

            mesageDo += runing.getProcess().getJc() + "  已执行 "
                    + runing.getRuntime() + "ms " + "  还需运行时间 "
                    + runing.getEndtime() + "ms   " + "  当前时刻 " + Alltime + "ms  " + meg + "  \n";
            meg = "";


            if (runing.getRuns()[next].getOpt().equals("读写磁盘") && runing.getRuntime() == runing.getRuns()[next].getTime()) {//读写磁盘退出条件
                runing.setLefttime(runing.getRuns()[next].getAdd());
                runing.setNextdo(++next);
                flag = true;
                if (timesize == 0) {
                    System.out.println("时间片用完且执行io操作");
                    mesageDo+="\n时间片用完且执行io操作\n";
                    timesize = tt;
                }
                break;
            }

            if (runing.getEndtime() == 0 || (runing.getRuns()[next].getOpt().equals("结束") && runing.getRuntime() == runing.getRuns()[next].getTime())) {//运行完毕退出条件
                System.out.println("\n" + runing.getProcess().getJc() + "  执行完毕结束时间为 " + Alltime + "   ");
                for (int i = 0; i < 5; i++) {
                    if (pcbs[i] == runing) {
                        arr[i] = -1;
                        try {
                            res[i].setEndtime(Alltime);
                            res[i].setRoundtime((res[i].getEndtime() - res[i].getStarttime()));
                            System.out.println(runing.getProcess().getJc() + " 运行时间: " + res[i].getRuntime() + "  到达时间: " + res[i].getStarttime()
                                    + " 结束时间： " + res[i].getEndtime() + " 周转时间：" + res[i].getRoundtime() + "  带权周转时间： " + res[i].getRoundtime() / res[i].getRuntime() * 1.0 + "\n");
                            mesageDo += runing.getProcess().getJc() + " 运行时间: " + res[i].getRuntime() + "  到达时间: " + res[i].getStarttime()
                                    + " 结束时间： " + res[i].getEndtime() + " 周转时间：" + res[i].getRoundtime() + "  带权周转时间： " + res[i].getRoundtime() / res[i].getRuntime() * 1.0 + "\n\n";
                            mesage += " " + runing.getProcess().getJc() + " 运行时间: " + res[i].getRuntime() + "  到达时间: " + res[i].getStarttime()
                                    + " 结束时间： " + res[i].getEndtime() + " 周转时间：" + res[i].getRoundtime() + "  带权周转时间： " + res[i].getRoundtime() / res[i].getRuntime() * 1.0 + "\n\n";
                        } catch (Exception e) {
                            System.out.println(e.fillInStackTrace());
                        }
                    }
                }
                timesize = tt;
                break;
            }

            if (!IoQ.isEmpty() && IoQ.peek().getLefttime() > 0) {
                IoQ.peek().setLefttime(IoQ.peek().getLefttime() - 1);
                if (IoQ.peek().getLefttime() == 0) {
                    System.out.println("\n" + IoQ.peek().getProcess().getJc() + "------IO操作完毕 由阻塞态转为就绪态------\n");
                    mesageDo += "\n" + IoQ.peek().getProcess().getJc() + "------IO操作完毕 由阻塞态转为就绪态------\n\n";
                    for (int i = 0; i < 5; i++) {
                        if (pcbs[i] == IoQ.peek()) {
                            arr[i] = 1;
                        }
                    }
                    readyQ.add(IoQ.remove());
                }
            }

            if (runing.getRuntime() == runing.getRuns()[next].getTime() && runing.getRuns()[next].getOpt().equals("跳转")) {
                System.out.println("\n" + runing.getProcess().getJc() + "---(" + runing.getRuns()[next].getOpt() + "  " + runing.getRuns()[next].getAdd() + ")");
                mesageDo += "\n" + runing.getProcess().getJc() + "---(" + runing.getRuns()[next].getOpt() + "  " + runing.getRuns()[next].getAdd() + ")\n";
                next++;
            }


            System.out.print(" 【  ");
            mesageDo += " 【  ";
            for (int i = 0; i < 5; i++) {//输出程序的状态
                if (arr[i] == -1) {
                    System.out.print(pcbs[i].getProcess().getJc() + " 执行完成 ");
                    mesageDo += pcbs[i].getProcess().getJc() + " 执行完成 ";
                }
                if (arr[i] == 0) {
                    System.out.print(pcbs[i].getProcess().getJc() + " 等待态 ");
                    mesageDo += pcbs[i].getProcess().getJc() + " 等待态 ";
                }
                if (arr[i] == 1) {
                    System.out.print(pcbs[i].getProcess().getJc() + " 就绪态 ");
                    mesageDo += pcbs[i].getProcess().getJc() + " 就绪态 ";
                }
                if (arr[i] == 2) {
                    System.out.print(pcbs[i].getProcess().getJc() + " 阻塞态 ");
                    mesageDo += pcbs[i].getProcess().getJc() + " 阻塞态 ";
                }
                if (arr[i] == 3) {
                    System.out.print(pcbs[i].getProcess().getJc() + " 运行态 ");
                    mesageDo += pcbs[i].getProcess().getJc() + " 运行态 ";
                }

            }
            System.out.print(" 】  ");
            mesageDo += " 】  ";
            Alltime++;
            if (timesize-- == 0) {//时间片用完了
                System.out.println("+++++");
                flag1 = true;
                break;
            }

        }

        if(flag){
            int a=next;
            System.out.println("\n"+runing.getProcess().getJc()+"\n------运行态转为阻塞态（IO请求）------"+"(执行IO操作时长 " + runing.getRuns()[a-1].getAdd() + "ms)"+"\n");
            for (int i = 0; i < 5; i++) {
                if (pcbs[i]==runing){
                    arr[i]=2;
                }
            }
            IoQ.add(runing);
        }

        if(flag1){
            System.out.println("\n"+runing.getProcess().getJc()+"------时间片用完由运行态转就绪态------"+"\n");
            mesageDo+="\n"+runing.getProcess().getJc()+"------时间片用完由运行态转就绪态------"+"\n\n";
            for (int i = 0; i < 5; i++) {
                if (pcbs[i]==runing){
                    arr[i]=1;
                }
            }
            timesize=tt;
            readyQ.add(runing);
        }
    }

   public String RunP(int sign,int tj,int ps,int np,int arr2[]){
        Init(sign,tj,ps,np,arr2);
        while (!readyQ.isEmpty()) {
            Run();
        }
        Write write=new Write();
        write.getStr("\n"+mesage);
        mesageDo+="文件保存成功！！";
        return  mesageDo;
    }

}
