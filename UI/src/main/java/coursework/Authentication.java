package coursework;

import api.Gateway;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URISyntaxException;

public class Authentication {
  private JPanel rootPanel;
  private JTabbedPane tabbedPane;
  private JTextField userNameTextField;
  private JButton signInButton;
  private JLabel errorLabel;
  private JPasswordField passwordField;
  private Gateway gateway;

  public Authentication(Gateway gateway) {
    this.gateway = gateway;

    JFrame frame = new JFrame();
    frame.add(rootPanel);
    frame.setTitle("Home budget");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(550, 350);
    frame.setLocation(500, 250);
    frame.setVisible(true);

    authenticationImplementation(frame);
  }

  private void authenticationImplementation(JFrame frame) {
    JFrame loaderFrame = new JFrame();
    errorLabel.setText("");

    signInButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String username = userNameTextField.getText();
        String password = String.valueOf(passwordField.getPassword());

        Loader loader = new Loader(loaderFrame);

        try {
          gateway.login(username, password).exceptionally(exception -> {
            loaderFrame.dispose();
            errorLabel.setText("Incorrect parameters");
            return null;
          }).thenAccept(model -> {
            if(model == null)
              return;

            loaderFrame.dispose();
            frame.dispose();
            Menu menu = new Menu(model);
            menu.menuImplementation();
          });
        }
        catch(URISyntaxException ex) {
          ex.printStackTrace();
        }
      }
    });
  }
}
