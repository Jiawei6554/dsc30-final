public class Student extends User{

    public Student(String PID, String username) {
        super(PID, username);
    }

    /**
     * Answers a post with a response. A student can contain up to 50 words in their response.
     * The action returns false 1) if the above condition is violated, or
     * 2) if the post is inaccessible(any private post not created by the current user) or
     * (3) if the post is not question type.
     *
     * You update the collection of answers for the answered post,
     * as well as add the answered post to the collection of posts related to the user.
     * If p is a question, then make sure to set the status as answered.
     * update the count as well.
     * @param p the post we want to answer
     * @param response the response of the post
     * @return
     */
    public boolean answerQuestion(Post p, String response) {
        if (!(p instanceof Question)) {
            return false;
        }
        int wordCount = response.split("\\s+").length;
        if (wordCount > MAX_RE_LEN) {
            return false;
        }
        if (p.isPrivate && p.getPoster() != this) {
            return false;
        }
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
     * A student can only endorse posts that can be accessed,
     * which are public posts. Update the endorsementCount accordingly.
     *
     * @param p the post the user want to endorse
     * @return
     */
    @Override
    public boolean endorsePost(Post p) {
        if (!p.isPrivate | p.poster == this) {
            p.endorsementCount ++;
            return true;
        }
        return false;
    }

    /**
     *  Edits a post p.
     *  Students can only edit posts that are public or any post where he/she is the poster
     *  If the above condition is not satisfied, the method returns false.
     *  If the condition is satisfied, then the text of that post is replaced with the new text.
     *  The number of posts submitted by the current student should also be
     *  updated if post p is not yet in the user’s collection of posts.
     *
     * @param p the post we are editing
     * @param newText new content of the post that need to be set
     * @return
     */
    @Override
    public boolean editPost(Post p, String newText) {
        if ((!p.isPrivate) | (p.poster == this)) {
            p.editText(newText);
            if (!this.posts.contains(p)) this.posts.add(p);
            return true;
        }
        return false;
    }

    /**
     * Displays the name of the student object in the format:
     * "Student: username_of_the_student, PID: PID"
     * @return the displayed name
     */
    @Override
    public String displayName() {
        String dis = username;
        dis += ", PID:";
        dis += PID;
        return dis;
    }
}
