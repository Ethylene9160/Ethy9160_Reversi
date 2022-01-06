package com.ethy9160.AI;

import java.util.ArrayList;
//没用
public class ResRoute {
    public ArrayList<Integer> steps;
    public ArrayList<Integer> scores;

    public ResRoute(int step, int score) {
        this.steps.add(step);
        this.scores.add(score);
    }
}
