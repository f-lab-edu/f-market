INSERT INTO major_category (category_name, description)
VALUES ('브랜드 패션', '고급 브랜드 의류 및 패션 아이템'),
       ('패션의류', '일반 의류 및 패션 아이템'),
       ('유아', '유아를 위한 제품 및 장난감'),
       ('생필품', '일상 생활에 필요한 필수품'),
       ('문구', '학습 및 사무용 문구류'),
       ('컴퓨터', '컴퓨터 및 관련 액세서리'),
       ('스포츠', '스포츠 용품 및 의류'),
       ('자동차', '자동차 관련 제품 및 액세서리');

INSERT INTO `subcategory` (category_name, major_category_id, description)
VALUES
-- 브랜드 패션 하위 카테고리
('브랜드의류', 1, '브랜드 기반의 다양한 의류'),
('브랜드 여성의류', 1, '브랜드 여성용 의류'),
('브랜드 남성의류', 1, '브랜드 남성용 의류'),
('브랜드 캐주얼의류', 1, '캐주얼 브랜드 의류'),
('브랜드잡화', 1, '브랜드 액세서리 및 잡화'),
('브랜드 쥬얼리/시계', 1, '브랜드 쥬얼리 및 시계'),
('수입명품', 1, '해외 수입 명품 제품'),
('스포츠브랜드', 1, '스포츠 관련 브랜드 상품'),
('브랜드 아웃도어', 1, '아웃도어 브랜드 의류 및 용품'),
('브랜드 스포츠패션', 1, '스포츠와 패션의 조화된 브랜드 제품'),

-- 패션의류 하위 카테고리
('브랜드의류', 2, '브랜드 패션과 겹치는 의류'),
('브랜드 여성의류', 2, '브랜드 여성용 의류'),
('브랜드 남성의류', 2, '브랜드 남성용 의류'),
('브랜드 캐주얼의류', 2, '캐주얼한 브랜드 의류'),

-- 유아 하위 카테고리
('유아동', 3, '유아 및 아동을 위한 제품'),
('출산/육아', 3, '출산 및 육아에 필요한 제품'),
('장난감/완구', 3, '유아동 장난감 및 완구'),
('유아동 의류', 3, '유아 및 아동용 의류'),
('유아동 신발/잡화', 3, '유아 및 아동용 신발 및 잡화'),

-- 생필품 하위 카테고리
('생필품', 4, '생활에 필요한 필수품'),
('바디/헤어', 4, '신체 및 헤어 관리용품'),

-- 문구 하위 카테고리
('문구', 5, '필기구 및 학습용 문구류'),
('문구/사무용품', 5, '사무용 및 학습용 문구'),
('사무기기', 5, '사무실에서 사용하는 기기'),

-- 컴퓨터 하위 카테고리
('컴퓨터', 6, '컴퓨터 및 관련 제품'),
('노트북/데스크탑', 6, '노트북 및 데스크탑 PC'),
('모니터/프린터', 6, '모니터 및 프린터 기기'),
('PC주변기기', 6, '컴퓨터 주변 기기 및 액세서리'),
('저장장치', 6, '컴퓨터 및 디지털 저장장치'),

-- 스포츠 하위 카테고리
('스포츠의류/운동화', 7, '스포츠용 의류 및 신발'),
('휘트니스/수영', 7, '휘트니스 및 수영 관련 용품'),
('구기/라켓', 7, '구기 및 라켓 스포츠 용품'),
('골프', 7, '골프 관련 장비 및 의류'),
('자전거/보드/기타레저', 7, '자전거, 보드 및 기타 레저 용품'),
('캠핑/낚시', 7, '캠핑 및 낚시에 필요한 용품'),
('등산/아웃도어', 7, '등산 및 아웃도어 활동용품'),

-- 자동차 하위 카테고리
('자동차용품', 8, '자동차 관련 용품'),
('공구', 8, '자동차와 관련된 공구'),
('공구/안전/산업용품', 8, '공구 및 안전 장비, 산업 용품');
