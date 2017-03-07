/*
 * This an LL(1) parser for arithmetic expressions, with the following contraints
 *    1. Numbers are single digits
 *    2. Exponentiation is not supported
 *
 *    The parser works by first building a parse tree, and then recursively
 *    evaluating it to resolve to a final result. 
 *
 *    The EBNF of the grammar for this parser is as follows:

 *    <exp> -> <term>{linop <term>}
 *    <linop>-> +|-
 *    <term> ->  <factor>{nonlinop <factor>}
 *    <nonlinop>-> *|/
 *    <factor> -> (<exp>)| number

 */
package arithmeticparser;
import java.lang.String;
import java.util.StringTokenizer; 
import java.lang.Character; 

/**
 *
 * @author stuarttruax
 */
public class ArithmeticParser {

    /**
     * @param args the command line arguments
     */
    
    private String eString;
    private int sIndex; 
    
    public static void main(String[] args) {
        
        String test =  new String("(4+6)");
        String test2 =  new String("6/(9+2)+7");
        
        ArithmeticParser testParser =  new ArithmeticParser(test2);
        
        System.out.println(testParser.parseExpression());
        
    }
    
    
    
    
    public ArithmeticParser(String s)
    {
       eString = s; 
       sIndex = 0; 
    }
    
    private char peek()
    {
        if(sIndex < eString.length())
            return eString.charAt(sIndex);
        return ' '; 
    }
    
    private void gobble()
    {
        sIndex=sIndex+1;  
    }
    
    
    public double parseExpression(){
        
        ASTNode toEvaluate = isExpr();
        
        if(toEvaluate!=null)
        {
           // System.out.println("Parsed the String!");
            return eval(toEvaluate);
        }
            
        return 0.0;     
        
    }
    
    private double eval(ASTNode toEvaluate)
    {
        if(toEvaluate != null)
        {
            String type = toEvaluate.getType();
       //     System.out.println(type);
            switch(type){
                
                case "num":
                            return toEvaluate.getValue(); 
                case "*":
                            return eval(toEvaluate.getRight()) * eval(toEvaluate.getLeft()); 
                case "/":
                            return eval(toEvaluate.getRight()) / eval(toEvaluate.getLeft()); 
                case "+":
                            return eval(toEvaluate.getRight()) + eval(toEvaluate.getLeft()); 
                case "-":
                            return eval(toEvaluate.getRight()) - eval(toEvaluate.getLeft());
                default:
                            return toEvaluate.getValue(); 
            }
            
        }
       return 0.0; 
    }
    
    
    
     private ASTNode isExpr()
    {
        ASTNode ret = isTerm();
        if(ret != null)
            return isExp_Tail(ret);
        return ret;
    }
    
    private ASTNode isExp_Tail(ASTNode node)
    {
       
       ASTNode ret, right; 
        if((peek() == '+')||(peek()=='-'))
        {
            if(peek()=='+')
            {
                ret = new ASTNode(new String(""+peek())+"");
                gobble(); 
                right = isTerm();
                 
                ret.setLeft(node);
                ret.setRight(right);
                return isExp_Tail(ret); 
            }
            else if(peek() == '-')
            {
                ret = new ASTNode(new String(""+peek())+"");
                gobble(); 
                right = isTerm();
                 
                ret.setLeft(node);
                ret.setRight(right);
                return isExp_Tail(ret);
            }
        }
         
        return node; 
    }
    
    private ASTNode isTerm()
    {
        ASTNode ret = isFactor();
        if(ret!= null)
            return isT_Tail(ret); 
        return null;
    }
    
    
    private ASTNode isT_Tail(ASTNode node)
    { 
        ASTNode ret, right; 
        if((peek() == '*')||(peek()=='/'))
        {
            if(peek()=='*')
            {
                ret = new ASTNode(new String(""+peek())+"");
                gobble(); 
                right = isFactor();
                 
                ret.setLeft(right);
                ret.setRight(node);
                return isT_Tail(ret); 
            }
            else if(peek() == '/')
            {
                ret = new ASTNode(new String(""+peek())+"");
                gobble(); 
                right = isFactor();
                 
                ret.setLeft(right);
                ret.setRight(node);
                return isT_Tail(ret);
            }
        }
        
        return node;
    }
    
    
    private ASTNode isFactor()
    {
        ASTNode ret; 
        if(peek() == '(')
        {
            gobble();
            if( (ret = isExpr()) != null)
                
                if(peek() == ')')
                {
                    gobble();
                    return ret;         
                }
            return null; 
        }
        else if((ret = isNumber()) != null)
        {
            return ret; 
        }
        return null;
    }
    
    private ASTNode isNumber()
    {
        ASTNode ret = null; 
        int test = Character.getNumericValue(peek());
        
        if((test >= 0) && (test <=9))
        {
            gobble(); 
            ret = new ASTNode("number");
            ret.setValue((double)test);
            return ret; 
        }
        
        return null; 
        
    }
    
   
    
    
}
