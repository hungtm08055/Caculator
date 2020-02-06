package caculate;

import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Trim;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {
    public String caculate;

    public Calculator()
    {

    }

    public Calculator(String caculate) {
        this.caculate = caculate;
    }

    public String getCaculate() {
        return caculate;
    }

    public void setCaculate(String caculate) {
        this.caculate = caculate;
    }

    public int Prec(char ch)
    {
        switch (ch)
        {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;

        }
        return -1;
    }

    public boolean ktraNgoacdon(String str)
    {
        if (str.isEmpty())
            return true;

        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < str.length(); i++)
        {
            char current = str.charAt(i);
            if (current == '(')
            {
                stack.push(current);
            }

            if (current == ')')
            {
                if (stack.isEmpty())
                    return false;

                char last = stack.peek();
                if (current == ')' && last == '(')
                    stack.pop();
                else
                    return false;
            }
        }
        return stack.isEmpty();
    }

    public double divideToZero(double a, double b) {
//        Calculator m = new Calculator();
        try
        {
            if (b == 0)
            {
                throw new IllegalArgumentException("Không chia được cho 0");
            }
        }
        catch (IllegalArgumentException e)
        {
            e.getMessage();
        }

        System.out.print(a+"/"+b+" = Vô cực ");
        return a/b;

    }

    public boolean isLetter(String caculate)
    {
        for (int i = 0 ; i < caculate.length() ; i++) {
            char c = caculate.charAt(i);
            if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z')
            {
                return false;
            }
        }
        return true;
    }

    public boolean inValidCharacter(String caculate)
    {
        for (int i = 0 ; i < caculate.length() ; i++) {
            char c = caculate.charAt(i);
            if (c >= ' ' && c <= '&' || c == '.' || c >= ':' && c <= '@' || c >= '[' && c <= ']' || c >= '{' && c <= '~')
            {
                return false;
            }
        }
        return true;
    }

    public boolean operatorSideBySide(String caculate)
    {
        for (int i = 0 ; i < caculate.length() ; i++) {
            char c = caculate.charAt(i);
            if (caculate.contains("^^") || caculate.contains("**")  || caculate.contains("//") || caculate.contains("++") || caculate.contains("*/") || caculate.contains("/*") || caculate.contains("+-") || caculate.contains("-+"))
            {
                return false;
            }
        }
        return true;
    }

    public String infixToPostfix(String caculate) throws EmptyStackException
    {
        // initializing empty String for result
        String result = new String("");

        // initializing empty stack
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i< caculate.length(); ++i)
        {
            char c = caculate.charAt(i);

            // If the scanned character is an operand, add it to output.
                if (Character.isLetterOrDigit(c)) {
                    result += c;

//                if operand is access, add ' '
                    if (i + 1 >= caculate.length() || !Character.isDigit(caculate.charAt(i + 1))) {
                        result += ' ';
                    }
                }

                // If the scanned character is an '(', push it to the stack.
                else if (c == '(')
                {
                   stack.push(c);
                }

                //  If the scanned character is an ')', pop and output from the stack
                // until an '(' is encountered.
                else if (c == ')') {

                    while (!stack.isEmpty() && stack.peek() != '(')
                        result += stack.pop();

                    if (!stack.isEmpty() && stack.peek() != '(')
                        return "Invalid Expression1";
                    else
                        stack.pop();
                }

                else // an operator is encountered
                {
                    while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek())){
                        if(stack.peek() == '(')
                            return "Invalid Expression2";
                        result += stack.pop();
                    }
                    stack.push(c);
                }
            }

            // pop all the operators from the stack
            while (!stack.isEmpty())
            {
                if(stack.peek() == '(')
                {
                    return "fail";
                }
                result += stack.pop();

            }
        return result;
    }

    // this can evaluate postfix more than 2 digit
    public double evaluatePostfix(String caculate){
        //create a stack
        Stack<Double> stack = new Stack<>();

        // Scan all characters one by one
        for(int i = 0; i < caculate.length(); i++)
        {
            char c = caculate.charAt(i);

            if(c == ' ')
            {
                continue;
            }
                // If the scanned character is an operand
                // (number here),extract the number
                // Push it to the stack.
            else if(Character.isDigit(c))
            {
                double n = 0;

                //extract the characters and store it in num
                while(Character.isDigit(c))
                {
                    n = n*10 + (int)(c-'0');
                    i++;
                    c = caculate.charAt(i);
                }
                i--;

                //push the number in stack
                stack.push(n);
            }

            // If the scanned character is an operator, pop two
            // elements from stack apply the operator

            else
            {
                double val1 = stack.pop();
                double val2 = stack.pop();

                switch (c) {
                    case '+':
                        stack.push(val2 + val1);
                        break;

                    case '-':
                        stack.push(val2 - val1);
                        break;

                    case '/':
                        if (val1 == 0) {
                            double divide = divideToZero(val2, val1);
                            stack.push(divide);
                        } else {
                            stack.push(val2 / val1);
                        }
                        break;

                    case '*':
                        stack.push(val2 * val1);
                        break;

                    case '^':
                        stack.push(Math.pow(val2, val1));
                        break;
                }
            }
        }
        return stack.pop();
    }

    public void basicCaculate()
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Nhập vào biểu thức : ");
        caculate = 0 + sc.nextLine().replaceAll("\\s","").replaceAll("--","+");

        for (int i = 0;i<caculate.length();i++)
        {
            char c = caculate.charAt(i);

            // ktra ngoac don
            try
            {
                if (!ktraNgoacdon(caculate))
                {
                    throw new EmptyStackException();
                }
            }
            catch (EmptyStackException e)
            {
                System.out.println("Nhập đủ ngoặc đơn theo một cặp");
                caculate = 0 + sc.nextLine().replaceAll("\\s","").replaceAll("--","+");

            }

            // bieu thuc nhap la chu
            try {
                if (!isLetter(caculate)) {
                    throw new EmptyStackException();
                }
            } catch (EmptyStackException e) {
                System.out.println("Vui lòng nhập vào là chữ số");
                caculate = 0 + sc.nextLine().replaceAll("\\s", "").replaceAll("--", "+");
            }


            // co cac ky tu ko phu hop
            try
            {
                if (!inValidCharacter(caculate))
                {
                    throw new EmptyStackException();
                }
            }
            catch (EmptyStackException e)
            {
                System.out.println("Ký tự không hợp lệ, vui lòng nhập lại");
                caculate = 0 + sc.nextLine().replaceAll("\\s","").replaceAll("--","+");
            }

            // nhap 2 toan tu lien nhau
            try
            {
                if (!operatorSideBySide(caculate))
                {
                    throw new EmptyStackException();
                }
            }
            catch (EmptyStackException e)
            {
                System.out.println("Không nhập hai toán tử liền nhau, vui lòng nhập lại");
                caculate = 0 + sc.nextLine().replaceAll("\\s","").replaceAll("--","+");
            }

            // chia cho 0
            try
            {
                if (caculate.contains("/0"))
                {
                    throw new EmptyStackException();
                }
            }
            catch (EmptyStackException e)
            {
                System.out.println("Không được chia cho 0");
                caculate = 0 + sc.nextLine().replaceAll("\\s","").replaceAll("--","+");
            }

            // co 2 dau ngoac canh nhau
            try
            {
                if(caculate.contains(")(") || caculate.contains("()"))
                {
                    throw new StringIndexOutOfBoundsException();
                }
            }
            catch (StringIndexOutOfBoundsException e)
            {
                System.out.println("Lỗi định dạng ngoặc đơn, vui lòng nhập lại");
                caculate = 0 + sc.nextLine().replaceAll("\\s","").replaceAll("--","+");
            }
        }

        System.out.println("= "+ evaluatePostfix(infixToPostfix(caculate)));

    }

    public void luongGiac()
    {
        System.out.println("Chọn hàm lượng giác muốn tính ( đơn vị: độ ) \n 1.Sin \n 2.Cos \n 3.Tan");
        Scanner sc = new Scanner(System.in);

                int opt = sc.nextInt();

                if (opt <=3)
                {
                    switch (opt)
                    {
                        case 1:
                            sin();
                            break;
                        case 2:
                            cos();
                            break;
                        case 3:
                            tan();
                            break;
                    }
                }
                else {
                    System.out.println("Tuỳ chọn không hợp lệ!");
                }
        }

    public void sin()
    {
        System.out.println("Nhập chỉ số( đơn vị: độ )");
        Scanner sc = new Scanner(System.in);
        long sin = sc.nextLong();

        System.out.println("sin "+sin+" = "+Math.sin(Math.toRadians(sin)));
    }

    public void cos()
    {
        System.out.println("Nhập chỉ số( đơn vị: độ )");
        Scanner sc = new Scanner(System.in);
        long cos = sc.nextLong();

        System.out.println("cos "+cos+" = "+Math.sin(Math.toRadians(cos)));
    }

    public void tan()
    {
        System.out.println("Nhập chỉ số( đơn vị: độ )");
        Scanner sc = new Scanner(System.in);
        long tan = sc.nextLong();

        System.out.println("tan "+tan+" = "+Math.tan(Math.toRadians(tan)));
    }

    public void option()
    {
        System.out.println("Chọn phép toán: \n 1. Tính toán cơ bản( +, - , * , / ) \n 2. Hàm lượng giác");
        Scanner sc = new Scanner(System.in);

        try
        {
            int opt = sc.nextInt();

            if (opt <=2)
            {
                switch (opt)
                {
                    case 1:
                        basicCaculate();
                        break;
                    case 2:
                        luongGiac();
                        break;
                }
            }
            else
            {
                System.out.println("Tuỳ chọn không hợp lệ!");
            }
        }
        catch (InputMismatchException e)
        {
            System.out.println("Vui lòng nhập vào là chữ số");
        }
    }

    public static void main(String[] args)
    {
        System.out.println("Welcome to Caculator Aplication!!! \n");
        Calculator m = new Calculator();

        while (true)
        {
            m.option();
        }
    }
}
