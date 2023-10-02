class IntWait extends Event {
    IntWait(double eventTime, Customer cus, Server ser) {
        super(eventTime, ser, cus);
    }

    @Override
    boolean isWaiting() {
        return true; 
    }


    @Override 
    public Pair<Event, ImList<Server>> update(ImList<Server> server) {
        int serverIndex = this.getServer().getServerTag() - 1;
        Pair<Event, ImList<Server>> newPair = new Pair<Event, ImList<Server>>(this, server);
        double nextTime = server.get(serverIndex).getNextTime();
        if (!server.get(serverIndex).getWaitQueue().isEmpty() 
            && server.get(serverIndex).getWaitQueue().get(0).equals(this.getCustomer()) 
            && server.get(serverIndex).isDone()) {
            if (server.get(serverIndex).getNextTime() >= this.getEventTime()) {
                Serve newServe = new Serve(server.get(serverIndex).getNextTime(),
                    this.getCustomer(), server.get(serverIndex));
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
   
    
}
