package view;

import controller.NetworkProvider;
import controller.ParserJsonProvider;
import lombok.SneakyThrows;
import model.Computer;
import model.Workplace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeWorkplaceInfo extends JFrame {


    private final JTextField domainNameField = new JTextField();
    private final JTextField ipField = new JTextField();
    private final JTextField macField = new JTextField();

    private final JTextField powerSupplyField = new JTextField();
    private final JTextField motherboardField = new JTextField();
    private final JTextField ramField = new JTextField();
    private final JTextField cpuField = new JTextField();
    private final JTextField videoCardField = new JTextField();
    private final JTextField caseFormFactorField = new JTextField();
    private final JTextField hddOrSsdField = new JTextField();


    public ChangeWorkplaceInfo(Workplace workplace) {

        boolean makeWorkplace;
        if (workplace == null) {
            setTitle("Make workplace");
            makeWorkplace = true;
        } else {
            setTitle("Change workplace");
            makeWorkplace = false;
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeHeight = screenSize.height / 2;
        int sizeWidth = screenSize.width / 2;

        this.setBounds(
                (screenSize.width - sizeWidth) / 2 + 10,
                (screenSize.height - sizeHeight) / 2 + 10,
                sizeWidth,
                sizeHeight);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);

        GridLayout layout = new GridLayout(6, 4, 5, 4);

        setLayout(layout);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(list -> dispose());
        JButton peripheryButton = new JButton("Periphery..");
        peripheryButton.addActionListener(list -> MainView.FRAMES.add(new PeripheryView(workplace)));


        JLabel domainNameLabel = new JLabel("???????????????? ??????: ");
        add(domainNameLabel);
        add(domainNameField);
        JLabel ipLabel = new JLabel("IP: ");
        add(ipLabel);
        add(ipField);
        JLabel macLabel = new JLabel("MAC: ");
        add(macLabel);
        add(macField);

        JLabel powerSupplyLabel = new JLabel("???????? ??????????????: ");
        add(powerSupplyLabel);
        add(powerSupplyField);
        JLabel motherboardLabel = new JLabel("?????????????????????? ??????????: ");
        add(motherboardLabel);
        add(motherboardField);
        JLabel ramLabel = new JLabel("?????????????????????? ????????????: ");
        add(ramLabel);
        add(ramField);
        JLabel cpuLabel = new JLabel("??????????????????: ");
        add(cpuLabel);
        add(cpuField);
        JLabel videoCardLabel = new JLabel("????????????????????: ");
        add(videoCardLabel);
        add(videoCardField);
        JLabel caseFormFactorLabel = new JLabel("???????? ???????????? ??????????????: ");
        add(caseFormFactorLabel);
        add(caseFormFactorField);
        JLabel hddOrSsdLabel = new JLabel("??????????: ");
        add(hddOrSsdLabel);
        add(hddOrSsdField);


        if (makeWorkplace) {
            JButton nextStep = new JButton("Create");
            add(nextStep);
            add(closeButton);

            nextStep.addActionListener(new CreateOrChangeWorkplaceActionListener(workplace, null));
        } else {
            JButton saveButton = new JButton("Save");
            add(saveButton);
            add(closeButton);
            add(peripheryButton);
            JButton remove = new JButton("Delete");
            add(remove);
            NetworkProvider networkProvider = NetworkProvider.getInstance();
            ParserJsonProvider jsonProvider = ParserJsonProvider.getInstance();

            Iterable<Computer> computers = jsonProvider.parsComputers(networkProvider.getComputerToWorkplaceId(workplace.getWorkplaceId()));

            for (Computer computer : computers) {
                powerSupplyField.setText(computer.getPowerSupply());
                motherboardField.setText(computer.getMotherboard());
                ramField.setText(computer.getRam());
                cpuField.setText(computer.getCpu());
                videoCardField.setText(computer.getVideoCard());
                caseFormFactorField.setText(computer.getCaseFormFactor());
                hddOrSsdField.setText(computer.getHddOrSsd());

                saveButton.addActionListener(new CreateOrChangeWorkplaceActionListener(workplace, computer));
                remove.setForeground(Color.RED);
                remove.addActionListener(new DeleteComputerAndWorkplace(workplace, computer));
            }


            domainNameField.setText(workplace.getDomainName());
            ipField.setText(workplace.getIp());
            macField.setText(workplace.getMac());

        }


        pack();

        setVisible(true);
    }




    private class DeleteComputerAndWorkplace implements ActionListener {
        Workplace workplace;
        Computer computer;

        public DeleteComputerAndWorkplace(Workplace workplace, Computer computer) {
            this.workplace = workplace;
            this.computer = computer;

        }

        @SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            final JPanel panel = new JPanel();
            Object[] options = {"????",
                    "??????"};
            int response = JOptionPane.showOptionDialog(panel,
                    "???? ?????????????????????????? ???????????? ?????????????? ?????????????? ???????????",
                    "???????????????? ???????????????? ??????????",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            if (response == 0) {
                NetworkProvider networkProvider = NetworkProvider.getInstance();
                networkProvider.delComputerToWorkplaceId(computer.getUuid());
                networkProvider.delWorkplaceToId(workplace.getWorkplaceId());
                networkProvider.delPeripheryByWorkplaceId(workplace.getWorkplaceId());
                dispose();
                for (JFrame frame : MainView.FRAMES) {
                    if (frame.getClass() == MainView.class) {
                        MainView main = (MainView) frame;
                        main.dispose();
                        MainView.FRAMES.clear();
                        new InitialView();
                        break;


                    }
                }
            }
        }
    }

    private class CreateOrChangeWorkplaceActionListener implements ActionListener {
        Workplace workplace;
        Computer computer;

        public CreateOrChangeWorkplaceActionListener(Workplace workplace, Computer computer) {
            this.workplace = workplace;
            this.computer = computer;

        }
        private boolean checkNotNullTextFields() {
            String domainName = domainNameField.getText();

            String powerSupply = powerSupplyField.getText();
            String motherboard = motherboardField.getText();
            String ram = ramField.getText();
            String cpu = cpuField.getText();
            String hddOrSsd = hddOrSsdField.getText();


            if (domainName.isEmpty()) domainNameField.setBorder(BorderFactory.createLineBorder(Color.RED));
            else
                domainNameField.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            if (powerSupply.isEmpty()) powerSupplyField.setBorder(BorderFactory.createLineBorder(Color.RED));
            else
                powerSupplyField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if (motherboard.isEmpty()) motherboardField.setBorder(BorderFactory.createLineBorder(Color.RED));
            else
                motherboardField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if (ram.isEmpty()) ramField.setBorder(BorderFactory.createLineBorder(Color.RED));
            else
                ramField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if (cpu.isEmpty()) cpuField.setBorder(BorderFactory.createLineBorder(Color.RED));
            else
                cpuField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            if (hddOrSsd.isEmpty()) hddOrSsdField.setBorder(BorderFactory.createLineBorder(Color.RED));
            else
                hddOrSsdField.setBorder(BorderFactory.createLineBorder(Color.BLACK));


            if (!domainName.isEmpty() &&
                    !powerSupply.isEmpty() &&
                    !motherboard.isEmpty() &&
                    !ram.isEmpty() &&
                    !cpu.isEmpty() &&
                    !hddOrSsd.isEmpty()
            ) {
                return true;
            }

            JOptionPane.showMessageDialog(null, "?????????????????? ???????????? ????????");
            return false;
        }

        @SneakyThrows
        @Override
        public void actionPerformed(ActionEvent e) {
            if (checkNotNullTextFields()) {
                ParserJsonProvider jsonProvider = ParserJsonProvider.getInstance();
                NetworkProvider networkProvider = NetworkProvider.getInstance();

                if (workplace == null) {
                    Workplace newWorkplace = new Workplace(
                            null,
                            domainNameField.getText(),
                            ipField.getText(),
                            macField.getText()
                    );
                    String returned = networkProvider.createNewWorkplace(jsonProvider.parsWorkplaceToString(newWorkplace));
                    Workplace timeWorkplace = jsonProvider.parsWorkplace(returned);

                    Computer newComputer = new Computer(null,
                            timeWorkplace.getWorkplaceId(),
                            powerSupplyField.getText(),
                            motherboardField.getText(),
                            ramField.getText(),
                            cpuField.getText(),
                            videoCardField.getText(),
                            caseFormFactorField.getText(),
                            hddOrSsdField.getText());
                    networkProvider.createNewComputer(jsonProvider.parsComputerToString(newComputer));

                } else {
                    workplace.setIp(ipField.getText());
                    workplace.setMac(macField.getText());
                    workplace.setDomainName(domainNameField.getText());

                    networkProvider.createNewWorkplace(jsonProvider.parsWorkplaceToString(workplace));

                    Computer newComputer = new Computer(
                            computer.getUuid(),
                            computer.getWorkPlaceId(),
                            powerSupplyField.getText(),
                            motherboardField.getText(),
                            ramField.getText(),
                            cpuField.getText(),
                            videoCardField.getText(),
                            caseFormFactorField.getText(),
                            hddOrSsdField.getText()
                    );

                    networkProvider.createNewComputer(jsonProvider.parsComputerToString(newComputer));
                }

                dispose();
                for (JFrame frame : MainView.FRAMES) {
                    if (frame.getClass() == MainView.class) {
                        MainView main = (MainView) frame;
                        main.dispose();
                        MainView.FRAMES.clear();
                        new InitialView();
                        break;
                    }
                }
            }


        }

    }
}
