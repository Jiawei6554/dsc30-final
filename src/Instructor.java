import java.util.ArrayList;

public class Instructor extends User{

    static final String insPID = "A0000";

    public Instructor(String username) {
        super(username, insPID);
    }

    public boolean answerQuestion(Post p, String response) {
        Boolean answered = ((Question) p).answerQuestion(response);
        if (answered) {
            ((Question) p).answered = true;
            this.numOfPostsAnswered ++;
            this.posts.add(p);
            return true;
        }

        return false;
    }

    @Override
    public boolean endorsePost(Post p) {
        if (p.endorsedByCourseStaff == false) {
            p.endorsementCount ++;
            p.endorsedByCourseStaff = true;
            return true;
        }
        p.poster.numOfEndorsement ++;
        return false;
    }

    @Override
    public String displayName() {
        String display = "Instructor: ";
        display += username;
        display += ", PID: ";
        display += PID;
        return display;
    }

    /**
     * attempt to delet the post
     * when operation denied, return null
     * when not deleted (piazza delete return false) return null
     * when deleted return p
     * @param p
     * @param piazza
     * @return
     */
    public Post deletePost(Post p, PiazzaExchange piazza) {

        try {
            Boolean deleted = piazza.deletePostFromDatabase(this, p);
            if (deleted == false) return null;
            return p;
        } catch (OperationDeniedException e) {
            return null;
        }
    }

    public boolean inactivatePiazza(PiazzaExchange piazza) {
        return piazza.deactivatePiazza(this);
    }

    public boolean editPost(Post p, String newText){
        p.editText(newText);
        if (!this.posts.contains(p)) {
            //this.posts.add(p);
            this.numOfPostSubmitted ++ ;
        }
        return true;
    }

    /**
     * get the top k urgent questions from a specific piazza
     *
     * @param pe the target Piazza
     * @param k the amount of urgent post that we want to get
     * @return the k urgent posts
     * @throws OperationDeniedException when the operation is denied
     */
    public Post[] getTopKUrgentQuestion(PiazzaExchange pe, int k) throws OperationDeniedException {
        Post[] top = pe.computeTopKUrgentQuestion(k);
        return top;
    }
}
