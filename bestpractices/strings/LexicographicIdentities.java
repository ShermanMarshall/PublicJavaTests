package bestpractices.strings;

public class LexicographicIdentities {

	public static boolean nothingIsShorterThanEmptyString() {
		String shortest = "";

		String[] alternatives = { "", "ABCDEFG", "abcdefg", "small", "12345", "" };
	
		boolean isTrue = true;
		for (String s : alternatives) {
			System.out.println(isTrue = (shortest.compareTo(s) <= 0));
		}
		return isTrue;
	}

	public static void main(String...args) {
		nothingIsShorterThanEmptyString();
	}

}
