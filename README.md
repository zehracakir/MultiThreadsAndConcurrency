# MULTITHREADS and CONCURRENCY

# Creating and Using Threads

- When we run any application in Java, a main thread (Thread) is launched by default. In addition to this main thread, software developers can run sub threads. We already talked about that. Now let's examine how the Thread is defined and executed in the Java language.
- There is a class called "Thread" for creating threads in Java. Thus, we simply open a thread. Of course, it should not be forgotten that opening a Thread is a costly task to the system. Continuously producing Threads negatively affects resource usage. Therefore there is the concept of Thread Pooling to solve this problem.
- These costly objects are initially created in a certain amount and put into the pool in a ready state. Those who need a Thread use a Thread from this pool and return it to the system. Thus, besides the performance gain, resource utilization is also improved.

```java
Thread thread = new Thread();
```

- As seen above, we created an object from the "Thread" class and generated a thread. To start this thread we will need to call the "start" function.

```java
thread.start();
```

- Thus, our thread will continue to run until it finishes its job. However, in the example above, we did not provide a piece of code for the thread to run. There are two ways to give this.
1. It is necessary to create a subclass that inherits from the "Thread" class and override its "run" function.
2. Creating a subclass that inherits from the "Runnable" interface and overriding the "run" function, then passing it as an object to the constructor of the "Thread" class.

```java
public class SimpleThread extends Thread {

	@Override
	public void run() {
		// We get the name of the currently running Thread.
		String threadName = Thread.currentThread().getName();
		
		System.out.println("My summation " + threadName + " is started!");
		
		int total = 0;
		for(int i=0; i < 1000; i++) 
		{
			total += i;
		}
		
		System.out.println("Total: " + total);
	}
}
```

- Above, we defined a class named "SimpleThread" that inherits from Java's "Thread" class. We write the codes we want to run on the Thread into the "run" method in this class. This piece of code will run concurrently on any CPU at the operating system level. It should be noted that the thread we created is a sub-thread of the Java console application we are preparing.

```java
SimpleThread simpleThread = new SimpleThread();
simpleThread.start();
```

- We are creating an object of class "SimpleThread". Then, when we call the "start" function, the operating system creates a Thread resource for us and the "run" function that we override in the "SimpleThread" class starts to run. The codes inside the "run" function are now processed in a separate Thread. Likewise, when we produce another object and say "start", a new Thread is created and another thread is created.

```java
SimpleThread simpleThread2 = new SimpleThread();
simpleThread2.start();
```

- The results are as follows. As you can see, two different Threads were run at the same time.

```java
My summation Thread-0 is started!
Total: 499500
My summation Thread-1 is started!
Total: 499500
```

### Using Thread with Runnable interface

- We can run the piece of code as a thread by giving a Runnable type object to the threads.

```java
public class SimpleRunnable implements Runnable {

	@Override
	public void run() {
		
		// We get the name of the currently running Thread.
		String threadName = Thread.currentThread().getName();
		
		System.out.println("My summation " + threadName + " is started!");
		
		int total = 0;
		for(int i=0; i < 1000; i++) 
		{
			total += i;
		}
		
		System.out.println("Total: " + total);
	}

}
```

- Above, we defined a class named "SimpleRunnable" that inherits from the "Runnable" interface. We write the code to be run inside the thread by overriding the "run" method in this class.

```java
// We are creating an object from the "SimpleRunnable" class, which inherits from the Runnable interface.
SimpleRunnable simpleRunnable = new SimpleRunnable();

// We send the Runnable type object to the Thread constructor.
Thread simpleThread3 = new Thread(simpleRunnable);

// When we call the start function, the "run" function in the "SimpleRunnable" class will be executed.
simpleThread3.start();
```

- We are creating an object from our class of type "Runnable". Then, we create an object from the Thread class and send the "Runnable" type object into the constructor method. However, we define 3 Threads.

```java
My summation Thread-0 is started!
My summation Thread-1 is started!
Total: 499500
Total: 499500
My summation Thread-2 is started!
Total: 499500
```

- The results of 3 threads are above.
- Note: With Thread.currentThread() we can get the reference of the currently active thread. The Thread object we get here refers to the code that is run inside the thread.

## Put The Thread On Hold

- If we want to make a thread wait for a certain time in Java, we can use the "sleep" function of the Thread class.
- The sleep function expects a value in milliseconds. So, for example, the value 3000 corresponds to 3 seconds.

```java
try {
    Thread.sleep(10L * 1000L);
} catch (InterruptedException e) {
    e.printStackTrace();
}
```

- With Thread.sleep above, the thread is suspended for 10 seconds. Since the "sleep" function is a function that can throw an error, we have seen in previous issues that it should be checked in the try-catch block or we need to throw the error it throws up with the "throws" keyword.

## Stop Thread

- When a thread starts running, it is started to be executed by a CPU. There is a "stop" function inside the Thread class to stop the thread.
- However, this function does not guarantee to stop the thread. Therefore, while designing the code to run in the Thread, the programmer himself should code how to stop the running piece of code. Therefore, we can ensure that the code snippet is terminated by keeping a boolean value in the Thread class or in a class derived from the Runnable interface.
- Let's examine this with an example.

```java
public class ThreadSleeper {
	public static void sleep(long milliseconds) {
		
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
```

- We created a class called "ThreadSleeper". The "sleep" function in this class makes the thread wait for a certain period of time.
- Then, we update the piece of code we wrote in the "run" method in our "SimpleRunnable" class. We have defined a boolean variable named “live” to stop the running code snippet. With this variable, we stop the running work in Thread.

```java
public class SimpleRunnable implements Runnable {

	private boolean live = true;
	
	@Override
	public void run() {
		
// We get the name of the currently running Thread.
		String threadName = Thread.currentThread().getName();
		
		System.out.println("My summation " + threadName + " is started!");
		
		int total = 0;
		while(live) 
		{
			total += 1;
			
		// we pause for half a second.
			ThreadSleeper.sleep(500);
		}
		
		System.out.println("Total: " + total);
	}
	
	public void stop() {
		this.live = false;
	}

}
```

- We start the thread with the start function. Then we pause the main program for 10 seconds. When the waiting is over, we call the "stop" function in the "SimpleRunnable" and stop the running piece of code.

```java
// We are creating an object from the "SimpleRunnable" class, which inherits from the Runnable interface.
SimpleRunnable simpleRunnable = new SimpleRunnable();

// We send the Runnable type object to the Thread constructor.
Thread simpleThread3 = new Thread(simpleRunnable);

// When we call the start function, the "run" function in the "SimpleRunnable" class will be executed.
simpleThread3.start();

ThreadSleeper.sleep(10000);

simpleRunnable.stop();
SimpleRunnable simpleRunnable = new SimpleRunnable();

// We send the Runnable type object to the Thread constructor.
Thread simpleThread3 = new Thread(simpleRunnable);

// When we call the start function, the "run" function in the "SimpleRunnable" class will be executed.
simpleThread3.start();

ThreadSleeper.sleep(10000);

simpleRunnable.stop();
```

## **Critical Sections and Race Condition**

- Applications running as a single thread do not need any synchronization when accessing the memory area and other system resources. The program runs and uses resources, then returns resources to the system. However, in multithreaded programming, if multiple threads attempt to access a common resource at the same time, it will be necessary to grant them sequentially.
- While using a thread resource, others must wait for it. These shared common areas used by more than one thread are called "Critical Sections". The best example of this is changing the value of a variable. Because, changing the value of the variable means making a change in the memory region. Threads using such a common resource must access this "Critical Section" sequentially while reading and writing.

```java
public class QMatic implements Runnable {

	private int orderNo;
	
	public QMatic() {
		this.orderNo = 0;
	}
	
	@Override
	public void run() {
		
		// a little bit delay to see race condition!
		ThreadSleeper.sleep(50);
		
		// Critical section for all threads!
		this.orderNo = this.orderNo + 1;
		
		StringBuilder builder = new StringBuilder();
		builder.append(Thread.currentThread().getName());
		builder.append(" thread got ");
		builder.append(this.orderNo);
		builder.append(" from Qmatic!");

		System.out.println(builder.toString());
	}
}
```

- In the example above, consider a device in a bank branch with a serial number. The incoming customer receives a receipt in order and sees his number. The value of the variable named “orderNo” is changed in the “run” method in the class named “QMatic”. When customers press the device button, the sequence number increases by one.
- If all customers get numbers on a single device in the branch, there is no problem. One device, one Thread! However, imagine that the branch manager adds one of these devices to the branch for the sake of efficiency. We have 2 numbering devices.
- However, even if there are 2 devices, we still need to give the sequence numbers sequentially and consistently. 2 Threads 1 Critical Section! 2 devices means 2 Threads for us, and the sequence number means "Ciritical Section" for us. We need to properly manage the sequence number here.
- The “Critical Section” in the above example code is this.orderNo = this.orderNo + 1; is the expression. This may seem like a one-line instruction, but at the processor level in the background, this process consists of multiple steps.
- Qmatic1: Reads the value in the variable this.orderNo as 0 from memory.
- Qmatic2: Reads the value in the variable this.orderNo as 0 from memory.
- Qmatic2: Adds 1 to its 0 value.
- Qmatic2: Changes the "orderNo" variable to 1. The value of the orderNo variable is now 1.
- Qmatic1: Adds 1 to its 0 value.
- Qmatic1: Changes the "orderNo" variable to 1. The value of the orderNo variable is now 1.
- As you can see, if the read and write operations for threads are not synchronized, we can give the same sequence number to more than one person. This event is called “Race Condition”. Because Threads compete with each other to access the common resource.
- Inconsistent situations as above occur if the first accessor starts processing, but other Threads do not wait for it to finish its job. For this reason, Threads' access to the common resource must be sequential.

- Results :

![Ekran görüntüsü 2022-09-02 223106.png](MULTITHREADS%20and%20CONCURRENCY%20095cf76d4435429fbbb9fd2760423016/Ekran_grnts_2022-09-02_223106.png)

### Java "synchronized" Keyword

✴️ Reminders:

- Critical Region = In applications using Thread, it is the memory region that is shared and can be processed.
- Race Status = A situation where more than one thread accesses the shared memory area at the same time and processes the memory area at the same time.

- One of the methods that can be used to prevent the "Race Condition" in code regions with "Ciritical Section" is the "synchronized" keyword. With this keyword, the "Critical Section" code area can open sequential access between Threads.
- Briefly, the sequential access logic is as follows. The first thread that arrives in the critical region locks this region to indicate that it is processing. In this way, other threads understand that a thread is processing inside and can wait for the process to finish. When the thread that completed the process exits, the lock is removed and the waiting threads are opened again for processing. When another thread waiting for the process enters, it restricts access by using the lock structure again and continues in a loop.
- For example: It will create a big problem as if a thread is writing to a file while another thread wants to do the same. In such cases, the thread that first reaches the resource should be closed to access with the lock mechanism until it completes its job, and then it should be opened again.

⭐ Using synchronized in the method:

- This method is generally used in cases where the code is written by someone else but it is desired to be synchronized on it. In this way, the management is intervened, even partially.

```java
metot(){
    
    synchronized(Lock objesi){
        // operations in the critical area
    }

}
```

- The keyword “synchronized” can be given to a variable, part of a block, or method. If the above example is now written as "Thread Safe";

```java
public class QMatic implements Runnable {

	private int orderNo;
	
	private Object LOCK = new Object();
	
	public QMatic() {
		this.orderNo = 0;
	}
	
	@Override
	public void run() {
		
		// a little bit delay to see race condition!
		ThreadSleeper.sleep(50);
		
		// Critical section for all threads!
		
		synchronized (LOCK) {
			this.orderNo = this.orderNo + 1;
			
			StringBuilder builder = new StringBuilder();
			builder.append(Thread.currentThread().getName());
			builder.append(" thread got ");
			builder.append(this.orderNo);
			builder.append(" from Qmatic!");

			System.out.println(builder.toString());
		}
		
	}
}
```

- A lock object of type Object named "LOCK" is created in the class derived from the "Runnable" interface named QMatic. Then, it protects the code block that operates with the "orderNo" variable, which is common to all Threads specified as "Critical Section", with the "synchronized" keyword, and opens sequential access for Threads. If a Thread enters the code block marked as "Critical Section" and starts using resources, other Threads have to wait until that job is finished.

![Ekran görüntüsü 2022-09-02 223623.png](MULTITHREADS%20and%20CONCURRENCY%20095cf76d4435429fbbb9fd2760423016/Ekran_grnts_2022-09-02_223623.png)

⭐ Synchronized Methods

- When any method in a class receives the synchronized statement, when a thread enters that method, the object containing the method is automatically blocked from access by the lock mechanism. In this case, another thread cannot access any synchronized methods in that class. When the thread operating on the synchronized method exits the method, the lock is removed and the entire object becomes accessible again.
- As can be seen, the "synchronized" structure can be used in the method as well as in the method itself. This keyword should be inserted after the access token and before the return type of the function.

```java
access_sign synchronized return_type function_name(){
    //actions to be taken
}
```

- If the code block we mentioned above as "synchronized" is enclosed in a method, it can do as follows.

```java
private synchronized void increment() {
	
	this.orderNo = this.orderNo + 1;
	
	StringBuilder builder = new StringBuilder();
	builder.append(Thread.currentThread().getName());
	builder.append(" thread got ");
	builder.append(this.orderNo);
	builder.append(" from Qmatic!");

	System.out.println(builder.toString());
}
```

### Java "volatile" Keyword

- The "volatile" keyword guarantees that the value stored by the variable will be read by the Threads when they try to read the same value. In computer architecture there is a main memory region. While a program is running, the data and program commands are stored in this main memory area.
- This memory area is known as RAM. Also, there are processors (CPU) in computer architecture. The CPU and the memory area are in constant communication. There is a tight communication traffic.
- For this reason, there are small memory areas on the CPU side, as seen below. These are called caches. These caches are used to reduce the data read-write speed from the main memory to a minimum. Frequently used variable values are placed in these cache regions. Thus, instead of wasting time by constantly going to the main memory, they are processed faster.
- It is stated that when it is necessary to access the value of a variable marked with the "volatile" keyword, it will be taken directly from the main memory and if the related variable is to be written to, it will be written directly to the main memory region.
- There may be thoughts such as "Okay, we get it, they are kept in the main memory, but there are things that still do not fit in my head." The following scenario can be considered in response to such questions. For example, let's assume that a multithread application is written with a variable controlled between Threads and this application can be run by dozens of threads. Also, let's not forget that these threads can be run on different cores on the processor. In such a scenario, inconsistencies may occur in the operation of the program, since not all threads can use the same cache. When the control variable in this scenario is marked as "volatile", it will be kept in the main memory instead of the cache. In this way, all threads will be able to control the status of the variable simultaneously.
- Use of:

```java
Access_Token volatile Data_Type Variable_Name;
```

![Ekran görüntüsü 2022-09-02 232552.png](MULTITHREADS%20and%20CONCURRENCY%20095cf76d4435429fbbb9fd2760423016/Ekran_grnts_2022-09-02_232552.png)

```java
private volatile int orderNo;
```

## **ThreadPooling**

- Creating threads is seriously expensive. A certain resource is allocated in the system for each Thread. These resources are important ones like CPU, Memory. While our application is running, we may want to limit it to a certain amount of Thread.
- Therefore, we create a Thread pool and fill it with Thread objects that are already created and ready to use. Thus, we can achieve performance gain and efficient use of system resources.

```java
ExecutorService executor = Executors.newFixedThreadPool(15);
```

- You can create a Thread pool by calling the "newFixedThreadPool" method in the "Executors" class available in Java above. Then it will create and give us a pool with 15 Threads ready to use. This function will also give us an "ExecutorService" type object that will manage Thread usage on this pool. We will use a Thread in the pool with the "execute" function on this object and return it to the system when we are done.

```java
ExecutorService executor = Executors.newFixedThreadPool(15);

QMatic qmatic = new QMatic();

for(int i=0; i < 100; i++) {
 executor.execute(qmatic);
}
```

- In the above example, we are creating an object named “QMatic” from the Runnable class that we used before, which gives a sequence number. We run this piece of code with the "execute" function using a Thread in the pool. As you can see, although the pool has a capacity of 15, there is a request to use Thread 100 times in the loop. If there is no available free Thread in the pool, it will remain pending. It will take the first free Thread object from the pool and run.