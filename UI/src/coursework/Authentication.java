package coursework;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Authentication
{
  private JFrame frame1;
  private JPanel rootPanel;
  private JPasswordField passwordField;
  private JTextField textField;
  private JButton signInButton;
  private JLabel errorLabel;

  public Authentication()
  {
    JFrame frame = new JFrame();
    frame.add(rootPanel);
    frame.setTitle("Home budget");
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setSize(1000, 700);
    frame.setLocation(290, 50);
    frame.setVisible(true);

    frame1 = frame;
  }

  public void interfaceImplementation()
  {
    signInButton.addActionListener(new ActionListener()
    {
      @Override
      public void actionPerformed(ActionEvent e)
      {
        errorLabel.setText("test");
      }
    });
  }
}