import java.util.Scanner;
import java.util.InputMismatchException;
class Arbitrary {
  private static boolean keepRunning = true;
	private static Scanner console = new Scanner(System.in);
	private static int base=10;
	private static double ans = 9999999;
	private static int errorCode;
	private static Calculator myCalc = new Calculator();
	private static int maxDigits=16;
	private static int printedDigits=0;

	public static void main(String args[]){
		System.out.println("\nWelcome to calculator\n");
		printSettings();
		System.out.println("\nType \"quit\",\"settings\", \"input base\", \"output base\", or enter your query.\n");
		System.out.println("Note: possible bugs\n");
		String inputStr="";

		//control loop
		while (keepRunning){
			// get input
			inputStr = console.nextLine().replaceAll("\\s","");//"removes all whitespaces and non visible characters such as tab, \n" -- some guy on a forum
			//quit if desired
			if (inputStr.compareToIgnoreCase("quit")==0){
				System.out.println("\nGoodbye!\n");
				keepRunning=false;
			}//print settings
			else if (inputStr.compareToIgnoreCase("settings")==0){
				System.out.print("\nCurrent:\n");
				printSettings();
				System.out.print("\n");
			}//change base if desired
			else if (inputStr.compareToIgnoreCase("inputbase")==0){
				System.out.print("\nCurrent:\n");
				myCalc.setBase(changeBase());
				printSettings();
				System.out.print("\n");
				inputStr = console.nextLine();// hackish, but whatevs.
			}
			else if (inputStr.compareToIgnoreCase("outputbase")==0){
				System.out.print("\nCurrent:\n");
				base=changeBase();
				printSettings();
				System.out.print("\n");
				maxDigits=(int)Math.round(53*Math.log(2)/Math.log(base));//beastly
				inputStr = console.nextLine();// hackish, but whatevs.
			}
			else if(inputStr.compareToIgnoreCase("changemode")==0){
				//int vs double
			}
			else {//otherwise calculate answer
				myCalc.setInput(inputStr.toCharArray());
				errorCode=myCalc.enter();
				if(errorCode!=0){
					System.out.println("Input Error: "+errorCode);
					switch(errorCode){
					case 1:System.out.println("digit outside of what input base allows\n");
						break;
					case 2:System.out.println("looking for a number, didn't find valid number\n");
						break;
					case 3:System.out.println("looking for a number, didn't find valid number\n");
						break;
					}
				}else {
					ans = myCalc.getAnswer();
					printAnswer();
				}
			}
		}
	}

	//Brings up menu to change the base for output
	private static int changeBase(){
		int temp;
		printSettings();
		System.out.println("Base Options:\n"+
			"2. Binary\n"+
			"8. Octal\n"+
			"10. Decimal (choose this if you don't know)\n"+
			"16. Hex\n"+
			"or any base 2 to 36\n");
		System.out.print("Enter desired base: ");
		try {temp = console.nextInt();}
		catch(InputMismatchException e) {//if input wasn't an integer
			System.out.println("Not gonna work, using 10");
			temp=10;
		}
		if (temp>36||temp<2){// base out of bounds
			System.out.println("Not gonna work, using 10");
			temp=10;
		}
		return temp;
	}

	//Prints the base currently selected for output
	private static void printSettings(){
		System.out.print("Input Base: ");
		printBase(myCalc.getBase());
		System.out.print("Output Base: ");
		printBase(base);
	}
	private static void printBase(int input){
		switch(input){//cheap party trick
			case 2:System.out.print("Binary");
				break;
			case 3:System.out.print("Ternary");
				break;
			case 4:System.out.print("Quaternary");
				break;
			case 5:System.out.print("Quinary");
				break;
			case 6:System.out.print("Senary");
				break;
			case 7:System.out.print("Septenary");
				break;
			case 8:System.out.print("Octal");
				break;
			case 9:System.out.print("Nonary");
				break;
			case 10:System.out.print("Decimal");
				break;
			case 11:System.out.print("Undecimal");
				break;
			case 12:System.out.print("Duodecimal");
				break;
			case 13:System.out.print("Tridecimal");
				break;
			case 14:System.out.print("Tetradecimal");
				break;
			case 15:System.out.print("Pentadecimal");
				break;
			case 16:System.out.print("Hexadecimal");
				break;
			case 17:System.out.print("Septendecimal");
				break;
			case 18:System.out.print("Octodecimal");
				break;
			case 19:System.out.print("Nonadecimal");
				break;
			case 20:System.out.print("Vigesimal");
				break;
			case 21:System.out.print("Unovigesimal");
				break;
			case 22:System.out.print("Duovigesimal");
				break;
			case 23:System.out.print("Triovigesimal");
				break;
			case 24:System.out.print("Quadrovigesimal");
				break;
			case 25:System.out.print("Pentavigesimal");
				break;
			case 26:System.out.print("Hexavigesimal");
				break;
			case 27:System.out.print("Heptovigesimal");
				break;
			case 28:System.out.print("Octovigesimal");
				break;
			case 29:System.out.print("Novovigesimal");
				break;
			case 30:System.out.print("Trigesimal");
				break;
			case 31:System.out.print("Unotrigesimal");
				break;
			case 32:System.out.print("Duotrigesimal");
				break;
			case 33:System.out.print("Triotrigesimal");
				break;
			case 34:System.out.print("Quadrotrigesimal");
				break;
			case 35:System.out.print("Pentatrigesimal");
				break;
			case 36:System.out.print("Hexatrigesimal");
				break;
			}
			System.out.println(" - Base "+input);
	}

	//prints out the answer in arbitrary base, using class variables base, ans
	private static void printAnswer(){
		//separate whole from fractional portion
		int whole=(int)Math.floor(ans);
		double fraction = ans-whole;
		System.out.print("ans = ");

		//if base 2, 8, 16 print prefix
		if(base==2)
			System.out.print("0b");
		else if(base==8)
			System.out.print("0");
		else if(base==16)
			System.out.print("0x");

		//recursively print the whole portion of answer
		printWhole(whole);

		// print the fractional portion if there is one
		if(fraction>0){
			System.out.print(".");
			// algorithm: fraction = fraction*base, print floor(fraction)
			while(fraction>0&&printedDigits<maxDigits){// while there is still fraction and while digits printed is less than max
				fraction*=base;
				whole=(int)Math.floor(fraction);
				if(whole<10)
					System.out.print(whole);
				else
					System.out.print((char)('A'+whole-10));
				fraction-=whole;
				printedDigits++;
			}
		}
		printedDigits=0;
		//if non-standard base, print base #
		if(base!=10 && base!=2 && base!=16 && base!=8)
			System.out.print(" base "+base);

		System.out.println("\n");
	}
	//recursively prints a whole number
	private static void printWhole(int whole){
		int num=whole%base;
		if(whole!=0){
			printedDigits++;
			printWhole(whole/base);
			if(num<10)
				System.out.print(num);
			else
				System.out.print((char)('A'+num-10));
		}
	}
}
