# Algorithm Blog

알고리즘 이론 정리와 문제 풀이를 체계적으로 기록하고 검색하기 위한 개인 블로그입니다. 글 작성부터 분류, 탐색까지 한 흐름으로 묶어 실제 운영 가능한 수준의 서비스로 구현했습니다.

## 핵심 기능
- 글 작성/수정/삭제: CKEditor 5 기반 WYSIWYG 에디터로 코드 블록, 표, 미디어 임베드 지원
- 알고리즘 분류 태그: 자동완성 기반 태그 입력, 다중 선택 및 삭제, 목록/상세 화면 배지 표시
- 탐색 경험 개선: 검색, 정렬(최신/오래된/제목), 페이지 크기(2/4/8), 미리보기 토글
- 필터 구조: 이론정리/문제풀이 분리 + 분류 태그 필터
- 인증/보안: OAuth2(구글) 로그인 + JWT 기반 인증/리프레시 토큰
- SEO: `sitemap.xml`, `robots.txt` 제공 및 공유 메타 태그 적용

## 기술 스택
- Backend: Java 21, Spring Boot 3.2.5, Spring MVC, Spring Security, Spring Data JPA
- Frontend: Thymeleaf, Vanilla JS, CKEditor 5, 커스텀 CSS
- DB: MySQL(운영), H2(Test 프로파일)
- Infra/DevOps: Docker, GitHub Actions, Nginx 리버스 프록시(배포 구성)

## 도메인 모델 요약
- `Article`: 제목, 본문, 타입(이론정리/문제풀이), 작성자, 작성/수정일
- `AlgorithmCategory`: 알고리즘 분류(한글/영문명, 출처, 문제 수)
- `Article <-> AlgorithmCategory`: 다대다 관계(`article_category` 조인 테이블)
- `User`, `RefreshToken`: 로그인 및 토큰 관리

## 프로젝트 구조
```
src/main/java/com/hihat/blog
  controller/  service/  repository/  domain/  dto/  util/
src/main/resources
  templates/  static/  data.sql  seed/algorithm-categories.tsv
deploy/
  README.md  docker-compose.yml  nginx.conf  cicd.yml
```

## 실행 방법
### Test 프로파일(H2 메모리 DB)
```
./gradlew bootRun --args='--spring.profiles.active=test'
```

### 기본 프로파일(MySQL)
`application.yml`에서 아래 환경 변수를 사용합니다.
```
DB_NAME, DB_USERNAME, DB_PASSWORD
ISSUER, SECRET_KEY
GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET
```
또한 `/config/application-key.yml`을 선택적으로 로드합니다.

## 빌드/테스트
```
./gradlew test
./gradlew bootJar
```

## 배포/CI
- GitHub Actions에서 멀티 아키텍처 이미지를 빌드하고 레지스트리에 업로드
- `deploy/` 디렉토리에 Docker Compose, Nginx 설정, CI 워크플로 샘플 제공
- 예시 실행:
```
docker compose --env-file .env up -d
```

## 데이터 초기화
- `data.sql`에 테스트용 글/사용자 샘플 포함
- `AlgorithmCategorySeeder`가 `seed/algorithm-categories.tsv`를 읽어 분류 데이터 자동 삽입

## 개발 포인트
- 목록 화면의 미리보기/정렬/페이지 크기 설정을 세션 스토리지에 저장해 UX 개선
- 분류 태그를 중심으로 글 탐색 경로를 단순화
- SEO, 공유 메타, 사이트맵으로 검색 노출을 고려한 구성
