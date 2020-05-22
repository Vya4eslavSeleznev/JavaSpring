package coursework;

import api.*;
import exception.NotFoundException;
import exception.NotUniqueException;

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
import java.util.Optional;

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

    close(frame);
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
        loaderFrame.setVisible(true);

        if(checkingForVoidsAndWhitespaces(addArticleTextField, errorAddArticleLabel, loaderFrame)) {
          loaderFrame.setVisible(false);
          return;
        }
        try {
          gateway.addArticle(addArticleTextField.getText(), tokenModel.getToken()).exceptionally(exception -> {
            if(exception.getCause().getClass() == NotUniqueException.class) {
              errorAddArticleLabel.setText("The name is not unique");
            }

            loaderFrame.setVisible(false);
            return null;
          }).thenAccept(model -> {
            loaderFrame.setVisible(false);
            showAllArticle(addArticleTable);
          });
        }
        catch(URISyntaxException ex) {
          errorAddArticleLabel.setText("Incorrect parameters");
          loaderFrame.setVisible(false);
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
        loaderFrame.setVisible(true);

        if(checkingForVoidsAndWhitespaces(deleteArticleTextField, errorDeleteArticleLabel, loaderFrame)) {
          loaderFrame.setVisible(false);
          return;
        }

        Integer id = stringToInt(deleteArticleTextField, errorDeleteArticleLabel, loaderFrame);

        if (id == null) {
          loaderFrame.setVisible(false);
          return;
        }

        try {
          gateway.delete("http://localhost:8080/article/", id, tokenModel).exceptionally(
            exception -> {
              noSuchElement(exception, errorDeleteArticleLabel);
              loaderFrame.setVisible(false);
              return null;
            }).thenAccept(model -> {
            loaderFrame.setVisible(false);
            showAllArticle(deleteArticleTable);
          });
        }
        catch(URISyntaxException ex) {
          errorDeleteArticleLabel.setText("Incorrect parameters");
          loaderFrame.setVisible(false);
        }

        deleteArticleTextField.setText("");
      }
    });
  }

  private void showAllArticle(JTable table) {
    try {
      loaderFrame.setVisible(true);

      gateway.getArticle(tokenModel).thenAccept(listArticle -> {
        reloadTableArticle(table, getDefaultDataModelForArticle(), listArticle);
        loaderFrame.setVisible(false);
      });
    }
    catch(URISyntaxException ex) {
      loaderFrame.setVisible(false);
    }
  }

  private void articleFilter() {
    articleFilterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        loaderFrame.setVisible(true);
        errorArticleFilterLabel.setText("");

        if(checkingForVoidsAndWhitespaces(filterArticleTextField, errorArticleFilterLabel, loaderFrame)) {
          loaderFrame.setVisible(false);
          return;
        }

        Integer id = stringToInt(filterArticleTextField, errorArticleFilterLabel, loaderFrame);

        if (id == null) {
          loaderFrame.setVisible(false);
          return;
        }

        try {
          gateway.getArticleFilter(id, tokenModel).thenApply(listArticle -> {
            reloadTableOperation(filterArticleTable, getDefaultDataModelForOperation(), listArticle);
            loaderFrame.setVisible(false);
            return listArticle;
          });
        }
        catch(URISyntaxException ex) {
          errorArticleFilterLabel.setText("Incorrect parameters");
          loaderFrame.setVisible(false);
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
        loaderFrame.setVisible(true);

        if(checkingForVoidsAndWhitespaces(addBalanceDateTextField, errorAddBalanceLabel,
          loaderFrame) || checkingForVoidsAndWhitespaces(addBalanceDebitTextField, errorAddBalanceLabel,
          loaderFrame) || checkingForVoidsAndWhitespaces(addBalanceCreditTextField, errorAddBalanceLabel,
          loaderFrame)) {
          balanceFieldSetText();
          loaderFrame.setVisible(false);
          return;
        }

        createDate = checkingDate(addBalanceDateTextField, errorAddBalanceLabel);

        if(createDate == null) {
          errorAddBalanceLabel.setText("Incorrect date");
          balanceFieldSetText();
          loaderFrame.setVisible(false);
          return;
        }

        double debit = 0;
        double credit = 0;

        try {
          debit = Double.parseDouble(addBalanceDebitTextField.getText());
          credit = Double.parseDouble(addBalanceCreditTextField.getText());
        }
        catch(NumberFormatException ex) {
          errorAddBalanceLabel.setText("Incorrect debit or credit");
          loaderFrame.setVisible(false);
          balanceFieldSetText();
          return;
        }

        if(checkingDebitAndCredit(debit, credit, errorAddBalanceLabel, loaderFrame)) {
          balanceFieldSetText();
          loaderFrame.setVisible(false);
          return;
        }

        try {
          gateway.addBalance(createDate, debit, credit, tokenModel.getToken()).exceptionally(exception -> {
            loaderFrame.setVisible(false);
            return null;
          }).thenAccept(model -> {
            loaderFrame.setVisible(false);
            showAllBalance(addBalanceTable);
          });
        }
        catch(URISyntaxException ex) {
          loaderFrame.setVisible(false);
          errorAddBalanceLabel.setText("Incorrect parameters");
        }

        balanceFieldSetText();
      }
    });
  }

  private void deleteBalance() {
    deleteBalanceButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorDeleteBalanceLabel.setText("");
        loaderFrame.setVisible(true);

        if(checkingForVoidsAndWhitespaces(deleteBalanceTextField, errorDeleteBalanceLabel, loaderFrame)) {
          loaderFrame.setVisible(false);
          return;
        }

        Integer id = stringToInt(deleteBalanceTextField, errorDeleteBalanceLabel, loaderFrame);

        if (id == null) {
          loaderFrame.setVisible(false);
          return;
        }

        try {
          gateway.delete("http://localhost:8080/balance/", id, tokenModel).exceptionally(exception -> {
            noSuchElement(exception, errorDeleteBalanceLabel);
            loaderFrame.setVisible(false);
            return null;
          }).thenAccept(model -> {
            loaderFrame.setVisible(false);
            showAllBalance(deleteBalanceTable);
          });
        }
        catch(URISyntaxException ex) {
          loaderFrame.setVisible(false);
          errorDeleteBalanceLabel.setText("Incorrect parameters");
        }

        deleteBalanceTextField.setText("");
      }
    });
  }

  private void showAllBalance(JTable table) {
    try {
      loaderFrame.setVisible(true);

      gateway.getBalance(tokenModel).thenAccept(listBalance -> {
        reloadTableBalance(table, getDefaultDataModelForBalance(), listBalance);
        loaderFrame.setVisible(false);
      });
    }
    catch(URISyntaxException ex) {
      loaderFrame.setVisible(false);
    }
  }

  private void balanceFilter() {
    balanceFilterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorBalanceFilterLabel.setText("");
        loaderFrame.setVisible(true);
        Date from = null;
        Date to = null;

        if(checkingForVoidsAndWhitespaces(filterBalanceFromTextField, errorBalanceFilterLabel,
          loaderFrame) || checkingForVoidsAndWhitespaces(filterBalanceToTextField, errorBalanceFilterLabel,
          loaderFrame)) {
          dateFilterFieldSetText(filterBalanceFromTextField, filterBalanceToTextField);
          loaderFrame.setVisible(false);
          return;
        }

        from = checkingDate(filterBalanceFromTextField, errorBalanceFilterLabel);
        to = checkingDate(filterBalanceToTextField, errorBalanceFilterLabel);

        if(from == null || to == null) {
          errorBalanceFilterLabel.setText("Incorrect date");
          dateFilterFieldSetText(filterBalanceFromTextField, filterBalanceToTextField);
          loaderFrame.setVisible(false);
          return;
        }

        try {
          gateway.getBalanceFilter(filterBalanceFromTextField.getText(), filterBalanceToTextField.getText(),
            tokenModel).thenApply(listArticle -> {
            reloadTableBalance(filterBalanceTable, getDefaultDataModelForBalance(), listArticle);
            loaderFrame.setVisible(false);
            return listArticle;
          });
        }
        catch(URISyntaxException ex) {
          loaderFrame.setVisible(false);
          errorBalanceFilterLabel.setText("Incorrect parameters");
        }

        dateFilterFieldSetText(filterBalanceFromTextField, filterBalanceToTextField);
      }
    });
  }

  private void addOperation() {
    addOperationButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorAddOperationLabel.setText("");
        loaderFrame.setVisible(true);

        if(checkingForVoidsAndWhitespaces(addOperationDateTextField, errorAddOperationLabel,
          loaderFrame) || checkingForVoidsAndWhitespaces(addOperationDebitTextField, errorAddOperationLabel,
          loaderFrame) || checkingForVoidsAndWhitespaces(addOperationCreditTextField, errorAddOperationLabel,
          loaderFrame) || checkingForVoidsAndWhitespaces(addOperationArtIdTextField, errorAddOperationLabel,
          loaderFrame) || checkingForVoidsAndWhitespaces(addOperationBalIdTextField, errorAddOperationLabel,
          loaderFrame)) {
          operationFieldSetText();
          loaderFrame.setVisible(false);
          return;
        }

        Date createDate = checkingDate(addOperationDateTextField, errorAddOperationLabel);

        if(createDate == null) {
          errorAddOperationLabel.setText("Incorrect date");
          operationFieldSetText();
          loaderFrame.setVisible(false);
          return;
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
          loaderFrame.setVisible(false);
          errorAddOperationLabel.setText("Incorrect parameters");
          operationFieldSetText();
          return;
        }

        if(checkingDebitAndCredit(debit, credit, errorAddOperationLabel, loaderFrame)) {
          operationFieldSetText();
          loaderFrame.setVisible(false);
          return;
        }

        try {
          gateway.addOperation(articleId, debit, credit, createDate, balanceId, tokenModel.getToken()).exceptionally(
            exception -> {
              noSuchElement(exception, errorAddOperationLabel);
              loaderFrame.setVisible(false);
              return null;
            }).thenAccept(model -> {
            loaderFrame.setVisible(false);
            showAllOperation(addOperationTable);
          });
        }
        catch(URISyntaxException ex) {
          loaderFrame.setVisible(false);
          errorAddOperationLabel.setText("Incorrect parameters");
        }

        operationFieldSetText();
      }
    });
  }

  private void deleteOperation() {
    deleteOperationButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorDeleteOperationLabel.setText("");
        loaderFrame.setVisible(true);

        if(checkingForVoidsAndWhitespaces(deleteOperationTextField, errorDeleteOperationLabel, loaderFrame)) {
          loaderFrame.setVisible(false);
          return;
        }

        Integer id = stringToInt(deleteOperationTextField, errorDeleteOperationLabel, loaderFrame);

        if (id == null) {
          loaderFrame.setVisible(false);
          return;
        }

        try {
          gateway.delete("http://localhost:8080/operation/", id, tokenModel).exceptionally(exception -> {
            noSuchElement(exception, errorDeleteOperationLabel);
            loaderFrame.setVisible(false);
            return null;
          }).thenAccept(model -> {
            loaderFrame.setVisible(false);
            showAllOperation(deleteOperationTable);
          });
        }
        catch(URISyntaxException ex) {
          loaderFrame.setVisible(false);
          errorDeleteOperationLabel.setText("Incorrect parameters");
        }

        deleteOperationTextField.setText("");
      }
    });
  }

  private void showAllOperation(JTable table) {
    try {
      loaderFrame.setVisible(true);

      gateway.getOperation(tokenModel).thenAccept(listOperation -> {
        reloadTableOperation(table, getDefaultDataModelForOperation(), listOperation);
        loaderFrame.setVisible(false);
      });
    }
    catch(URISyntaxException ex) {
      loaderFrame.setVisible(false);
    }
  }

  private void operationFilter() {
    operationFilterButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        errorOperationFilterLabel.setText("");
        loaderFrame.setVisible(true);
        Date from = null;
        Date to = null;

        if(checkingForVoidsAndWhitespaces(operationFilterDateFromTextField, errorOperationFilterLabel,
          loaderFrame) || checkingForVoidsAndWhitespaces(operationFilterDateToTextField, errorOperationFilterLabel,
          loaderFrame)) {
          dateFilterFieldSetText(operationFilterDateFromTextField, operationFilterDateToTextField);
          loaderFrame.setVisible(false);
          return;
        }

        from = checkingDate(operationFilterDateFromTextField, errorOperationFilterLabel);
        to = checkingDate(operationFilterDateToTextField, errorOperationFilterLabel);

        if(from == null || to == null) {
          errorOperationFilterLabel.setText("Incorrect date");
          dateFilterFieldSetText(operationFilterDateFromTextField, operationFilterDateToTextField);
          loaderFrame.setVisible(false);
          return;
        }

        try {
          gateway.getOperationFilter(operationFilterDateFromTextField.getText(),
            operationFilterDateToTextField.getText(), tokenModel).thenApply(listArticle -> {
            reloadTableOperation(operationFilterTable, getDefaultDataModelForOperation(), listArticle);
            loaderFrame.setVisible(false);
            return listArticle;
          });
        }
        catch(URISyntaxException ex) {
          loaderFrame.setVisible(false);
          errorOperationFilterLabel.setText("Incorrect parameters");
        }

        dateFilterFieldSetText(operationFilterDateFromTextField, operationFilterDateToTextField);
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
        i).getCredit(), arr.get(i).getBalanceId(), arr.get(i).getCreateDate()});
    }

    table.setModel(model);
  }

  private void selectMainTabbedPane() {
    mainTabbedPane.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        if(mainTabbedPane.getSelectedIndex() == 0) {
          showAllArticle(addArticleTable);
        }
        else if(mainTabbedPane.getSelectedIndex() == 1) {
          showAllBalance(addBalanceTable);
        }
        else if(mainTabbedPane.getSelectedIndex() == 2) {
          showAllOperation(addOperationTable);
        }
      }
    });
  }

  private void selectArticleTabbedPane() {
    articleTabbedPane.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        if (articleTabbedPane.getSelectedIndex() == 0) {
          showAllArticle(addArticleTable);
        } else if (articleTabbedPane.getSelectedIndex() == 1) {
          showAllArticle(deleteArticleTable);
        } else if (articleTabbedPane.getSelectedIndex() == 2) {
          showAllArticle(showAllArticleTable);
        }
      }
    });
  }

  private void selectBalanceTabbedPane() {
    balanceTabbedPane.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        if (balanceTabbedPane.getSelectedIndex() == 0) {
          showAllBalance(addBalanceTable);
        } else if (balanceTabbedPane.getSelectedIndex() == 1) {
          showAllBalance(deleteBalanceTable);
        } else if (balanceTabbedPane.getSelectedIndex() == 2) {
          showAllBalance(showAllBalanceTable);
        }
      }
    });
  }

  private void selectOperationTabbedPane() {
    operationTabbedPane.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        if (operationTabbedPane.getSelectedIndex() == 0) {
          showAllOperation(addOperationTable);
        } else if (operationTabbedPane.getSelectedIndex() == 1) {
          showAllOperation(deleteOperationTable);
        } else if (operationTabbedPane.getSelectedIndex() == 2) {
          showAllOperation(showAllOperationTable);
        }
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

  private Date checkingDate(JTextField textField, JLabel label) {
    try {
      return new SimpleDateFormat("yyyy-MM-dd").parse(textField.getText());
    }
    catch(ParseException ex) {
      label.setText("Incorrect date");
      textField.setText("");
      return null;
    }
  }

  private void noSuchElement(Throwable exception, JLabel label) {
    if(exception.getCause().getClass() == NotFoundException.class) {
      label.setText("No such element");
    }
  }

  private void operationFieldSetText() {
    addOperationDateTextField.setText("");
    addOperationDebitTextField.setText("");
    addOperationCreditTextField.setText("");
    addOperationArtIdTextField.setText("");
    addOperationBalIdTextField.setText("");
  }

  private void balanceFieldSetText() {
    addBalanceDateTextField.setText("");
    addBalanceDebitTextField.setText("");
    addBalanceCreditTextField.setText("");
  }

  private void dateFilterFieldSetText(JTextField first, JTextField second) {
    first.setText("");
    second.setText("");
  }

  private boolean checkingDebitAndCredit(double debit, double credit, JLabel label, JFrame frame) {
    if(debit < 0 || credit < 0) {
      label.setText("Credit or debit less than zero");
      frame.dispose();

      return true;
    }

    return false;
  }

  private void close(JFrame frame) {
    frame.addWindowListener(new java.awt.event.WindowAdapter() {
      @Override
      public void windowClosing(java.awt.event.WindowEvent windowEvent) {
        loaderFrame.dispose();
      }
    });
  }

  private Integer stringToInt(JTextField textField, JLabel errorLabel, JFrame frame) {
    try {
      return Integer.parseInt(textField.getText());
    }
    catch(NumberFormatException ex) {
      errorLabel.setText("Incorrect id");
      textField.setText("");
      frame.setVisible(false);

      return null;
    }
  }
}
