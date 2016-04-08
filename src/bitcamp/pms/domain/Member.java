package bitcamp.pms.domain;

public class Member {
  protected int no;
  protected String name;
  protected String email;
  protected String password;
  protected String tel;

  public Member() {}

  public Member(int no,String name, String email, String password, String tel) {
    this.no = no;
    this.name = name;
    this.email = email;
    this.password = password;
    this.tel = tel;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getName() {
    return this.name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getEmail() {
    return this.email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPassword() {
    return this.password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getTel() {
    return this.tel;
  }
  public void setTel(String tel) {
    this.tel = tel;
  }

  @Override
  public String toString() {
    return this.no + "," + this.name + "," + this.email + "," + this.password + "," + this.tel;
  }


}
