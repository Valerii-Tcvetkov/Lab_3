public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();

        parser.parse("src/INI.txt").printData();
        System.out.println(parser.find("COMMON", "StatisterTimeMs").getClass());

    }
}