import java.util.Arrays;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        // 초기 메모리 사용량 측정
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        long startMemory = runtime.totalMemory() - runtime.freeMemory();

        // 랜덤 배열 생성 스레드
        CreateRandomArray arrayCreator = new CreateRandomArray();
        Thread arrayCreationThread = new Thread(() -> {
            arrayCreator.generateRandomArray();
            System.out.println("랜덤 배열 생성 완료.");
        });

        // 배열 생성 스레드 실행
        arrayCreationThread.start();

        // 배열 생성이 완료될 때까지 기다림
        try {
            arrayCreationThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 생성된 배열 가져오기
        int[] randomArray = arrayCreator.getRandomArray();
        System.out.println("생성된 랜덤 배열: " + Arrays.toString(randomArray));

        Scanner scanner = new Scanner(System.in);
        System.out.print("수행하고자 하는 일을 입력하세요 (검색 1 정렬 2): ");
        int taskValue = scanner.nextInt();
        
        if (taskValue == 1) {
            System.out.print("찾고자 하는 값을 입력하세요: ");
            int targetValue = scanner.nextInt();

            System.out.print("사용할 스레드의 개수를 입력하세요: ");
            int numThreads = scanner.nextInt();
            scanner.close();

            // 각 스레드의 작업 범위 계산
            int length = randomArray.length;
            int chunkSize = (length + numThreads - 1) / numThreads; // 스레드당 작업 범위 계산

            Thread[] threads = new Thread[numThreads];
            RandomArraySearch[] searchTasks = new RandomArraySearch[numThreads];

            // 검색 작업 시작 시간 기록
            long startTime = System.nanoTime();

            // 스레드 생성 및 실행
            for (int i = 0; i < numThreads; i++) {
                int startIndex = i * chunkSize;
                int endIndex = Math.min(startIndex + chunkSize, length);
                searchTasks[i] = new RandomArraySearch(randomArray, targetValue, startIndex, endIndex);
                threads[i] = new Thread(searchTasks[i]);
                threads[i].start();
            }

            // 스레드 종료 대기
            boolean found = false;
            for (int i = 0; i < numThreads; i++) {
                try {
                    threads[i].join();
                    if (searchTasks[i].isFound()) {
                        found = true;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 검색 작업 종료 시간 및 메모리 사용량 기록
            long endTime = System.nanoTime();
            long endUsedMemory = runtime.totalMemory() - runtime.freeMemory();

            if (!found) {
                System.out.println("값 " + targetValue + "을(를) 찾을 수 없습니다.");
            }

            // 소요 시간 계산 및 출력
            long duration = endTime - startTime;
            System.out.println("총 소요 시간: " + duration + " ns");

            // 메모리 사용량 계산 및 출력
            long usedMemory = endUsedMemory - startMemory;
            System.out.println("총 사용 메모리: " + usedMemory + " byte");
        } else if (taskValue == 2) {
            // 사용자로부터 사용할 스레드 수 입력받기
            System.out.print("사용할 스레드의 개수를 입력하세요: ");
            int numThreads = scanner.nextInt();
            scanner.close();

            // 각 스레드의 작업 범위 계산
            int length = randomArray.length;
            int chunkSize = (length + numThreads - 1) / numThreads; // 스레드당 작업 범위 계산

            Thread[] threads = new Thread[numThreads];
            MergeSortTask[] sortTasks = new MergeSortTask[numThreads];

            // 정렬 작업 시작 시간 및 메모리 사용량 기록
            long startTime = System.nanoTime();
            long startUsedMemory = runtime.totalMemory() - runtime.freeMemory();

            // 스레드 생성 및 실행
            for (int i = 0; i < numThreads; i++) {
                int startIndex = i * chunkSize;
                int endIndex = Math.min(startIndex + chunkSize, length) - 1;
                sortTasks[i] = new MergeSortTask(randomArray, startIndex, endIndex);
                threads[i] = new Thread(sortTasks[i]);
                threads[i].start();
            }

            // 스레드 종료 대기
            for (int i = 0; i < numThreads; i++) {
                try {
                    threads[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // 검색 작업 종료 시간 및 메모리 사용량 기록
            long endTime = System.nanoTime();
            long endUsedMemory = runtime.totalMemory() - runtime.freeMemory();

            // 소요 시간 계산 및 출력
            long duration = endTime - startTime;
            System.out.println("총 소요 시간: " + duration + " 나노초");

            // 메모리 사용량 계산 및 출력
            long usedMemory = endUsedMemory - startUsedMemory;
            System.out.println("총 사용 메모리: " + usedMemory + " 바이트");

            // 최종 정렬 결과 출력
            System.out.println("정렬된 배열: " + Arrays.toString(randomArray));
        } else {
            System.err.println("Wrong Task Value");
        }

        
        
    }
}
