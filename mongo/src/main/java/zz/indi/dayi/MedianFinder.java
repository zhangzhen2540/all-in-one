package zz.indi.dayi;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MedianFinder {

    private PriorityQueue<Integer> left;
    private PriorityQueue<Integer> right;

    public static void main(String[] args) {
        MedianFinder medianFinder = new MedianFinder();
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        System.out.println(medianFinder.findMedian());
        medianFinder.addNum(3);
        System.out.println(medianFinder.findMedian());
    }

    public MedianFinder() {
        this.left = new PriorityQueue<>(Comparator.reverseOrder());
        this.right = new PriorityQueue<>(Integer::compareTo);
    }

    public void addNum(int num) {
        if (this.left.isEmpty() || num <= this.left.peek()) {
            this.left.add(num);
        } else {
            this.right.add(num);
        }

        int diff = this.left.size() - this.right.size();
        if (diff > 1) {
            this.right.add(this.left.poll());
        } else if (diff < 0) {
            this.left.add(this.right.poll());
        }
    }

    public double findMedian() {
        if (this.left.size() > this.right.size()) {
            return this.left.peek();
        }
        return ((double) this.left.peek() + this.right.peek()) / 2;
    }
}
