abstract class Event {
    private final double eventTime; 
    private final Server ser; 
    private final Customer cus; 

    Event(double eventTime, Server ser, Customer cus) {
        this.eventTime = eventTime; 
        this.ser = ser; 
        this.cus = cus; 
    }

    Server getServer() {
        return this.ser; 
    }

    public boolean equals(Event other) {
        if (this.eventTime == other.eventTime) {
            if (this.getServer().equals(other.getServer())) {
                if (this.getCustomer().equals(this.getCustomer())) {
                    return true; 
                }
            }
        }
        return false; 
    }

    
    boolean isWaiting() {
        return false; 
    }
    
    Customer getCustomer() {
        return this.cus; 
    }

    double getEventTime() {
        return this.eventTime; 
    } 


    public int served() {
        return 0;
    }

    public int left() {
        return 0;
    }

    public double getWait(ImList<Server> servers) {
        return 0.0;
    }

    public Pair<Event, ImList<Server>> update(ImList<Server> servers) {
        return new Pair<Event, ImList<Server>>(this, servers);
    }

    
}
