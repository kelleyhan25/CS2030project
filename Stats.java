class Stats {
    private final int numServed;
    private final int numLeft; 
    private final double avgWaitTime; 

    Stats(int numServed, int numLeft, double avg) {
        this.numServed = numServed; 
        this.numLeft = numLeft; 
        this.avgWaitTime = avg;
    }

    Stats updateStats(int numServed, int numLeft, double avg) {
        return new Stats(numServed, numLeft, avg);
    }

    @Override 
    public String toString() {
        return "[" + String.format("%.3f",this.avgWaitTime) + " " + this.numServed + " " 
            + this.numLeft + "]";
    }
}
