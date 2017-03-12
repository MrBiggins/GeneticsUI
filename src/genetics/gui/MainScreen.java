package genetics.gui;
import genetics.classes.DiofantManager;
import genetics.gui.Interfaces.LogEvent;
import genetics.gui.models.SettingsModel;

import javax.swing.*;

/**
 * Created by denistimchenko on 26.02.17.
 */
public class MainScreen extends JFrame implements LogEvent {


    private JPanel mainPanel;
    private JList logList;
    private JButton startButton;
    public DefaultListModel model;
    public SettingsModel settingsModels;

    public MainScreen() {
        model = new DefaultListModel();
        settingsModels = new SettingsModel();
        initUI();
        initControls();
    }

    private void initControls() {
        startButton.addActionListener(e -> {
            DiofantManager geneticsManager = new DiofantManager(this, settingsModels);
            geneticsManager.startGenetecs();
        });
        logList.setVisible(true);
       // add(logList, BorderLayout.CENTER);
        //JScrollPane pane = new JScrollPane(logList);
    }


    private void initUI() {

        setTitle("Genetic Window");
        //setSize(500, 600);
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void addLogItem(String message) {
        model.addElement(message);
        logList.setModel(model);
    }
}





