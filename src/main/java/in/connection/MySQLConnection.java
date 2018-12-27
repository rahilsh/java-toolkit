package in.connection;

public class MySQLConnection implements Connection {

  Connection con;

  MySQLConnection(Connection con) {
    this.con = con;
  }

  public void close() {
    // TODO: return connection back to connection pool
  }

  public Object execute(Object query) {
    // Do operations
    return con.execute(query);
  }
}
