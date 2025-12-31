# Repository Guidelines

## Project Structure & Module Organization
This is a Gradle-based Spring Boot app. Core Java code lives under `src/main/java/com/hihat/blog/` with common layers split into `config/`, `controller/`, `service/`, `repository/`, `domain/`, `dto/`, and `util/`. Server templates are in `src/main/resources/templates/` (Thymeleaf), and static assets are under `src/main/resources/static/` (CSS, JS, images). Configuration is in `src/main/resources/application.yml` and `src/main/resources/application-test.yml`, with seed data in `src/main/resources/data.sql`. Tests live in `src/test/java/`.

## Build, Test, and Development Commands
- `./gradlew build` builds the app and runs tests.
- `./gradlew test` runs the JUnit 5 test suite only.
- `./gradlew bootRun` starts the app using the Spring Boot dev runner.
- `./gradlew clean` removes build artifacts.
- `./start.sh` runs the built JAR on port 9000 and writes `output.log` (requires `./gradlew build` first).
- `./stop.sh` stops the PID stored in `algo_blog_app.pid`.
- `scripts/deploy.sh` is for server deploys and assumes `/home/ubuntu/app-blog`.

## Coding Style & Naming Conventions
Use standard Java 17 conventions: 4-space indentation, braces on the same line, and package names under `com.hihat.blog`. Layer classes follow clear suffixes such as `*Controller`, `*Service`, and `*Repository`. DTOs use `*Request` / `*Response`. Tests follow `*Test` naming. Lombok is enabled for boilerplate, so annotate as needed rather than hand-writing accessors.

## Testing Guidelines
Tests use JUnit 5 with Spring Boot test utilities. Place new tests in `src/test/java/` alongside the package they exercise. Prefer focused unit tests for services and repository behavior, and integration tests for controllers and security flows. Use `application-test.yml` for test-specific configuration.

## Commit & Pull Request Guidelines
Commit history uses short, descriptive messages (Korean or English) without a strict conventional-commit format. Keep summaries concise and scoped (e.g., “blog api 수정”, “JWT 토큰 검증 보완”). For PRs, include a clear description, note test commands run, link related issues if any, and add screenshots/GIFs for template or UI changes.

## Configuration & Security Notes
Keep secrets and OAuth credentials out of version control. Use environment variables or local overrides when modifying `application.yml`. Document any new required config keys in the PR description.
