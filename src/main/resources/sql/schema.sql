DROP TABLE IF EXISTS members;
CREATE TABLE members (
                       member_id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 고유 ID (Primary Key)
                       phone_number VARCHAR(15) NOT NULL, -- 전화번호
                       username VARCHAR(50) UNIQUE NOT NULL, -- 사용자성명
                       password VARCHAR(255) NOT NULL, -- 비밀번호 (암호화된 상태로 저장)
                       role ENUM('PARENT', 'CHILD') NOT NULL, -- 역할: 부모 또는 자녀
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성 시간
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 업데이트 시간
);




