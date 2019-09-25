create table if not exists file_import (
    id serial primary key,
    source text not null,
    import_date timestamp not null,
    status varchar not null,
    creation timestamp not null,
    last_update timestamp not null
)