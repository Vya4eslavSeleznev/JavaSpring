package coursework;

import javax.swing.*;

public class Authentication
{
  public Authentication()
  {
    JFrame frame = new JFrame();
    frame.add(rootPanel);
    frame.setTitle("Home budget");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(1000, 700);
    frame.setLocation(290, 50);
    frame.setVisible(true);
  }

  private JPanel rootPanel;
  private JPasswordField passwordField;
  private JTextField textField;
  private JButton signInButton;
}
