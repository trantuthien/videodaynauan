package thientt.app.android.videodaynauan.pojo;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import thientt.app.android.videodaynauan.R;

public class ThienTTCardView extends FrameLayout {

    @Bind(R.id.textView)
    public TextView textView;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private ThumbnailListener thumbnailListener;
    ArrayList<Clip> data;
    OnItemClickListener mItemClickListener;
    OnScrollListener onScrollListener;
    LinearLayoutManager layoutManager;
    RecyclerImageAdapter adapter;

    public ThienTTCardView(Context context) {
        super(context);
        initView();
    }

    public ThienTTCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ThienTTCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.parent_item_layout, null);
        ButterKnife.bind(this, view);
        addView(view);
    }

    public void initRecyclerView(ArrayList<Clip> object) {
        this.data = object;
        adapter = new RecyclerImageAdapter();
        thumbnailListener = new ThumbnailListener();
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new HidingScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (onScrollListener != null) {
                    onScrollListener.onScrolled(recyclerView, dx, dy, layoutManager);
                    onScrollListener.currentPosition(layoutManager.findFirstVisibleItemPosition());
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public interface OnScrollListener {
        void onScrolled(RecyclerView recyclerView, int dx, int dy, LinearLayoutManager layoutManager);

        void currentPosition(int childPosition);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public class RecyclerImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public RecyclerImageAdapter() {
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
            View sView = mInflater.inflate(R.layout.child_item_layout, parent, false);
            return new ViewHolder(sView);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            ViewHolder holder1 = (ViewHolder) viewHolder;
            holder1.setInfo(position);
        }

        public int getBasicItemCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (isPositionHeader(position)) {
                return 0;
            }
            return 1;
        }

        @Override
        public int getItemCount() {
            return getBasicItemCount();
        }

        private boolean isPositionHeader(int position) {
            return position == 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.thumbnail)
        YouTubeThumbnailView imageView;

        @Bind(R.id.title)
        TextView title;

        @Bind(R.id.time)
        TextView time;

        private Spring spring_imageview;

        public ViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);

            spring_imageview = SpringSystem.create().createSpring();
            spring_imageview.addListener(new ThienTTSpringListener(imageView));
            imageView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            spring_imageview.setEndValue(1);
                            break;
                        case MotionEvent.ACTION_UP:
                            spring_imageview.setEndValue(0);
                            if (mItemClickListener != null) {
                                mItemClickListener.onItemClick(v, getAdapterPosition());
                            }
                            break;
                        case MotionEvent.ACTION_CANCEL:
                            spring_imageview.setEndValue(0);
                            break;
                    }
                    return true;
                }
            });
            imageView.initialize(DeveloperKey.DEVELOPER_KEY, thumbnailListener);
        }

        public void setInfo(int position) {
            //Glide.with(getContext()).load(data.get(position).getThumbnail()).into(imageView);
           // Date date = new Date(location.getTime());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.getDefault());
           // mTimeText.setText("Time: " + dateFormat.format(date));

            time.setText( dateFormat.format(data.get(position).getDate_add()));
            title.setText(data.get(position).getTitle());
            imageView.setTag(data.get(position).getLink());
        }
    }

    private final class ThumbnailListener implements
            YouTubeThumbnailView.OnInitializedListener,
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onInitializationSuccess(
                YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
            loader.setOnThumbnailLoadedListener(this);
            String videoId = (String) view.getTag();
            loader.setVideo(videoId);
        }

        @Override
        public void onInitializationFailure(
                YouTubeThumbnailView view, YouTubeInitializationResult loader) {
            view.setImageResource(R.drawable.hinh);
        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView view, YouTubeThumbnailLoader.ErrorReason errorReason) {
            view.setImageResource(R.drawable.hinh);
        }
    }

    public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

        private static final int HIDE_THRESHOLD = 20;
        private int mScrolledDistance = 0;
        private boolean mControlsVisible = true;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            if (firstVisibleItem == 0) {
                if (!mControlsVisible) {
                    mControlsVisible = true;
                }
            } else {
                if (mScrolledDistance > HIDE_THRESHOLD && mControlsVisible) {
                    mControlsVisible = false;
                    mScrolledDistance = 0;
                } else if (mScrolledDistance < -HIDE_THRESHOLD && !mControlsVisible) {
                    mControlsVisible = true;
                    mScrolledDistance = 0;
                }
            }
            if ((mControlsVisible && dy > 0) || (!mControlsVisible && dy < 0)) {
                mScrolledDistance += dy;
            }
        }

    }
}