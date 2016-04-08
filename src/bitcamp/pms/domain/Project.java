package bitcamp.pms.domain;

import java.sql.Date;

public class Project {
  protected int    no;
  protected String title;
  protected String description;
  protected int    state;
  protected Date   startDate;
  protected Date   endDate;

  public Project() {}

  public String getTitle() {
    return this.title;
  }
  public void setTitle(String title) {
    this.title = title;
  }
  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getDescription() {
    return this.description;
  }
  public void setDescription(String description) {
    this.description = description;
  }
  public Date getStartDate() {
    return this.startDate;
  }
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  public Date getEndDate() {
    return this.endDate;
  }
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  public int getState() {
    return this.state;
  }
  public void setState(int state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "Project [no=" + no + ", title=" + title + ", description=" + description + ", state=" + state
        + ", startDate=" + startDate + ", endDate=" + endDate + "]";
  }

}
