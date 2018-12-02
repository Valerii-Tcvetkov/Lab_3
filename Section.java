import java.util.ArrayList;
import java.util.List;

public class Section {

    public String name;
    public List<Parameter> parameters = new ArrayList<Parameter>();

    Section(String name) {
        this.name = name;
    }

    public void newParameter(String key, String value) {
        this.parameters.add(new Parameter(key, value));
    }

    public Object find(String parameter) {
        for (Parameter i : this.parameters) {
            if (i.key.equals(parameter)) return i.value;
        }
        return "Parameter not found";
    }

    @Override
    public String toString() {
        return this.name;
    }
}
