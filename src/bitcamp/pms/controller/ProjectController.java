package bitcamp.pms.controller;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.ProjectDao;
import bitcamp.pms.domain.Project;
import bitcamp.pms.util.CommandUtil;

@Controller
@RequestMapping("project/")
public class ProjectController {
  @Autowired
  private ProjectDao projectDao;
 
  @RequestMapping("add.do")
  public void add(Scanner keyScan) {       
    
    Project project = new Project();    
    System.out.print("프로젝트명? ");
    project.setTitle(keyScan.nextLine());
    System.out.print("시작일? ");
    project.setStartDate(Date.valueOf(keyScan.nextLine()));
    System.out.print("종료일? ");
    project.setEndDate(Date.valueOf(keyScan.nextLine()));
    System.out.print("설명? ");
    project.setDescription(keyScan.nextLine());
    if (CommandUtil.confirm(keyScan, "저장하시겠습니까?")) {
      try {
        projectDao.insert(project);
        System.out.println("저장하였습니다.");
        System.out.println("---------------------------------");
      } catch (Exception e) {
        System.out.println("데이터 처리중 오류 발생");
        e.printStackTrace();
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
      System.out.print("삭제하고 싶은 프로젝트 번호를 입력하세요. ");
      int no = Integer.parseInt(keyScan.nextLine());
      if (CommandUtil.confirm(keyScan, "삭제 하시겠습니까?")) {
        int count = projectDao.delete(no);
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
  
  @RequestMapping("list.do")
  public void list() {
    System.out.println("저장된 프로젝트 목록입니다.");
    try {
      List<Project> projects = projectDao.selectList();
      for (Project project : projects) {
        System.out.printf("%d번 %s, %s, %s\n", project.getNo(), project.getTitle(), project.getStartDate(), project.getEndDate());
      }  
    } catch (Exception e) {
      System.out.println("데이터 로딩중 오류 발생");
      e.printStackTrace();
    }
  }
  
  @RequestMapping("update.do")
  public void update(Scanner keyScan) {   
    try {
      this.list();
      System.out.print("변경하고 싶은 프로젝트 번호를 입력하세요. ");
      int no = Integer.parseInt(keyScan.nextLine());      
      Project project = projectDao.selectOne(no);      
      System.out.printf("프로젝트명(%s)? ", project.getTitle());
      project.setTitle(keyScan.nextLine());
      System.out.printf("시작일(%s)? ", project.getStartDate());
      project.setStartDate(Date.valueOf(keyScan.nextLine()));
      System.out.printf("종료일(%s)? ", project.getEndDate());
      project.setEndDate(Date.valueOf(keyScan.nextLine()));
      System.out.printf("설명(%s)? ", project.getDescription()); 
      project.setDescription(keyScan.nextLine());
      if (CommandUtil.confirm(keyScan, "변경 하시겠습니까?")) {
        int count = projectDao.update(project);
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
