package bitcamp.pms.dao;

import java.util.List;

import bitcamp.pms.domain.Board;

public interface BoardDao {    
 List<Board> selectList() throws Exception;  
  Board selectOne(int no) throws Exception;
  int insert(Board board) throws Exception;
  int update(Board board) throws Exception;
  int delete(int no) throws Exception;
}
