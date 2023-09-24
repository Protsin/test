package entity.impl;

import entity.Question;
import entity.User;
import gui.DisplayQuestion;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import service.Io;

/**
 * @Author : Ted
 * @create 2023/9/19 11:33 类功能:
 */
public class QuestionImpl implements Question {
  String[] signs = {"+", "-", "*", "/"};
  String[] operands;
  /**
   * 对应位置是否包含括号，只有同时包含才非法
   */
  boolean[] flagsKuo;

  /**
   * 对应位置是否有拼音
   */
  boolean[] flagsPingfang;
  /**
   * 对应位置是否有根号
   */
  boolean[] flagsGeng;
  /**
   * 对应位置是否有三角函数
   */
  boolean[] flagsTrigonometricFunc;
  Random random = new Random();
  static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

  /**
   * 全部符号
   */
  String[] symbol = new String[]{"+", "-", "*", "/", "²", "√", "sin", "cos", "tan"};//符号
  int length=0,xNum=0,lenX=0,kuohao=0,left=0;
  String testLine = new String();//问题
  String result = new String();//计算含特殊符号的式子后的问题
  /**
   * 出过的题目会放进set，用于去重
   */
  Set<String> questionSet=null;
  int total=0;
  int correct=0;

  String[] tests;
  String[] answers;
  String[][] selections;
  int []correctPosition;
  static int i;

  public String[] getTests(){
    return tests;
  }
  public String[] getAnswers(){
    return answers;
  }
  public String[][] getSelections(){
    return selections;
  }
  public int[] getCorrectPosition(){
    return correctPosition;
  }
  /**
   * 参数一是用户的答案，参数二是正确答案
   * @param answerByUser
   * @param result
   */
  public void addResult(String answerByUser,String result){
    if(answerByUser!=null&&answerByUser.equals(result)){
      correct++;
    }
    total++;
  }



  public String getScore(int[] userSelections){
    for(int i=0;i<Math.min(userSelections.length,correctPosition.length);i++){
      System.out.println("答案位置："+correctPosition[i]);
      System.out.println("用户选择的位置："+userSelections[i]);
      System.out.println("");
      if(userSelections[i]==correctPosition[i]){
        correct++;
      }
      total++;
    }
    float number=correct*100/total;
    String result = String.format("%.1f", number);
    return result;
  }

  public void generateSelections(int num){
    Random rand = new Random();
    for(int k=0;k<num;k++){
      String []ops=new String[4];
      int loc = rand.nextInt(4)+0 ;//随机正确答案的选项
      for (int i = 0; i < 4; i++) {//随机四个选项的式子
        if (i == loc) {
          ops[i] = answers[k];
        } else {
          ops[i] = String.valueOf(rand.nextFloat()*100);
        }
      }
      selections[k]=ops;
      correctPosition[k]=loc+1;
    }
  }


  public QuestionImpl(){
    total=0;
    correct=0;
    questionSet=new HashSet<>();
  }

  public QuestionImpl(int num){
    total=0;
    correct=0;
    questionSet=new HashSet<>();

    tests=new String[num];
    answers=new String[num];
    selections=new String[num][4];
    correctPosition=new int[num];
  }

  void init(String line,String res,int len,int xnum,int lenx,int kuo,int l){
    testLine = line;//初始化
    result = res;
    length = len;
    xNum = xnum;
    lenX = lenx;//随机生成1-3
    kuohao = kuo;
    left = l;
  }

  /**
   * 判断是否重复；不重复则加入set，重复则返回true
   * @param test
   * @return
   */
  public boolean isRepeated(String test){
    if(questionSet.contains(test)){
      return true;
    }
    questionSet.add(test);
    return false;
  }
  public void generateQuestion(int level,User user,int num){
    String[] res=new String[2];
    if(level==0){
      createPrimaryTests(num);
      generateSelections(num);
      Io.writeIntoTxt(tests,user);
    }
    else if(level==1){
      createJuniorTests(num);
      generateSelections(num);
      Io.writeIntoTxt(tests,user);
    }
    else if(level==2){
      createSeniorTests(num);
      generateSelections(num);
      Io.writeIntoTxt(tests,user);
    }
  }

  public User changeState(User user,int level){
    user.setState(level);
    return user;
  }


  void leftKuo(int kuoNum,int l){
    testLine = testLine + "(";//随机生成含特殊符号的式子
    result = result + "(";
    kuohao=+kuoNum;
    left=l;
  }

  int junior(Random r,int receive){
    if (r.nextInt(2) == 0) {//随机生成含特殊符号的式子
      if (r.nextInt(2) == 0) {
        testLine = testLine + String.valueOf(receive) + symbol[4];
        result = result + String.valueOf(receive * receive);
      } else {
        testLine = testLine + symbol[5] + String.valueOf(receive);
        result = result + String.valueOf(Math.sqrt(receive));
      }

      --xNum;
    } else {//随机生成不含特殊符号的式子
      testLine = testLine + String.valueOf(receive);
      result = result + String.valueOf(receive);
    }

    if (r.nextInt(2) == 0 && kuohao > 0 && left >= 2) {//随机生成左括号
      testLine = testLine + ")";
      result = result + ")";
      --kuohao;
    }
    return receive;
  }

  int senior(Random r,int receive){
    if (r.nextInt(2) == 0 && kuohao > 0) {//随机生成左括号
      leftKuo(-1,left+1);
      --kuohao;
      receive = 0;
    }
    ++receive;
    int num = r.nextInt(100) + 1;//随机生成数字
    if (r.nextInt(3) == 0) {//随机生成含特殊符号的式子
      if (r.nextInt(2) == 0) {
        testLine = testLine + String.valueOf(num) + symbol[4];
        result = result + String.valueOf(num * num);
      } else {
        testLine = testLine + symbol[5] + num;
        result = result + String.valueOf(Math.sqrt(num));
      }

      --xNum;
    } else if (r.nextInt(2) == 0) {//随机生成含特殊符号sin cos tan的式子
      int signs = r.nextInt(3) + 6;
      testLine = testLine + symbol[signs] + num;
      float a = 0;
      if (signs == 6) {
        a = (float) Math.sin(num);
      }
      if (signs == 7) {
        a = (float) Math.cos(num);
      }
      if (signs == 8) {
        a = (float) Math.tan(num);
      }
      if (a >= 0) {
        result = result + String.valueOf(a);
      } else {
        result = result + "(" + String.valueOf(a) + ")";
      }
      --lenX;
    } else {//随机生成不含特殊符号的式子
      testLine = testLine + String.valueOf(num);
      result = result + String.valueOf(num);
    }
    return  receive;
  }

  @Override
  public void createPrimaryTests(int num) {
    // 使用当前时间作为随机种子，避免生成的序列相同
    long seed = System.currentTimeMillis();
    random.setSeed(seed);
    for(int k=0;k<num;k++){
      StringBuffer finalStr=null;
      while(true) {
        finalStr= new StringBuffer();
        //操作数个数
        int numbers = random.nextInt(4) + 2;
        operands = new String[numbers];
        flagsKuo = new boolean[numbers];
        //1.生成操作数
        for (int j = 0; j < numbers; j++) {
          //生成1-100的数字
          operands[j] = String.valueOf(random.nextInt(100) + 1);
        }
        //2.生成括号，长度>=3才生成
        if (numbers >= 3) {
          generateParentheses(numbers);
        }
        //3.拼接算术符号
        for (int j = 0; j < numbers - 1; j++) {
          finalStr.append(operands[j] + signs[random.nextInt(4)]);
        }
        finalStr.append(operands[numbers - 1]);
        //4.判断是否重复，如果重复则重新生成
        if(isRepeated(finalStr.toString())){
          continue;
        }else{
          break;
        }
      }
      tests[k]=i+": "+finalStr.toString();
      try {
        answers[k]=jse.eval(finalStr.toString()).toString();
      } catch (ScriptException e) {
        e.printStackTrace();
      }
    }
  }


  @Override
  public void createJuniorTests(int num) {
    // 使用当前时间作为随机种子，避免生成的序列相同
    long seed = System.currentTimeMillis();
    Random r=new Random();
    r.setSeed(seed);
    for(int k=0;k<num;k++){
      int receive;
      int curRound=0;
      while(true) {
        do {
          init("","",0, r.nextInt(2) + 1,r.nextInt(2),0,0);
          while (true) {
            if (r.nextInt(2) == 0 && lenX > 0) {//随机生成左括号
              leftKuo(1,0);
              --lenX;
            }
            ++left;
            receive = r.nextInt(100) + 1;//随机生成数字
            receive=junior(r,receive);
            if (length >= 2 && xNum <= 0 && lenX <= 0 && kuohao <= 0) {//当满足长度符号括号个数时结束循环
              break;
            }
            int x = r.nextInt(4);
            testLine = testLine + symbol[x];//将问题赋给计算含特殊符号的式子后的问题
            result = result + symbol[x];
            ++length;
          }
        } while (length >= 5 || left >= 5);//满足长度小于5时结束循环
        if(curRound>1000||!isRepeated(testLine)){
          break;
        }
        curRound++;
      }
      tests[k]=i+": "+testLine;
      try {
        answers[k]=jse.eval(result).toString();
      } catch (ScriptException e) {
        e.printStackTrace();
      }
    }
  }

  //生成num个高中题目
  @Override
  public void createSeniorTests(int num) {
    long seed = System.currentTimeMillis();
    Random r = new Random();//随机数
    r.setSeed(seed);
    for(int k=0;k<num;k++){
      int receive = 0;
      int curRound=0;
      while(true){
        do {
          init("","",0, 1,r.nextInt(3) + 1,r.nextInt(3),0);
          receive = 0;
          while (true) {
            receive=senior(r,receive);
            if (r.nextInt(2) == 0 && left > 0 && receive >= 2) {
              testLine = testLine + ")";
              result = result + ")";
              --left;
            }
            if (length >= 2 && xNum <= 0 && lenX <= 0 && kuohao <= 0 && left <= 0) {//当满足长度符号括号个数时结束循环
              break;
            }
            int x = r.nextInt(4);
            testLine = testLine + symbol[x];//将问题赋给计算含特殊符号的式子后的问题
            result = result + symbol[x];
            ++length;
          }
        } while (length >= 5 || receive >= 5);//满足长度小于5时结束循环
        if (curRound>1000||!isRepeated(testLine)) {
          break;
        }
        curRound++;
      }
      tests[k]=i+": "+testLine;
      try {
        answers[k]=jse.eval(result).toString();
      } catch (ScriptException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 生成括号,参数为：操作数个数 生成原则，操作数个数>=3时生成，个数为1至numbers-2；生成一个长度2<=len<=numbers-1
   * 起始位置:start<numbers-len,这样结束位置<=start+len<numbers 注意，生成(号前，该位置不能有); 生成)号前，该位置不能有(
   *
   * @param numbers
   */
  public void generateParentheses(int numbers) {
    //个数为1至numbers-2
    int kuohaoNums = random.nextInt(numbers - 2) + 1;
    while (kuohaoNums > 0) {
      //生成一个长度2<=len<=numbers-1
      int len = random.nextInt(numbers - 2) + 2;
      int start = random.nextInt(numbers - len), end = start + len - 1;
      //如果首尾重复了，则生成失败
      if (flagsKuo[start] && flagsKuo[end]) {
        kuohaoNums--;
        continue;
      }
      operands[start] = "(" + operands[start];
      operands[end] += ")";
      //设置标记，已经加过括号了
      flagsKuo[start] = true;
      flagsKuo[end] = true;
      kuohaoNums--;
    }
  }


  /**
   * 生成平方和根号,最多生成numbers个（限制难度）
   *
   * @param numbers
   */
  public void generateSpecialSigns1(int numbers) {
    int totals = random.nextInt(numbers) + 1;
    for (int i = 0; i < totals; i++) {
      int position = random.nextInt(numbers);
      //一半概率生成平方，并且他之前没生成过平方
      if (random.nextBoolean()) {
        if (!flagsPingfang[position]) {
          operands[position] += "²";
          flagsPingfang[position] = true;
        } else {
          continue;  //重复了，继续
        }
      } else {

        if (!flagsGeng[position]) {
          operands[position] = "√" + operands[position];
          flagsGeng[position] = true;
        } else {
          continue;  //重复了，继续
        }
      }
    }
  }

  /**
   * 生成三角函数,最多生成numbers-1个，并且同一位置不重复生成（限制难度）
   *
   * @param numbers
   */
  public void generateTrigonometricFunc(int numbers) {
    int totals = random.nextInt(numbers);
    for (int i = 0; i < totals; i++) {
      int position = random.nextInt(numbers);
      int state = random.nextInt(3);
      //生成sin
      if (state == 0 && !flagsTrigonometricFunc[position]) {
        operands[position] = "sin" + operands[position];
        flagsTrigonometricFunc[position] = true;
      }
      //生成cos
      else if (state == 1 && !flagsTrigonometricFunc[position]) {
        operands[position] = "cos" + operands[position];
        flagsTrigonometricFunc[position] = true;
      }
      //生成tan
      else if (state == 2 && !flagsTrigonometricFunc[position]) {
        operands[position] = "tan" + operands[position];
        flagsTrigonometricFunc[position] = true;
      }
    }
  }
}
