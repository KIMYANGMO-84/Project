/*
 * 용도
 * => ApplicationContext가 관리하는 객체 중에서 명령을 처리하는 메서드 정보를
 *    따로 보관하여 관리한다.
 * => ProjectApp은 명령어를 처리할 때 이 클래스를 통해 메서드 정보를 얻어서 
 *    명령어를 처리한다
 * 
 */

package bitcamp.pms.context.request;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.context.ApplicationContext;

public class RequestHandlerMapping {
  ApplicationContext appContext;
  HashMap<String, RequestHandler> handlerMap = new HashMap<>();
  
  public RequestHandlerMapping(ApplicationContext appContext) {
    this.appContext = appContext;
    
    //1) ApplicationContext를 통하여 @Controller가 붙은 객체를 꺼낸다.
    Map<String, Object> controllersMap = 
          appContext.getBeansWithAnnotation(Controller.class);  
    
    Collection<Object> controllers = controllersMap.values();
    Method[] methods = null;
    RequestMapping anno = null;
    RequestMapping classAnno = null;
    String baseName = null;
    
    for (Object controller : controllers) {
      // 클래스에 @RequestMapping이 붙어있다면, 기본 이름으로 저장한다.
      classAnno = controller.getClass().getAnnotation(RequestMapping.class);
      if (classAnno != null) {
        baseName = classAnno.value();
      } else {
        baseName = "";
      }
      //2) 각 객체에서 @RequestMapping이 붙은 메서드를 꺼낸다.
      methods = controller.getClass().getMethods();
      
      for (Method m : methods) {
        anno = m.getAnnotation(RequestMapping.class);
        if (anno == null) {
          continue;
        }
        //3) RequestHandler 객체에 메서드와 객체 정보를 저장한다.        
        //4) requestMap에 RequestHandler 객체를 보관한다
        handlerMap.put(baseName + anno.value(), new RequestHandler(m, controller));
        //System.out.printf("%s --> %s\n", controller.getClass().getName(), m.getName());
      }      
    }    
  }

  public RequestHandler getRequestHandler(String name) {
    return handlerMap.get(name);    
  }
}
