package athlonix.controllers;

import athlonix.AppSettings;
import athlonix.models.Theme;
import athlonix.repository.ThemeRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

public class ThemesViewController implements Initializable {


    @FXML
    private Button saveButton;

    @FXML
    private TilePane themesContainer;

    ThemeRepository themeRepository = new ThemeRepository();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Theme> themes = themeRepository.getAllThemes();
            createThemesBoxes(themes);
        } catch (Exception e) {
            System.out.println("failed to fetch themes");
        }

    }

    private void createThemesBoxes(List<Theme> themes) {
        HashSet<String> installedThemes = AppSettings.getInstalledThemes();

        for (Theme theme : themes) {
            Pane themeBox;
            if(installedThemes.contains(theme.getName())) {
                themeBox = createInstalledBox(theme);
            } else {
                themeBox = createDownloadBox(theme);
            }
            themesContainer.getChildren().add(themeBox);
        }
    }

    private Pane createInstalledBox(Theme theme) {
        Pane pane = new Pane();
        pane.setPrefHeight(207.0);
        pane.setPrefWidth(278.0);
        pane.setStyle("-fx-border-color: black; -fx-border-radius: 8;");
        pane.toFront();

        String themeName;
        themeName = theme.getName() + " (" +(theme.getSize()) + ")";
        RadioButton radioButton = new RadioButton(themeName);
        radioButton.setLayoutX(11.0);
        radioButton.setLayoutY(177.0);
        radioButton.setMnemonicParsing(false);
        radioButton.setStyle("-fx-font-weight: 800;");
        radioButton.getStyleClass().add("bold");

        StackPane stackPane = new StackPane();
        stackPane.setLayoutX(1.0);
        stackPane.setLayoutY(1.0);
        stackPane.setPrefHeight(170.0);
        stackPane.setPrefWidth(276.0);
        String imageUrl = "http://localhost:8086/theme/image/"+theme.getName();
        stackPane.setStyle("-fx-background-image: url('"+imageUrl+"'); -fx-background-size: cover; -fx-background-radius: 8 8 0 0; z");
        stackPane.toBack();

        pane.getChildren().addAll(radioButton, stackPane);
        return pane;
    }

    private Pane createDownloadBox(Theme theme) {
        Pane pane = new Pane();
        pane.setLayoutX(622.0);
        pane.setLayoutY(257.0);
        pane.setPrefHeight(207.0);
        pane.setPrefWidth(278.0);
        pane.setStyle("-fx-border-color: black; -fx-border-radius: 8;");
        pane.toFront();

        StackPane stackPane = new StackPane();
        stackPane.setLayoutX(1.0);
        stackPane.setLayoutY(1.0);
        stackPane.setPrefHeight(170.0);
        stackPane.setPrefWidth(276.0);
        stackPane.toBack();

        String imageUrl = "http://localhost:8086/theme/image/"+theme.getName();
        stackPane.setStyle("-fx-background-image: url('"+imageUrl+"'); -fx-background-size: cover; -fx-background-radius: 8 8 0 0;");

        String themeName = theme.getName() + " (" +(theme.getSize()) + ")";
        Text text = new Text(themeName);
        text.setLayoutX(14.0);
        text.setLayoutY(194.0);
        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setStyle("-fx-font-weight: 800;");

        Hyperlink hyperlink = new Hyperlink("Télécharger");
        hyperlink.setLayoutX(187.0);
        hyperlink.setLayoutY(175.0);

        hyperlink.setOnAction(event -> {
            try {
                themeRepository.downloadTheme(theme.getName());
            } catch (IOException e) {
                System.out.println("failed to install theme");
            }
        });

        pane.getChildren().addAll(stackPane, text, hyperlink);
        return pane;
    }
}