package coursework;

import javax.swing.*;

public class Loader
{
  private JPanel rootPanel;
  private JProgressBar progressBar;

  public Loader(JFrame frame)
  {
    frame.add(rootPanel);
    frame.setTitle("Loading...");
    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    frame.setSize(400, 200);
    frame.setLocation(570, 300);
    frame.setVisible(true);
    progressBar.setIndeterminate(true);
  }
}
