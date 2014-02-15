package com.quicktour.entity;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author Roman Lukash
 */
@Entity
@Table(name = "comments")
public class Comment {
    private int commentId;
    private String content;
    private Tour tour;
    private Timestamp commentDate;
    private User user;
    private Comment parent;
    private Collection<Comment> children;

    @Id
    @Column(name = "comment_id")
    @GeneratedValue
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int id) {
        this.commentId = id;
    }


    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String comment) {
        this.content = comment;
    }


    @Column(name = "comment_date")
    public Timestamp getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Comment comment = (Comment) o;

        return commentId == comment.commentId;

    }

    @Override
    public int hashCode() {
        int result = commentId;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (commentDate != null ? commentDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinColumn(name = "tour_id", referencedColumnName = "tour_id")
    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "next_comment_id", referencedColumnName = "comment_id")
    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    @JsonManagedReference
    public Collection<Comment> getChildren() {
        return children;
    }

    public void setChildren(Collection<Comment> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Comment{" + "content='" + content + '\'' + '}';
    }
}
