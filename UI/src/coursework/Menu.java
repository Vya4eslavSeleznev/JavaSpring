package coursework;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Menu
{
  private JPanel rootPanel;

  private JTabbedPane mainTabbedPane;
  private JTabbedPane articleTabbedPane;
  private JTabbedPane balanceTabbedPane;
  private JTabbedPane operationTabbedPane;

  private JButton addArticleButton;
  private JButton deleteArticleButton;
  private JButton articleFilterButton;

  private JButton addBalanceButton;
  private JButton deleteBalanceButton;
  private JButton balanceFilterButton;

  private JButton addOperationButton;
  private JButton deleteOperationButton;
  private JButton operationFilterButton;

  private JTextField addArticleTextField;
  private JTextField deleteArticleTextField;
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

  private JTable addArticleTable;
  private JTable deleteArticleTable;
  private JTable showAllArticleTable;
  private JTable filterArticleTable;

  private JTable addBalanceTable;
  private JTable deleteBalanceTable;
  private JTable showAllBalanceTable;
  private JTable filterBalanceTable;

  private JTable addOperationTable;
  private JTable deleteOperationTable;
  private JTable showAllOperationTable;
  private JTable operationFilterTable;

  private JLabel errorDeleteArticleLabel;
  private JLabel errorArticleFilterLabel;

  private JLabel errorAddBalanceLabel;
  private JLabel errorDeleteBalanceLabel;
  private JLabel errorBalanceFilterLabel;

  private JLabel errorAddOperationLabel;
  private JLabel errorOperationFilterLabel;
  private JLabel errorDeleteoperationLabel;

  public Menu()
  {
    JFrame frame = new JFrame();
    frame.add(rootPanel);
    frame.setTitle("Menu");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(1000, 700);
    frame.setLocation(290, 50);
    frame.setVisible(true);

    DefaultTableModel modelForArticle = getDefaultDataModelForArticle();
    DefaultTableModel modelForArticleFilter = getDefaultDataModelForArticleFilter();
    addArticleTable.setModel(modelForArticle);
    deleteArticleTable.setModel(modelForArticle);
    showAllArticleTable.setModel(modelForArticle);
    filterArticleTable.setModel(modelForArticleFilter);

    DefaultTableModel modelForBalance = getDefaultDataModelForBalance();
    addBalanceTable.setModel(modelForBalance);
    deleteBalanceTable.setModel(modelForBalance);
    showAllBalanceTable.setModel(modelForBalance);
    filterBalanceTable.setModel(modelForBalance);

    DefaultTableModel modelForOperation = getDefaultDataModelForBalance();
    addOperationTable.setModel(modelForOperation);
    deleteOperationTable.setModel(modelForOperation);
    showAllOperationTable.setModel(modelForOperation);
    operationFilterTable.setModel(modelForOperation);
  }

  private DefaultTableModel getDefaultDataModelForArticle()
  {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Id");
    model.addColumn("Name");

    return model;
  }

  private DefaultTableModel getDefaultDataModelForArticleFilter()
  {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ArticleId");
    model.addColumn("Debit");
    model.addColumn("Credit");
    model.addColumn("CreateDate");

    return model;
  }

  private DefaultTableModel getDefaultDataModelForBalance()
  {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Id");
    model.addColumn("CreateDate");
    model.addColumn("Debit");
    model.addColumn("Credit");
    model.addColumn("Amount");

    return model;
  }

  private DefaultTableModel getDefaultDataModelForOperation()
  {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Id");
    model.addColumn("ArticleId");
    model.addColumn("Debit");
    model.addColumn("Credit");
    model.addColumn("CreateDate");
    model.addColumn("BalanceId");

    return model;
  }
}
