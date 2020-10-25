package com.filmfinder.templates;

import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class ReviewTemplate {

	private String comment;
	private float star;
    private int reviewId;

    public ReviewTemplate() {}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public float getStar() {
		return this.star;
	}

	public void setStar(float star) {
		this.star = star;
	}
	
	public int getReviewId() {
		return this.reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}



}