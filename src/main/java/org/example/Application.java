package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Application extends JFrame implements ActionListener {
    static JTextField t;

    static JLabel label;
    // JFrame
    static JFrame f;

    // JButton
    static JButton b;

    // label to display text
    static JLabel l, showTemp, showHumidity, showFeelsLike;
    static JPanel p;
    private Deserializer des;

    private static final Logger logger = LogManager.getLogger(Application.class);
    private JTextPane textPane1;

    Application()
    {
        try {
            des = new Deserializer();
            f = new JFrame("Weather App");

            label = new JLabel();
            // create a label to display text
            l = new JLabel("nothing entered");
            showTemp = new JLabel("Temperature");
            showHumidity = new JLabel("Humidity");
            showFeelsLike = new JLabel("FeelsLike");

            // create a new button
            b = new JButton("submit");

            // create a object of JTextField with 16 columns and a given initial text
            t = new JTextField(16);
            // create a panel to add buttons and textfield
            p = new JPanel();
            b.addActionListener(this::actionPerformed);
            // add buttons and textfield to panel
            p.add(t);
            p.add(b);
            p.add(l);
            p.add(textPane1);

            // add panel to frame
            f.add(p);

            // set the size of frame
            f.setSize(300, 300);
            f.setDefaultCloseOperation(EXIT_ON_CLOSE);
            f.show();
        }
        catch(Exception e){
            logger.error("Couldnt create GUI!");
        }
    }

    // if the button is pressed
    public void actionPerformed(ActionEvent e)
    {
        des.setLocation(t.getText());
        des.updateFields();
        String s = e.getActionCommand();
        if (s.equals("submit")) {
            // set the text of the label to the text of the field
            l.setText(t.getText());
            des.setLocation(t.getText());
            // set the text of field to blank
            t.setText("");

            label.setIcon(new ImageIcon(getIcon(des.getSky())));
            p.remove(showTemp);
            p.remove(showHumidity);
            showTemp.setText("Location temp: " + des.getTemp() + "*");
            showHumidity.setText("Location Humidity: " + des.getHumidity() + "%");
            showFeelsLike.setText("Location Feels Like Temperature: " + des.getFeelsLike() + "*");
            p.remove(label);
            p.revalidate();
            p.add(label);
            p.add(showTemp);
            p.add(showHumidity);
            p.add(showFeelsLike);
        }
    }

    private Image getIcon(String weather)
    {
        ImageIcon icon = null;

        try {

            switch (weather) {
                case "Rain": {
                    icon = new ImageIcon("./src/main/java/org/example/img/rain.png");
                    break;
                }
                case "Clouds": {
                    icon = new ImageIcon("./src/main/java/org/example/img/clouds.png");
                    break;
                }
                case "Thunderstorm":
                case "Mist": {
                    icon = new ImageIcon("./src/main/java/org/example/img/storm.png");
                    break;
                }
                case "Clear":
                default: {
                    icon = new ImageIcon("./src/main/java/org/example/img/clear.png");
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("Cannot open icons\n" + e);
        }
        return icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
    }
}
