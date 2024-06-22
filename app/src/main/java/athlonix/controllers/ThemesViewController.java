package athlonix.controllers;

import athlonix.AppSettings;
import athlonix.models.Theme;
import athlonix.repository.ThemeRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
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

    DashboardController dashboardController;

    List<Theme> allThemes;

    private String selectedTheme;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            selectedTheme = AppSettings.getTheme();
            allThemes = themeRepository.getAllThemes();
            createThemesBoxes(allThemes);
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

    @FXML
    void saveThemeClicked(ActionEvent event) {
        try {
            AppSettings.setTheme(selectedTheme);
            Stage currentStage = (Stage) saveButton.getScene().getWindow();
            currentStage.close();
            dashboardController.reload();
        } catch (Exception e) {
            System.out.println("failed to reload");
        }
    }

    private Pane createInstalledBox(Theme theme) {

        Pane pane = new Pane();
        pane.setPrefHeight(207.0);
        pane.setPrefWidth(278.0);
        String border = "-fx-border-color: black;";

        if(theme.getName().equals(selectedTheme)) {
            border = "-fx-border-color: -color-accent-5;";
        }

        pane.setStyle(border + "-fx-border-radius: 0 0 8 8;-fx-border-width: 2;");
        pane.toFront();

        String themeName;
        themeName = theme.getName() + " (" +(theme.getSize()) + ")";
        RadioButton radioButton = new RadioButton(themeName);
        radioButton.setLayoutX(11.0);
        radioButton.setLayoutY(177.0);
        radioButton.setMnemonicParsing(false);
        radioButton.setStyle("-fx-font-weight: 800;");
        radioButton.getStyleClass().add("bold");

        radioButton.setOnAction(event -> {
            boolean selected = radioButton.isSelected();
            if(!selected) {
                radioButton.setSelected(true);
                return;
            }

            selectedTheme = theme.getName();
            refreshThemes();
        });

        if(theme.getName().equals(selectedTheme)) {
            radioButton.setSelected(true);
        }

        StackPane stackPane = new StackPane();
        stackPane.setLayoutX(2.0);  // Adjusted layout to fit within the pane's border
        stackPane.setLayoutY(2.0);
        stackPane.setPrefHeight(168.0);
        stackPane.setPrefWidth(274.0);
        String imageUrl = "http://localhost:8086/theme/image/"+theme.getName();
        stackPane.setStyle("-fx-background-image: url('"+imageUrl+"'); -fx-background-size: cover; -fx-background-radius: 8 8 8 8;");
        stackPane.toBack();
        Rectangle rec =  new javafx.scene.shape.Rectangle(pane.getPrefWidth(), pane.getPrefHeight());
        stackPane.setClip(rec);


        pane.getChildren().addAll(radioButton, stackPane);
        return pane;
    }

    private Pane createDownloadBox(Theme theme) {
        Pane pane = new Pane();
        pane.setLayoutX(622.0);
        pane.setLayoutY(257.0);
        pane.setPrefHeight(207.0);
        pane.setPrefWidth(278.0);
        String border = "-fx-border-color: black;";
        pane.setStyle(border + "-fx-border-radius: 0 0 8 8;-fx-border-width: 2;");
        pane.toFront();

        StackPane stackPane = new StackPane();
        stackPane.setLayoutX(2.0);  // Adjusted layout to fit within the pane's border
        stackPane.setLayoutY(2.0);
        stackPane.setPrefHeight(168.0);
        stackPane.setPrefWidth(274.0);
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
                Thread downloadThread = themeRepository.downloadTheme(theme.getName());
                downloadThread.join();
                refreshThemes();
            } catch (IOException e) {
                System.out.println("failed to install theme");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        pane.getChildren().addAll(stackPane, text, hyperlink);
        return pane;
    }

    private void refreshThemes() {
        themesContainer.getChildren().clear();
        createThemesBoxes(allThemes);
    }
}