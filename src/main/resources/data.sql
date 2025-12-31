INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('제목 1', '<p>내용 1</p>', '작성자 1', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('제목 2', '<p>내용 2</p>', '작성자 2', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('제목 3', '<p>내용 3</p>', '작성자 3', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES (
  '제목 4',
  '<p>이 글은 목록에서 스크롤을 확인할 수 있도록 길게 작성된 샘플입니다.</p><p>여러 문단과 코드 블록, 리스트를 포함해 실제 작성 흐름을 확인합니다.</p><ul><li>그래프 탐색</li><li>DP 최적화</li><li>그리디 전략</li></ul><p>긴 내용이 카드 영역을 넘어서더라도 스크롤로 자연스럽게 확인할 수 있어야 합니다.</p><p>추가 문단을 계속 이어서 표시합니다. 추가 문단을 계속 이어서 표시합니다. 추가 문단을 계속 이어서 표시합니다.</p><p>추가 문단을 계속 이어서 표시합니다. 추가 문단을 계속 이어서 표시합니다. 추가 문단을 계속 이어서 표시합니다.</p>',
  '작성자 4',
  '이론정리',
  NOW(),
  NOW()
);
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - BFS 레벨 탐색', '<p>큐를 활용해 레벨 순으로 방문합니다.</p><p>최단 거리 문제에 자주 쓰입니다.</p>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 14502 연구소', '<p>벽 3개를 세우고 안전 영역을 최대화합니다.</p><p>브루트포스 + BFS 조합을 연습하기 좋습니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - 이분 탐색', '<p>정렬된 배열에서 구간을 절반씩 줄여 찾습니다.</p><pre><code>while (lo &lt;= hi) {\n  int mid = (lo + hi) / 2;\n}\n</code></pre>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 2178 미로 탐색', '<p>격자 최단 거리는 BFS로 풉니다.</p><p>방문 배열을 반드시 확인합니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - 세그먼트 트리', '<p>구간 합과 최댓값을 빠르게 질의합니다.</p><ul><li>구간 분할</li><li>트리 구축</li><li>업데이트</li></ul>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 1916 최소비용 구하기', '<p>다익스트라로 최단 경로를 계산합니다.</p><p>우선순위 큐 사용이 핵심입니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - 최소 스패닝 트리', '<p>모든 노드를 최소 비용으로 연결합니다.</p><p>크루스칼과 프림을 비교합니다.</p>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 1197 최소 스패닝 트리', '<p>간선을 정렬하고 유니온 파인드를 사용합니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - 위상 정렬', '<p>진입 차수가 0인 노드를 순서대로 처리합니다.</p><p>사이클 존재 여부도 함께 확인합니다.</p>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 2623 음악프로그램', '<p>위상 정렬 결과를 출력합니다.</p><p>사이클이면 0을 출력합니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - 다익스트라', '<p>양의 가중치 그래프에서 최단 경로를 구합니다.</p><p>방문 처리 시점을 주의합니다.</p>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 4485 녹색 옷', '<p>격자형 그래프에서 다익스트라를 적용합니다.</p><p>비용 누적을 체크합니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - 플로이드 워셜', '<p>모든 쌍 최단 거리를 구하는 알고리즘입니다.</p><p>O(N^3) 복잡도를 이해합니다.</p>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 11404 플로이드', '<p>도시 간 최소 비용을 플로이드 워셜로 계산합니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - 유니온 파인드', '<p>서로소 집합 자료구조를 설명합니다.</p><p>경로 압축과 랭크 최적화를 포함합니다.</p>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 1717 집합', '<p>유니온 파인드로 합집합과 질의를 처리합니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - DP 기본', '<p>작은 문제로 쪼개서 큰 문제를 해결합니다.</p><p>메모이제이션과 테이블 구성법을 정리합니다.</p>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 11053 LIS', '<p>가장 긴 증가하는 부분 수열을 구합니다.</p><p>O(N log N) 풀이로 정리합니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - 그리디', '<p>매 순간 최선의 선택을 합니다.</p><p>정당성 증명을 함께 기록합니다.</p>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 1931 회의실', '<p>끝나는 시간이 빠른 순서로 선택합니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - 누적 합', '<p>구간 합을 빠르게 계산하기 위한 전처리입니다.</p><p>1차원과 2차원을 비교합니다.</p>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 11659 구간 합', '<p>누적 합 배열로 질의를 처리합니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - 투 포인터', '<p>두 개의 포인터를 움직이며 구간 조건을 만족합니다.</p><p>정렬 여부를 확인합니다.</p>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 1806 부분합', '<p>최소 길이 부분합을 투 포인터로 해결합니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - 슬라이딩 윈도우', '<p>고정 길이 구간을 이동하면서 계산합니다.</p><p>카운트 업데이트가 핵심입니다.</p>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 12891 DNA 비밀번호', '<p>문자 빈도를 유지하면서 유효 조건을 검사합니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - 스택과 큐', '<p>기본 자료구조의 동작을 정리합니다.</p><p>괄호 검사 예제를 포함합니다.</p>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 10828 스택', '<p>스택의 기본 연산을 구현합니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('이론 정리 - 트라이', '<p>문자열 검색을 위한 트리 구조입니다.</p><p>접두사 탐색에 유리합니다.</p>', '관리자', '이론정리', NOW(), NOW());
INSERT INTO article (title, content, author, type, created_at, updated_at) VALUES ('문제 풀이 - 5052 전화번호', '<p>트라이로 접두사 충돌을 검사합니다.</p>', '관리자', '문제풀이', NOW(), NOW());
INSERT INTO users (email, password, nickname) VALUES ('beomseok.dev@gmail.com', '', 'TESTER');
