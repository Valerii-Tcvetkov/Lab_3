import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private List<Section> sections = new ArrayList<Section>();
    private Pattern _section = Pattern.compile("^\\[([0-9a-zA-z_]+)\\]\\s*");
    private Pattern _keyValue = Pattern.compile("^([0-9a-zA-Z_]+) = ([0-9a-zA-Z/\\.]+).*");
    private Pattern _comments = Pattern.compile("^;.*");

    private void newSection(String string) {
        this.sections.add(new Section(string));
    }

    private void newParameter(String key, String value) {
        try {
            this.sections.get(this.sections.size() - 1).newParameter(key, value);
        } catch (NullPointerException ex) {
            System.out.println("Wrong file format");
        }
    }

    public Parser parse(String path) {
        Parser parser = new Parser();
        BufferedReader file = this.openFile(path);
        String line;
        try {
            while ((line = file.readLine()) != null) {
                Matcher sectionmatcher = _section.matcher(line);
                Matcher keyvaluematcher = _keyValue.matcher(line);
                Matcher comments = _comments.matcher(line);
                if (sectionmatcher.find()) {
                    parser.newSection(sectionmatcher.group(1));
                } else if (keyvaluematcher.find()) {
                    parser.newParameter(keyvaluematcher.group(1), keyvaluematcher.group(2));
                } else if (!comments.find()) {
                    System.out.println("Wrong format of file");
                    return null;
                }
            }
            this.sections = parser.sections;
            return this;
        } catch (IOException ex) {
            return null;
        }
    }

    private BufferedReader openFile(String str) {
        try {
            BufferedReader file = new BufferedReader(new FileReader(str));
            return file;
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public void printData() {
        for (Section i : this.sections) {
            System.out.println(i);
            for (Parameter j : i.parameters) {
                System.out.println(j);
            }
        }
    }

    public Object find(String section, String parameter) {
        for (Section i : this.sections) {
            if (i.name.equals(section)) {
                return i.find(parameter);
            }
        }
        return "Section not found";
    }
}