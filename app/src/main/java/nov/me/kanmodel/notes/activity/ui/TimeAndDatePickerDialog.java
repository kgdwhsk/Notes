package nov.me.kanmodel.notes.activity.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

import nov.me.kanmodel.notes.R;

/**
 * 日期时间选择器
 * Created by KanModel on 2017/12/29.
 */
public class TimeAndDatePickerDialog {
    private Context mContext;
    private AlertDialog.Builder mAlertDialog;
    private int mHour, mMinute;
    private TimePickerDialogInterface timePickerDialogInterface;
    private TimePicker mTimePicker;
    private DatePicker mDatePicker;
    private int mTag = 0;
    private int mYear, mDay, mMonth;

    public TimeAndDatePickerDialog(Context context) {
        mContext = context;
        timePickerDialogInterface = (TimePickerDialogInterface) context;
    }

    /**
     * 初始化DatePicker
     */
    private View initDatePicker() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.time_picker_dialog, null);
        mDatePicker = inflate.findViewById(R.id.date_picker);
        inflate.findViewById(R.id.time_picker).setVisibility(View.GONE);
        resizePicker(mDatePicker);
        return inflate;
    }

    /**
     * 初始化TimePicker
     */
    private View initTimePicker() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.time_picker_dialog, null);
        mTimePicker = inflate.findViewById(R.id.time_picker);
        inflate.findViewById(R.id.date_picker).setVisibility(View.GONE);
        mTimePicker.setIs24HourView(true);
        resizePicker(mTimePicker);
        return inflate;
    }

    private View initDateAndTimePicker() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.time_picker_dialog, null);
        mTimePicker = inflate.findViewById(R.id.time_picker);
        mDatePicker = inflate.findViewById(R.id.date_picker);
        mTimePicker.setIs24HourView(true);
        resizePicker(mTimePicker);
        resizePicker(mDatePicker);
        return inflate;
    }

    /**
     * 创建dialog
     */
    private void initDialog(View view) {
        mAlertDialog.setPositiveButton("确定",
                new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        if (mTag == 0) {
                            getTimePickerValue();
                        } else if (mTag == 1) {
                            getDatePickerValue();
                        } else if (mTag == 2) {
                            getDatePickerValue();
                            getTimePickerValue();
                        }
                        timePickerDialogInterface.positiveListener();
                    }
                });
        mAlertDialog.setNegativeButton("取消",
                new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        timePickerDialogInterface.negativeListener();
                        dialog.dismiss();
                    }
                });
        mAlertDialog.setView(view);
    }

    /**
     * 显示时间选择器
     */
    public void showTimePickerDialog() {
        mTag = 0;
        View view = initTimePicker();
        mAlertDialog = new AlertDialog.Builder(mContext);
        mAlertDialog.setTitle("选择时间");
        initDialog(view);
        mAlertDialog.show();

    }

    /**
     * 显示日期选择器
     */
    public void showDatePickerDialog() {
        mTag = 1;
        View view = initDatePicker();
        mAlertDialog = new AlertDialog.Builder(mContext);
        mAlertDialog.setTitle("选择时间");
        initDialog(view);
        mAlertDialog.show();
    }

    /**
     * 显示日期时间选择器
     */
    public void showDateAndTimePickerDialog() {
        mTag = 2;
        View view = initDateAndTimePicker();
        mAlertDialog = new AlertDialog.Builder(mContext);
        mAlertDialog.setTitle("选择时间");
        initDialog(view);
        mAlertDialog.show();
    }

    /**
     * 调整numberPicker大小
     */
    private void resizeNumberPicker(NumberPicker np) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(120, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 0, 10, 0);
        np.setLayoutParams(params);
    }

    /**
     * 调整FrameLayout大小
     */
    private void resizePicker(FrameLayout tp) {
        List<NumberPicker> npList = findNumberPicker(tp);
        for (NumberPicker np : npList) {
            resizeNumberPicker(np);
        }
    }

    /**
     * 得到viewGroup里面的NumberPicker组件
     */
    private List<NumberPicker> findNumberPicker(ViewGroup viewGroup) {
        List<NumberPicker> npList = new ArrayList<>();
        View child;
        if (null != viewGroup) {
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                child = viewGroup.getChildAt(i);
                if (child instanceof NumberPicker) {
                    npList.add((NumberPicker) child);
                } else if (child instanceof LinearLayout) {
                    List<NumberPicker> result = findNumberPicker((ViewGroup) child);
                    if (result.size() > 0) {
                        return result;
                    }
                }
            }
        }
        return npList;
    }

    public int getYear() {
        return mYear;
    }

    public int getDay() {
        return mDay;
    }

    public int getMonth() {
        //返回的时间是0-11
        return mMonth + 1;
    }

    public int getMinute() {
        return mMinute;
    }

    public int getHour() {
        return mHour;
    }


    /**
     * 获取日期选择的值
     */
    private void getDatePickerValue() {
        mYear = mDatePicker.getYear();
        mMonth = mDatePicker.getMonth();
        mDay = mDatePicker.getDayOfMonth();
    }

    /**
     * 获取时间选择的值
     */
    private void getTimePickerValue() {
        // api23这两个方法过时
        mHour = mTimePicker.getCurrentHour();// timePicker.getHour();
        mMinute = mTimePicker.getCurrentMinute();// timePicker.getMinute();
    }


    public interface TimePickerDialogInterface {
        void positiveListener();

        void negativeListener();
    }
}