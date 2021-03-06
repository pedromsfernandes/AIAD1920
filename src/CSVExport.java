import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVExport {

    private static File file;
    private static String[] columns;
    private static Path path;

    public static void init(String fileName, String[] cols) {
        path = Paths.get(fileName);
        file = new File(fileName);
        columns = cols;
    }

    public static String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }

    public static void writeLine(String[] line) {

        try {
            if (file.length() == 0) {
                Files.write(path, Arrays.asList(convertToCSV(columns)), StandardCharsets.UTF_8,
                        Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
            }
            Files.write(path, Arrays.asList(convertToCSV(line)), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String convertToCSV(String[] data) {
        return Stream.of(data).map(CSVExport::escapeSpecialCharacters).collect(Collectors.joining(","));
    }
}