package thientt.app.android.videodaynauan.pojo;

/**
 * Created by thientran on 6/27/16.
 */
public class Category {
    private int _id;
    private int type;
    private int pos;
    private String name, link;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public Category() {

    }

    public Category(int _id, int type, String name, String link) {

        this._id = _id;
        this.type = type;
        this.name = name;
        this.link = link;
    }
}
