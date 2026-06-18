package exceptions;

import org.testng.SkipException;

/**
 * Custom exception to gracefully skip tests that are tied to known, unresolved bugs.
 * Extends TestNG's SkipException so it doesn't fail the build.
 */
public class KnownBugException extends SkipException {

    // ANSI escape codes for styling the console output
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_BLACK_TEXT = "\u001B[30m";
    private static final String ANSI_CYAN_TEXT = "\u001B[36m";
    private static final String ANSI_BOLD = "\u001B[1m";
    private static final String ANSI_RESET = "\u001B[0m";

    /**
     * Creates a highly visible, color-coded exception message.
     * 
     * @param bugId    The tracking ID of the bug (e.g., JIRA-1234)
     * @param bugTitle A brief description of the bug
     */
    public KnownBugException(String bugId, String bugTitle) {
        super(
            "\n" + 
            ANSI_YELLOW_BACKGROUND + ANSI_BLACK_TEXT + ANSI_BOLD + 
            " ⚠️ SKIPPED DUE TO KNOWN BUG " + ANSI_RESET + " \n" +
            ANSI_CYAN_TEXT + ANSI_BOLD + "ID: " + ANSI_RESET + bugId + "\n" +
            ANSI_CYAN_TEXT + ANSI_BOLD + "Title: " + ANSI_RESET + bugTitle + "\n"
        );
    }
}
