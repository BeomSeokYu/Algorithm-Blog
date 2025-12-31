# 배포 가이드 (Raspberry Pi 4B + 5)

## 개요
GitHub Actions에서 ARM64 이미지를 빌드해 4B의 private registry로 push하고, 5B에서 Docker Compose로 앱을 실행합니다. 4B의 Nginx가 TLS를 종료하고 5B로 리버스 프록시합니다.

## 사전 준비
- 4B: MariaDB 실행 중, 5B에서 3306 접근 가능
- 4B: Docker private registry 실행 중(인증 없음, insecure registry 허용)
- 4B 또는 5B: self-hosted GitHub Actions runner(ARM64)
- 5B: Docker Engine + Compose plugin 설치

## GitHub Actions 워크플로
1) `deploy/cicd.yml`을 `.github/workflows/cicd.yml`로 복사합니다.
2) self-hosted runner 라벨이 `self-hosted`, `linux`, `arm64`인지 확인합니다.
3) runner의 Docker 데몬에 insecure registry를 허용해야 합니다.

### GitHub Secrets 설정 (예시 + 형식 설명)
- `REGISTRY_HOST` (필수): `192.168.0.4:5000`
  - 형식: `호스트:포트`만 입력 (`http://` 등 스키마 금지)
- `REGISTRY_USERNAME` (선택): `registry_user`
  - private registry가 basic auth를 쓰는 경우만 설정
- `REGISTRY_PASSWORD` (선택): `strong-password-here`
  - `REGISTRY_USERNAME`의 비밀번호

인증이 없는 레지스트리라면 `REGISTRY_USERNAME`, `REGISTRY_PASSWORD`는 비워 두세요(로그인 단계가 자동으로 건너뜁니다).

## MariaDB (4B) 예시
```sql
CREATE DATABASE algorithm_blog;
CREATE USER 'blog_user'@'192.168.0.%' IDENTIFIED BY 'change_me';
GRANT ALL PRIVILEGES ON algorithm_blog.* TO 'blog_user'@'192.168.0.%';
FLUSH PRIVILEGES;
```

## 5B 배포 절차
1) 디렉토리와 설정 파일을 준비합니다.
```bash
sudo mkdir -p /apps/algorithm-blog/config
sudo touch /apps/algorithm-blog/config/application-key.yml
```
2) `deploy/.env.example`을 `/apps/algorithm-blog/.env`로 복사하고 값을 채웁니다.
3) `deploy/docker-compose.yml`을 `/apps/algorithm-blog/docker-compose.yml`로 복사합니다.
4) 실행:
```bash
cd /apps/algorithm-blog
docker compose pull
docker compose up -d
```

## Nginx (4B)
- `deploy/nginx.conf`를 템플릿으로 사용하세요.
- `server_name`, TLS 인증서 경로, `upstream`의 5B IP를 실제 값으로 수정합니다.
- 변경 후 테스트/리로드:
```bash
sudo nginx -t && sudo systemctl reload nginx
```

## 업데이트 및 롤백
- 업데이트: `docker compose pull && docker compose up -d`
- 롤백: `.env`의 `IMAGE_TAG`를 이전 커밋 SHA로 변경 후 compose 재실행
