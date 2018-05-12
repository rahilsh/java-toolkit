package in.jasper;

public class AuditLog {
  String name;
  String owner;
  String date;

  public AuditLog(String name, String owner, String date) {
    this.name = name;
    this.owner = owner;
    this.date = date;
  }
}
