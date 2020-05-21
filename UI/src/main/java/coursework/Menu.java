package coursework;

import api.*;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
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

  private JLabel errorAddArticleLabel;
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
    showAllArticle(addArticleTable);
    selectMainTabbedPane();
    selectArticleTabbedPane();
    selectBalanceTabbedPane();
    selectOperationTabbedPane();

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

  public TokenModel getTokenModel() {
    return tokenModel;
  }

  private void addArticle() {
    addArticleButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorAddArticleLabel.setText("");
        Loader loader = new Loader(loaderFrame);

        if(checkingForVoidsAndWhitespaces(addArticleTextField, errorAddArticleLabel, loaderFrame))
          return;

        try {
          gateway.addArticle(addArticleTextField.getText(), tokenModel.getToken()).exceptionally(exception -> {
            loaderFrame.dispose();
            return null;
          }).thenAccept(model -> {
            loaderFrame.dispose();
            showAllArticle(addArticleTable);
          });
        }
        catch(URISyntaxException ex) {
          errorAddArticleLabel.setText("Incorrect parameters");
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
        Loader loader = new Loader(loaderFrame);

        if(checkingForVoidsAndWhitespaces(deleteArticleTextField, errorDeleteArticleLabel, loaderFrame))
          return;

        //Если такого id нет, то setText

        try {
          gateway.delete("http://localhost:8080/article/", deleteArticleTextField.getText(),
                          tokenModel).exceptionally(exception -> {
            loaderFrame.dispose();
            return null;
          }).thenAccept(model -> {
            loaderFrame.dispose();
            showAllArticle(deleteArticleTable);
          });
        }
        catch(URISyntaxException ex) {
          errorDeleteArticleLabel.setText("Incorrect parameters");
        }

        deleteArticleTextField.setText("");
      }
    });
  }

  private void showAllArticle(JTable table) {
    try {
      gateway.getArticle(tokenModel).thenApply(listArticle -> {
        reloadTableArticle(table, getDefaultDataModelForArticle(), listArticle);

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

        if(checkingForVoidsAndWhitespaces(filterArticleTextField, errorArticleFilterLabel, loaderFrame))
          return;

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
        Loader loader = new Loader(loaderFrame);

        if(checkingForVoidsAndWhitespaces(addBalanceDateTextField, errorAddBalanceLabel, loaderFrame) ||
           checkingForVoidsAndWhitespaces(addBalanceDebitTextField, errorAddBalanceLabel, loaderFrame) ||
           checkingForVoidsAndWhitespaces(addBalanceCreditTextField, errorAddBalanceLabel, loaderFrame)) {
          addBalanceDateTextField.setText("");
          addBalanceDebitTextField.setText("");
          addBalanceCreditTextField.setText("");

          return;
        }

        checkingDate(createDate, addBalanceDateTextField, errorAddBalanceLabel);

        double debit = 0;
        double credit = 0;

        try {
          debit = Double.parseDouble(addBalanceDebitTextField.getText());
          credit = Double.parseDouble(addBalanceCreditTextField.getText());
        }
        catch(NumberFormatException ex) {
          errorAddBalanceLabel.setText("Incorrect debit or credit");
        }

        try {
          gateway.addBalance(createDate, debit, credit, tokenModel.getToken()).exceptionally(exception -> {
            loaderFrame.dispose();
            System.out.println(exception.toString());
            return null;
          }).thenAccept(model -> {
            loaderFrame.dispose();
            showAllBalance(addBalanceTable);
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

        if(checkingForVoidsAndWhitespaces(deleteBalanceTextField, errorDeleteBalanceLabel, loaderFrame))
          return;

        //Если такого id нет, то setText

        try {
          gateway.delete("http://localhost:8080/balance/", id, tokenModel).exceptionally(exception -> {
            loaderFrame.dispose();
            return null;
          }).thenAccept(model -> {
            loaderFrame.dispose();
            showAllBalance(deleteBalanceTable);
          });
        }
        catch(URISyntaxException ex) {
          errorDeleteBalanceLabel.setText("Incorrect parameters");
        }

        deleteBalanceTextField.setText("");
      }
    });
  }

  private void showAllBalance(JTable table) {
    try {
      gateway.getBalance(tokenModel).thenApply(listBalance -> {
        reloadTableBalance(table, getDefaultDataModelForBalance(), listBalance);

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
        Date from = null;
        Date to = null;

        if(checkingForVoidsAndWhitespaces(filterBalanceFromTextField, errorBalanceFilterLabel, loaderFrame) ||
           checkingForVoidsAndWhitespaces(filterBalanceToTextField, errorBalanceFilterLabel, loaderFrame)) {
          filterBalanceFromTextField.setText("");
          filterBalanceToTextField.setText("");

          return;
        }

        checkingDate(from, filterBalanceFromTextField, errorBalanceFilterLabel);
        checkingDate(to, filterBalanceToTextField, errorBalanceFilterLabel);

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

        Loader loader = new Loader(loaderFrame);

        if(checkingForVoidsAndWhitespaces(addOperationDateTextField, errorAddOperationLabel, loaderFrame) ||
          checkingForVoidsAndWhitespaces(addOperationDebitTextField, errorAddOperationLabel, loaderFrame) ||
          checkingForVoidsAndWhitespaces(addOperationCreditTextField, errorAddOperationLabel, loaderFrame) ||
          checkingForVoidsAndWhitespaces(addOperationArtIdTextField, errorAddOperationLabel, loaderFrame) ||
          checkingForVoidsAndWhitespaces(addOperationBalIdTextField, errorAddOperationLabel, loaderFrame)) {
          addOperationDateTextField.setText("");
          addOperationDebitTextField.setText("");
          addOperationCreditTextField.setText("");
          addOperationArtIdTextField.setText("");
          addOperationBalIdTextField.setText("");

          return;
        }

        checkingDate(createDate, addOperationDateTextField, errorAddOperationLabel);

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

        try {
          gateway.addOperation(articleId, debit, credit, createDate, balanceId, tokenModel.getToken()).exceptionally(
            exception -> {
              loaderFrame.dispose();
              return null;
            }).thenAccept(model -> {
            loaderFrame.dispose();
            showAllOperation(addOperationTable);
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

        if(checkingForVoidsAndWhitespaces(deleteOperationTextField, errorDeleteOperationLabel, loaderFrame))
          return;

        //Если такого id нет, то setText

        try {
          gateway.delete("http://localhost:8080/operation/", id, tokenModel).exceptionally(exception -> {
            loaderFrame.dispose();
            return null;
          }).thenAccept(model -> {
            loaderFrame.dispose();
            showAllOperation(deleteOperationTable);
          });
        }
        catch(URISyntaxException ex) {
          errorDeleteOperationLabel.setText("Incorrect parameters");
        }

        deleteOperationTextField.setText("");
      }
    });
  }

  private void showAllOperation(JTable table) {
    try {
      gateway.getOperation(tokenModel).thenApply(listOperation -> {
        reloadTableOperation(table, getDefaultDataModelForOperation(), listOperation);

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
        Date from = null;
        Date to = null;

        if(checkingForVoidsAndWhitespaces(operationFilterDateFromTextField, errorOperationFilterLabel, loaderFrame) ||
          checkingForVoidsAndWhitespaces(operationFilterDateToTextField, errorOperationFilterLabel, loaderFrame)) {
          operationFilterDateFromTextField.setText("");
          operationFilterDateToTextField.setText("");

          return;
        }

        checkingDate(from, operationFilterDateFromTextField, errorOperationFilterLabel);
        checkingDate(to, operationFilterDateToTextField, errorOperationFilterLabel);


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

  private void reloadTableArticle(JTable table, DefaultTableModel model, ArrayList<ArticleModelGet> arr) {
    for(int i = 0; i < arr.size(); ++i) {
      model.addRow(new Object[] {arr.get(i).getId(), arr.get(i).getName()});
    }

    table.setModel(model);
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

  private void selectMainTabbedPane() {
    mainTabbedPane.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        showAllArticle(addArticleTable);
        showAllBalance(addBalanceTable);
        showAllOperation(addOperationTable);
      }
    });
  }

  private void selectArticleTabbedPane() {
    articleTabbedPane.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        showAllArticle(addArticleTable);
        showAllArticle(deleteArticleTable);
        showAllArticle(showAllArticleTable);
      }
    });
  }

  private void selectBalanceTabbedPane() {
    balanceTabbedPane.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        showAllBalance(addBalanceTable);
        showAllBalance(deleteBalanceTable);
        showAllBalance(showAllBalanceTable);
      }
    });
  }

  private void selectOperationTabbedPane() {
    operationTabbedPane.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        showAllOperation(addOperationTable);
        showAllOperation(deleteOperationTable);
        showAllOperation(showAllOperationTable);
      }
    });
  }

  private boolean checkingForVoidsAndWhitespaces(JTextField textField, JLabel label, JFrame frame) {
    if(textField.getText().equals("") || textField.getText().contains(" ")) {
      label.setText("Text field is empty or contains whitespaces");
      frame.dispose();
      textField.setText("");

      return true;
    }

    return false;
  }

  private void checkingDate(Date date, JTextField textField, JLabel label) {
    try {
      date = new SimpleDateFormat("yyyy-MM-dd").parse(textField.getText());
    }
    catch(ParseException ex) {
      label.setText("Incorrect date");
    }
  }
}
