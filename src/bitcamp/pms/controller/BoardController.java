package bitcamp.pms.controller;

import java.util.List;
import java.util.Scanner;

import bitcamp.pms.annotation.Controller;
import bitcamp.pms.annotation.RequestMapping;
import bitcamp.pms.dao.BoardDao;
import bitcamp.pms.domain.Board;
import bitcamp.pms.util.CommandUtil;

@Controller
@RequestMapping("board/") // 매핑정보 일부를 선언한다.
public class BoardController {  
  private BoardDao boardDao;  

  public void setBoardDao(BoardDao boardDao) {
    this.boardDao = boardDao;
  }

  @RequestMapping("add.do") // 매핑정보 나머지를 선언한다.
  public void add(Scanner keyScan) {    
    Board board = new Board();    
    System.out.print("제목? ");
    board.setTitle(keyScan.nextLine());
    System.out.print("내용? ");
    board.setContent(keyScan.nextLine());
    System.out.print("암호? ");
    board.setPassword(keyScan.nextLine());    
    if (CommandUtil.confirm(keyScan, "저장하시겠습니까?")) {         
      try {
        boardDao.insert(board);   
        System.out.println("저장하였습니다.");
        System.out.println("---------------------------------");      
      } catch (Exception e) {
        System.out.println("데이터 저장을 실패하였습니다.");
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
      System.out.print("삭제하고 싶은 게시물 번호를 입력하세요. ");
      int no = Integer.parseInt(keyScan.nextLine());
      if (CommandUtil.confirm(keyScan, "삭제 하시겠습니까?")) {
        int count = boardDao.delete(no);
        if (count > 0) {
          System.out.println("삭제하였습니다.");
          System.out.println("---------------------------------");
          this.list();
        } else {
          System.out.println("유효하지 않은 번호이거나 이미 삭제된 항목입니다.");
          System.out.println("---------------------------------");
        }
      } else {
        System.out.println("취소하였습니다.");
        System.out.println("---------------------------------");
      }
    } catch (Exception e) {
      System.out.println("데이터 처리중 오류 발생");
    }    
  } 
  
  @RequestMapping("list.do")
  public void list() {
    try {
      List<Board> boards = boardDao.selectList();          
      System.out.println("저장된 게시물 목록입니다.");
      for (Board board : boards) {
        System.out.printf("%d번 %s, %d, %s\n", board.getNo(),
            board.getTitle(), board.getViews(), board.getCreatedDate());        
      }
    } catch (Exception e) {
      throw new RuntimeException("게시판 데이터 로딩 실패!", e);
    }      
  } 
  
  @RequestMapping("update.do")
  public void update(Scanner keyScan) {    
    
    try { 
      this.list();
      System.out.print("변경하고 싶은 게시물 번호를 입력하세요. ");
      int no = Integer.parseInt(keyScan.nextLine());      
      Board board = boardDao.selectOne(no);      
      System.out.printf("제목(%s)? ", board.getTitle());
      board.setTitle(keyScan.nextLine());
      System.out.printf("내용(%s)? ", board.getContent());
      board.setContent(keyScan.nextLine());
      System.out.print("암호? ");
      board.setPassword(keyScan.nextLine());     
      
      if (CommandUtil.confirm(keyScan, "변경 하시겠습니까?")) {        
        int count = boardDao.update(board);
        if (count > 0) {
          System.out.println("변경하였습니다.");
          System.out.println("---------------------------------");
          this.list();
        } else {
          System.out.println("유효하지 않은 번호이거나 이미 삭제된 항목입니다.");
          System.out.println("---------------------------------");
        }
      } else {
        System.out.println("취소하였습니다.");
        System.out.println("---------------------------------");
      }
    } catch (Exception e) {      
      System.out.println("데이터 처리과정에 오류 발생");
      e.printStackTrace();
    }    
  }
}
