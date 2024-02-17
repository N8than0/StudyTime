
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.time.LocalTime;



import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

class Counter {
    private final JButton button;
    private final JFormattedTextField hours;
    private final JFormattedTextField minutes;
    private final JFormattedTextField seconds;
    private AudioInputStream audioInputStream;
    private Clip clip;

    private final Timer timer;
    private final int delay = 1000;
    private final ActionListener taskPerformer = new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
            time = time.minusSeconds(1);
            if (time.equals(LocalTime.MIN)) {
                clip = null;
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(this.getClass().getClassLoader().getResource("res/nvrgna.wav"));
                    clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(button, "getoutgetoutgetoutgetout", "Your time is up, its over!", JOptionPane.INFORMATION_MESSAGE);
                clip.stop();
                

                
                timer.stop(); 
            }
            seconds.setText(String.valueOf(time.getSecond()));
            minutes.setText(String.valueOf(time.getMinute()));
            hours.setText(String.valueOf(time.getHour()));
        }
    };

    private LocalTime time = LocalTime.of(0, 0, 0);

    public static void main(String[] args) {

        SwingUtilities.invokeLater(Counter::new);
    }

    Counter() {

        timer = new Timer(delay,taskPerformer);
        JFrame frame = new JFrame();
        ImageIcon ico = new ImageIcon(this.getClass().getClassLoader().getResource("res/ico.png"));
        frame.setIconImage(ico.getImage());
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(null);
        JPanel subPanel1 = new JPanel(new GridLayout(2, 3));
        
        subPanel1.setBackground(Color.BLACK);
        subPanel1.setBorder(null);


        /*
         * The following lines ensure that the user can
         * only enter numbers.
         */

        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        //"labeling"

        JTextField text1 = new JTextField();
        text1.setText("HOURS");
        text1.setEditable(false);
        text1.setBackground(Color.BLACK);
        text1.setFont(new Font("Calibri",Font.BOLD,30));
        text1.setHorizontalAlignment(JLabel.CENTER);
        text1.setBorder(null);
        

        JTextField text2 = new JTextField();
        text2.setText("MINUTES");
        text2.setEditable(false);
        text2.setFont(new Font("Calibri",Font.BOLD,30));
        text2.setBackground(Color.BLACK);
        text2.setHorizontalAlignment(JLabel.CENTER);
        text2.setBorder(null);
        text2.setForeground(new Color(169,169,169));


        JTextField text3 = new JTextField();
        text3.setText("SECONDS");
        text3.setEditable(false);
        text3.setFont(new Font("Calibri",Font.BOLD,30));
        text3.setForeground(Color.WHITE);
        text3.setBackground(Color.BLACK);
        text3.setHorizontalAlignment(JLabel.CENTER);
        text3.setBorder(null);

        //fields for minutes and seconds
        hours = new JFormattedTextField(formatter);
        hours.setHorizontalAlignment(JLabel.CENTER);
        minutes = new JFormattedTextField(formatter);
        minutes.setHorizontalAlignment(JLabel.CENTER);
        seconds = new JFormattedTextField(formatter);
        seconds.setHorizontalAlignment(JLabel.CENTER);
        hours.setText("0");
        hours.setFont(new Font("Calibri",Font.BOLD,90));
        minutes.setText("0");
        minutes.setFont(new Font("Calibri",Font.BOLD,90));
        seconds.setText("0");
        seconds.setFont(new Font("Calibri",Font.BOLD,90));
        hours.setBackground(Color.BLACK);
        minutes.setBackground(Color.BLACK);
        seconds.setBackground(Color.BLACK);
        hours.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        minutes.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        seconds.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));


        JPanel subPanel2 = new JPanel();
        subPanel2.setBackground(Color.BLACK);
        /*
         * When the user presses the OK-button, the program will
         * start to count down.
         */

        button = new JButton("Start");
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.LIGHT_GRAY);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.addActionListener(actionEvent -> {
            time = LocalTime.of(Integer.parseInt(hours.getText()), Integer.parseInt(minutes.getText()), Integer.parseInt(seconds.getText()));
            button.setEnabled(false);
            //Timer for one second delay
            timer.start();
        });

        //Reset-button
        JButton button2 = new JButton("Reset");
        button2.setFocusPainted(false);
        button2.setBackground(Color.DARK_GRAY);
        button2.setForeground(Color.LIGHT_GRAY);
        button2.setBorderPainted(false);
        button2.addActionListener(actionEvent -> {
            hours.setText("0");
            minutes.setText("0");
            seconds.setText("0");
            button.setEnabled(true);
            time = LocalTime.of(0, 0, 0);
            timer.stop();
        });

        subPanel1.add(text1);
        subPanel1.add(text2);
        subPanel1.add(text3);
        subPanel1.add(hours);
        subPanel1.add(minutes);
        subPanel1.add(seconds);
        subPanel2.add(button);
        subPanel2.add(button2);
        panel.add(subPanel1, BorderLayout.CENTER);
        panel.add(subPanel2, BorderLayout.PAGE_START);
        panel.setBackground(Color.BLACK);
        frame.add(panel);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setVisible(true);
    }
}