package com.covid19.data.utils;

import com.covid19.data.dto.FileUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownload {

    private static File tmpFile = null;
    private static final Logger log = LoggerFactory.getLogger(FileDownload.class);

    public static File download(String fileUrl) {

        BufferedInputStream in = null;
        BufferedOutputStream bout = null;
        byte[] buffer = new byte[1024];

        try {
            tmpFile = File.createTempFile("covid-data", ".tmp");
            URL url = new URL(fileUrl);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(http.getInputStream());
            FileOutputStream fos = new FileOutputStream(tmpFile);
            bout = new BufferedOutputStream(fos, 1024);

            int read;
            while((read = in.read(buffer,0,1024))>=0)
                bout.write(buffer,0,read);

        } catch (IOException e) {
            log.error("Can't download file with url={}", fileUrl, e);
        } finally {
            try {
                if (bout != null) {
                    bout.close();
                    in.close();
                }
            } catch (IOException e) {
                log.error("Can't close stream", e);
            }
        }

        log.info("download complete");
        tmpFile.deleteOnExit();

        return tmpFile;
    }
}
