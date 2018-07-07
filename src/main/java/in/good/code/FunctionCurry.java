package in.good.code;

import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntUnaryOperator;

public class FunctionCurry {

  public static void main(String[] args) {
    IntBinaryOperator simplyAdd = (a, b) -> a + b;
    System.out.println(simplyAdd.applyAsInt(1, 2));

    IntFunction<IntUnaryOperator> curriedAdd = a -> b -> a + b;
    IntUnaryOperator addr4 = curriedAdd.apply(4);

    System.out.println(addr4.applyAsInt(5));
    System.out.println(addr4.applyAsInt(6));
  }
}
