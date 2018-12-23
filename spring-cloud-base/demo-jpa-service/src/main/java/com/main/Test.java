package com.main;

public class Test
{
    String name;
    
    
    public Test(String name)
    {
        this.name = name;
    }

    public synchronized void m1(Test t) {
        System.out.println(name+"--------m1  invoke-----------");
        
        t.m2();
    }
    
    public synchronized void m2() {
        System.out.println(name+"--------m2  invoke-----------");
    }
    
    
    public static void main(String[] args) throws Exception
    {
        Test a = new Test("a");
        Test b = new Test("b");
        
        Thread t1 = new Thread()
        {
            public void run()
            {
                synchronized (a)
                {
                    a.m1(b);
                }
                
                System.out.println("t1 over");
            }
        };
        
        Thread t2 = new Thread()
        {
            public void run()
            {
                synchronized (b)
                {
                    b.m1(a);
                }
                
                System.out.println("t2 over");
            }
        };
        
        
        t1.start();
        t2.start();
    }
    
}
