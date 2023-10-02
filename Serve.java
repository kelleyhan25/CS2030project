import java.util.function.Supplier;

class Serve extends Event { 

    Serve(double eventTime, Customer cus, Server ser) {
        super(eventTime, ser, cus);
    }

    
    @Override 
    public Pair<Event, ImList<Server>> update(ImList<Server> server) {    
        Pair<Event, ImList<Server>> newPair = new Pair<Event, ImList<Server>>(this, server); 
        int serverIndex = this.getServer().getServerTag() - 1;
        double serviceTime = this.getCustomer().getServiceTime(); 
        double next = this.getEventTime() + serviceTime;
        String tag = Integer.toString(this.getServer().getServerTag());
        ImList<Customer> updatedWaitQueue = server.get(serverIndex).getWaitQueue();
        if (!server.get(serverIndex).getWaitQueue().isEmpty() 
            && server.get(serverIndex).getWaitQueue().get(0).equals(this.getCustomer())) {
            updatedWaitQueue = updatedWaitQueue.remove(0);
        }
        for (int i = 0; i < server.size(); i++) {
            if (server.get(i).isSelfCheck()) {
                server.set(i, server.get(i).updateAllQueue(updatedWaitQueue));
            }
        }
        Supplier<Double> rest = server.get(serverIndex).getRest();  
        Server replace = new Server(next, tag, 
            server.get(serverIndex).getMaxQueue(), updatedWaitQueue, rest);
        server = server.set(serverIndex, replace);
        Done newDone = new Done(next, this.getCustomer(), server.get(serverIndex));
        newPair = new Pair<Event, ImList<Server>>(newDone, server);
        return newPair;


    }

    @Override 
    public int served() {
        return 1; 
    }

    @Override
    public double getWait(ImList<Server> server) {
        double arrival = this.getCustomer().getArrivalTime();
        return this.getEventTime() - arrival; 
    }
    

    @Override 
    public String toString() {
        return String.format("%.3f", this.getEventTime()) + " " 
            +  this.getCustomer().getCustomerTag() 
            + " serves by " + this.getServer().toString();
    }
}

