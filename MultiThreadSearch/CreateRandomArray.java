import java.util.Random;

public class CreateRandomArray {
    // 배열 크기 고정
    private static final int ARRAY_SIZE = 100000;
    private int[] randomArray;

    // 생성자: 배열을 고정 크기로 초기화
    public CreateRandomArray() {
        this.randomArray = new int[ARRAY_SIZE];
    }

    public void generateRandomArray() {
        Random random = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            randomArray[i] = random.nextInt();
            // randomArray[i] = random.nextInt(1000);
        }
    }

    public int[] getRandomArray() {
        return randomArray;
    }
}
