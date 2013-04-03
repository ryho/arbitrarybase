import java.util.ArrayList;
// input a char array of the typed in problem
// return a double of the answer

/**
 * Features:
 * four function calculator [*,/,+,-]
 * set input base to arbitrary base (2-36) ["input base"] [*]
 * set output base to arbitrary base (2-36) ["output base"] [*]
 * dynamic input in most common bases, regardless of input base
 *   	base 2 [0b*.*]
 * 		base 8 [0*.*]
 * 		base 10 [0b*.*]
 *		base 16 [0x*.*] or [0h*.*]
 * allows use parenthesis
 * 5(4) returns same thing as 5*(4)
 * follows order of operations PMDAS
 * input and out numbers with a mantissa
 * 64 bit double precision
 * automatically adjusts max number of digits to print based on given base
 * 
 * 
*/

/*
 * error=1;//digit outside of what input base allows
 * error=2;//looking for a number, didn't find valid number
 * error=3;//looking for a number, didn't find valid number
*/
class Calculator {
	private int globalIndex = 0;
	private int length = 0;
	private double answer = 0;
	private char[] input;
	private int error = 0;
	private int base = 10;
	private int fracLength=0;
	private static int[] mem = new int[11];
	
	public Calculator(){
	}
	public void setBase(int base){
		this.base=base;
	}
	public int getBase(){
		return base;
	}

	// gives the calculator the char array the user typed in
	public void setInput(char[] input){
		this.input=input;
		length = input.length;
	}

	//returns the last calculated answer
	public double getAnswer(){
		return answer;
	}

	//calculates the answer to the input char array
	public int enter () {
		answer=0;
		error=0;
		globalIndex=0;
		ArrayList<Double> num = new ArrayList<Double>();
		ArrayList<Integer> ops = new ArrayList<Integer>();
		// parse into list of numbers and operations
		while(error==0 && globalIndex<length){
			num.add(getNumber());//get number
			if(error==0 && globalIndex<length){// get operation
				ops.add(getOperation());
				globalIndex++;
			}else break;
		}
		for(int i =0;i<ops.size();i++){
			if(ops.get(i)==1){
				num.set(i+1,num.get(i)*num.get(i+1));
				num.set(i,0.0);
			}
			else if(ops.get(i)==2){
				num.set(i+1,num.get(i)/num.get(i+1));
				num.set(i,0.0);
			}
			else if(ops.get(i)==4){
				num.set(i+1,num.get(i+1)*-1);
			}
		}
		for(int i=0;i<num.size();i++){
			answer+=num.get(i);
		}
		return error;
	}

	//if the next character should be an operation, this funtion finds what it is
	private int getOperation(){
		if(input[globalIndex]=='*'){
			return 1;
		}
		else if(input[globalIndex]=='('){
			globalIndex--;
			return 1;
		}
		else if(input[globalIndex]=='/'){
			return 2;
		}
		else if(input[globalIndex]=='+'){
			return 3;
		}
		else if(input[globalIndex]=='-'){
			return 4;
		}
		error=2;//invalid operation
		return 0;
	}

	//if the next characters should be numbers, this function gets them
	private double getNumber(){
		// If parenthesis, then get value of all in parenthesis
		if(input[globalIndex]=='('){
			int x=0,i=1;
			// find length till closing parenthesis
			while(i>0){
				x++;
				if(input[globalIndex+x]=='('){
					i++;
				}
				else if (input[globalIndex+x]==')'){
					i--;
				}
			}
			// recursivelyish get value in parenthesis
			char[] output = new char[x-1];
			for(i=0;i<x-1;i++){
				output[i]=input[globalIndex+i+1];
			}
			Calculator myCalc = new Calculator();
			myCalc.setInput(output);
			myCalc.enter();
			globalIndex+=x+1;
			return myCalc.getAnswer();
		}//if .123
		else if (input[globalIndex]=='.'){
			globalIndex++;
			return getFrac();
		}//if 0x123, 0b101, 0123, 0.123 or simply 0
		else if (input[globalIndex]=='0'){
			globalIndex++;
			if(globalIndex<length){
				double tempAns;
				int tempBase=base;
				if(input[globalIndex]>'0' && input[globalIndex]<='7'){
					//globalIndex++;
					base=8;
					tempAns=getWhole(0);
					base=tempBase;
					return tempAns;
				} else if(input[globalIndex]=='x'||input[globalIndex]=='h'){
					globalIndex++;
					base=16;
					tempAns=getWhole(0);
					base=tempBase;
					return tempAns;
				} else if(input[globalIndex]=='b'){
					globalIndex++;
					base=2;
					tempAns=getWhole(0);
					base=tempBase;
					return tempAns;
				} else if(input[globalIndex]=='d'){
					globalIndex++;
					base=10;
					tempAns=getWhole(0);
					base=tempBase;
					return tempAns;
				}else if(input[globalIndex]=='.'){
					globalIndex++;
					return getFrac();
				}
				return 0;//decimal zero
			}
			return 0;// end of file, decimal zero
		}// if decimal number
		else if(input[globalIndex]>'0' && input[globalIndex]<='9'||
			input[globalIndex]>='a' && input[globalIndex]<='z'||
			input[globalIndex]>='A' && input[globalIndex]<='Z'){
			return getWhole(0);
		}//looking for number, didn't get one. give error
		error=3;//looking for a number, didn't find valid number
		return 0;
	}
	//get a number in arbitrary base
	private double getWhole(int prev){
		// right here globalIndex should be pointing at the char after x
		int num=-1;
		char curChar;
		if(globalIndex<length){
			curChar = input[globalIndex++];
			if(curChar>='0' && curChar<='9'){
				// 0 - 9
				num=curChar-'0';
			}
			else if(curChar>='a' && curChar<='z'){
				// a - f (lower case)
				num=curChar-'a'+10;
			}
			else if(curChar>='A' && curChar<='Z'){
				// A - F (upper case)
				num=curChar-'A'+10;
			}else globalIndex--;
			if(num>=base){
				error=1;//number larger than base allows
			}else if(num!=-1){
				return getWhole(prev*base+num);
			}else if(curChar=='.'){
				globalIndex++;
				fracLength=0;
				return prev+getFrac();
			}
		}
		//FYI the very last call returns the answer all the way up
		return prev;
	}

	//gets the fractional portion of arbitrary base number assuming it is after a decimal point
	private double getFrac(){
		int num=-1;
		char curChar;
		if(globalIndex<length){
			curChar = input[globalIndex++];
			if(curChar>='0' && curChar <='9'){
				// 0-9
				num=curChar-'0';
			}
			else if(curChar>='a' && curChar<='z'){
				// a - f (lower case)
				num=curChar-'a'+10;
			}
			else if(curChar>='A' && curChar<='Z'){
				// A - F (upper case)
				num=curChar-'A'+10;
			}
			else globalIndex--;
			if(num>=base){
				error=1;//digit outside of what input base allows
			}else if(num!=-1){
				fracLength+=1;
				return (getFrac()+num)/base;
			}
		}
		return 0;
	}
}
