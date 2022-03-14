import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class PiazzaExchange {

    String courseID;
    Instructor instructor;
    ArrayList<User> users;
    ArrayList<Post> posts;
    ArrayList<Post> unanswered;
    String status;
    boolean selfEnroll;
    private Forest keywordForest;

    private static final String STATS_STRING = "%s submitted %d posts, answered %d posts, received %d endorsements\n";

    /**
     * Constructor of the PiazzaExchange.
     *
     * @param instructor the instructor of this class
     * @param courseID The course ID
     * @param selfEnroll whether the class allow self enrolling or not.
     */
    public PiazzaExchange(Instructor instructor, String courseID, boolean selfEnroll) {
        this.instructor = instructor;
        this.courseID = courseID;
        this.selfEnroll = selfEnroll;
        this.status = "inactive";

        users  = new ArrayList<>();
        posts = new ArrayList<>();
        unanswered = new ArrayList<>();
    }

    //is there a reason why we don't combine these two constructors?
    //by default, selfEnroll is false, and we're setting DSC30 as default courseID

    /**
     * Constructor of the PiazzaExchange with a roster of user.
     *
     * @param instructor the instructor who create this piazza
     * @param roster the list of Users that will be included in this piazza
     */
    public PiazzaExchange(Instructor instructor, ArrayList<User> roster) {
        courseID = "DSC30";
        selfEnroll = false;
        users = roster;
        this.instructor = instructor;

        posts = new ArrayList<>();
        unanswered = new ArrayList<>();

    }

    public Forest getKeywordForest() {
        return keywordForest;
    }

    /**
     * Query for the top two endorsed posts
     *
     * @return two posts that has the highest endorsed
     */
    public Post[] computeTopTwoEndorsedPosts() {
        // TODO
        return null;
    }

    
    /* helper method for getTopStudentContributions() */

    public int getStudentContributions(User u) {
        return u.numOfPostSubmitted + u.numOfPostsAnswered + u.numOfEndorsement;
    }

    /**
     * get recent-30 day stats(including today), where index i corresponds to ith day away from current day
     *
     * @return integer array with the daily post status
     */
    public int[] computeDailyPostStats() {
        // TODO
        return null;
    }

    /**
     * get the month post stats for the last 12 month
     *
     * @return integer array that indicates the monthly status.
     */
    public int[] computeMonthlyPostStats(){
        // TODO
        return null;
    }

    /**
     * Activate the pizza. This action should be done by instructor only.
     *
     * @param u the instructor who wish to activate the class
     * @return successfulness of the action call
     */
    public boolean activatePiazza(User u){
        //checking if is instructor
        if (!(u instanceof Instructor)) return false;

        if (this.status.equals("inactive")) {
            this.status = "active";
            return true;
        }
        return false;
    }

    /**
     * Activate the pizza. This action should be done by instructor only.
     *
     * @param u the instructor who wish to activate the class
     * @return successfulness of the action call
     */
    public boolean deactivatePiazza(User u){
        if (u == this.instructor && this.status.equals("active")) {
            this.status = "inactive";
            this.selfEnroll = false;
            return true;
        }
        return false;
    }

    /**
     * Enroll the user to this PiazzaExchange. If the self enroll is disabled, only
     * instructor and tutor can request a new enrollment action.
     *
     * @param requester the requester of enrollment
     * @param u the user to enroll
     * @return successfulness of the action call
     */
    public boolean enrollUserToDatabase(User requester, User u){

        //inactive course check
        if (this.status == "inactive") return false;

        //duplicated re-enroll
        if (users.contains(u)) return false;

        if (this.selfEnroll) {
            this.users.add(u);
            return true;
        } else {
            if ((requester instanceof Instructor) | (requester instanceof Tutor)) {
                this.users.add(u);
                return true;
            }
        }
        return false;
    }

    /**
     * Enroll this user to PiazzaExchange
     *
     * @param u the user to enroll
     * @return successfulness of the action call
     */
    public boolean enrollUserToDatabase(User u){
        return enrollUserToDatabase(u, u);
    }

    ////////////// BEGIN BENCHMARKED METHOD /////////////

    /**
     * Given a specific posts, add this post to the database
     *
     * @param u The user that initiate this add-post action
     * @param p the post that we are going to add to the database
     * @throws OperationDeniedException when the action is not allowed:
     * thrown if (1) User u is not enrolled in this course or (2)course status is inactive.
     */
    public void addPostToDatabase(User u, Post p) throws OperationDeniedException {
        if (users.contains(u) == false | status.equals("inactive")) {
            throw new OperationDeniedException();
        }
        posts.add(p);
        u.numOfPostSubmitted ++;

    }

    /**
     * Get the post posted by this user that has the specific keyword
     *
     * @param u the poster of the post
     * @param keyword the keywords that we are going to query on
     * @return the post array that contains every single post that has the keyword
     */
    public Post[] retrievePost(User u, String keyword){
        ArrayList<Post> retrieved = new ArrayList<>();
        for (Post p: posts) {
            if ((p.poster == u) && (p.getKeyword().equals(keyword))) {
                retrieved.add(p);
            }
        }
        if (retrieved.size() == 0) return null;
        return retrieved.toArray(new Post[retrieved.size()]);
    }

    /**
     * Get the post that has the specific keyword
     *
     * @param keyword the keyword that we are searching on
     * @return the post array that contains every single post that has the keyword
     */
    public Post[] retrievePost(String keyword){
        ArrayList<Post> retrieved = new ArrayList<>();
        for (Post p: posts) {
            if (p.getKeyword().equals(keyword)) {
                retrieved.add(p);
            }
        }
        if (retrieved.size() == 0) return null;
        return retrieved.toArray(new Post[retrieved.size()]);
    }

    /**
     * Get the post with specific poster
     *
     * @param u the poster of posts
     * @return the post array that contains every single post that has specified poster u
     */
    public Post[] retrievePost(User u) {
        ArrayList<Post> retrieved = new ArrayList<>();
        for (Post p: posts) {
            if (p.poster == u) {
                retrieved.add(p);
            }
        }
        if (retrieved.size() == 0) return null;
        return retrieved.toArray(new Post[retrieved.size()]);
    }

    /**
     * delete the post from the PE. User should be either the creator of the post or a course staff
     * return whether the post got successfully deleted or not
     *
     * @param u the user who initiate this action
     * @param p the post to delete
     * @return whether the action is successful
     * @throws OperationDeniedException when the action is denied
     */
    public boolean deletePostFromDatabase(User u, Post p) throws OperationDeniedException {
        if ((u instanceof Instructor) == false) {
            throw  new OperationDeniedException();
        }
        if (posts.contains(p) == false) return false;
        posts.remove(p);
        return true;
    }

    /**
     * Compute the most urgent question in the unanswered post DS
     * for future answering.
     *
     * @return the Post with the highest urgency rating
     */
    public Post computeMostUrgentQuestion() {
        Question top = null;

        for (Post p: posts) {
            if (p instanceof Question) {
                if (top == null) top = (Question) p;
                if (p.compareTo(top) > 0) top = (Question) p;
            }
        }

        return top;
    }

    /**
     * Compute the top K urgent question from the unanswered post DS
     *
     * @param k the number of unanswered post that we want to have
     * @return the post array that is sorted by the urgency of the post
     * @throws OperationDeniedException when the operation is denied
     */
    public Post[] computeTopKUrgentQuestion(int k) throws OperationDeniedException{
        Post[] urgent = new Post[k];
        return null;
    }

    /**
     * answer the post. Removed from the unanswered post DataStructure
     *
     * @param u the answerer of the post
     * @param p the post that u is trying to answer
     * @param response the response to be appended to the post as an answer
     * @return the Post instance of the answered post
     * @throws OperationDeniedException when the operation is denied
     */
    public Post answerQuestion(User u, Post p, String response) throws OperationDeniedException{
        if (posts.contains(p) == false) throw new OperationDeniedException();
        if (p instanceof Question == false) return p;
        unanswered.remove(p);
        u.answerQuestion(p, response);
        return p;
    }

    ////////////// END BENCHMARKED METHOD /////////////

    /**
     *Retrieves user u in String format. Students can only see their own stats,
     *
     *  Tutors and instructors are able to access every student’s stats,
     *  therefore you’ll print all of the students’ stats(don’t output tutor stats),
     *  concatenated by a newline character between each students’ stats.
     *  User stats should be in the order in which the user is added/enrolled,
     *  so the first user being added to the piazzaExchange will be located at the first line,
     *  and the last user added to the piazzaExchange will be located at the last line.
     *  (where each line is defined by the separation of newline characters).
     *  the last user stats shouldn’t be appended with newline character.
     * @param u
     * @return
     */

    public String viewStats(User u){
        if (u instanceof Student) return getStudentStat(u);

        String stats = "";

        for (User user: this.users) {
            if (user instanceof Student) stats += getStudentStat(u);
        }

        //remove the trailing white space
        return stats.trim();
    }

    /**
     * format:
     * “(user’s username) submitted (numOfPostSubmitted) posts, answered (numOfPostsAnswered) posts, received (numOfPostEndorsed) endorsements”
     * @param u passed in an student object
     */
    private String getStudentStat(User u) {
        String stats = String.format("%s submitted %d posts, " +
                "answered %d posts, " +
                "received %d endorsements",
                u.username,
                u.numOfPostSubmitted,
                u.numOfPostsAnswered,
                u.numOfEndorsement);
        return stats;
    }

    /**
     * Retrieve all the posts from this piazza
     *
     * @param u the user who initiate this action
     * @return the posts array that contains every single post
     *      in this piazza
     */
    public Post[] retrieveLog(User u){
        ArrayList<Post> log = new ArrayList<>();
        for (Post p: u.posts) {
            log.add(p);
        }
        return (Post[]) log.toArray();
    }

    //If the length > 10, students only be able to access the first 10 posts right?

    /**
     * Retrieve posts log with specified length
     *
     * @param u the user who initiate this action
     * @param length of the posts that is allowed to fetch
     * @return the posts array that satisfy the conditions
     */
    public Post[] retrieveLog(User u, int length){
        if (u instanceof Student) {

        }
        return null;
    }

    private String[] getEleMultipleIndex(String[] arr, int[] indexes) {
        String[] output = new String[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            output[i] = arr[indexes[i]];
        }
        return output;
    }

    /**
     * Helper method that initialize the semantic connection
     * of the keywordForest.
     */
    private void initializeForest() {
        String[] allKeywords = new String[]{"tree", "midterm", "heap", "heap sort", "in place",
                "bst", "linked list", "queue",
                "priority queue", "SLL", "DLL", "hash table", "collision", "element", "hash function", "bloom filters",
                 "probing", "linear probing", "quadratic probing", "double hashing", "rehash", "chaining"};
        int[][] childrenIndex = {
                {2, 5}, // i = 0
                {5, 6},
                {7, 3},
                {4},
                {},
                {}, // i = 5
                {9, 10},
                {8},
                {},
                {},
                {}, // i = 10
                {12, 13, 14, 15},
                {16},
                {},
                {19, 20, 21},
                {}, // i = 15
                {17, 18},
                {},
                {},
                {},
                {}, // i = 20
                {}
        };
        for (int i = 0; i < allKeywords.length; i++) {
            keywordForest.addConnection(allKeywords[i], getEleMultipleIndex(allKeywords, childrenIndex[i]));
        }
    }

    /**
     * Sort by the title, return the first k occurrences of the posts with the keyword
     * Forest of tree and storing key using HashMap.
     *
     * @param keyword The keyword that we initiate the starting point of the search
     * @param k the number of similar post that we are querying
     */
    public Post[] computeKSimilarPosts(String keyword, int k) {
        // TODO
        return null;
    }

    /**
     * Sort by the title, return the first k occurrences of the posts with the keyword
     * Forest of tree of BST and store key using HashMap.
     */
    public Post[] computeKSimilarPosts(String keyword, int k, int level) {
        // TODO
        return null;
    }

    /**
     * describes basic course info, user and post status
     * NOT GRADED, for your own debugging purposes
     */
    public String toString(){
        // TODO
        return null;
    }

}
