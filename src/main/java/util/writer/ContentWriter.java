package util.writer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ContentWriter {

    private static final ContentWriter INSTANCE = new ContentWriter();

    public static ContentWriter getInstance() {
        return INSTANCE;
    }

    private ContentWriter() {}

    public void writeContent(InputStream inputStream, String path) throws IOException {
        OutputStream outputStream = new FileOutputStream(path);
        outputStream.write(inputStream.readAllBytes());
    }
}
