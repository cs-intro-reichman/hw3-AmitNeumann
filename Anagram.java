import java.util.Random;

/** Functions for checking if a given string is an anagram. */
public class Anagram {
	public static void main(String args[]) {
		// Tests the isAnagram function.
		System.out.println(isAnagram("silent","listen"));  // true
		System.out.println(isAnagram("William Shakespeare","I am a weakish speller")); // true
		System.out.println(isAnagram("Madam Curie","Radium came")); // true
		System.out.println(isAnagram("Tom Marvolo Riddle","I am Lord Voldemort")); // true

		// Tests the preProcess function.
		System.out.println(preProcess("What? No way!!!"));
		System.out.println(isAnagram("m  o my", "YoMm "));
		// Tests the randomAnagram function.
		System.out.println("silent and " + randomAnagram("silent") + " are anagrams.");
		
		//Performs a stress test of randomAnagram 
		String str = "1234567";
		Boolean pass = true;
		//// 10 can be changed to much larger values, like 1000
		for (int i = 0; i < 10; i++) {
			String randomAnagram = randomAnagram(str);
			System.out.println(randomAnagram);
			pass = pass && isAnagram(str, randomAnagram);
			if (!pass) break;
		}
		System.out.println(pass ? "test passed" : "test Failed");
	}  

	// Returns true if the two given strings are anagrams, false otherwise.
	public static boolean isAnagram(String str1, String str2) {
		str1=preProcess(str1);
		str2=preProcess(str2);
		boolean isAnagram=true;
		int times1=0,times2=0; 
		char chr1;
		String maxStr,minStr;
		if(str1.length()>=str2.length()){
			maxStr=str1;
			minStr=str2;
		} else{
			maxStr=str2;
			minStr=str1;
		}
		for(int i=0;i<maxStr.length()&&isAnagram;i++){
			chr1=maxStr.charAt(i);
			if(chr1!=' '){
				if(minStr.indexOf(chr1)==-1) { // if the letter is not in the word
					isAnagram=false;
					break;
				} else{
					times1=countChr(maxStr, chr1); // time of letter(i) in str1
					times2=countChr(minStr, chr1); // time of letter(i) in str2
					if(times1!=times2) {
						isAnagram=false;
						break;
					}
				}
			 	}
			}
		
		return isAnagram;
	}
	// checks how many times a letter is in a word
	public static int countChr(String str1, char c) { 
		int count=0;
		for(int i=0;i<str1.length();i++) {
			if (str1.charAt(i)==c) {
				count++;
			}
		}
		return count;
	}

	// Returns a preprocessed version of the given string: all the letter characters are converted
	// to lower-case, and all the other characters are deleted, except for spaces, which are left
	// as is. For example, the string "What? No way!" becomes "whatnoway"
	public static String preProcess(String str) {
		String newStr="";
		char chr;
		str=str.toLowerCase();
		for(int i=0;i<str.length();i++) {
			chr=str.charAt(i);
			if(Character.isLetter(chr)||chr==' ') {
				newStr+=""+chr;
			}
		}
		return newStr;
	} 
	   
	// Returns a random anagram of the given string. The random anagram consists of the same
	// characters as the given string, re-arranged in a random order. 
	public static String randomAnagram(String str) {
		char[] strArr = str.toCharArray();
        int length = strArr.length;
        char[] result = new char[length]; // create anagram
        for (int i = 0; i < length; i++) {
        // Use Math.random() to generate a random index
        	int randomIndex = (int) (Math.random() * (length - i));
            result[i] = strArr[randomIndex]; // add character to nrew array
            for (int j = randomIndex; j < length - i - 1; j++) { // Shift remaining characters
                strArr[j] = strArr[j + 1];
            }
        }
        return new String(result);// Convert to String
    }

	}

