public class Tutor extends User{


    public Tutor(String PID, String username){
        super(PID, username);

    }

    public boolean answerQuestion(Post p, String response){
        Boolean answered = ((Question) p).answerQuestion(response);
        if (answered) {
            ((Question) p).answered = true;
            this.numOfPostsAnswered ++;
            this.posts.add(p);
            return true;
        }

        return false;
    }

    /**
     * Displays the name of the tutor object in the following format:
     * "Tutor: username_of_tutor, PID: PID”
     * @return
     */
    @Override
    public String displayName() {
        String display = "Tutor: ";
        display += username;
        display += ", PID:";
        display += PID;
        return display;
    }

    @Override
    public boolean endorsePost(Post p){
        if (p.endorsedByCourseStaff == false) {
            p.endorsementCount ++;
            p.endorsedByCourseStaff = true;
            return true;
        }
        p.poster.numOfEndorsement ++;
        return false;
    }

    @Override
    public boolean editPost(Post p, String newText) {
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
