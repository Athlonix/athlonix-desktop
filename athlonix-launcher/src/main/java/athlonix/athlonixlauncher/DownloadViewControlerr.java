package athlonix.athlonixlauncher;

import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    public void close() {
        Stage stage = (Stage) downloadText.getScene().getWindow();
        stage.close();
    }

}
