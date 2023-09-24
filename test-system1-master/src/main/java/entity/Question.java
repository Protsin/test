package entity;

/**
 * @Author : Ted
 * @create 2023/9/19 11:33 类功能:
 */
public interface Question {
  /**
   * 出小学题，
   */
  public void createPrimaryTests(int num);

  /**
   * 出初中题
   */
  public void createJuniorTests(int num);

  /**
   * 出高中题，
   */
  public void createSeniorTests(int num);

}
