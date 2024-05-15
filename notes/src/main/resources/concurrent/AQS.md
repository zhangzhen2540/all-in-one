# AQS
## 概念
    AbstractQueuedSynchronizer 抽象队列同步器，用于实现同步工具，比如ReentrantLock
    
    并通过 LockSupport 对线程进行 park/unpard

### VarHandle
    VarHandle 提供对字段进行 cas 操作，
```java
static final class Node {
    volatile Node next;
    private static final VarHandle NEXT;

    final boolean compareAndSetNext(Node expect, Node update) {
        return NEXT.compareAndSet(this, expect, update);
    }

    static {
        try {
            MethodHandles.Lookup l = MethodHandles.lookup();
            NEXT = l.findVarHandle(Node.class, "next", Node.class);
        } catch (ReflectiveOperationException var1) {
            throw new ExceptionInInitializerError(var1);
        }
    }
}
```

### Node
    队列节点数据结构

    Node SHARED = new Node();

    prev
    next
    thread: Node构造时，会引用当前线程
    nextWaiter: 节点通过该变量是否为静态常量 SHARED 来判断是否为共享节点

### Condition
    提供 await/signal/signalAll

### ConditionObject
    含 Node 节点的队列，实现了 Condition 接口

    是一个处于条件等待状态的线程队列

#### signal/signalAll 实现：
    
    最终调用了 AQS.transferForSignal(Node node) 实现
    
    首先将 node waitStatus 通过 cas 从 -2 置为 0，如果失败，结束
    aqs.enq(node) 重新入队
    然后通过 cas 设置 node waitStatus 为 -1，成功则返回，如果失败，则通过 LockSupport.unpard(node.thread) 唤醒节点线程
    (节点线程在 await 时，会释放锁，进入等待队列，当 signal 唤醒，这儿入队失败后，则会恢复到 await 点，先竞争释放掉的锁）
    
    如果执行signal，会从等待队列头顺序调用 AQS.transferForSignal(node) 到第一个执行成功的
    如果是signalAll，则会对所有等待节点执行  AQS.transferForSignal(node)

    调用过 AQS.transferForSignal(node) 后，节点就会从等待队列移除；执行失败：节点已经不是-2状态，成功：其他
    节点 waitStatus 为 -2(Condition) 时，AQS.transferForSignal(node) 才会执行

```java
import java.util.concurrent.locks.Condition;

class ConditionObject implements Condition {
    public final void signal() {
        if (!AbstractQueuedSynchronizer.this.isHeldExclusively()) {
            throw new IllegalMonitorStateException();
        } else {
            Node first = this.firstWaiter;
            if (first != null) {
                this.doSignal(first);
            }

        }
    }

    private void doSignal(Node first) {
        do {
            if ((this.firstWaiter = first.nextWaiter) == null) {
                this.lastWaiter = null;
            }

            first.nextWaiter = null;
        } while(!AbstractQueuedSynchronizer.this.transferForSignal(first) && (first = this.firstWaiter) != null);

    }

}
```

### await
    

#### 节点状态
    1: CANCELLED
    0: INIT
    -1: SIGNAL
    -2: CONDITION
    -3: PROPAGATE

### 


## LockSupport,Unsafe