package net.felsstudio.fels.Modules.fels.net.floader;

import net.felsstudio.fels.Console;
import net.felsstudio.fels.Modules.Module;
import net.felsstudio.fels.lib.Arguments;
import net.felsstudio.fels.lib.MapValue;
import net.felsstudio.fels.lib.*;
import net.felsstudio.fels.lib.Variables;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class floader implements Module {
    @Override
    public void init() {
        final MapValue map = new MapValue(10);

        map.set("download",args ->{
            Value v = downloader(args);
            return v;
        });

        Variables.define("floader",map);
    }

    private Value downloader(Value[] args) throws IOException, InterruptedException {
        Arguments.checkRange(2, 4, args.length);
        final String downloadUrl = args[0].asString();
        final String filePath = args[1].asString();
        final Function progressCallback;
        final int contentLength;
        if ( (args.length >= 3) && (args[2].type() == Types.FUNCTION) ) {
            progressCallback = ((FunctionValue) args[2]).getValue();
            // For showing progress we need to get content length
            contentLength = getContentLength(downloadUrl);
        } else {
            progressCallback = null;
            contentLength = -1;
        }
        final int bufferSize = (args.length == 4) ? Math.max(1024, args[3].asInt()) : 16384;

        final NumberValue contentLengthBoxed = NumberValue.of(contentLength);
        final boolean calculateProgressEnabled = contentLength > 0 && progressCallback != null;
        if (calculateProgressEnabled) {
            progressCallback.execute(
                    NumberValue.ZERO /*%*/,
                    NumberValue.ZERO /*bytes downloaded*/,
                    contentLengthBoxed /*file size*/);
        }

        try (InputStream is = new URL(downloadUrl).openStream();
             OutputStream os = new FileOutputStream(Console.fileInstance(filePath))) {
            int downloaded = 0;
            final byte[] buffer = new byte[bufferSize];
            int read;
            while ((read = is.read(buffer, 0, bufferSize)) != -1) {
                os.write(buffer, 0, read);
                downloaded += read;
                if (calculateProgressEnabled) {
                    final int percent = (int) (downloaded / ((double) contentLength) * 100.0);
                    progressCallback.execute(
                            NumberValue.of(percent),
                            NumberValue.of(downloaded),
                            contentLengthBoxed);
                }
            }
        } catch (IOException ioe) {
            return NumberValue.ZERO;
        } finally {
            if (progressCallback != null) {
                progressCallback.execute(NumberValue.of(100), contentLengthBoxed, contentLengthBoxed);
            }
        }
        return NumberValue.ONE;
    }

    private static int getContentLength(String url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            return connection.getContentLength();
        } catch (IOException ioe) {
            return -1;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
