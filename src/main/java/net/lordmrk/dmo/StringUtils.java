package net.lordmrk.dmo;

public class StringUtils {

    public static String capitalizeEveryWord(String input) {
        StringBuilder output = new StringBuilder();

        for(String word : input.split(" ")) {
            output.append(" ").append(capitalizeFirstLetter(word));
        }

        return output.substring(1);
    }

    public static String capitalizeFirstLetter(String input) {
        if(input.length() == 0) {
            return input;
        }

        if(input.length() == 1) {
            return input.toUpperCase();
        }

        return ("" + input.charAt(0)).toUpperCase() + input.substring(1);
    }
}
