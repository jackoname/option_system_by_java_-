package pojo;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Reader {
    public static String readFile(String fileName)
    {
        String fileContent = "";
        try
        {
            File f = new File(fileName);
            if(f.isFile()&&f.exists())
            {
                InputStreamReader read = new InputStreamReader(new FileInputStream(f));
                BufferedReader reader=new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null)
                {
                    fileContent += line+"\n";
                }
                read.close();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return fileContent;
    }
    public  Process reProcess(int key) {
        List<String> str = Arrays.asList(readFile("src\\Process.txt").split("\n"));
        Process pro=new Process();
        try {
            if(key==0) {
                List<String> strings = Arrays.asList(str.get(0).split("\t"));
                pro.setJc(strings.get(0));
                pro.setArrive(Integer.valueOf(strings.get(1)));
                pro.setPs(strings.get(3));
            }
            else  if(key==1){
                List<String> strings = Arrays.asList(str.get(1).split("\t"));
                pro.setJc(strings.get(0));
                pro.setArrive(Integer.valueOf(strings.get(1)));
                pro.setPs(strings.get(3));
            }
            else  if(key==2){
                List<String> strings = Arrays.asList(str.get(2).split("\t"));
                pro.setJc(strings.get(0));
                pro.setArrive(Integer.valueOf(strings.get(1)));
                pro.setPs(strings.get(3));
            }
            else  if(key==3){
                List<String> strings = Arrays.asList(str.get(3).split("\t"));
                pro.setJc(strings.get(0));
                pro.setArrive(Integer.valueOf(strings.get(1)));
                pro.setPs(strings.get(3));
            }
            else  if(key==4){
                List<String> strings = Arrays.asList(str.get(4).split("\t"));
                pro.setJc(strings.get(0));
                pro.setArrive(Integer.valueOf(strings.get(1)));
                pro.setPs(strings.get(3));
            }
        }catch (Exception e){}
        return pro;
    }
    public  Program[] reProg(int key) {
        Program pro[]=new  Program[6];
        for (int i = 0; i < 6; i++) {
            pro[i]= new Program();
        }
        String str1 ="";
        String name="",size="";
        List<String> str = Arrays.asList(readFile("src\\program.txt").split("文件名"));
        for (int i = 1; i <=5; i++) {
            List<String> str2 = Arrays.asList(str.get(i).split("\n"));
            for (int j = 1; j < str2.size(); j++) {
                List<String> str3 = Arrays.asList(str2.get(j).split("\t"));
                try {

                   name+=str3.get(0)+"/";
                   size+=str3.get(1)+"/";
                }catch (Exception e){}
            }
        }
        String[] name1= name.split("/");
        String[] size1= size.split("/");
       try {
           int j=0;
           if (key==0) {
               for (int i = 0; i < 5; i++) {
                   pro[j].setNopro(name1[i]);
                   pro[j].setSize(Double.valueOf(size1[i]));
                //   System.out.println(pro[j].getNopro() + "   " + pro[j].getSize());
                   j++;
               }

           } else if (key==1) {
               for (int i = 5; i < 11; i++) {
                   pro[j].setNopro(name1[i]);
                   pro[j].setSize(Double.valueOf(size1[i]));
                   //System.out.println(pro[j].getNopro() + "   " + pro[j].getSize());
                   j++;
               }
           } else if (key==2) {
               for (int i = 11; i < 15; i++) {
                   pro[j].setNopro(name1[i]);
                   pro[j].setSize(Double.valueOf(size1[i]));
                 //  System.out.println(pro[j].getNopro() + "   " + pro[j].getSize());
                   j++;
               }
           } else if (key==3) {
               for (int i = 15; i < 20; i++) {
                   pro[j].setNopro(name1[i]);
                   pro[j].setSize(Double.valueOf(size1[i]));
                  // System.out.println(pro[j].getNopro() + "   " + pro[j].getSize());
                   j++;
               }
           } else {
               for (int i = 20; i < 25; i++) {
                   pro[j].setNopro(name1[i]);
                   pro[j].setSize(Double.valueOf(size1[i]));
                 //  System.out.println(pro[j].getNopro() + "   " + pro[j].getSize());
                   j++;
               }
           }
       }catch (Exception e){
      //     System.out.println(e.fillInStackTrace());
       }
      //  System.out.println("------------------------------------------------------------");
        for (int i = 0; i < name1.length; i++) {

          //  System.out.println(name1[i] +"  "+size1[i]);
        }
        return pro;
    }
    public  Run[] reRun(int key) {
        Run run[] = new Run[6];
        for (int i = 0; i < 6; i++) {
            run[i]= new Run();
        }
        String str1 ="";
        String time="",opt="",adder="";
       List<String> str = Arrays.asList(readFile("src\\run.txt").split(".*程序"));
        for (int j = 0; j <str.size(); j++) {
            String[] str5=str.get(j).split("\n");
            for (int i = 1; i < str5.length; i++) {
                String[] str3 = str5[i].split("\t");
                try {
             //       System.out.println(str3[0] + "   " + str3[1] + "   " + str3[2]);
                    time+=str3[0]+"/";
                    opt+=str3[1]+"/";
                    adder+=str3[2]+"/";
                }catch (Exception e){}
            }
        }
        String time1[]=time.split("/");
        String opt1[]=opt.split("/");
        String adder1[]=adder.split("/");
        try {
            int j=0;
            if (key==0) {
                for (int i = 0; i < 6; i++) {
                    run[j].setTime(Integer.valueOf(time1[i]));
                    run[j].setOpt(opt1[i]);
                    run[j].setAdd(Integer.valueOf(adder1[i]));
             //       System.out.println(run[j].getTime() + "   " + run[j].getOpt()+"  "+run[j].getAdd());
                    j++;
                }

            } else if (key==1) {
                for (int i = 6; i < 12; i++) {
                    run[j].setTime(Integer.valueOf(time1[i]));
                    run[j].setOpt(opt1[i]);
                    run[j].setAdd(Integer.valueOf(adder1[i]));
            //        System.out.println(run[j].getTime() + "   " + run[j].getOpt()+"  "+run[j].getAdd());
                    j++;
                }
            } else if (key==2) {
                for (int i = 12; i < 18; i++) {
                    run[j].setTime(Integer.valueOf(time1[i]));
                    run[j].setOpt(opt1[i]);
                    run[j].setAdd(Integer.valueOf(adder1[i]));
            //        System.out.println(run[j].getTime() + "   " + run[j].getOpt()+"  "+run[j].getAdd());
                    j++;
                }
            } else if (key==3) {
                for (int i = 18; i < 24; i++) {
                    run[j].setTime(Integer.valueOf(time1[i]));
                    run[j].setOpt(opt1[i]);
                    run[j].setAdd(Integer.valueOf(adder1[i]));
           //         System.out.println(run[j].getTime() + "   " + run[j].getOpt()+"  "+run[j].getAdd());
                    j++;
                }
            } else if (key==4){
                for (int i = 24; i < 30; i++) {
                    run[j].setTime(Integer.valueOf(time1[i]));
                    run[j].setOpt(opt1[i]);
                    run[j].setAdd(Integer.valueOf(adder1[i]));
             //       System.out.println(run[j].getTime() + "   " + run[j].getOpt()+"  "+run[j].getAdd());
                    j++;
                }
            }
        }catch (Exception e){
         //   System.out.println(e.fillInStackTrace()+"错误");
        }
        return run;
    }
}

