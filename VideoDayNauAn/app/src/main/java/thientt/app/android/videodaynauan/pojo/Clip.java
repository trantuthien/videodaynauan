package thientt.app.android.videodaynauan.pojo;

import java.util.Date;

/**
 * Created by thientran on 6/27/16.
 */
public class Clip {
    private int _id, clip_id, category_id;
    private String thumbnail, title, link;
    private Date date_add;

    public Clip() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getClip_id() {
        return clip_id;
    }

    public void setClip_id(int clip_id) {
        this.clip_id = clip_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getDate_add() {
        return date_add;
    }

    public void setDate_add(Date date_add) {
        this.date_add = date_add;
    }

    public Clip(int _id, int clip_id, int category_id, String thumbnail, String title, String link) {

        this._id = _id;
        this.clip_id = clip_id;
        this.category_id = category_id;
        this.thumbnail = thumbnail;
        this.title = title;
        this.link = link;
    }
}
