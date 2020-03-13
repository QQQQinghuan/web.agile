package webTest;

import java.util.Arrays;

/**
 * @Author:chujibo
 * @Date:2020/3/13 17:19
 */
public class Poker {

    public static void main(String[] args) {

        String black = "2H 3D 5S 9C KD";
        String white = "2C 3H 4S 8C AH";
        String black1 = "2H 4S 4C 2D 4H";
        String white1 = "2S 8S AS QS 3S";
        String black2 = "2H 3D 5S 9C KD";
        String white2 = "2C 3H 4S 8C KH";
        String black3 = "2H 3D 5S 9C KD";
        String white3 = "2D 3H 5C 9S KH";
        System.out.println(compareFirst(black, white));
        System.out.println(compareFirst(black1, white1));
        System.out.println(compareFirst(black2, white2));
        System.out.println(compareFirst(black3, white3));

    }

    /**
     * 主函数
     * @param a
     * @param b
     * @return
     */
    public static String compareFirst(String a, String b) {

        // 同花顺＞铁支＞葫芦＞同花＞顺子＞三条＞两对＞对子＞散牌
        //   0    1    2   3    4    5    6    7    8
        int[] numA = preProgress(a);
        int[] numB = preProgress(b);

        int[] valueOfBlacak = reProcess(numA);
        int[] valueOfWhite = reProcess(numB);

        for (int i = 0; i < 9; i++) {
            // 不同类型的牌
            if (valueOfBlacak[i] != valueOfWhite[i]) {
                return valueOfBlacak[i] == 1? "Black wins.": "White wins";
                // 同类型的牌
            } else if (valueOfBlacak[i] == 1) {
                return compareSecond(numA, numB, i);
            }
        }
        return null;

    }

    /**
     * 预处理，将扑克从0-51标号 (可以将/4计为牌的大小，%4计为牌的花色)
     * @param a 按字符串输入牌
     * @return 返回一个从小到大排列的int[]数组
     */
    private static int[] preProgress(String a) {
        String[] oo = a.split(" ");
        String sb = "2D2S2H2C3D3S3H3C4D4S4H4C5D5S5H5C6D6S6H6C7D7S7H7C8D8S8H8C9D9S9H9C" +
                "TDTSTHTCJDJSJHJCQDQSQHQCKDKSKHKCADASAHAC";
        int[] res = new int[5];
        for (int i = 0; i < 5; i++) {
            res[i] = sb.indexOf(oo[i]) / 2;
        }
        Arrays.sort(res);
        return res;
    }

    /**
     * 同花顺＞铁支＞葫芦＞同花＞顺子＞三条＞两对＞对子＞散牌
     *   0    1    2   3    4    5    6    7    8
     * @param a
     * @return 牌的类型
     */
    private static int[] reProcess(int[] a) {
        int[] value = new int[9];
        // boolean flag = false;
        value[3] = isSameColor(a); // if (value[3] == 1) flag = true;
        value[4] = isJunko(a); // if (value[4] == 1) flag = true;
        value[0] = (value[3] == 1 && value[4] == 1)? 1: -1; // if (flag) return value;
        value[1] = isBoom(a); // if (value[1] == 1) return value;
        value[2] = isGourd(a); // if (value[2] == 1) return value;
        value[5] = isThree(a); // if (value[5] == 1) return value;
        value[6] = isDoubleTwo(a); // if (value[6] == 1) return value;
        value[7] = isSingleTwo(a); // if (value[7] == 1) return value;
        value[8] = 1;
        return value;

    }

    /**
     * 如果两种牌是同类型，则在该类型中进行判断
     * @param numA
     * @param numB
     * @param i [同花顺＞铁支＞葫芦＞同花＞顺子＞三条＞两对＞对子＞散牌]
     * @return
     */
    private static String compareSecond(int[] numA, int[] numB, int i) {
        switch (i) {
            case 0:
            case 4: return compareThird(numA[4], numB[4]);
            case 1:
            case 2:
            case 5: return numA[2] / 4 > numB[2] / 4? "Black wins.": "White wins.";
            case 3:
            case 8: return compareScatter(numA, numB);
            case 6: return compareScatter(reProcessDoubleTwo(numA), reProcessDoubleTwo(numB));
            case 7: return compareScatter(reProcessSingleTwo(numA), reProcessSingleTwo(numB));
            default: return null;
        }
    }

    private static String compareThird(int a, int b) {
        if (a / 4  > b / 4) return "Black wins.";
        else if (a / 4 < b / 4) return "White wins";
        return "Tie.";
    }

    /**
     * 判断是否为同花
     * @param a
     * @return
     */
    private static int isSameColor(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] % 4 != a[i + 1] % 4) {
                return -1;
            }
        }
        return 1;
    }

    /**
     * 判断是否为顺子
     * @param a
     * @return
     */
    private static int isJunko(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            if (a[i] / 4 - a[i - 1] / 4 != 1) {
                return -1;
            }
        }
        return 1;
    }

    /**
     * 判断是否为炸弹
     * @param a
     * @return
     */
    private static int isBoom(int[] a) {
        if (a[4]- a[1] == 3 || a[3] - a[0] == 3) return 1;
        else return -1;
    }

    /**
     * 判断是否为三带二
     * @param a
     * @return
     */
    private static int isGourd(int[] a) {
        if ((a[2] / 4 == a[0] / 4 && a[4] / 4 == a[3] / 4)
            || (a[4] / 4 == a[2] / 4 && a[1] / 4 == a[0] / 4)){
            return 1;
        }
        return -1;
    }

    /**
     * 三条带俩单
     * @param a
     * @return
     */
    private static int isThree(int[] a) {
        if ((a[2] / 4 == a[0] / 4 && a[4] / 4 != a[3] / 4)
            || (a[4] / 4 == a[2] / 4 && a[1] / 4 != a[0] / 4)
            || (a[3] / 4 == a[1] / 4)) return 1;
        return -1;
    }

    /**
     * 判断是否为两对
     * @param a
     * @return
     */
    private static int isDoubleTwo(int[] a) {
        if ((a[1] / 4 == a[0] / 4 && a[4] / 4 == a[3] / 4)
            || (a[1] / 4 == a[0] / 4 && a[3] / 4 == a[2] / 4)
            || (a[2] / 4 == a[1] / 4 && a[4] / 4 == a[3] / 4)) return 1;
        return -1;
    }

    /**
     * 再处理两对 [1,2,2,3,3] -> [1,2,3]
     * @param a
     * @return
     */
    private static int[] reProcessDoubleTwo(int[] a) {
        if (a[1] / 4 == a[0] / 4 && a[4] / 4 == a[3] / 4) {
            return new int[]{a[2], Math.min(a[1], a[4]), Math.max(a[1], a[4])};
        } else if (a[1] / 4 == a[0] / 4 && a[3] / 4 == a[2] / 4) {
            return new int[]{a[4], Math.min(a[1], a[2]), Math.max(a[1], a[2])};
        }
        return new int[]{a[0], Math.min(a[1], a[3]), Math.max(a[1], a[3])};
    }

    /**
     * 判断是否是一对
     * @param a
     * @return
     */
    private static int isSingleTwo(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i + 1] / 4 - a[i] / 4 == 0) {
                return 1;
            }
        }
        return -1;
    }

    /**
     * 再处理一对 [1,2,2,3,4] -> [1,3,4,2]
     * @param a
     * @return
     */
    private static int[] reProcessSingleTwo(int[] a) {
        int[] res = new int[4];
        int j = 0;
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] / 4 == a[i + 1] / 4) {
                res[3] = a[i++];
            } else {
                res[j++] = a[i];
            }
        }
        if (res[2] == 0) res[2] = a[4];
        return res;
    }

    /**
     * 散排/同花/两对/一对
     * @param a
     * @param b
     * @return
     */
    private static String compareScatter(int[] a, int[] b) {
        for (int i = a.length - 1; i >= 0; i--) {
            if (a[i] / 4 > b[i] / 4) {
                return "Black wins.";
            } else if (a[i] / 4 < b[i] / 4) {
                return "White wins.";
            }
        }
        return "Tie";
    }
}
