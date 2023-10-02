import java.util.function.Supplier; 

class Arrive extends Event {
    Arrive(double eventTime, Customer cus, Server ser) {
        super(eventTime, ser, cus);
    }

        
    @Override 
    public Pair<Event, ImList<Server>> update(ImList<Server> server) {
        boolean waiting = false; 
        boolean beingServed = false; 
        Pair<Event, ImList<Server>> newPair = new Pair<Event, ImList<Server>>(this, server);
        for (int i = 0; i < server.size(); i++) {
            if (server.get(i).isAvailable(this.getEventTime()) 
                && server.get(i).getWaitQueue().size() == 0) {
                Serve newServe = new Serve(this.getEventTime(), this.getCustomer(), server.get(i)); 
                newPair = new Pair<Event, ImList<Server>>(newServe, server);
                beingServed = true; 
                break;
            } 
        }
        if (!beingServed) {
            for (int j = 0; j < server.size(); j++) {
                if (server.get(j).getWaitQueue().size() < server.get(j).getMaxQueue()) {
                    ImList<Customer> temp = server.get(j).getWaitQueue();
                    temp = temp.add(this.getCustomer());
                    for (int i = 0; i < server.size(); i++) {
                        if (server.get(i).isSelfCheck()) {
                            server.set(i, server.get(i).updateAllQueue(temp));
                        }
                    }
                    double serverTime = server.get(j).getNextTime();
                    int maxQueue = server.get(j).getMaxQueue();
                    int tag = server.get(j).getServerTag();
                    String tag2 = Integer.toString(tag);
                    Supplier<Double> rest = server.get(j).getRest();
                    if (server.get(j).isSelfCheck()) {
                        SelfCheckout temp3 = new SelfCheckout(serverTime, 
                            Integer.toString(tag), maxQueue, temp, rest);
                        server = server.set(j, temp3);
                    } else {
                        Server temp2 = new Server(serverTime, tag2, maxQueue, temp, rest);
                        server = server.set(j, temp2);
                    }
                    Wait newWait = new Wait(this.getEventTime(), server.get(j), this.getCustomer());
                    newPair = new Pair<Event, ImList<Server>>(newWait, server);
                    waiting = true; 
                    break;
                }
            }
        } 

        
        if (!beingServed && !waiting) {
            Supplier<Double> rest = this.getServer().getRest();
            Server empty = new Server("0", 0, rest);
            Leave newLeave = new Leave(this.getEventTime(), this.getCustomer(), empty);
            newPair = new Pair<Event, ImList<Server>>(newLeave, server);
        }
        return newPair;
    }

    @Override
    public double getWait(ImList<Server> server) {
        return 0.0;
    }

    @Override 
    public String toString() {
        return this.getCustomer().toString() + " arrives";
    }
  
}

