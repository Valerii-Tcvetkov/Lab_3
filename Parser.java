import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    public HashMap<String, HashMap<String, Object>> data = new HashMap<String, HashMap<String, Object>>();
    private BufferedReader file;
    private Pattern _section = Pattern.compile("^\\[([0-9a-zA-z_]+)\\]\\s*");
    private Pattern _keyValue = Pattern.compile("^([0-9a-zA-Z_]+) = ([0-9a-zA-Z/\\.]+).*");
    private Pattern _comments = Pattern.compile("^;.*");

    Parser() {
    }

    private void openFile(String str) {
        try {
            file = new BufferedReader(new FileReader(str));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private Object parseValue(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException ex) {
            try {
                return Float.parseFloat(string);
            } catch (NumberFormatException ex1) {
                return string;
            }
        }
    }

    public Parser parse(String name) {
        this.openFile(name);
        HashMap<String, HashMap<String, Object>> hashMap = new HashMap<String, HashMap<String, Object>>();
        String line;
        String section = null;
        try {
            while ((line = this.file.readLine()) != null) {
                Matcher sectionmatcher = _section.matcher(line);
                Matcher keyvaluematcher = _keyValue.matcher(line);
                Matcher comments = _comments.matcher(line);
                if (sectionmatcher.find()) {
                    section = sectionmatcher.group(1);
                    hashMap.put(section, new HashMap<String, Object>());
                } else if (keyvaluematcher.find()) {
                    hashMap.get(section).put(keyvaluematcher.group(1), this.parseValue(keyvaluematcher.group(2)));
                } else if (!comments.find()){
                    System.out.println("Wrong type of file");
                    return null;
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.data = hashMap;
        return this;
    }

    public Object find(String section, String key) {
        if (this.data.containsKey(section)) {
            if (this.data.get(section).containsKey(key)) {
                return this.data.get(section).get(key);
            }
        }
        return null;
    }
}