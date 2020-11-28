package lt.verbus.util;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Properties;

public class PropertiesReader {

    private Integer maxYear;
    private Integer minyear;

    public PropertiesReader(){
        maxYear = null;
        try (InputStream input = PropertiesReader.class.getClassLoader().getResourceAsStream("quiz.properties")) {
                Properties properties = new Properties();
                properties.load(input);
                if (input == null) {
                    System.out.println("Sorry, unable to find quiz.properties");
                }
                String maxYearProperty = properties.getProperty("max_year");
                String minYearProperty = properties.getProperty("min_year");
                maxYear = Integer.parseInt(maxYearProperty);
                minyear = minYearProperty.equals("current") ? LocalDate.now().getYear() : Integer.parseInt(minYearProperty);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getMaxYear() {
        return maxYear;
    }

    public Integer getMinyear() {
        return minyear;
    }
}