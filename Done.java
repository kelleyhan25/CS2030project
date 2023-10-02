import java.util.function.Supplier; 

class Done extends Event {
    Done(double eventTime, Customer cus, Server ser) {
        super(eventTime, ser, cus);
    }

    
    @Override 
    public Pair<Event, ImList<Server>> update(ImList<Server> server) {
        int serverIndex = this.getServer().getServerTag() - 1;
        String tag = Integer.toString(this.getServer().getServerTag());
        ImList<Customer> wait = server.get(serverIndex).getWaitQueue();
        int max = server.get(serverIndex).getMaxQueue();
        Supplier<Double> restTime = server.get(serverIndex).getRest();
        double rest = server.get(serverIndex).getRestTime();
        double next = server.get(serverIndex).getNextTime() + rest; 
        if (server.get(serverIndex).isSelfCheck()) {
            SelfCheckout temp2 = new SelfCheckout(next, tag, max, wait, restTime);
            server = server.set(serverIndex, temp2.doneServing());
        } else {
            Server temp  = new Server(next, tag, max, wait, server.get(serverIndex).getRest());
            server = server.set(serverIndex, temp.doneServing());
        }
        return new Pair<Event, ImList<Server>>(this, server);
    }

    @Override 
    public double getWait(ImList<Server> server) {
        return 0.0;
    }

    @Override 
    public String toString() {
        return String.format("%.3f", this.getEventTime()) + " " 
            + this.getCustomer().getCustomerTag()
             + " done serving by " + this.getServer().toString();
    }

}

