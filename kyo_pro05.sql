CREATE DATABASE pro05;

USE pro05;

-- 회원
CREATE TABLE member(
    mno BIGINT PRIMARY KEY AUTO_INCREMENT,            -- 고유번호
    id VARCHAR(20) UNIQUE KEY NOT NULL,             -- 로그인 아이디
    pw VARCHAR(300) NOT NULL,                       -- 비밀번호
    name VARCHAR(100) NOT NULL,                     -- 이름
    tel VARCHAR(20) NOT NULL,                       -- 전화번호
    email VARCHAR(100),                             -- 이메일
    addr1 VARCHAR(100),                             -- 주소
    addr2 VARCHAR(200),                             -- 상세 주소
    addr3 VARCHAR(100),                             -- 주요 직거래 주소
    postcode VARCHAR(10),                           -- 우편 번호
    status VARCHAR(50) DEFAULT 'ACTIVE',            -- REMOVE(삭제), DORMANT(휴면), ACTIVE(활동)
    createAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,   -- 회원 등록일
    loginAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP     -- 마지막 로그인
);

INSERT INTO member (id, pw, name, tel, status) VALUES ('admin', '$2a$10$oS1.3wpbnpIanIW4RoXxSOea/vGIijBMpLUBxZqurQqNjjMiJHgGa', 'admin', '010-1111-1111', default);
INSERT INTO member (id, pw, name, tel) VALUES ('teacher1', '$2a$10$oS1.3wpbnpIanIW4RoXxSOea/vGIijBMpLUBxZqurQqNjjMiJHgGa', '김쌤1', '010-1111-1111');
INSERT INTO member (id, pw, name, tel) VALUES ('teacher2', '$2a$10$oS1.3wpbnpIanIW4RoXxSOea/vGIijBMpLUBxZqurQqNjjMiJHgGa', '김쌤2', '010-1111-1111');
INSERT INTO member (id, pw, name, tel) VALUES ('teacher3', '$2a$10$oS1.3wpbnpIanIW4RoXxSOea/vGIijBMpLUBxZqurQqNjjMiJHgGa', '김쌤3', '010-1111-1111');
INSERT INTO member (id, pw, name, tel, status) VALUES ('rest', '$2a$10$oS1.3wpbnpIanIW4RoXxSOea/vGIijBMpLUBxZqurQqNjjMiJHgGa', 'admin', '010-1111-1111', 'sendjin@gmail.com', 'REST');
INSERT INTO member (id, pw, name, tel, status) VALUES ('out', '$2a$10$oS1.3wpbnpIanIW4RoXxSOea/vGIijBMpLUBxZqurQqNjjMiJHgGa', 'admin', '010-1111-1111', 'OUTSIDE');
UPDATE member set loginAt='2023-08-15 15:12:35' WHERE id='rest'
UPDATE member set status='ACTIVE' WHERE id='rest'

CREATE EVENT daily_update_status
    ON SCHEDULE
    EVERY 60 SECOND
    STARTS CURRENT_TIMESTAMP
            DO
UPDATE member
SET status = 'REST'
WHERE loginAt <= DATE_SUB(NOW(), INTERVAL 30 DAY);

-- 상품 카테고리 (추가됨)
CREATE TABLE category(
	cate VARCHAR(50) NOT NULL PRIMARY KEY, 	-- 카테고리 코드
	cateName VARCHAR(100) NOT NULL);				-- 카테고리 이름
	
INSERT INTO category VALUES('A', '국어');
INSERT INTO category VALUES('B', '수학');
INSERT INTO category VALUES('C', '영어');

-- 중고거래 상품
CREATE TABLE product(
    pno BIGINT PRIMARY KEY AUTO_INCREMENT,
    pname VARCHAR(300) NOT NULL,                                -- 상품 이름
    content VARCHAR(2000),                                      -- 상품 설명
    cate VARCHAR(100),                                      	 -- 상품 카테고리
    seller VARCHAR(20) NOT NULL,                                -- 판매자 member.id
    price INT NOT NULL DEFAULT 0,                               -- 상품 가격
    proaddr VARCHAR(100),                                       -- 직거래 동네
    image BIGINT,                                                 -- 썸네일 이미지
    createAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,      -- 등록일
    baseAt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,        -- 끌어올리기 날짜
    status VARCHAR(50) DEFAULT 'SALE',                          -- REMOVE(삭제), 'SALE' 판매중, 'RESERVED' 예약중, 'OUT' 거래 완료
    visited INT DEFAULT 0,                                      -- 조회수
    heart INT DEFAULT 0                                         -- 찜한 수
);

INSERT INTO product VALUES(DEFAULT, '상품1', '상품1 내용', 'A', 'kimbk111', 1000, '가산동', null, DEFAULT, NULL, 'SALE', DEFAULT, DEFAULT);

-- 업로드 파일 관리
CREATE TABLE fileData(
    fileNo BIGINT PRIMARY KEY AUTO_INCREMENT,
    tableName VARCHAR(100),                     -- 테이블 이름
    columnNo INT,                               -- 테이블 PK 번호
    originName VARCHAR(255),                    -- 원래 이름
    saveName VARCHAR(255),                      -- 저장 이름
    savePath VARCHAR(255),                      -- 저장 파일
    fileType VARCHAR(100),                      -- 파일 종류
    status VARCHAR(50) DEFAULT 'ACTIVE'         -- REMOVE(삭제), ACTIVE(활동)
);

-- 채팅방
CREATE TABLE chatRoom (
    roomNo BIGINT PRIMARY KEY AUTO_INCREMENT,  -- 고유 번호
    memId VARCHAR(20) NOT NULL,            -- member.id
    pno INT NOT NULL,                       -- product.pno
    status VARCHAR(50) DEFAULT 'ON',        -- ON(진행), OFF(차단)
    UNIQUE (memId, pno)                    -- memId와 pno를 묶어서 UNIQUE 제약 설정
);

-- 채팅 메시지 ( receiver 추가됨)
CREATE TABLE chatMessage(
    chatNo BIGINT PRIMARY KEY AUTO_INCREMENT,   -- 채팅 번호
    type VARCHAR(20) NOT NULL,                  -- 채팅 타입: ENTER, TALK, LEAVE, NOTICE
    roomNo INT NOT NULL,                        -- 채팅방 번호
    sender VARCHAR(20) NOT NULL,                -- 송신자
    receiver VARCHAR(20) NOT NULL,              -- 수신자
    message VARCHAR(2000) NOT NULL,             -- 채팅 메시지
    status VARCHAR(50) DEFAULT 'UNREAD',        -- 읽음 여부
    time TIMESTAMP DEFAULT CURRENT_TIMESTAMP    -- 채팅 발송 시간
);

-- 사용자 알림 키워드 지정
CREATE TABLE keyword (
    kno BIGINT AUTO_INCREMENT PRIMARY KEY,      -- 고유번호
    word VARCHAR(200) NOT NULL,                 -- 키워드
    uid VARCHAR(20) NOT NULL                    -- member.id
);

-- 알림
CREATE TABLE notification (
    nno BIGINT AUTO_INCREMENT PRIMARY KEY,      -- 고유번호
    tableName VARCHAR(100),                     -- 테이블 이름
    columnNo BIGINT NOT NULL,                   -- 테이블의 PK 번호(keyword의 kno)
    word VARCHAR(200) NOT NULL,                 -- 어떤 키워드에 대한 알림인지
    message VARCHAR(2000),                      -- 알림 내용
    uid VARCHAR(20) NOT NULL,                   -- member.id
    status VARCHAR(50) DEFAULT 'UNREAD'         -- 읽음 여부
);

CREATE TABLE notice(
    no INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(500) NOT NULL,
    content VARCHAR(1000) NOT NULL,
    author VARCHAR(50),
    img VARCHAR(1000),
    resdate timestamp DEFAULT CURRENT_TIMESTAMP
)

CREATE TABLE recomment(
    NO INT PRIMARY KEY AUTO_INCREMENT,
    mem_id VARCHAR(100) NOT NULL,
    COMMENT VARCHAR(300) NOT null
);

-- 찜
CREATE TABLE wish (
    wno BIGINT AUTO_INCREMENT PRIMARY KEY,      -- 고유 번호
    pno BIGINT,                                 -- 상품 번호
    uid VARCHAR(20) NOT NULL,                   -- member.id
    status INT DEFAULT 0                        -- 찜 여부
);

-- 상품의 카테고리 이름을 같이 가져오기 위한 view
CREATE VIEW productWithCate AS (
    SELECT pno, pname, seller, price, proaddr, image, createAt, baseAt, status, visited, cateName, heart
    FROM product p
    JOIN category c ON (p.cate = c.cate)
);
