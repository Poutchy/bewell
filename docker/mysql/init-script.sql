-- Create databases if they don't exist
CREATE DATABASE IF NOT EXISTS client_service;
CREATE DATABASE IF NOT EXISTS booking_service;
CREATE DATABASE IF NOT EXISTS payment_service;
CREATE DATABASE IF NOT EXISTS auth_service;

-- Create user and grant privileges (if needed)
CREATE USER IF NOT EXISTS 'tahabens'@'%' IDENTIFIED BY 'taassbewell';
GRANT ALL PRIVILEGES ON client_service.* TO 'tahabens'@'%';
GRANT ALL PRIVILEGES ON booking_service.* TO 'tahabens'@'%';
GRANT ALL PRIVILEGES ON payment_service.* TO 'tahabens'@'%';
GRANT ALL PRIVILEGES ON auth_service.* TO 'tahabens'@'%';
FLUSH PRIVILEGES;
