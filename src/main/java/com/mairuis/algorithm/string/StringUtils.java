package com.mairuis.algorithm.string;

import com.mairuis.algorithm.analysis.Watch;

import java.util.Random;

/**
 * 字符串相关算法
 *
 * @author Mairuis
 * @date 2019/7/1
 */
public class StringUtils {

    public static int[][] buildDFA(String pattern) {
        int M = pattern.length();
        int R = 256;
        int[][] dfa = new int[R][M];
        dfa[pattern.charAt(0)][0] = 1;
        for (int X = 0, j = 1; j < M; j += 1) {
            for (int c = 0; c < R; c += 1) {
                dfa[c][j] = dfa[c][X];
            }
            dfa[pattern.charAt(j)][j] = j + 1;
            X = dfa[pattern.charAt(j)][X];
        }
        return dfa;
    }

    public static int kmpMatch(String txt, String pattern, int[][] dfa) {
        int j = 0, i = 0;
        for (; i < txt.length() && j < pattern.length(); i += 1) {
            assert dfa != null;
            j = dfa[txt.charAt(i)][j];
        }
        if (j == pattern.length()) {
            return i - pattern.length();
        }
        return -1;
    }

    public static int bruteMatch(String txt, String pattern) {
        int N = txt.length(), M = pattern.length();
        for (int i = 0; i < N; i += 1) {
            if (N - i >= M) {
                int j;
                for (j = 0; j < M; j += 1) {
                    if (txt.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                }
                if (j == M) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static void ahoCorasick(String string, String pattern) {

    }

    public static String[] randomString() {
        throw new UnsupportedOperationException();
    }

    public static String newRandomText(int length) {
        char[] characters = {
                'a', 'b', 'c', 'e', 'f',
                'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p',
                'q', 'r', 's', 't', 'u',
                'v', 'w', 'x', 'y', 'z',

                'A', 'B', 'C', 'E', 'F',
                'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P',
                'Q', 'R', 'S', 'T', 'U',
                'V', 'W', 'X', 'Y', 'Z'
        };
        char[] ch = new char[length];
        Random r = new Random();
        for (int i = 0; i < length; i += 1) {
            ch[i] = characters[r.nextInt(characters.length)];
        }
        return String.valueOf(ch);
    }

    public static void main(String[] args) {
        String text = newRandomText(1000000);
        String pattern = text.substring(text.length() - (text.length() / 3));
        Watch t = new Watch();
        int[][] dfa = buildDFA(pattern);
        t.begin();
        System.out.println(kmpMatch(text, pattern, dfa));
        System.out.println(t.end() + "ms");

        Watch t2 = new Watch();
        t2.begin();
        System.out.println(text.indexOf(pattern));
        System.out.println(t2.end() + "ms");
    }
}
