import java.util.function.Supplier;

class Simulator {
    private final int numOfServers; 
    private final int qMax; 
    private final ImList<Double> arrivalTimes; 
    private final Supplier<Double> serviceTimes;
    private final Supplier<Double> restTime; 
    private final int numSelf; 

    Simulator(int numServers, int numSelf, int qMax, 
        ImList<Double> arrival, Supplier<Double> service, Supplier<Double> rest) {
        this.numOfServers = numServers; 
        this.qMax = qMax; 
        this.arrivalTimes = arrival; 
        this.serviceTimes = service; 
        this.restTime = rest; 
        this.numSelf = numSelf; 
    }



    public String simulate() {
        PQ<Event> pqEvent = new PQ<Event>(new EventComparator());
        String result = "";
        int numServed = 0; 
        int numLeft = 0;
        double avgWait = 0.0;
        ImList<Server> servers = new ImList<Server>();
        for (int i = 0; i < this.numOfServers; i++) {
            servers = servers.add(new Server(Integer.toString(i + 1), this.qMax, this.restTime));
        }

        ImList<Customer> selfCheckOutQueue = new ImList<Customer>(); 

        for (int k = 0; k < this.numSelf; k++) {
            servers = servers.add(new SelfCheckout(0.0,
                Integer.toString(this.numOfServers + 1), this.qMax, 
                    selfCheckOutQueue,this.restTime));
        }


        for (int j = 0; j < this.arrivalTimes.size(); j++) {
            String tag = Integer.toString(j + 1);
            Customer cus = new Customer(this.arrivalTimes.get(j),tag, this.serviceTimes);
            Server temp = new Server("0", this.qMax, this.restTime);
            pqEvent = pqEvent.add(new Arrive(this.arrivalTimes.get(j), cus, temp));
        }

        while (!pqEvent.isEmpty()) {
            Pair<Event, PQ<Event>> pr = pqEvent.poll();
            Event currEvent = pr.first();
            pqEvent = pr.second();
            
            if (!currEvent.isWaiting()) {
                result += currEvent.toString() + "\n";
            }
            numServed += currEvent.served();
            numLeft += currEvent.left();
            avgWait += currEvent.getWait(servers); 
            Pair<Event, ImList<Server>> updated = currEvent.update(servers);
            Event temp = updated.first();
            servers = updated.second();
            if (!currEvent.equals(temp)) {
                pqEvent = pqEvent.add(temp);
            } 
            
            
        } 

        avgWait = avgWait / numServed; 

        Stats stat = new Stats(numServed, numLeft, avgWait);
        result += stat.toString();

        return result;
    }
}

