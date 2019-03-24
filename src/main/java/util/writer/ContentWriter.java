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

    /**
     * Writes content from given input stream to the file with given path.
     * @param inputStream stream with information to write.
     * @param path path to the file to write information.
     * @throws IOException if error occurred during the process of writing
     * information.
     */
    public void writeContent(InputStream inputStream, String path) throws IOException {
        OutputStream outputStream = new FileOutputStream(path);
        outputStream.write(inputStream.readAllBytes());
    }
}
