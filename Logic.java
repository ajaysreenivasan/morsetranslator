import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Logic {
	public static String conversion(String input) {
		Map<String, String> referenceTable = new HashMap<String, String>();

		referenceTable.put("A", ".-");
		referenceTable.put("B", "-...");
		referenceTable.put("C", "-.-.");
		referenceTable.put("D", "-..");
		referenceTable.put("E", ".");
		referenceTable.put("F", "..-.");
		referenceTable.put("G", "--.");
		referenceTable.put("H", "....");
		referenceTable.put("I", "..");
		referenceTable.put("J", ".---");
		referenceTable.put("K", "-.-");
		referenceTable.put("L", ".-..");
		referenceTable.put("M", "--");
		referenceTable.put("N", "-.");
		referenceTable.put("O", "---");
		referenceTable.put("P", ".--.");
		referenceTable.put("Q", "--.-");
		referenceTable.put("R", ".-.");
		referenceTable.put("S", "...");
		referenceTable.put("T", "-");
		referenceTable.put("U", "..-");
		referenceTable.put("V", "...-");
		referenceTable.put("W", ".--");
		referenceTable.put("X", "-..-");
		referenceTable.put("Y", "-.--");
		referenceTable.put("Z", "--..");
		referenceTable.put(" ", "___");
		referenceTable.put(".", "@");
		referenceTable.put(",", "@");
		referenceTable.put("!", "@");
		referenceTable.put("?", "@");
		referenceTable.put("&", "@");
		referenceTable.put("#", "@");
		// . = 1
		// - = 3
		// end of letter = "_"
		// end of word = "_ _ _"

		// BufferedReader bufferedReader = new BufferedReader(new
		// FileReader("input.txt"));

		String convertedInput;

		StringBuilder sb = new StringBuilder();

		// doesn't this use unnecessary memory?
		// for (char c : input.toCharArray()) {
		// System.out.println(c);
		// System.out.print(referenceTable.get(String.valueOf(c)));
		// }

		for (int i = 0; i < input.length(); i++) {
			sb.append(referenceTable.get(String.valueOf(input.charAt(i))));
			sb.append("_");
		}
		convertedInput = sb.toString();

		return convertedInput;

		// System.out.println(referenceTable.get("a"));
	}
}
