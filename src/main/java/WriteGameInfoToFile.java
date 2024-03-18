import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteGameInfoToFile {

    private File file;

    public WriteGameInfoToFile() {
        this.file = new File("src/main/resources/GameInfo.txt");
    }

    public synchronized void writeToFile(String content) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try(FileWriter fw = new FileWriter(file.getAbsoluteFile(), true)) {
            fw.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
