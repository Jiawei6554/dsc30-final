import java.util.*;

public class Forest {

    // Connects the InternalNode with their specific keywords in the hashmap
    private HashMap<String, InternalNode> forest;
    int treeCount;

    protected class InternalNode {

        String key;
        ArrayList<Post> posts;
        ArrayList<InternalNode> children;

        /**
         * A constructor that initializes the InternalNode instance variables.
         *
         * @param key      Node's key
         */
        public InternalNode(String key, Post post) {
            this.key = key;
            this.posts = new ArrayList<>();
            this.posts.add(post);
            children = new ArrayList<>();
        }

        /**
         * A constructor that initializes InternalNode variables. Note: This constructor is
         * used when you want to add a key with no related information yet. In this
         * case, you must create an empty ArrayList for the node.
         *
         * @param key Node's key
         */
        public InternalNode(String key) {
            this.key = key;
            this.posts = new ArrayList<>();
            this.children = new ArrayList<>();
        }

        /**
         * Return the key
         *
         * @return The key
         */
        public String getKey() {
            return this.key;
        }


        /**
         * Return the linked list of the node
         *
         * @return The linked list of the node
         */
        public ArrayList<Post> getPosts() {
            return this.posts;
        }

        public ArrayList<InternalNode> getChildren() {
            return this.children;
        }

        public void addChildren(InternalNode node) {
            children.add(node);
        }

        public void setChildren(ArrayList<InternalNode> children) {
            this.children = children;
        }

        public boolean removeChildren(InternalNode node) {
            return children.remove(node);
        }

        /**
         * Setter for the linked list of the node
         *
         * @param newPosts New linked list
         */
        public void setPostsList(ArrayList<Post> newPosts) {
            this.posts = newPosts;
        }

        /**
         * Append new data to the end of the existing linked list of the node
         *
         * @param data New data to be appended
         */
        public void addNewPost(Post data) {
            posts.add(data);
        }

        /**
         * Remove 'data' from the linked list of the node and return true. If the linked
         * list does not contain the value 'data', return false.
         *
         * @param data Info to be removed
         * @return True if data was found, false otherwise
         */
        public boolean removePost(Post data) {
            return posts.remove(data);
        }
    }

    /**
     * Constructor that initialize the instance variable of the forest
     */
    public Forest() {
        this.forest = new HashMap<>();
        treeCount = 0;
    }


    /**
     * Insert the specific key into the forest with InternalNode with empty posts
     *
     * @param key the key of the internal node
     */
    public void insert(String key) {
        //decapitalize
        key = key.toLowerCase();

        forest.put(key, new InternalNode(key));
    }

    /**
     * Insert the specific key and value pairs into the forest
     *
     * @param post insert the post according to the post's key
     */
    public void insert(Post post) {
        String key = post.getKeyword().toLowerCase();

        if (forest.containsKey(key) == false) insert(key);

        forest.get(key).addNewPost(post);
    }

    /**
     * Helper method. Returns the node with the given key. 
     * If the key doesn’t exist in the forest, return null.
     * 
     * @param key querying the internal node with this specific key
     */
    private InternalNode nodeLookUp(String key) {
        key = key.toLowerCase();
        return forest.get(key);
    }

    /**
     * Get the posts that relate to a specific key. If the key does
     * not exist in the forest, throw IllegalArgumentException
     *
     * @param key the key
     * @return the Arraylist of posts
     */
    public ArrayList<Post> getPosts(String key) {
        key = key.toLowerCase();
        if (forest.containsKey(key) == false) throw new IllegalArgumentException();
        return nodeLookUp(key).getPosts();
    }

    /**
     * add Connection of more than one internal nodes by their keys
     *
     * @param parent the parent node's key
     * @param children the array of children node's keys
     */
    public void addConnection(String parent, String[] children) {
        InternalNode parentNode = nodeLookUp(parent);
        if (parentNode == null) {
            this.insert(parent);
            parentNode = nodeLookUp(parent);
        }
        ArrayList<InternalNode> childrenList = new ArrayList<>();
        for (String key: children) {
            key = key.toLowerCase();
            InternalNode node = nodeLookUp(key);
            if (node != null) {
                childrenList.add(node);
            } else {
                // if not exist, store it as empty node as placeholder
                this.insert(key);
                childrenList.add(nodeLookUp(key));
            }
        }
        parentNode.setChildren(childrenList);
    }

    /**
     * add connection for one internal nodes by their keys
     *
     * @param parent the key of the parent key
     * @param child the key of the child key
     */
    public void addConnection(String parent, String child) {
        addConnection(parent, new String[] {child});
    }

    /**
     * query the connection between the internal node by traversing the edge
     * of the forest
     *
     * @param key the initial start point of
     * @return the children of that specific node
     */
    public String[] queryConnection(String key) {
        InternalNode root = nodeLookUp(key);
        if (root == null) return null;
        /*
        HashSet<InternalNode> discovered = new HashSet<>();
        ArrayList<InternalNode> visited = new ArrayList<>();

        Queue<InternalNode> pending = new LinkedList<>();
        pending.add(root);
        discovered.add(root);

        while (pending.isEmpty() == false) {
            InternalNode currCheck = pending.remove();
            discovered.add(currCheck);
            for (InternalNode child: currCheck.getChildren()) {
                if (discovered.contains(child) == false) {
                    pending.add(child);
                    discovered.add(child);
                }
            }
            visited.add(currCheck);
        }

        String[] keywords = new String[visited.size()];
        for (int i = 0; i < visited.size(); i++) {
            keywords[i] = visited.get(i).getKey();
        }
         */

        ArrayList<String> keys = new ArrayList<>();

        for (InternalNode child: root.getChildren()) {
            keys.add(child.getKey());
        }

        return keys.toArray(new String[keys.size()]);
    }

}
