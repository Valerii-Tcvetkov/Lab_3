public class Parameter {

    public String key;
    public Object value;

    Parameter(String key, String value) {
        this.key = key;
        this.value = this.customize(value);
    }

    private Object customize(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException ex) {
            try {
                return Double.parseDouble(string);
            } catch (NumberFormatException ex1) {
                return string;
            }
        }
    }

    @Override
    public String toString() {
        return this.key + " = " + this.value;
    }
}
