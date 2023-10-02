import java.util.function.Supplier;

class Server { 
    private final double nextAvailableTime; 
    private final String serverTag; 
    private final int maxQueue; 
    private final ImList<Customer> waitQueue;
    private final boolean isDone;
    private final Supplier<Double> restTime;  
    

    Server(String serverTag, int maxQueue, Supplier<Double> rest) {
        this.serverTag = serverTag; 
        this.nextAvailableTime = 0.0;
        this.maxQueue = maxQueue;
        this.waitQueue = new ImList<Customer>();
        this.isDone = false; 
        this.restTime = rest; 
    }

    Server(double nextAvailableTime, String serTag, 
        int maxQueue, ImList<Customer> currWaitQueue, 
            Supplier<Double> rest) {
        this.nextAvailableTime = nextAvailableTime; 
        this.serverTag = serTag;
        this.maxQueue = maxQueue; 
        this.waitQueue = currWaitQueue; 
        this.isDone = false; 
        this.restTime = rest;
    }

    

    Server(double time, String tag, int max, 
        ImList<Customer> queue, boolean done, Supplier<Double> rest) {
        this.nextAvailableTime = time; 
        this.serverTag = tag; 
        this.maxQueue = max; 
        this.waitQueue = queue; 
        this.isDone = done; 
        this.restTime = rest;
    }

    Server doneServing() {
        return new Server(this.nextAvailableTime, 
            this.serverTag, this.maxQueue, 
                this.waitQueue, true, this.restTime);
    }

    Server updateAllQueue(ImList<Customer> queue) {
        return new Server(this.nextAvailableTime, this.serverTag, 
            this.maxQueue, queue, this.restTime);
    }


    Supplier<Double> getRest() {
        return this.restTime;
    }

    double getRestTime() {
        return this.restTime.get();
    }

    

    
    

           
    ImList<Customer> getWaitQueue() {
        return this.waitQueue;
    }

    double getNextTime() {
        return this.nextAvailableTime;
    }

    int getMaxQueue() {
        return this.maxQueue;
    }


    boolean isSelfCheck() {
        return false; 
    }


    boolean serverQueueFull() {
        if (this.waitQueue.size() == maxQueue + 1) {
            return true; 
        } else {
            return false; 
        }
    }

    int getServerTag() {
        return Integer.parseInt(serverTag);
    }

    boolean isAvailable(double time) {
        if (time >= nextAvailableTime) {
            return true;
        }  else {
            return false; 
        }
    }

    boolean isDone() {
        return this.isDone; 
    }

   
    

    Server updateQueue(ImList<Customer> queue) {
        return new Server(this.nextAvailableTime, this.serverTag, 
            this.maxQueue, queue, this.restTime);
    }



    @Override 
    public String toString() {
        return this.serverTag; 
    }

}

