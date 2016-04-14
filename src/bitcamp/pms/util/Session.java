/*역할
 *-controller 객체들이 공통으로 사용할 값 저장
 *
 */
package bitcamp.pms.util;

import java.util.HashMap;

public class Session {  
  HashMap<String, Object> pool = new HashMap<>();
  
  public Session() {
    pool.put("state", false);
  }
  
  public void setAttribute(String name, Object value) {
    pool.put(name,value);
  } 

  public Object getAttribute(String name) {
    return pool.get(name);
  }
}
