package bitcamp.pms.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import bitcamp.pms.domain.Task;

public interface TaskDao {  
  List<Task> selectList();
  Task selectOne(int no);
  int insert(Task task);
  int update(Task task);
  int delete(int no);
}



