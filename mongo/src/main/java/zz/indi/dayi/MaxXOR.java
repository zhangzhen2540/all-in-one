package zz.indi.dayi;

import java.util.Arrays;

public class MaxXOR {

    public static void main(String[] args) {

        System.out.println(new MaxXOR().findMaximumXOR(new int[]{0}));
        System.out.println(new MaxXOR().findMaximumXOR(new int[]{2, 4}));
        System.out.println(new MaxXOR().findMaximumXOR(new int[]{16, 8}));
        System.out.println(new MaxXOR().findMaximumXOR(new int[]{8, 10 , 2}));
    }

    public int findMaximumXOR(int[] nums) {
        int r = 0;

        Trie trie = new Trie();
        trie.add(nums[0]);

        for (int i = 1; i < nums.length; i++) {
            int[] bit = bit(nums[i]);

            int tmp = 0;
            Trie.TrieNode tn = trie.root;
            for (int k = 0; k < bit.length; k++) {
                Trie.TrieNode tt;

                if (tn.child[1] == null) {
                    tt = tn.child[0];
                } else if (tn.child[0] == null) {
                    tt = tn.child[1];
                } else {
                    tt = tn.child[1 ^ bit[k]];
//                    if (bit[k] == 0) {
//                        tt = tn.child[1];
//                    } else {
//                        tt = tn.child[0];
//                    }
                }

                tmp = (tmp << 1 | (tt.v ^ bit[k]));
                tn = tt;
            }
            r = Math.max(r, tmp);

            trie.add(nums[i]);
        }

        return r;
    }

    private static int[] bit(int num) {
        int[] bit = new int[32];
        Arrays.fill(bit, 0);

        int i = 31;
        while (num > 0) {
            bit[i--] = (num & 1) == 1 ? 1 : 0;
            num = num >> 1;
        }

        return bit;
    }

    static class Trie {

        void print(TrieNode cur) {
            System.out.printf("%d", cur.v);
            if (cur.child[0] == null && cur.child[1] == null) {
                System.out.println();
            }
            if (cur.child[0] != null) {
                print(cur.child[0]);
            }
            if (cur.child[1] != null) {
                print(cur.child[1]);
            }
        }

        TrieNode root;


        public Trie() {
            this.root = new TrieNode();
        }

        public void add(int num) {
            int[] bit = bit(num);

//            for (int i = 0; i < bit.length; i++) {
//                System.out.printf("%d", bit[i]);
//            }
//            System.out.println(" >>>>>>>>> " + num);

            TrieNode p = this.root;
            for (int i = 0; i < bit.length; i++) {
                if (p.child[bit[i]] == null) {
                    p.child[bit[i]] = new TrieNode();
                    p.child[bit[i]].v = bit[i];
                }

                p = p.child[bit[i]];
            }
        }

        class TrieNode {
            TrieNode[] child = new TrieNode[2];

            int v;
        }
    }
}
