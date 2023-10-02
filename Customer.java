import java.util.function.Supplier;

class Customer {
    private final double arrivalTime;
    private final String customerTag;
    private final Supplier<Double> serviceTimes; 

    Customer(double arrivalTime, String customerTag, Supplier<Double> serviceTimes) {
        this.arrivalTime = arrivalTime; 
        this.customerTag = customerTag; 
        this.serviceTimes = serviceTimes;
    }

    Customer(double arrival, String tag, Supplier<Double> serviceTimes, boolean isServed) {
        this.arrivalTime = arrival; 
        this.customerTag = tag; 
        this.serviceTimes = serviceTimes; 
    }

    

    Supplier<Double> getService() {
        return this.serviceTimes; 
    }


    double getServiceTime() {
        return this.serviceTimes.get();
    }

    float getArrivalTime() {
        return (float)this.arrivalTime; 
    }

    int getCustomerTag() {
        return Integer.parseInt(this.customerTag);
    }


    @Override 
    public String toString() {
        String arr = String.format("%.3f",this.arrivalTime);
        return arr  + " " + this.customerTag;
    }


}

