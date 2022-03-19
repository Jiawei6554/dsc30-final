import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Post implements Comparable<Post> {

    String UID;
    String parentPEID;
    int endorsementCount;
    boolean endorsedByCourseStaff;
    private String header;
    protected String text;
    boolean isPrivate;
    User poster;
    LocalDate date;
    int priority;
    private String keyword;

    /**
     * Constructor for Post, text, keyword,
     * and PEID is unknown, we’ll set the text as an empty string
     * and set the keyword and PEID as null.
     *
     * @param poster the poster of the post
     * @param header the header of the post
     */
    public Post(User poster, String header, String UID) {
        this(poster, header, "", null, null, UID);
    }

    /**
     * Overloaded constructor for Post
     */
    public Post(User poster, String header, String text, String keyword, String PEID, String UID) {
        this.poster = poster;
        this.header = header;
        this.text = text;
        this.keyword = keyword;
        this.parentPEID = PEID;
        this.UID = UID;

        this.date = LocalDate.now();
        this.priority = 0;
    }

    /**
     * Getter method of the keyword of the post
     * @return the keyword of the post
     */
    public String getKeyword() {
        return this.keyword;
    }

    /**
     * Getter method of the text of the post
     * @return the text of the post
     */
    public abstract String getText(User u) throws OperationDeniedException;

    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Set the date of the post to the provided new date
     * 
     * @param newDate the new date we are setting the post to
     */
    public void setDate(LocalDate newDate) {
        this.date = newDate;
    }

    /**
     * return the poster user object
     * @return poster
     */
    public User getPoster() {
        return this.poster;
    }

    public void editText(String text) {
        this.text = text;
    }

    public String toString() {
        // TODO
        return null;
    }

    /**
     * Compare two posts by their priority
     *
     * @param other the other post that we are comparing
     * @return whether this post is larger than the other post
     */
    public int compareTo(Post other){
        if (this.calculatePriority() > other.calculatePriority()) return 1;
        return 0;
    }

    public int calculatePriority() {
        return this.endorsementCount+(int)(this.date.until(LocalDate.now()).getDays()/3.0);
    }
}
