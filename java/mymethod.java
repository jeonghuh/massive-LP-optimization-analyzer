package teamproject;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ben95
 */
import java.util.*;
import java.io.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class mymethod  {
    
    public ArrayList<String> readText(String text) {
        ArrayList<String> scanned = new ArrayList<>();
        try {
            Scanner scan = new Scanner(new File(text));
            while (scan.hasNextLine()) {
                scanned.add(scan.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        return scanned;
    }
    
    public void OptimalCheck(String text, String csvFilePath){
        ArrayList<String> scanned = readText(text);
        int cnt=0;
        ArrayList<Integer> optimal= new ArrayList<>();
        ArrayList<Integer> infeasible = new ArrayList<>();
        
        try (PrintWriter csvWriter = new PrintWriter(new File(csvFilePath))) {
            csvWriter.println("problem_num,Solved"); // CSV 파일의 헤더
            
            for(String x : scanned){
                cnt++;
                String status;
                if(x.equals("optimal")){
                    optimal.add(cnt);
                    status = "optimal";
                } else {
                    infeasible.add(cnt);
                    status = "infeasible";
                }
                csvWriter.println(cnt + "," + status); // 결과를 CSV 파일에 쓰기
            }
            
            csvWriter.println("Solved," + optimal.size());
            csvWriter.println("Unsolved," + infeasible.size());
            
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while writing the CSV file.");
        }
    }
    public void printTime(String text, String csvFilePath) {
        ArrayList<String> scanned = readText(text);
        int cnt = 0;
        double averageTime = 0;

        try (PrintWriter csvWriter = new PrintWriter(new File(csvFilePath))) {
            csvWriter.println("problem_num,Time"); // CSV 파일의 헤더

            for (String x : scanned) {
                cnt++;
                double t = Double.parseDouble(x);
                averageTime += t;
                csvWriter.println(cnt + "," + t); // 결과를 CSV 파일에 쓰기
                System.out.println("#" + cnt + " problem solving time: " + x);
            }

            System.out.println("Average solving time:" + averageTime / cnt);
            csvWriter.println("Average," + averageTime / cnt); // 평균 시간도 추가

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while writing the CSV file.");
        }
    }


    
    
    
    //풀린 문제의 최적 목적식 값들
    public void printObjValue(String text, String csvFilePath) {
        ArrayList<String> scanned = readText(text);
        int cnt = 0;

        try (PrintWriter csvWriter = new PrintWriter(new File(csvFilePath))) {
            csvWriter.println("problem_num,ObjValue"); // CSV 파일의 헤더

            for (String x : scanned) {
                cnt++;
                if (!x.equals("None")) {
                    csvWriter.println(cnt + "," + x); // 결과를 CSV 파일에 쓰기
                    System.out.println(cnt + "번 문제의 최적 목적식 값: " + x);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while writing the CSV file.");
        }
    }
    
    //풀린 문제의 최적 해를 가질 때 변수 값들

    public void printValue(String text, String csvFilePath) {
        ArrayList<String> scanned = readText(text);
        if (scanned.isEmpty()) return; // 파일이 비어 있으면 함수 종료

        try (PrintWriter csvWriter = new PrintWriter(new File(csvFilePath))) {
            // 첫 번째 줄에서 변수 이름 추출 및 CSV 헤더 작성
            String[] variableNames = scanned.get(0).split(" ");
            csvWriter.print("problem_num");
            for (int i = 0; i < variableNames.length; i++) {
                csvWriter.print("," + variableNames[i]);
            }
            csvWriter.println();

            // 두 번째 줄부터 각 문제의 값을 처리하여 CSV에 작성
            for (int i = 1; i < scanned.size(); i++) {
                csvWriter.print(i); // 문제 번호
                String[] values = scanned.get(i).split(" ");

                for (String value : values) {
                    csvWriter.print("," + (value.equals("Null") ? "" : value));
                }
                csvWriter.println();
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while writing the CSV file.");
        }
    }

    
    public void LowK(int k, String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        PriorityQueue<Double> maxHeap = new PriorityQueue<>((a, b) -> Double.compare(b, a));
        String line;
        // 파일의 끝까지 한 줄씩 읽어오기
        while ((line = br.readLine()) != null) {
            if (!line.equals("None")) {
                double value = Double.parseDouble(line);
                // 우선순위 큐에 값 추가
                maxHeap.offer(value);
                // 큐의 크기가 K보다 크면 가장 큰 값 제거
                if (maxHeap.size() > k) {
                    maxHeap.poll();
                }}}
        // 우선순위 큐에는 현재 Low K 값이 맨 앞에 들어있음
        System.out.println("Low " + k + "th objective value: " + maxHeap.peek());
    } catch (IOException e) {
    }
}
    public void TopK(int k, String filePath) {
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        PriorityQueue<Double> minHeap = new PriorityQueue<>();
        String line;
        // 파일의 끝까지 한 줄씩 읽어오기
        while ((line = br.readLine()) != null) {
            if (!line.equals("None")) {
                double value = Double.parseDouble(line);
                minHeap.offer(value); // 우선순위 큐에 값 추가
                if (minHeap.size() > k) { //큐의 크기가 k보다 크면 가장 작은 값 제거
                    minHeap.poll();
                } } }
        // 우선순위 큐에는 현재 Top K 값이 맨 앞에 들어있음
        System.out.println("Top " + k + "th objective value: " + minHeap.peek());
    } catch (IOException e) {
    }
}
    public void averageTimeForProblemsWithVariable(String variableName, String valueFilePath, String timeFilePath) {
    ArrayList<String> valueData = readText(valueFilePath);
    ArrayList<String> timeData = readText(timeFilePath);
    HashMap<Integer, Double> problemTimes = new HashMap<>();
    double totalTime = 0;
    int count = 0;

    // 결정변수 이름이 있는 줄에서 인덱스 찾기
    String[] variableNames = valueData.get(0).split(" ");
    int variableIndex = -1;
    for (int i = 0; i < variableNames.length; i++) {
        if (variableNames[i].equals(variableName)) {
            variableIndex = i;
            break;
        }
    }

    if (variableIndex == -1) {
        System.out.println("Variable not found");
        return;
    }

    // 해당 결정변수를 포함하는 문제들의 시간 추출 및 합산
    for (int i = 1; i < valueData.size(); i++) {
        String[] values = valueData.get(i).split(" ");
        if (!values[variableIndex].equals("Null")) {
            double time = Double.parseDouble(timeData.get(i - 1));
            totalTime += time;
            count++;
        }
    }

    // 평균 시간 계산
    double averageTime = totalTime / count;
    System.out.println("Average time for problems with variable '" + variableName + "': " + averageTime);
}
public void findSolvedAndUnsolvedProblemsWithVariable(String variableName, String valueFilePath, String statusFilePath) {
    ArrayList<String> valueData = readText(valueFilePath);
    ArrayList<String> statusData = readText(statusFilePath);
    ArrayList<Integer> solvedProblems = new ArrayList<>();
    ArrayList<Integer> unsolvedProblems = new ArrayList<>();

    // 변수 이름이 있는 줄에서 인덱스 찾기
    String[] variableNames = valueData.get(0).split(" ");
    int variableIndex = -1;
    for (int i = 0; i < variableNames.length; i++) {
        if (variableNames[i].equals(variableName)) {
            variableIndex = i;
            break;
        }
    }

    if (variableIndex == -1) {
        System.out.println("Variable not found");
        return;
    }

    // 문제가 해당 변수를 사용하는지 확인하고 해결 상태를 분류
    for (int i = 1; i < valueData.size(); i++) {
        String[] values = valueData.get(i).split(" ");
        if (!values[variableIndex].equals("Null")) {
            int problemNum = i;
            String status = statusData.get(i - 1);
            if (status.equals("optimal")) {
                solvedProblems.add(problemNum);
            } else {
                unsolvedProblems.add(problemNum);
            }
        }
    }

    // 결과 출력
    System.out.println("Solved problems with variable '" + variableName + "': " + solvedProblems);
    System.out.println("Unsolved problems with variable '" + variableName + "': " + unsolvedProblems);
}

public void findObjectiveValuesOfSolvedProblemsWithVariable(String variableValuesFilePath, String statusFilePath, String objectiveValuesFilePath, String variableName) {
    ArrayList<String> variableValuesData = readText(variableValuesFilePath);
    ArrayList<String> statusData = readText(statusFilePath);
    ArrayList<String> objectiveValuesData = readText(objectiveValuesFilePath);
    ArrayList<String> solvedObjectiveValues = new ArrayList<>();

    // 첫 번째 행에서 변수 인덱스 찾기
    String[] variableNames = variableValuesData.get(0).split(" ");
    int variableIndex = -1;
    for (int i = 0; i < variableNames.length; i++) {
        if (variableNames[i].equals(variableName)) {
            variableIndex = i;
            break;
        }
    }

    if (variableIndex == -1) {
        System.out.println("Variable not found");
        return;
    }

    // 첫 번째 행(변수 이름 행)을 건너뛰고 데이터 처리 시작
    for (int i = 1; i < variableValuesData.size(); i++) {
        String[] values = variableValuesData.get(i).split(" ");
        String status = statusData.get(i - 1); // 인덱스 조정
        if (status.equals("optimal") && !values[variableIndex].equals("Null")) {
            // objectiveValuesData의 크기 확인
            if (i < objectiveValuesData.size()) {
                solvedObjectiveValues.add(objectiveValuesData.get(i)); // 인덱스 조정
            }
        }
    }

    System.out.println("Objective values of solved problems with variable '" + variableName + "': " + solvedObjectiveValues);
}

public void findOptimalValuesOfVariableInSolvedProblems(String variableValuesFilePath, String statusFilePath, String variableName) {
    ArrayList<String> variableValuesData = readText(variableValuesFilePath);
    ArrayList<String> statusData = readText(statusFilePath);
    ArrayList<String> optimalValues = new ArrayList<>();

    // 첫 번째 행에서 변수 인덱스 찾기
    String[] variableNames = variableValuesData.get(0).split(" ");
    int variableIndex = -1;
    for (int i = 0; i < variableNames.length; i++) {
        if (variableNames[i].equals(variableName)) {
            variableIndex = i;
            break;
        }
    }

    if (variableIndex == -1) {
        System.out.println("Variable not found");
        return;
    }

    // 첫 번째 행(변수 이름 행)을 건너뛰고 데이터 처리 시작
    for (int i = 1; i < variableValuesData.size(); i++) {
        String[] values = variableValuesData.get(i).split(" ");
        String status = statusData.get(i - 1);
        if (status.equals("optimal") && !values[variableIndex].equals("Null")) {
            optimalValues.add(values[variableIndex]);
        }
    }

    System.out.println("Optimal values of variable '" + variableName + "' in solved problems: " + optimalValues);
}

public void findProblemsWithObjectiveValueLessThan(String objectiveValuesFilePath, double threshold) {
    ArrayList<String> objectiveValuesData = readText(objectiveValuesFilePath);
    ArrayList<Integer> problemIndices = new ArrayList<>();

    // 첫 번째 행(헤더 행)을 건너뛰고 데이터 처리 시작
    for (int i = 1; i < objectiveValuesData.size(); i++) {
        String valueString = objectiveValuesData.get(i);

        // 값이 "None"인 경우 건너뛰기
        if (valueString.equals("None")) {
            continue;
        }

        try {
            double value = Double.parseDouble(valueString);
            if (value <= threshold) {
                problemIndices.add(i); // 인덱스 추가 (1부터 시작)
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format at line " + i + ": " + valueString);
        }
    }

    System.out.println("Problems with objective value less than " + threshold + ": " + problemIndices);
}

public void findProblemsWithObjectiveValueMoreThan(String objectiveValuesFilePath, double threshold) {
    ArrayList<String> objectiveValuesData = readText(objectiveValuesFilePath);
    ArrayList<Integer> problemIndices = new ArrayList<>();

    // 첫 번째 행(헤더 행)을 건너뛰고 데이터 처리 시작
    for (int i = 1; i < objectiveValuesData.size(); i++) {
        String valueString = objectiveValuesData.get(i);

        // 값이 "None"인 경우 건너뛰기
        if (valueString.equals("None")) {
            continue;
        }

        try {
            double value = Double.parseDouble(valueString);
            if (value >= threshold) {
                problemIndices.add(i); // 인덱스 추가 (1부터 시작)
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format at line " + i + ": " + valueString);
        }
    }

    System.out.println("Problems with objective value more than " + threshold + ": " + problemIndices);
}

public void findProblemsWithObjectiveValueMoreLessThan(String objectiveValuesFilePath, double more, double less) {
    ArrayList<String> objectiveValuesData = readText(objectiveValuesFilePath);
    ArrayList<Integer> problemIndices = new ArrayList<>();

    // 첫 번째 행(헤더 행)을 건너뛰고 데이터 처리 시작
    for (int i = 1; i < objectiveValuesData.size(); i++) {
        String valueString = objectiveValuesData.get(i);

        // 값이 "None"인 경우 건너뛰기
        if (valueString.equals("None")) {
            continue;
        }

        try {
            double value = Double.parseDouble(valueString);
            if (value >= more && value <= less) {
                problemIndices.add(i); // 인덱스 추가 (1부터 시작)
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format at line " + i + ": " + valueString);
        }
    }

    System.out.println("Problems with objective value " + more+" to "+less + ": " + problemIndices);
}

public void findTotalCountOfProblemsWithObjectiveValueLessThan(String objectiveValuesFilePath, double threshold) {
    ArrayList<String> objectiveValuesData = readText(objectiveValuesFilePath);
    int count=0;
    // 첫 번째 행(헤더 행)을 건너뛰고 데이터 처리 시작
    for (int i = 1; i < objectiveValuesData.size(); i++) {
        String valueString = objectiveValuesData.get(i);

        // 값이 "None"인 경우 건너뛰기
        if (valueString.equals("None")) {
            continue;
        }

        try {
            double value = Double.parseDouble(valueString);
            if (value <= threshold) {
                count++;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format at line " + i + ": " + valueString);
        }
    }

    System.out.println("Total count of problems with objective value less than " + threshold + ": " + count);
}

public void findTotalCountOfProblemsWithObjectiveValueMoreThan(String objectiveValuesFilePath, double threshold) {
    ArrayList<String> objectiveValuesData = readText(objectiveValuesFilePath);
    int count=0;
    // 첫 번째 행(헤더 행)을 건너뛰고 데이터 처리 시작
    for (int i = 1; i < objectiveValuesData.size(); i++) {
        String valueString = objectiveValuesData.get(i);

        // 값이 "None"인 경우 건너뛰기
        if (valueString.equals("None")) {
            continue;
        }

        try {
            double value = Double.parseDouble(valueString);
            if (value >= threshold) {
                count++;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format at line " + i + ": " + valueString);
        }
    }

    System.out.println("Total count of problems with objective value more than " + threshold + ": " + count);
}

public void findTotalCountOfProblemsWithObjectiveValueMoreLessThan(String objectiveValuesFilePath, double more, double less) {
    ArrayList<String> objectiveValuesData = readText(objectiveValuesFilePath);
    int count=0;

    // 첫 번째 행(헤더 행)을 건너뛰고 데이터 처리 시작
    for (int i = 1; i < objectiveValuesData.size(); i++) {
        String valueString = objectiveValuesData.get(i);

        // 값이 "None"인 경우 건너뛰기
        if (valueString.equals("None")) {
            continue;
        }

        try {
            double value = Double.parseDouble(valueString);
            if (value >= more && value <= less) {
                count++;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format at line " + i + ": " + valueString);
        }
    }

    System.out.println("Total count of problems with objective value " + more+" to "+less + ": " + count);
}

public void findProblemsWithVariableCondition(String variableValuesFilePath, String variableName, String operator, double threshold) {
    ArrayList<String> variableValuesData = readText(variableValuesFilePath);
    ArrayList<Integer> problemIndices = new ArrayList<>();

    // 첫 번째 행에서 변수 인덱스 찾기
    String[] variableNames = variableValuesData.get(0).split(" ");
    int variableIndex = -1;
    for (int i = 0; i < variableNames.length; i++) {
        if (variableNames[i].equals(variableName)) {
            variableIndex = i;
            break;
        }
    }

    if (variableIndex == -1) {
        System.out.println("Variable not found: " + variableName);
        return;
    }

    // 첫 번째 행(변수 이름 행)을 건너뛰고 데이터 처리 시작
    for (int i = 1; i < variableValuesData.size(); i++) {
        String[] values = variableValuesData.get(i).split(" ");
        String valueString = values[variableIndex];

        // 값이 "Null"인 경우 건너뛰기
        if (valueString.equals("Null")) {
            continue;
        }

        try {
            double value = Double.parseDouble(valueString);
            boolean conditionMet = switch (operator) {
                case "<" -> value < threshold;
                case "<=" -> value <= threshold;
                case ">" -> value > threshold;
                case ">=" -> value >= threshold;
                case "==" -> value == threshold;
                case "!=" -> value != threshold;
                default -> throw new IllegalArgumentException("Invalid operator: " + operator);
            };

            if (conditionMet) {
                problemIndices.add(i); // 인덱스 추가 (1부터 시작)
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format at line " + i + ": " + valueString);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            break;
        }
    }

    System.out.println("Problems with " + variableName + " value " + operator + " " + threshold + ": " + problemIndices);
}


}


