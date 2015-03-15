package ru.hh.data;

/**
 * Created by HonneyHelen on 15-Mar-15.
 */
public class Counters {

    private Integer unread_negotiations;
    private Integer new_resume_views;

    public Integer getUnread_negotiations() {
        return unread_negotiations;
    }

    public void setUnread_negotiations(Integer unread_negotiations) {
        this.unread_negotiations = unread_negotiations;
    }

    public Integer getNew_resume_views() {
        return new_resume_views;
    }

    public void setNew_resume_views(Integer new_resume_views) {
        this.new_resume_views = new_resume_views;
    }

    @Override
    public String toString() {
        return "Counters{" +
                "unread_negotiations=" + unread_negotiations +
                ", new_resume_views=" + new_resume_views +
                '}';
    }
}
