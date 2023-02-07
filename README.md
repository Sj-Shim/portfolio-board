# 게시판 서비스 프로젝트
- 아카라이브(arca.live) 틀을 모방한 게시판 서비스 생성
- 게시글 작성(수정/삭제), 댓글 작성(수정/삭제), 대댓글 작성(수정/삭제), 게시판 구분(채널), 게시판 생성(수정/삭제), 게시판 구독/취소 기능, 회원가입
# 작동 화면
-메인화면(index)

![217242022-1172a536-0c76-459d-b835-a73c3de5390c](https://user-images.githubusercontent.com/115637503/217242956-04a702ed-a4cb-4f61-bfcd-a24bcf05bfcd.png)

-회원가입창

![217242026-bb559b86-b0e4-4b63-92ae-e8c56bbed620](https://user-images.githubusercontent.com/115637503/217242929-d0f9c326-7bc1-4620-bab9-c221815a5729.png)

-로그인 후(로그인 유저의 구독목록 드롭다운 확인. 구독버튼 변화( + -> v/x(호버시))

![217242020-c4e51d5a-27e6-4ecf-b073-ebfa9dfee2f1](https://user-images.githubusercontent.com/115637503/217242981-ace33942-20e4-4819-99f1-3cc644bbb4c1.png)

-유저 아이디 버튼 클릭 시 드롭다운 아이템

![217242017-a5ccf624-c8ef-45f4-b6c2-efcfc817a1ea](https://user-images.githubusercontent.com/115637503/217243104-62f2213d-617a-4e48-83bb-3b2e2b7f5235.png)

-내정보 창

![217241992-f685d8ac-df51-401c-a9c8-b12ec8fe5736](https://user-images.githubusercontent.com/115637503/217243227-9a639e31-a884-43a8-8e5d-1129e9333f17.png)

-비밀번호 변경창(유효성 검사 후 반영)

![217241989-2a04f42b-82cf-418c-86f2-e5365c165b16](https://user-images.githubusercontent.com/115637503/217243239-aae32b53-ed97-4f66-9a6c-2e9b47411dd7.png)

-게시판 만들기(중복 확인 등 유효성 검사 후 반영)

![217241998-9ce5291a-b98e-4027-ab22-a8229b15101e](https://user-images.githubusercontent.com/115637503/217243265-667853b6-11cd-469a-a338-43cdddf4ed46.png)

-게시판 메인

![217242012-69267280-358a-4cb7-9ccd-861821bebc0d](https://user-images.githubusercontent.com/115637503/217243344-017cf2b0-3ac2-4343-bb63-93ca7f4c2dbe.png)

-검색결과 화면

![217242002-986b0466-2526-4485-be2c-f6413fe8996f](https://user-images.githubusercontent.com/115637503/217243388-6054be6f-9a64-4fd0-a98b-5e374090069b.png)

-글작성 페이지(수정 페이지 레이아웃 동일)

![217242014-24e3008c-1a72-46d0-9249-bd341c6c2beb](https://user-images.githubusercontent.com/115637503/217243518-703a745d-263c-4e08-8e69-c84e30945a43.png)

-게시글 상세 페이지(작성자와 로그인유저 일치시 수정/삭제 버튼 생성. 대댓글 가진 댓글은 '삭제됨' 표시. 대댓글 없는 경우 완전 삭제)

![217242007-b282ddf9-bf44-4ea4-bde2-ad9694d0acf1](https://user-images.githubusercontent.com/115637503/217243559-7ae345c4-f3f3-487a-acbd-c484b47bc8b4.png)

-댓글 수정 버튼 클릭 시 모달창 

![217242004-6456cb4a-41dc-4e49-ae99-71b596213fc2](https://user-images.githubusercontent.com/115637503/217243578-be28d2c7-f65c-4fdf-a8d2-41cecfb2aa26.png)

# 개발 환경
- IntelliJ IDEA 22.2.3 (Ultimate Edition)
- Java 17
- Gradle 7.6
- Spring Boot 2.7.7
# 기술 세부 스택
 Spring Boot
 - Spring Web
 - Spring Data JPA
 - Thymeleaf
 - Spring Security
 - MySQL Driver
 - Lombok
 - Spring Boot DevTools
 
 그 외
 - QueryDSL 5.0.0
 - BootStrap 5.2.0
 
