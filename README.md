# 猜拳游戏 (RockGuess)

一个基于 Android 的石头剪刀布猜拳游戏，玩家可以与电脑进行猜拳对决，并记录胜负平的比分统计。

## 🎮 功能特性

- **猜拳对决**：玩家选择石头、剪刀或布，与电脑进行随机对决
- **比分统计**：实时记录玩家胜利、电脑胜利和平局的次数
- **结果显示**：通过对话框清晰展示每局的胜负结果
- **重新开始**：支持随时重置游戏，回到初始状态
- **精美界面**：采用卡片式设计，紫色主题配色，视觉效果美观

## 🛠️ 技术栈

- **开发语言**：Java
- **开发框架**：Android SDK (API 24+)
- **UI组件**：ConstraintLayout, CardView, Material Design
- **构建工具**：Gradle 8.13

## 📱 运行方式

### 前置条件

- Android Studio 2023.1.1 (Hedgehog) 或更高版本
- JDK 11 或更高版本
- Android SDK API 36

### 步骤

1. 克隆项目到本地：
   ```bash
   git clone https://github.com/xumks/RockGuess.git
   ```

2. 打开 Android Studio，选择 `Open` 打开项目

3. 等待 Gradle 同步完成

4. 连接 Android 设备或启动模拟器

5. 点击 `Run` 按钮运行项目

## 📁 项目结构

```
RockGuess/
├── app/
│   ├── src/main/java/com/example/myapplication/
│   │   └── MainActivity.java      # 主活动，游戏核心逻辑
│   ├── src/main/res/
│   │   ├── drawable/              # 图片资源和按钮选择器
│   │   ├── layout/
│   │   │   └── activity_main.xml  # 主界面布局
│   │   ├── values/
│   │   │   ├── colors.xml         # 颜色配置
│   │   │   ├── strings.xml        # 字符串资源
│   │   │   └── themes.xml         # 主题样式
│   │   └── values-night/
│   │       └── themes.xml         # 暗色主题
│   └── build.gradle               # 模块构建配置
├── gradle/                        # Gradle 配置
├── build.gradle                   # 项目构建配置
├── settings.gradle                # 项目设置
└── gradlew.bat                    # Gradle 运行脚本
```

## 🏆 游戏规则

- **布 (Paper)** 包 **石头 (Rock)** → 布赢
- **石头 (Rock)** 砸 **剪刀 (Scissors)** → 石头赢
- **剪刀 (Scissors)** 剪 **布 (Paper)** → 剪刀赢
- 双方选择相同 → 平局

## 📝 代码优化说明

### 原始代码问题

1. **代码重复严重**：三个按钮的点击事件处理逻辑几乎相同，重复了约 130 行代码
2. **注释不准确**：部分注释与实际逻辑不符（如注释说"手机布"但实际是锤子）
3. **硬编码文本**：所有中文文本直接写在代码中，不利于国际化
4. **缺少计分功能**：没有记录胜负平的统计数据
5. **UI 布局简陋**：界面排列不够整齐，缺乏视觉层次感

### 优化改进

1. **消除代码重复**：
   - 抽取 `judgeResult()` 方法统一判断输赢逻辑
   - 抽取 `showResultDialog()` 方法统一显示结果对话框
   - 使用常量代替魔法数字（`CHOICE_PAPER`, `CHOICE_ROCK`, `CHOICE_SCISSORS`）

2. **添加计分功能**：
   - 新增 `playerWins`, `computerWins`, `draws` 变量记录比分
   - 新增 `updateScoreDisplay()` 方法更新比分显示
   - 比分卡片实时展示统计数据

3. **UI 美化**：
   - 使用 CardView 包裹电脑和玩家区域，提升视觉层次
   - 添加紫色主题配色方案
   - 为按钮添加圆形选择器，点击时有视觉反馈
   - 重新布局，使界面更加整齐美观

4. **代码规范**：
   - 将所有硬编码文本移到 `strings.xml` 资源文件中
   - 添加详细的中文注释，便于初学者理解
   - 统一变量命名风格

## 📄 许可证

MIT License

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！