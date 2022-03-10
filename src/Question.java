import java.util.ArrayList;

public class Question extends Post {

    boolean answered;
    ArrayList<String> answers;

    /**
     * Constructor of Question
     * @param poster the poster of this question
     * @param header the summary of this question
     */
    public Question(User poster, String header, String UID) {
        super(poster, header, UID);
        answered = false;
        answers = new ArrayList<String>();
    }

    /**
     * Overloaded constructor for Question. With more specifications
     *
     * @param poster The poster of this question
     * @param header the summary header of this question
     * @param question the question body
     * @param keyword the keywords of the question
     * @param PEID the unique identification ID of course ID
     */
    public Question(User poster, String header, String question, String keyword, String PEID, String UID){
        super(poster, header, question, keyword, PEID, UID);
        answered = false;
        answers = new ArrayList<String>();
    }

    /**
     * Returns the text of the question post. Tutors and instructors are able to access
     * all of the questions. Students are only able to view/get public questions,
     * or private questions posted by the user. If a student requests a private question
     * post that isn’t written by him/her, OperationDeniedException should be thrown to
     * signify that this operation is invalid.
     */
    public String getText(User u) throws OperationDeniedException {
        if (u instanceof Student) {
            //case public text
            if (this.isPrivate == false) return this.text;
            //case owned question
            if (u == this.poster) return this.text;

            throw new OperationDeniedException();
        }
        if (u instanceof Tutor || u instanceof Instructor) return this.text;

        //should not be reached
        return null;
    }

    /**
     * Getting the status of this question
     *
     * @return the status of the question
     */
    public String getStatus(){
        if (answered) return "Resolved";
        return "Unresolved";
    }

    /**
     * To string method. Feel free to change it to anything
     * for debugging purposes
     */
    @Override
    public String toString() {
        // TODO
        return null;
    }

    /**
     * answer this post
     *
     * @param s the answer of this question
     * @return whether the action is successful
     */
    public boolean answerQuestion(String s) {
        boolean added = answers.add(s);
        if (added) answered = true;
        return added;
    }
}
