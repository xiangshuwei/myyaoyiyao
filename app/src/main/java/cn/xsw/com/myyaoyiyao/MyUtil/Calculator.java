package cn.xsw.com.myyaoyiyao.MyUtil;

public class Calculator {

    public static int combination(int m, int n) {            //Cmn，组合算法
        int k = 1;
        int j = 1;
        for (int i = n; i >= 1; i--) {
            k = k * m;
            j = j * n;
            m--;
            n--;
        }
        return k / j;
    }

    public static long calculateBetNum(int redBallNum, int blueBallNum) {            //根据红篮球数，算出投注注数
        return combination(redBallNum, 6) * blueBallNum;
    }
}
