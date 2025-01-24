#!/bin/bash
set -e

# 마스터가 준비될 때까지 대기
until mysql -h db-master -u root -p$MYSQL_ROOT_PASSWORD -e "SHOW DATABASES;"; do
  echo "마스터 데이터베이스를 기다리는 중..."
  sleep 3
done

# 마스터에서 Dump 받기
echo "마스터 DB 덤프 시작"
mysqldump -h db-master -u root -p"$MYSQL_ROOT_PASSWORD" \
  --all-databases \
  --single-transaction \
  --set-gtid-purged=ON \
  --master-data=2 > /tmp/master_dump.sql
echo "마스터 DB 덤프 완료: /tmp/master_dump.sql"

echo "레플리카 서버에 DB 덤프 파일 Import"
mysql -u root -p"$MYSQL_ROOT_PASSWORD" < /tmp/master_dump.sql
echo "Import 완료."

# 레플리카 구성
mysql -u root -p$MYSQL_ROOT_PASSWORD <<EOF

CHANGE REPLICATION SOURCE TO
    SOURCE_HOST='db-master',
    SOURCE_PORT=3306,
    SOURCE_USER='$MYSQL_REPLICATION_USER',
    SOURCE_PASSWORD='$MYSQL_REPLICATION_PASSWORD',
    SOURCE_AUTO_POSITION=1,
    GET_SOURCE_PUBLIC_KEY=1;

    START REPLICA;
EOF
