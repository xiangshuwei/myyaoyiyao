package cn.xsw.com.myyaoyiyao.MyUtil;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by XSW on 2017/5/4.
 */

public class RandomMadeBall {
    /**
     * 生成随机验证码的的方法
     *
     * @return 装载随机验证码的的数组randomword
     */
    public static ArrayList<String> getRedBall() {
        ArrayList<Integer> randomRed = new ArrayList<Integer>();
        for (int i = 0; i < 6; i++) {
            //Math.random()：产生一个[0，1)之间的随机数,大于等于0小于1的随机小数，返回值是double类型的
            int hong = (int) (Math.random() * 33);
            if (i == 0) {
                randomRed.add(hong);
            } else {
                if (randomRed.indexOf(hong) != -1) {
                    i--;
                } else {
                    randomRed.add(hong);
                }
            }
        }

        Collections.sort(randomRed);
        ArrayList<String> redBall = new ArrayList<String>();
        for (int k = 0; k < randomRed.size(); k++) {
            redBall.add(randomRed.get(k).toString());
        }
        return redBall;
    }

    /**
     * 生成随机验证码的的方法
     *
     * @return 装载随机验证码的的数组randomword
     */
    public static ArrayList<String> getBlueBall() {

        ArrayList<String> blueBall = new ArrayList<String>();
        for (int i = 0; i < 1; i++) {
            int blue = (int) (Math.random() * 16);
            if (i == 0) {
                blueBall.add(blue + "");
            } else {
                if (blueBall.get(i - 1) == blue + "") {
                    i--;
                } else {
                    blueBall.add(blue + "");
                }
            }
        }
        return blueBall;
    }
}
