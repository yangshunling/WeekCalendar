package com.example.anonymous.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beiing.weekcalendar.WeekCalendar;
import com.beiing.weekcalendar.listener.DateSelectListener;
import com.beiing.weekcalendar.listener.GetViewHelper;
import com.beiing.weekcalendar.listener.WeekChangeListener;
import com.beiing.weekcalendar.utils.CalendarUtil;

import org.joda.time.DateTime;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeekCalendar weekCalendar = findViewById(R.id.week_calendar);

        /**
         * 自定义日历布局
         */
        weekCalendar.setGetViewHelper(new GetViewHelper() {
            @Override
            public View getDayView(int position, View convertView, ViewGroup parent, DateTime dateTime, boolean select) {
                //加载布局文件
                if(convertView == null){
                    convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_day, parent, false);
                }
                //初始化组件
                LinearLayout layout = convertView.findViewById(R.id.ll_day);
                TextView tvDay = convertView.findViewById(R.id.tv_day);
                ImageView ivPoint = convertView.findViewById(R.id.iv_point);
                //设置日期
                tvDay.setText(dateTime.toString("d"));
                //根据用户状态做不同操作
                if(CalendarUtil.isToday(dateTime) && select){
                    //日期是今天并且被选中
                    tvDay.setText("今");
                    tvDay.setTextColor(Color.WHITE);
                    layout.setBackgroundResource(R.drawable.circular_blue);

                } else if(CalendarUtil.isToday(dateTime)){
                    //日期是今天
                    tvDay.setText("今");
                    tvDay.setTextColor(Color.WHITE);
                    layout.setBackgroundResource(R.drawable.circular_blue);

                } else if(select){
                    //日期被选中
                    tvDay.setTextColor(Color.WHITE);
                    layout.setBackgroundResource(R.drawable.circular_blue);

                } else {
                    //不是今天并且没有被选中
                    tvDay.setTextColor(Color.BLACK);
                    layout.setBackgroundColor(Color.TRANSPARENT);

                }

                //根据具体业务常见选择显示或者隐藏原点
                ivPoint.setVisibility(View.VISIBLE);
                return convertView;
            }

            @Override
            public View getWeekView(int position, View convertView, ViewGroup parent, String week) {
                //加载头部星期几布局文件
                if(convertView == null){
                    convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_week, parent, false);
                }
                TextView tvWeek = convertView.findViewById(R.id.tv_week);
                //默认显示"星期X"，可以自行替换
                tvWeek.setText(week.replace("星期",""));
                return convertView;
            }
        });

        /**
         * 日期选择监听
         */
        weekCalendar.setDateSelectListener(new DateSelectListener() {
            @Override
            public void onDateSelect(DateTime selectDate) {
                Toast.makeText(MainActivity.this, "你选择的日期是：" + selectDate.toString("yyyy-MM-dd"), Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * 周变化监听
         */
        weekCalendar.setWeekChangedListener(new WeekChangeListener() {
            @Override
            public void onWeekChanged(DateTime firstDayOfWeek) {
                String text = "本周第一天:" + firstDayOfWeek.toString("yyyy年M月d日")
                        + ",本周最后一天:" + new DateTime(firstDayOfWeek).plusDays(6).toString("yyyy年M月d日");
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
