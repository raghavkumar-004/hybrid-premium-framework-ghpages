# Hybrid Premium UI + API Automation Framework

Features:
- ✅ Hybrid UI + API testing (one feature contains both)
- ✅ Page Object Model (POM)
- ✅ Environment-based configs (dev/qa/prod)
- ✅ Retry via rerun runner
- ✅ Parallel execution (JUnit 5)
- ✅ Allure reports with screenshots on failure
- ✅ Centralized logging (Log4j2)
- ✅ External test data (JSON)
- ✅ API JSON schema validation
- ✅ GitHub Actions CI/CD with Allure Pages publishing
- ✅ Headless toggle via `-Dheadless=true|false`

## Run
```bash
mvn clean test -Dcucumber.filter.tags='@hybrid' -Dbrowser=chrome -Dheadless=true
```
To re-run only failed scenarios:
```bash
mvn -P retry-failures test
```

## Allure
```bash
allure serve target/allure-results
```

## Headless & Browser
Use `-Dbrowser=chrome|firefox` and `-Dheadless=true|false`.
