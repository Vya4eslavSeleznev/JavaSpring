package coursework;

import javax.swing.*;

public class Menu
{
  public Menu()
  {
    JFrame frame = new JFrame();
    frame.add(rootPanel);
    frame.setTitle("Menu");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(1000, 700);
    frame.setLocation(290, 50);
    frame.setVisible(true);
  }

  private JPanel rootPanel;
  private JButton articleButton;
  private JButton balanceButton;
  private JButton operationButton;
  private JButton backButton;
}
