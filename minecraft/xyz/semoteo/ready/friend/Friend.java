package xyz.semoteo.ready.friend;


public class Friend {
  private String alias;
  
  private String name;
  
  public Friend(String name, String alias) {
    this.name = name;
    this.alias = alias;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getAlias() {
    return this.alias;
  }
}
