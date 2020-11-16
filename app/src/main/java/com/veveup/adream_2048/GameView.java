package com.veveup.adream_2048;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by veve on 14/10/2017.
 */

/**
 * 继承GridLaout 增加游戏需要的方法和属性
 */
public class GameView extends GridLayout {
    private Card[][] cardsMap = new Card[4][4];
    private List<Point> emptyPoints = new ArrayList<Point>();

    public GameView(Context context) {
        super(context);
        initgame();
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initgame();
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initgame();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public GameView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initgame();
    }

    /**
     * 初始化游戏方法
     */
    private void initgame() {
        Log.d("Tag", "initgame is run");
        setColumnCount(4);
        setBackgroundColor(0xFFBBADA0);
        // 页面Touch方法注册 识别手势方向 根据按下位置和抬起位置
        setOnTouchListener(new OnTouchListener() {
            float starx = 0, stary = 0, offsetx, offsety;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                // Log.d("Tag","Ontouch is run"+motionEvent.getAction()+MotionEvent.ACTION_UP+"       "+motionEvent.getX());
                switch (motionEvent.getAction()) {
                    // 按下将坐标记录
                    case MotionEvent.ACTION_DOWN:
                        starx = motionEvent.getX();
                        stary = motionEvent.getY();
                        break;
                    // 抬起手根据 x y 相对坐标判断手势的方向
                    case MotionEvent.ACTION_UP:
                        offsetx = motionEvent.getX() - starx;
                        offsety = motionEvent.getY() - stary;
                        if (Math.abs(offsetx) > Math.abs(offsety)) {
                            if (offsetx < -5) {
                                swipeleft();
                            } else if (offsetx > 5) {
                                swiperight();
                            }
                        } else {
                            if (offsety < -5) {
                                swipeup();
                            } else if (offsety > 5) {
                                swipedown();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }

        });
        //Log.d("Tag",getMeasuredHeight()+"   "+getMeasuredWidth());
        //int cardWidth =(Math.min(getWidth(),getHeight())-10)/4;
        //addCards(cardWidth,cardWidth);
        //startGame();
        //removeView(v);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int cardWidth = ((wm.getDefaultDisplay().getWidth())) / 4;
        addCards(cardWidth, cardWidth);
        startGame();
        Log.d("Tag", "startGame;");
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("Tag", "OnsizeChanged is run");
        int cardWidth = (Math.min(w, h) - 10 / 4);
        Log.d("Tag", cardWidth + "");
        // addCards(cardWidth,cardWidth);
        // startGame();

    }

    /**
     * 根据card的高度和宽度 实例化 16 个Card
     *
     * @param cardWidth  card宽度
     * @param cardHeight card高度
     */
    private void addCards(int cardWidth, int cardHeight) {
        Card c;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Log.d("Tag", "addCard is run");
                c = new Card(getContext());
                c.setNum(0);
                addView(c, cardWidth, cardHeight);
                cardsMap[j][i] = c;
            }
        }

    }

    /**
     * 初始化准备好之后 调用方法
     */
    private void startGame() {
        // 将原来存在值得都初始化为 0
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                cardsMap[y][x].setNum(0);
            }
        }
        // 开始的时候 随机的添加一部分 数字
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();
        addRandomNum();


    }

    /**
     * 随机的添加一个数字到面板
     */
    private void addRandomNum() {
        // 判断为0的card 然后添加到 为空的list中
        emptyPoints.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (cardsMap[j][i].getNum() <= 0) {
                    emptyPoints.add(new Point(j, i));
                }
            }
        }
        // 从为空的list中取出一个 Point
        Point p = emptyPoints.remove((int) (Math.random() * emptyPoints.size()));
        // 按照 9：1的比例随机添加 2：4
        cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
        Log.d("Tag", "Random");
    }


    /**
     * 左滑时 执行的方法
     */
    private void swipeleft() {
        // 是否有两个相同且左右相邻的数存在 若存在则可以执行操作 反之不做任何操作
        boolean merge = false;
        Log.d("Tag", "swipe left");
// 横向遍历每一行 判断是否有空的Card或者相同的数字 分别执行滑动操作 或者 合并操作
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

                for (int x1 = x + 1; x1 < 4; x1++) {

                    Log.d("Tag", x + "   " + y);

                    if (cardsMap[x1][y].getNum() > 0) {


                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            merge = true;
                            x--;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());
                            merge = true;
                        }
                        break;
                    }
                }
            }
        }
        // 是否有操作发生 若发生 则随机添加一个数字 然后 检测游戏是否结束
        if (merge) {
            //addRandomNum();
            addRandomNum();
            checkComplete();
        }
    }

    /**
     * 右滑时 执行的方法
     */
    private void swiperight() {
        boolean merge = false;
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int x1 = x - 1; x1 >= 0; x1--) {
                    if (cardsMap[x1][y].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum());
                            cardsMap[x1][y].setNum(0);
                            merge = true;
                            x++;
                        } else if (cardsMap[x][y].equals(cardsMap[x1][y])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x1][y].setNum(0);

                            merge = true;
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());

                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            //  addRandomNum();
            addRandomNum();
            checkComplete();
        }

    }

    /**
     * 上滑时 执行的方法
     */
    private void swipeup() {
        boolean merge = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int y1 = y + 1; y1 < 4; y1++) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y--;
                            merge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);

                            merge = true;
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());

                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            //addRandomNum();
            addRandomNum();
            checkComplete();
        }

    }

    /**
     * 下滑时 执行的方法
     */
    private void swipedown() {
        boolean merge = false;
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int y1 = y - 1; y1 >= 0; y1--) {
                    if (cardsMap[x][y1].getNum() > 0) {
                        if (cardsMap[x][y].getNum() <= 0) {
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum());
                            cardsMap[x][y1].setNum(0);
                            y++;
                            merge = true;
                        } else if (cardsMap[x][y].equals(cardsMap[x][y1])) {
                            cardsMap[x][y].setNum(cardsMap[x][y].getNum() * 2);
                            cardsMap[x][y1].setNum(0);

                            merge = true;
                            MainActivity.getMainActivity().addScore(cardsMap[x][y].getNum());

                        }
                        break;
                    }
                }
            }
        }
        if (merge) {
            //addRandomNum();
            addRandomNum();
            checkComplete();
        }

    }

    /**
     * 每次操作过后 检查是否游戏结束
     */
    public void checkComplete() {

        boolean complete = true;
        // 遍历整个面板 只要遇到 为空的面板 或者相邻的两个Card值相等 则还可以继续操作
        All:
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                if (cardsMap[x][y].getNum() == 0 ||
                        (x > 0 && cardsMap[x][y].equals(cardsMap[x - 1][y])) ||
                        (x < 3 && cardsMap[x][y].equals(cardsMap[x + 1][y])) ||
                        (y > 0 && cardsMap[x][y].equals(cardsMap[x][y - 1])) ||
                        (y < 3 && cardsMap[x][y].equals(cardsMap[x][y + 1]))) {
                    complete = false;
                    break All;
                }
            }
        }
        // 若没有下一步 操作 则弹出一个提醒框 提示游戏结束 然后stattGame
        if (complete) {
//            Intent intent = new Intent(getContext(), MainActivity.class);
//            getContext().startActivity(intent);
            new AlertDialog.Builder(getContext()).setTitle("Sorry!").setMessage("Games Over!").setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    MainActivity.getMainActivity().clearScore();
//                    startGame();
                }
            }).show();
            MainActivity.getMainActivity().clearScore();
            startGame();
        }
    }
}
