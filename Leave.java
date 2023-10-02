class Leave extends Event { 
    Leave(double eventTime, Customer cus, Server ser) {
        super(eventTime, ser, cus);
    }

    @Override 
    public Pair<Event, ImList<Server>> update(ImList<Server> servers) {
        return new Pair<Event, ImList<Server>>(this,servers);
    }

    @Override 
    public int left() {
        return 1; 
    }

    @Override 
    public double getWait(ImList<Server> server) {
        return 0.0;
    }

    @Override 
    public String toString() {
        return this.getCustomer().toString() + " leaves";
    }
}

