package zz.indi.dayi;

import java.util.Arrays;

public class MaxXOR {

    public static void main(String[] args) {

        System.out.println(new MaxXOR().findMaximumXOR(new int[]{0}));
        System.out.println(new MaxXOR().findMaximumXOR(new int[]{3}));
        System.out.println(new MaxXOR().findMaximumXOR(new int[]{2, 4}));
        System.out.println(new MaxXOR().findMaximumXOR(new int[]{16, 8}));
        System.out.println(new MaxXOR().findMaximumXOR(new int[]{8, 10, 2}));
    }

    public int findMaximumXOR(int[] nums) {
        int r = 0;

        Trie trie = new Trie();

        for (int i = 0; i < nums.length; i++) {
            trie.add(nums[i]);
            r = Math.max(r, trie.get(nums[i]));

//            r = Math.max(r, trie.get(nums[i]));
//            trie.add(nums[i]);
        }

        return r;
    }

    static class Trie {

        void print() {
            print(this.root, new StringBuilder());
        }
        void print(TrieNode cur, StringBuilder pre) {
            if (cur.child[0] == null && cur.child[1] == null) {
                System.out.printf("%s%d\n", pre, cur.v);
            }
            pre.append(cur.v);
            if (cur.child[0] != null) {
                print(cur.child[0], pre);
            }
            if (cur.child[1] != null) {
                print(cur.child[1], pre);
            }
            pre.deleteCharAt(pre.length() - 1);
        }

        TrieNode root;

        public Trie() {
            this.root = new TrieNode();
        }

        public void add(int num) {
            TrieNode p = this.root;

            for (int i = 31; i >= 0; i--) {
                int v = num >> i & 1;
                if (p.child[v] == null) {
                    p.child[v] = new TrieNode();
                }
                p.child[v].v = v;
                p = p.child[v];
            }
        }

        public int get(int num) {
            TrieNode cur = this.root;
            if (cur.child[0] == null && cur.child[1] == null) {
                return 0;
            }

            int res = 0;
            for (int i = 31; i >= 0; i--) {
                int v = num >> i & 1;

                TrieNode t;
                if (cur.child[0] == null) {
                    t = cur.child[1];
                } else if (cur.child[1] == null) {
                    t = cur.child[0];
                } else {
                    t = cur.child[1 - v];
                }

                res |= (t.v ^ v) << i;
                cur = t;
            }
            return res;
        }

        class TrieNode {
            TrieNode[] child = new TrieNode[2];

            int v;
        }
    }
}
