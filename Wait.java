import java.util.function.Supplier; 

class Wait extends Event {
    Wait(double eventTime, Server ser, Customer cus) {
        super(eventTime, ser, cus);
    }




    @Override 
    public Pair<Event, ImList<Server>> update(ImList<Server> server) {
        int serverIndex = this.getServer().getServerTag() - 1;
        Pair<Event, ImList<Server>> newPair = new Pair<Event, ImList<Server>>(this, server);
        double nextTime = server.get(serverIndex).getNextTime();
        String tag = Integer.toString(this.getServer().getServerTag());
        ImList<Customer> wq = server.get(serverIndex).getWaitQueue();
        Supplier<Double> rest = server.get(serverIndex).getRest();
        if (!server.get(serverIndex).getWaitQueue().isEmpty() 
                && server.get(serverIndex).getWaitQueue().get(0).equals(this.getCustomer())) {
            if (server.get(serverIndex).getNextTime() >= this.getEventTime()) {
                if (server.get(serverIndex).isSelfCheck()) {
                    SelfCheckout rep = new SelfCheckout(nextTime, tag, 
                        this.getServer().getMaxQueue(), wq, rest);
                    server = server.set(serverIndex, rep);
                } else {
                    Server replace = new Server(nextTime, tag, 
                        this.getServer().getMaxQueue(), wq, rest);
                    server = server.set(serverIndex, replace);
                }
                Serve newServe = new Serve(nextTime,this.getCustomer(), 
                    server.get(serverIndex));
                newPair = new Pair<Event, ImList<Server>>(newServe, server); 
            }
            
        } else {
            IntWait waitEvent = new IntWait(nextTime, this.getCustomer(),
                server.get(serverIndex));
            newPair = new Pair<Event, ImList<Server>>(waitEvent, server); 
        }
        return newPair; 
    } 

    @Override 
    public double getWait(ImList<Server> server) {
        double wait = 0.0;
        return wait; 
    }


    @Override 
    public String toString() {
        return this.getCustomer().toString() + " waits at " + this.getServer(); 
    }
}
