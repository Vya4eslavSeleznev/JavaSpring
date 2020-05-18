package coursework;

import javax.swing.*;

public class Menu
{
  private JPanel rootPanel;
  private JTabbedPane mainTabbedPane;
  private JTabbedPane articleTabbedPane;
  private JTabbedPane balanceTabbedPane;
  private JTabbedPane operationTabbedPane;
  private JButton deleteArticleButton;
  private JTextField deleteArticleTextField;
  private JTable deleteArticleTable;
  private JTable showAllArticleTable;
  private JButton addArticleButton;
  private JTextField addArticleTextField;
  private JTable addArticleTable;
  private JButton filterArticleButton;
  private JTextField filterArticleTextField;
  private JTable table1;
  private JScrollPane filterArticleTable;
  private JButton addBalanceButton;
  private JTextField addBalanceDateTextField;
  private JTextField addBalanceDebitTextField;
  private JTextField addBalanceCreditTextField;
  private JTable addBalanceTable;
  private JButton deleteBalanceButton;
  private JTextField deleteBalanceTextField;
  private JTable deleteBalanceTable;
  private JTable showAllBalanceTable;
  private JButton filterBalanceButton;
  private JTextField filterBalanceFromTextField;
  private JTextField filterBalanceToTextField;
  private JTable filterBalanceTable;

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

  public JPanel getPanel()
  {
    return this.rootPanel;
  }
}
