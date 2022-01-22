package view;

import controller.NetworkProvider;
import controller.ParserJsonProvider;
import model.Workplace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InitialView extends JFrame {


    public InitialView() {
        super("Initial view");
        int sizeHeight = 100;
        int sizeWidth = 380;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(
                (screenSize.width - sizeWidth)/ 2,
                (screenSize.height - sizeHeight) / 2,
                sizeWidth,
                sizeHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel grid1 = new JPanel(new FlowLayout());
        JActivityIndicator activityIndicator = new JActivityIndicator(0);
        grid1.add(activityIndicator);
        JLabel helloLabel = new JLabel("Происходит настройка системы. Пожалуйста подождите", SwingConstants.CENTER);
        grid1.add(helloLabel);
        activityIndicator.startRotating();
        Container container = getContentPane();
        container.add(grid1, BorderLayout.CENTER);
        this.setResizable(false);
        setVisible(true);


        ActionListener task = e -> {
            NetworkProvider networkProvider = NetworkProvider.getInstance();
            ParserJsonProvider json = ParserJsonProvider.getInstance();
            Iterable<Workplace> workplaces = json.parsWorkplaces(networkProvider.getAllWorkplace());
            dispose();
            if (workplaces != null) {
                new MainView(workplaces);
            }
        };

        Timer timer = new Timer(500, task);
        timer.setRepeats(false);
        timer.start();


    }
}
