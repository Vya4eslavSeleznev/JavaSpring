package coursework;

import api.Gateway;

public class Main {
  public static void main(String[] args) {
    Gateway client = new Gateway();
    Authentication authentication = new Authentication(client);
  }
}
