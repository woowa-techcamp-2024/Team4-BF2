# 🛵 BF2: Backend For Frontend

<img width="688" alt="스크린샷 2024-08-29 오후 9 22 46" src="https://github.com/user-attachments/assets/3e95a3ae-03d6-4507-be5e-236fe1ebc99c">



## 🎤 소개

우아한 테크캠프 7기 4팀 최종 프로젝트입니다.
<br>
배달의 민족의 많은 기능 중, 가게 노출 기능을 중점적으로 개발하였습니다.
<br>
가게 **100만건**, 메뉴 **26000만건**의 데이터를
EC2 small 3대, EC2 medium 3대로 평균 **0.3초** 이내의 응답율을 기록합니다.

## ✋팀원 소개

<div>
    <table border=""4 width="100%">
        <td align="center"><a href="http://github.com/huiseung"><img src="https://github.com/huiseung.png" width="180px;" style="vertical-align:top" alt=""/>
        <td align="center"><a href="https://github.com/hellomatia"><img src="https://github.com/hellomatia.png" width="180px;" style="vertical-align:top" alt=""/>
        <td align="center"><a href="https://github.com/jcw1031"><img src="https://github.com/jcw1031.png" width="180px;" style="vertical-align:top" alt=""/>
        <td align="center"><a href="https://github.com/hek-git"><img src="https://github.com/hek-git.png" width="180px;" style="vertical-align:top" alt=""/>
        <tr />
        <td align="center"><a href="http://github.com/huiseung">조희승</td>
        <td align="center"><a href="http://github.com/hellomatia">이지표</td>
        <td align="center"><a href="http://github.com/jcw1031">지찬우</td>
        <td align="center"><a href="https://github.com/hek-git">홍은기</td>
    </table>
</div>

## 📒협업 전략

### 🌵브렌치 전략

- 배포: main
- 개발: dev
- 기능 구현: feature/{issue_number}-{기능 이름}
- 리팩토링: refactor/{issue_number}-{리팩토링 사항}
- 버그 수정: fix/{issue_number}-{이슈 사항}
- 설정: config/{issue_number}-{설정 사항}
- 문서: docs/{issue_number}-{문서 내용}

### 🪢 Merge 룰

- 삭제를 포함하여 200 ~ 300줄로 유지한다.
    - PR시 리뷰어들이 코드 리뷰에 들어가는 시간을 줄이고 최대한 많은 버그들을 미리 발견할 수 있도록 한다.

### 💬 코드 리뷰

- PR 파트너가 PR을 올릴 시, 하던일을 중단하고 즉시 리뷰를 진행한다.
- 코드 리뷰 멘트가 PR작성자에게 어떤 의미로 전달 되길 바라는지 알파벳으로 표현한다.
    - RCA룰을 준수한다.

## 📢 그라운드 룰

- 어떤 방식으로든 이슈 발생 또는 진행 사항 등등 공유한다.
    - 예) 노션, 슬랙, 문서화 등...
- 테크 스펙 작성하기
    - 기능을 개발하기 전 테크 스펙을 작성하고, 기능 개발을 완료했을 시 테크 스펙 링크를 같이 첨부하여 올린다.

## 🖊️ 기획
- 현실적으로 3주안에 새로운 서비스 만드는 것은 어렵다고 판단되었습니다.
- 사람들이 사용하지 않는 프로젝트를 만드는 것보다 기술에 초점을 맞추어 프로젝트를 진행하였습니다.
- 배달의 민족의 다양한 기능 중 사용자들이 주문하기 위해 행하는 첫 동작인 가게 찾기 기능을 구현해보았습니다.
<br>
**[자세히 보기...](https://github.com/woowa-techcamp-2024/Team4-BF2/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EA%B8%B0%ED%9A%8D)**

## 🛠️ 아키텍처
<img alt="스크린샷 2024-08-29 오후 9 31 56" src="https://github.com/user-attachments/assets/47ef3dd6-0801-4a78-9917-b48af67973b8">

## 📝 프로젝트 기록들

### 멀티 모듈 구조 채용
- 같은 기능에 대해 사용하는 인프라를 바꾸는 일이 자주 발생될 것 같아 멀티 모듈 구조를 채용했습니다.
<br>

<details>
<summary>자세히 보기</summary>
<div markdown="1">
<img width="1125" alt="스크린샷 2024-08-29 오후 9 10 39" src="https://github.com/user-attachments/assets/8ac350a7-9320-4a29-85a0-851e5fd2e6a5">
</div>
</details>

### 검색성능 최대 600배(?) 개선!
- MySQL의 Like 연산과 ElasticSearch를 성능을 비교하였습니다.
- 전체적인 성능을 고려하여 ElasticSearch를 사용하였습니다.
<br>
<details>
<summary>자세히 보기</summary>
<div markdown="1">
<img width="1127" alt="스크린샷 2024-08-29 오후 9 31 56" src="https://github.com/user-attachments/assets/f82444f9-2913-4f82-885b-5e4e99c38651">
</div>
</details>

### 관심사의 분리: 이벤트
- 가게, 메뉴, 리뷰 서비스에 이벤트를 도입하였습니다.
- 각각 도메인에 데이터 변경사항이 발생하면, 이벤트를 발행하여 검색 서비스, 캐시 서비스에도 반영되도록 하였습니다.
<br>
<details>
<summary>자세히 보기</summary>
<div markdown="1">
<img width="1127" alt="스크린샷 2024-08-29 오후 9 37 56" src="https://github.com/user-attachments/assets/75137251-5926-4a72-91fe-3b3a43fcdde5">
</div>
</details>

### 생각하지 못한 동시성 이슈: 락
- 부하 테스트를 진행하는 중, 가게의 리뷰 수를 업데이트 할 때 생긴 리뷰 총 숫자와 리뷰 수의 값 불일치 현상이 발상하였습니다.
- 여러가지 락을 공부해보고, 비관 락을 적용하였습니다.
<br>
<details>
<summary>자세히 보기</summary>
<div markdown="1">
<img width="1126" alt="스크린샷 2024-08-29 오후 9 40 41" src="https://github.com/user-attachments/assets/d706a7fe-7218-4a4b-95c2-bd2c86dd83ee">
</div>
</details>

### 데이터 베이스 부하를 줄이자: 캐시
- Elastic Search에 검색 기준이 되는 최소 데이터만 보관, 검색에 결과로 id를 반환합니다.
- 캐시 서비스에서 자주 검색되는 가게 id에 대해 정보들을 보관하였습니다.
- 캐시 서버는 Redis로 작성되어 있습니다.
<br>
<details>
<summary>자세히 보기</summary>
<div markdown="1">
<img width="1124" alt="스크린샷 2024-08-29 오후 9 42 19" src="https://github.com/user-attachments/assets/ed41b5f4-3853-4b32-9d28-dc36f9550692">
</div>
</details>

### 다양한 정보를 모아 보자: 비동기 요청
- 검색 결과에 필요한 여러 정보들을 모아오는 상황을 배달의 민족과 유사하게 연출하였습니다.
- 동기 요청과 비동기 요청에 대해 알아보았습니다.
- 비동기 요청의 구현 방식중, RestTemplate을 비동기로 요청하는 방법과 WebClient 요청 방법의 성능을 비교해보았습니다.
- 성능이 비슷하면서, 리소스를 적게 사용하는 WebClient 요청 방식을 사용하였습니다.
<br>
<details>
<summary>자세히 보기</summary>
<div markdown="1">
<img width="1127" alt="스크린샷 2024-08-29 오후 9 49 46" src="https://github.com/user-attachments/assets/9c7adcd2-f502-4c61-8beb-a4f570dcc918">
<img width="1126" alt="스크린샷 2024-08-29 오후 9 50 21" src="https://github.com/user-attachments/assets/5de89475-a790-409a-a704-a1ee822dba5d">
<img width="1126" alt="스크린샷 2024-08-29 오후 9 50 55" src="https://github.com/user-attachments/assets/e4afc0b0-f19f-4165-b75c-d9cca5235733">
</div>
</details>

### 장애 전파를 막자: 서킷 브레이커
- 서버 요청을 통해 데이터를 가져올 경우 외부 서비스에서 장애가 발생하면 호출한 서비스까지 영향을 받게 됩니다.
- 장애 전파를 막기 위해 서킷 브레이커를 사용해보았습니다.
<br>
<details>
<summary>자세히 보기</summary>
<div markdown="1">
<img width="1126" alt="스크린샷 2024-08-29 오후 9 53 31" src="https://github.com/user-attachments/assets/e8ded723-2895-4d66-8140-9f54d5dcf823">
</div>
</details>

### 더 많은 데이터: 인프라 고민
- 앱이 너무 잘되어서 입점 업체가 증가하면 어떻게 인프라 구조를 바꾸어야 할까요?
- 더 효율적인 인프라로 비용도 줄이고, 성능도 높여보았습니다.
<br>
<details>
<summary>자세히 보기</summary>
<div markdown="1">
<img width="1127" alt="스크린샷 2024-08-29 오후 10 27 57" src="https://github.com/user-attachments/assets/dcb80da1-e942-4ce5-8cca-18037bbdad39">
<img width="1124" alt="스크린샷 2024-08-29 오후 10 28 25" src="https://github.com/user-attachments/assets/eff72b3b-7478-44db-8b18-551c9bf49432">
<img width="1126" alt="스크린샷 2024-08-29 오후 10 28 46" src="https://github.com/user-attachments/assets/201f5540-73eb-48bf-8faa-3fbd718a48b5">
</div>
</details>


### 리소스를 절약하자: WebFlux
- 가게 노출 서버의 경우, 노출에 필요한 API 호출하여 정보를 취합하고 결과를 반환합니다.
- 단순 비동기, blocking I/O를 사용하게 되면 수 많은 쓰레드를 사용하게 됩니다.
- Netty를 사용하여 Event Loop기반으로 모든 요청과 응답을 받게 개선 하였습니다.
- 기존 Spring MVC모델 대비 10%의 쓰레드를 사용하여도 더 높은 성능을 기록하였습니다.
<br>
<details>
<summary>자세히 보기</summary>
<div markdown="1">
<img width="1127" alt="스크린샷 2024-08-29 오후 10 10 58" src="https://github.com/user-attachments/assets/1c3c417c-54d3-4101-ae1d-42ce6a6fd38b">
</div>
</details>

### 같이 잘 일하는 법: 팀 문화
- 혼자 잘하기 금지!!
- 매일마다 일일 퍼실리데이터를 정하여 스크럼을 진행하고, 커뮤니케이션 촉진을 담당하도록 하였습니다.
- RCA룰을 사용하여 코멘트의 중요도가 명확하게 들어나게 하였습니다.
- 프로젝트를 진행하면서 필요한 기술을 공부하고, 팀원에게 배운 내용을 설명하도록 하였습니다.
<br>
<details>
<summary>자세히 보기</summary>
<div markdown="1">
 <img width="1129" alt="스크린샷 2024-08-29 오후 10 13 34" src="https://github.com/user-attachments/assets/f4ad865d-37b9-491d-a91e-77328a07236f">
<img width="1127" alt="스크린샷 2024-08-29 오후 10 13 54" src="https://github.com/user-attachments/assets/7f3831a0-6535-455e-bd86-66365c1d1747">
<img width="1128" alt="스크린샷 2024-08-29 오후 10 21 36" src="https://github.com/user-attachments/assets/0ead799f-0b5f-4809-a766-c45b9cd89d05">
</div>
</details>

### 못 해서 아쉬운 것 들
- 엘라스틱 서치 bulk insert/update
- 큐 서비스
- 배달 가능 지역 고도화
- 로드 밸런싱, 오토 스케일링