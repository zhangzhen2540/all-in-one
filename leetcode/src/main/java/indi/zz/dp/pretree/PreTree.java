package indi.zz.dp.pretree;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreTree {



    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();

        PreTree ptree = new PreTree();
        for (int i = 0; i < words.length; i++) {
            ptree.put(words[i]);
        }

        ptree.print();

        int needLen = words[0].length() * words.length;
        for (int i = 0; i < s.length() - needLen + 1; i++) {
            if (find(ptree, new HashMap<>(), s, i, i + needLen)) {
                res.add(i);
            }
        }

        return res;
    }

    private boolean find(PreTree ptree, Map<String, Integer> count, String s, int i, int end) {
        Node c = ptree.root;
        StringBuilder word = new StringBuilder();
        for (; i < end && i < s.length(); i++) {
            Node child = c.child(s.charAt(i));
            if (child == null) {
                return false;
            }
            if (child.word) {
                count.put(word.toString(), count.getOrDefault(word, 0) + 1);
                boolean b = find(ptree, count, s, i + word.length(), end);
                count.put(word.toString(), count.get(word) - 1);
                if (b) {
                    return b;
                }
            }
        }
        return i >= end;
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(new PreTree().findSubstring("f", new String[]{ "f"})));;
//        System.out.println(JSON.toJSONString(new PreTree().findSubstring("barfoothefoobarman", new String[]{ "foo","bar"})));;
    }

    private Node root;

    public Node match(String s, int i) {
        Node cur = this.root;

        for (; i < s.length(); i++) {
            char c = s.charAt(i);
            Node child = cur.child(c);
            if (child == null) {
                break;
            }

            if (child.word) {
                return child;
            }
        }
        return null;
    }

    public PreTree() {
        this.root = new Node((char) 0);
    }

    public void print() {
        Node cur = this.root;

        for (Node node : cur.children) {
            if (node == null) {
                continue;
            }
            print(node, new StringBuilder().append(node.value));
        }
    }

    private void print(Node cur, StringBuilder p) {
        if (cur == null) {
            return;
        }
        for (Node node : cur.children) {
            if (node == null) {
                continue;
            }
            Character key = node.value;
            p.append(key);

            if (node.isWord()) {
                System.out.printf("%s \t\t %d\n", p, node.wordCount);
            }
            print(node, p);
            p.deleteCharAt(p.length() - 1);
        }
    }

    public void put(String word) {
        Node cur = this.root;
        for (int i = 0; i < word.length(); i++) {

            char c = word.charAt(i);
            cur = cur.addChild(c, i == word.length() - 1);

        }
    }

    @Data
    public static class Node {

        public Node(char value) {
            this(value, false);
        }

        public Node(char value, boolean word) {
            this.value = value;
            this.word = word;
        }

        public Node child(char value) {
            return children[value - 'a'];
        }

        public Node addChild(char value, boolean end) {
            int idx = value - 'a';
            Node node = children[idx];
            if (node == null) {
                node = new Node(value);
                this.children[idx] = node;
            }

            if (end) {
                node.word = end;
                node.wordCount++;
            }

            return node;
        }

        private char value;

        private boolean word;

        private int wordCount;

        private Node[] children = new Node[26];
    }

}
