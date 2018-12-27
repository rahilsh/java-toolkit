package in.array;

public class FindRotationPoint {

  public static void main(String[] args) {
    int[] array = {4, 5, 1, 2, 3};
    findRotationPoint(array);
  }

  private static void findRotationPoint(int[] A) {
    int start = 0, end = A.length - 1, mid;
    mid = start + (end - start) / 2;
    int last = A[A.length - 1];
    while (start <= end) {
      if (A[mid] > last) {
        start = mid + 1;
      } else if (A[mid] < last) {
        end = mid - 1;
      } else {
        break;
      }
      mid = start + (end - start) / 2;
    }
    System.out.println(mid);
  }

}
