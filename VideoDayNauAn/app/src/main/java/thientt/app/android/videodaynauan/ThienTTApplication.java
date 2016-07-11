package thientt.app.android.videodaynauan;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import thientt.app.android.videodaynauan.pojo.Category;
import thientt.app.android.videodaynauan.pojo.Clip;
import thientt.app.android.videodaynauan.pojo.ConnectService;
import thientt.app.android.videodaynauan.pojo.Constant;

/**
 * Created by thientran on 6/10/16.
 */
public class ThienTTApplication extends Application {
    private ArrayList<Clip> clips;
    private ArrayList<Category> categories;
    public String URL = "http://" + Constant.IP;
    private Messenger messenger_full;
    private Messenger messenger_category;
    private Handler main_handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            int mode = bundle.getInt(Constant.ACTION_SERVICE);

            Message message = Message.obtain();
            switch (mode) {
                case Constant.ACTION_GET_LINK_SERVER:
                    message.setData(bundle);
                    try {
                        messenger_full.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case Constant.ACTION_GET_CATEGORY_SERVER:
                    message.setData(bundle);
                    try {
                        messenger_category.send(message);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            return true;
        }
    });

    public ArrayList<Clip> getClips() {
        return clips;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public Category getCategory(final int category_id) {
//        return Stream.of(categories).filter(arr -> arr.get_id() == category_id).findFirst().get();
        return categories.get(0);
    }

    public void getFullClip(Handler handler) {
        messenger_full = new Messenger(handler);
        new ConnectService(this).getLinkServer(main_handler);
    }

    public void getFullCategory(Handler handler) {
        messenger_category = new Messenger(handler);
        new ConnectService(this).getCategoryServer(main_handler);
    }

    public String getURL() {
        String link = getSharedPreferences(Constant.AUTHORITY, Context.MODE_PRIVATE).getString(Constant.IP, null);
        return link != null ? link : URL;
    }

    public void saveClipServer(ArrayList<Clip> clips) {
        Collections.sort(clips, new Comparator<Clip>() {
            public int compare(Clip s1, Clip s2) {
                return s2.getClip_id() - s1.getClip_id();
            }
        });
        this.clips = clips;
    }


    public void saveCategoryServer(ArrayList<Category> categories) {
        Collections.sort(categories, new Comparator<Category>() {
            public int compare(Category s1, Category s2) {
                return s1.getPos() - s2.getPos();
            }
        });
        this.categories = categories;
    }

    public ArrayList<Clip> getClipsByCategoryType(int type) {
        ArrayList<Clip> result = new ArrayList<>();
        for (int i = 0; i < clips.size(); i++) {
            if (clips.get(i).getCategory_id() == type) {
                result.add(clips.get(i));
            }
        }
        return result;
    }
}
