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
INSERT INTO users (email, password, nickname) VALUES ('beomseok.dev@gmail.com', '', 'TESTER');
