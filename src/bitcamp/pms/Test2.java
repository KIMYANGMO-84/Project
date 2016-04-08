package bitcamp.pms;

import bitcamp.pms.util.PatternChecker;

public class Test2 {

  public static void main(String[] args) {
    String[] strs = {
    "000-0000-0000",
    "00-0000-0000",
    "00000-000-0000",
    "0000-00000",
    "00000-0000",
    "1111-1111"};
    for (int i = 0; i < strs.length; i++)
      System.out.println(PatternChecker.isTel(strs[i]));
    
    System.out.println("--------------------------------------");
    for (int i = 0; i < strs.length; i++)
      System.out.println(PatternChecker.isCellPhone(strs[i]));
  }

}
