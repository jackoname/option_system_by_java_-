package OS;
import pojo.Program;
import pojo.Reader;
import pojo.Run;
import java.util.ArrayList;
import java.util.List;
public class page {
   Program program[]=new Program[5];
   double Pagesize[]=new double[5];
   int numPage[]=new  int[5];
   double pageS;//每个程序页面总大小
  int sort[][]=new int[5][6];//每个程序页面申请序列
   Reader reader =new Reader();
    Run run[]=new Run[5];
    int BlockRun[][];//运行物理块
    int block;
    int blocksize[]=new int[5];//每个程序的物理块
    boolean isNopage[]=new boolean[5];
   String  mesageDo="";
    public  void GetSize(){//获取页面大小，及程序大小
        for (int i = 0; i < 5; i++) {
            run=reader.reRun(i);
            program=reader.reProg(i);
            for (int i1 = 0; i1 < 5; i1++) {
                Pagesize[i]+=program[i1].getSize();
            }
            for (int i1 = 0; i1 < 6; i1++) {
                if(!run[i1].getOpt().equals("读写磁盘")&&run[i1].getAdd()>0)
                    sort[i][i1]=run[i1].getAdd();
                else
                    sort[i][i1]=-999;
            }

        }
    }

    public void divPage(double s,int arr[]){

        for (int i = 0; i < 5; i++) {//确定物理块个数
            blocksize[i]=arr[i];
        }
        pageS=s;
        for (int i = 0; i < 5; i++) {
            numPage[i]=(int)(Pagesize[i]*1024/s);
            System.out.println("程序"+(char)(65+i)+" 总计大小："+String.format("%,.2f",Pagesize[i]*1024)+" 字节  分为 "+(numPage[i]+1)+" 页  从0 到"+numPage[i]);
            mesageDo+="程序"+(char)(65+i)+" 总计大小："+String.format("%,.2f",Pagesize[i]*1024)+" 字节  分为 "+(numPage[i]+1)+" 页  从0 到"+numPage[i]+"\n";
        }
    }

    public  boolean[] FIFO(int b[],int p){
        int a[][]= new int[p][6];

        for (int i1 = 0; i1 < 5; i1++) {
            for (int k = 0; k <blocksize[0] ; k++) {
                a[k][i1]=65535;
            }
        }
        int flag=0;
        int noPage=0;
        boolean b1=true;

        for (int i = 0; i <b.length; i++) {//页数循环
            if (b[i] >= 0) {
                b1 = true;
                for (int i1 = 0; i1 < p; i1++) {//判断物理块是否有该页
                    if (a[i1][i] == b[i]) {//如果有
                        b1 = false;
                        isNopage[i]= true;
                    break;

                    }
                }

                if (b1) {//如果没有调进该页 ---
                    a[flag++ % p][i] = b[i];
                    noPage++;
                }

                for (int j = 0; j < p; j++) {
                    a[j][i + 1] = a[j][i];

                }

            }
            else break;
        }
        for (int i = 0; i < p; i++) {
            for (int i1 = 0; i1 < 5; i1++) {
                if(b[i1]<0)
                    break;
                if (a[i][i1]!=65535) {
                    System.out.print(a[i][i1] + "  ");
                mesageDo+=a[i][i1] + "  ";
                }
                else {
                    System.out.print("*" + "  ");
                    mesageDo+="*" + "  ";
                }
            }
            System.out.println();
            mesageDo+="\n";
        }
        int ff=0;
        if(ff++==0) {
            mesageDo+=" 页面请求序列 ：";
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if(sort[i][j]>0) {
                        mesageDo += "   " + (int) (sort[i][j] / (int) pageS);
                    }
                }
                mesageDo+="\n";
            }
        }
        System.out.print("\n缺页 ： "+ noPage);
        mesageDo+="\n缺页 ： "+ noPage;
        if(b[4]<0){
            System.out.print("    缺页率 ： "+ ((double)noPage/4.0)*100+"%\n\n");
            mesageDo+="    缺页率 ： "+ ((double)noPage/4.0)*100+"%\n\n";
        }
        else{
            System.out.print("    缺页率 ： "+  ((double)noPage/5.0)*100+"%\n\n");
            mesageDo+="    缺页率 ： "+  ((double)noPage/5.0)*100+"%\n\n";
        }
        return  isNopage;
    }
    public  boolean[] LRU(int b[],int p){
        int flag=0;
        int noPage=0;
        boolean b1=true;
        List<Integer> list= new ArrayList<Integer>();
        int a[][]= new int[p][6];
        for (int i1 = 0; i1 < 5; i1++) {
            for (int k = 0; k <blocksize[0] ; k++) {
                a[k][i1]=65535;
            }
        }
        for (int i = 0; i <b.length; i++) {//页数循环
            if (b[i]>=0) {
                b1 = true;
                for (int i1 = 0; i1 < p; i1++) {//判断物理块是否有该页
                    if (a[i1][i] == b[i]) {//如果有
                        b1 = false;
                        isNopage[i]=true;
                        break;
                    }
                }

                if (b1) {//如果没有调进该页 ---
                   int maxsign=0;
                    int dis=0,nim=999;
                    int sign=0;
                    boolean aa=false;
                    int l=0;
                       //如果物理块不满则按顺序添加页面
                    for (int i1 = 0; i1 < p; i1++) {
                        if(a[i1][i]==65535) {
                            aa = true;
                            l = i1;
                            break;
                        }
                    }
                    if (aa) {
                        noPage++;
                        a[l][i] = b[i];
                        list.add(b[i]);
                    }
                    else {
                        noPage++;
                        for (int j = 0; j <p ; j++) {
                            for (int k = list.size()-1; k >=0 ; k--) {
                                if (a[j][i]==list.get(k)){
                                    dis=k;
                                    sign=j;
                                    break;
                                }
                            }
                            if(dis<nim){
                                nim=dis;
                                maxsign=sign;
                            }
                        }
                        a[maxsign][i]=b[i];
                        list.add(b[i]);
                    }
                }
                for (int j = 0; j < p; j++) {
                    a[j][i + 1] = a[j][i];
                }

            }
            else break;
        }
        for (int i = 0; i < p; i++) {
            for (int i1 = 0; i1 < 5; i1++) {
                if(b[i1]<0)
                    break;
                if (a[i][i1]!=65535) {
                    System.out.print(a[i][i1] + "  ");
                    mesageDo+=a[i][i1] + "  ";
                }
                else {
                    System.out.print("*" + "  ");
                    mesageDo+="*" + "  ";
                }

            }
            System.out.println();
            mesageDo+="\n";
        }
        System.out.print("\n缺页 ： "+ noPage);
        mesageDo+="\n缺页 ： "+ noPage;
        int ff=0;
        if(ff++==0) {
            mesageDo+=" 页面请求序列 ：";
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if(sort[i][j]>0) {
                        System.out.println("页面请求序列 ：" + sort[i][j]);
                        mesageDo += "   " + (int) (sort[i][j] / (int) pageS);
                    }
                }
                mesageDo+="\n";
            }
        }
        if(b[4]<0){
            System.out.print("    缺页率 ： "+ ((double)noPage/4.0)*100+"%\n\n");
            mesageDo+="    缺页率 ： "+ ((double)noPage/4.0)*100+"%\n\n";
        }
        else{
            System.out.print("    缺页率 ： "+  ((double)noPage/5.0)*100+"%\n\n");
            mesageDo+="    缺页率 ： "+  ((double)noPage/5.0)*100+"%\n\n";
        }
        return  isNopage;
    }
    public  boolean[] Runpage(int pagesize,int i,int flag,int arr[]) {//返回是否缺页
        if(i==0) {
            GetSize();
            divPage(pagesize, arr);
        }
            for (int i1 = 0; i1 < 5; i1++) {
                isNopage[i1] = false;
            }
            int a[] = new int[5];
            int j = 0;
            for (int i1 = 0; i1 < 6; i1++) {
                if (sort[i][i1] > 0) {
                    a[j++] = sort[i][i1];
                }
            }
            for (int i1 = 0; i1 < 5; i1++) {
                if (a[i1] == 0)
                    a[i1] = -999999;
                a[i1] = (int) (a[i1] / pageS);
                //  System.out.print(a[i1]+"  ");
            }

            if(flag==1) {
                System.out.println("--FIFO----程序 "+((char)(65+i))+"-----------------------------------");
                mesageDo+="\"--FIFO----程序"+((char)(65+i))+"-----------------------------------\n";
                return FIFO(a, blocksize[i]);

            }
            else {
                System.out.println("--LRU----程序 "+((char)(65+i))+"-----------------------------------");
                mesageDo+="--LRU----程序"+((char)(65+i))+"-----------------------------------\n";
                return LRU(a, blocksize[i]);
            }
    }
    public  int[][] reSort(){
        return sort;
    }
    public  String reMsg(){
        return mesageDo;
    }
}
