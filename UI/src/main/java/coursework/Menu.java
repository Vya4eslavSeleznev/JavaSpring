package coursework;

import api.Gateway;
import api.TokenModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Menu {
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

  private TokenModel tokenModel;
  private JFrame loaderFrame;
  private Gateway gateway;

  public Menu(TokenModel tokenModel, JFrame loaderFrame, Gateway gateway) {
    this.tokenModel = tokenModel;
    this.loaderFrame = loaderFrame;
    this.gateway = gateway;

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

  public void menuImplementation() {
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

  private void addArticle() {
    DefaultTableModel modelForArticle = getDefaultDataModelForArticle();

    addArticleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        /*articleName = addArticleTextField.getText();
        articleId = 123;
        reloadArticleTable(addArticleTable, modelForArticle, articleId, articleName);*/

        String articleName = addArticleTextField.getText();
        Loader loader = new Loader(loaderFrame);

        try {
          gateway.addArticle(articleName, tokenModel).exceptionally(exception -> {
            loaderFrame.dispose();
            return null;
          }).thenAccept(model -> {
            if(model == null)
              return;

            loaderFrame.dispose();
            //загрузить таблицу в UI. Хреново работает loader
          });
        }
        catch(URISyntaxException ex) {
          ex.printStackTrace();
        }



      }
    });
  }

  private void deleteArticle() {
    DefaultTableModel modelForArticle = getDefaultDataModelForArticle();

    deleteArticleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });
  }

  private void showAllArticle() {

  }

  private void articleFilter() {
    articleFilterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });
  }

  private void addBalance() {
    addBalanceButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        errorAddBalanceLabel.setText("test");



        Date createDate = null;
        String date = addBalanceDateTextField.getText();

        try {
          createDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }
        catch(ParseException ex) {
          errorAddBalanceLabel.setText("Incorrect parameters");
        }
        catch(NullPointerException ex) {
          errorAddBalanceLabel.setText("Incorrect parameters");
        }

        String debitString = addBalanceDebitTextField.getText();
        String creditString = addBalanceCreditTextField.getText();
        double debit = 0;
        double credit = 0;

        try {
          debit = Double.parseDouble(debitString);
          credit = Double.parseDouble(creditString);
        }
        catch(NullPointerException ex) {
          errorAddBalanceLabel.setText("Incorrect parameters");
        }
        catch(NumberFormatException ex) {
          errorAddBalanceLabel.setText("Incorrect parameters");
        }

        Loader loader = new Loader(loaderFrame);


        try {
          gateway.addBalance(createDate, debit, credit, tokenModel).exceptionally(exception -> {
            loaderFrame.dispose();
            return null;
          }).thenAccept(model -> {
            if(model == null)
              return;

            loaderFrame.dispose();
            //загрузить таблицу в UI. Хреново работает loader
          });
        }
        catch(URISyntaxException ex) {
          errorAddBalanceLabel.setText("Incorrect parameters");
        }



      }
    });
  }

  private void deleteBalance() {
    deleteBalanceButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });
  }

  private void showAllBalance() {

  }

  private void balanceFilter() {
    balanceFilterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });
  }

  private void addOperation() {
    addOperationButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        Date createDate = null;
        String date = addOperationDateTextField.getText();

        try {
          createDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }
        catch(ParseException ex) {
          errorAddOperationLabel.setText("Incorrect parameters");
        }
        catch(NullPointerException ex) {
          errorAddOperationLabel.setText("Incorrect parameters");
        }

        String debitString = addOperationDebitTextField.getText();
        String creditString = addOperationCreditTextField.getText();
        double debit = 0;
        double credit = 0;
        int articleId = 0;
        int balanceId = 0;

        try {
          debit = Double.parseDouble(debitString);
          credit = Double.parseDouble(creditString);

          articleId = Integer.parseInt(addOperationArtIdTextField.getText());
          balanceId = Integer.parseInt(addOperationBalIdTextField.getText());
        }
        catch(NullPointerException ex) {
          errorAddOperationLabel.setText("Incorrect parameters");
        }
        catch(NumberFormatException ex) {
          errorAddOperationLabel.setText("Incorrect parameters");
        }

        Loader loader = new Loader(loaderFrame);

        try {
          gateway.addOperation(articleId, debit, credit, createDate, balanceId, tokenModel).exceptionally(exception -> {
            loaderFrame.dispose();
            return null;
          }).thenAccept(model -> {
            if(model == null)
              return;

            loaderFrame.dispose();
            //загрузить таблицу в UI. Хреново работает loader
          });
        }
        catch(URISyntaxException ex) {
          errorAddOperationLabel.setText("Incorrect parameters");
        }








      }
    });
  }

  private void deleteOperation() {
    deleteOperationButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });
  }

  private void showAllOperation() {

  }

  private void operationFilter() {
    operationFilterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

      }
    });
  }


  private DefaultTableModel getDefaultDataModelForArticle() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Id");
    model.addColumn("Name");

    return model;
  }

  private DefaultTableModel getDefaultDataModelForArticleFilter() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("ArticleId");
    model.addColumn("Debit");
    model.addColumn("Credit");
    model.addColumn("CreateDate");

    return model;
  }

  private DefaultTableModel getDefaultDataModelForBalance() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Id");
    model.addColumn("CreateDate");
    model.addColumn("Debit");
    model.addColumn("Credit");
    model.addColumn("Amount");

    return model;
  }

  private DefaultTableModel getDefaultDataModelForOperation() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Id");
    model.addColumn("ArticleId");
    model.addColumn("Debit");
    model.addColumn("Credit");
    model.addColumn("CreateDate");
    model.addColumn("BalanceId");

    return model;
  }

  private void reloadArticleTable(JTable table, DefaultTableModel model, int id, String name) {

    model.addRow(new Object[] {id, name});
    table.setModel(model);
  }

  private void selectTabArticle() {

  }

  private void selectTabBalance() {

  }

  private void selectTabOperation() {

  }

  public TokenModel getTokenModel() {
    return tokenModel;
  }
}
