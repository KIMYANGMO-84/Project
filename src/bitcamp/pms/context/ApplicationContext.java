/* Responsibility(or Role)
 * => 객체를 준비하고 관리한다. -->"빈 컨테이너 (bean container)"라고 부른다.
 * => 객체(인스턴스) --> 자바에서는 "bean"이라고 부른다.
 * => 관리하는 클래스를 보통 "컨테이너(container)"라고 부른다.
 *
 * Component가 붙은 클래스에 대해서만 인스턴스를 생성하고 관리한다
 */
package bitcamp.pms.context;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.annotation.Controller;


public class ApplicationContext {
  HashMap<String,Object> objPool = new HashMap<>();

  public ApplicationContext(String basePackage) {
    String path = "./bin/" + basePackage.replace(".", "/");
    createObject(new File(path));
    injectDependency();
  }

  private void injectDependency() {
    Collection<Object> objects = objPool.values();
    Class<?> clazz = null;
    Method[] methods = null;
    Class<?> paramType = null;
    Object dependency = null;
    for (Object obj : objects) {      
      clazz = obj.getClass();
      
      //우리가 만든 클래스(@Component, @Controller)에 대해서만 의존 객체 주입을 수행한다.
      //빈 컨테이너가 우리가 만든 객체 외에 다른 객체도 포함하기 때문에
      //의존 객체 주입할 때 검사해야 한다.
      if (!clazz.isAnnotationPresent(Component.class) && 
          !clazz.isAnnotationPresent(Controller.class)) {
        continue;
      }
        
      
      //System.out.println(clazz.getName());
      methods = clazz.getMethods();
      for (Method m : methods) {
        if(!m.getName().startsWith("set")) {
        continue;
        }
        paramType = m.getParameterTypes()[0];
        //System.out.printf("-->%s(%s)\n", m.getName(), paramType.getName());
        dependency = findObjectByType(paramType);
        if (dependency == null) {
          continue;
        }
        try {
          m.invoke(obj, dependency); // 셋터를 호출하여 의존 객체를 주입한다.
        } catch (Exception e) {}
      }
    }
  }

  private Object findObjectByType(Class<?> paramType) {
    Collection<Object> objects = objPool.values();
    for (Object obj : objects) {
      if (paramType.isInstance(obj)) {
        return obj;
      }
    }
    return null;
  }

  private void createObject(File file) {
    if (file.isFile() && file.getName().endsWith(".class")) {
      String classNameWithPackage = file.getPath()
                        .replace("./bin/", "")
                        .replace(".class", "")
                        .replace("/", ".");
      try {
        Class<?> clazz = Class.forName(classNameWithPackage);
        //@Component 또는 @Controller 애노테이션이 붙었는지 알아본다

        Annotation[] annos = clazz.getAnnotations();
        for (Annotation anno : annos) {
          if (anno.annotationType() == Component.class) {
            //System.out.printf("%s --> %s\n", clazz.getName(), "Component");
            processComponentAnnotation(clazz);
          } else if (anno.annotationType() == Controller.class) {
           // System.out.printf("%s --> %s\n", clazz.getName(), "Controller");
            processControllerAnnotation(clazz);
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return;
    }
    
    if (!file.isDirectory()) {
      return;
    }
    
    File[] subfiles = file.listFiles();
    for (File subfile : subfiles) {      
      createObject(subfile);
    }
  }

  private void processControllerAnnotation(Class<?> clazz) throws Exception {
    Controller anno = clazz.getAnnotation(Controller.class);
    String key = anno.value();
    if (key.equals("")) {
      key = clazz.getName();
      }
      objPool.put(key, clazz.newInstance()); // 애노테이션의 value값을 이용하여 인스턴스를 저장한다.
    }


  private void processComponentAnnotation(Class<?> clazz) throws Exception {
    Component anno = clazz.getAnnotation(Component.class);
    String key = anno.value();
    if (key.equals("")) {
      key = clazz.getName();
      }
      objPool.put(key, clazz.newInstance()); // 애노테이션의 value값을 이용하여 인스턴스를 저장한다.
    }

  public List<Object> getBeans(Class<?> beanType) {
    ArrayList<Object> list = new ArrayList<>();
    Collection<Object> objects = objPool.values();
    for (Object obj : objects) {
      if (beanType.isInstance(obj)) {
        list.add(obj);
      }
    }

    return list;
  }

  public Object getBean(String name) {
    return objPool.get(name);
  }

  public Map<String, Object> getBeansWithAnnotation(Class<?
  extends Annotation> annoType) {
    //전체 객체 목록
    Set<Entry<String, Object>> entrySet = objPool.entrySet();
    //map에선 반복문을 돌릴수가 없기때문에 Set에 entryset를 뽑아 저장

    //특정 애노테이션이 붙은 객체 목록
    HashMap<String, Object> objMap = new HashMap<>();

    Object obj = null;
    for (Entry<String, Object> entry : entrySet) {
      obj = entry.getValue();
      if (obj.getClass().getAnnotation(annoType) == null) {
        continue;
      }
      objMap.put(entry.getKey(), obj);

    }
    return objMap;
  }

  public Object getBean(Class<?> type) {
    Collection<Object> objects = objPool.values();
    for (Object obj : objects) {
      if (type.isInstance(obj)) {
        return obj;
      }
    }
    return null;
  }

  //외부에서 임의로 객체를 추가할 수 있게 한다.
  public void addBean(String name, Object bean) {
    objPool.put(name, bean);
    injectDependency();
  }
}
