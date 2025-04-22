public class TextFormatter {
    public static String toSentenceCase(String input){
        if (input == null || input.isEmpty()) return input;
        input = input.trim();
        return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
