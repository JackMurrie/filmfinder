package com.filmfinder.templates;

import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class ReviewTemplate {

    private String token;
    private String comment;
    private int reviewId;

    public ReviewTemplate() {}

    // public ReviewTemplate(String token, String comment) {
    //     this.token = token;
    //     this.comment = comment;
    // }

    // public ReviewTemplate(int reviewId, String token, String comment) {
    //     this(token, comment);
    //     this.reviewId = reviewId;
    // }
    
    public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getReviewId() {
		return this.reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}



}