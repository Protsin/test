package service;

import entity.User;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author : Ted
 * @create 2023/9/11 16:09 类功能: io读取、写入文件
 */

public class Io {

  //所有的题目集合，用于去重
  public static Set<String> questionsSet = new HashSet<>();
  public static Set<User> usersSet = new HashSet<>();
  static String  basePath="./src/main/resources/files/questions/";


  /**
   * 判断某一文件夹下的文件个数
   *
   * @param path
   * @return
   */
  public static int getFileNums(String path) {
    File directory = new File(path);
    //获取目录下的文件数
    File[] files = directory.listFiles();
    return files.length;
  }

  /**
   * 判断文件夹是否存在,不存在则创建
   *
   * @param dirPath
   */
  public static void createDirectory(String dirPath) {
    File directory = new File(dirPath);
    if (!directory.exists()) {
      boolean isCreated = directory.mkdirs();
      if (isCreated) {
        //System.out.println("文件夹不存在，已经创建成功！");
      } else {
        System.out.println("文件夹创建失败！");
      }
    }
  }

  /**
   * 写入txt文件
   *
   * @param questions
   */
  public static void writeIntoTxt(String[] questions,User user) {
    String dirPath = basePath + user.getUsername();   //注意，要先登录
    try {
      //先判断文件夹是否存在并创建
      createDirectory(dirPath);
      //int fileNum=GetFileNums(dirPath); //获取文件个数

      //开始写入
      //定义路径，使用可变字符串stringbuffer，减少空间消耗
      StringBuffer targetPath = new StringBuffer(
          basePath+ user.getUsername() + "/");
      //获取当前时间，为文件名加上时间
      List timeList = Utils.getCurrTime();
      for (int i = 0; i < timeList.size(); i++) {
        if (i == timeList.size() - 1) {
          targetPath.append(timeList.get(i));
          break;
        }
        targetPath.append(timeList.get(i) + "-");
      }
      FileWriter filewriter = new FileWriter(targetPath + ".txt");

      BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
      for (int i=0;i<questions.length;i++) {
        bufferedWriter.write("题目"+questions[i]);
        bufferedWriter.newLine(); //空一行
      }
      System.out.println("保存题目需要一点时间，请不要着急");
      //关闭流
      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
