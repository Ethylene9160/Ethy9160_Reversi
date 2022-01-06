package com.base;

public interface PanelInter {
    /**
     * 让棋子移动的方法
     * 由于每个panel实现走起的方法略有不同，
     * 因此需要重写走棋的方法。<p>
     * 例如，人机、互联网、用户自己这几个不同的对战情况
     * 需要有不同的获取对方棋子的方法。
     * @return null
     * @author Ethylene9160
     */
    void moveChess();

    /**判定让手棋的方法
     * 提前预测让手棋的不同使用方法。
     * 由于代码行数不多，所以直接在panel里进行重写。
     * @return null
     * @author Ethylene9160
     */
    void judgePass();

    /**对自己的棋局的额外初始化
     * 每个面板都有额外的对应初始化功能。
     * @return null
     * @author Ethylene9160
     */
    void initForThis();

    /**
     * 每个面板可以根据自己的特性来选择性的实现它的步时和局时，
     * 如果某一方超过了这个限制，那么它应该被失败。
     * @author Ethylene9160
     * @param
     * @return null
     */
    void timeLimit();

    /**
     * 这是一个用来设置buttons的方法，使用的时候，把button的<b><code> Bound  Icon</code> 以及button要实现的功能</b>
     * 写入这个方法。
     * @return null
     * @author Ethylene9160
     */
    void setButtons();




}
