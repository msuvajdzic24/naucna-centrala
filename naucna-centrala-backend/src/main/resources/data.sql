-- roles
insert into role(id, name) values (1, 'admin')
insert into role(id, name) values (2, 'editor')
insert into role(id, name) values (3, 'reviewer')
insert into role(id, name) values (4, 'author')
insert into role(id, name) values (5, 'buyer')

-- permission
insert into permission(id, name) values (1, 'get_journal')
insert into permission(id, name) values (2, 'get_journals')
insert into permission(id, name) values (3, 'get_journal_editions')
insert into permission(id, name) values (4, 'subscribe_journal')
insert into permission(id, name) values (5, 'unsubscribe_journal')
insert into permission(id, name) values (6, 'membership_fee_journal')
insert into permission(id, name) values (7, 'get_edition')
insert into permission(id, name) values (8, 'get_editions')
insert into permission(id, name) values (9, 'get_edition_articles')
insert into permission(id, name) values (10, 'buy_edition')
insert into permission(id, name) values (11, 'get_article')
insert into permission(id, name) values (12, 'get_articles')
insert into permission(id, name) values (13, 'buy_article')


insert into role_permissions(role_id, permissions_id) values (4, 1)
insert into role_permissions(role_id, permissions_id) values (5, 1)
insert into role_permissions(role_id, permissions_id) values (4, 2)
insert into role_permissions(role_id, permissions_id) values (5, 2)
insert into role_permissions(role_id, permissions_id) values (5, 3)
insert into role_permissions(role_id, permissions_id) values (5, 4)
insert into role_permissions(role_id, permissions_id) values (5, 5)
insert into role_permissions(role_id, permissions_id) values (4, 6)
insert into role_permissions(role_id, permissions_id) values (5, 7)
insert into role_permissions(role_id, permissions_id) values (5, 8)
insert into role_permissions(role_id, permissions_id) values (5, 9)
insert into role_permissions(role_id, permissions_id) values (5, 10)
insert into role_permissions(role_id, permissions_id) values (5, 11)
insert into role_permissions(role_id, permissions_id) values (5, 12)
insert into role_permissions(role_id, permissions_id) values (5, 13)


-- scientific areas
insert into scientific_area(id, name) values (1, 'biological sciences')
insert into scientific_area(id, name) values (2, 'business and commerce')
insert into scientific_area(id, name) values (3, 'health sciences')
insert into scientific_area(id, name) values (4, 'humanities')
insert into scientific_area(id, name) values (5, 'physical sciences')
insert into scientific_area(id, name) values (6, 'social science')

-- users
insert into user(id, username, password, email, activated) values (1, 'admin', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'admin@mail.com', true)
insert into administrator(id) values (1)
insert into user_roles(user_id, roles_id) values (1, 1)

insert into user(id, username, password, email, activated) values (2, 'chiefeditor', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'chiefeditor@mail.com', true)
insert into editor(id, name, surname, city, country, title, journal_id) values (2, 'editor', 'chief', 'Novi Sad', 'Serbia', 'PhD', null)
insert into editor_areas(editor_id, areas_id) values (2, 1)
insert into editor_areas(editor_id, areas_id) values (2, 3)
insert into user_roles(user_id, roles_id) values (2, 2)

insert into user(id, username, password, email, activated) values (3, 'chiefeditor2', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'chiefeditor2@mail.com', true)
insert into editor(id, name, surname, city, country, title, journal_id) values (3, 'editor', 'chief2', 'Novi Sad', 'Serbia', 'PhD', null)
insert into editor_areas(editor_id, areas_id) values (3, 2)
insert into user_roles(user_id, roles_id) values (3, 2)

insert into user(id, username, password, email, activated) values (4, 'chiefeditor3', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'chiefeditor3@mail.com', true)
insert into editor(id, name, surname, city, country, title, journal_id) values (4, 'editor', 'chief3', 'Novi Sad', 'Serbia', 'PhD', null)
insert into editor_areas(editor_id, areas_id) values (4, 4)
insert into user_roles(user_id, roles_id) values (4, 2)

insert into user(id, username, password, email, activated) values (5, 'chiefeditor4', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'chiefeditor4@mail.com', true)
insert into editor(id, name, surname, city, country, title, journal_id) values (5, 'editor', 'chief4', 'Novi Sad', 'Serbia', 'PhD', null)
insert into editor_areas(editor_id, areas_id) values (5, 5)
insert into user_roles(user_id, roles_id) values (5, 2)

insert into user(id, username, password, email, activated) values (6, 'editor1', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'editor1@mail.com', true)
insert into editor(id, name, surname, city, country, title, journal_id) values (6, 'editor', '1', 'Novi Sad', 'Serbia', 'PhD', null)
insert into editor_areas(editor_id, areas_id) values (6, 1)
insert into user_roles(user_id, roles_id) values (6, 2)

insert into user(id, username, password, email, activated) values (7, 'editor2', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'editor2@mail.com', true)
insert into editor(id, name, surname, city, country, title, journal_id) values (7, 'editor', '2', 'Novi Sad', 'Serbia', 'PhD', null)
insert into editor_areas(editor_id, areas_id) values (7, 4)
insert into user_roles(user_id, roles_id) values (7, 2)

insert into user(id, username, password, email, activated) values (8, 'editor3', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'editor3@mail.com', true)
insert into editor(id, name, surname, city, country, title, journal_id) values (8, 'editor', '3', 'Novi Sad', 'Serbia', 'PhD', null)
insert into editor_areas(editor_id, areas_id) values (8, 3)
insert into user_roles(user_id, roles_id) values (8, 2)

insert into user(id, username, password, email, activated) values (9, 'editor4', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'editor4@mail.com', true)
insert into editor(id, name, surname, city, country, title, journal_id) values (9, 'editor', '4', 'Novi Sad', 'Serbia', 'PhD', null)
insert into editor_areas(editor_id, areas_id) values (9, 6)
insert into user_roles(user_id, roles_id) values (9, 2)

insert into user(id, username, password, email, activated) values (10, 'editor5', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'editor5@mail.com', true)
insert into editor(id, name, surname, city, country, title, journal_id) values (10, 'editor', '5', 'Novi Sad', 'Serbia', 'PhD', null)
insert into editor_areas(editor_id, areas_id) values (10, 6)
insert into user_roles(user_id, roles_id) values (10, 2)

insert into user(id, username, password, email, activated) values (11, 'reviewer1', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'reviewer1@mail.com', true)
insert into reviewer(id, name, surname, city, country, title) values (11, 'reviewer', '1', 'Novi Sad', 'Serbia', 'PhD')
insert into reviewer_areas(reviewer_id, areas_id) values (11, 1)
insert into reviewer_areas(reviewer_id, areas_id) values (11, 2)
insert into user_roles(user_id, roles_id) values (11, 3)

insert into user(id, username, password, email, activated) values (12, 'reviewer2', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'reviewer2@mail.com', true)
insert into reviewer(id, name, surname, city, country, title) values (12, 'reviewer', '2', 'Novi Sad', 'Serbia', 'PhD')
insert into reviewer_areas(reviewer_id, areas_id) values (12, 2)
insert into reviewer_areas(reviewer_id, areas_id) values (12, 3)
insert into reviewer_areas(reviewer_id, areas_id) values (12, 4)
insert into user_roles(user_id, roles_id) values (12, 3)

insert into user(id, username, password, email, activated) values (13, 'reviewer3', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'reviewer3@mail.com', true)
insert into reviewer(id, name, surname, city, country, title) values (13, 'reviewer', '3', 'Novi Sad', 'Serbia', 'PhD')
insert into reviewer_areas(reviewer_id, areas_id) values (13, 5)
insert into reviewer_areas(reviewer_id, areas_id) values (13, 6)
insert into user_roles(user_id, roles_id) values (13, 3)

insert into user(id, username, password, email, activated) values (14, 'author1', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'author1@mail.com', true)
insert into author(id, name, surname, city, country) values (14, 'author', '1', 'Belgrade', 'Serbia')
insert into user_roles(user_id, roles_id) values (14, 4)

insert into user(id, username, password, email, activated) values (15, 'author2', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'author2@mail.com', true)
insert into author(id, name, surname, city, country) values (15, 'author', '2', 'Belgrade', 'Serbia')
insert into user_roles(user_id, roles_id) values (15, 4)

insert into user(id, username, password, email, activated) values (16, 'buyer1', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'buyer1@mail.com', true)
insert into buyer(id) values (16)
insert into user_roles(user_id, roles_id) values (16, 5)

insert into user(id, username, password, email, activated) values (17, 'buyer2', '$2a$04$BbwK95rER/4T0Y0xzQg9feVamyuJQc6ZjwzB3je1cvGf3sAEN9ZMq', 'buyer2@mail.com', true)
insert into buyer(id) values (17)
insert into user_roles(user_id, roles_id) values (17, 5)

-- journals
insert into journal(id, name, isbn, type, chief_editor_id, edition_price, article_price, currency, merchant_id) values (1, 'journal1', '123-345-67890', 0, 2, 50.5, 10.20, 'EUR', '36f2621a-7be1-445c-af01-d15b97381ec4')
insert into journal_areas(journal_id, areas_id) values (1, 1)
insert into journal_areas(journal_id, areas_id) values (1, 2)
insert into journal_areas(journal_id, areas_id) values (1, 4)
insert into journal_editors(journal_id, editors_id, editors_key) values (1, 6, 'biological sciences')
insert into journal_editors(journal_id, editors_id, editors_key) values (1, 7, 'humanities')
insert into journal_reviewers(journal_id, reviewers_id) values (1, 11)
insert into journal_reviewers(journal_id, reviewers_id) values (1, 12)
update editor set journal_id = 1 where id = 2
update editor set journal_id = 1 where id = 6
update editor set journal_id = 1 where id = 7

insert into journal(id, name, isbn, type, chief_editor_id, edition_price, article_price, currency, merchant_id) values (2, 'journal2', '111-345-67890', 1, 3, 20.8, 2.4, 'USD', '36f2621a-7be1-445c-af01-d15b97381ec4')
insert into journal_areas(journal_id, areas_id) values (2, 2)
insert into journal_areas(journal_id, areas_id) values (2, 3)
insert into journal_areas(journal_id, areas_id) values (2, 4)
insert into journal_areas(journal_id, areas_id) values (2, 5)
insert into journal_areas(journal_id, areas_id) values (2, 6)
insert into journal_editors(journal_id, editors_id, editors_key) values (2, 8, 'health sciences')
insert into journal_editors(journal_id, editors_id, editors_key) values (2, 9, 'social science')
insert into journal_reviewers(journal_id, reviewers_id) values (2, 11)
insert into journal_reviewers(journal_id, reviewers_id) values (2, 12)
insert into journal_reviewers(journal_id, reviewers_id) values (2, 13)
update editor set journal_id = 2 where id = 3
update editor set journal_id = 2 where id = 8
update editor set journal_id = 2 where id = 9

-- editions
insert into edition(id, number, journal_id) values (1, 1, 1)
insert into edition(id, number, journal_id) values (2, 2, 1)
insert into edition(id, number, journal_id) values (3, 3, 1)
insert into edition(id, number, journal_id) values (4, 1, 1)
insert into edition(id, number, journal_id) values (5, 2, 2)
insert into edition(id, number, journal_id) values (6, 3, 2)
insert into edition(id, number, journal_id) values (7, 4, 2)
insert into edition(id, number, journal_id) values (8, 5, 2)
insert into edition(id, number, journal_id) values (9, 6, 2)

-- articles
--insert into article(id, name, area_id, author_id, edition_id) values (1, 'article1', 1, 14, 1)
--insert into article(id, name, area_id, author_id, edition_id) values (2, 'article2', 3, 14, 2)
--insert into article(id, name, area_id, author_id, edition_id) values (3, 'article3', 4, 15, 4)
--insert into article(id, name, area_id, author_id, edition_id) values (4, 'article4', 5, 15, 4)
--insert into article(id, name, area_id, author_id, edition_id) values (5, 'article5', 2, 14, 6)
--insert into article(id, name, area_id, author_id, edition_id) values (6, 'article6', 6, 15, 7)

-- order

-- subscribe

-- review