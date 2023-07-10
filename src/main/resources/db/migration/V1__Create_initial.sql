create table events (
    id             bigserial not null primary key,
    title          text      not null,
    start_datetime timestamp not null,
    end_datetime   timestamp not null
);

create table libraries (
    id    bigserial not null primary key,
    name  text      not null,
    event bigint    not null references events (id)
);

create table games (
    id    bigserial not null primary key,
    title text      not null
);

create table game_copies (
    id      bigserial not null primary key,
    owner   text      not null,
    notes   text,
    game_id bigint    not null references games (id)
);

create table libraries_game_copies (
    id              bigserial not null primary key,
    library_copy_id text      not null,
    library_id      bigint    not null references libraries (id),
    game_copy_id    bigint    not null references game_copies (id)
);

create table attendees (
    id         bigserial not null primary key,
    first_name text      not null,
    last_name  text,
    pronouns   text      not null,
    badge_id   text      not null,
    event      bigint    not null references events (id)
);

create table checkouts (
    id             bigserial not null primary key,
    start_datetime timestamp not null,
    end_datetime   timestamp,
    game_copy_id   bigint    not null references game_copies (id),
    attendee_id    bigint    not null references attendees (id)
);

create table plays (
    id          bigserial not null primary key,
    attendee_id bigint    not null references attendees (id),
    checkout_id bigint    not null references checkouts (id),
    rating      smallint
);