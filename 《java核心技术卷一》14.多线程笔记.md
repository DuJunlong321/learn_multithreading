## 《java核心技术卷一》14.多线程笔记

### 14.1 什么是线程

1.**EventQueue.invokeLater()** 

是一个在Java Swing中用于在事件分发线程（Event Dispatch Thread，EDT）上执行代码的方法。Swing是基于事件驱动的图形用户界面（GUI）库，事件分发线程是负责处理Swing组件事件的主要线程。

在Swing中，所有与组件相关的操作，如创建、修改、渲染和事件处理，都应该在事件分发线程上进行，以确保GUI的线程安全性。`EventQueue.invokeLater()` 方法可以用来将代码提交给事件分发线程，以便在其中执行。

具体来说，`EventQueue.invokeLater()` 接受一个 `Runnable` 对象作为参数，该对象中的 `run()` 方法中包含了要在事件分发线程上执行的代码块。这样做可以避免在主线程（通常是应用程序的启动线程）中执行可能导致线程安全问题的GUI操作。

2.**面板（Panel）**、 **事件监听器（Event Listener）**：

面板是一个轻量级的容器组件，用于组织和布局其他组件，如按钮、文本框、标签等。在Java Swing中，`JPanel` 是一个常用的面板类。它可以用来划分界面区域、添加其他组件以及提供外观和布局的管理。你可以把面板看作是一个可以容纳其他组件的矩形区域。

在你的代码中，`buttonPanel` 是一个 `JPanel` 实例，用于容纳 "Start" 和 "Close" 两个按钮。通过将这些按钮添加到面板上，你可以更方便地管理按钮的布局和位置。

事件监听器是一种用于捕获和响应特定事件的机制。在图形用户界面（GUI）编程中，用户的操作（例如鼠标点击、键盘输入、按钮点击等）会触发各种事件。事件监听器是一种设计模式，允许你编写代码来响应这些事件，以执行相应的操作。

```java
JPanel buttonPanel = new JPanel();  // 创建一个按钮面板
        addButton(buttonPanel, "Start", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBall();
            }
        });
        addButton(buttonPanel, "Close", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
```

3.**add(comp, BorderLayout.CENTER);**

`add(comp, BorderLayout.CENTER);` 是用于将一个组件（`comp`）添加到容器中的特定位置（`BorderLayout.CENTER`）的操作。

`BorderLayout` 是一种布局管理器，用于在容器中按照北、南、东、西和中心的方向排列组件。这个布局将容器划分为五个区域，每个区域可以包含一个组件。当你使用 `BorderLayout` 时，组件会根据指定的位置放置在容器的不同区域。

==4.Java在一个单独的线程中执行任务的过程==

1. **创建任务（Runnable 或 Callable）：** 首先，你需要定义一个实现了 `Runnable` 接口或 `Callable` 接口的任务类。这个任务类会包含你想要在线程中执行的具体逻辑。

   - `Runnable` 接口包含一个 `run()` 方法，你需要在其中编写任务的逻辑。
   - `Callable` 接口包含一个 `call()` 方法，与 `run()` 类似，你也需要在其中编写任务的逻辑。

2. **创建线程对象：** 接下来，你需要创建一个线程对象，这可以通过直接实例化 `Thread` 类来实现。

   ```java
   Thread thread = new Thread(new MyRunnable()); // MyRunnable 是你定义的任务类
   ```

3. **启动线程：** 一旦你有了线程对象，你可以调用 `start()` 方法来启动线程。这将导致线程执行 `run()` 方法中定义的任务。

   ```java
   thread.start();
   ```

4. **等待线程完成（可选）：** 如果你希望等待线程执行完成，你可以使用 `join()` 方法。这会使当前线程等待，直到被等待线程执行完成。

   ```java
   try {
       thread.join();
   } catch (InterruptedException e) {
       // 处理中断异常
   }
   ```

这些步骤可以让你在一个单独的线程中执行任务。请注意，创建线程是有一定开销的，因此在创建大量线程之前，需要考虑线程池等技术来更有效地管理线程。

==5.Runnable和 Thread 之间的关系==

- `Runnable` 是一个接口，用于定义可以在一个单独线程中执行的任务，它包含一个 `run()` 方法，你需要在实现类中实现这个方法来定义任务逻辑。
- `Thread` 是一个类，用于表示一个线程。你可以通过实例化 `Thread` 类，并将一个 `Runnable` 实例作为参数传递给它的构造函数，然后调用 `start()` 方法来启动一个新的线程并执行传递的任务。另外，你也可以继承 `Thread` 类，覆写其 `run()` 方法，在这个方法中定义线程要执行的任务。

通过使用 `Runnable`，你可以将任务逻辑与线程的管理分离开来，使得代码更加清晰和可维护。这也有助于更好地利用线程池等线程管理技术。

### 14.5同步

==1.如何实现同步？锁对象==

**方法一：隐式锁 synchonized**

**1. 同步方法：**

在方法声明中使用 `synchronized` 关键字可以将整个方法变为同步方法，从而保证在同一时刻只有一个线程可以执行这个方法。示例如下：

```java
public synchronized void synchronizedMethod() {
    // 同步的代码块
}
```

**2. 同步块：**

你也可以使用 `synchronized` 关键字来创建同步块，将需要同步的代码包裹在同步块中。这允许你更细粒度地控制同步范围，而不必将整个方法都加锁。同步块使用的锁对象可以是任何对象。示例如下：

```java
public void someMethod() {
    // 一些非同步的代码

    synchronized (lockObject) {
        // 需要同步的代码块
    }

    // 一些非同步的代码
}
```

在上述示例中，`lockObject` 是用作锁的对象，同步块内的代码在同一时刻只能由一个线程执行。

需要注意以下几点：

- 对于同步方法，锁对象是实例对象（即`this`），或者对于静态同步方法，锁对象是类对象。
- 对于同步块，锁对象可以是任何对象，但通常使用一个专门用于锁定的对象。
- 使用过多的同步可能会导致性能问题，因为它可能会引起线程之间的竞争和阻塞。避免过度的同步可以提高多线程程序的性能。
- 要小心避免死锁，即多个线程因为互相等待对方释放锁而无法继续执行的情况。

**方法二：显式锁ReentrantLock**

演示了如何创建、获取、释放锁，以及如何使用条件（Condition）等特性：

利用condition进行线程之间的通信

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class ExplicitLockExample {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition(); // 条件
    private int value = 0;

    public void increment() {
        lock.lock();
        try {
            value++;
            System.out.println("Incremented value: " + value);
            condition.signalAll(); // 唤醒等待的线程
        } finally {
            lock.unlock();
        }
    }

    public void waitForValue(int targetValue) throws InterruptedException {
        lock.lock();
        try {
            while (value < targetValue) {
                condition.await(); // 等待条件满足
            }
            System.out.println("Value reached the target: " + value);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExplicitLockExample example = new ExplicitLockExample();

        Thread thread1 = new Thread(() -> {
            example.waitForValue(5);
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                example.increment();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();	// 主线程会被阻塞，直到 thread1 线程执行完成
        thread2.join(); //主线程会被阻塞，直到 thread2 线程执行完成
    }
}

```

