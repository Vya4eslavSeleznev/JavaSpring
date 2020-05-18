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
  private JButton addArticleButton;
  private JButton articleFilterButton;
  private JButton addBalanceButton;
  private JButton deleteBalanceButton;
  private JButton balanceFilterButton;
  private JButton addOperationButton;
  private JButton deleteOperationButton;
  private JButton operationFilterButton;

  private JTextField deleteArticleTextField;
  private JTextField addArticleTextField;
  private JTextField filterArticleTextField;
  private JTextField addBalanceDateTextField;
  private JTextField addBalanceDebitTextField;
  private JTextField addBalanceCreditTextField;
  private JTextField deleteBalanceTextField;
  private JTextField filterBalanceFromTextField;
  private JTextField filterBalanceToTextField;
  private JTextField addOperationDateTextField;
  private JTextField addOperationArtIdTextField;
  private JTextField addOperationBalIdTextField;
  private JTextField addOperationDebitTextField;
  private JTextField addOperationCreditTextField;
  private JTextField deleteOperationTextField;
  private JTextField operationFilterDateFromTextField;
  private JTextField operationFilterDateToTextField;

  private JTable deleteArticleTable;
  private JTable showAllArticleTable;
  private JTable addArticleTable;
  private JTable filterArticleTable;
  private JTable addBalanceTable;
  private JTable deleteBalanceTable;
  private JTable showAllBalanceTable;
  private JTable filterBalanceTable;
  private JTable addOperationTable;
  private JTable deleteOperationTable;
  private JTable showAllOperationTable;
  private JTable operationFilterTable;

  private JLabel errorArticleFilterLabel;
  private JLabel errorDeleteArticleLabel;
  private JLabel errorAddBalanceLabel;
  private JLabel errorDeleteBalanceLabel;
  private JLabel errorBalanceFilterLabel;
  private JLabel errorAddOperationLabel;
  private JLabel errorOperationFilterLabel;

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
