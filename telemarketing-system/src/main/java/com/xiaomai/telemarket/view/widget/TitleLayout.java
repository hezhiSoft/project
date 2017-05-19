package com.xiaomai.telemarket.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jinggan.library.ui.dialog.ToastUtil;
import com.xiaomai.telemarket.DataApplication;
import com.xiaomai.telemarket.R;

/**
 * @author yangdu <youngdu29@gmail.com>
 * @description 顶部导航栏
 * @createtime 06/05/2017 1:11 PM
 **/
public class TitleLayout extends RelativeLayout implements View.OnClickListener{

    private TextView mTvBack;
    private TextView mTvTitleText;
    private ImageView mImgTopAdd;
    private ImageView mImgTopFilter;
    private OnNaviBarClickListener onNaviBarClickListener;


    public TitleLayout(Context context) {
        super(context,null);
    }

    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttributes(context,attrs,0);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_title_layout, null);
        mTvBack = (TextView) view.findViewById(R.id.tv_back);
        mTvTitleText = (TextView) view.findViewById(R.id.tv_title_text);
        mImgTopAdd = (ImageView) view.findViewById(R.id.img_top_add);
        mImgTopFilter = (ImageView) view.findViewById(R.id.img_top_filter);
        mTvBack.setOnClickListener(this);
        mImgTopAdd.setOnClickListener(this);
        mImgTopFilter.setOnClickListener(this);
//        setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT));
        addView(view);
    }

    private void initAttributes(Context context, AttributeSet attrs, int defStyle) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleLayout, defStyle, 0);
        String textTitle = typedArray.getString(R.styleable.TitleLayout_titleText);
        boolean showAdd = typedArray.getBoolean(R.styleable.TitleLayout_showAdd, false);
        boolean showFilter = typedArray.getBoolean(R.styleable.TitleLayout_showFilter, false);
        mTvTitleText.setText(textTitle);
        mImgTopAdd.setVisibility(showAdd?View.VISIBLE:View.GONE);
        if (showFilter&&showAdd) {
            mImgTopAdd.setVisibility(View.VISIBLE);
            mImgTopFilter.setVisibility(View.VISIBLE);
        }else if(showFilter&&!showAdd){
            mImgTopAdd.setVisibility(View.GONE);
            LayoutParams layoutParams = (LayoutParams) mImgTopFilter.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.setMargins(0, 0, 0, 0);
            mImgTopFilter.setLayoutParams(layoutParams);
        }else if(!showAdd&&!showFilter){
            mImgTopAdd.setVisibility(View.INVISIBLE);
            mImgTopFilter.setVisibility(View.INVISIBLE);
        }
        typedArray.recycle();
    }

    public void setTitle(String title){
        mTvTitleText.setText(title);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_back:
//                ToastUtil.showToast(DataApplication.getINSTANCE().getApplicationContext(),"");
                if (onNaviBarClickListener!=null) {
                    onNaviBarClickListener.onBackClick();
                }
                break;
            case R.id.img_top_add:
                if (onNaviBarClickListener!=null) {
                    onNaviBarClickListener.onAddClick();
                }
                ToastUtil.showToast(DataApplication.getInstance().getApplicationContext(),"新增");
                break;
            case R.id.img_top_filter:
                if (onNaviBarClickListener!=null) {
                    onNaviBarClickListener.onFilterClick();
                }
                ToastUtil.showToast(DataApplication.getInstance().getApplicationContext(),"筛选");
                break;
        }

    }

    public void setOnNaviBarClickListener(OnNaviBarClickListener onNaviBarClickListener) {
        this.onNaviBarClickListener = onNaviBarClickListener;
    }

    public interface OnNaviBarClickListener{
        void onBackClick();
        void onAddClick();
        void onFilterClick();
    }
}
