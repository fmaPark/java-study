public class RandomArraySearch implements Runnable {
    private int[] array;
    private int targetValue;
    private int startIndex;
    private int endIndex;
    private boolean found;

    public RandomArraySearch(int[] array, int targetValue, int startIndex, int endIndex) {
        this.array = array;
        this.targetValue = targetValue;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.found = false;
    }

    public boolean isFound() {
        return found;
    }

    @Override
    public void run() {
        for (int i = startIndex; i < endIndex; i++) {
            if (array[i] == targetValue) {
                System.out.println("값 " + targetValue + "을(를) 인덱스 " + i + "에서 찾았습니다.");
                found = true;
                return;
            }
        }
    }
}
