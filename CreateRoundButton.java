import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

//add some comments
public class CreateRoundButton extends JButton {
  public CreateRoundButton(String label) {
    super(label);
    Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width,size.height);
    setPreferredSize(size);

    setContentAreaFilled(false);
  }

  protected void paintComponent(Graphics g) {
    if (getModel().isArmed()) {
      g.setColor(Color.lightGray);
    } else {
      g.setColor(getBackground());
    }
    g.fillOval(0, 0, getSize().width-5,getSize().height-5);

    super.paintComponent(g);
  }

  protected void paintBorder(Graphics g) {
    g.setColor(getForeground());
    g.drawOval(0, 0, getSize().width-5,     getSize().height-5);
  }

  Shape shape;
  public boolean contains(int x, int y) {
    if (shape == null || 
      !shape.getBounds().equals(getBounds())) {
      shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }

  public static void main(String[] args) {
    JButton button = new CreateRoundButton("Click");
    button.setBackground(Color.gray);

    JFrame frame = new JFrame();
    frame.getContentPane().add(button);
    frame.getContentPane().setLayout(new FlowLayout());
    frame.setSize(150, 150);
    frame.setVisible(true);
  }
}
