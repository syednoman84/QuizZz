package jorge.rv.quizzz.csvloader;

import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HelperCSV {
  public static String TYPE = "text/csv";
  static final String[] HEADERS = { "question", "answer" };

  public static boolean hasCSVFormat(MultipartFile file) {

    if (!TYPE.equals(file.getContentType())) {
      return false;
    }

    return true;
  }

  public static List<EntityCSV> csvToEntity(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.newFormat('|').withFirstRecordAsHeader().withHeader(HEADERS).withIgnoreHeaderCase().withTrim());) {

      List<EntityCSV> questionsList = new ArrayList<EntityCSV>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
        EntityCSV question = new EntityCSV(
              csvRecord.get(HEADERS[0]),
              csvRecord.get(HEADERS[1])
            );

        questionsList.add(question);
      }

      return questionsList;

    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }

  public static ByteArrayInputStream entityToCSV(List<EntityCSV> questions) {
    final CSVFormat format = CSVFormat.newFormat('|').withQuoteMode(QuoteMode.MINIMAL);

    try (ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
      csvPrinter.printRecord("question", "answer");
      csvPrinter.printRecord(System.getProperty("line.separator"));
      for (EntityCSV question : questions) {
        csvPrinter.printRecord(question.getQuestion(), question.getAnswer());
        csvPrinter.printRecord(System.getProperty("line.separator"));
      }

      csvPrinter.flush();
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
    }
  }

}
