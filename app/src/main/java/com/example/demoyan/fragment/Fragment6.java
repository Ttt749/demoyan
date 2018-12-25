package com.example.demoyan.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.demoyan.R;
import com.example.demoyan.config.Config;
import com.example.demoyan.constant.ResultCode;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class Fragment6 extends Fragment {

    private static StandardGSYVideoPlayer videoPlayer;
    private static OrientationUtils orientationUtils;
    private static Activity mContext;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment6,container,false);
        mContext = getActivity();
        init();
        return view;
    }
    private void init() {
        videoPlayer =  (StandardGSYVideoPlayer)view.findViewById(R.id.video_player);

        String source1 = "http://192.168.1.101:8080/file/video/20180713_200758.mp4";
        videoPlayer.setUp(source1, true, "测试视频");

        //增加封面
//        ImageView imageView = new ImageView(getContext());
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setImageResource(R.drawable.house1);
//        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.GONE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.GONE);
        videoPlayer.setBottomProgressBarDrawable(null);
        //设置旋转
        orientationUtils = new OrientationUtils(getActivity(), videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWigetFull(false);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
            }
        });
        videoPlayer.setLooping(true);
        videoPlayer.startPlayLogic();
    }


    @Override
    public void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case ResultCode.SHOW_NEWVIDEO:
                    String string = (String) msg.obj;
//                    String string = "https://media.w3.org/2010/05/sintel/trailer.mp4";
                    videoPlayer.release();
                    videoPlayer.setUp(Config.baseUrl + string, true, "测试视频");

                    videoPlayer.setLooping(true);
                    videoPlayer.startPlayLogic();
                    break;
                default:
                    break;
            }
        }
    };
}
