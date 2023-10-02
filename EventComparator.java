import java.util.Comparator;

class EventComparator implements Comparator<Event> {
    @Override
    public int compare(Event a, Event b) {
        double t1 = a.getEventTime(); 
        double t2 = b.getEventTime();
        int a2 = a.getCustomer().getCustomerTag();
        int b2 = b.getCustomer().getCustomerTag();

        if (t1 != t2) {
            if (t1 > t2) {
                return 1; 
            } else {
                return -1;
            }
        } else {
            if (a2 > b2) {
                return 1; 
            } else {
                return -1;
            }
        }
    } 
}
