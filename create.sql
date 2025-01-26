
    create table author (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table book (
        id bigint not null auto_increment,
        isbn varchar(255),
        published_date varchar(255),
        title varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table books_authors (
        author_id bigint not null,
        book_id bigint not null,
        primary key (author_id, book_id)
    ) engine=InnoDB;

    create table books_genres (
        book_id bigint not null,
        genre_id bigint not null,
        primary key (book_id, genre_id)
    ) engine=InnoDB;

    create table genre (
        id bigint not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table books_authors 
       add constraint FKov77rfnon6gwd8emlsah05qty 
       foreign key (author_id) 
       references author (id);

    alter table books_authors 
       add constraint FK6ojkw2gy23xsgdkqih628favt 
       foreign key (book_id) 
       references book (id);

    alter table books_genres 
       add constraint FKm0yqdd4t2neamf9s7pe9pqg19 
       foreign key (genre_id) 
       references genre (id);

    alter table books_genres 
       add constraint FKih47hjfsl8cfacjhocsvbipfe 
       foreign key (book_id) 
       references book (id);
