package indi.zz.dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Solution_472 {

    public static void main(String[] args) {
        System.out.println(new Solution_472().findAllConcatenatedWordsInADict(new String[]{
                "cat",
                "dog",
                "catdog"
        }));
    }

    public List<String> findAllConcatenatedWordsInADict(String[] words) {

        Arrays.sort(words, Comparator.comparingInt(String::length));

        PTree pt = new PTree();

        List<String> result = new ArrayList<>();
        for (String word : words) {
            if (search(pt, pt.root, word, 0) > 1) {
                result.add(word);
            } else {
                pt.add(word);
            }
        }
        return result;
    }

    private int search(PTree pt, PNode node, String word, int ind) {
        if (ind == word.length()) {
            return 1;
        }

        PNode cur = node;
        for (int i = ind; i < word.length(); i++) {
            cur = cur.next(word, i);
            if (cur == null) {
                return 0;
            }

            if (cur.word && i == word.length() - 1) {
                return 1;
            }

            if (cur.word) {
                int cnt = search(pt, pt.root, word, i + 1) + 1;
                if (cnt > 1) {
                    return cnt;
                }
            }
        }

        return 0;
    }



    class PTree {
        PNode root = new PNode();

        public void add(String word) {
            PNode cur = root;

            add(cur, word, 0);
        }

        private void add(PNode p, String word, int i) {
            if (i >= word.length()) {
                p.word = true;
                return;
            }

            char ch = word.charAt(i);
            if (p.nexts[conv(ch)] == null) {
                PNode tmp = new PNode();
                tmp.ch = ch;
                p.nexts[conv(tmp.ch)] = tmp;
            }

            add(p.nexts[conv(ch)], word, i + 1);
        }

    }
    private int conv(char c) {
        return c - 'a';
    }

    class PNode {
        char ch;
        boolean word = false;
        PNode[] nexts = new PNode[26];

        public PNode next(String word, int i) {
            return nexts[conv(word.charAt(i))];
        }
    }
}
