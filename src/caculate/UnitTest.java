package caculate;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

import static jdk.nashorn.internal.objects.Global.Infinity;

public class UnitTest extends TestCase {
    public UnitTest(String caculate)
    {
        super(caculate);
    }

    @Test
    public void testDivideToZero()
    {
        Calculator c = new Calculator();

        try
        {
            c.divideToZero(3,0);
        }
        catch (IllegalArgumentException e )
        {
            assertEquals("Không chia được cho không",e.getMessage());
        }
    }

    @Test
    public void testKtraNgoacDon()
    {
        Calculator c = new Calculator();

        assertEquals(true,c.ktraNgoacdon("()"));

        assertEquals(false,c.ktraNgoacdon("("));

        assertEquals(false,c.ktraNgoacdon(")"));

        assertEquals(false,c.ktraNgoacdon(")("));

        assertEquals(false,c.ktraNgoacdon("(()"));
    }

    @Test
    public void testIsLetter()
    {
        Calculator c = new Calculator();

        assertEquals(true,c.isLetter("1+2+3"));

        assertEquals(false,c.isLetter("1a+2b+3c"));

    }

    @Test
    public void testInValidCharacter()
    {
        Calculator c = new Calculator();

        assertEquals(true,c.inValidCharacter("1234567890+-*/^()"));

        assertEquals(false,c.inValidCharacter("~!@#$%&=?'\".,:;"));

    }

    @Test
    public void testOperatorSideBySide()
    {
        Calculator c = new Calculator();

        assertEquals(true,c.operatorSideBySide("1+2-3*4/5^7"));

        assertEquals(false,c.operatorSideBySide("1++2"));

        assertEquals(false,c.operatorSideBySide("1+-2"));

        assertEquals(false,c.operatorSideBySide("1-+2"));

        assertEquals(false,c.operatorSideBySide("1/*2"));

        assertEquals(false,c.operatorSideBySide("1*/2"));

        assertEquals(false,c.operatorSideBySide("1//2"));

        assertEquals(false,c.operatorSideBySide("1**2"));

        assertEquals(false,c.operatorSideBySide("1^^2"));

    }

    @Test
    public void testPrec()
    {
        Calculator c = new Calculator();

        assertEquals(1,c.Prec('+'));

        assertEquals(1,c.Prec('-'));

        assertEquals(2,c.Prec('*'));

        assertEquals(2,c.Prec('/'));

        assertEquals(3,c.Prec('^'));

        assertEquals(-1,c.Prec(' '));
    }

    @Test
    public void testInfixToPostfix() {
        Calculator c = new Calculator();

        assertEquals("1 2 +",c.infixToPostfix("1+2"));

        assertEquals("4 2 -",c.infixToPostfix("4-2"));

        assertEquals("9 8 *",c.infixToPostfix("9*8"));

        assertEquals("1237123 8 /",c.infixToPostfix("1237123/8"));

        assertEquals("6 12 ^",c.infixToPostfix("6^12"));

        assertEquals("2 3 +4 5 +*3 6 +-1 2 4 +++",c.infixToPostfix("(2+3)*(4+5)-(3+6)+(1+(2+4))"));

    }

    @Test
    public void testEvaluatePosfix()
    {
        Calculator c = new Calculator();

        assertEquals(3.0,c.evaluatePostfix("1 2 +"));

        assertEquals(2.0,c.evaluatePostfix("03 1 -"));

        assertEquals(6.0,c.evaluatePostfix("02 3 *"));

        assertEquals(4.5,c.evaluatePostfix("09 2 /"));

        assertEquals(8.0,c.evaluatePostfix("02 3 ^"));

        assertEquals(3.0,c.evaluatePostfix("09 3 /"));

        assertEquals(-9.0,c.evaluatePostfix("0 1 2 +3 4 *-"));

        assertEquals(7776.0,c.evaluatePostfix("06 3 2 +^"));

        assertEquals(265.0,c.evaluatePostfix("02 2 3 ^^9 +"));

        assertEquals(1.0,c.evaluatePostfix("0 1 -2 +"));

        assertEquals(7776.0,c.evaluatePostfix("06 3 2 +^"));


        try
        {
            c.infixToPostfix("03 0 /");
        }
        catch (IllegalArgumentException e )
        {
            assertEquals("Không chia được cho không",e.getMessage());
        }

        try
        {
            c.infixToPostfix("03 1 1 -/");
        }
        catch (IllegalArgumentException e )
        {
            assertEquals("Không chia được cho không",e.getMessage());
        }
    }

}



