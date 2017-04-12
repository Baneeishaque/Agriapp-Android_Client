package com.example.agriapp.modals;

/**
 * Created by DK_win10-Asus_3ghz on 31-01-2017.
 */

public class Comment_Modal {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    String datetime;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    String comment;
    
    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    String commenter;
    
    public String getBlogid() {
        return blogid;
       
    }

    public void setBlogid(String blogid) {
        this.blogid = blogid;
    }

    String blogid;



	/*public String getName() {
	        return name;
	    }
	
	    public void setName(String name) {
	        this.name = name;
	    }
	
	    String name;*/
}
