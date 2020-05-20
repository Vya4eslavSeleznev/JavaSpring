package coursework;

import api.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
    DefaultTableModel model = getDefaultDataModelForOperation();
    addArticleTable.setModel(modelForArticle);
    deleteArticleTable.setModel(modelForArticle);
    showAllArticleTable.setModel(modelForArticle);
    filterArticleTable.setModel(model);

    DefaultTableModel modelForBalance = getDefaultDataModelForBalance();
    addBalanceTable.setModel(modelForBalance);
    deleteBalanceTable.setModel(modelForBalance);
    showAllBalanceTable.setModel(modelForBalance);
    filterBalanceTable.setModel(modelForBalance);

    addOperationTable.setModel(model);
    deleteOperationTable.setModel(model);
    showAllOperationTable.setModel(model);
    operationFilterTable.setModel(model);
  }

  public void menuImplementation() {
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
    addArticleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        /*articleName = addArticleTextField.getText();
        articleId = 123;
        reloadArticleTable(addArticleTable, modelForArticle, articleId, articleName);*/

        String articleName = addArticleTextField.getText();
        Loader loader = new Loader(loaderFrame);

        try {
          gateway.addArticle(articleName, tokenModel.getToken()).exceptionally(exception -> {
            loaderFrame.dispose();
            return null;
          }).thenAccept(model -> {
            loaderFrame.dispose();
          });
        }
        catch(URISyntaxException ex) {
          ex.printStackTrace();
        }

        addArticleTextField.setText("");
      }
    });
  }

  private void deleteArticle() {
    deleteArticleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorDeleteArticleLabel.setText("");
        String id = deleteArticleTextField.getText();
        Loader loader = new Loader(loaderFrame);

        try {
          gateway.delete("http://localhost:8080/article/", id, tokenModel).exceptionally(exception -> {
            loaderFrame.dispose();
            return null;
          }).thenAccept(model -> {
            loaderFrame.dispose();
            //загрузить таблицу в UI. Хреново работает loader
          });
        }
        catch(URISyntaxException ex) {
          //ex.printStackTrace();
          errorDeleteArticleLabel.setText("Incorrect parameters");
        }

        deleteArticleTextField.setText("");
      }
    });
  }

  private void showAllArticle() {
    try {
      gateway.getArticle(tokenModel).thenApply(listArticle -> {
        reloadTableArticle(getDefaultDataModelForArticle(), listArticle);

        return listArticle;
      });
    }
    catch(URISyntaxException ex) {
      ex.printStackTrace();
    }
  }

  private void articleFilter() {
    articleFilterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorArticleFilterLabel.setText("");

        try {
          gateway.getArticleFilter(filterArticleTextField.getText(), tokenModel).thenApply(listArticle -> {
            reloadTableOperation(filterArticleTable, getDefaultDataModelForOperation(), listArticle);

            return listArticle;
          });
        }
        catch(URISyntaxException ex) {
          errorArticleFilterLabel.setText("Incorrect parameters");
        }

        filterArticleTextField.setText("");
      }
    });
  }

  private void addBalance() {
    addBalanceButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorAddBalanceLabel.setText("");
        Date createDate = null;

        try {
          createDate = new SimpleDateFormat("yyyy-MM-dd").parse(addBalanceDateTextField.getText());
        }
        catch(ParseException ex) {
          errorAddBalanceLabel.setText("Incorrect parameters");
        }

        double debit = 0;
        double credit = 0;

        try {
          debit = Double.parseDouble(addBalanceDebitTextField.getText());
          credit = Double.parseDouble(addBalanceCreditTextField.getText());
        }
        catch(NumberFormatException ex) {
          errorAddBalanceLabel.setText("Incorrect parameters");
        }

        Loader loader = new Loader(loaderFrame);

        try {
          gateway.addBalance(createDate, debit, credit, tokenModel.getToken()).exceptionally(exception -> {
            loaderFrame.dispose();
            System.out.println(exception.toString());
            return null;
          }).thenAccept(model -> {
            loaderFrame.dispose();
          });
        }
        catch(URISyntaxException ex) {
          errorAddBalanceLabel.setText("Incorrect parameters");
        }

        addBalanceDateTextField.setText("");
        addBalanceDebitTextField.setText("");
        addBalanceCreditTextField.setText("");
      }
    });
  }

  private void deleteBalance() {
    deleteBalanceButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorDeleteBalanceLabel.setText("");
        String id = deleteBalanceTextField.getText();
        Loader loader = new Loader(loaderFrame);

        try {
          gateway.delete("http://localhost:8080/balance/", id, tokenModel).exceptionally(exception -> {
            loaderFrame.dispose();
            return null;
          }).thenAccept(model -> {
            loaderFrame.dispose();
          });
        }
        catch(URISyntaxException ex) {
          errorDeleteBalanceLabel.setText("Incorrect parameters");
        }

        deleteBalanceTextField.setText("");
      }
    });
  }

  private void showAllBalance() {
    try {
      gateway.getBalance(tokenModel).thenApply(listBalance -> {
        reloadTableBalance(showAllBalanceTable, getDefaultDataModelForBalance(), listBalance);

        return listBalance;
      });
    }
    catch(URISyntaxException ex) {
      ex.printStackTrace();
    }
  }

  private void balanceFilter() {
    balanceFilterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorBalanceFilterLabel.setText("");

        try {
        gateway.getBalanceFilter(filterBalanceFromTextField.getText(),
          filterBalanceToTextField.getText(), tokenModel).thenApply(listArticle -> {
          reloadTableBalance(filterBalanceTable, getDefaultDataModelForBalance(), listArticle);

          return listArticle;
        });
      }
        catch(URISyntaxException ex) {
        errorBalanceFilterLabel.setText("Incorrect parameters");
      }

        filterBalanceFromTextField.setText("");
        filterBalanceToTextField.setText("");
    }
    });
  }

  private void addOperation() {
    addOperationButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorAddOperationLabel.setText("");
        Date createDate = null;

        try {
          createDate = new SimpleDateFormat("yyyy-MM-dd").parse(addOperationDateTextField.getText());
        }
        catch(ParseException ex) {
          errorAddOperationLabel.setText("Incorrect parameters");
        }

        double debit = 0;
        double credit = 0;
        int articleId = 0;
        int balanceId = 0;

        try {
          debit = Double.parseDouble(addOperationDebitTextField.getText());
          credit = Double.parseDouble(addOperationCreditTextField.getText());

          articleId = Integer.parseInt(addOperationArtIdTextField.getText());
          balanceId = Integer.parseInt(addOperationBalIdTextField.getText());
        }
        catch(NumberFormatException ex) {
          errorAddOperationLabel.setText("Incorrect parameters");
        }

        Loader loader = new Loader(loaderFrame);

        try {
          gateway.addOperation(articleId, debit, credit, createDate, balanceId, tokenModel.getToken()).exceptionally(
            exception -> {
              loaderFrame.dispose();
              return null;
            }).thenAccept(model -> {
            loaderFrame.dispose();
          });
        }
        catch(URISyntaxException ex) {
          errorAddOperationLabel.setText("Incorrect parameters");
        }

        addOperationDateTextField.setText("");
        addOperationDebitTextField.setText("");
        addOperationCreditTextField.setText("");
        addOperationArtIdTextField.setText("");
        addOperationBalIdTextField.setText("");
      }
    });
  }

  private void deleteOperation() {
    deleteOperationButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorDeleteOperationLabel.setText("");
        String id = deleteOperationTextField.getText();
        Loader loader = new Loader(loaderFrame);

        try {
          gateway.delete("http://localhost:8080/operation/", id, tokenModel).exceptionally(exception -> {
            loaderFrame.dispose();
            return null;
          }).thenAccept(model -> {
            loaderFrame.dispose();
          });
        }
        catch(URISyntaxException ex) {
          errorDeleteOperationLabel.setText("Incorrect parameters");
        }

        deleteOperationTextField.setText("");
      }
    });
  }

  private void showAllOperation() {
    try {
      gateway.getOperation(tokenModel).thenApply(listOperation -> {
        reloadTableOperation(showAllOperationTable, getDefaultDataModelForOperation(), listOperation);

        return listOperation;
      });
    }
    catch(URISyntaxException ex) {
      ex.printStackTrace();
    }
  }

  private void operationFilter() {
    operationFilterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorOperationFilterLabel.setText("");

        try {
          gateway.getOperationFilter(operationFilterDateFromTextField.getText(),
            operationFilterDateToTextField.getText(), tokenModel).thenApply(listArticle -> {
            reloadTableOperation(operationFilterTable, getDefaultDataModelForOperation(), listArticle);

            return listArticle;
          });
        }
        catch(URISyntaxException ex) {
          errorOperationFilterLabel.setText("Incorrect parameters");
        }

        operationFilterDateFromTextField.setText("");
        operationFilterDateToTextField.setText("");
      }
    });
  }

  private DefaultTableModel getDefaultDataModelForArticle() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Id");
    model.addColumn("Name");

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
    model.addColumn("BalanceId");
    model.addColumn("CreateDate");

    return model;
  }

  private void reloadTableArticle(DefaultTableModel model, ArrayList<ArticleModelGet> arr) {
    for(int i = 0; i < arr.size(); ++i) {
      model.addRow(new Object[] {arr.get(i).getId(), arr.get(i).getName()});
    }

    showAllArticleTable.setModel(model);
  }

  private void reloadTableBalance(JTable table, DefaultTableModel model, ArrayList<BalanceModelGet> arr) {
    for(int i = 0; i < arr.size(); ++i) {
      model.addRow(new Object[] {arr.get(i).getId(), arr.get(i).getCreateDate(), arr.get(i).getDebit(), arr.get(
        i).getCredit(), arr.get(i).getAmount()});
    }

    table.setModel(model);
  }

  private void reloadTableOperation(JTable table, DefaultTableModel model, ArrayList<OperationModelGet> arr) {
    for(int i = 0; i < arr.size(); ++i) {
      model.addRow(new Object[] {arr.get(i).getId(), arr.get(i).getArticleId(), arr.get(i).getDebit(), arr.get(
        i).getCredit(), arr.get(i).getCreateDate(), arr.get(i).getBalanceId()});
    }

    table.setModel(model);
  }

  public TokenModel getTokenModel() {
    return tokenModel;
  }
}
