package com.jinggan.library.ui.date.listener;


import com.jinggan.library.ui.date.bean.DateBean;
import com.jinggan.library.ui.date.bean.DateType;

/**
 * Created by codbking on 2016/9/22.
 */

public interface WheelPickerListener {
     void onSelect(DateType type, DateBean bean);
}
