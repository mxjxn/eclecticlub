-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(uuid, username, email, password)
VALUES (:uuid, :username, :email, :password)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET username = :username, email = :email
WHERE uuid = :uuid

-- :name login-user! :! :1
-- :doc updates last-login and is-active
UPDATE users
SET last_login = NOW(), is_active = true
WHERE email = :email

-- :name get-user :? :1
-- :doc retrieves a user record given the email
SELECT * FROM users
WHERE email = :email

-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE uuid = :uuid
