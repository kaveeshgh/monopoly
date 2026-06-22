/*
 * FIFO Queue
 * T defines generic Object
 * 
 */
 
import java.util.Arrays;

public class Queue<T>
{
	//variable declarations
    int front;
	int rear;
	int maxSize;
	Object[] list;

	//constructor
	//constructs an empty array of Object type T with given size
	//pre: int size of array to store queue
	public Queue(int maxSize)
	{
	    front = -1;
	    rear = -1;
	    this.maxSize = maxSize;
	    list = new Object[maxSize];
	}
    
    //enqueue
    //adds the object to the queue (last position moves forward one) if there is room
    //resizes if the queue is full
	//pre: object of type T to add to queue
	@SuppressWarnings("unchecked")
    public void enqueue (T obj)
    {
        if (isEmpty())
        {
            rear = 0;
            front = 0;
            list[rear] = obj;
        }
        
        else if (size() == maxSize)
        {
            System.out.println("Queue is full. Resizing...");
            resize(obj);
        }
        
        else
        {
            rear  = (rear + 1) % maxSize;
            list[rear] = obj;
        }
    }
    
	//dequeue
	//removes first item in the queue (first position moves forward one)
	//post: returns the object of type T in the first position 
	@SuppressWarnings("unchecked")
	public T dequeue()
	{
	    if (isEmpty())
        {
	        return null;
	    }
	    
	    else if (front == rear)
	    {
	        T t = (T) list[front];
	        clear();
	        return t;
	    }
	    
	    else 
	    {
	        T t = (T) list[front];
	        front = (front + 1) % maxSize;
	        return t;
	    }
	}
	 
	//isEmpty
	//post: returns true if the queue is empty; else returns false
	public boolean isEmpty()
	{
	    if (front == -1 && rear == -1){
	        return true;
	    }
	    
	    return false;
	}

	//front
	//post: return the object at the front of the array but do not remove it
	@SuppressWarnings("unchecked")
    public T front()
    {
        if (isEmpty())
        {
            return null;
        }
        
        else 
        {
            T t = (T) list[front];
            return t;
        }
    }

	//toString
    //post: return the string representing the queue from first to last
    public String toString()
    {
        String output;
        if (isEmpty())
        {
            output = "Queue is empty";
        }
        
        else
        {
            output = "[";
            
            if (rear >= front)
            {
                for (int i = front; i <= rear; i++){
                    output += list[i] + " ";
                }
            }
            
            else
            {
                for (int i = front; i < maxSize; i++){
                    output += list[i] + " ";
                }
                for (int i = 0; i <= rear; i++){
                    output += list[i] + " ";
                }
            }
            
            output += "]";
        }
        
        return output;
    }
	
    //size
    //post: return the integer number of items in the queue
    public int size()
    {
        if (isEmpty()){
            return 0;
        }
        
        else if (rear < front){
            return ((maxSize + 1) - (front - rear));
        }
        
        else{
            return rear - front + 1;
        }
    }

    //clear
    //clears the array 
    public void clear()
    {
        front = -1;
        rear = -1;
    }
    
    //resize
    //doubles the capacity of the queue
    @SuppressWarnings("unchecked")
    public void resize(T obj)
    {
        Object[] newList = new Object[2*maxSize];
        
        for (int i = front; i < maxSize; i++){
            newList[i-front] = list[i];
        }
    
        for (int i = 0; i <= rear; i++){
            newList[i + (maxSize - front)] = list[i];
        }
        
        list = newList;
        front = 0;
        rear = maxSize;
        list[rear] = obj;
        maxSize = 2*maxSize;
    }
}