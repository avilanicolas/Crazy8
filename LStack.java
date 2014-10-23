 

import java.util.LinkedList;
import java.util.Iterator;
import java.lang.Comparable;

/**
 * LSTack useses the LinkedList methods and structure to recreate
 * a stack class for Java. This remasks, effectively, some of the
 * methods as more accessible one, and conforms to the general idea
 * of what a stack is.
 */
public class LStack<T>
{
    LinkedList<T> stack = new LinkedList<T>();
    int size = 0; 
    
    public void LStack()
    {
        // do nothing
    }
   
    /**
     * @return A boolean telling if the stack is empty or not.
     */
    public boolean isEmpty()
    {
        // An empty stack can be determined if .peek() returns
        // a null value or not.
        boolean ret = false;
        if(stack.peek() == null)
        {
            ret = true;
        }
        return ret;
    }
    
    /**
     * Iterate through the stack for the desired element.
     * @return An int containing the index of a found element. -1 if it is not discovered.
     */
    public int search(T key)
    {
        Iterator iter = stack.listIterator();
        int i = -1;
        boolean found = false;
        while(iter.hasNext() && !found)
        {
            i++;
            if(iter.next() == key)
            {
                found = true;
            }
        }
        // If found, i, else, -1
        return (found) ? i : -1;
    }
    
    /**
     * The stack push method.
     * @param value The value to push.
     */
    public void push(T value)
    {
        this.size++;
        stack.push(value);
    }
    
    /**
     * The stack pop method.
     * @return The popped item.
     */
    public T pop()
    {
        this.size--;
        return stack.pop();
    }
    
    /**
     * The stack peek method.
     * @return The top of the stack.
     */
    public T peek()
    {
        return stack.peek();
    }
    
    /**
     * Get the value of an item at a certain index with the stack.
     * NOTE: Stack indices begin from 1, not zero.
     * @param index The index in the stack you want to get.
     * @return The item at the specified index.
     */
    public T get(int index)
    {
        T ret = null;
        // If the given index is 
        if(index <= size && index >= 1 && !stack.isEmpty())
        {
            // This the LinkedList method .get()
            ret = stack.get(index - 1);
        }
        return ret;
    }
}