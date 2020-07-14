package xyz.semoteo.ready.utils;

import java.util.Random;

public class RandomUtils {
  static Random rn = new Random();
  
  public static int randomInt(int min, int max) {
    return min + rn.nextInt(max - min);
  }
  
  public static int randomNORPInt(int max) {
    return rn.nextInt(max) * (rn.nextBoolean() ? -1 : 1);
  }
  
  public static int randomNext(int max) {
    return rn.nextInt(max);
  }
}
