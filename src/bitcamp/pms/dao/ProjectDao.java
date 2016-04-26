package bitcamp.pms.dao;


import java.util.List;

import bitcamp.pms.domain.Project;

public interface ProjectDao {  
  List<Project> selectList();
  Project selectOne(int no);
  int insert(Project project);
  int update(Project project);
  int delete(int no);
}

