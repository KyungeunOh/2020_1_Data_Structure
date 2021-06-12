import java.util.*;

public class Calculator {
	public Stack<String> stack;
	String Instr,Poststr,s;
	
	public Calculator(String Instr) {
		stack = new Stack<String>();
		this.Instr = Instr;
	}
	
	public int change(String s) {
		switch(s) {
		case "(": case ")": return 0;
		case "+": case "-": return 1;
		case "*": case "/": return 2;
		default: return 3;
		}
	}
	
	public String InfixToPostfix(String input) {
		StringTokenizer tok = new StringTokenizer(Instr,"()+-*/",true);
		Poststr = new String();
		int n1=0, n2=0;
		
		while(tok.hasMoreTokens()) {
			s = tok.nextToken();
			if(s.equals("(")) {
				stack.push(s);
				n1++;
			}
			else if(s.equals(")")){
				n2++;
				if(n1!=n2) {
					System.out.println("수식 오류");
					System.exit(0);
				}
				while(!stack.peek().equals("(")) 
					Poststr += stack.pop()+" ";
				stack.pop();
			}else if(s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/")) {
				while(!stack.isEmpty()&&change(s)<=change(stack.peek()))
					Poststr += stack.pop()+" ";
				stack.push(s);
			}else if(s.equals(" ")) continue;
			else Poststr += s+" ";
		}
		while(!stack.isEmpty()) Poststr += stack.pop()+" ";
		return Poststr;
	}
	
	public double Prove() {
		double n1,n2;
		StringTokenizer tok = new StringTokenizer(Poststr," ");
		while(tok.hasMoreTokens()) {
			s = tok.nextToken();
			if(s.equals("+")||s.equals("-")||s.equals("*")||s.equals("/")) {
				n2 = Double.parseDouble(stack.pop());
				if(stack.peek()==null) n1=0.0;
				else n1 = Double.parseDouble(stack.pop());
				switch(s) {
				case "+": stack.push(String.valueOf(n1+n2));break;
				case "-": stack.push(String.valueOf(n1-n2));break;
				case "*": stack.push(String.valueOf(n1*n2));break;
				case "/": stack.push(String.valueOf(n1/n2));break;
				}
			}else stack.push(s);
		}return Double.parseDouble(stack.pop());
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String check;
		System.out.println("======= MyCalculator =======\nMyCalculator 사용을 환영합니다.");
		while(true) {
			System.out.print("\nInfix로 수식을 입력하시오. <enter>\n>");
			String input = sc.nextLine();
			
			Calculator cal = new Calculator(input);
			System.out.println(">Postfix로 변환 : "+cal.InfixToPostfix(input));
			System.out.print("\n계산을 시작할까요? (Y/N)\n>");
			check = sc.next();
			if(check.equals("Y"))
				System.out.println(">계산 값 : "+cal.Prove());
			
			System.out.print("\n계속하시겠습니까?\n>");
			check = sc.next();
			if(check.equals("N")) break;
			sc.nextLine();
		}
		System.out.println("\n사용해주셔서 감사합니다.\n프로그램을 종료합니다.\n");
		sc.close();
	}
}