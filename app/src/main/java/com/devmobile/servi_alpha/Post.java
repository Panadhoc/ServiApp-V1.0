package com.devmobile.servi_alpha;

/**
 * Created by Souhail on 03/01/2016.
 */
public class Post {

    String title;
    String postid;
    String desc;
    String owner;
    int photoID;
    Post (String title, String postid, String desc,String owner, int photoID) {

        this.title=title;
        this.postid=postid;
        this.desc=desc;
        this.photoID=photoID;
        this.owner=owner;
    }
}
