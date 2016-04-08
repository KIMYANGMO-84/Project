package bitcamp.pms.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.domain.Board;


@Component
public class BoardDao {    
  SqlSessionFactory sqlSessionFactory;

  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {    
    this.sqlSessionFactory = sqlSessionFactory;
  }  

  public BoardDao() {}
  
  public List<Board> selectList() throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    
    try {
      return sqlSession.selectList("BoardDao.selectList");
    } finally {     
      sqlSession.close();
    }    
  }
  
  public Board selectOne(int no) throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    
    try {
      return sqlSession.selectOne("BoardDao.selectOne", no);
    } finally {     
      sqlSession.close();
    }    
  } 
  
  
  public int insert(Board board) throws Exception {    
   SqlSession sqlSession = sqlSessionFactory.openSession(true); //< true값을 넘기면 autocommit설정
    try {           
      return sqlSession.insert("BoardDao.insert", board);            
      
    } finally {      
      sqlSession.close();
    }
  }
  
  public int update(Board board) throws Exception {    
    SqlSession sqlSession = sqlSessionFactory.openSession(); //< true값을 넘기면 autocommit설정  
    try {      
      int count = sqlSession.update("BoardDao.update", board);
      sqlSession.commit();
      return count;
    } finally {            
      sqlSession.close();
    }  
  }
 
  public int delete(int no) throws Exception {    
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {      
      int count = sqlSession.delete("BoardDao.delete", no);
      sqlSession.commit();
      return count; 
    } finally {            
      sqlSession.close();
    }
  }
}
