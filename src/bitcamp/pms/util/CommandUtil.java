package bitcamp.pms.util;

import java.util.Scanner;

public class CommandUtil {
  
  public static boolean confirm(Scanner keyScan, String message) {
    String ans = null;
    while (true) {
      System.out.print(message + "(y/n) ");
      ans = keyScan.nextLine().toLowerCase();
      if (ans.equals("y")) {
        return true;
      } else if (ans.equals("n")) {
        return false;
      } else {
        System.out.println("잘못 입력하셨습니다.");
      }
    }
  }
}
