package coursework;

import api.CustomHttpClient;

import javax.swing.*;

public class Main
{
  public static void main(String[] args)
  {
    CustomHttpClient client = new CustomHttpClient();
    Authentication authentication = new Authentication(client);
  }
}
