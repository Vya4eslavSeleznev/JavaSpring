package coursework;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Authentication
{
  private JPanel rootPanel;
  private JTabbedPane tabbedPane;
  private JTextField loginTextField;
  private JButton signInButton;
  private JLabel errorLabel;
  private JPasswordField passwordField;

  public Authentication()
  {
    JFrame frame = new JFrame();
    frame.add(rootPanel);
    frame.setTitle("Home budget");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(550, 350);
    frame.setLocation(500, 250);
    frame.setVisible(true);

    authenticationImplementation(frame);
  }

  private void authenticationImplementation(JFrame frame)
  {
    signInButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        String login = loginTextField.getText();
        char[] password = passwordField.getPassword();
        //String myPass = String.valueOf(passwordField.getPassword());

        if(true)
        {



          frame.dispose();
          Menu menu = new Menu();
          menu.menuImplementation();
        }
        else
        {
          errorLabel.setText("Incorrect parameters");
        }
      }
    });
  }
}
