package in.connection;

import java.util.ArrayList;
import java.util.List;

public class MyConnectionPool implements ConnectionPool {

  List<Connection> connections = new ArrayList<>();
  int index = 0;

  MyConnectionPool(List<Connection> connections) {
    if (connections != null && !connections.isEmpty()) {
      this.connections = connections;
    }
  }

  public synchronized Connection getConnection() {
    // implement me!
    if (index >= connections.size()) {
      throw new RuntimeException("We Just ran of Connections!");
    }
    return new MySQLConnection(connections.get(index++));
  }
}
