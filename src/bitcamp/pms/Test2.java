package bitcamp.pms;

import java.util.HashMap;

public class Test2 {

  public static void main(String[] args) {
    HashMap<String,Object> pool = new HashMap<>();
    pool.put("state", true);
    
    System.out.println(pool.get("state"));
    System.out.println("------------------");
    pool.put("state", false);
    
    System.out.println(pool.get("state"));
    System.out.println("------------------");
  }
}
