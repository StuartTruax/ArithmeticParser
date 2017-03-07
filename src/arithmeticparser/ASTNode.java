package arithmeticparser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.lang.String;

/**
 *
 * @author stuarttruax
 */
public class ASTNode {
    
    private ASTNode right;
    private ASTNode left; 
    private String type;
    private double value; 
    
    public ASTNode(String type)
    {
        this.type = type; 
        right = null;
        left = null;
    }
    
    public ASTNode getRight()
    {
        return right;
    }
    
    public void setRight(ASTNode right)
    {
        this.right = right; 
    }
    
    public ASTNode getLeft()
    {
        return left; 
    }
    
    public void setLeft(ASTNode left)
    {
        this.left = left; 
    }
    
    public double getValue()
    {
        return this.value;
    }
    
    public void setValue(double value)
    {
        this.value = value;
    }

    public String getType()
    {
        return type; 
    }
    
    
    
    
    
}
