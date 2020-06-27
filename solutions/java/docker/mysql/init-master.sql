CREATE USER 'repl'@'%' IDENTIFIED BY 'slave_password';
GRANT REPLICATION SLAVE ON *.* TO 'repl'@'%';
