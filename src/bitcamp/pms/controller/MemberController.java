package bitcamp.pms.controller;

import java.util.List;
import java.util.Scanner;

import bitcamp.pms.Test01;
import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.annotation.StartUp;
import bitcamp.pms.dao.MemberDao;
import bitcamp.pms.domain.Member;
import bitcamp.pms.util.CommandUtil;
import bitcamp.pms.util.PatternChecker;

@Controller
@RequestMapping("member/")
public class MemberController {
  
  private MemberDao memberDao;
 
  public void setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
  }

  @RequestMapping("add.do")
  public void add(Scanner keyScan) {    
    Member member = new Member();
    System.out.print("이름? ");
    member.setName(keyScan.nextLine());
    System.out.print("이메일? ");
    member.setEmail(keyScan.nextLine()); 
    System.out.print("암호? ");
    member.setPassword(keyScan.nextLine());
    System.out.print("전화? ");
    member.setTel(keyScan.nextLine());
    if (CommandUtil.confirm(keyScan, "저장하시겠습니까?")) {
      try {
        memberDao.insert(member);
        System.out.println("저장하였습니다.");
        System.out.println("-----------------------------------");      
      } catch (Exception e) {
        System.out.println("데이터 저장을 실패하였습니다.");
      }
    } else {
      System.out.println("저장을 취소하였습니다.");
      System.out.println("-----------------------------------");
    }
  } 
  
  @RequestMapping("delete.do")
  public void delete(Scanner keyScan) {    
    try {      
      this.list();
      System.out.print("삭제할 회원 번호는? ");
      int no = Integer.parseInt(keyScan.nextLine());

      if (CommandUtil.confirm(keyScan, "정말 삭제하시겠습니까?")) {
        int count =  memberDao.delete(no);
        if (count > 0) {
          System.out.println("삭제하였습니다.");
          System.out.println("-----------------------------------");
          this.list();
        } else {
          System.out.println("유효하지 않은 번호이거나, 이미 삭제된 항목입니다.");          
        }
      } else {
        System.out.println("취소하였습니다.");
        System.out.println("-----------------------------------");
      }
    } catch (Exception e) {
      System.out.println("데이터 처리에 실패했습니다.");
    }   
  }
  
  @RequestMapping("list.do")
  public void list() {   
    System.out.println("회원 리스트를 로딩합니다.");
    try {
      List<Member> members = memberDao.selectList();      
      for (Member member : members) {        
        System.out.printf("%d, %s, %s, %s\n", member.getNo(), member.getName(), member.getEmail(), member.getTel());
      }
    } catch (Exception e) {
      throw new RuntimeException("회원 데이터 로딩 실패!", e);
    }    
  }
  
  public Member search(String email) {
    try {
      List<Member> members = memberDao.selectList();    
      for (Member member : members) {        
        if (email.equals(member.getEmail())) {
          return member;
        } 
      }
      return null;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("회원 데이터 로딩 실패!", e);
    }        
  }
  
  @RequestMapping("unsubscribe.do")
  public void doUnsubscribe(Scanner keyScan) {
    try { 
      Member member;
      int no;
      while (true) {
        System.out.println("탈퇴 할 계정 정보를 확인합니다");
        System.out.print("email : ");
        String input = keyScan.nextLine();
        if (search(input) != null) {
          member = search(input);
          no = member.getNo();
          break;
        }
      }  
      if (CommandUtil.confirm(keyScan, "정말 탈퇴하시겠습니까?")) {
        int count =  memberDao.delete(no);
        if (count > 0) {
          System.out.println("회원 정보를 삭제하였습니다.");
          System.out.println("-----------------------------------");
          Test01.state = 0;
        } else {
          System.out.println("취소하였습니다.");
          System.out.println("-----------------------------------");
          Test01.state = 1;
        } 
      }      
    } catch (Exception e) {
        System.out.println("데이터 처리에 실패했습니다.");
    }    
  }
  
  @StartUp("2")
  public void doSignIn(Scanner keyScan) {
    Member member = new Member();
    String check;
    System.out.print("이름? ");
    member.setName(keyScan.nextLine());
    while (true) {
      System.out.print("이메일? ");    
      check = keyScan.nextLine();
      if (PatternChecker.isEmail(check)) {
        member.setEmail(check);
        break;
      } else {
        System.out.println("이메일 형식이 맞지 않습니다.");
        System.out.println("이메일은 xxx@xxx.com 형식입니다.");
      }
    }
    while (true) {
      System.out.print("암호? ");    
      check = keyScan.nextLine();
      if (PatternChecker.isPassword(check)) {
        member.setPassword(check);
        break;
      } else {
        System.out.println("암호 형식이 맞지 않습니다. 암호는 4~10자로 이루어지며");
        System.out.println("숫자1개이상 특수문자(?,!,@)를 1개이상 포함해야 합니다");
      }
    }
    while (true) {
      System.out.print("전화? ");    
      check = keyScan.nextLine();
      if (PatternChecker.isTel(check)) {
        member.setTel(check);
        break;
      } else if (PatternChecker.isCellPhone(check)) {
        member.setTel(check);
        break;
      } else {
        System.out.println("전화 형식이 맞지 않습니다.");
        System.out.println("xxx-xxxx-xxxx or xxxx-xxxx 형식으로 입력하세요");
      }
    }      
    try {
      memberDao.insert(member);
      System.out.printf("%s님 회원 가입을 환영합니다.\n", member.getName());
      System.out.println("-----------------------------------");      
    } catch (Exception e) {
      System.out.println("회원 가입을 실패하였습니다.");
    }    
  }
  
  @StartUp("1")
  public void doLogin(Scanner keyScan) {    
    Member member;
    while (true) {
      System.out.print("Email : ");
      String input = keyScan.nextLine();
      if (search(input) != null) {
        member = search(input);
        while (true) {
          System.out.print("Password : ");
          input = keyScan.nextLine();
          if (input.equals(member.getPassword())) {
            break;
          } else {
            System.out.println("암호가 맞지 않습니다.");
          }
        }
        break;
      } else {
        System.out.println("등록되지 않은 사용자입니다.");
      }
    } 
    System.out.printf("환영합니다 %s님!\n", member.getName());
    Test01.state = 1;
  }
  
  @RequestMapping("update.do")
  public void update(Scanner keyScan) {    
    try {
      this.list();
      System.out.print("변경할 회원 번호는? ");
      int no = Integer.parseInt(keyScan.nextLine());      
      Member member = memberDao.selectOne(no);      
      System.out.printf("이름(%s)? ", member.getName());
      member.setName(keyScan.nextLine());
      System.out.printf("이메일(%s)? ", member.getEmail());
      member.setEmail(keyScan.nextLine());
      System.out.printf("암호(%s)? ", member.getPassword());
      member.setPassword(keyScan.nextLine());
      System.out.printf("전화(%s)? ", member.getTel());
      member.setTel(keyScan.nextLine());
      if (CommandUtil.confirm(keyScan, "변경하시겠습니까?")) {        
        int count =  memberDao.update(member);
        if (count > 0) {
          System.out.println("변경 하였습니다.");
          System.out.println("-----------------------------------");
          this.list();
        } else {
        System.out.println("유효하지 않은 번호이거나, 이미 삭제된 항목입니다.");
        }
      } else {
        System.out.println("취소하였습니다.");
        System.out.println("-----------------------------------");
      }
    } catch (Exception e) {
      System.out.println("데이터 로딩 또는 저장중 오류가 발생했습니다.");
    }
  }
}
