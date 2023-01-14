package pojo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Write {
 public void getStr(String meg) {
       try {
           BufferedWriter out = new BufferedWriter(new FileWriter("result.txt"));
           out.write(meg);
           out.close();
           System.out.println("文件创建成功！");}
       catch (IOException e){}
   }

}

