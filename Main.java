public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.parse("src/INI.txt");
        Object find = parser.find("DEBUG", "PlentySockMaxQSize");
        System.out.print(find + " " + find.getClass());
    }
}
