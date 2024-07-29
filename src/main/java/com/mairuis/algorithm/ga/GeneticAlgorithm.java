package com.mairuis.algorithm.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GeneticAlgorithm {

    // 个体类
    static class Individual {
        int[] genes;

        public Individual(int geneLength) {
            genes = new int[geneLength];
            for (int i = 0; i < genes.length; i++) {
                genes[i] = ThreadLocalRandom.current().nextInt(2);
            }
        }

        // 计算适应度
        public int fitness() {
            int fitness = 0;
            for (int gene : genes) {
                fitness += gene;
            }
            return fitness;
        }
    }

    // 遗传算法类
    static class Algorithm {
        static final int POPULATION_SIZE = 100;
        static final int GENE_LENGTH = 100;
        static final int MAX_GENERATIONS = 100;
        static final double MUTATION_RATE = 0.1;

        List<Individual> population = new ArrayList<>();

        // 初始化种群
        public void initializePopulation() {
            for (int i = 0; i < POPULATION_SIZE; i++) {
                population.add(new Individual(GENE_LENGTH));
            }
        }

        // 选择父母
        public Individual selectParent() {
            //elite select 策略，排序，然后从top3中随机选择
            final List<Individual> individuals = population.stream().sorted((o1, o2) -> o2.fitness() - o1.fitness()).limit(3).toList();
            return individuals.get(ThreadLocalRandom.current().nextInt(individuals.size()));
        }

        // 交叉
        public Individual crossover(Individual parent1, Individual parent2) {
            Individual child = new Individual(GENE_LENGTH);
            Random rand = new Random();
            for (int i = 0; i < GENE_LENGTH; i++) {
                if (rand.nextDouble() < 0.5) {
                    child.genes[i] = parent1.genes[i];
                } else {
                    child.genes[i] = parent2.genes[i];
                }
            }
            return child;
        }

        // 变异
        public void mutate(Individual individual) {
            for (int i = 0; i < GENE_LENGTH; i++) {
                if (ThreadLocalRandom.current().nextDouble() < MUTATION_RATE) {
                    individual.genes[i] = 1 - individual.genes[i];
                }
            }
        }

        // 进化
        public void evolve() {
            List<Individual> newPopulation = new ArrayList<>();
            for (int i = 0; i < POPULATION_SIZE; i++) {
                Individual parent1 = selectParent();
                Individual parent2 = selectParent();
                Individual child = crossover(parent1, parent2);
                mutate(child);
                newPopulation.add(child);
            }
            population = newPopulation;
        }

        // 获取种群中最优个体
        public Individual getBestIndividual() {
            Individual best = population.get(0);
            for (Individual individual : population) {
                if (individual.fitness() > best.fitness()) {
                    best = individual;
                }
            }
            return best;
        }
    }

    public static void main(String[] args) {
        Algorithm ga = new Algorithm();

        // 初始化种群
        ga.initializePopulation();

        // 进化若干代
        for (int generation = 0; generation < Algorithm.MAX_GENERATIONS; generation++) {
            System.out.println("Generation " + generation + " Best Fitness: " + ga.getBestIndividual().fitness());
            ga.evolve();
        }

        // 输出最优个体
        Individual best = ga.getBestIndividual();
        System.out.println("Best Individual: ");
        for (int gene : best.genes) {
            System.out.print(gene + " ");
        }
        System.out.println("\nBest Fitness: " + best.fitness());
    }
}
