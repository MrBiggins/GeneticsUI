package sample;

import genetics.classes.DiofantManager;
import genetics.gui.Interfaces.LogEvent;
import genetics.gui.models.SettingsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.swing.*;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.util.Callback;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements LogEvent {

    public SettingsModel settingsModels;
    public DefaultListModel model;

    @FXML private TextField likeHoodTxt;
    @FXML private TextField populationTxt;
    @FXML private ListView logList;

    ObservableList observableList = FXCollections.observableArrayList();



    public Controller() {
        model = new DefaultListModel();
        settingsModels = new SettingsModel();
    }


    @Override
    public void addLogItem(String message) {
      //logList  model.addElement(message);

        observableList.add(message);
        logList.setItems(observableList);
        /*logList.setCellFactory(new Callback<ListView<String>, ListCell<String>>()
        {
            @Override
            public ListCell<String> call(ListView<String> listView)
            {
                return new ListViewCell();
            }
        });*/

    }

    @FXML
    public void startGenetics(ActionEvent actionEvent) {
        try {
            logList.getItems().clear();
            String likehood = likeHoodTxt.getText();
            String population = populationTxt.getText();
            settingsModels.setMutationLikehood(Float.valueOf(likehood));
            settingsModels.setpopulation(Integer.valueOf(population));
            DiofantManager geneticsManager = new DiofantManager(this, settingsModels);
            geneticsManager.startGenetecs();
        }catch (Exception ex){

        }
    }
}
