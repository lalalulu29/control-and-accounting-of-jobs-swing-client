package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PeripheryForView {
    Periphery periphery;
    JLabel modelLabel;
    JTextField modelTextField;
    JLabel commentsLabel;
    JTextField commentsTextField;
    JComboBox<String> peripheryTypeComboBox;
    JButton deleteButton;
    boolean needDelete;
}
