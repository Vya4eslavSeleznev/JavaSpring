package coursework;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
  private JLabel errorDeleteOperationLabel;

  private String articleName;
  private int articleId;

  //private int

  public Menu()
  {
    JFrame frame = new JFrame();
    frame.add(rootPanel);
    frame.setTitle("Home budget");
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

    DefaultTableModel modelForOperation = getDefaultDataModelForOperation();
    addOperationTable.setModel(modelForOperation);
    deleteOperationTable.setModel(modelForOperation);
    showAllOperationTable.setModel(modelForOperation);
    operationFilterTable.setModel(modelForOperation);

    //необходимо загрузить все данные в таблицы

  }

  public void menuImplementation()
  {
    /*System.out.println(mainTabbedPane.getTitleAt(0));
    System.out.println(mainTabbedPane.getTitleAt(1));
    System.out.println(mainTabbedPane.getTitleAt(2));
    System.out.println("================================");
    System.out.println(articleTabbedPane.getTitleAt(0));
    System.out.println(articleTabbedPane.getTitleAt(1));
    System.out.println(articleTabbedPane.getTitleAt(2));
    System.out.println(articleTabbedPane.getTitleAt(3));
    System.out.println("================================");
    System.out.println(balanceTabbedPane.getTitleAt(0));
    System.out.println(balanceTabbedPane.getTitleAt(1));
    System.out.println(balanceTabbedPane.getTitleAt(2));
    System.out.println(balanceTabbedPane.getTitleAt(3));
    System.out.println("================================");
    System.out.println(operationTabbedPane.getTitleAt(0));
    System.out.println(operationTabbedPane.getTitleAt(1));
    System.out.println(operationTabbedPane.getTitleAt(2));
    System.out.println(operationTabbedPane.getTitleAt(3));*/



    addArticle();
    deleteArticle();
    articleFilter();

    addBalance();
    deleteBalance();
    balanceFilter();

    addOperation();
    deleteOperation();
    operationFilter();
  }

  private void addArticle()
  {
    DefaultTableModel modelForArticle = getDefaultDataModelForArticle();

    addArticleButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        articleName = addArticleTextField.getText();
        articleId = 123;
        reloadArticleTable(addArticleTable, modelForArticle, articleId, articleName);
      }
    });
  }

  private void deleteArticle()
  {
    DefaultTableModel modelForArticle = getDefaultDataModelForArticle();

    deleteArticleButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {

      }
    });
  }

  private void showAllArticle()
  {

  }

  private void articleFilter()
  {
    articleFilterButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {

      }
    });
  }

  private void addBalance()
  {
    addBalanceButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {

      }
    });
  }

  private void deleteBalance()
  {
    deleteBalanceButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {

      }
    });
  }

  private void showAllBalance()
  {

  }

  private void balanceFilter()
  {
    balanceFilterButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {

      }
    });
  }

  private void addOperation()
  {
    addOperationButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {

      }
    });
  }

  private void deleteOperation()
  {
    deleteOperationButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {

      }
    });
  }

  private void showAllOperation()
  {

  }

  private void operationFilter()
  {
    operationFilterButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {

      }
    });
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

  private void reloadArticleTable(JTable table, DefaultTableModel model, int id, String name)
  {

    model.addRow(new Object[] {id, name});
    table.setModel(model);
  }

  private void selectTabArticle()
  {

  }

  private void selectTabBalance()
  {

  }

  private void selectTabOperation()
  {

  }
}
