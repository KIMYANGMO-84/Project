package bitcamp.pms;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import bitcamp.pms.context.ApplicationContext;
import bitcamp.pms.context.request.RequestHandler;
import bitcamp.pms.context.request.RequestHandlerMapping;

public class Test01 {
  static ApplicationContext appContext;
  static RequestHandlerMapping requestHandlerMapping;
  static Scanner keyScan = new Scanner(System.in);  
  static int state = 0;
  public static void main(String[] args) throws Exception {    
    appContext = new ApplicationContext("bitcamp.pms");
    requestHandlerMapping = new RequestHandlerMapping(appContext);
    
    //명령을 처리하는 메서드에서 keyScan을 사용할 수 있도록 ApplicationContext에 추가한다.    
    appContext.addBean("stdinScan", keyScan); // bean
    
    //mybatis SqlSessionFactory 객체 준비
    
    try {
      InputStream inputStream = 
          Resources.getResourceAsStream("conf/mybatis-config.xml");
      appContext.addBean("sqlSessionFactory", 
          new SqlSessionFactoryBuilder().build(inputStream));
      
    } catch (Exception e) {
      System.out.println("DB커넥션 오류입니다.\n시스템을 종료하겠습니다.");
      e.printStackTrace();
      return;
    }    
    
    String input;
    
    
    do {
      input = prompt(state);
      processCommand(input);
    } while (!input.equals("quit"));
    keyScan.close();
  }

  static void processCommand(String input) {    
    if (input.equals("quit")) {
      doQuit();
    } else if (input.equals("about")) {
      doAbout();
    } else {
      if (input.equals("1")) {
        input = "member/login.do";
        state = 1;
      } else if (input.equals("2")) {
        input = "member/signIn.do";
      } else if (input.equals("out")) {
        input = "Unsubscribe.do";
      }
      RequestHandler requestHandler 
      = (RequestHandler)requestHandlerMapping.getRequestHandler(input);
      if (requestHandler == null) {
        doError();
        return;
      }  
      Method method = requestHandler.getMethod();
      Object obj = requestHandler.getObj();
      try {
        //파라미터의 값을 담을 List를 준비한다.
        ArrayList<Object> args = new ArrayList<>();
        //메서드의 파라미터 정보를 알아낸다.
        Parameter[] params = method.getParameters();
        Object arg = null;
        
        for (Parameter param : params) {
          //파라미터에 해당하는 객체가 ApplicationContext에 있는지 알아본다.
          arg = appContext.getBean(param.getType());
          //찾은 값을 아규먼트 목록에 담는다. 못찾았으면 null을 담는다.
          args.add(arg);
        }          
        //준비한 값을 가지고 메서드를 호출한다.          
        method.invoke(obj, args.toArray());          
      } catch (Exception e) {
        System.out.println("명령 처리중에 오류가 발생했습니다.");
      }     
    }
  }

  static String prompt(int state) {
    if (state == 1) {
      System.out.print("명령> ");
      return keyScan.nextLine().toLowerCase();
    } else {
      System.out.printf("%s\n%s\n", "1)로그인", "2)회원가입");
      System.out.println("원하는 메뉴를 선택하세요.");
      System.out.print("선택> ");
      return keyScan.nextLine().toLowerCase();
    }
  }

  static void doQuit() { 
    System.out.println("안녕히 가세요!");
  }

  static void doError() {
    System.out.println("올바르지 않은 명령어입니다.");
  }

  static void doAbout() {
    System.out.println("비트캠프 80기 프로젝트 관리 시스템!");
  }
/*  
  static void start(String input) {
    System.out.printf("%s\n%s\n", "1)로그인", "2)회원가입");
    System.out.println("원하는 메뉴를 선택하세요.");
    if (input.equals("1")) {
      input = "member/login.do";
      
    } else if (input.equals("2")) {
      input = "member/signIn.do";
    }
    RequestHandler requestHandler 
    = (RequestHandler)requestHandlerMapping.getRequestHandler(input);
    if (requestHandler == null) {
      doError();
      return;
    }  
    Method method = requestHandler.getMethod();
    Object obj = requestHandler.getObj();
    try {      
      ArrayList<Object> args = new ArrayList<>();      
      Parameter[] params = method.getParameters();
      Object arg = null;      
      for (Parameter param : params) {        
        arg = appContext.getBean(param.getType());        
        args.add(arg);
      }          
                
      method.invoke(obj, args.toArray());          
    } catch (Exception e) {
      System.out.println("명령 처리중에 오류가 발생했습니다.");
    }     
  
  }
*/
}
