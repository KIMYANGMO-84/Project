package bitcamp.pms.controller;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.TaskDao;
import bitcamp.pms.domain.Task;
import bitcamp.pms.util.CommandUtil;

@Controller
@RequestMapping("task/")
public class TaskController {
 
  private TaskDao taskDao;
 
  public void setTaskDao(TaskDao taskDao) {
    this.taskDao = taskDao;
  }
  @RequestMapping("add.do")
  public void add(Scanner keyScan) {       
    
    Task task = new Task();
    System.out.print("이메일? ");
    task.setWorkerEmail(keyScan.nextLine());
    System.out.print("태스크명? ");
    task.setTitle(keyScan.nextLine());
    System.out.print("내용? ");
    task.setContent(keyScan.nextLine());
    System.out.print("시작일? ");
    task.setStartDate(Date.valueOf(keyScan.nextLine()));
    System.out.print("종료일? ");
    task.setEndDate(Date.valueOf(keyScan.nextLine()));
    if (CommandUtil.confirm(keyScan, "저장하시겠습니까?")) {
      try {
        taskDao.insert(task);
        System.out.println("저장하였습니다.");
        System.out.println("---------------------------------");
      } catch (Exception e) {
        System.out.println("데이터 처리중 오류 발생");
      }
    } else {
      System.out.println("취소하였습니다.");
      System.out.println("---------------------------------");
    }
  }
  
  @RequestMapping("delete.do")
  public void delete(Scanner keyScan) {    
    try {    
      this.list();
      System.out.print("삭제하고 싶은 태스크 번호를 입력하세요. ");
      int no = Integer.parseInt(keyScan.nextLine());
      if (CommandUtil.confirm(keyScan, "삭제 하시겠습니까?")) {
        int count = taskDao.delete(no);
          if (count > 0) {
            System.out.println("저장하였습니다.");
            System.out.println("---------------------------------");
            this.list();
          } else {
            System.out.println("유효하지 않은 번호이거나 이미 삭제된 번호입니다.");
            System.out.println("---------------------------------");
          }
      } else {
        System.out.println("취소되었습니다.");
        System.out.println("---------------------------------");
      }     
    } catch (IndexOutOfBoundsException e) {
      System.out.println("유효하지 않은 인덱스 입니다.");
    } catch (Exception e) {
      System.out.println("에러 발생");
    }
  }
  
  @RequestMapping("list.do")
  public void list() {
    System.out.println("저장된 태스크 목록입니다.");
    try {
      List<Task> tasks = taskDao.selectList();
      for (Task task : tasks) {
        System.out.printf("%d번 %s, %s, %s, %s\n", task.getNo(), 
              task.getWorkerEmail(), task.getTitle(), 
              task.getStartDate(), task.getEndDate());
      }  
    } catch (Exception e) {
      System.out.println("데이터 로딩중 오류 발생");
    }
  }
  
  @RequestMapping("update.do")
  public void update(Scanner keyScan) {   
    try {
      this.list();
      System.out.print("변경하고 싶은 태스크 번호를 입력하세요. ");
      int no = Integer.parseInt(keyScan.nextLine());      
      Task task = taskDao.selectOne(no);      
      System.out.printf("이메일(%s)? ", task.getWorkerEmail());
      task.setWorkerEmail(keyScan.nextLine());
      System.out.printf("태스크명(%s)? ", task.getTitle());
      task.setTitle(keyScan.nextLine());
      System.out.printf("내용(%s)? ", task.getContent());
      task.setContent(keyScan.nextLine());
      System.out.printf("시작일(%s)? ", task.getStartDate());
      task.setStartDate(Date.valueOf(keyScan.nextLine()));
      System.out.printf("종료일(%s)? ", task.getEndDate());
      task.setEndDate(Date.valueOf(keyScan.nextLine()));
      if (CommandUtil.confirm(keyScan, "변경 하시겠습니까?")) {
        int count = taskDao.update(task);
          if (count > 0) {
            System.out.println("저장하였습니다.");
            System.out.println("---------------------------------");
            this.list();
          } else {
            System.out.println("유효하지 않은 번호이거나 이미 삭제된 번호입니다.");
            System.out.println("---------------------------------");
          }
      } else {
        System.out.println("취소되었습니다.");
        System.out.println("---------------------------------");
      }
    } catch (Exception e) {
        System.out.println("에러 발생");
    }
  }  
}
