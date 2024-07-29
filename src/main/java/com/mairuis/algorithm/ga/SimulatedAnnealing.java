package com.mairuis.algorithm.ga;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Mairuis
 * @since 7/29/2024
 */
public class SimulatedAnnealing {


    // 定义需要优化的目标函数
    public static double objectiveFunction(double x) {
        return x * x + 4 * Math.cos(x);
    }

    // 产生一个新的解
    public static double getNeighbor(double x, double step) {
        return x + (ThreadLocalRandom.current().nextDouble() * 2 - 1) * step;
    }

    // 模拟退火算法
    public static double simulatedAnnealing(double initialTemp, double finalTemp, double alpha, double step) {
        double temp = initialTemp;
        double currentSolution = ThreadLocalRandom.current().nextDouble() * 10 - 5; // 初始化当前解，范围在-5到5之间
        double bestSolution = currentSolution;

        while (temp > finalTemp) {
            double newSolution = getNeighbor(currentSolution, step);
            double currentEnergy = objectiveFunction(currentSolution);
            double newEnergy = objectiveFunction(newSolution);

            // 接受新的解的概率
            if (newEnergy < currentEnergy || Math.exp((currentEnergy - newEnergy) / temp) > ThreadLocalRandom.current().nextDouble()) {
                currentSolution = newSolution;
            }

            // 更新最佳解
            if (objectiveFunction(currentSolution) < objectiveFunction(bestSolution)) {
                bestSolution = currentSolution;
            }

            // 降低温度
            temp *= alpha;
        }

        return bestSolution;
    }

    public static void main(String[] args) {
        double initialTemp = 10000;
        double finalTemp = 1;
        double alpha = 0.9;
        double step = 0.1;

        double bestSolution = simulatedAnnealing(initialTemp, finalTemp, alpha, step);
        System.out.println("最佳解: " + bestSolution);
        System.out.println("最佳解对应的目标函数值: " + objectiveFunction(bestSolution));
    }
}
