# 🤖 TSID vs UUID Benchmark Test

> - TSID 와 UUID 의 생성, Set 삽입 연산 시 소요 시간을 비교하는 벤치마크 테스트입니다.
> - <a href='https://velog.io/@jthugg/tsid-vs-uuid-benchmark-test' target='_blank'>블로그 포스트</a>
> - <a href='https://drive.google.com/drive/folders/1p1Ja8I5xAjarDEE6KMdC8_6VatAjwi9n?usp=sharing' target='_blank'>벤치마크 결과</a>

## 🧑‍💻 테스트 실행 방법

- 프로젝트를 클론하고 루트 디렉토리에서 테스트 스크립트를 실행합니다.
  - [생성 테스트](./run-generate-test.sh)
  ```shell
  ./run-generate-test.sh
  ```
  - [Set 삽입 테스트](./run-set-insert-test.sh)
  ```shell
  ./run-set-insert-test.sh
  ```

- 테스트 스크립트 실행이 완료되면 [./src/test/resources/results/](./src/test/resources/results) 경로에 `.csv` 파일이 생성됩니다.

## 📚 참고사항

- `Set` 자료구조는 `TreeSet(*Red-Black Tree)`입니다.
- `Set` 삽입 연산 시 synchronized 블록으로 동기화처리를 수행합니다.
