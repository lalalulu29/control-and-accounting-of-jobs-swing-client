package view;

import controller.NetworkProvider;
import controller.ParserJsonProvider;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class PeripheryView extends JFrame {

    List<PeripheryForView> peripheryForViewList = new ArrayList<>();


    public PeripheryView(Workplace workplace) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        AtomicInteger sizeHeight = new AtomicInteger(screenSize.height / 14);
        int sizeWidth = screenSize.width / 2;
        this.setBounds(
                (screenSize.width - sizeWidth)/ 2 + 10,
                (screenSize.height - sizeHeight.get()) / 2 + 10,
                sizeWidth,
                sizeHeight.get());

        NetworkProvider networkProvider = NetworkProvider.getInstance();
        ParserJsonProvider jsonProvider = ParserJsonProvider.getInstance();

        Iterable<Periphery> peripheries = jsonProvider.parsPeriphery(networkProvider.getPeripheryToWorkplaceId(workplace.getWorkplaceId()));
        Iterable<PeripheryType> peripheryTypes = jsonProvider.parsAllPeripheryType(networkProvider.getAllPeripheryType());


        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);


        GridLayout layout = new GridLayout(0, 6, 2, 3);
        setLayout(layout);


        add(new JLabel());
        add(new JLabel());
        add(new JLabel());
        JButton saveButton = new JButton("Save");
        add(saveButton);
        JButton closeButton = new JButton("Close");
        add(closeButton);
        JButton addNewPeriphery = new JButton("+");
        add(addNewPeriphery);
        for (Periphery periphery : peripheries) {
            sizeHeight.addAndGet(screenSize.height / 20);
            JLabel modelLabel = new JLabel("Модель: ");
            add(modelLabel);
            JTextField peripheryModelField = new JTextField();
            peripheryModelField.setText(periphery.getModel());
            add(peripheryModelField);
            JLabel commentLabel = new JLabel("Комментарии: ");
            add(commentLabel);

            JTextField peripheryCommentField = new JTextField();
            peripheryCommentField.setText(periphery.getComments());
            add(peripheryCommentField);

            JComboBox<String> peripheryType = new JComboBox<>();
            for (PeripheryType type : peripheryTypes) {

                peripheryType.addItem(type.getName());
                if(Objects.equals(periphery.getPeripheryType(), type.getType())) {
                    peripheryType.setSelectedItem(type.getName());
                }
            }
            add(peripheryType);
            JButton delPeriphery = new JButton("Delete");
            add(delPeriphery);

            peripheryForViewList.add(new PeripheryForView(
                    periphery,
                    modelLabel,
                    peripheryModelField,
                    commentLabel,
                    peripheryCommentField,
                    peripheryType,
                    delPeriphery,
                    false
            ));
            delPeriphery.addActionListener(new DeletePeripheryAction(periphery));
            delPeriphery.addActionListener(lis-> {
                sizeHeight.addAndGet((screenSize.height / 20)- 108) ;
                this.setBounds(
                        (screenSize.width - sizeWidth)/ 2 + 10,
                        (screenSize.height - sizeHeight.get()) / 2 + 10,
                        sizeWidth,
                        sizeHeight.get());

            });

        }

        this.setBounds(
                (screenSize.width - sizeWidth)/ 2 + 10,
                (screenSize.height - sizeHeight.get()) / 2 + 10,
                sizeWidth,
                sizeHeight.get());
        setVisible(true);

        closeButton.addActionListener(list->dispose());

        addNewPeriphery.addActionListener(list -> {

            JLabel modelLabel = new JLabel("Модель: ");
            add(modelLabel);
            JTextField peripheryModelField = new JTextField();
            add(peripheryModelField);

            JLabel commentLabel = new JLabel("Комментарии: ");
            add(commentLabel);

            JTextField peripheryCommentField = new JTextField();
            add(peripheryCommentField);

            JComboBox<String> peripheryType = new JComboBox<>();
            for (PeripheryType type : peripheryTypes) {
                peripheryType.addItem(type.getName());
            }
            add(peripheryType);
            JButton delPeriphery = new JButton("Delete");
            add(delPeriphery);
            delPeriphery.addActionListener(lis -> {
                remove(modelLabel);
                remove(peripheryModelField);
                remove(commentLabel);
                remove(peripheryCommentField);
                remove(peripheryType);
                remove(delPeriphery);



            });
            delPeriphery.addActionListener(lis-> {
                sizeHeight.addAndGet((screenSize.height / 20)- 108) ;
                this.setBounds(
                        (screenSize.width - sizeWidth)/ 2 + 10,
                        (screenSize.height - sizeHeight.get()) / 2 + 10,
                        sizeWidth,
                        sizeHeight.get());

            });

            sizeHeight.addAndGet(screenSize.height / 20);
            this.setBounds(
                    (screenSize.width - sizeWidth)/ 2 + 10,
                    (screenSize.height - sizeHeight.get()) / 2 + 10,
                    sizeWidth,
                    sizeHeight.get());


            peripheryForViewList.add(new PeripheryForView(
                    null,
                    modelLabel,
                    peripheryModelField,
                    commentLabel,
                    peripheryCommentField,
                    peripheryType,
                    delPeriphery,
                    false
            ));
        });

        saveButton.addActionListener(list-> {
            for (PeripheryForView peripheryForView : peripheryForViewList) {
                if (peripheryForView.isNeedDelete()) {
                    Long peripheryId = peripheryForView.getPeriphery().getUuid();
                    networkProvider.delPeripheryToId(peripheryId);
                } else if (peripheryForView.getPeriphery() == null) {
                    String peripheryType = (String) peripheryForView.getPeripheryTypeComboBox().getSelectedItem();
                    switch(Objects.requireNonNull(peripheryType)) {
                        case "Клавиатура": peripheryType = PeripheryTypeEnum.KBRD.toString(); break;
                        case "Монитор": peripheryType = PeripheryTypeEnum.MNTR.toString(); break;
                        case "Мышь": peripheryType = PeripheryTypeEnum.MUSE.toString(); break;
                        case "Принтер": peripheryType = PeripheryTypeEnum.PRNT.toString(); break;
                        case "Сканер": peripheryType = PeripheryTypeEnum.SCAN.toString(); break;
                        case "Видео/Аудио": peripheryType = PeripheryTypeEnum.VDAD.toString(); break;
                        default: break;
                    }
                    Long workplaceId = workplace.getWorkplaceId();
                    String model = peripheryForView.getModelTextField().getText();
                    String comment = peripheryForView.getCommentsTextField().getText();


                    Periphery periphery = new Periphery(null, workplaceId, peripheryType, model, comment);
                    try {
                        networkProvider.createNewPeriphery(jsonProvider.parsPeripheryToString(periphery));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Periphery periphery = peripheryForView.getPeriphery();
                        String peripheryType = (String) peripheryForView.getPeripheryTypeComboBox().getSelectedItem();
                        switch(Objects.requireNonNull(peripheryType)) {
                            case "Клавиатура": peripheryType = PeripheryTypeEnum.KBRD.toString(); break;
                            case "Монитор": peripheryType = PeripheryTypeEnum.MNTR.toString(); break;
                            case "Мышь": peripheryType = PeripheryTypeEnum.MUSE.toString(); break;
                            case "Принтер": peripheryType = PeripheryTypeEnum.PRNT.toString(); break;
                            case "Сканер": peripheryType = PeripheryTypeEnum.SCAN.toString(); break;
                            case "Видео/Аудио": peripheryType = PeripheryTypeEnum.VDAD.toString(); break;
                            default: break;
                        }
                        periphery.setPeripheryType(peripheryType);
                        periphery.setModel(peripheryForView.getModelTextField().getText());
                        periphery.setComments(peripheryForView.getCommentsTextField().getText());
                        networkProvider.createNewPeriphery(jsonProvider.parsPeripheryToString(periphery));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            dispose();
        });

    }

    private final class DeletePeripheryAction implements ActionListener {

        Periphery periphery;
        public DeletePeripheryAction(Periphery periphery) {
            this.periphery = periphery;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (PeripheryForView peripheryForView : peripheryForViewList) {
                if (Objects.equals(peripheryForView.getPeriphery().getUuid(), periphery.getUuid())) {
                    remove(peripheryForView.getModelLabel());
                    remove(peripheryForView.getModelTextField());
                    remove(peripheryForView.getCommentsLabel());
                    remove(peripheryForView.getCommentsTextField());
                    remove(peripheryForView.getPeripheryTypeComboBox());
                    remove(peripheryForView.getDeleteButton());

                    peripheryForView.setNeedDelete(true);
                    return;
                }
            }

        }
    }


}
