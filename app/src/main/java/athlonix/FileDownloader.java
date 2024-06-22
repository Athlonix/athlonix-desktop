package athlonix;

import athlonix.controllers.DownloadViewControlerr;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FileDownloader {
    public void downloadFile(String route, String folderName) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/download-view.fxml"));
        Parent root = fxmlLoader.load();
        DownloadViewControlerr downloadViewControlerr = fxmlLoader.getController();

        Scene scene = SceneLoader.GetScene(root);
        Stage stage = new Stage();
        stage.setTitle("download");
        stage.setScene(scene);
        stage.show();

        Runnable updatethread = new Runnable() {
            public void run() {

                try {
                    URL url = null;
                    try {
                        url = new URL(route);
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    }
                    HttpURLConnection httpConnection = (HttpURLConnection) (url.openConnection());
                    long completeFileSize = httpConnection.getContentLength();

                    String disposition = httpConnection.getHeaderField("Content-Disposition");
                    String fileName = "";

                    if (disposition != null) {
                        int index = disposition.indexOf("File-Name=");
                        if (index > 0) {
                            fileName = disposition.substring(index + 10);
                            downloadViewControlerr.setTextValue(fileName);
                        }
                    }



                    java.io.BufferedInputStream in = new java.io.BufferedInputStream(httpConnection.getInputStream());
                    java.io.FileOutputStream fos = new java.io.FileOutputStream(
                            "app/" + folderName + "/" + fileName);
                    java.io.BufferedOutputStream bout = new BufferedOutputStream(
                            fos, 1024);
                    byte[] data = new byte[1024];
                    long downloadedFileSize = 0;
                    int x = 0;
                    while ((x = in.read(data, 0, 1024)) >= 0) {
                        downloadedFileSize += x;

                        // calculate progress
                        final double currentProgress = (((double)downloadedFileSize) / ((double)completeFileSize));

                        // update progress bar
                        downloadViewControlerr.setProgressBarValue(currentProgress);

                        bout.write(data, 0, x);
                    }
                    bout.close();
                    in.close();
                } catch (IOException e) {
                    System.out.println("error while downloading file");
                }

            }
        };
        new Thread(updatethread).start();
    }
}
