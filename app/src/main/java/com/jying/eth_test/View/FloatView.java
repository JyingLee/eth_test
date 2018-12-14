package com.jying.eth_test.View;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jying.eth_test.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FloatView extends RelativeLayout {
    private static final long ANIMATION_TIME = 1000;
    private static final long ANIMATION_DEFAULT_TIME = 2000;
    private static final String TAG = "===debug===";
    private Context mContext;
    private List<? extends Number> mFloat = new ArrayList<>();
    private List<View> mViews = new ArrayList<>();
    public RelativeLayout parentView;
    private int parentWidth;
    private int parentHeight;
    private TextView defaultView;
    private int textColor;
    private int childId;
    private int parentId;
    private String defaultViewText;
    private float childSize;
    private ballClickListaner mListener;
    private DefaultViewListener defaultViewListener;
    private int flag = 1;

    public interface ballClickListaner {
        void itemClick(int position, Number value);
    }

    public void setOnBallClickListener(ballClickListaner itemClickListener) {
        this.mListener = itemClickListener;
    }

    public interface DefaultViewListener {
        void click(View view);
    }

    public void setDefaultViewListener(DefaultViewListener defaultViewListener) {
        this.defaultViewListener = defaultViewListener;
    }

    public FloatView(Context context) {
        super(context);
        this.mContext = context;
    }

    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FloatView);
        textColor = typedArray.getColor(R.styleable.FloatView_childTextColor, getResources().getColor(android.R.color.white));
        childSize = typedArray.getDimension(R.styleable.FloatView_chidTextSize, 8);
        childId = typedArray.getResourceId(R.styleable.FloatView_childViewBackground, R.drawable.shape_circle);
        parentId = typedArray.getResourceId(R.styleable.FloatView_parentViewBackground, R.mipmap.star_bg);
        defaultViewText = typedArray.getString(R.styleable.FloatView_defaultViewText);
        //一定会要释放资源
        typedArray.recycle();
    }

    public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    private void init() {
        setDefaultView();
        addChidView();
    }

    private void addChidView() {
        for (int i = 0; i < mFloat.size(); i++) {
            TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.view_float, this, false);
            textView.setTextColor(textColor);
            textView.setTextSize(childSize);
            textView.setBackgroundResource(childId);
            textView.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            textView.setText(mFloat.get(i) + "");
            textView.setTag(i);
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    childClick(v);
                }
            });
            setChildViewPosition(textView);//设置小球位置
            initAnim(textView);//初始化动画
            initFloatAnim(textView);//上下浮动
            mViews.add(textView);
            addView(textView);
        }
    }

    private void initFloatAnim(TextView textView) {
        Animation animation = new TranslateAnimation(0, 0, -5, 10);
        animation.setDuration(ANIMATION_TIME);
        animation.setRepeatCount(Integer.MAX_VALUE);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setRepeatMode(Animation.REVERSE);//反向重复执行
        textView.startAnimation(animation);
    }

    private void initAnim(TextView textView) {
        textView.setAlpha(0);
        textView.setScaleX(0);
        textView.setScaleY(0);
        textView.animate().alpha(1).scaleX(1).scaleY(1).setDuration(ANIMATION_DEFAULT_TIME).start();
    }

    private void setChildViewPosition(TextView textView) {
        Random randomX = new Random();
        Random randomY = new Random();
        float x;
        float y;
        int centerY = parentHeight / 2;
        if (flag == 1) {
            x = randomX.nextFloat() * parentWidth + parentWidth - textView.getMeasuredWidth();
            y = randomY.nextFloat() * centerY + centerY - textView.getMeasuredHeight();
            flag = -1;
        } else {
            x = parentWidth - (randomX.nextFloat() * parentWidth) + textView.getMeasuredWidth();
            y = centerY - randomY.nextFloat() * centerY + textView.getMeasuredHeight();
            flag = 1;
        }
//        y = randomY.nextFloat() * (parentHeight - textView.getMeasuredHeight());
        textView.setX(x);
        textView.setY(y);
        Log.e(TAG, x + "," + y);//小球坐标
    }

    //设置中间默认view
    private void setDefaultView() {
        parentView = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.view_item, this, true);
        parentView.setBackgroundResource(parentId);
        int width = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        parentView.measure(width, height);
        parentWidth = parentView.getMeasuredWidth();
        parentHeight = parentView.getMeasuredHeight();

        LayoutParams rules = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        defaultView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.view_float, this, false);
        defaultView.setText(defaultViewText);
        rules.addRule(RelativeLayout.CENTER_IN_PARENT);
        defaultView.setTextSize(childSize);
        defaultView.setTextColor(textColor);
        defaultView.setBackgroundResource(childId);
        if (mFloat.size() != 0) {
            defaultView.setVisibility(View.GONE);
        }
        addView(defaultView, rules);
        //设置动画
        initAnim(defaultView);
        //设置上下抖动的动画
        initFloatAnim(defaultView);

        defaultView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (defaultViewListener != null) {
                    defaultViewListener.click(v);
                }
            }
        });
    }

    //点击事件
    private void childClick(View view) {
        mViews.remove(view);
        animRemoveView(view);
        if (mViews.size() == 0) {
            defaultView.setVisibility(View.VISIBLE);
        }
        if (mListener != null) {
            mListener.itemClick((Integer) view.getTag(), mFloat.get((Integer) view.getTag()));
        }
    }

    private void animRemoveView(View view) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(parentHeight, 0);
        valueAnimator.setDuration(ANIMATION_TIME);
        valueAnimator.setInterpolator(new LinearInterpolator());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float Value = (float) valueAnimator.getAnimatedValue();
                Log.d(TAG, "onAnimationUpdate: " + view.getTranslationY());
                Log.d(TAG, "onAnimationUpdate: " + view.getY());
                view.setAlpha(Value / parentHeight);
                view.setTranslationY(view.getY() - (parentHeight - Value));
            }
        });


        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(view);
            }
        });
        valueAnimator.start();
    }

    public void setList(List<? extends Number> list) {
        this.mFloat.clear();
        this.mFloat = list;
        //使用post方法确保在UI加载完的情况下 调用init() 避免获取到的宽高为0
        post(new Runnable() {
            @Override
            public void run() {
                init();
            }
        });
    }
}
