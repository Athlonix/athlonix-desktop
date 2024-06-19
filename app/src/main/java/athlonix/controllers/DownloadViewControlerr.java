package athlonix.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

public class DownloadViewControlerr {

    @FXML
    private Text downloadText;

    @FXML
    private ProgressBar progressBar;

    public void setProgressBarValue(double value) {
        if(value < 0 ) value = 0;
        if(value > 100) value = 100;
        progressBar.setProgress(value);
    }

    public void setTextValue(String fileName) {
        downloadText.setText("Telechargement de " + fileName);
    }
}
