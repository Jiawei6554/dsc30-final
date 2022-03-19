import java.util.Comparator;

public class TimeComparator implements Comparator<Post> {
    public int compare(Post p1, Post p2) {
        if (p1.getDate().isAfter(p2.getDate())) {
            return 1;
        } else if (p1.getDate().isBefore(p2.getDate())) {
            return -1;
        }
        return 0;
    }
}
