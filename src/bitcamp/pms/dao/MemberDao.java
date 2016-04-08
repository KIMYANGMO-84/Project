package bitcamp.pms.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import bitcamp.pms.annotation.Component;
import bitcamp.pms.domain.Member;

@Component
public class MemberDao {    
  SqlSessionFactory sqlSessionFactory;

  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {    
    this.sqlSessionFactory = sqlSessionFactory;
  }  

  public MemberDao() {}
  
  public List<Member> selectList() throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    
    try {
      return sqlSession.selectList("MemberDao.selectList");
    } finally {     
      sqlSession.close();
    }    
  }
  
  public Member selectOne(int no) throws Exception {
    SqlSession sqlSession = sqlSessionFactory.openSession();
    
    try {
      return sqlSession.selectOne("MemberDao.selectOne", no);
    } finally {     
      sqlSession.close();
    }    
  } 
  
  
  public int insert(Member member) throws Exception {    
   SqlSession sqlSession = sqlSessionFactory.openSession(true); //< true값을 넘기면 autocommit설정
    try {           
      return sqlSession.insert("MemberDao.insert", member);            
      
    } finally {      
      sqlSession.close();
    }
  }
  
  public int update(Member member) throws Exception {    
    SqlSession sqlSession = sqlSessionFactory.openSession(); //< true값을 넘기면 autocommit설정  
    try {      
      int count = sqlSession.update("MemberDao.update", member);
      sqlSession.commit();
      return count;
    } finally {            
      sqlSession.close();
    }  
  }
 
  public int delete(int no) throws Exception {    
    SqlSession sqlSession = sqlSessionFactory.openSession();
    try {      
      int count = sqlSession.delete("MemberDao.delete", no);
      sqlSession.commit();
      return count; 
    } finally {            
      sqlSession.close();
    }
  }
}
