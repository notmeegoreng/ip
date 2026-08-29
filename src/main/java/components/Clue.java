package components;

public class Clue {
    private static final Storage storage = new Storage("./data.txt");

    static void main(String[] args) {
        Parser parser = new Parser(storage.load());
        try (Ui ui = new Ui(parser, System.in, System.out)) {
            ui.listen();
        } finally {
            if (!storage.save(parser.getTasks())) {
                System.out.println("An error occurred when trying to save!");
            }
        }
    }
}
