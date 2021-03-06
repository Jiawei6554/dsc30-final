import java.util.ArrayList;

public abstract class User {

    String PID;
    String username;
    int numOfEndorsement;
    int numOfPostSubmitted;
    int numOfPostsAnswered;
    ArrayList<Post> posts;
    ArrayList<PiazzaExchange> courses;

    private static final int DAILY_STATS = 1;
    private static final int MONTHLY_STATS = 2;

    static final int MAX_RE_LEN = 50;
    /**
     * Constructor for the User abstract class
     *
     * @param PID the PID of the user
     * @param username the username of the user
     */
    public User(String PID, String username) {
        this.PID = PID;
        this.username = username;
        posts = new ArrayList<Post>();
        courses = new ArrayList<PiazzaExchange>();
    }

    /**
     * Enroll this user to the pizza.
     *
     * @param piazza the piazza to join
     * @return whether the action is successful
     */
    public boolean enrollClass(PiazzaExchange piazza) {
        return piazza.enrollUserToDatabase(this);
    }


    /**
     * Edit the content of the given post
     *
     * @param p the post we are editing
     * @param newText new content of the post that need to be set
     * @return
     */
    public abstract boolean editPost(Post p, String newText);

    /**
     * User can add the post to the piazza exchange database
     *
     * @param pe the target Piazza
     * @param p the post we want to add
     * @return whether the action is successful or not
     * @throws OperationDeniedException when the action is denied
     */
    public boolean addPost(PiazzaExchange pe, Post p) throws OperationDeniedException {
        try {
            pe.addPostToDatabase(this, p);
            return true;
        } catch (OperationDeniedException e) {
            throw e;
        }
    }

    /**
     * Request status of this specific user
     *
     * @param option daily or monthly
     * @return the statistic of the user
     */
    public int[] requestStats(PiazzaExchange p, int option) throws OperationDeniedException{
        switch (option) {
            case DAILY_STATS: return p.computeDailyPostStats();
            case MONTHLY_STATS: return p.computeMonthlyPostStats();
            default: throw new OperationDeniedException();
        }
    }

    ////////////// Stats querying method BEGINS /////////////

    /**
     * FOREST PART
     * Complete this after you finish the forest part
     *
     * Initiating Search K similar posts for a specific piazza exchange.
     *
     * @param pe the target piazza
     * @param keyword the initiating keyword of the search
     * @param k the amount of posts we want to get back
     * @return the k posts
     */
    public Post[] searchKSimilarPosts(PiazzaExchange pe, String keyword, int k) {
        // TODO
        return null;
    }

    /**
     * Requests the PiazzaExchange class to provide a set of posts associated with the user.
     * The option parameter specifies additional search filters.
     *
     * option = 1: request all posts within this piazzaExchange associated with the user
     * option = 2: request all posts related to the current user and with associated keywords,
     * by calling retrievePost(User u, String keyword) in PiazzaExchange
     * option = 3: request all posts related to the given keyword, by calling retrievePost(String keyword) in piazzaExchange
     *
     * @param keyword keyword we are searching
     * @param option the different search type we want to perform
     * @param p the target piazza exchange
     * @return the post array
     */
    public Post[] getPost(String keyword, int option, PiazzaExchange p) {
        if (option == 1) {
            return p.retrievePost(this);
        } else if (option == 2) {
            return p.retrievePost(this, keyword);
        } else if (option == 3) {
            return p.retrievePost(keyword);
        } else {
            return null;
        }
    }

    /**
     * Requests the PiazzaExchange class to provide the most recent logs/posts associated with the user.
     * If length = k, you retrieve the most recent k posts(sorted by date of the post in descending order,
     * where the most recent post is stored at index 0),  by calling retrieveLog will query across piazzaExchange posts and return it.
     * If option = 1, you???ll get all posts related to the current piazzaExchange,  by calling retrieveLog(User u)
     * If option = 2, you???ll get all posts related to the current user and with associated length, by calling retrieveLog(User u, int length) in piazzaExchange.
     * If the value of option is not valid, OperationDeniedException will be thrown.
     * @param pe the target piazza
     * @param length the amount of logs to be retrieved
     * @param option the query type when retrieving log 
     * @return the post array
     */
    public Post[] getLog(int length, int option, PiazzaExchange pe) throws OperationDeniedException{
        if (length <= 0) throw new OperationDeniedException();
        if (length > pe.posts.size()) throw new OperationDeniedException();

        if (option == 1) {
            return pe.retrieveLog(this);
        } else if (option == 2) {
            return pe.retrieveLog(this, length);
        }

        return null;
    }

    ////////////// Stats querying method END /////////////

    /**
     * answer a given post instance with the response
     *
     * @param p the post we want to answer
     * @param response the response of the post
     * @return whether the action is successful
     */
    public abstract boolean answerQuestion(Post p, String response);

    /**
     * Endorse a specific post instance
     *
     * @param p the post the user want to endorse
     * @return whether the action is successful or not
     */
    public abstract boolean endorsePost(Post p);

    /**
     * gets top two endorsed posts (by number of endorsements)
     *
     * @param pe the post the user want to endorse
     * @return top two posts
     */
    public Post[] getTopTwoEndorsedPosts(PiazzaExchange pe){
        return null;
    }

    /**
     * Display name for the user
     *
     * @return the name of the user
     */
    public abstract String displayName();

}
