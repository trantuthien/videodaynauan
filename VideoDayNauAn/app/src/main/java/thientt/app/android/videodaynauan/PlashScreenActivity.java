package thientt.app.android.videodaynauan;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import thientt.app.android.videodaynauan.pojo.Category;
import thientt.app.android.videodaynauan.pojo.Clip;
import thientt.app.android.videodaynauan.pojo.Constant;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class PlashScreenActivity extends AppCompatActivity {
    private ThienTTApplication application;
    private ArrayList<Clip> serverLinks;
    private  ArrayList<Category> categories;
//    private ImageView imageView;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            if (bundle != null) {
                int mode = bundle.getInt(Constant.ACTION_SERVICE);
                String data = null;
                switch (mode) {
                    case Constant.ACTION_GET_LINK_SERVER:
                        data = bundle.getString(Constant.RESULT_HANDLER);
                        if (data != null) {
                            // TLog.d(null, clips1);
                            try {
                                JSONObject object = new JSONObject(data);
                                if (object.length() > 0) {
                                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                                    serverLinks = gson.fromJson(object.getJSONArray("clip").toString(), new TypeToken<ArrayList<Clip>>() {
                                    }.getType());
                                    if (serverLinks != null) {
                                        showDataToScreen(serverLinks);
                                    }
                                } else {
                                    Toast.makeText(PlashScreenActivity.this, "Something Wrong, try again", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case Constant.ACTION_GET_CATEGORY_SERVER:
                        data = bundle.getString(Constant.RESULT_HANDLER);
                        if (data != null) {
                            // TLog.d(null, clips1);
                            try {
                                JSONObject object = new JSONObject(data);
                                if (object.length() > 0) {
                                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                                    categories = gson.fromJson(object.getJSONArray("category").toString(), new TypeToken<ArrayList<Category>>() {
                                    }.getType());
                                    if (categories != null) {
                                        application.saveCategoryServer(categories);
                                        application.getFullClip(handler);

                                    }
                                } else {
                                    Toast.makeText(PlashScreenActivity.this, "Something Wrong, try again", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                }
            }
            return true;
        }
    });

    private void showDataToScreen(ArrayList<Clip> serverLinks) {
        application.saveClipServer(serverLinks);
        // Glide.with(this).load(gamePlay.getBg_link()).centerCrop().into(imageView);

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(PlashScreenActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_plash_screen);

        application = (ThienTTApplication) getApplication();
//        imageView = (ImageView) findViewById(R.id.image);
        //createKey();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkInternet(null);
                    }
                });
            }
        }.start();
    }

    public void checkInternet(View view) {
        if (Constant.isNetworkAvailable(this)) {
            application.getFullCategory(handler);
        } else {
            Constant.showSnackBar(findViewById(R.id.image), getString(R.string.lost_internet));
            findViewById(R.id.buton).setVisibility(View.VISIBLE);
        }
    }

}
