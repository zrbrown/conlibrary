CREATE OR REPLACE PROCEDURE create_data()
    LANGUAGE plpgsql
AS
$$
declare
    geekway23                       bigint;
    common                          bigint;
    playandwin                      bigint;
    scythe                          bigint;
    sevenwonders                    bigint;
    hanabi                          bigint;
    powergrid                       bigint;
    terramystica                    bigint;
    tickettoride                    bigint;
    tzolkin                         bigint;
    inis                            bigint;
    carcassonne                     bigint;
    dragonsgold                     bigint;
    tichu                           bigint;
    terraformingmars                bigint;
    castlesofmadkingludwig          bigint;
    winlosebanana                   bigint;
    loveletter                      bigint;
    tichu_copy_1                    bigint;
    sevenwonders_copy_1             bigint;
    loveletter_copy_1               bigint;
    winlosebanana_copy_1            bigint;
    terraformingmars_copy_1         bigint;
    terraformingmars_copy_2         bigint;
    carcassonne_copy_1              bigint;
    dragonsgold_copy_1              bigint;
    tichu_library_copy_1            bigint;
    sevenwonders_library_copy_1     bigint;
    loveletter_library_copy_1       bigint;
    terraformingmars_library_copy_1 bigint;
    terraformingmars_library_copy_2 bigint;
    carcassonne_library_copy_1      bigint;
    dragonsgold_library_copy_1      bigint;
    zack                            bigint;
    nathan                          bigint;
    brendon                         bigint;
    aaron                           bigint;
    robin                           bigint;
    irina                           bigint;
    joe                             bigint;
    checkout_tichu_1                bigint;
    checkout_tichu_2                bigint;
    checkout_sevenwonders_1         bigint;
    checkout_loveletter_1           bigint;
    checkout_terraformingmars_1     bigint;
    checkout_terraformingmars_2     bigint;
    checkout_carcassonne_1          bigint;
    checkout_dragonsgold_1          bigint;
begin
    insert into events(title, start_datetime, end_datetime)
    values ('Geekway 2023', now() - interval '1 day', now() + interval '2 days')
    returning id into geekway23;
    commit;

    insert into libraries(name, event) values ('Common', geekway23) returning id into common;
    insert into libraries(name, event) values ('Play & Win', geekway23) returning id into playandwin;
    commit;

    insert into games(title) values ('Scythe') returning id into scythe;
    insert into games(title) values ('7 Wonders') returning id into sevenwonders;
    insert into games(title) values ('Hanabi') returning id into hanabi;
    insert into games(title) values ('Power Grid') returning id into powergrid;
    insert into games(title) values ('Terra Mystica') returning id into terramystica;
    insert into games(title) values ('Ticket to Ride') returning id into tickettoride;
    insert into games(title) values ('Tzolk''in') returning id into tzolkin;
    insert into games(title) values ('Inis') returning id into inis;
    insert into games(title) values ('Carcassonne') returning id into carcassonne;
    insert into games(title) values ('Dragon''s Gold') returning id into dragonsgold;
    insert into games(title) values ('Tichu') returning id into tichu;
    insert into games(title) values ('Terraforming Mars') returning id into terraformingmars;
    insert into games(title) values ('Castles of Mad King Ludwig') returning id into castlesofmadkingludwig;
    insert into games(title) values ('Win, Lose, or Banana') returning id into winlosebanana;
    insert into games(title) values ('Love Letter') returning id into loveletter;
    commit;

    insert into game_copies(game_id, owner, notes)
    values (tichu, 'Brendon Faithfull', 'Missing most pieces')
    returning id into tichu_copy_1;
    insert into game_copies(game_id, owner, notes)
    values (sevenwonders, 'Zack Brown', 'special box')
    returning id into sevenwonders_copy_1;
    insert into game_copies(game_id, owner, notes)
    values (loveletter, 'Geekway', 'Donated by Miniature Market')
    returning id into loveletter_copy_1;
    insert into game_copies(game_id, owner, notes)
    values (winlosebanana, 'Geekway', 'Donated by Miniature Market')
    returning id into winlosebanana_copy_1;
    insert into game_copies(game_id, owner, notes)
    values (terraformingmars, 'Geekway', 'Donated by Miniature Market')
    returning id into terraformingmars_copy_1;
    insert into game_copies(game_id, owner, notes)
    values (terraformingmars, 'Geekway', 'Donated by Miniature Market')
    returning id into terraformingmars_copy_2;
    insert into game_copies(game_id, owner, notes)
    values (carcassonne, 'Geekway', 'Donated by Miniature Market')
    returning id into carcassonne_copy_1;
    insert into game_copies(game_id, owner, notes)
    values (dragonsgold, 'Geekway', 'Donated by Miniature Market')
    returning id into dragonsgold_copy_1;
    commit;

    insert into library_game_copies(library_copy_id, library_id, game_copy_id)
    values ('11111', common, tichu_copy_1)
    returning id into tichu_library_copy_1;
    insert into library_game_copies(library_copy_id, library_id, game_copy_id)
    values ('11112', common, sevenwonders_copy_1)
    returning id into sevenwonders_library_copy_1;
    insert into library_game_copies(library_copy_id, library_id, game_copy_id)
    values ('11113', common, loveletter_copy_1)
    returning id into loveletter_library_copy_1;
    insert into library_game_copies(library_copy_id, library_id, game_copy_id)
    values ('11114', common, terraformingmars_copy_1)
    returning id into terraformingmars_library_copy_1;
    insert into library_game_copies(library_copy_id, library_id, game_copy_id)
    values ('88888', common, terraformingmars_copy_2)
    returning id into terraformingmars_library_copy_2;
    insert into library_game_copies(library_copy_id, library_id, game_copy_id)
    values ('99999', common, carcassonne_copy_1)
    returning id into carcassonne_library_copy_1;
    insert into library_game_copies(library_copy_id, library_id, game_copy_id)
    values ('66666', common, dragonsgold_copy_1)
    returning id into dragonsgold_library_copy_1;
    commit;

    insert into attendees(first_name, last_name, pronouns, badge_id, event_id)
    values ('Zack', 'Brown', 'he/him', '55551', geekway23)
    returning id into zack;
    insert into attendees(first_name, last_name, pronouns, badge_id, event_id)
    values ('Nathan', 'Vonder Haar', 'he/him', '55552', geekway23)
    returning id into nathan;
    insert into attendees(first_name, last_name, pronouns, badge_id, event_id)
    values ('Brendon', 'Faithfull', 'he/him', '99999', geekway23)
    returning id into brendon;
    insert into attendees(first_name, last_name, pronouns, badge_id, event_id)
    values ('Aaron', 'Bruce', 'he/him', '55554', geekway23)
    returning id into aaron;
    insert into attendees(first_name, last_name, pronouns, badge_id, event_id)
    values ('Robin', 'Carroll-Dolci', 'he/him', '66666', geekway23)
    returning id into robin;
    insert into attendees(first_name, last_name, pronouns, badge_id, event_id)
    values ('Irina', 'Pushel', 'she/her', '88888', geekway23)
    returning id into irina;
    insert into attendees(first_name, last_name, pronouns, badge_id, event_id)
    values ('Joe', 'Hawkes-Cates', 'he/him', '55556', geekway23)
    returning id into joe;
    commit;

    insert into checkouts(start_datetime, end_datetime, library_game_copy_id, attendee_id)
    values (now() - interval '1 hour', null, tichu_library_copy_1, zack)
    returning id into checkout_tichu_1;
    insert into checkouts(start_datetime, end_datetime, library_game_copy_id, attendee_id)
    values (now() - interval '3 hours', now() - interval '1 hour 1 minute', sevenwonders_library_copy_1, zack)
    returning id into checkout_sevenwonders_1;
    insert into checkouts(start_datetime, end_datetime, library_game_copy_id, attendee_id)
    values (now() - interval '5 hours', now() - interval '4 hours 3 minutes', tichu_library_copy_1, zack)
    returning id into checkout_tichu_2;
    insert into checkouts(start_datetime, end_datetime, library_game_copy_id, attendee_id)
    values (now() - interval '7 hours', now() - interval '6 hours 3 minutes', loveletter_library_copy_1, nathan)
    returning id into checkout_loveletter_1;
    insert into checkouts(start_datetime, end_datetime, library_game_copy_id, attendee_id)
    values (now() - interval '3 hours 55 minutes', null, terraformingmars_library_copy_1, brendon)
    returning id into checkout_terraformingmars_1;
    insert into checkouts(start_datetime, end_datetime, library_game_copy_id, attendee_id)
    values (now() - interval '4 hours 1 minute', null, carcassonne_library_copy_1, aaron)
    returning id into checkout_carcassonne_1;
    insert into checkouts(start_datetime, end_datetime, library_game_copy_id, attendee_id)
    values (now() - interval '9 hours', null, dragonsgold_library_copy_1, robin)
    returning id into checkout_dragonsgold_1;
    insert into checkouts(start_datetime, end_datetime, library_game_copy_id, attendee_id)
    values (now() - interval '2 hours', null, terraformingmars_library_copy_2, joe)
    returning id into checkout_terraformingmars_2;
    commit;

    insert into plays(attendee_id, checkout_id, rating)
    values (zack, checkout_tichu_1, 5);
    insert into plays(attendee_id, checkout_id, rating)
    values (zack, checkout_sevenwonders_1, 5);
    insert into plays(attendee_id, checkout_id, rating)
    values (zack, checkout_tichu_2, 4);
    insert into plays(attendee_id, checkout_id, rating)
    values (zack, checkout_loveletter_1, 3);
    commit;
END;
$$;
call create_data();