package com.mairuis.algorithm.gather;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Mairuis
 * @since 9/20/2024
 */
public class MinHeap {
    private List<Integer> heap;

    // 构造函数，初始化空堆
    public MinHeap() {
        heap = new ArrayList<>();
    }

    // 获取最小值（堆顶元素）
    public int peek() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap.get(0); // 堆顶就是最小值
    }

    // 插入一个新元素
    public void insert(int value) {
        heap.add(value); // 将新元素添加到堆的末尾
        siftUp(heap.size() - 1); // 上浮操作，保持最小堆的性质
    }

    // 删除并返回堆顶元素（最小值）
    public int remove() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }

        // 交换堆顶元素和最后一个元素
        int rootValue = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);

        // 下沉操作，重新调整堆的结构
        if (!heap.isEmpty()) {
            siftDown(0);
        }

        return rootValue; // 返回原来的最小值
    }

    // 打印堆的内容（用于调试）
    public void printHeap() {
        System.out.println(heap);
    }

    // 上浮操作，保持最小堆的性质
    private void siftUp(int index) {
        int parentIndex = (index - 1) / 2;

        // 当当前节点比父节点小，则交换，并继续上浮
        while (index > 0 && heap.get(index) < heap.get(parentIndex)) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    // 下沉操作，保持最小堆的性质
    private void siftDown(int index) {
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;
        int smallest = index;

        // 找到当前节点、左子节点、右子节点中的最小值
        if (leftChild < heap.size() && heap.get(leftChild) < heap.get(smallest)) {
            smallest = leftChild;
        }

        if (rightChild < heap.size() && heap.get(rightChild) < heap.get(smallest)) {
            smallest = rightChild;
        }

        // 如果当前节点不是最小值，则与最小值节点交换，继续下沉
        if (smallest != index) {
            swap(index, smallest);
            siftDown(smallest);
        }
    }

    // 交换两个索引对应的元素
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // 测试代码
    public static void main(String[] args) {
        MinHeap minHeap = new MinHeap();

        minHeap.insert(10);
        minHeap.insert(15);
        minHeap.insert(20);
        minHeap.insert(17);
        minHeap.insert(8);

        System.out.println("当前最小值 (堆顶): " + minHeap.peek());

        minHeap.printHeap(); // 打印当前堆

        System.out.println("删除最小值: " + minHeap.remove());
        minHeap.printHeap(); // 删除后的堆

        System.out.println("删除最小值: " + minHeap.remove());
        minHeap.printHeap(); // 再次删除后的堆
    }
}
