CREATE TABLE user(
    no INT AUTO_INCREMENT PRIMARY KEY,				    	-- 회원 번호 : 자동 발생
    id VARCHAR(20) UNIQUE KEY,								-- 회원 아이디
    pw VARCHAR(500) NOT NULL,								-- 회원 비밀번호
    name VARCHAR(50) NOT NULL,								-- 회원 이름
    email VARCHAR(50),						                -- 회원 이메일
    tel VARCHAR(20) NOT NULL,								-- 회원 전화번호
    addr1 VARCHAR(200),										-- 회원 주소
    addr2 VARCHAR(100),										-- 회원 상세 주소
    postcode VARCHAR(10),									-- 회원 우편 번호
    regdate DATETIME DEFAULT CURRENT_TIMESTAMP(),	        -- 회원 등록일
    birth DATE,												-- 회원 생일
    grade int DEFAULT 2,								    -- 회원 등급 [ 0 : 관리자, 1 : 선생님, 2 : 일반사용자]
    pt INT DEFAULT 10000,								    -- 회원 포인트
    visited INT DEFAULT 0									-- 회원 방문수
);

CREATE TABLE board(
      no INT PRIMARY KEY AUTO_INCREMENT,
      title VARCHAR(500) NOT NULL,
      content VARCHAR(1000) NOT NULL,
      author VARCHAR(50),
      FILE VARCHAR(1000),
      resdate timestamp DEFAULT CURRENT_TIMESTAMP
)

CREATE TABLE recomment(
      NO INT PRIMARY KEY AUTO_INCREMENT,
      id_no INT NOT NULL,
      COMMENT VARCHAR(300) NOT null
)

CREATE TABLE file(
     fno INT PRIMARY KEY AUTO_INCREMENT,   							-- 파일번호: 자동발생
     par INT NOT NULL,   													-- 해당 게시글 번호
     saveFolder VARCHAR(1000) NOT NULL,								-- 파일 저장 폴더
     originNm VARCHAR(500) NOT NULL,									-- 파일 원래 이름
     saveNm VARCHAR(500) NOT NULL,										-- 파일 저장 이름
     fileType VARCHAR(100) NOT NULL,									-- 파일 확장자
     uploadDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,	-- 파일 업로드 일자
     toUse VARCHAR(100) NOT NULL										-- 사용 테이블
);


CREATE TABLE product(
                        pno INT AUTO_INCREMENT PRIMARY KEY,						-- 상품 번호 : 자동증가
                        userNo INT NOT NULL,											-- 고객번호
                        userId VARCHAR(100) NOT NULL,								-- 고객 아이디
                        title VARCHAR(150),								-- 상품 제목
                        cate VARCHAR(100),								-- 상품 카테고리
                        content VARCHAR(2000),							-- 상품 내용
                        startDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),	-- 상품 시작 기간 - 오프라인 사용
                        ingDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),		-- 상품 종료 기간 - 오프라인 사용	teacherNm VARCHAR(20),
                        thumbnail VARCHAR(100),                				-- 상품 썸네일
                        price INT, 											-- 강의가격
                        addr1 VARCHAR(100),												-- 상품 지역
                        visited INT DEFAULT 0,
                        STATUS INT DEFAULT 0
);

insert into product values(DEFAULT, 123, 'kyo', '요리', '전자기기', '핸드폰 팝니다.', default, DEFAULT, '사진들', 50000, '계산동', default)