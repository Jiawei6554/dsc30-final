
import java.util.Comparator;

public class PriorityComparator implements Comparator<Post> {

    @Override
    public int compare(Post p1, Post p2) {
        if (p1.calculatePriority() > p2.calculatePriority()) {
            return -1;
        } else if (p1.calculatePriority() < p2.calculatePriority()) {
            return 1;
        }
        return 0;
    }
}
