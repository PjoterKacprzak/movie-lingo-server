package com.example.movielingo.controller;

import java.io.*;
import java.net.URL;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

public class GZIPFile {
    private final static Logger logger = Logger.getLogger(GZIPFile.class.getName());

    void decompress(String url,String gzipFile, String newFile) {
        try {
            File yourFile = new File("test.gz");
            yourFile.createNewFile();

                    BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());

                    FileOutputStream fileOutputStream = new FileOutputStream(yourFile, false);
                    // if f


                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
                System.out.println(fileOutputStream);

                FileInputStream fis = new FileInputStream("test.gz");
                GZIPInputStream gis = new GZIPInputStream(fis);
            File txtFile = new File("srtFile.txt");
            System.out.println(txtFile.createNewFile());
                FileOutputStream fos = new FileOutputStream(newFile);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = gis.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                //close resources
                fos.close();
                gis.close();
            System.out.println(fos);
            } catch (IOException e) {
                logger.info("IOEXception");
            }

    }


    void downloadFromLink(String fileName, String url) throws IOException {
//     try {
//        // FileOutputStream fileOutputStream = new FileOutputStream("src/test.txt");
//         File file = new File("test1.txt");
//         //file.getParentFile().mkdirs();
//         boolean success = file.createNewFile();
//         System.out.println(success);
//     }catch (FileNotFoundException e)
//     {
//         logger.info("not found");
//     }
//     catch (IOException e)
//     {
//         logger.info("IO Exception");
//     }
//
        File yourFile = new File("test.gz");
        yourFile.createNewFile();
        try (
                BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());

             FileOutputStream fileOutputStream = new FileOutputStream(yourFile,false)
                // if f
                ) {

            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println(fileOutputStream);
        } catch (IOException e) {
            // handle exception
        }

    }
}
