package athlonix.athlonixlauncher;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipper {



    public static void unzip(String zipFile, String outputPath) {
        if (outputPath == null)
            outputPath = "";
        else
            outputPath += File.separator;

        // 1.0 Create output directory
        File outputDirectory = new File(outputPath);

        if (outputDirectory.exists())
            outputDirectory.delete();

        outputDirectory.mkdirs();

        // 2.0 Unzip (create folders & copy files)
        try {
            // 2.1 Get zip input stream
            ZipInputStream zip = new ZipInputStream(new FileInputStream(zipFile));

            ZipEntry entry = null;
            int len;
            byte[] buffer = new byte[1024];

            // 2.2 Go over each entry "file/folder" in zip file
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    // Create the directory
                    File dir = new File(outputPath + entry.getName());
                    dir.mkdirs();
                } else {
                    System.out.println("-" + entry.getName());

                    // Create a new file
                    File file = new File(outputPath + entry.getName());

                    // Create file parent directory if it does not exist
                    if (!new File(file.getParent()).exists())
                        new File(file.getParent()).mkdirs();

                    // Get new file output stream
                    FileOutputStream fos = new FileOutputStream(file);

                    // Copy bytes
                    while ((len = zip.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }

                    fos.close();
                }
            }
            zip.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
