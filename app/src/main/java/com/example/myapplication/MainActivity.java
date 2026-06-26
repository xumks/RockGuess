package com.example.myapplication;

// 导入Android系统需要的类
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

// 导入AndroidX库中的类
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * 猜拳游戏主活动类
 * 继承自AppCompatActivity，实现View.OnClickListener接口处理点击事件
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // ============ 常量定义 ============
    // 玩家选择的常量：0=布，1=石头，2=剪刀
    private static final int CHOICE_PAPER = 0;    // 布
    private static final int CHOICE_ROCK = 1;     // 石头
    private static final int CHOICE_SCISSORS = 2; // 剪刀

    // 游戏结果的常量：0=赢，1=输，2=平局
    private static final int RESULT_WIN = 0;   // 玩家赢
    private static final int RESULT_LOSE = 1;  // 玩家输
    private static final int RESULT_DRAW = 2;  // 平局

    // ============ 界面组件变量 ============
    ImageView iv_computer;       // 显示电脑出拳的图片
    ImageView iv_user;           // 显示玩家出拳的图片
    ImageButton iv_paper;        // 布的按钮
    ImageButton iv_rock;         // 石头的按钮
    ImageButton iv_scissors;     // 剪刀的按钮
    TextView tv_result;          // 显示游戏结果的文本
    TextView tv_score_player;    // 显示玩家胜利次数
    TextView tv_score_computer;  // 显示电脑胜利次数
    TextView tv_score_draw;      // 显示平局次数

    // 图片资源数组：按顺序对应 布、石头、剪刀 的图片
    int[] imgSrc = {R.drawable.iv_paper, R.drawable.iv_rock, R.drawable.iv_scissors};

    // ============ 游戏统计变量 ============
    int playerWins = 0;   // 玩家胜利次数
    int computerWins = 0; // 电脑胜利次数
    int draws = 0;        // 平局次数

    /**
     * Activity创建时执行的方法，相当于程序的入口
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 启用全屏边缘到边缘显示
        EdgeToEdge.enable(this);
        // 设置布局文件
        setContentView(R.layout.activity_main);
        
        // 处理系统状态栏的间距，避免内容被遮挡
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ============ 初始化界面组件 ============
        // 通过ID找到布局文件中的组件
        iv_computer = findViewById(R.id.iv_computer);
        iv_user = findViewById(R.id.iv_user);
        iv_paper = findViewById(R.id.iv_paper);
        iv_rock = findViewById(R.id.iv_rock);
        iv_scissors = findViewById(R.id.iv_scissors);
        tv_result = findViewById(R.id.tv_result);
        tv_score_player = findViewById(R.id.tv_score_player);
        tv_score_computer = findViewById(R.id.tv_score_computer);
        tv_score_draw = findViewById(R.id.tv_score_draw);

        // ============ 设置点击监听器 ============
        // 为三个按钮设置点击事件监听器，点击时会触发onClick方法
        iv_paper.setOnClickListener(this);
        iv_rock.setOnClickListener(this);
        iv_scissors.setOnClickListener(this);

        // 初始化显示比分
        updateScoreDisplay();
    }

    /**
     * 点击事件处理方法，当用户点击按钮时会自动调用
     * @param v 被点击的View（按钮）
     */
    @Override
    public void onClick(View v) {
        // 初始化玩家选择为-1（表示未选择）
        int playerChoice = -1;

        // 判断用户点击了哪个按钮
        if (v.getId() == R.id.iv_paper) {
            // 用户选择了布
            playerChoice = CHOICE_PAPER;
            // 在玩家区域显示布的图片
            iv_user.setImageResource(imgSrc[CHOICE_PAPER]);
        } else if (v.getId() == R.id.iv_rock) {
            // 用户选择了石头
            playerChoice = CHOICE_ROCK;
            // 在玩家区域显示石头的图片
            iv_user.setImageResource(imgSrc[CHOICE_ROCK]);
        } else if (v.getId() == R.id.iv_scissors) {
            // 用户选择了剪刀
            playerChoice = CHOICE_SCISSORS;
            // 在玩家区域显示剪刀的图片
            iv_user.setImageResource(imgSrc[CHOICE_SCISSORS]);
        }

        // 如果没有选择任何按钮，直接返回
        if (playerChoice == -1) {
            return;
        }

        // ============ 电脑随机出拳 ============
        // 生成0-2的随机数，代表电脑的选择
        int computerChoice = (int) (Math.random() * 3);
        // 在电脑区域显示电脑选择的图片
        iv_computer.setImageResource(imgSrc[computerChoice]);

        // ============ 判断输赢 ============
        // 调用judgeResult方法判断结果
        int result = judgeResult(playerChoice, computerChoice);
        // 显示结果对话框
        showResultDialog(result);
    }

    /**
     * 判断游戏结果的方法
     * 规则：布包石头，石头砸剪刀，剪刀剪布
     * @param player 玩家的选择（0=布，1=石头，2=剪刀）
     * @param computer 电脑的选择（0=布，1=石头，2=剪刀）
     * @return 结果（0=赢，1=输，2=平局）
     */
    private int judgeResult(int player, int computer) {
        // 如果玩家和电脑选择相同，返回平局
        if (player == computer) {
            return RESULT_DRAW;
        }
        // 判断玩家赢的情况：
        // 玩家出布(0)且电脑出石头(1)，或
        // 玩家出石头(1)且电脑出剪刀(2)，或
        // 玩家出剪刀(2)且电脑出布(0)
        if ((player == CHOICE_PAPER && computer == CHOICE_ROCK) ||
            (player == CHOICE_ROCK && computer == CHOICE_SCISSORS) ||
            (player == CHOICE_SCISSORS && computer == CHOICE_PAPER)) {
            return RESULT_WIN;
        }
        // 其他情况都是玩家输
        return RESULT_LOSE;
    }

    /**
     * 显示结果对话框的方法
     * @param resultType 结果类型（0=赢，1=输，2=平局）
     */
    private void showResultDialog(int resultType) {
        String resultMessage;  // 结果消息文本
        int iconResId;         // 对话框图标资源ID

        // 根据结果类型设置消息和图标
        switch (resultType) {
            case RESULT_WIN:
                // 玩家赢
                resultMessage = getString(R.string.result_win);
                iconResId = R.drawable.outline_alarm_on_24;
                playerWins++;  // 玩家胜利次数加1
                break;
            case RESULT_LOSE:
                // 玩家输
                resultMessage = getString(R.string.result_lose);
                iconResId = R.drawable.outline_alarm_pause_24;
                computerWins++;  // 电脑胜利次数加1
                break;
            default:
                // 平局
                resultMessage = getString(R.string.result_draw);
                iconResId = R.drawable.outline_alarm_smart_wake_24;
                draws++;  // 平局次数加1
                break;
        }

        // 更新比分显示
        updateScoreDisplay();

        // ============ 创建对话框 ============
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dialog_title);           // 设置标题
        builder.setIcon(iconResId);                        // 设置图标
        builder.setMessage(resultMessage + "\n" + getString(R.string.dialog_restart_question));  // 设置消息

        // "确定"按钮：重新开始游戏
        builder.setPositiveButton(R.string.dialog_positive, (dialog, which) -> {
            // 重置电脑和玩家的图片为初始状态
            iv_computer.setImageResource(R.drawable.iv_org);
            iv_user.setImageResource(R.drawable.iv_org);
            // 重置结果文本
            tv_result.setText(R.string.result_initial);
        });

        // "取消"按钮：保持当前状态
        builder.setNegativeButton(R.string.dialog_negative, (dialog, which) -> {
            // 在结果文本中显示当前结果
            tv_result.setText(resultMessage);
        });

        // 创建并显示对话框
        builder.create().show();
    }

    /**
     * 更新比分显示的方法
     * 将最新的胜利、失败、平局次数显示在对应的TextView上
     */
    private void updateScoreDisplay() {
        tv_score_player.setText(getString(R.string.score_player, playerWins));
        tv_score_computer.setText(getString(R.string.score_computer, computerWins));
        tv_score_draw.setText(getString(R.string.score_draw, draws));
    }
}