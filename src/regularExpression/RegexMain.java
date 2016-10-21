package regularExpression;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class RegexMain {
	
    // utility function that reads given file and returns its entire contents as a single string
    private static String readFileToString (String filename) throws FileNotFoundException {
        final String END_OF_FILE = "\\z";
        Scanner input = new Scanner(new File(filename));
        input.useDelimiter(END_OF_FILE);
        String result = input.next();
        input.close();
        return result;
    }
    // given some text, prints results of parsing it using the given language
    private static void parseText (ProgramParser lang, String[] text) {
        for (String s : text) {
            if (s.trim().length() > 0) {
                System.out.println(String.format("%s : %s", s, lang.getSymbol(s)));
            }
        }
        System.out.println();
    }
    public static void main (String[] args) {
        final String WHITESPACE = "\\p{Space}";
        String[] examples = {
            "",
            "# foo",
            "foo #",
            "#",
            "fd",
            "FD",
            "forwardd",
            "equalp",
            "equal?",
            "equal??",
            "+",
            "SuM",
            "-",
            "*",
            "/",
            "%",
            "~",
            "+not",
            "not+",
            "++",
            "+*+",
            "or",
            "FOR",
            "allOrNothing",
            "all_or_nothing",
            "allOr_nothing?",
            "allOr?nothing_",
            ":allornothing",
            "PI",
            "90",
            "9.09",
            "9.0.0",
            "[",
            "]",
            "(",
            ")"
        };
        ProgramParser lang = new ProgramParser();
        // these are more specific, so add them first to ensure they are checked first
        lang.addPatterns("resources/languages/English");
        lang.addPatterns("resources/languages/Syntax");
//        try {
//            String userInput = "fd 50 rt 90 BACK :distance Left :angle";
//            String fileInput = readFileToString("/Users/yrsong/Documents/workspace/slogo_team18/data/examples/variables/square.logo");
            // try against different inputs
            parseText(lang, examples);
//            parseText(lang, userInput.split(WHITESPACE));
//            parseText(lang, fileInput.split(WHITESPACE));
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//            System.err.println(String.format("Could not load pattern file %s", e.getMessage()));
//        }
    }
}