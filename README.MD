# Java Multithreading, Concurrency & Performance Optimization
- [Udemy course](https://www.udemy.com/course/java-multithreading-concurrency-performance-optimization/learn/lecture/10187964#overview)
- [Github project](https://github.com/jgregorio0/java-multithreading)
- [Document](https://docs.google.com/document/d/1-n6S-RQ5urgqxQPE1JbnhpjRXl_Ix7kekNB-IBvjJfs)

# Thread fundamentals
## Create thread implementing Runnable
- thread-fundamentals
`threads.fundamentals.CreateThreadImplRunnable`

## Create thread extending Thread
- thread-fundamentals
`threads/fundamentals/CreateThreadExtendingThread`

## Interrupt thread
- thread-fundamentals
`threads/fundamentals/InterruptExceptionThread`
- Threads consume resources (Memory, kernel resources, CPU, cache...)
- Thread finished work we want to clean up threads resources
- Thread is misbehaving we want to terminate thread
- App will not stop if one thread is still running so we want to close all threads gracefully

## InterruptedException
- thread-fundamentals
`threads/fundamentals/InterruptExceptionThread`
- InterruptedException: Thread executes a method that throws InterruptedException

## isInterrupted
- thread-fundamentals
  `threads/fundamentals/IsInterruptedThread`
- Theads code is handling interrupt signal

## Daemon threads
- thread-fundamentals
  `threads/fundamentals/DaemonThread`
Using Daemon threads when:
- Background tasks that should not block our app
  - File saving
- Code could not listen to interrupt
  - External library
- Thread will be finished even if not throwing InterruptedException or checking isInterrupted

## Thread coordination
- thread-fundamentals
  `threads/fundamentals/JoinInterruptExample`
- join method sleep thread A until thread B is finished
  - timeout parameter throws InterruptedException after x miliseconds

# Performance optimization
- Latency: Time to complete a task. Time units
- Throughput: Amount of task in a period. Task/time units

## Latency
- thread-optimization
  `threads/optimization/LatencyOptimization`

- Latency = T/N
  - T time to execute original task
  - N number of subtasks
- Dividing a task into N tasks to run in parallel
  - N = number of cores
  - each core running 1 thread
  - if threads are runable without interruption (IO blocking, sleep...)
  - No other tasks are consuming CPU
  - Hyperthreading: virtual cores share hardware
- Cost of parallelization
  - Breaking task into multiple tasks
  - Thread creation and passing task to threads
  - Time to schedule a task
  - Time until last task finishes and signals
  - Time until aggregator taks runs
  - Aggreegation of the results
- It is not posible to divide a task always:
  - Parallelizable tasks
  - Sequential tasks
  - Partially parallelizable / partially sequential
  
## Throughput
- thread-optimization
  `threads/optimization/ThroughputOptimization`

- Throughput = N/T (rendimiento)
  - N number of subtasks
  - T time to execute original task
- Dividing tasks into N tasks
  - Latency = T/N
- Runing tasks in parallel
  - Each task in different thread
  - Improve throghput by N
  - N = Threads = Cores
- Thread pooling
  - Reusing threads minimize creation and schedule tasks
  - Executor.newFixedThreadPool
  
# Data Sharing

## Concurrency Solutions

### Atomic operations
- Ocurred at once
- Single state, all or nothing, without intermediate states
- What is atomic?
  - Assignament to
    - References, including getters and setters
  - Assignament to
  - int
  - short
  - byte
  - float
  - char
  - boolean
  - volatile long
  - volatile double
  - java.util.concurrent.atomic

### Synchronized
- Lock mechanism
- Locks all synchronized methods of the object
- Use sychronized blocks instead of synchronized methods lock only the block instead of all methods

## Data Race
- thread-data-sharing
  `threads/datasharing/DataRace`
  
- A shared resource
  - is accessed by multiple threads
  - is modified by at least one of those thread
- Timing of threads scheduling may cause incorrect results
- Problem: Non atomic operations performed
- Solution:
  - Using synchronized to atomize a critical section
  - Using volatile to atomize long and double assignaments
Example:
```
Thread1 { x++; y++;}
Thread2 { if (y > x) throws new Exception(“Data Race detected!!”); }
```

## Race condition
- thread-data-sharing
  `threads/datasharing/RaceCondition`
  
- A shared resource
  - is accessed by multiple threads
  - is modified by at least one of those thread
- Compiler & CPU may execute code out of order to increase performance and utilization maintaining logical correctness
- Problem: Reorder code in one thread results in unexpected behaviour for the other
- Solution:
  - Using synchronized to atomize a critical section
  - Using volatile to guarantee order on the previous and next instruction

Example:
```
Thread1 { i++; i--; print i;}
Thread2 { i++; i--; print i;}
Result i != 0
```

## Summary
- synchronized
  - atomize a critical section
  - performance decrease
- volatile
  - atomize long and double assignments
  - guarantee order on the previous and next instruction
- Rule
  - Any variable used by multiple threads and modified by one at least must be in synchronized block or be declared as volatile

# Lock
- Coarse grain locking
  - one lock for all shared resources
  - Decrease paralelism
```
public class CoarseGrain
private DatabaseConnection dbConnection;
private List queue;

public synchronized Item getitemFromDB() {
}
public synchronized void addTaskToQueue() {
}
```  

- Fine grain locking
  - many locks for each shared resource
  - Deadlock
```
public class FineGrain
private DatabaseConnection dbConnection;
private List queue;

public Item getitemFromDB() {
synchronized(dbConnection) { ... }
}
public void addTaskToQueue() {
synchronized(queue) { ... }
}
```  

## Deadlock
- thread-deadlock
  `threads.deadlock.TrainIntersection`

- Mutual Exclusion - Only one thread can have exclusive access to a resource
- Hold and Wait - At least one thread is holding a resource and is waiting for another resource
- Non-preemptive allocation - A resource is released only after the thread is done using it.
- Circular wait - A chain of at least two threads each one is holding one resource and waiting for another resource

## Deadlock Solution
- Avoid circular wait - Strict order of locking shared resources
- Deadlock detection - Whatchdog
- Thread interruption
- tryLock operations

## ReentrantLock
- thread-lock
  `threads.lock.ReentrantLockExample`
  
- same functionalities than synchronized method plus:
  - check lock status
  - lockInterruptibly - Allow to interrupt thread waiting for lock
  - tryLock
    - thread is not suspended forever
    - wait until timeout is achieved and return false if !lock

## ReentrantReadWriteLock
- thread-lock
  `threads.lock.ReadWriteLock`

- same than ReentrantLock plus:
  - it allow many threads to read
  - only one to write
  - It also block readers when writing

# Semaphore
- semaphore
  `threads.semaphore.SemaphoreSample`
  
- Restrict how many threads access to shared resources
- Lock
  - allows access to single thread only
  - is reentrant
- Semaphore
  - allows access to a multiple threads
  - Not reentrant
  
# Iter-thread communication
## Condition Variables
`inter-thread/multithread/intercomm/conditionvariable/threads.lock.ConditionWithLock`

Thread can wait for condition that release other thread using Lock.newCondition()
- java.util.concurrent.locks.Lock
    - lock
    - unlock
- java.util.concurrent.locks.Condition
    - await
    - signal
    - signalAll

## Object
- java.lang.Object
    - synchronized(object)
    - wailt
    - notify
    - notifyAll
