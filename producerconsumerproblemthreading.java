package producerconsumerproblemthreading;
import java.util.Vector;
class producer implements Runnable{
private final Vector sharedQueue;
private final int size;
public producer(Vector sharedQueue,int size)
{
this.sharedQueue= sharedQueue;
this.size=size;
}
public void run() {
for(int i=0;i<10;i++) {
System.out.println("produced :" +i);
try {
produce(i);
}catch(InterruptedException e) {
}
}
}
private void produce(int i)throws InterruptedException{
while(sharedQueue.size()==size) {
synchronized(sharedQueue) {
System.out.println("queue is full" +Thread.currentThread().getName()+ "is waiting,size:" +sharedQueue.size());
sharedQueue.wait();
}
}
synchronized(sharedQueue) {
sharedQueue.add(i);
sharedQueue.notifyAll();
}
}
}


class consumer implements Runnable{
private final Vector sharedQueue;
private final int size;
public consumer(Vector sharedQueue, int size) {
this.sharedQueue=sharedQueue;
this.size=size;
}
public void run() {
while(true) {
try {
System.out.println("consumed: "+consume());
Thread.sleep(50);
}
catch(InterruptedException e ) {
}
}
}
private int consume() throws InterruptedException{
while(sharedQueue.isEmpty()){
synchronized(sharedQueue) {
System.out.println("queue is empty" +Thread.currentThread().getName()+ "is waiting, size:"+sharedQueue.size());
sharedQueue.wait();
}
}
synchronized(sharedQueue) {
sharedQueue.notifyAll();
return(Integer) sharedQueue.remove(0);
}
}	
}
public class producerconsumerproblemthreading {
public static void main(String args[]) {
Vector sharedQueue = new Vector();
int size = 6;
Thread prodThread = new Thread(new producer(sharedQueue, size), "Producer");
Thread consThread = new Thread(new consumer(sharedQueue, size), "Consumer");
prodThread.start();
consThread.start(};}}
