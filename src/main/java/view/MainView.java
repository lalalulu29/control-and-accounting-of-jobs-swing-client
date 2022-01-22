package view;

import model.Workplace;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



public class MainView extends JFrame {

    public static final List<JFrame> FRAMES = new ArrayList<>();


    public MainView(Iterable<Workplace> workplaces) {
        super("Main view");
        FRAMES.add(this);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeHeight = screenSize.height / 2;
        int sizeWidth = screenSize.width / 2;
        this.setResizable(false);
        this.setBounds(
                (screenSize.width - sizeWidth) / 2,
                (screenSize.height - sizeHeight) / 2,
                sizeWidth,
                sizeHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }


        };
        tableModel.addColumn("ID");
        tableModel.addColumn("Domain name");
        tableModel.addColumn("IP");
        tableModel.addColumn("MAC");
        JTable mainTable = new JTable(tableModel);
        mainTable.getTableHeader().setResizingAllowed(false);
        mainTable.getTableHeader().setReorderingAllowed(false);

        tableModel.addRow(new Object[] {"+", "+", "+", "+"});
        for(Workplace workplace : workplaces) {
            tableModel.addRow(
                    new Object[]{
                            workplace.getWorkplaceId(),
                            workplace.getDomainName(),
                            workplace.getIp(),
                            workplace.getMac()

                    }
            );
        }

        mainTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    if(table.getValueAt(row, 0) == "+") {
                        FRAMES.add(new ChangeWorkplaceInfo(null));
                    } else {
                        Long selectId = (Long) table.getValueAt(row, 0);
                        for(Workplace workplace : workplaces) {
                            if(Objects.equals(selectId, workplace.getWorkplaceId())) {
                                FRAMES.add(new ChangeWorkplaceInfo(workplace));
                            }
                        }
                    }

                }
            }
        });


        Font font = new Font("Verdana", Font.PLAIN, 12);
        mainTable.setFont(font);



        GridLayout layout = new GridLayout(1, 0, 0, 0);

        setLayout(layout);
        JScrollPane scrollPane = new JScrollPane(mainTable);
        add(scrollPane);

        setVisible(true);
    }
}
