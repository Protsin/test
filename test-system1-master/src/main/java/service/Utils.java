package service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author : Ted
 * @create 2023/9/11 14:32 类功能: 工具类
 */
public class Utils {

  /**
   * 0,1,2分别对应小学、初中、高中, 存储用户信息
   */
  public static List<Map<String, String>> levels = new ArrayList<>();

  /**
   * 下面分别是数字和各个等级的转换
   */
  public static Map<String, Integer> namesToNum = new HashMap<>();
  public static Map<Integer, String> numToNames = new HashMap<>();


  public static void init() {
    namesToNum.put("小学", 0);
    namesToNum.put("初中", 1);
    namesToNum.put("高中", 2);
    numToNames.put(0, "小学");
    numToNames.put(1, "初中");
    numToNames.put(2, "高中");

    for (int i = 0; i < 3; i++) {
      Map<String, String> level = new HashMap<String, String>();
      levels.add(level);
    }
//    levels.get(0).put("张三1", "123");
//    levels.get(0).put("张三2", "123");
//    levels.get(0).put("张三3", "123");
//    levels.get(1).put("李四1", "123");
//    levels.get(1).put("李四2", "123");
//    levels.get(1).put("李四3", "123");
//    levels.get(2).put("王五1", "123");
//    levels.get(2).put("王五2", "123");
//    levels.get(2).put("王五3", "123");
  }

  /**
   * 通过数字获取对应学科等级
   *
   * @param status
   * @return
   */
  public static String getLevelNameByNum(int status) {
    return numToNames.get(status);
  }

  /**
   * 通过学科等级获取数字
   * @param levelName
   * @return
   */
  public static int getNumByLevelName(String levelName) {
    String tmp = levelName.substring(3, 5);

    if ("小学".equals(tmp) || "初中".equals(tmp) || "高中".equals(tmp)) {
      return namesToNum.get(tmp);
    }
    return -1;

  }

  /**
   * 查重判断,通过与set比较
   *
   * @param curQuestion
   * @return
   */
  public static boolean repetitionCheck(String curQuestion) {
    if (Io.questionsSet.contains(curQuestion)) {
      return true;
    }
    return false;
  }

  /**
   * 获取当前时间
   *
   * @return
   */
  public static List getCurrTime() {
    // 获取当前时间
    LocalDateTime now = LocalDateTime.now();
    List<Integer> list = new ArrayList<>();
    // 获取年、月、日、时、分、秒
    int year = now.getYear();
    int month = now.getMonthValue();
    int day = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = now.getMinute();
    int second = now.getSecond();
    list.add(year);
    list.add(month);
    list.add(day);
    list.add(hour);
    list.add(minute);
    list.add(second);

    return list;
  }

}
