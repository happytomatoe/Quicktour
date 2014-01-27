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
    private int id;
    private String comment;
    private Tour tour;
    private Timestamp commentDate;
    private User user;
    private Comment parent;
    private Collection<Comment> children;

    @Id
    @Column(name = "id")
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Column(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comments = (Comment) o;

        if (id != comments.id) return false;
        if (comment != null ? !comment.equals(comments.comment) : comments.comment != null) return false;
        if (commentDate != null ? !commentDate.equals(comments.commentDate) : comments.commentDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (commentDate != null ? commentDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JsonIgnore
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinColumn(name = "tour_id", referencedColumnName = "ToursId", nullable = false)
    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "next_comment_id", referencedColumnName = "id")
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
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("id=").append(id);
        sb.append(", comment='").append(comment).append('\'');
        sb.append(", tour=").append(tour);
        sb.append(", commentDate=").append(commentDate);
        sb.append(", user=").append(user);
        sb.append(", parent=").append(parent);
        sb.append('}');
        return sb.toString();
    }
}
