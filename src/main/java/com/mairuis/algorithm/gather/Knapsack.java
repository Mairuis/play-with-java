package com.mairuis.algorithm.gather;

/**
 * @author Mairuis
 * @since 9/20/2024
 */
public class Knapsack {

    // 动态规划求解0-1背包问题
    public static int solveKnapsack(int[] itemWeights, int[] itemValues, int maxCapacity) {
        int itemCount = itemWeights.length;

        // 创建dp数组，dp[i][capacity]表示前i个物品在容量为capacity的情况下的最大价值
        int[][] dp = new int[itemCount + 1][maxCapacity + 1];

        // 填充dp数组
        for (int currentItem = 1; currentItem <= itemCount; currentItem++) {
            for (int currentCapacity = 0; currentCapacity <= maxCapacity; currentCapacity++) {
                // 如果不选择当前物品
                dp[currentItem][currentCapacity] = dp[currentItem - 1][currentCapacity];

                // 如果选择当前物品，前提是容量足够
                final int itemWeight = itemWeights[currentItem - 1];
                final int itemValue = itemValues[currentItem - 1];
                if (currentCapacity >= itemWeight) {
                    dp[currentItem][currentCapacity] = Math.max(dp[currentItem][currentCapacity], dp[currentItem - 1][currentCapacity - itemWeight] + itemValue);
                }
            }
        }

        // dp[itemCount][maxCapacity]是最终的最大价值
        return dp[itemCount][maxCapacity];
    }

    public static void main(String[] args) {
        // 物品的重量数组
        int[] itemWeights = {2, 3, 4, 5};
        // 物品的价值数组
        int[] itemValues = {3, 4, 5, 6};
        // 背包的最大容量
        int maxCapacity = 5;

        // 调用函数求解0-1背包问题的最大价值
        int maxTotalValue = solveKnapsack(itemWeights, itemValues, maxCapacity);

        // 输出结果
        System.out.println("最大总价值: " + maxTotalValue);
    }
}
