# webmove

这个包的代码行数是：967行（含空行和注释）。(2021/12/17)



数字传输的含义：（应当大于88）

**游戏开始前：**

90：对方已准备(finished)

93：接受连接邀请(finished)

92：发出连接邀请(finished)

98：网络错误：没有连接到对手（对手不存在）（finished）

91：对方正在游戏中（finished）

**任何时候都要有**



**连接之后需要：**

96：对方掉线了；isStart = false; isConnect = false;isOpponent = false;(finished)

**游戏过程中：**

99：超时；(finished)

95：颜色不符；(finished)

94：未知原因产生的网络错误；退出重来（finished）

89：认输(finished)

9: 接收服务器即将关闭的消息（finished）

10：网络错误，没有连接到服务器;(在另一个代码)

8:对手离开房间（finished）

7:对方初始化判断：(finished)







## 重点项目思路

### 1.邀请连接

邀请方点击“连接”邀请发送消息，内容为`“92”+yourID`，接收方 **如果没有建立连接**，那么接受这个消息，接受的时候判断这个消息，如果 **这个消息的长度大于2**，那么将这个消息拆分为`92` `opponentID`，判断钱一个消息是否是92，然后弹出“opponentID想要连接你”的ID。

### 2.接受邀请

接收方接到邀请后，点击“确定”然后发送“93”，同时isConnect；客户机收到93后，isConect = true。

**总结：**接收到93，那么isConnect；接收到92并按下确定，那么isConnect。

