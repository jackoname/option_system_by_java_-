package OS;

import pojo.PCB;
import pojo.Reader;
public class Creat {
    static PCB[] pcbs=new PCB[5];
    static Reader reader=new Reader();

    public  PCB[] CreatPcb(){
        try {
            for (int i = 0; i < 5; i++) {//创建5个进程pcb
                pcbs[i] = new PCB();
                pcbs[i].setId(1000 + i);
                pcbs[i].setIsmer("就绪");
                pcbs[i].setProcess(reader.reProcess(i));
                pcbs[i].setRuns(reader.reRun(i));
                pcbs[i].setPrograms(reader.reProg(i));
                //   pcbs[i].setEndtime(pcbs[i].getRuns()[5].getTime());
            }
        }catch (Exception e){
            System.out.println(e.fillInStackTrace());
        }
        return pcbs;
    }

    public int [] ReadyQ(){
        int arr[]=new int[6];
        int sig[]=new int[6];
        for (int i=0; i<5; i++) {//就绪队列 信息
            arr[i]=pcbs[i].getProcess().getArrive();
            sig[i]=i;
        }
        for (int i = 0; i < 5; i++) {
            for (int i1 = 1; i1 < 5-i-1; i1++) {//按到时间排序信息
                if(arr[i1]<arr[i1-1]){
                    int t;
                    t=sig[i1];
                    sig[i1]=sig[i1-1];
                    sig[i1-1]=t;
                }
            }
        }
        return sig;
    }
}
