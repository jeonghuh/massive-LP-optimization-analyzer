# massive-LP-optimization-analyzer

## Overview
본 프로젝트는3,000개의 결정변수와 10,000개의 시나리오를 기반으로 한 80,000개의 선형계획법(LP) 모델을 분석하는 대규모 최적화 분석 시스템입니다.

Data Structure 수업에서 진행한 팀 프로젝트로 수행되었으며, 각 모델의 성능을 비교·분석하고 변수별 영향을 평가하여 금융 포트폴리오 최적화 관점에서 의사결정 지원 시스템을 구현하는 것을 목표로 하였습니다.

This project is a large-scale Linear Programming (LP) analysis system designed to evaluate 80,000 portfolio optimization models based on 3,000 decision variables and 10,000 scenarios.

## Problem Context
- 결정변수 수 (M): 3,000개
- 시나리오 수 (N): 10,000개
- 총 분석 모델 수: 80,000개
- 문제 유형: Portfolio Credit Risk Optimization

각 LP 문제는 vars.txt에 정의된 서로 다른 변수 조합을 사용하여 구성

Objective: 
1. 각 LP 모델의 최적화 결과 확인
2. 목적함수 값 분석
3. 결정변수 별 영향도 분석
4. 대규모 결과 데이터를 기반으로 금융적 인사이트 도출


## Core Features

### 기본 서비스
1. 각 문제를 푸는 시간 -printTime
2. 각 문제가 풀렸는지의 유무 -OptimalCheck
3. 각 문제가 풀렸다면 최적 목적식 값들 -printObjValue
4. 각 문제가 풀렸다면 최적 해 값들 -printValue
5. 평균 걸린 시간 -printTime
6. 특정 결정변수가 포함된 문제들의 평균 걸린 시간 -averageTimeForProblemsWithVariable

### 추가 서비스
1. k번째로 큰 목적함수 값 탐색 -TopK(MinHeap 활용)
2. k번째로 작은 목적함수 값 탐색 -LowK(MaxHeap 활용)
3. 결정변수(예를 들어, x3)가 사용된 LP문제들 중에 풀린 문제들과 풀리지 않은 문제들을 찾아낼 수 있다-findSolvedAndUnsolvedProblemsWithVariable
4. 결정변수(예를 들어, x3)가 사용된 LP문제들 중에 풀린 문제들의 목적식 값들을 확인한다.-findObjectiveValuesOfSolvedProblemsWithVariable
5. 결정변수(예를 들어, x3)가 사용된 LP문제들 중에 풀린 문제들의 그 변수들의 최적 해 값들을 확인한다.-findOptimalValuesOfVariableInSolvedProblems
6. 목적식 값이 어느 조건(예를 들어, 80 이하)을 만족하는 문제들을 찾아낸다. -findProblemsWithObjectiveValueLessThan
7. 목적식 값이 어느 조건(예를 들어, 80 이상)을 만족하는 문제들을 찾아낸다. -findProblemsWithObjectiveValueMoreThan
8. 목적식 값이 어느 조건(예를 들어, 80 이상 100이하)을 만족하는 문제들을 찾아낸다. -findProblemsWithObjectiveValueMoreLessThan
9. 목적식 값이 어느 조건(예를 들어, 80 이상 100이하)을 만족하는 문제의 갯수를 찾아낸다. -findTotalNumOfProblemsWithObjectiveValueMoreLessThan
10. 목적식 값이 어느 조건(예를 들어, 80 이상)을 만족하는 문제의 갯수를 찾아낸다. -findTotalNumOfProblemsWithObjectiveValueMoreThan
11. 목적식 값이 어느 조건(예를 들어, 100이하)을 만족하는 문제의 갯수를 찾아낸다. -findTotalNumOfProblemsWithObjectiveValueLessThan
12. 최적해에서 결정변수(예를 들어, x3)의 값이 어느 조건을 (예를 들어, x3*이 3이하) 만족하는 문제들을 찾아낸다. -findProblemsWithVariableCondition
