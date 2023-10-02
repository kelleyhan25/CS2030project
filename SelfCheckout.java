import java.util.function.Supplier; 

class SelfCheckout extends Server {
    SelfCheckout(String tag, int max, Supplier<Double> rest) {
        super(tag, max, rest);
    }

    SelfCheckout(double time, String tag, int max, ImList<Customer> queue, Supplier<Double> rest) {
        super(time, tag, max, queue, rest);
    }

    SelfCheckout updateAllQueue(ImList<Customer> queue) {
        return new SelfCheckout(this.getNextTime(), Integer.toString(this.getServerTag()),
            this.getMaxQueue(), queue, this.getRest());
    }

    @Override 
    Server doneServing() {
        return new SelfCheckout(this.getNextTime(), Integer.toString(this.getServerTag()), 
            this.getMaxQueue(), this.getWaitQueue(), this.getRest());
    }

    @Override
    boolean isSelfCheck() {
        return true; 
    }
    
    @Override
    double getRestTime() {
        return 0.0;
    }

    @Override
    public String toString() {
        return "self-check " + this.getServerTag();
    }


}
